package com.base.component.instanced.base;

import com.Global.*;
import com.base.component.ComponentReference;
import com.base.component.instanced.AbstractComponentInstance;
import com.google.gson.JsonObject;

public abstract class IOIBase<T extends IOBase<T,I>,I extends IOIBase<T,I>> extends AbstractComponentInstance<T,I> {
    public IOIBase(ComponentReference<T> reference) {
        super(reference);
    }

    public IOIBase(JsonObject reference) {
        super(reference);
    }
}
