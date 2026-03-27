package com.utilities;

import com.google.gson.JsonObject;

public interface JsonSerializable<T extends JsonSerializable<T>> {
    JsonObject toJson();
    void fromJson(JsonObject json);
    T empty();
}
