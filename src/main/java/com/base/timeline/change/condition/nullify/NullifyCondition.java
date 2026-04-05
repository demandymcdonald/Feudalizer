package com.base.timeline.change.condition.nullify;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineChange;
import com.base.condition.Condition;

public abstract class NullifyCondition<T extends DateMutableEntity<?>> extends Condition<NullifyResult, DMEReference<? extends T>, TimelineChange<? extends T>, TimelineChange<?>> {

    public NullifyCondition(String id) {
        super(id);
    }
}
