package com.utilities.hierarchy;

import org.apache.commons.lang3.mutable.MutableLong;

public interface StateIntegrity {
    MutableLong getLast();
    long getCurrent();
    default void onDirty(){};
    default boolean isDirty(){
        long current = getCurrent();
        if (current != getLast().longValue()){
            getLast().setValue(current);
            onDirty();
            return true;
        }
        return false;
    }
}
