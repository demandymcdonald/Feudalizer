package com.base.instanced;

import com.google.gson.JsonObject;
import com.utilities.serialization.SuperclassSerializable;

public abstract class AbstractIO<T extends AbstractIO<T,I>,I extends IOInstance<T,I>> implements SuperclassSerializable<T>,IInstancedObject<T,I>{
    private InstanceType type;
    private String id;
    private final IOManager<T,I> manager = findManager();
    public AbstractIO(InstanceType type, String id){
        this.type = type;
        this.id = id;
        if (type != InstanceType.DATA_DRIVEN) {
            manager.register((T) this);
        }
    }
    @Override
    public final IOManager<T,I> getManager(){
        return manager;
    }
    public final InstanceType getInstanceType(){
        return type;
    }
    private IOManager<T,I> findManager(){
        return IOManager.getManager(getClass());
    }
    @Override
    public final String getID(){
        return id;
    }

    @Override
    public final IOReference<T> getReference() {
        return IInstancedObject.super.getReference();
    }

    @Override
    public final void mainLoad(JsonObject object) {
        id = object.get("id").getAsString();
        type = InstanceType.valueOf(object.get("type").getAsString());
    }

    @Override
    public final void mainSave(JsonObject object) {
        object.addProperty("id", getID());
        object.addProperty("type", getInstanceType().toString());
    }
}
