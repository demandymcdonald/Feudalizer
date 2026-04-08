package com.utilities.serialization;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public interface RegistrySerializable {
    default JsonObject serialize(){
        JsonObject json = new JsonObject();
        json.addProperty("type", this.getClass().getName());
        JsonObject payload = new JsonObject();
        toJson(payload);
        json.add("payload", payload);
        return json;
    }

    void toJson(JsonObject json);
}
