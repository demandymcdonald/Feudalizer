package com.base;

import com.Global;
import com.base.reference.DMEReference;
import com.base.timeline.Timeline;
import com.base.timeline.state.TimelineState;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.changes.TimelineChange;
import com.google.gson.JsonObject;
import com.objects.CauseOfEnd;
import com.utilities.SuperclassSerializable;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.*;

/**
 * Represents an abstract class for date-aware mutable entities that track state changes over time.
 * This class maintains a timeline of states, which are stored as objects of the nested {@code DateState} record.
 * Each state includes metadata such as creation and end dates, associated keys for state changes, and a payload
 * representing the state value.
 *
 * @param <T> The type representing the state of the entity.
 */
public abstract class DateMutableEntity<T extends DateMutableEntity<T>> implements SuperclassSerializable {
    private final UUID id;
    private final Timeline<T> timeline;
    private final DMEReference<T> reference;
    public DateMutableEntity(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<T,?>> initialState) {
        this.id = id;
        this.reference = DMEReference.of(this.getClass(),id);
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
    // 3. linkX is built for objects to add shortcut links to to an object (for example: BookCharacter has a fleeting
    // list of the Family's it's a part of. For safety, any linked variable should be cleared on reload



    public final UUID getId() {
        return id;
    }

    public LocalDate getCreated(){
        return timeline.getStart();
    };
    public LocalDate getEnded(){
        return timeline.getEnd();
    }
    public final DMEReference<T> getReference(){
        return reference;
    }
    //use to add any shortcut/linked entries to other objects (for example, family adding a shortcut link to itself in every member)
    protected abstract void onLink();
    //Use to clear any shortcut/linked variables.
    public abstract void doDateChange();
    public void onDateChange(){
        doDateChange();
        timeline.doTimeChange(current());
    }
    public void relink(){
        onLink();
    }

    protected final LocalDate current(){
        return Global.getDate();
    }
    protected final void setCreated(LocalDate created){
        timeline.moveStart(created);
    }
    protected final void setEnded(LocalDate ended){
        timeline.moveEnd(ended);
    }
    public TimelineState<T>[] getAllStates(){
        return timeline.getStates();
    }
    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof DateMutableEntity<?> dme && this.getClass().equals(dme.getClass())) {
            return this.getId().equals(dme.getId());
        }
        return false;
    }
    public final Timeline<T> getTimeline(){
        return timeline;
    }

    public final TimelineState<T> buildBirth(DMEReference<T> dme, LocalDate date, List<ChangeSupplier<T,?>> defaults){
        List<TimelineChange<? super T>> changes = buildChangeList(date,getBirthChange(dme,date),dme,defaults);
        return new TimelineState<T>(dme.get().getTimeline(), date, date,true, changes);
    };
    public final TimelineState<T> buildDeath(DMEReference<T> dme, LocalDate date, CauseOfEnd cOd, List<ChangeSupplier<T,?>> defaults){
        List<TimelineChange<? super T>> changes = buildChangeList(date,getDeathChange(dme,date,cOd),dme,defaults);
        return new TimelineState<T>(dme.get().getTimeline(),date, date,true, changes);
    };
    public abstract TimelineChange<T> getBirthChange(DMEReference<T> dme, LocalDate date);
    public abstract TimelineChange<T> getDeathChange(DMEReference<T> dme, LocalDate date, CauseOfEnd cOd);
    public abstract CauseOfEnd defaultDeathCause();
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
