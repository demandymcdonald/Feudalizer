package com.base;

import com.base.reference.DMEReference;
import com.base.timeline.Timeline;
import com.base.timeline.TimelineState;
import com.base.timeline.change.TimelineChange;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
    public DateMutableEntity(UUID id, LocalDate created, @Nullable LocalDate ended, List<TimelineChange<? super T>> initialState) {
        this.id = id;
        this.timeline = new Timeline<T>(getObjectType(),id,created,ended,initialState);
        DMRegistry.registerDateMutable(this);
    }
    public DateMutableEntity(LocalDate created, @Nullable LocalDate ended, List<TimelineChange<? super T>> initialState) {
        this(UUID.randomUUID(), created, ended, initialState);
    }
    public DateMutableEntity(DMEReference<T> dme) {
        if (dme.getType() != this.getObjectType()){
            throw new IllegalArgumentException("Entity type mismatch between DME and class: " + dme.getType() + " vs " + this.getObjectType());
        }
        this.id = dme.getID();
        this.timeline = new Timeline<T>(dme);
        DMRegistry.registerDateMutable(this);
    }
    //Note for subclasses. To keep nomenclature simple:
    // 1. setX is the way to trigger a statechange (and sandbox),
    // it should EXCLUSIVELY create a new TLChange and pass it to the timeline with: addChange(TLChange).
    // setX Methods are used by the UI to trigger state changes and sandboxing.
    // 2. internalX should actually modify the variable on the runtime object. they should NEVER touch the timeline.
    // 3. linkX is built for objects to add shortcut links to to an object (for example: BookCharacter has a fleeting
    // list of the Family's it's a part of. For safety, any linked variable should be cleared on reload



    public UUID getId() {
        return id;
    }
    @Override
    public final void saveMain(JsonObject json) {
        json.add("timeline", timeline.save());
    }
    @Override
    public final void loadMain(JsonObject json) {
        JsonObject timelineJson = json.get("timeline").getAsJsonObject();
        timeline.load(timelineJson);
    }
    public LocalDate getCreated(){
        return timeline.getEarliestDate();
    };
    public LocalDate getEnded(){
        return timeline.getLatestDate();
    }
    //use to add any shortcut/linked entries to other objects (for example, family adding a shortcut link to itself in every member)
    public abstract void onLink();
    //Use to clear any shortcut/linked variables.
    public abstract void onStateChange();

    public static <R extends DateMutableEntity<R>,B extends Collection<R>> List<UUID> convert (B b){
        List<UUID> result = new ArrayList<>();
        for (R r : b){
            result.add(r.getId());
        }
        return result;
    }
    public static JsonArray buildJson(List<UUID> ids){
        JsonArray json = new JsonArray();
        for (UUID id : ids){
            json.add(id.toString());
        }
        return json;
    }
    @SafeVarargs
    public static <R extends DateMutableEntity<R>> JsonArray buildJson(R... ent){
        JsonArray json = new JsonArray();
        for (R r : ent){
            JsonObject obj = new JsonObject();
            obj.addProperty("type",r.getClass().getSimpleName());
            obj.addProperty("id", r.getId().toString());
            json.add(obj);
        }
        return buildJson(convert(List.of(ent)));
    }

    public static List<UUID> buildUUID (JsonArray json){
        List<UUID> result = new ArrayList<>();
        for (int i = 0; i < json.size(); i++){
            result.add(UUID.fromString(json.get(i).getAsString()));
        }
        return result;
    }
    //TODO: Get proper manager from class type.
    public static Map<UUID,ObjectType> quickBuildID(DateMutableEntity<?>... entities){
        Map<UUID,ObjectType> result = new HashMap<>();
        for (DateMutableEntity<?> e : entities){
            result.put(e.getId(),DMRegistry.getObjectType(e));
        }
        return result;
    }

    public void relink(){

    }

    protected void setCreated(LocalDate created){
        timeline.moveBirth((T) this,created);
    }
    protected void setEnded(LocalDate ended){
        timeline.moveDeath((T) this,ended);
    }
    public TimelineState<T>[] getAllStates(){
        return timeline.getStates();
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DateMutableEntity<?> dme && this.getClass().equals(dme.getClass())) {
            return this.getId().equals(dme.getId());
        }
        return false;
    }
    public Timeline<T> getTimeline(){
        return timeline;
    }
    public AbstractMutableManager<T> getManager(){
        return DMRegistry.getManager(getObjectType());
    };
    public abstract ObjectType getObjectType();
}
