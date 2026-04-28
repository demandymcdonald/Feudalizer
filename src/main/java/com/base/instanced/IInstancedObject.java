package com.base.instanced;

import com.utilities.id.StringIdentifiable;
import com.utilities.serialization.SuperclassSerializable;

public interface IInstancedObject<T extends IInstancedObject<T, I>, I extends IOInstance<T, I>> extends StringIdentifiable, SuperclassSerializable<T>, IIO{
    T getNewObject(String id);
    InstanceType getInstanceType();
    IOManager<T,I> getManager();
    default IOReference<T> getReference(){
        return IOReference.of((T) this);
    }
}
