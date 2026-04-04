package com.objects.title.condition;

import com.base.timeline.condition.Condition;
import com.base.timeline.error.StateError;
import com.objects.title.Title;

public abstract class CanInheritCondition<T extends Title<T>> extends Condition<StateError, T> {
    public CanInheritCondition(String id) {
        super(id);
    }
}
