package com.base.component;

import com.Global.*;
import com.base.component.instanced.IInstancedComponent;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public abstract class AbstractComponent<T extends AbstractComponent<T>> implements IComponent<T> {

    private InstanceType type;
    private String id;
    private final ComponentReference<T> reference;

    public AbstractComponent(InstanceType type, String id){
        this.type = type;
        this.id = id;
        reference = ComponentReference.of((T)this);
        if (type != InstanceType.DATA_DRIVEN) {
            ComponentRegistry.getManager(this.getClass()).register(this);
        }
    }
    @Override
    public final InstanceType getInstanceType(){
        return type;
    }

    @Override
    public final String getID(){
        return id;
    }

    @Override
    public final ComponentManager<? super T> getManager() {
        return IComponent.super.getManager();
    }

    @Override
    public final JsonElement serializeRef() {
        return IComponent.super.serializeRef();
    }

    @Override
    public final ComponentReference<T> getReference() {
        return reference;
    }

    @Override
    public void mainLoad(JsonObject object) {
        id = object.get("id").getAsString();
        type = InstanceType.valueOf(object.get("type").getAsString());
    }
    @Override
    public void mainSave(JsonObject object) {
        object.addProperty("id", getID());
        object.addProperty("type", getInstanceType().toString());
    }
}
