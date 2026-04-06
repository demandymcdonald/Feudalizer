package com.base.timeline;

import com.Global;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeID;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.error.SandboxCode;
import com.base.timeline.sandbox.function.SandboxFunctions;
import com.base.timeline.sandbox.core.Objective;
import com.base.timeline.sandbox.core.SandboxHandler;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;

public class Timeline<T extends DateMutableEntity<T>> extends TimelineObject<T> {
    private final TreeMap<LocalDate, TimelineState<T>> timeline = new TreeMap<>();
    //TODO Caching for performance?
    public boolean isLoaded = false;
    public Timeline(T o, DMEReference<T> owner, LocalDate start, @Nullable LocalDate end, List<ChangeSupplier<T,?>> initialState) {
        super(owner);
        //AbstractMutableManager<?,T,?> manager = DMRegistry.getManager(o.getClass());
        timeline.put(start, o.buildBirth(owner,start, initialState));
        if (end == null){
            timeline.put(end, o.buildDeath(owner, Global.MAX_DATE,o.defaultDeathCause(),initialState));
        } else {
            timeline.put(end, o.buildDeath(owner,end,o.defaultDeathCause(),initialState));
        }
        isLoaded = true;
    }

    public Timeline(DMEReference<T> dme){
        super(dme);
    }


    public LocalDate getTimelineMin(){
        return timeline.firstKey();
    }
    public LocalDate getTimelineMax(){
        TimelineState<T> last = timeline.lastEntry().getValue();
        return last.getEnd();
    }
    //Note: Sandbox methods should never be used outside of a sandbox's worker thread! Probably wouldn't break anything, but it's not built for main thread use!
    public void addChange(TimelineChange<T> change){
        //This is the safe way to insert a change using propagation. It should be used by all runtime setters.
        getOrMakeState(change.getStart()); //We just need a state at the exact start date.
        Consumer<SandboxCode> afterChange = (code) -> {
            TimelineState<T> state = getStateAt(change.getStart());
            if (state.isEmpty()){
                removeState(change.getStart());
                TimelineState<T> before = getStateBefore(state.getStart());
                TimelineState<T> after = getStateAfter(state.getStart());
                if (before != null && after != null){
                    before.setEnd(after.getStart().minusDays(1));
                }
            }
        };
        SandboxHandler.StartSandbox(new Objective<>(owner,Global.TimeDirection.FORWARD,change,
                new SandboxFunctions.CanAddChange<>()),getEnd(),null,afterChange);
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
    @Override
    public LocalDate getStart(){
        return timeline.firstKey();
    }
    @Override
    public LocalDate getEnd(){
        return timeline.lastKey();
    }

    public void moveStart(LocalDate date){
        final TimelineState<T> startState = getStateAt(getStart());
        moveState(startState,date,null);
    }
    public void moveEnd(LocalDate date){
        final TimelineState<T> endState = getStateAt(getEnd());
        moveState(endState,null,date);
    }


    public boolean isLoaded() {
        return isLoaded;
    }
    public void doTimeChange(LocalDate date){
        final TimelineState<T> t = getStateAt(date);
        for(TimelineChange<? super T> tc : t.getAllChanges(false)){
            tc.apply(getOwner(),t);
        }
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
    public TimelineState<T> getStateAtExact(LocalDate date, boolean throwIfNotFound){
        TimelineState<T> state = timeline.get(date);
        if (state == null && throwIfNotFound){
            throw new IllegalArgumentException("No state found at " + date);
        }
        return state;
    }
    public TimelineState<T> getStateBefore(LocalDate date){
        TimelineState<T> state = timeline.floorEntry(date.minusDays(1)).getValue();
        date = state.getStart().minusDays(1);
        return timeline.floorEntry(date).getValue();
    }
    public TimelineState<T> getStateAfter(LocalDate date){
        TimelineState<T> state = timeline.ceilingEntry(date.plusDays(1)).getValue();
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

    public TimelineState<T> makeNewState(LocalDate start){
        TimelineState<T> before = timeline.floorEntry(start.minusDays(1)).getValue();
        TimelineState<T> after = timeline.ceilingEntry(start.plusDays(1)).getValue();
        if (before.getStart().equals(start)){
            return before;
        } else if(after.getStart().equals(start)) {
            return after;
        }
        if (before.isDuring(start)){
            before.setEnd(start.minusDays(1));
        }
        TimelineState<T> newState = new TimelineState<>(this,start,after.getStart().minusDays(1),false,before.buildFullBreadcrumbList(),new HashMap<>());
        timeline.put(start,newState);
        return newState;
    }

    public boolean moveState(TimelineState<T> state, @Nullable LocalDate newStart, @Nullable LocalDate newEnd){
        boolean changeStart = true;
        boolean changeEnd = true;
        if (newStart == null){
            newStart = state.getStart();
            changeStart = false;
        }
        if (newEnd == null){
            newEnd = state.getEnd();
            changeEnd = false;
        }
        if (!newStart.isBefore(newEnd)){
            logger.error("Cannot move state to a start date that is after the end date! Start: {}, End:{}, State: {}",newStart,newEnd,state);
            return false;
        }
        TimelineState<T> atPlanned = timeline.get(newStart);
        if (atPlanned != null && !atPlanned.equals(state)){
            logger.error("Cannot move state to a start date that is already occupied! Start: {}, End:{}, State: {}",newStart,newEnd,state);
            return false;
        }
        TimelineState<T> beforeEnd = timeline.floorEntry(newEnd.minusDays(1)).getValue();
        TimelineState<T> afterStart = timeline.ceilingEntry(newStart.plusDays(1)).getValue();
        if (afterStart != beforeEnd){
            logger.error("Cannot move state to span over multiple states! Start: {}, End:{}, State: {}",newStart,newEnd,state);
            return false;
        }
        TimelineState<T> afterEnd = timeline.ceilingEntry(newEnd.plusDays(1)).getValue();
        TimelineState<T> beforeStart = timeline.floorEntry(newStart.minusDays(1)).getValue();
        if (changeStart){
            timeline.remove(state.getStart());
            if (beforeStart != null){
                beforeStart.setEnd(newStart.minusDays(1));
            }
            state.setStart(newStart);
            timeline.put(newStart,state);
        }
        if (changeEnd){
            if (afterEnd != null){
                afterEnd.setStart(newEnd.plusDays(1));
            }
            state.setEnd(newEnd);
        }
        return true;
    }
    public void replaceTimeline(JsonObject o){
        isLoaded = false;
        timeline.clear();
        fromJson(o);
        isLoaded = true;
    }
    //==== Shortcut Methods ====
    public final <TC extends TimelineChange<? super T>> TC followBreadcrumb(ChangeID id){
        return followBreadcrumb(this,id);
    }
    public final <TC extends TimelineChange<? super T>> List<TC> findChangeByClassID(LocalDate starting, Global.TimeDirection direction, String classType,
                                                                     final boolean includeDeactivated) {
        return findChangeByClassID(this, starting,direction,includeDeactivated,ChangeID.buildChangeClassID(classType));
    }
    public final <TC extends TimelineChange<? super T>> List<TC> findChangeByClassID(LocalDate starting, Global.TimeDirection direction, TC example,
                                                                                     final boolean includeDeactivated) {
        return findChangeByClassID(this,starting,direction,includeDeactivated,ChangeID.buildChangeClassID(example.getClass().getName()));
    }
    //==== Serializers ====

    public JsonObject toJson(){
        JsonObject o = new JsonObject();
        //Seems wasteful to nest the array in the object, but I'm future proofing here incase TL gets some more variables :)
        JsonArray states = new JsonArray();
        for (Map.Entry<LocalDate, TimelineState<T>> entry : timeline.entrySet()) {
            states.add(entry.getValue().serialize());
        }
        o.add("states",states);
        return o;
    }
    public void fromJson(JsonObject o){
        JsonArray states = o.getAsJsonArray("states");
        for (int i = 0; i < states.size(); i++) {
            TimelineState<T> state = TimelineState.deserialize(this,states.get(i).getAsJsonObject());
            timeline.put(state.getStart(),state);
        }
    }

}
