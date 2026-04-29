package com.base.component.immutable;

import com.Global.*;
import com.base.component.AbstractComponent;
import com.base.component.InstanceType;

public abstract class ImmutableComponent<T extends ImmutableComponent<T>> extends AbstractComponent<T> {
    public ImmutableComponent(InstanceType type, String id) {
        super(type, id);
    }
}
