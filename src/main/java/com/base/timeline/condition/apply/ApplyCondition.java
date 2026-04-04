package com.base.timeline.condition.apply;

import com.base.DateMutableEntity;
import com.base.timeline.change.changes.TimelineChange;
import com.base.timeline.condition.Condition;
import com.base.timeline.error.StateError;

public abstract class ApplyCondition<T extends DateMutableEntity<?>> extends Condition<StateError,T,TimelineChange<? super T>, TimelineChange<?>> {
    public ApplyCondition(String id) {
        super(id);
    }
}
