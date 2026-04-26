package com.base.instanced.bi;

import com.base.instanced.AbstractIO;
import com.base.instanced.InstanceType;

public abstract class  IOBi<T extends IOBi<T,I,A,B>,I extends IOIBi<T,I,A,B>,A,B> extends AbstractIO<T,I> {
    public IOBi(InstanceType type, String id) {
        super(type, id);
    }

    public abstract I instance(A a, B b);
}
