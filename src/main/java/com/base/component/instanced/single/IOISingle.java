package com.base.component.instanced.single;

import com.base.AbstractIOI;
import com.base.component.ComponentReference;
import com.base.component.instanced.IComponentInstance;

public abstract class IOISingle <T extends IOSingle<T,I,A>,I extends IOISingle<T,I,A>,A> extends AbstractIOI<T,I> {

    public IOISingle(ComponentReference<T> reference) {
        super(reference);
    }
}
