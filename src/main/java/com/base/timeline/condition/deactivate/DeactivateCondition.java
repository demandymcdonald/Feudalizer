package com.base.timeline.condition.deactivate;

import com.Global.*;
import com.base.DateMutableEntity;
import com.base.timeline.change.changes.TimelineChange;
import com.base.timeline.condition.Condition;
import com.base.timeline.error.StateError;

public abstract class DeactivateCondition<T extends DateMutableEntity<?>> extends Condition<StateError,T, TimelineChange<? super T>, TimelineChange<?>> {
    public DeactivateCondition(String id) {
        super(id);
    }
}
