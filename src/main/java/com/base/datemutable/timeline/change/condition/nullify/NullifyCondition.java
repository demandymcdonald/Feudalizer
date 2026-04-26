package com.base.datemutable.timeline.change.condition.nullify;

import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.TimelineChange;
import com.base.condition.Condition;

public abstract class NullifyCondition<T extends DateMutableEntity<?>> extends Condition<NullifyResult, DMEReference<? extends T>, TimelineChange<? extends T>, TimelineChange<?>> {

    public NullifyCondition(String id) {
        super(id);
    }
}
