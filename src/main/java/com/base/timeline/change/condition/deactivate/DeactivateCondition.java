package com.base.timeline.change.condition.deactivate;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineChange;
import com.base.condition.Condition;
import com.base.timeline.error.StateError;

public abstract class DeactivateCondition<T extends DateMutableEntity<?>> extends Condition<StateError, DMEReference<? extends T>,TimelineChange<? extends T>, TimelineChange<?>> {
    public DeactivateCondition(String id) {
        super(id);
    }
}
