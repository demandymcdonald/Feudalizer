package com.base.component.instanced.tri;

import com.base.component.instanced.AbstractIOI;
import com.base.component.ComponentReference;

public abstract class IOITri<T extends IOTri<T,I,A,B,C>,I extends IOITri<T,I,A,B,C>,A,B,C> extends AbstractIOI<T,I> {


    public IOITri(ComponentReference<T> reference) {
        super(reference);
    }
}
