package com.base.component.instanced;

import com.base.component.ComponentReference;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.utilities.serialization.StringToHex;

import java.util.UUID;

public abstract class AbstractComponentInstance<IO extends IInstancedComponent<IO,IN>,IN extends IComponentInstance<IO,IN>> implements IComponentInstance<IO,IN> {
    private ComponentReference<IO> base;
    private UUID runtimeID;
    public AbstractComponentInstance(ComponentReference<IO> reference){
        this.base = reference;
        this.runtimeID = UUID.randomUUID();
    }
    public AbstractComponentInstance(JsonObject reference){
        this.deserialize(reference);
    }
    @Override
    public final ComponentReference<IO> getBase() {
        return base;
    }

    @Override
    public final UUID getID() {
        return runtimeID;
    }

    @Override
    public final void mainLoad(JsonObject object) {
        String[] parts = object.get("runtimeID").getAsString().split("::");
        base = ComponentReference.fromJson(new JsonPrimitive(parts[0]));
        runtimeID = UUID.fromString(StringToHex.decode(parts[1]));
    }

    @Override
    public final void mainSave(JsonObject object) {
        String s = base.toJson().toString() + "::" + StringToHex.encode(runtimeID.toString());
        object.add("runtimeID", new JsonPrimitive(s));
    }

}
