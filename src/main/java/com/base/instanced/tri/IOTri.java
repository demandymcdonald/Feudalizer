package com.base.instanced.tri;

import com.base.instanced.AbstractIO;
import com.base.instanced.InstanceType;

public abstract class IOTri<T extends IOTri<T,I,A,B,C>,I extends IOITri<T,I,A,B,C>,A,B,C> extends AbstractIO<T,I> {
    public IOTri(InstanceType type, String id) {
        super(type, id);
    }

    public abstract I instance(A a, B b, C c);
}
