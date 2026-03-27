package com.base.timeline.propagation.core;

import com.base.ObjectType;
import com.base.reference.DMEReference;
import com.base.timeline.TimelineChangeState;

import java.util.UUID;

public record  Objective(ObjectType type, UUID id, TimelineChangeState stateToApply) {
    public Objective(DMEReference<?> reference, TimelineChangeState stateToApply){
        this(reference.getType(),reference.getUuid(),stateToApply);
    }
    public TimelineChangeState state(){
        return stateToApply;
    }
}
