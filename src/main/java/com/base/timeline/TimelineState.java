package com.base.timeline;

import com.base.DateMutableEntity;
import com.base.StateChangeKey;
import com.base.reference.StateReference;
import com.base.timeline.change.TimelineChange;
import com.google.gson.JsonObject;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a point-in-time snapshot of an entity with its associated change history.
 *
 * <p>{@code trail} is the ordered sequence of {@link TimelineChange} instances that have
 * affected this entity from its birth up to (and including) this state. Each new state
 * inherits the previous state's trail and appends the incoming change, so the trail grows
 * monotonically and can be retraced to reconstruct the propagation history. This is
 * distinct from {@code changeLog}, which only tracks the <em>currently active</em> changes
 * for this state snapshot.</p>
 */
public record TimelineState<T extends DateMutableEntity<T,?>> (LocalDate start, Optional<LocalDate> end, boolean isImmutable, JsonObject payload, ArrayList<TimelineChange<T>> changeLog, ArrayList<TimelineChange<T>> trail) {
    /** Convenience constructor — empty trail. */
    public TimelineState(LocalDate start, Optional<LocalDate> end, JsonObject payload, ArrayList<TimelineChange<T>> changeLog){
        this(start, end, false, payload, changeLog, new ArrayList<>());
    }
    /** Convenience constructor — explicit immutability, empty trail. */
    public TimelineState(LocalDate start, Optional<LocalDate> end, boolean isImmutable, JsonObject payload, ArrayList<TimelineChange<T>> changeLog){
        this(start, end, isImmutable, payload, changeLog, new ArrayList<>());
    }
    /** Copy constructor that replaces the trail. Used when inheriting a trail during state insertion. */
    public TimelineState(TimelineState<T> source, ArrayList<TimelineChange<T>> trail) {
        this(source.start(), source.end(), source.isImmutable(), source.payload(), source.changeLog(), trail);
    }
    public JsonObject serialize() {
        JsonObject json = new JsonObject();
        JsonObject metadata = new JsonObject();
        metadata.addProperty("start", start.toString());
        end.ifPresent(e -> metadata.addProperty("end", e.toString()));
        json.add("metadata", metadata);
        json.add("payload", payload);
        return json;
    }
    public static<T extends DateMutableEntity<T,?>> TimelineState<T> deserialize(JsonObject json) {
        return new TimelineState<T>(LocalDate.parse(json.getAsJsonObject("metadata").get("start").getAsString()), Optional.ofNullable(json.getAsJsonObject("metadata").get("end")).map(e -> LocalDate.parse(e.getAsString())), json.getAsJsonObject("payload"), new ArrayList<>());
    }
    public boolean isComplete(){
        return end.isPresent();
    }
    @Override
    public Optional<LocalDate> end() {
        return end;
    }

    @Override
    public LocalDate start() {
        return start;
    }
    public List<TimelineChange<T>> getChanges(){
        return new ArrayList<>(changeLog);
    }
    /** Returns a snapshot of the breadcrumb trail for this state. */
    public List<TimelineChange<T>> getTrail() {
        return new ArrayList<>(trail);
    }
}
