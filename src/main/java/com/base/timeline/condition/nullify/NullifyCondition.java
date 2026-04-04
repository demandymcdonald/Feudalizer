package com.base.timeline.condition.nullify;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.changes.TimelineChange;
import com.base.timeline.condition.Condition;

public abstract class NullifyCondition<T extends DateMutableEntity<?>> extends Condition<NullifyResult, T, TimelineChange<T>, TimelineChange<?>> {

    public NullifyCondition(String id) {
        super(id);
    }
}
