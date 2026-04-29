package com.base.component;

public enum InstanceType {
    HARDCODED,
    FACTORY(true),
    PROCEDURAL,
    DATA_DRIVEN(true);

    private final boolean isSavable;

    InstanceType() {
        this.isSavable = false;
    }

    InstanceType(boolean isSavable) {
        this.isSavable = isSavable;
    }
    public boolean isSavable() {
        return isSavable;
    }
}
