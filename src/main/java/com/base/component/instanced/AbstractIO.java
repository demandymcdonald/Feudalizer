package com.base.component.instanced;

import com.base.component.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.utilities.serialization.SuperclassSerializable;

public abstract class AbstractIO<T extends AbstractIO<T,I>,I extends IComponentInstance<T,I>> extends AbstractComponent<T> implements SuperclassSerializable<T>, IInstancedComponent<T,I> {

    public AbstractIO(InstanceType type, String id) {
        super(type, id);
    }
}
