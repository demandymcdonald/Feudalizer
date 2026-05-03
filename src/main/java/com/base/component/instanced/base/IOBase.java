package com.base.component.instanced.base;

import com.Global.*;
import com.base.component.InstanceType;
import com.base.component.instanced.AbstractInstancedComponent;

public abstract class IOBase<T extends IOBase<T,I>,I extends IOIBase<T,I>> extends AbstractInstancedComponent<T,I> {
    public IOBase(InstanceType type, String id) {
        super(type, id);
    }
    public abstract I instance();
}
