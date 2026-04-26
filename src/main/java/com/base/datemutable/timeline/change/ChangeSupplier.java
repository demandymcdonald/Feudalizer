package com.base.datemutable.timeline.change;

import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;

import java.time.LocalDate;

public abstract class ChangeSupplier<T extends DateMutableEntity<T>,C extends TimelineChange<? super T>> {


    public abstract C supply(LocalDate date, DMEReference<T> subject);
}
