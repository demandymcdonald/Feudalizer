package com.base.datemutable.timeline.change.condition.deactivate;

import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.TimelineChange;
import com.base.condition.Condition;
import com.base.datemutable.timeline.error.StateError;

public abstract class DeactivateCondition<T extends DateMutableEntity<?>> extends Condition<DeactivateCondition<T>,StateError, DMEReference<? extends T>, TimelineChange<? extends T>,Boolean> {
    public DeactivateCondition(String id) {
        super(id);
    }


}
