package com.base.timeline.propagation.core;

import com.base.ObjectType;
import com.base.timeline.TimelineChangeState;

import java.util.UUID;

public record  Objective(ObjectType type, UUID id, TimelineChangeState stateToApply) {

    public TimelineChangeState state(){
        return stateToApply;
    }
}
