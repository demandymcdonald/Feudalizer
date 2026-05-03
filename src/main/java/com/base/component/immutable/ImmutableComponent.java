package com.base.component.immutable;

import com.Global.*;
import com.base.component.AbstractComponent;
import com.base.component.InstanceType;

/**
 * Represents an immutable component that cannot be modified after creation through its factory variables.
 * This serves as the base implementation for components that are intended to be immutable in nature.
 *
 * @param <T> The type of the component that extends from this base class, enabling fluent typing for subclasses.
 */
public abstract class ImmutableComponent<T extends ImmutableComponent<T>> extends AbstractComponent<T> {
    public ImmutableComponent(InstanceType type, String id) {
        super(type, id);
    }
}
