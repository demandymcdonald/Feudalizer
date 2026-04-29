package com.base.component.instanced.single;

import com.base.component.instanced.AbstractIO;
import com.base.component.InstanceType;

public abstract class IOSingle<T extends IOSingle<T,I,A>,I extends IOISingle<T,I,A>,A> extends AbstractIO<T,I> {
    public IOSingle(InstanceType type, String id) {
        super(type, id);
    }

    public abstract I instance(A a);
}
