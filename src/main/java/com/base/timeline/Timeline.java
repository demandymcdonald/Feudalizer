package com.base.timeline;

import com.base.DMRegistry;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.propagation.core.Objective;
import com.base.timeline.propagation.core.Sandbox;
import com.google.gson.JsonObject;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

public class Timeline<T extends DateMutableEntity<T,C>,C extends TimelineContainer<C>> {
    private final TreeMap<LocalDate,TimelineState<T>> timeline = new TreeMap<>();
    //TODO Caching for performance?
    private final C template;


    public Timeline(C template) {
        this.template = template;
    }
    public boolean safeInsertState(DateMutableEntity<T,C> entity, TimelineState<T> state, @Nullable TimelineChange<T> change){
        TimelineState<T> existing = timeline.floorEntry(state.start()).getValue();
        if(existing == null){
            insertState(state);
        } else {
            TimelineState<T> newExisting = resolveExistingState(existing,state);
            TimelineState<T> nextState = timeline.higherEntry(state.start()).getValue();
            if (nextState != null && change != null){
                //TODO propagation sandbox here
                LocalDate end = getTimelineMax();
                TimelineChangeState<T> changeState = new TimelineChangeState<>(state.start(), Optional.of(end), change);
                Sandbox s = new Sandbox(new Objective(entity.getObjectType(),entity.getId(),changeState));
                s.startSimulation();
                CompletableFuture<HashMap<DMEReference<?>, JsonObject>> future = s.getFuture();
                s.startSimulation();


            } else {
                removeState(existing);
                insertState(newExisting);
                insertState(state);
            }
        }
    }
    public void unsafeInsertState(TimelineState<T> state){
        insertState(state);
    }
    public LocalDate getTimelineMin(){
        timeline.firstKey();
    }
    public LocalDate getTimelineMax(){
        TimelineState<T> last = timeline.lastEntry().getValue();
        return last.end().orElse(last.start().plus(1, ChronoUnit.DAYS));
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
