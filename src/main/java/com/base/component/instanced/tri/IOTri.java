package com.base.component.instanced.tri;

import com.base.component.instanced.AbstractInstancedComponent;
import com.base.component.InstanceType;

public abstract class IOTri<T extends IOTri<T,I,A,B,C>,I extends IOITri<T,I,A,B,C>,A,B,C> extends AbstractInstancedComponent<T,I> {
    public IOTri(InstanceType type, String id) {
        super(type, id);
    }

    public abstract I instance(A a, B b, C c);
}
