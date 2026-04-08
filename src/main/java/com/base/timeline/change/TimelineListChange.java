package com.base.timeline.change;

import com.Global.*;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;

import java.time.LocalDate;
import java.util.Collection;

public abstract class TimelineListChange<T extends DateMutableEntity<?>,C extends Collection<R>,R> extends TimelineChange<T>{

    protected TimelineListChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }
}
