package com.objects.culture.object.ideology;

import com.base.instanced.IOInstance;
import com.base.instanced.IOReference;
import com.google.gson.JsonElement;

import java.util.UUID;

public record IdeologyInstance(IOReference<Ideology> ideology) implements IOInstance<Ideology, IdeologyInstance> {
    @Override
    public IOReference<Ideology> getBase() {
        return null;
    }

    @Override
    public UUID getID() {
        return null;
    }

    @Override
    public JsonElement toJson() {
        return null;
    }

    @Override
    public void fromJson(JsonElement json) {

    }
}
