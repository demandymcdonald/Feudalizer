package com.base.instanced;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.utilities.id.Identifiable;
import com.utilities.id.StringIdentifiable;
import com.utilities.serialization.SuperclassSerializable;

import java.util.UUID;

public interface IInstance<T extends IInstantiatable<T,I,ID>,I extends IInstance<T,I,ID>,ID> extends StringIdentifiable, SuperclassSerializable<I> {
    T getBase();
    UUID getInstanceID();
    void setInstanceID(UUID id);
    @Override
    default String getID(){
        return getBase().getID().toString() + ":" + getInstanceID();
    };
    JsonElement serializeBase();
    void deserializeBase(JsonElement element);
    @Override
    default void mainLoad(JsonObject object){

    };

    @Override
    default void mainSave(JsonObject object){

    };
}
