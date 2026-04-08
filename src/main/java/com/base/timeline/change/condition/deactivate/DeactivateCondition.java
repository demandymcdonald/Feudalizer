package com.base.timeline.change.condition.deactivate;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineChange;
import com.base.condition.Condition;
import com.base.timeline.error.StateError;
import com.base.timeline.state.TimelineState;

public abstract class DeactivateCondition<T extends DateMutableEntity<?>> extends Condition<StateError, DMEReference<? extends T>, TimelineChange<? extends T>,Boolean> {
    public DeactivateCondition(String id) {
        super(id);
    }

    @Override
    public boolean runOncePerState() {
        return true;
    }
}
