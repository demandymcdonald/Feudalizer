package com.base.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.utilities.id.StringIdentifiable;
import com.utilities.serialization.SuperclassSerializable;

public interface IComponent<T extends IComponent<T>> extends StringIdentifiable, SuperclassSerializable<T>, IComponentLogged {

    InstanceType getInstanceType();
    T getNewObject(InstanceType type, String id, JsonObject data);
    default ComponentManager<? super T> getManager(){
        return ComponentRegistry.getManager(this.getClass());
    };
    ComponentReference<T> getReference();
    default JsonElement serializeRef(){
        return getReference().toJson();
    }
    static <T extends IComponent<T>> ComponentReference<T> deserializeRef(JsonElement element){
        return ComponentReference.fromJson(element.getAsJsonPrimitive());
    }
    static <T extends IComponent<T>> T deserializeRaw(JsonElement element){
        return (T) deserializeRef(element).get();
    }
}
