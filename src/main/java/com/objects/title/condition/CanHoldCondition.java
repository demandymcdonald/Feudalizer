package com.objects.title.condition;

import com.base.timeline.condition.Condition;
import com.base.timeline.error.StateError;
import com.objects.title.Title;

public abstract class CanHoldCondition<T extends Title<T>> extends Condition<StateError, T> {
    public CanHoldCondition(String id) {
        super(id);
    }



}
