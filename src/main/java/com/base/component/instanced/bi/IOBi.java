package com.base.component.instanced.bi;

import com.base.component.instanced.AbstractIO;
import com.base.component.InstanceType;

public abstract class  IOBi<T extends IOBi<T,I,A,B>,I extends IOIBi<T,I,A,B>,A,B> extends AbstractIO<T,I> {
    public IOBi(InstanceType type, String id) {
        super(type, id);
    }

    public abstract I instance(A a, B b);
}
