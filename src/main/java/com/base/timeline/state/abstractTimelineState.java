package com.base.timeline.state;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.Timeline;
import com.base.timeline.TimelineObject;
import com.base.timeline.change.ChangeID;
import com.base.timeline.change.TimelineChange;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class abstractTimelineState<T extends DateMutableEntity<T>> extends TimelineObject<T>  {

    protected abstractTimelineState(DMEReference<T> owner) {
        super(owner);
    }


}
