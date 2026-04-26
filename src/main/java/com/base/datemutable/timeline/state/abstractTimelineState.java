package com.base.datemutable.timeline.state;

import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.TimelineObject;

public abstract class abstractTimelineState<T extends DateMutableEntity<T>> extends TimelineObject<T>  {

    protected abstractTimelineState(DMEReference<T> owner) {
        super(owner);
    }


}
