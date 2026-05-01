package com.base.component.instanced.single;

import com.base.component.instanced.AbstractIOI;
import com.base.component.ComponentReference;

public abstract class IOISingle <T extends IOSingle<T,I,A>,I extends IOISingle<T,I,A>,A> extends AbstractIOI<T,I> {

    public IOISingle(ComponentReference<T> reference) {
        super(reference);
    }
}
