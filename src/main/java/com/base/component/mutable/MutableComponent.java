package com.base.component.mutable;

import com.base.component.AbstractComponent;
import com.base.component.InstanceType;
import com.google.gson.JsonObject;

import java.util.UUID;

public abstract class MutableComponent<T extends MutableComponent<T>> extends AbstractComponent<T> {
    private static final String delimiter = ":{id}:";
    public MutableComponent(InstanceType type, String id) {
        super(type, buildID(type, id));
    }
    private static String buildID(InstanceType type, String id){
        if (type == InstanceType.DATA_DRIVEN || type == InstanceType.EXTERNAL) {
            if(id.contains(delimiter)){
                return id;
            } else {
                UUID runtime = UUID.randomUUID();
                return id + delimiter + runtime.toString();
            }
        } else {
            return id;
        }
    }
}
