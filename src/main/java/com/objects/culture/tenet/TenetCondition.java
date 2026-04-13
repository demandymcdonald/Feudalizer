package com.objects.culture.tenet;

import com.base.DateMutableEntity;
import com.base.condition.Condition;
import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.error.StateError;
import com.objects.culture.tenet.types.MutableTenet;


public abstract class TenetCondition<T extends TimelineChange<D>,D extends DateMutableEntity<D>, CT extends MutableTenet<CT,?>>
        extends Condition<StateError, CT, T, DMEReference<? extends D>> {
    public TenetCondition(String id) {
        super(id);
    }
}
