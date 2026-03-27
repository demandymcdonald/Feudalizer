package com.base;

import com.Feudalizer;

import com.GlobalVars;
import com.base.timeline.Timeline;
import com.base.timeline.TimelineContainer;
import com.base.timeline.TimelineState;
import com.base.timeline.change.TimelineChange;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.util.*;
import java.util.function.BiConsumer;
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
public abstract class DateMutableEntity<T extends DateMutableEntity<T,C>, C extends TimelineContainer<C>> {
    private final UUID id;
    private LocalDate created;
    private LocalDate ended;
    private LocalDate currentStateStart;
    private final Timeline<T,C> timeline;

    private LocalDate currentStateEnd;
    private final JsonObject additionalData;
//    private long currentTime;
//    protected List<DateState<T>> timeline = new ArrayList<DateState<T>>();

    public DateMutableEntity(UUID id, LocalDate created, LocalDate ended) {
        this(id,created,ended,new JsonObject());
    }
    public DateMutableEntity(UUID id, LocalDate created, LocalDate ended, JsonObject additionalData) {
        this.id = id;
        this.created = created;
        this.ended = ended;
        this.additionalData = additionalData;
        this.timeline = new Timeline<>(getManager().getEmptyObject());
    }
    public DateMutableEntity(JsonObject payload, Timeline<T,C> timeline) {
        this.id = UUID.fromString(payload.get("id").getAsString());
        this.created = LocalDate.ofEpochDay(payload.get("created").getAsLong());
        this.timeline = timeline;
        if (payload.has("ended")) {
            this.ended = LocalDate.ofEpochDay(payload.get("ended").getAsLong());
        } else {
            this.ended = null;
        }
        JsonArray states = payload.get("states").getAsJsonArray();
        for (JsonElement state : states) {
            JsonObject obj = state.getAsJsonObject();
//            JsonObject metadata = obj.getAsJsonObject("metadata");
//            JsonObject pl = obj.getAsJsonObject().getAsJsonObject("payload");
            timeline.unsafeInsertState(TimelineState.deserialize(obj));
        }
        this.additionalData = payload.get("additionalData").getAsJsonObject();
    }

    public C getStateAt(){
        return getStateAt(GlobalVars.CURRENT_DATE());
    }
    public TimelineState<T,C> getDateStateAt(LocalDate d){
        return timeline.getState(d);
    }
    public C getStateAt(LocalDate d) {
        return timeline.getContainer(d);
    }
    public LocalDate getNextDate(){
        TimelineState<?> s = timeline.getNextState(GlobalVars.CURRENT_DATE());
        if (s == null) return null;
        return s.start();
    }
    public abstract C getCurrentContainer(); // Each subclass implements this. Should package up current state
    public boolean isLast(TimelineState<T> check){
        return timeline.isLast(check.start());
    }
    /**
     * Adds a state change to the entity's timeline based on the provided start date and StateChangeKey.
     * Performs validation, updates the timeline, and saves the updated entity state to the database.
     *
     * @param date  The starting date of the new state change.
     * @param startNotes The details of the state change in the form of a StateChangeKey.
     */
    @SafeVarargs
    protected final void addStateChange(LocalDate date, TimelineChange<T> startNotes, Consumer<TimelineChange<T>> runnable){
        addStateChange(date,false,startNotes);
    }
    @SafeVarargs
    protected final boolean addStateChange(LocalDate date, boolean bypass, TimelineChange<T>... startNotes) {
        Feudalizer.LOGGER.info("{} {} State Change: {} -> {}", this.getClass(),this.getId(),date,startNotes);
        TimelineState<T> before = timeline.getState(date);
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
    public boolean saveStateChange(TimelineState<T> state){
        if (DMRegistry.isMain()) {
            return timeline.safeInsertState(this,state);
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
        json.addProperty("id", id.toString());
        json.addProperty("created", created.toEpochDay());
        if (ended != null) {
            json.addProperty("ended", ended.toEpochDay());
        }
        json.add("additionalData", saveAdditional(new JsonObject()));
        JsonArray states = new JsonArray();
        for (TimelineState ds : timeline.getStates()) {
            JsonObject state = ds.serialize();
            states.add(state);
        }
        json.add("states", states);
        return json;
    }
    public LocalDate getCreated(){
        return created;
    };
    public LocalDate getEnded(){
        if (ended == null) {
            return MAX_DATE;
        }
        return ended;
    }
    /**
     * Retrieves the additional data associated with the entity.
     *
     * @return a JsonObject representing the additional data of the entity.
     */
    protected JsonObject getAdditionalData(){
        return additionalData;
    }
    /**
     * Saves or processes additional data related to the entity and returns the modified or processed
     * data as a JsonObject. This method may be overridden in subclasses to implement specific data
     * handling logic.
     *
     * @param j a JsonObject containing the additional data to be saved or processed.
     * @return a JsonObject representing the saved or processed additional data.
     */
    protected JsonObject saveAdditional(JsonObject j){
        return new JsonObject();
    }
    /**
     * Updates or modifies the current state of the entity using the provided state information.
     *
     * @param state The new state instance to be linked or associated with the entity. This state
     *              is expected to represent valid, updated data used for modifying or
     *              re-establishing the entity's state.
     */
    public abstract void relink(C state);
    protected abstract JsonObject serializeData(T data);

    /**
     * Constructs a new state instance from the provided JSON object. This method is intended
     * to deserialize or interpret the JSON object to create an instance of the state.
     *
     * @param o the JSON object containing the data to build the state. The JSON must contain
     *          the necessary fields to properly construct an instance of the state.
     * @return an instance of the state constructed from the input JSON object.
     */
    protected final C buildState(JsonObject o){
        return getManager().deserializeContainer(o);
    };
    protected void setCurrentState(LocalDate date){
        TimelineState<T> state = getDateStateAt(date);
        currentStateStart = state.start();
        currentStateEnd = state.end().orElse(null);
        if (state != null) {
            relink(timeline.getContainer(date));
            //this.currentTime = date.getTime();
        }
    };

    public static <R extends DateMutableEntity<?,?>,B extends Collection<R>> List<UUID> convert (B b){
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
    public static <R extends DateMutableEntity<?,?>> JsonArray buildJson(R... ent){
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
    public static Map<UUID,ObjectType> quickBuildID(DateMutableEntity<?,?>... entities){
        Map<UUID,ObjectType> result = new HashMap<>();
        for (DateMutableEntity<?,?> e : entities){
            result.put(e.getId(),DMRegistry.getObjectType(e));
        }
        return result;
    }
    public LocalDate getCurrentStateEnd() {
        return currentStateEnd;
    }

    public LocalDate getCurrentStateStart() {
        return currentStateStart;
    }
    public TimelineState<T> getCurrentState(){
        return timeline.getState(GlobalVars.CURRENT_DATE());
    }
    public void relink(){

    }
    public abstract TimelineChange<T> defaultKey();
    public void init(){
        if (timeline.isEmpty()) {
            addStateChange(getCreated(),defaultKey());
        }
        DMRegistry.registerDateMutable(this);
        Feudalizer.LOGGER.debug("{} {} Created", this.getClass(),this.getId());
    };
    protected void setCreated(LocalDate created){
        this.created = created;
    }
    protected void setEnded(LocalDate ended){
        this.ended = ended;
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

    public abstract AbstractMutableManager<T,C> getManager();
    public ObjectType getObjectType(){
        return DMRegistry.getObjectType(this);
    }
}
