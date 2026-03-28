package com.base.timeline;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineChange;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TimelineContainer<T extends TimelineContainer<T,D>, D extends DateMutableEntity<D,T>> {
    JsonObject getSerialized();
    T getDeserialized(JsonObject json);
    String Header();

    TimelineChange<D> getBirthChange(DMEReference<D> dme, LocalDate date);
    TimelineChange<D> getDeathChange(DMEReference<D> dme, LocalDate date);
    default TimelineState<D> buildBirth(DMEReference<D> dme, LocalDate date, JsonObject payload){
        ArrayList<TimelineChange<D>> changes = new ArrayList<>();
        changes.add(getBirthChange(dme,date));
        return new TimelineState<>(date, Optional.of(date),true,payload, changes);
    };
    default TimelineState<D> buildDeath(DMEReference<D> dme, LocalDate date, JsonObject payload){
        ArrayList<TimelineChange<D>> changes = new ArrayList<>();
        changes.add(getBirthChange(dme,date));
        return new TimelineState<>(date, Optional.of(date),true,payload, changes);
    };
    default JsonObject serialize(){
        JsonObject json = new JsonObject();
        json.addProperty("header", Header());
        json.add("data", getSerialized());
        return json;
    }
    default T deserialize(JsonObject json){
        if (!json.has("data") || !json.has("header")){
            throw new IllegalArgumentException("Invalid TimelineContainer" + json.toString());
        }
        if (!json.get("header").getAsString().equals(Header())){
            throw new IllegalArgumentException("Invalid TimelineContainer Header for Container Type" + json.get("header").getAsString() + " Expected: " + Header());
        }
        return getDeserialized(json.getAsJsonObject("data"));
    }
}
