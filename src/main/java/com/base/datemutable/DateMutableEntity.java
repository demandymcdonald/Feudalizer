package com.base.datemutable;

import com.Global;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.Timeline;
import com.base.datemutable.timeline.state.TimelineState;
import com.base.datemutable.timeline.change.ChangeSupplier;
import com.base.datemutable.timeline.change.TimelineChange;
import com.google.gson.JsonObject;
import com.objects.CauseOfEnd;
import com.utilities.id.Identifiable;
import com.utilities.number.DateUtilities;
import com.utilities.serialization.SuperclassSerializable;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Represents an abstract class for date-aware mutable entities that track state changes over time.
 * This class maintains a timeline of states, which are stored as objects of the nested {@code DateState} record.
 * Each state includes metadata such as creation and end dates, associated keys for state changes, and a payload
 * representing the state value.
 *
 * @param <T> The type representing the state of the entity.
 */
public abstract class DateMutableEntity<T extends DateMutableEntity<T>> implements SuperclassSerializable<DateMutableEntity<?>>, Identifiable<UUID> {
    private final UUID id;
    private final Timeline<T> timeline;
    //TODO: Add the author notes interface and container once it's ready. Low prio though, limit complexity until I get the core loop running.
    private final DMEReference<T> reference;
    private final AtomicBoolean isLoaded = new AtomicBoolean(false);
    public DateMutableEntity(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<T,?>> initialState) {
        this.id = id;
        this.reference = DMEReference.of((Class<T>) this.getClass(),id);
        this.timeline = new Timeline<>((T) this,reference,created,ended,initialState);
    }
    public DateMutableEntity(LocalDate created, LocalDate ended, List<ChangeSupplier<T,?>> initialState){
        this(UUID.randomUUID(),created,ended,initialState);
    }
    public DateMutableEntity(DMEReference<T> dme) {
        if (dme == null) throw new NullPointerException("DMEReference cannot be null");
        if (!dme.getType().equals(this.getClass())) throw new IllegalArgumentException("DMEReference: "+ dme +" must be of type " + this.getClass());
        this.id = dme.getID();
        this.timeline = new Timeline<T>(dme);
        this.reference = dme;
    }
    //Note for subclasses. To keep nomenclature simple:
    // 1. setX is the way to trigger a statechange (and sandbox),
    // it should EXCLUSIVELY create a new TLChange and pass it to the timeline with: addChange(TLChange).
    // setX Methods are used by the UI to trigger state changes and sandboxing.
    // 2. internalX should actually modify the variable on the runtime object. they should NEVER touch the timeline.
    // 3. linkX is built for objects to add shortcut links to to an object (for example: HumanCharacter has a fleeting
    // list of the FamilyGroups's it's a part of. For safety, any linked variable should be cleared on reload

    public boolean isAlive(){
        return DateUtilities.isBetween(timeline.getStart(),timeline.getEnd(),current());
    }
    public final String getDisplayID() {
        return id.toString();
    }
    public final UUID getID() {
        return id;
    }
    public final LocalDate getCreated(){
        return timeline.getStart();
    };
    public final LocalDate getEnded(){
        return timeline.getEnd();
    }
    public final DMEReference<T> getReference(){
        return reference;
    }
    //use to add any shortcut/linked entries to other objects (for example, family adding a shortcut link to itself in every member)
    protected abstract void onLink();
    //Use to clear any shortcut/linked variables.
    public abstract void doDateChange();
    public final void onDateChange(){
        isLoaded.set(false);
        doDateChange();
        timeline.doTimeChange(current());
    }
    public final void link(){
        if (isLoaded()){
            return;
        }
        onLink();
        isLoaded.set(true);
    }
    public final void forceLink(){
        if (!isLoaded()){
            link();
        }
    }
    public boolean isLoaded(){
        return isLoaded.get();
    }
    protected final LocalDate current(){
        return Global.getDate();
    }
    protected final void setCreated(LocalDate created){
        timeline.moveStart(created);
    }
    //Validation is handled for setCreated and setEnded is handled in timeline, not here.
    protected final void setEnded(LocalDate ended){
        timeline.moveEnd(ended);
    }
    public TimelineState<T>[] getAllStates(){
        return timeline.getStates();
    }
    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof DateMutableEntity<?> dme && this.getClass().equals(dme.getClass())) {
            return this.getDisplayID().equals(dme.getDisplayID());
        }
        return false;
    }
    public final Timeline<T> getTimeline(){
        return timeline;
    }
    public final TimelineState<T> getCurrentState(){
        //Intentionally left with a compile error so I don't forget to wire this once I figure out the best way to handle it.
    }
    public final List<TimelineChange<? super T>> getCurrentChanges(){
        return getCurrentState().getAllCurrentChanges(false);
    }
    public final TimelineState<T> buildBirth(DMEReference<T> dme, LocalDate date, List<ChangeSupplier<T,?>> defaults){
        List<TimelineChange<? super T>> changes = buildChangeList(date,getBirthChange(dme,date),dme,defaults);
        return new TimelineState<T>(dme.get().getTimeline(), date, date,true, changes);
    };
    public final TimelineState<T> buildDeath(DMEReference<T> dme, LocalDate date, CauseOfEnd<? super T> cOd, List<ChangeSupplier<T,?>> defaults){
        List<TimelineChange<? super T>> changes = buildChangeList(date,getDeathChange(dme,date,cOd),dme,defaults);
        return new TimelineState<T>(dme.get().getTimeline(),date, date,true, changes);
    };
    public abstract TimelineChange<T> getBirthChange(DMEReference<T> dme, LocalDate date);
    public abstract TimelineChange<T> getDeathChange(DMEReference<T> dme, LocalDate date, CauseOfEnd<? super T> cOd);
    public abstract CauseOfEnd<? super T> defaultDeathCause();
    private List<TimelineChange<? super T>> buildChangeList(LocalDate date, TimelineChange<T> change, DMEReference<T> ref, List<ChangeSupplier<T,?>> defaults){
        List<TimelineChange<? super T>> Changes = new ArrayList<>();
        Changes.add(change);
        for (ChangeSupplier<T,?> c : defaults) {
            Changes.add(c.supply(date,ref));
        }
        return Changes;
    }
    @Override
    public final JsonObject serialize() {
        return SuperclassSerializable.super.serialize();
    }
    @Override
    public final void deserialize(JsonObject data) {
        SuperclassSerializable.super.deserialize(data);
    }
    @Override
    public final void mainSave(JsonObject json) {
        json.add("timeline", timeline.toJson());
    }
    @Override
    public final void mainLoad(JsonObject json) {
        JsonObject timelineJson = json.get("timeline").getAsJsonObject();
        timeline.fromJson(timelineJson);
    }
    @Override
    public final void metadataSave(JsonObject data) {
        SuperclassSerializable.super.metadataSave(data);
        data.addProperty("id",id.toString());
    }
}
