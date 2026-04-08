package com.utilities.serialization;

import com.google.gson.JsonObject;

public interface JsonSerializable {
    JsonObject toJson();
    void fromJson(JsonObject json);
}
