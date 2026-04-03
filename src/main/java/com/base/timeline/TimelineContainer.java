package com.base.timeline;

import com.base.DateMutableEntity;
import com.google.gson.JsonObject;

public interface TimelineContainer<T extends TimelineContainer<T,D>, D extends DateMutableEntity<D>> {
    JsonObject getSerialized();
    T getDeserialized(JsonObject json);
    String Header();


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
