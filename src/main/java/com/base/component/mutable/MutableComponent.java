package com.base.component.mutable;

import com.base.component.AbstractComponent;
import com.base.component.InstanceType;
import com.google.gson.JsonObject;

import java.util.UUID;

public abstract class MutableComponent<T extends MutableComponent<T>> extends AbstractComponent<T> {
    private static final String delimiter = ":{id}:";
    private final UUID runtime;
    public MutableComponent(InstanceType type, String id) {
        super(type, buildID(id));
        runtime = UUID.fromString(this.getID().split(delimiter)[1]);
    }
    private static String buildID(String id){
        if(id.contains(delimiter)){
            return id;
        } else {
            UUID runtime = UUID.randomUUID();
            return id + delimiter + runtime.toString();
        }
    }
    public UUID getRuntime() {
        return runtime;
    }
    @Override
    public final void mainLoad(JsonObject object) {
        super.mainLoad(object);
    }
    @Override
    public final void mainSave(JsonObject object) {
        super.mainSave(object);
    }
}
