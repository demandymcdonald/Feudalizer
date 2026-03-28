package com.base.timeline.propagation.core;

import com.GlobalVars;
import com.base.DMRegistry;
import com.base.DateMutableEntity;
import com.base.flags.SandboxCode;
import com.base.flags.StateError;
import com.base.reference.DMEReference;
import com.base.timeline.TimelineContainer;
import com.base.timeline.TimelineState;
import com.base.timeline.change.TimelineChange;
import com.utilities.DateUtilities;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.tuple.Pair;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;

public class Sandbox {
    private CompletableFuture<HashMap<DMEReference<?>, JsonObject>> future  = new CompletableFuture<>();
    private final HashMap<DMEReference<?>, JsonObject> dirty = new HashMap<>();
    private final HashSet<DMEReference<?>> Scope;
    private final ConcurrentLinkedDeque<StateError> ProblemQueue = new ConcurrentLinkedDeque<>();
    private final Objective primary;
    private final LocalDate startDate;
    private volatile LocalDate sandboxEndDate = LocalDate.MAX;
    private volatile SandboxCode status = SandboxCode.CONTINUE;
    private volatile Pair<LocalDate,StateError> killBox;
    /**
     * Propagation cache — tracks changes that have already been confirmed as
     * <em>not</em> nullifiable by the proposed change. When a change is in this
     * set we skip the full {@code canNullify} evaluation on subsequent simulation
     * ticks, avoiding redundant condition checks.
     */
    private final HashSet<TimelineChange<?>> propagationCache = new HashSet<>();
    //TODO I need a way to have secondary saves pulled by default:
    // for example the parent in a deJure change also needs to be saved, even though they aren't the subject.
    public Sandbox(Objective obj) {
        this.primary = obj;
        this.Scope = obj.state().change().getScope();
        this.startDate = obj.state().start();
    }
    public Sandbox(Objective obj, LocalDate endDate) {
        this.primary = obj;
        this.Scope = obj.state().change().getScope();
        this.startDate = obj.state().start();
        sandboxEndDate = endDate;
    }
    public void startSimulation() {
        startup(new HashMap<>());
    }
    public synchronized CompletableFuture<HashMap<DMEReference<?>, JsonObject>> getFuture() {
        return future;
    }
    /**
     * Exposes the flag queue to the UI thread so it can poll for incoming StateErrors
     * and present resolution dialogs to the user.
     */
    public ConcurrentLinkedDeque<StateError> getQueue() {
        return ProblemQueue;
    }

    private void runtime(HashMap<DMEReference<?>, JsonObject> sandbox) {
        DMRegistry.sandboxReInit();
        loadSandbox(sandbox);
        runSimulation();
        shutdown();
    }

    private void startup(HashMap<DMEReference<?>,JsonObject> sandbox) {
        populateSandbox(sandbox);
        Thread thread = new Thread(() -> runtime(sandbox));
        thread.start();

    }

    private void populateSandbox(HashMap<DMEReference<?>,JsonObject> sandbox) {
        for (DMEReference<?> type : Scope) {
            sandbox.put(type,DMRegistry.getEntityData(type.getType(),type.getUuid()));
        }
    }
    private void loadSandbox(HashMap<DMEReference<?>,JsonObject> sandbox) {
        for (DMEReference<?> type : Scope) {
            DMRegistry.load(type,sandbox.get(type));
        }
    }
    public SandboxCode getStatus() {
        return status;
    }
    @SuppressWarnings("unchecked")
    private <HT extends DateMutableEntity<HT,HC>,HC extends TimelineContainer<HC>> void runSimulation() {
        HashSet<StandingChange> standingChanges = new HashSet<>();
        HT host = DMRegistry.getEntity(primary.type(),primary.id());
        TimelineChange<HT> proposedChange = primary.state().change();
        LocalDate date = primary.state().start();
        while (true){
            GlobalVars.setCurrentDate(date);
            TimelineState<HT> state = host.getCurrentState();
            for (TimelineChange<HT> change : state.getChanges()) {
                List<StandingChange> localSC = new ArrayList<>();
                if (change.isDeactivated()) continue;

                // Step 5 — propagation cache: skip changes already confirmed clear.
                if (propagationCache.contains(change)) continue;

                if (proposedChange.canNullify(change)) {
                    // Step 6 — on first nullification, seal the breadcrumb end date and
                    // record the opposite-diff relationship before actually nullifying.
                    if (!change.getBreadcrumb().isComplete()) {
                        change.getBreadcrumb().addEndPoint(date);
                    }
                    change.setOppositeDiff(proposedChange);
                    // Persist the updated state (with the sealed breadcrumb in the trail)
                    // so the trail reflects the end-of-propagation cleanly.
                    host.saveStateChange(host.getCurrentState(), null);
                    change.nullify(host, proposedChange);
                    // Remove from cache since the change is now gone
                    propagationCache.remove(change);
                    continue; // nullified — no conflict check needed
                } else {
                    // Step 5 — this change is not nullifiable; add to cache so we skip it
                    // on future ticks.
                    propagationCache.add(change);
                }

                //boolean allClear = false;
                while (true){
                    List<StateError> errors = proposedChange.doesConflict(change);
                    if (errors.isEmpty()){
                        break;
                    }
                    HashMap<StateError,String> autoResolve = new HashMap<>();
                    for (StateError error : errors){
                        if (error.canAutoResolve()){
                            autoResolve.put(error,"");
                        }
                        long thisKey = StandingChange.generateEventKey(error);
                        for (StandingChange sc : standingChanges){
                            //Pretty sure the isBetween is overkill, but better to be safe than sorry!.
                            if (sc.isMatch(thisKey) && DateUtilities.isBetween(date,sc.start(),sc.endDate())){
                                autoResolve.put(error,sc.resolution());
                                break;
                            }
                        }
                    }
                    errors.removeAll(autoResolve.keySet());
                    Map<StandingChange,StateError> handled = handleError(change,host,errors,autoResolve);
                    localSC.addAll(handled.keySet());
                    if (killBox != null){
                        endSimulation(status,host, killBox.getKey(),standingChanges);
                        return;
                    }
                    standingChanges.addAll(handled.keySet());
                }
            }

            // Step 7 — retrace the breadcrumb trail stored in the current state.
            // Using fuzzyMatch=true: if the proposed change's date falls within any
            // trail entry's propagation window, the trail ends at that entry.
            retraceTrail(proposedChange, host.getCurrentState());

            LocalDate nextDate = host.getNextDate();
            if (nextDate == null || DateUtilities.floor(nextDate,sandboxEndDate).equals(sandboxEndDate)){
                break;
            } else {
                date = nextDate;
            }
        }
        endSimulation(SandboxCode.END_SAVE,host,DateUtilities.ceiling(date,sandboxEndDate),standingChanges);
    }

    /**
     * Step 7 — Retraces the breadcrumb trail held by the given state.
     *
     * <p>For each entry in the trail (oldest first), checks whether the proposed
     * change can nullify it using {@code fuzzyMatch=true}. The first match signals
     * the end of the trail: all subsequent entries are trimmed from the in-memory
     * trail list so that the trail accurately reflects where propagation of the
     * original change should stop. The matched entry's breadcrumb end date is
     * sealed to the proposed change's date if it hasn't been sealed already.</p>
     */
    @SuppressWarnings("unchecked")
    private <HT extends DateMutableEntity<HT,?>> void retraceTrail(TimelineChange<HT> proposedChange, TimelineState<HT> state) {
        if (state == null) return;
        List<TimelineChange<HT>> trail = state.getTrail();
        for (int i = 0; i < trail.size(); i++) {
            TimelineChange<HT> trailChange = trail.get(i);
            if (proposedChange.canNullify(trailChange, true)) {
                // Seal the breadcrumb if not already done
                if (!trailChange.getBreadcrumb().isComplete()) {
                    trailChange.getBreadcrumb().addEndPoint(proposedChange.getDate());
                }
                // Trim the trail to end at this entry (inclusive): remove everything after i.
                state.trail().subList(i + 1, state.trail().size()).clear();
                break;
            }
        }
    }

    /**
     * Handles errors related to a given timeline change involving a specific entity and resolves them
     * based on provided resolution priorities and responses.
     *
     * @param <HT> The type of the DateMutableEntity being modified by the timeline change.
     * @param <HC> The type of the TimelineContainer associated with the DateMutableEntity.
     * @param c The timeline change being processed.
     * @param e The specific entity being modified by the timeline change.
     * @param errors A list of {@link StateError} objects representing the initial errors to be handled.
     * @param autoResolves A map of {@link StateError} objects to their corresponding resolution values,
     *                     used for prioritizing and resolving errors automatically.
     * @return A map where each key is a {@link StandingChange} representing a processed change,
     *         and the corresponding value is the associated {@link StateError}.
     */
    private <HT extends DateMutableEntity<HT,HC>,HC extends TimelineContainer<HC>> Map<StandingChange,StateError> handleError(TimelineChange<HT> c, HT e, List<StateError> errors, HashMap<StateError,String> autoResolves){
        TreeMap<Integer,Pair<StateError,String>> errorsByPriority = new TreeMap<>();
        for (Map.Entry<StateError,String> entry : autoResolves.entrySet()){
            String resolution = entry.getValue();
            StateError error = entry.getKey();
            errorsByPriority.put(error.getResolutionPriority(resolution), Pair.of(error,entry.getValue()));
        }
        ProblemQueue.addAll(errors);
        final int toProcessSize = errors.size() + autoResolves.size();
        while (toProcessSize > errorsByPriority.size()){
            for (StateError error : errors){
                CompletableFuture<String> response = error.getResponse();;
                if (response.isDone()){
                    final String res = response.join();
                    //TODO lock in if I need a step before a decision is applied to check if the decision results in a save and exit here type of deal. Probably, but I'm not sure tbh.
                    errorsByPriority.put(error.getResolutionPriority(res),Pair.of(error,res));
                    //SandboxCode code = error.handleDecision(this,c,e);
                }
            }
        }
        return buildMap(c,e,errorsByPriority);
    }

    /**
     * Constructs a map of standing changes to their associated state errors based on
     * the given timeline change, entity, and prioritized error responses.
     *
     * @param <HT> The type of the DateMutableEntity being modified by the timeline change.
     * @param <HC> The type of the TimelineContainer associated with the DateMutableEntity.
     * @param c The timeline change being processed.
     * @param e The specific entity being modified by the timeline change.
     * @param errorsByPriority A TreeMap where keys represent priority levels and values
     *                         are pairs of {@link StateError} objects and their associated
     *                         response strings, used to process errors in priority order.
     * @return A map where each key is a {@link StandingChange} representing a processed change,
     *         and the corresponding value is the associated {@link StateError}.
     */
    private <HT extends DateMutableEntity<HT,HC>,HC extends TimelineContainer<HC>> Map<StandingChange,StateError> buildMap(TimelineChange<HT> c, HT e, TreeMap<Integer,Pair<StateError,String>> errorsByPriority){
        Map<StandingChange,StateError> standingChanges = new HashMap<>();
        for (Map.Entry<Integer,Pair<StateError,String>> entry : errorsByPriority.entrySet()){
            final StateError error = entry.getValue().getLeft();
            final String response = entry.getValue().getRight();
            final SandboxCode code = error.handleDecision(this,c,e);
            LocalDate start = error.getOldChange().getDate();
            LocalDate end = error.getOldChange().getEndDate();
            if (start == null){
                start = GlobalVars.CURRENT_DATE();
            }
            if (end == null){
                end = sandboxEndDate;
            }
            if (code == SandboxCode.BRANCHING_OBJECTIVE){
                //IDK dude. Need to refresh on how branching works. Probably just make sure the results are put into dirty?
            } else if (code != SandboxCode.CONTINUE){
                status = code;
                killBox = Pair.of(start,error);
                return standingChanges;
            }
            StandingChange sc = new StandingChange(error,c,error.getOldChange(),null,start,end,response);
            standingChanges.put(sc,error);
        }
        return standingChanges;
    }
    public <HT extends DateMutableEntity<HT,HC>,HC extends TimelineContainer<HC>> void endSimulation(SandboxCode code, HT subject, LocalDate endDate, Set<StandingChange> standingChanges){
        //TODO write out later, basically parse SandboxCode, then run shutdown
        switch (code){
            case SandboxCode.END_DISCARD -> {
                dirty.clear();
                handleReturn();
                break;
            }
            case SandboxCode.END_SAVE -> {
                TimelineChange<HT> change = primary.state().change();
                buildBreadcrumb(change,endDate,standingChanges);
            }
        }
    }
    private <HT extends DateMutableEntity<HT,HC>,HC extends TimelineContainer<HC>> void buildBreadcrumb(TimelineChange<HT> change, LocalDate date, Collection<StandingChange> standingChanges){
        TimelineChange.Breadcrumb crumb = change.getBreadcrumb();
        crumb.addEndPoint(date);
        for (StandingChange sc : standingChanges){
            crumb.insertError(sc.fullKey(),sc.resolution());
        }
    }
    private void handleReturn(){
        future.complete(dirty);
    }

    private void shutdown() {

    }
    public void setEndDate(LocalDate date){
        sandboxEndDate = date;
    }
    public LocalDate getEndDate(){
        return sandboxEndDate;
    }
    public static void ImplementChanges(HashMap<DMEReference<?>,JsonObject> diff){
        for (DMEReference<?> type : diff.keySet()) {
            DMRegistry.load(type,diff.get(type));
            //TODO add save in too
        }
    }

    /**
     * A record representing a change in the state or timeline of the sandbox, encapsulating key metadata
     * about the triggering event, the specific change, and the resolution applied. This class serves as a
     * temporary container for decisions made by the user during simulations, enabling error handling,
     * resolution tracking, and smoother algorithm flow in the sandbox.
     *
     * The primary purpose of the three-key system contained within this class is to identify events and
     * changes in a granular way, allowing the sandbox to effectively manage persistent errors and resolutions.
     */
    @SuppressWarnings("UnstableApiUsage")
    public record StandingChange(long privateKey, long eventKey, long fullKey, LocalDate start, LocalDate endDate, String resolution){
        //Purpose: this class is built to act as a temporary container for EVERY decision the user makes. The three key system allows the sandbox to check if it's seen an error before and autoresolve,
        //which helps with limiting error spam and allows for a more streamlined algo flow.
        public StandingChange(StateError error, TimelineChange<?> newChange, TimelineChange<?> existingChange, Integer procedural, LocalDate startDate, LocalDate endDate, String resolution){
            this(generatePrivateKey(existingChange),generateEventKey(error),generateFullKey(error,newChange,existingChange,procedural),startDate,endDate,resolution);
        }
        /**
         * Generates a unique full key used to serialize changes into the breadcrumb for undo functionality.
         * This full key is derived from a combination of the provided new change, existing change,
         * and procedural information, making it highly specific to the context of the event.
         * It ensures persistence and reflects the specific details of the event for streamlined processing.
         *
         * @param error the StateError instance used to retrieve the old change and generate the key
         * @param newChange the new change object representing the recently introduced timeline modification
         * @param existingChange the existing change object representing the state already recorded
         * @param procedural an optional procedural integer that influences the generated key
         * @return a long value representing the unique full key for the given event
         */
        //Full key is the actual change that gets saved to the breadcrumb and is used in undo functions.
        // This hash is very event specific, as it factors in the combination of the incomingChange AND the oldChange alongside event details.
        //This is the key that the change gets serialized under in the breadcrumb, so it's arguably the only persistent part of the breadcrumb (along with the resolution)
        public static long generateFullKey(StateError error, TimelineChange<?> newChange, TimelineChange<?> existingChange, Integer procedural){
            return error.generateID(newChange,existingChange,procedural);
        }

        /**
         * Generates a private key based on the provided timeline change event.
         * The private key acts as a unique identifier and is always tied to the existing event.
         *
         * @param t the timeline change event from which the private key is derived
         * @return a long value representing the generated private key
         */
        //Private key is basically the event that triggers it. It should always be the existing event
        public static long generatePrivateKey(TimelineChange<?> t){
            return StateError.buildForTLC(t);
        }
        /**
         * Generates a unique event key that represents the procedural details of the given StateError event.
         * The event key is internally composed using a hash of the error message.
         *
         * @param t the StateError instance which provides the message to generate the event key
         * @return a long value representing the generated event key
         */
        //event key is the procedural details of the event itself, generated internally.
        public static long generateEventKey(StateError t){
            Hasher hasher = Hashing.murmur3_128().newHasher();
            hasher.putString(t.getMessage(), StandardCharsets.UTF_8);
            return hasher.hash().asLong();
        }
        public boolean isMatch(long eventKeyToCheck){
            Hasher hasher = Hashing.murmur3_128().newHasher().putLong(eventKey).putLong(privateKey);
            Hasher hasher2 = Hashing.murmur3_128().newHasher().putLong(eventKeyToCheck).putLong(privateKey);
            return hasher.hash().equals(hasher2.hash());
        }
        public long parse(){
            return parse(eventKey,privateKey);
        }
        public static long parse(long eventKey, long privateKey){
            return Hashing.murmur3_128().newHasher().putLong(eventKey).putLong(privateKey).hash().asLong();
        }
        public long hash(long eventKey){
            return parse(eventKey,privateKey);
        }
    }
}