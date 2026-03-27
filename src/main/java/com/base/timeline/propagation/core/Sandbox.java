package com.base.timeline.propagation.core;

import com.GlobalVars;
import com.base.AbstractMutableManager;
import com.base.DMRegistry;
import com.base.DateMutableEntity;
import com.base.ObjectType;
import com.base.flags.Errors;
import com.base.flags.SandboxCode;
import com.base.flags.StateError;
import com.base.reference.DMEReference;
import com.base.timeline.TimelineChangeState;
import com.base.timeline.TimelineContainer;
import com.base.timeline.TimelineState;
import com.base.timeline.change.TimelineChange;
import com.google.common.collect.HashMultimap;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.gson.JsonObject;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;

public class Sandbox {
    private CompletableFuture<HashMap<DMEReference<?>, JsonObject>> future;
    private final HashMap<DMEReference<?>, JsonObject> dirty = new HashMap<>();
    private final HashSet<DMEReference<?>> Scope;
    private final ConcurrentLinkedDeque<StateError> ProblemQueue = new ConcurrentLinkedDeque<>();
    private final Objective primary;
    private volatile LocalDate sandboxEndDate = LocalDate.MAX;
    private volatile SandboxCode status = SandboxCode.CONTINUE;
    public Sandbox(Objective obj) {
        this.primary = obj;
        this.Scope = obj.state().change().getScope();
    }
    public void startSimulation() {
        future = new CompletableFuture<>();
        startup(new HashMap<>());
    }
    public synchronized CompletableFuture<HashMap<DMEReference<?>, JsonObject>> getFuture() {
        return future;
    }
    /**
     * Exposes the flag queue to the UI thread so it can poll for incoming StateErrors
     * and present resolution dialogs to the user.
     */
    public BlockingQueue<StateError> getQueue() {
        return queue;
    }

    private void runtime(HashMap<DMEReference<?>, JsonObject> sandbox) {
        DMRegistry.sandboxReInit();
        loadSandbox(sandbox);
        HashMap<DMEReference<?>, JsonObject> result = runSimulation();
        shutdown();
        future.complete(result);
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
    private <HT extends DateMutableEntity<HT,HC>,HC extends TimelineContainer<HC>> HashMap<DMEReference<?>, JsonObject> runSimulation() {
        HashMap<Long, StandingChange> standingChanges = new HashMap<>();
        HT host = DMRegistry.getEntity(primary.type(),primary.id());
        TimelineChange<HT> proposedChange = primary.state().change();
        LocalDate date = primary.state().start();
        while (date != null){
            GlobalVars.setCurrentDate(date);
            TimelineState<HT> state = host.getCurrentState();
            for (TimelineChange<HT> change : state.getChanges()) {
                if (change.isDeactivated()) continue;
                if (proposedChange.canNullify(change)) {
                    change.nullify(host,proposedChange);
                }
                boolean allClear = false;
                while (!allClear){
                    List<StateError> errors = proposedChange.doesConflict(change);
                    if (errors.isEmpty()){
                        allClear = true;
                        break;
                    }
                    for (StateError error : errors){

                    }
                }


            }
            date = host.getNextDate();
        }
    }
    private <HT extends DateMutableEntity<HT,HC>,HC extends TimelineContainer<HC>> Map<StandingChange,StateError> handleError(TimelineChange<HT> c, HT e, List<StateError> errors){
        Map<StandingChange,StateError> standingChanges = new HashMap<>();

        ProblemQueue.addAll(errors);
        while (!errors.isEmpty()){
            List<CompletableFuture<String>> toRemove = new ArrayList<>();
            for (StateError error : errors){
                CompletableFuture<String> response = error.getResponse();;
                if (response.isDone()){
                    //TODO lock in if I need a step before a decision is applied to check if the decision results in a save and exit here type of deal. Probably, but I'm not sure tbh.
                    SandboxCode code = error.handleDecision(this,c,e);
                    if (code != SandboxCode.CONTINUE){
                        endSimulation(code);
                        return standingChanges;
                    }
                    toRemove.add(response);
                    standingChanges.put(new StandingChange(StandingChange.generatePrivateKey(c),StandingChange.generateEventKey(error),sandboxEndDate,response.join()),error);
                }
            }
        }
        return standingChanges;
    }
    public void endSimulation(SandboxCode code){
        //TODO write out later, basically parse SandboxCode, then run shutdown

    }
    /**
     * Handles a single conflict by pushing the StateError to the UI queue and blocking
     * until the user (or autoresolve) completes the CompletableFuture with a resolution code.
     *
     * @return true if simulation should continue, false if it should cancel.
     */
    private boolean handleResolution(StateError error, DateMutableEntity<?,?> host, TimelineChangeState incomingChange, TimelineState conflictingState) {
        // Autoresolve errors skip the queue entirely
        if (isAutoResolvable(error)) {
            return autoResolve(error, host, incomingChange, conflictingState);
        }

        // Push to queue so UI thread can pick it up and show a dialog
        try {
            queue.put(error);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }

        // Block until user picks a resolution
        int choice;
        try {
            choice = error.response().get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            return false;
        }

        return applyResolution(choice, error, host, incomingChange, conflictingState);
    }

    /**
     * Checks if the error should be handled automatically without user input.
     * Currently covers TITLE_SEMANTIC_ERROR and TITLE_HOLDER_DEAD.
     */
    private boolean isAutoResolvable(StateError error) {
        return error.canAutoResolve();
    }

    /**
     * Handles autoresolvable errors. Returns true to continue simulation, false to cancel.
     * TODO: TITLE_HOLDER_DEAD should run succession planner here and swap the incoming change's target.
     */
    private boolean autoResolve(StateError error, DateMutableEntity<?> host, TimelineChangeState incomingChange, TimelineState conflictingState) {
        switch (error.message()) {
            case TITLE_SEMANTIC_ERROR -> {
                // Just clean up references silently and continue
                return true;
            }
            case TITLE_HOLDER_DEAD -> {
                // TODO: Run succession planner, find heir, redirect incomingChange to heir
                return true;
            }
            default -> {
                return true;
            }
        }
    }

    /**
     * Branches on the user's resolution choice and applies the appropriate action.
     * Resolution codes map to the options defined in StateError.Errors.
     *
     * @return true if simulation should continue, false if cancelled.
     */
    private boolean applyResolution(StateError error, DateMutableEntity<?,?> host, TimelineChangeState incomingChange, TimelineState conflictingState) {
        final int choice = error.response().join();
        switch (error.message()) {
            case DUPLICATE_STATE -> {
                switch (choice) {
                    case 0 -> { // Overwrite — carry on, the incoming change wins

                        return true;
                    }
                    case 1 -> { // Cancel
                        return false;
                    }
                    case 2 -> { // Nullify — remove the conflicting change from the state
                        incomingChange.change().doNullify(conflictingState);
                        return true;
                    }
                    case 3 -> { // AutoResolve
                        return autoResolve(error, host, incomingChange, conflictingState);
                    }
                }
            }
            case NEW_STATE_INVALIDATES_OLD_STATE, OLD_STATE_INVALIDATES_NEW_STATE -> {
                switch (choice) {
                    case 0 -> { return true; }  // Overwrite
                    case 1 -> { return true; }  // Ignore — continue without applying
                    case 2 -> {                  // End before conflict — close out the old state at the conflict date
                        // TODO: set end date on conflicting state
                        return true;
                    }
                    case 3 -> { return false; } // Cancel
                }
            }
            case OLD_TITLE_INVALIDATES_NEW_TITLE, NEW_TITLE_INVALIDATES_OLD_TITLE -> {
                switch (choice) {
                    case 0 -> { return true; }  // Overwrite
                    case 1 -> { return true; }  // Ignore
                    case 2 -> {                  // End before conflict
                        // TODO: set end date on conflicting title state
                        return true;
                    }
                    case 3 -> { return false; } // Cancel
                }
            }
            case TITLE_HOLDER_EXISTS -> {
                switch (choice) {
                    case 0 -> { return true; }  // Overwrite existing holder
                    case 1 -> {                  // End existing holder's state before conflict date
                        // TODO: end existing holder's title state
                        return true;
                    }
                    case 2 -> { return false; } // Cancel
                }
            }
            default -> { return false; }
        }
        return false;
    }

    /**
     * Serializes the host object and tracks it in the diff multimap.
     */
    private void trackMutation(DateMutableEntity<?> host) {
        ObjectType type = DMRegistry.getObjectType(host);
        JsonObject serialized = host.serialize();
        // Remove any previous version of this object from the diff before re-adding
        diff.get(type).removeIf(o -> o.get("id").getAsString().equals(host.getId().toString()));
        diff.put(type, serialized);
    }
    public LocalDate getCurrent(){
        r
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
    @SuppressWarnings("UnstableApiUsage")
    public record StandingChange(long privateKey, long eventKey, LocalDate endDate, String resolution){
        public static long generatePrivateKey(TimelineChange<?> t){
            return StateError.buildForTLC(t);
        }
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
    }
}