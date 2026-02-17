package com.base;

import com.GlobalData;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.time.Instant;
import java.util.*;

import static com.GlobalVars.MAX_DATE;

public abstract class DateMutableEntity<T> {
    private final UUID id;
    private final Date created;
    private final Date ended;
    private final JsonObject additionalData;
    private long currentTime;
    protected List<DateState<T>> timeline = new ArrayList<DateState<T>>();
    public record DateState<T>(Date created, Date ended, T state, List<StateChangeKey> startKey, String EndNotes) {
        public DateState(JsonObject metadata, T sta){
            this(
                    Date.from(Instant.parse(String.valueOf(metadata.get("created")))),
                    metadata.has("ended") ? Date.from(Instant.parse(String.valueOf(metadata.get("ended")))) : null,
                    sta,
                    buildStartKey(metadata.get("startKey").getAsJsonArray()),
                    metadata.has("endNotes") ? metadata.get("endNotes").getAsString() : null
            );
        }


        public JsonObject serializeMetadata(){
            JsonObject json = new JsonObject();
            json.addProperty("created", created.toInstant().toString());
            json.addProperty("ended", ended.toInstant().toString());
            json.addProperty("state", state.toString());
            json.add("startKey", buildStartArray());
            json.addProperty("Endnotes", EndNotes);
            return json;
        }
        private JsonArray buildStartArray(){
            JsonArray array = new JsonArray();
            for (StateChangeKey key : startKey){
                array.add(key.serialize());
            }
            return array;
        }
        private static List<StateChangeKey> buildStartKey(JsonArray array){
            List<StateChangeKey> key = new ArrayList<>();
            for (JsonElement element : array){
                key.add(StateChangeKey.deserialize(element.getAsJsonObject()));
            }
            return key;
        }
    }
    public DateMutableEntity(UUID id, Date created, Date ended) {
        this.id = id;
        this.created = created;
        this.ended = ended;
        this.additionalData = new JsonObject();
        DMRegistry.registerDateMutable(this);
    }
    public DateMutableEntity(UUID id, Date created, Date ended, JsonObject additionalData) {
        this.id = id;
        this.created = created;
        this.ended = ended;
        this.additionalData = additionalData;
        DMRegistry.registerDateMutable(this);
    }
    public DateMutableEntity(JsonObject payload) {
        this.id = UUID.fromString(payload.get("id").getAsString());
        this.created = Date.from(Instant.parse(payload.get("created").getAsString()));
        this.ended = Date.from(Instant.parse(payload.get("ended").getAsString()));
        JsonArray states = payload.get("states").getAsJsonArray();
        for (JsonElement state : states) {
            JsonObject obj = state.getAsJsonObject();
            JsonObject metadata = obj.getAsJsonObject("metadata");
            JsonObject pl = obj.getAsJsonObject().getAsJsonObject("payload");
            timeline.add(new DateState<>(metadata,buildState(pl)));
        }
        this.additionalData = payload.get("additionalData").getAsJsonObject();
        DMRegistry.registerDateMutable(this);
    }
    public T getStateAt(){
        return getStateAt(GlobalData.CurrentDate());
    }
    public T getStateAt(Date d) {
        return timeline.stream()
                .filter(ds -> ds.created.before(d) && (ds.ended == null || ds.ended.after(d)))
                .map(ds -> ds.state)
                .findFirst()
                .orElse(null);
    }
    protected abstract T getCurrentState(); // Each subclass implements this
    public boolean isLast(DateState<T> check){
        return timeline.get(timeline.size()-1).equals(check);
    }
    protected void addStateChange(Date startDate, StateChangeKey startNotes) {
        // Close previous state
        List<DateState<T>> unended = timeline.stream().filter(ds -> ds.ended == null || ds.ended.after(startDate)).toList();
        if (unended.size() > 1) {
            GlobalData.logger().warn("Multiple Unended states found: " + unended);
        }
        DateState<T> current = unended.getFirst();
        DateState<T> dsn;
        if (current.created.equals(startDate)) {
            for (StateChangeKey sck : current.startKey()){
                if (startNotes.equals(sck)) {
                    return;
                } else if (sck.canBeNullified(startNotes)){
                    //Nullify both entries.
                    timeline.remove(current);
                    return;
                }
            }
            current.startKey().add(startNotes);
            dsn = new DateState<>(current.created,null,getCurrentState(),current.startKey(),current.EndNotes);

        } else {
            timeline.add(new DateState<>(current.created, startDate, current.state(), current.startKey(), null)); //TODO: add state change type end messages l8r
            dsn =new DateState<>(startDate,null,current.state(),current.startKey(),null);
        }
        if (isLast(current)){
            timeline.add(dsn);
        } else {
            //TODO add sandbox instantiation here
        }
        timeline.remove(current);

//        for (DateState<T> ds : unended) {
//
//            timeline.remove(ds);
//            timeline.add(dsn);
//        }
        // Add new state
    }
    public void relinkStateChange(JsonObject o, T payload){

    }

    public UUID getId() {
        return id;
    }
    public JsonObject serialize(){
        JsonObject json = new JsonObject();
        json.addProperty("id", id.toString());
        json.addProperty("created", created.toInstant().toString());
        json.addProperty("ended", ended.toInstant().toString());
        json.add("additionalData", saveAdditional(new JsonObject()));
        for (DateState<T> ds : timeline) {
            json.add("metadata", ds.serializeMetadata());
            json.add("payload", serializeData(ds.state()));
        }
        return json;
    }
    public Date getCreated(){
        return created;
    };
    public Date getEnded(){
        if (ended == null) {
            return MAX_DATE;
        }
        return ended;
    }
    protected JsonObject getAdditionalData(){
        return additionalData;
    }
    protected JsonObject saveAdditional(JsonObject j){
        return new JsonObject();
    }
    public abstract void relink(T state);
    protected abstract JsonObject serializeData(T data);
    protected abstract T buildState(JsonObject o);
    protected void setCurrentState(Date date){
        T state = getStateAt(date);
        if (state != null) {
            relink(state);
            this.currentTime = date.getTime();
        }
    };
    public boolean isSynced(long checksum){
        return currentTime == checksum;

    }
    public static <R extends DateMutableEntity<?>,B extends Collection<R>> List<UUID> convert (B b){
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
    public static <R extends DateMutableEntity<?>> JsonArray buildJson(R... ent){
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
    public static List<UUID> quickBuildID(DateMutableEntity<?>... entities){
        List<UUID> result = new ArrayList<>();
        for (DateMutableEntity<?> e : entities){
            result.add(e.getId());
        }
        return result;
    }
}
