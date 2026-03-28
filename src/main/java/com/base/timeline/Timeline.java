package com.base.timeline;

import com.base.DMRegistry;
import com.base.DateMutableEntity;
import com.base.ObjectType;
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
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class Timeline<T extends DateMutableEntity<T,C>,C extends TimelineContainer<C,T>> {
    private final TreeMap<LocalDate,TimelineState<T>> timeline = new TreeMap<>();
    //TODO Caching for performance?
    private final C template;
    public boolean isLoaded = false;

    public Timeline(C template, ObjectType t, UUID id, LocalDate start, LocalDate end) {
        this.template = template;
        timeline.put(start, template.buildBirth(new DMEReference<>(t,id),start,new JsonObject()));
        timeline.put(end, template.buildDeath(new DMEReference<>(t,id),end,new JsonObject()));
        isLoaded = true;
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
        return timeline.firstKey();
    }
    public LocalDate getTimelineMax(){
        TimelineState<T> last = timeline.lastEntry().getValue();
        return last.end().orElse(last.start().plus(1, ChronoUnit.DAYS));
    }
    //Note: Sandbox methods should never be used outside of a sandbox's worker thread! Probably wouldn't break anything, but it's not built for main thread use!

    private void insertState(TimelineState<T> state){
        timeline.put(state.start(),state);
    }
    public TimelineState<T> getState(LocalDate date){
        return timeline.floorEntry(date).getValue();
    }
    public TimelineState<T> getNextState(LocalDate date){
        return timeline.higherEntry(date).getValue();
    }
    public C getContainer(LocalDate date){
        TimelineState<T> state = getState(date);
        if(state == null) return null;
        return template.getDeserialized(state.payload());
    }
    public C getContainer(TimelineState<T> state){
        return template.getDeserialized(state.payload());
    }
    public boolean isEmpty(){
        return timeline.isEmpty();
    }
    private void removeState(TimelineState<T> state){
        timeline.remove(state.start());
    }
    private TimelineState<T> resolveExistingState(TimelineState<T> existingState, TimelineState<T> newState){
        LocalDate end = newState.start();
        return new TimelineState<T>(existingState.start(), Optional.of(end), existingState.payload(), existingState.changeLog());
    }
    public TimelineState<T>[] getStates(){
        return timeline.values().toArray(TimelineState[]::new);
    }
    public boolean isLast(LocalDate date){
        return timeline.lastEntry().getValue().start().isBefore(date);
    }
    public TimelineState<T> getLastState(){
        return timeline.lastEntry().getValue();
    }
    public LocalDate getEarliest(){
        return timeline.firstKey();
    }
    public LocalDate getLatest(){
        return timeline.lastKey();
    }
    public void onInit(ObjectType t, UUID id, TimelineState<T> defaultState){
        JsonObject de = defaultState.serialize();
        timeline.put(getTimelineMin(), template.buildBirth(new DMEReference<>(t,id),getTimelineMin(),defaultState.serialize()));
        timeline.put(getTimelineMax(), template.buildDeath(new DMEReference<>(t,id),getTimelineMax(),defaultState.serialize()));
        isLoaded = true;
    }
    public void moveBirth(T ref, LocalDate date){
        LocalDate birth = getEarliest();
        if (birth.isAfter(date)){
            TimelineState<T> state = timeline.floorEntry(date).getValue();
            timeline.remove(birth);
            timeline.put(date, template.buildBirth(DMEReference.of(ref),date,state.payload()));
        } else {
            //TODO Sandbox out moving the birth later
        }
    }
    public void moveDeath(T ref, LocalDate date){
        LocalDate death = getLatest();
        if (death.isBefore(date)){
            TimelineState<T> state = timeline.floorEntry(date).getValue();
            timeline.remove(death);
            timeline.put(date, template.buildDeath(DMEReference.of(ref),date,state.payload()));
        } else {
            //TODO Sandbox out moving the birth earlier
        }
    }
    public boolean isLoaded() {
        return isLoaded;
    }
}
