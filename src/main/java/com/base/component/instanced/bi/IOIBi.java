package com.base.component.instanced.bi;

import com.base.component.instanced.AbstractIOI;
import com.base.component.ComponentReference;
import com.google.gson.JsonObject;

public abstract class IOIBi<T extends IOBi<T,I,A,B>,I extends IOIBi<T,I,A,B>,A,B> extends AbstractIOI<T,I> {

    public IOIBi(ComponentReference<T> reference) {
        super(reference);
    }

    public IOIBi(JsonObject reference) {
        super(reference);
    }
}
