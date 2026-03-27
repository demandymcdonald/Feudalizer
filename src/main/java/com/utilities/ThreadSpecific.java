package com.utilities;

public abstract class ThreadSpecific{

    public enum Type {
        GLOBAL_VARIABLE_CONTAINER,
        DM_REGISTRY
    }
    public abstract Type specificTypeName();
    public abstract void onThreadInit();
}
