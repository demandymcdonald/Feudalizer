package com.utilities.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public interface JsonSerializable {
    JsonElement toJson();
    void fromJson(JsonElement json);
}
