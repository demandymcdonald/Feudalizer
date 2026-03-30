package com.base;

import com.GlobalVars;
import com.base.reference.DMEReference;
import com.base.timeline.Timeline;
import com.base.timeline.TimelineState;
import com.base.timeline.change.TimelineChange;
import com.google.common.base.Suppliers;
import com.google.gson.JsonObject;
import com.utilities.SuperclassSerializable;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;

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
    public DateMutableEntity(UUID id, LocalDate created, @Nullable LocalDate ended, List<TimelineChange<? super T>> initialState) {
        this.id = id;
        this.reference = DMEReference.of(this.getClass(),id);
        this.timeline = new Timeline<T>((T) this,reference,created,ended,initialState);
    }
    public DateMutableEntity(LocalDate created, @Nullable LocalDate ended, List<TimelineChange<? super T>> initialState) {
        this(UUID.randomUUID(), created, ended, initialState);
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
    @Override
    public final void mainSave(JsonObject json) {
        json.add("timeline", timeline.save());
    }
    @Override
    public final void mainLoad(JsonObject json) {
        JsonObject timelineJson = json.get("timeline").getAsJsonObject();
        timeline.load(timelineJson);
    }
    public LocalDate getCreated(){
        return timeline.getEarliestDate();
    };
    public LocalDate getEnded(){
        return timeline.getLatestDate();
    }
    public final DMEReference<T> getReference(){
        return reference;
    }
    //use to add any shortcut/linked entries to other objects (for example, family adding a shortcut link to itself in every member)
    public abstract void onLink();
    //Use to clear any shortcut/linked variables.
    public abstract void doDateChange();
    public void onDateChange(){
        doDateChange();
        timeline.doTimeChange((T)this,current());

    }
    public void relink(){

    }
    protected final LocalDate current(){
        return GlobalVars.getDate();
    }
    protected final void setCreated(LocalDate created){
        timeline.moveBirth((T) this,created);
    }
    protected final void setEnded(LocalDate ended){
        timeline.moveDeath((T) this,ended);
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
    public abstract <M extends AbstractMutableManager<M,T,?>> M getManager();
    public abstract ObjectType getObjectType();
}
