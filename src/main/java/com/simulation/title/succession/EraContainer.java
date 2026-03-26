package com.simulation.title.succession;

import com.base.utilities.JsonSerializable;
import com.google.gson.JsonObject;

public record EraContainer() implements JsonSerializable<EraContainer> {
    private
    @Override
    public JsonObject toJson() {
        return null;
    }

    @Override
    public void fromJson(JsonObject json) {

    }

    @Override
    public EraContainer empty() {
        return null;
    }
}
