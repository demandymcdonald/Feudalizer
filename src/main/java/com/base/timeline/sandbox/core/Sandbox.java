package com.base.timeline.sandbox.core;

import com.Global;
import com.base.DMRegistry;
import com.base.DateMutableEntity;
import com.base.timeline.flags.SandboxCode;
import com.base.timeline.flags.StateError;
import com.base.reference.DMEReference;
import com.base.timeline.TimelineContainer;
import com.base.timeline.TimelineState;
import com.base.timeline.change.TimelineChange;
import com.utilities.DateUtilities;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.gson.JsonObject;
import com.utilities.ThreadManager;
import org.apache.commons.lang3.tuple.Pair;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;

public class Sandbox<T extends DateMutableEntity<T>> {
    private CompletableFuture<HashMap<DMEReference<?>, JsonObject>> future  = new CompletableFuture<>();
    private final HashMap<DMEReference<?>, JsonObject> dirty = new HashMap<>();
    private HashMap<DMEReference<?>, JsonObject> Scope = new HashMap<>();
    private final ConcurrentLinkedDeque<StateError> ProblemQueue = new ConcurrentLinkedDeque<>();


    private SandboxHandler<T> handler;
    private final Thread thread;
    private final Objective<T> objective;
    private final DMEReference<T> subject;
    private volatile LocalDate sandboxEndDate;
    private volatile SandboxCode status = SandboxCode.CONTINUE;
    private volatile Pair<LocalDate,StateError> killBox;
    //TODO I need a way to have secondary saves pulled by default:
    // for example the parent in a deJure change also needs to be saved, even though they aren't the subject.

    //TODO ensure that the change to the target object is applied to it's state at the END of the sandbox, not the beginning.
    //TODO add special handling for TimelineMapChanges, because they're different. Namely: check if a change already exists
    // in the state, and if so, merge the two. Also: set the leapfrog date for it (using a currently unwritten TL helper
    // method probably.
    public Sandbox(Objective<T> obj) {
        this.objective = obj;
        this.subject = obj.subject();
        sandboxEndDate = obj.getEnd() == null ? obj.subject().get().getEnded() : obj.getEnd();
        if (sandboxEndDate == null) {
            sandboxEndDate = LocalDate.MAX;
        }
        thread = ThreadManager.buildThread("Sandbox-" + subject.getID(), this::main);
    }
    public Sandbox(Objective<T> obj, LocalDate endDate) {
        this.objective = obj;
        this.subject = obj.subject();
        sandboxEndDate = endDate;
        thread = ThreadManager.buildThread("Sandbox-" + subject.getID(), this::main);
    }
    public void startSimulation() {
        startup();
    }
    public synchronized CompletableFuture<HashMap<DMEReference<?>, JsonObject>> getFuture() {
        return future;
    }


    private void startup(SandboxHandler<T> handler) {
        populateSandbox();
        this.handler = handler;
        //End of Parent Thread Stuff
        thread.start();
    }

    private void populateSandbox() {
        for (DMEReference<?> type : objective.change().getScope()) {
            Scope.put(type,DMRegistry.getEntityData(type.getType(),type.getID()));
        }
    }
    private void loadSandbox() {
        for (Map.Entry<DMEReference<?>,JsonObject> object : Scope.entrySet()) {
            DMRegistry.load(object.getKey(),object.getValue());
        }
    }
    public SandboxCode getStatus() {
        return status;
    }
    @SuppressWarnings("unchecked")
    private  void runSimulation() {
        HashSet<StandingChange> standingChanges = new HashSet<>();
        T host = DMRegistry.getEntity(objective.type(), objective.id());
        TimelineChange<T> proposedChange = objective.state().change();
        LocalDate date = objective.state().start();
        while (true){
            Global.setCurrentDate(date);
            TimelineState<T> state = host.getCurrentState();
            for (TimelineChange<T> change : state.getChanges()) {
                List<StandingChange> localSC = new ArrayList<>();
                if (change.isDeactivated()) continue;
                if (proposedChange.canNullify(change)) {
                    change.nullify(host,proposedChange);
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
            LocalDate nextDate = host.getNextDate();
            if (nextDate == null || DateUtilities.floor(nextDate,sandboxEndDate).equals(sandboxEndDate)){
                break;
            } else {
                date = nextDate;
            }
        }
        endSimulation(SandboxCode.END_SAVE,host,DateUtilities.ceiling(date,sandboxEndDate),standingChanges);
    }
    private void main(){
        loadSandbox();
        runSimulation();
        shutdown();
    }
    /**
     * Handles errors related to a given timeline change involving a specific entity and resolves them
     * based on provided resolution priorities and responses.
     *
     * @param <T> The type of the DateMutableEntity being modified by the timeline change.
     * @param <HC> The type of the TimelineContainer associated with the DateMutableEntity.
     * @param c The timeline change being processed.
     * @param e The specific entity being modified by the timeline change.
     * @param errors A list of {@link StateError} objects representing the initial errors to be handled.
     * @param autoResolves A map of {@link StateError} objects to their corresponding resolution values,
     *                     used for prioritizing and resolving errors automatically.
     * @return A map where each key is a {@link StandingChange} representing a processed change,
     *         and the corresponding value is the associated {@link StateError}.
     */
    private <HT extends DateMutableEntity<HT>,HC extends TimelineContainer<HC>> Map<StandingChange,StateError> handleError(TimelineChange<HT> c, HT e, List<StateError> errors, HashMap<StateError,String> autoResolves){
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
    private <HT extends DateMutableEntity<HT>,HC extends TimelineContainer<HC>> Map<StandingChange,StateError> buildMap(TimelineChange<HT> c, HT e, TreeMap<Integer,Pair<StateError,String>> errorsByPriority){
        Map<StandingChange,StateError> standingChanges = new HashMap<>();
        for (Map.Entry<Integer,Pair<StateError,String>> entry : errorsByPriority.entrySet()){
            final StateError error = entry.getValue().getLeft();
            final String response = entry.getValue().getRight();
            final SandboxCode code = error.handleDecision(this,c,e);
            LocalDate start = error.getOldChange().getStart();
            LocalDate end = error.getOldChange().getEnd();
            if (start == null){
                start = Global.CURRENT_DATE();
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
    public <HT extends DateMutableEntity<HT>,HC extends TimelineContainer<HC>> void endSimulation(SandboxCode code, HT subject, LocalDate endDate, Set<StandingChange> standingChanges){
        //TODO write out later, basically parse SandboxCode, then run shutdown
        switch (code){
            case SandboxCode.END_DISCARD -> {
                dirty.clear();
                handleReturn();
                break;
            }
            case SandboxCode.END_SAVE -> {
                TimelineChange<HT> change = objective.state().change();
                buildBreadcrumb(change,endDate,standingChanges);
            }
        }
    }
    private <HT extends DateMutableEntity<HT>,HC extends TimelineContainer<HC>> void buildBreadcrumb(TimelineChange<HT> change, LocalDate date, Collection<StandingChange> standingChanges){
        TimelineChange.SandboxBreadcrumb crumb = change.getBreadcrumb();
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
    public Thread getThread(){
        return thread;
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