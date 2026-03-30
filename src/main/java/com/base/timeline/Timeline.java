package com.base.timeline;

import com.GlobalVars;
import com.base.AbstractMutableManager;
import com.base.DMRegistry;
import com.base.DateMutableEntity;
import com.base.ObjectType;
import com.base.reference.DMEReference;
import com.base.timeline.change.BoundaryChange;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.sandbox.core.Objective;
import com.base.timeline.sandbox.core.SandboxHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.*;

public class Timeline<T extends DateMutableEntity<T>> {
    private final TreeMap<LocalDate,TimelineState<T>> timeline = new TreeMap<>();
    //TODO Caching for performance?
    public boolean isLoaded = false;
    private final DMEReference<T> owner;
    public Timeline(ObjectType t, UUID id, LocalDate start, @Nullable LocalDate end, List<TimelineChange<? super T>> initialState) {
        AbstractMutableManager<T> manager = DMRegistry.getManager(t);
        owner = DMEReference.of(t,id);
        timeline.put(start, manager.buildBirth(owner,start, initialState));
        if (end == null){
            timeline.put(end, manager.buildDeath(owner, GlobalVars.MAX_DATE,initialState));
        } else {
            timeline.put(end, manager.buildDeath(owner,end,initialState));
        }
        isLoaded = true;
    }

    public Timeline(DMEReference<T> dme){
        //FOR LOADING ONLY
        owner = dme;
    }
//    public boolean safeInsertState(DateMutableEntity<T> entity, TimelineState<T> state, @Nullable TimelineChange<T> change){
//        TimelineState<T> existing = timeline.floorEntry(state.start()).getValue();
//        if(existing == null){
//            insertState(state);
//        } else {
//            TimelineState<T> newExisting = resolveExistingState(existing,state);
//            TimelineState<T> nextState = timeline.higherEntry(state.start()).getValue();
//            if (nextState != null && change != null){
//                //TODO propagation sandbox here
//                LocalDate end = getTimelineMax();
//                TimelineChangeState<T> changeState = new TimelineChangeState<>(state.start(), Optional.of(end), change);
//                Sandbox s = new Sandbox(new Objective(entity.getObjectType(),entity.getId(),changeState));
//                CompletableFuture<HashMap<DMEReference<?>, JsonObject>> future = s.getFuture();
//                s.startSimulation();
//            } else {
//                removeState(existing);
//                insertState(newExisting);
//                insertState(state);
//            }
//        }
//        //TODO REDO Once Sandbox is working!
//    }
    public void unsafeInsertState(TimelineState<T> state){
        insertState(state);
    }
    public LocalDate getTimelineMin(){
        return timeline.firstKey();
    }
    public LocalDate getTimelineMax(){
        TimelineState<T> last = timeline.lastEntry().getValue();
        return last.getEnd();
    }
    //Note: Sandbox methods should never be used outside of a sandbox's worker thread! Probably wouldn't break anything, but it's not built for main thread use!

    private void insertState(TimelineState<T> state){
        timeline.put(state.getStart(),state);
    }
    public void addChange(TimelineChange<T> change){
        //This is the safe way to insert a change using propagation. It should be used by all runtime setters.
        getOrMakeState(change.getStart()); //We just need a state at the exact start date.
        SandboxHandler.SandboxApplyChange(new Objective<>(owner,change),change.getStart(),null);
    }
    public boolean isEmpty(){
        return timeline.isEmpty();
    }
    private void removeState(TimelineState<T> state){
        timeline.remove(state.getStart());
    }
    private void removeState(LocalDate start){
        timeline.remove(start);
    }

    public LocalDate getEarliestDate(){
        return timeline.firstKey();
    }
    public LocalDate getLatestDate(){
        return timeline.lastKey();
    }
    public boolean isLast(LocalDate date){
        return timeline.lastEntry().getValue().getStart().isBefore(date);
    }
    public void moveBirth(T ref, LocalDate date){
        LocalDate oldBirthDate = getEarliestDate();
        final AbstractMutableManager<T> template = DMRegistry.getManager(ref.getObjectType());

        if (oldBirthDate.isAfter(date)){
            //TODO investigate if succession planning needs to be run on the parents IF they've died in this case?
            TimelineState<T> state = getStateAt(oldBirthDate);
            final List<TimelineChange<T>> d= TimelineHelper.getChangesWhere(state.getAllChanges(), (c) -> {
                return !(c instanceof BoundaryChange<?,?>);
            });
            timeline.remove(oldBirthDate);
            timeline.put(date, template.buildBirth(owner,date,d));
        } else {
            TimelineChange<T> bt = template.getBirthChange(owner,date);
            SandboxHandler.SandboxApplyChange(new Objective<>(owner,bt),oldBirthDate,null);
            //TODO Sandbox out moving the birth later
        }
    }
    public void moveDeath(T ref, LocalDate date){
        LocalDate oldDeathDate = getLatestDate();
        final AbstractMutableManager<T> template = DMRegistry.getManager(ref.getObjectType());

        if (oldDeathDate.isBefore(date)){
            //TODO investigate if SuccessionPlanner needs to be rerun in general?
            TimelineState<T> state = getStateAt(oldDeathDate);
            final List<TimelineChange<T>> d= TimelineHelper.getChangesWhere(state.getAllChanges(), (c) -> {
                return !(c instanceof BoundaryChange<?,?>);
            });
            timeline.remove(oldDeathDate);
            timeline.put(date, template.buildDeath(owner,date,d));
        } else {
            TimelineChange<T> bt = template.getDeathChange(owner,date);
            SandboxHandler.SandboxApplyChange(new Objective<>(owner,oldDeathDate,bt),date,null);
        }
    }


    public boolean isLoaded() {
        return isLoaded;
    }

    public TimelineState<T> getStateNullable(LocalDate date){
        return timeline.floorEntry(date).getValue();
    }
    public TimelineState<T> getNextState(LocalDate date){
        return timeline.higherEntry(date).getValue();
    }
    public TimelineState<T> getStateAt(LocalDate date){
        TimelineState<T> state = timeline.floorEntry(date).getValue();
        if (state == null){
            throw new IllegalArgumentException("No state found at " + date);
        }
        return state;
    }
    public TimelineState<T> getStateBefore(LocalDate date){
        TimelineState<T> state = timeline.floorEntry(date).getValue();
        date = state.getStart().minusDays(1);
        return timeline.floorEntry(date).getValue();
    }
    public TimelineState<T> getOrMakeState(LocalDate d){
        TimelineState<T> state = timeline.get(d);
        if (state == null){
            return makeNewState(d);
        }
        return state;
    }
    public TimelineState<T>[] getStates(){
        return timeline.values().toArray(TimelineState[]::new);
    }
    public TimelineState<T> getLastState(){
        return timeline.lastEntry().getValue();
    }
    public JsonObject save(){
        JsonObject o = new JsonObject();
        //Seems wasteful to nest the array in the object, but I'm future proofing here incase TL gets some more variables :)
        JsonArray states = new JsonArray();
        for (Map.Entry<LocalDate, TimelineState<T>> entry : timeline.entrySet()) {
            states.add(entry.getValue().serialize());
        }
        o.add("states",states);
        return o;
    }
    public void load(JsonObject o){
        JsonArray states = o.getAsJsonArray("states");
        for (int i = 0; i < states.size(); i++) {
            TimelineState<T> state = TimelineState.deserialize(states.get(i).getAsJsonObject());
            timeline.put(state.getStart(),state);
        }
    }
    public TimelineState<T> makeNewState(LocalDate start){
        TimelineState<T> before = timeline.floorEntry(start).getValue();
        TimelineState<T> after = timeline.ceilingEntry(start).getValue();
        if (before.getStart().equals(start)){
            return before;
        } else if(after.getStart().equals(start)) {
            return after;
        }
        if (before.isDuring(start)){
            before.setEnd(start.minusDays(1));
        }
        HashMap<Long,LocalDate> breadcrumbs = TimelineHelper.extendTrail(this,before,start);
        TimelineState<T> newState = new TimelineState<>(owner,start,after.getStart().minusDays(1),false,breadcrumbs,new HashMap<>());
        timeline.put(start,newState);
        return newState;
    }
    public void replaceTimeline(JsonObject o){
        isLoaded = false;
        timeline.clear();
        load(o);
        isLoaded = true;
    }
}
