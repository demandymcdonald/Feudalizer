package com.base.timeline.state;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.TimelineObject;

public abstract class abstractTimelineState<T extends DateMutableEntity<T>> extends TimelineObject<T>  {

    protected abstractTimelineState(DMEReference<T> owner) {
        super(owner);
    }


}
