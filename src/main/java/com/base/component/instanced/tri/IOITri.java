package com.base.component.instanced.tri;

import com.base.component.instanced.AbstractComponentInstance;
import com.base.component.ComponentReference;

public abstract class IOITri<T extends IOTri<T,I,A,B,C>,I extends IOITri<T,I,A,B,C>,A,B,C> extends AbstractComponentInstance<T,I> {


    public IOITri(ComponentReference<T> reference) {
        super(reference);
    }
}
