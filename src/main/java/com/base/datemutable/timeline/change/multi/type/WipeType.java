package com.base.datemutable.timeline.change.multi.type;

import com.Global.*;

public enum WipeType {
    FORWARD(TimeDirection.FORWARD),
    BACKWARD(TimeDirection.BACKWARD),
    BOTH,
    NO_WIPE;

    private final TimeDirection direction;

    public TimeDirection getDirection() {
        return direction;
    }

    WipeType(TimeDirection direction) {
        this.direction = direction;
    }

    WipeType() {
        this.direction = TimeDirection.FORWARD;
    }
}
