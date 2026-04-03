package com.base.timeline.change;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.changes.TimelineChange;

import java.time.LocalDate;

public abstract class ChangeSupplier<T extends DateMutableEntity<T>,C extends TimelineChange<? super T>> {


    public abstract C supply(LocalDate date, DMEReference<T> subject);
}
