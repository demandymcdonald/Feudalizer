package com.base.component.instanced;

import com.base.component.*;
import com.utilities.serialization.SuperclassSerializable;

public abstract class AbstractInstancedComponent<T extends AbstractInstancedComponent<T,I>,I extends IComponentInstance<T,I>> extends AbstractComponent<T> implements SuperclassSerializable<T>, IInstancedComponent<T,I> {

    public AbstractInstancedComponent(InstanceType type, String id) {
        super(type, id);
    }
}
