package com.base.datemutable.timeline.change.multi.type;

public enum ChangeType {
    KEY(Delta.MODIFY_KEY, Delta.MODIFY_KEY_WIPE),
    VALUE(Delta.MODIFY_VALUE, Delta.MODIFY_VALUE_WIPE),
    BOTH(Delta.MODIFY_BOTH, Delta.MODIFY_BOTH_WIPE),

    NO_CHANGE(null, null);
    private final Delta type;
    private final Delta typeWipe;

    ChangeType(Delta type, Delta typeWipe) {
        this.type = type;
        this.typeWipe = typeWipe;
    }

    public Delta getDelta() {
        return type;
    }

    public Delta getDeltaWipe() {
        return typeWipe;
    }
}
