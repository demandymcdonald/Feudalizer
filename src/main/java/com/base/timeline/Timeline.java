package com.base.timeline;

import com.base.DMRegistry;
import com.base.DateMutableEntity;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.propagation.core.Objective;
import com.base.timeline.propagation.core.Sandbox;

import java.time.LocalDate;
import java.util.Optional;
import java.util.TreeMap;

public class Timeline<T extends DateMutableEntity<T,C>,C extends TimelineContainer<C>> {
    private final TreeMap<LocalDate,TimelineState<T,C>> timeline = new TreeMap<>();
    //TODO Caching for performance?
    private final C template;


    public Timeline(C template) {
        this.template = template;
    }
    public boolean safeInsertState(DateMutableEntity<T,C> entity, TimelineState<T,C> state){
        TimelineState<T,C> existing = timeline.floorEntry(state.start()).getValue();
        if(existing == null){
            insertState(state);
        } else {
            TimelineState<T,C> newExisting = resolveExistingState(existing,state);
            TimelineState<T,C> nextState = timeline.higherEntry(state.start()).getValue();
            if (nextState != null){
                //TODO propagation sandbox here
                for (TimelineChange<T,C> change : nextState.changeLog()) {
                    Sandbox prop = new Sandbox(new Objective(DMRegistry.getObjectType(entity),entity.getId(),new))
                }

            }
            removeState(existing);
            insertState(newExisting);
            insertState(state);
        }
    }
    public void unsafeInsertState(TimelineState state){
        insertState(state);
    }

    //Note: Sandbox methods should never be used outside of a sandbox's worker thread! Probably wouldn't break anything, but it's not built for main thread use!

    private void insertState(TimelineState<T,C> state){
        timeline.put(state.start(),state);
    }
    public TimelineState<T,C> getState(LocalDate date){
        return timeline.floorEntry(date).getValue();
    }
    public TimelineState<T,C> getNextState(LocalDate date){
        return timeline.higherEntry(date).getValue();
    }
    public C getContainer(LocalDate date){
        TimelineState<T,C> state = getState(date);
        if(state == null) return null;
        return template.getDeserialized(state.payload());
    }
    public C getContainer(TimelineState<T,C> state){
        return template.getDeserialized(state.payload());
    }
    public boolean isEmpty(){
        return timeline.isEmpty();
    }
    private void removeState(TimelineState<T,C> state){
        timeline.remove(state.start());
    }
    private TimelineState<T,C> resolveExistingState(TimelineState<T,C> existingState, TimelineState<T,C> newState){
        LocalDate end = newState.start();
        return new TimelineState<T,C>(existingState.start(), Optional.of(end), existingState.payload(), existingState.changeLog());
    }
    public TimelineState<T,C>[] getStates(){
        return timeline.values().toArray(TimelineState[]::new);
    }
    public boolean isLast(LocalDate date){
        return timeline.lastEntry().getValue().start().isBefore(date);
    }
    public TimelineState<T,C> getLastState(){
        return timeline.lastEntry().getValue();
    }
}
