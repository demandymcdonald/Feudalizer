package com.base;

import com.Feudalizer;

import com.GlobalVars;
import com.base.reference.DMEReference;
import com.base.timeline.Timeline;
import com.base.timeline.TimelineContainer;
import com.base.timeline.TimelineState;
import com.base.timeline.change.TimelineChange;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.utilities.SidecarSave;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;

import static com.GlobalVars.MAX_DATE;

/**
 * Represents an abstract class for date-aware mutable entities that track state changes over time.
 * This class maintains a timeline of states, which are stored as objects of the nested {@code DateState} record.
 * Each state includes metadata such as creation and end dates, associated keys for state changes, and a payload
 * representing the state value.
 *
 * @param <T> The type representing the state of the entity.
 */
public abstract class DateMutableEntity<T extends DateMutableEntity<T>> implements SidecarSave {
    private final UUID id;
    private final Timeline<T> timeline;
    public DateMutableEntity(LocalDate created, @Nullable LocalDate ended, List<TimelineChange<T>> initialState) {
        this.id = UUID.randomUUID();
        this.timeline = new Timeline<T>(getObjectType(),id,created,ended,initialState);
        DMRegistry.registerDateMutable(this);
    }
//    public DateMutableEntity(JsonObject payload, Timeline<T,C> timeline) {
//        this.id = UUID.fromString(payload.get("id").getAsString());
//        this.created = LocalDate.ofEpochDay(payload.get("created").getAsLong());
//        this.timeline = timeline;
//        if (payload.has("ended")) {
//            this.ended = LocalDate.ofEpochDay(payload.get("ended").getAsLong());
//        } else {
//            this.ended = null;
//        }
//        JsonArray states = payload.get("states").getAsJsonArray();
//        for (JsonElement state : states) {
//            JsonObject obj = state.getAsJsonObject();
////            JsonObject metadata = obj.getAsJsonObject("metadata");
////            JsonObject pl = obj.getAsJsonObject().getAsJsonObject("payload");
//            timeline.unsafeInsertState(TimelineState.deserialize(obj));
//        }
//        this.additionalData = payload.get("additionalData").getAsJsonObject();
//    }
    public DateMutableEntity(DMEReference<T> dme, JsonObject saveData) {
        this.id = UUID.randomUUID();
        this.timeline = new Timeline<T>(dme,saveData.get("timeline").getAsJsonObject());
        onLoad(saveData.get("additionalData").getAsJsonObject());
        DMRegistry.registerDateMutable(this);
    }

    /**
     * Adds a state change to the entity's timeline based on the provided start date and StateChangeKey.
     * Performs validation, updates the timeline, and saves the updated entity state to the database.
     *
     * @param date  The starting date of the new state change.
     * @param startNotes The details of the state change in the form of a StateChangeKey.
     */
    protected final void addStateChange(LocalDate date, TimelineChange<T> startNotes, Consumer<TimelineChange<T>> runnable){
        addStateChange(date,false,startNotes);
    }
    @SafeVarargs
    protected final boolean addStateChange(LocalDate date, boolean bypass, TimelineChange<T>... startNotes) {
        Feudalizer.LOGGER.info("{} {} State Change: {} -> {}", this.getClass(),this.getId(),date,startNotes);
        TimelineState<T> before = timeline.getStateNullable(date);
        TimelineState<T> after = timeline.getNextState(date);
        LocalDate endCurrent = null;
        ArrayList<TimelineChange<T>> mergedChangeLog;
        if (before != null && (before.end().isPresent()) && before.start().equals(date)) {
            if (DMRegistry.isMain() || bypass) {
                mergedChangeLog = new ArrayList<>(before.changeLog());
                mergedChangeLog.addAll(List.of(startNotes));
                mergedChangeLog = new ArrayList<>(List.of(startNotes));
            } else {
                mergedChangeLog = new ArrayList<>(before.changeLog());
            }
        } else {
            mergedChangeLog = new ArrayList<>(List.of(startNotes));
        }
        TimelineState<T> newState = new TimelineState<>(date,Optional.ofNullable(endCurrent),this.getCurrentContainer().getSerialized(),mergedChangeLog);
        return saveStateChange(newState);
    }
    public boolean saveStateChange(TimelineState<T> state, @Nullable TimelineChange<?> c){
        if (DMRegistry.isMain()) {
            return timeline.safeInsertState(this,state,c);
        } else {
            timeline.unsafeInsertState(state);
            return false;
        }
    }
    /**
     * Modifies or updates the state of the current entity by utilizing the provided
     * state information and data from the given JSON object.
     *
     * @param o       A JsonObject containing state-related data to be interpreted
     *                or deserialized for the update process.
     * @param payload A state-specific payload used to relink or modify the entity's state,
     *                allowing for fine-grained state management or synchronization.
     */
    public void relinkStateChange(JsonObject o, T payload){

    }

    public UUID getId() {
        return id;
    }

    /**
     * Serializes the current entity to a JsonObject representation. This includes properties such as
     * id, created, ended, additional data, and the entity's timeline of states. Each state in the
     * timeline is serialized along with its metadata and payload.
     *
     * @return a JsonObject containing the serialized representation of the entity.
     */
    public JsonObject serialize(){
        JsonObject json = new JsonObject();
        json.add("additionalData", saveAdditional(new JsonObject()));
        json.add("timeline", timeline.serialize());
        return json;
    }
    public LocalDate getCreated(){
        return timeline.getEarliestDate();
    };
    public LocalDate getEnded(){
        return timeline.getLatestDate();
    }



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

    public void init(){

        Feudalizer.LOGGER.debug("{} {} Created", this.getClass(),this.getId());
    };
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
        if (obj instanceof DateMutableEntity<?,?> dme && this.getClass().equals(dme.getClass())) {
            return this.getId().equals(dme.getId());
        }
        return false;
    }
    public Timeline<T,C> getTimeline(){
        return timeline;
    }
    public AbstractMutableManager<T,C> getManager(){
        return DMRegistry.getManager(getObjectType());
    };
    public abstract ObjectType getObjectType();
}
