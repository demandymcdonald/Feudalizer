package com.base.timeline.sandbox.core;

import com.Global;
import com.base.DMRegistry;
import com.base.DateMutableEntity;
import com.base.timeline.Timeline;
import com.base.timeline.error.SandboxCode;
import com.base.timeline.error.StateError;
import com.base.reference.DMEReference;
import com.base.timeline.sandbox.check.SandboxFunction;
import com.base.timeline.state.TimelineState;
import com.base.timeline.change.changes.TimelineChange;
import com.google.gson.JsonObject;
import com.utilities.ThreadManager;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import static com.base.timeline.error.SandboxCode.*;

public class Sandbox<T extends DateMutableEntity<T>> {
    private CompletableFuture<Map<DMEReference<?>, JsonObject>> toReturn = new CompletableFuture<>();
    private final List<DMEReference<?>> toSave = new ArrayList<>();
    private final Map<DMEReference<?>, JsonObject> dirty = new HashMap<>();
    private HashMap<DMEReference<?>, JsonObject> Scope = new HashMap<>();

    private SandboxHandler<T> handler;
    private final Thread thread;
    private final Objective<T> objective;
    private final DMEReference<T> subject;
    private volatile LocalDate sandboxEndDate;
    private volatile SandboxCode status = SandboxCode.CONTINUE;
    private volatile Pair<LocalDate, StateError> killBox;
    private AtomicBoolean isActiveState = new AtomicBoolean(false);
    //TODO I need a way to have secondary saves pulled by default:
    // for example the parent in a deJure change also needs to be saved, even though they aren't the subject.

    //TODO ensure that the change to the target object is applied to it's state at the END of the sandbox, not the beginning.
    //TODO add special handling for TimelineMapChanges, because they're different. Namely: check if a change already exists
    // in the state, and if so, merge the two. Also: set the leapfrog date for it (using a currently unwritten TL helper
    // method probably.
    public Sandbox(Objective<T> obj, @Nullable Thread parent) {
        this(obj, obj.getEnd() == null ? obj.subject().get().getEnded() : obj.getEnd(), parent);
    }

    public Sandbox(Objective<T> obj, LocalDate endDate, @Nullable Thread parent) {
        this.objective = obj;
        this.subject = obj.subject();
        sandboxEndDate = endDate;
        thread = ThreadManager.BuildThread("sandbox_" + subject.getID(), this::main, parent);
    }

    public void startSimulation() {
        startup(handler);
    }

    public synchronized CompletableFuture<Map<DMEReference<?>, JsonObject>> getToReturn() {
        return toReturn;
    }


    private void startup(SandboxHandler<T> handler) {
        populateSandbox();
        this.handler = handler;
        //End of Parent Thread Stuff
        thread.start();
    }

    private void populateSandbox() {
        for (DMEReference<?> type : objective.change().getScope()) {
            Scope.put(type, DMRegistry.getEntity(type).serialize());
        }
    }

    private void loadSandbox() {
        Global.setSandboxHandler(handler);
        for (Map.Entry<DMEReference<?>, JsonObject> object : Scope.entrySet()) {
            DMRegistry.load(object.getKey(), object.getValue());
        }
    }

    public SandboxCode getStatus() {
        return status;
    }

    @SuppressWarnings("unchecked")
    private void runSimulation() {
        toSave.add(subject);
        final Timeline<T> timeline = subject.get().getTimeline();
        LocalDate currentDate = objective.change().getStart().minusDays(1);
        isActiveState.set(true);
        SandboxCode currentCode = SandboxCode.CONTINUE;
        while ((currentDate.isBefore(sandboxEndDate) || currentDate.isEqual(sandboxEndDate))) {
            this.status = currentCode;
            currentCode = SandboxCode.CONTINUE;
            TimelineState<T> currentState = timeline.getNextState(currentDate);
            if (currentState == null) {
                break;
            }
            for (SandboxFunction<T> check : objective.toCheck()) {
                for (TimelineChange<? super T> change : currentState.getChanges()) {
                    SandboxCode code = check.cycle(this,subject, currentState, objective.change(),change);
                    if (code.sandboxComplete() || code == RESTART_FROM_STATE) {
                        currentCode = code;
                        break;
                    } else if (code != CONTINUE_NEW_SAVE) {
                        toSave.addAll(objective.change().getNewSaves());
                    }
                }
                if (currentCode.sandboxComplete() || currentCode == RESTART_FROM_STATE) {
                    break;
                }
            }
            if (currentCode.sandboxComplete()) {
                break;
            } else if (currentCode == RESTART_FROM_STATE) {
                continue;
            }
            currentDate = currentState.getStart();
            for (SandboxFunction<T> check : objective.toCheck()) {
                check.onStep(this,subject, currentState, objective.change(),isActiveState.get());
            }
            isActiveState.set(false);
        }
        endSimulation(timeline,currentDate.minusDays(1),currentCode);
    }

    private boolean tryResolveError(TimelineChange<? super T> newChange, StateError error) {
        String resolution;
        if (error.canAutoResolve()) {
            resolution = error.getAutoResolution();
        } else {
           resolution = newChange.getResolutionCode(error);
        }
        if(resolution != null) {
            error.getResponse().complete(resolution);
            return true;
        }
        return false;
    }


    public SandboxCode handleErrors(TimelineState<T> ts, TimelineChange<? super T> newChange, List<StateError> errors) {
        List<StateError> completeErrors = new ArrayList<>();
        List<StateError> forUser = new ArrayList<>();
        for (StateError error : errors) {
            boolean complete = tryResolveError(newChange, error);
            if (complete) {
                completeErrors.add(error);
                continue;
            }
            forUser.add(error);
        }
        //Handler will not end until all errors have been resolved (or maybe a timeout?)
        handler.handleErrors(forUser);
        completeErrors.addAll(forUser);
        completeErrors.sort(Comparator.comparing(StateError::getPriority));
        int count = 0;
        SandboxCode fcode = SandboxCode.CONTINUE;
        for (StateError error : completeErrors) {
            String resolution = error.getResponse().join();
            if(resolution != null) {
                newChange.addResolution(error, resolution);
                SandboxCode code = error.resolve(this, subject, ts, newChange, resolution);
                if (code.sandboxComplete() || error.isExclusive()) {
                    return code;
                } else if (code == RESTART_FROM_STATE) {
                    return RESTART_FROM_STATE;
                }
                fcode = code;
            }
            count++;
            if (count >= completeErrors.size()) {
                newChange.advanceStage(subject,ts,error.getExistingChange(),isActiveState.get());
                break;
            }
        }
        return fcode;
    }

    protected void endSimulation(Timeline<T> t, LocalDate end, SandboxCode code){
        for (SandboxFunction<T> check : objective.toCheck()) {
            check.onComplete(this,end,code,subject,objective.change());
        }
        buildDirtyMap();
        handleReturn();
    }
    public void buildDirtyMap(){
        for(DMEReference<?> ref : toSave){
            dirty.put(ref, ref.get().serialize());
        }
    }

    public static <V> V safePull(Supplier<V> supplier, V fallback){
        V value = supplier.get();
        if (value == null){
            return fallback;
        }
        return value;
    }

    private void main(){
        loadSandbox();
        runSimulation();
        shutdown();
    }
    public void handleReturn(){
        toReturn.complete(dirty);
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
    public Map<DMEReference<?>,JsonObject> getWorkingDirty(){
        return this.dirty;
    }
    public List<DMEReference<?>> getToSave(){
        return this.toSave;
    }
    public Thread getThread(){
        return thread;
    }
    protected void setHandler(SandboxHandler<T> handler){
        this.handler = handler;
    }
    public SandboxHandler<T> getHandler(){
        return handler;
    }
    public Objective<T> getObjective(){
        return objective;
    }
    public DMEReference<T> getSubject(){
        return subject;
    }
}