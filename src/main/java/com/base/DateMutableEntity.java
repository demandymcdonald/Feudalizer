package com.base;

import com.GlobalData;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public abstract class DateMutableEntity<T> {
    private final UUID id;
    private final Date created;
    private final Date ended;
    private final JsonObject additionalData;
    private long currentTime;
    protected List<DateState<T>> timeline = new ArrayList<DateState<T>>();
    public record DateState<T>(Date created, Date ended, T state, String Startnotes, String EndNotes) {
        public DateState(JsonObject metadata, T sta){
            this(
                    Date.from(Instant.parse(String.valueOf(metadata.get("created")))),
                    metadata.has("ended") ? Date.from(Instant.parse(String.valueOf(metadata.get("ended")))) : null,
                    sta,
                    metadata.get("startNotes").getAsString(),
                    metadata.has("endNotes") ? metadata.get("endNotes").getAsString() : null
            );
        }
        public JsonObject serializeMetadata(){
            JsonObject json = new JsonObject();
            json.addProperty("created", created.toInstant().toString());
            json.addProperty("ended", ended.toInstant().toString());
            json.addProperty("state", state.toString());
            json.addProperty("Startnotes", Startnotes);
            json.addProperty("Endnotes", EndNotes);
            return json;
        }
    }
    public DateMutableEntity(UUID id, Date created, Date ended) {
        this.id = id;
        this.created = created;
        this.ended = ended;
        this.additionalData = new JsonObject();
    }
    public DateMutableEntity(UUID id, Date created, Date ended, JsonObject additionalData) {
        this.id = id;
        this.created = created;
        this.ended = ended;
        this.additionalData = additionalData;
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

    protected void addStateChange(Date startDate, String startNotes) {
        // Close previous state
        List<DateState<T>> unended = timeline.stream().filter(ds -> ds.ended == null || ds.ended.after(startDate)).toList();
        if (unended.size() > 1) {
            GlobalData.logger().warn("Multiple Unended states found: " + unended);
        }
        for (DateState<T> ds : unended) {
            DateState<T> dsn = new DateState<>(ds.created,startDate,ds.state,ds.Startnotes,ds.EndNotes);
            timeline.remove(ds);
            timeline.add(dsn);
        }
        // Add new state
        timeline.add(new DateState<>(startDate, null, getCurrentState(), startNotes, null));
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

}
