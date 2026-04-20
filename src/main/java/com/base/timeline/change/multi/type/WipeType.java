package com.base.timeline.change.multi.type;

import com.Global;
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
