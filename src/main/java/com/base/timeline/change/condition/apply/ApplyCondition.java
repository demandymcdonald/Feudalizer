package com.base.timeline.change.condition.apply;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineChange;
import com.base.condition.Condition;
import com.base.timeline.error.StateError;

public abstract class ApplyCondition<T extends DateMutableEntity<?>> extends Condition<StateError, DMEReference<? extends T>,TimelineChange<? extends T>, TimelineChange<?>> {
    public ApplyCondition(String id) {
        super(id);
    }
}
