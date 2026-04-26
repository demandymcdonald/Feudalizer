package com.base.datemutable.timeline.change.multi.type;

public enum Delta {
    ADD,
    ADD_WIPE(WipeType.FORWARD),
    REMOVE,
    REMOVE_WIPE_FORWARD(WipeType.FORWARD),
    REMOVE_WIPE_BACKWARD(WipeType.BACKWARD),
    REMOVE_WIPE_BOTH(WipeType.BOTH),
    MODIFY_BOTH(ChangeType.BOTH),
    MODIFY_KEY(ChangeType.KEY),
    MODIFY_VALUE(ChangeType.VALUE),
    MODIFY_BOTH_WIPE(WipeType.FORWARD, ChangeType.BOTH),
    MODIFY_KEY_WIPE(WipeType.FORWARD, ChangeType.KEY),
    MODIFY_VALUE_WIPE(WipeType.FORWARD, ChangeType.VALUE),
    ;

    private final WipeType wipeType;
    private final ChangeType changeEntry;

    public WipeType getWipeType() {
        return wipeType;
    }

    public ChangeType getChangeType() {
        return changeEntry;
    }

    Delta(WipeType wipeType) {
        this.wipeType = wipeType;
        this.changeEntry = ChangeType.NO_CHANGE;
    }

    Delta(WipeType wipeType, ChangeType changeEntry) {
        this.wipeType = wipeType;
        this.changeEntry = changeEntry;
    }

    Delta(ChangeType changeEntry) {
        this.wipeType = WipeType.NO_WIPE;
        this.changeEntry = changeEntry;
    }

    Delta() {
        this.wipeType = WipeType.NO_WIPE;
        this.changeEntry = ChangeType.NO_CHANGE;
    }
}
