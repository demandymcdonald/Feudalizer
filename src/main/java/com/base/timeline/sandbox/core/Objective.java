package com.base.timeline.sandbox.core;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineChange;

import java.time.LocalDate;

public record  Objective<T extends DateMutableEntity<T>> (DMEReference<T> subject, LocalDate start, TimelineChange<T> change) {
    public Objective(DMEReference<T> subject, TimelineChange<T> change){
        this(subject, change.getStart(), change);
    }
    public static <T extends DateMutableEntity<T>> Objective<T> build(DMEReference<? extends T> subject, TimelineChange<T> change){
       return new Objective<>((DMEReference<T>) subject, change);
    }
    public LocalDate getStart(){
        return change.getStart();
    }
    public LocalDate getEnd(){
        return change.getEnd();
    }

}
