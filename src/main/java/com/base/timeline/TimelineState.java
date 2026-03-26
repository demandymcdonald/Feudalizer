package com.base.timeline;

import com.base.DateMutableEntity;
import com.base.StateChangeKey;
import com.base.reference.StateReference;
import com.base.timeline.change.TimelineChange;
import com.google.gson.JsonObject;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public record TimelineState<T extends DateMutableEntity<T,?>> (LocalDate start, Optional<LocalDate> end, JsonObject payload, ArrayList<TimelineChange<T>> changeLog) {

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
}
