package com.base.timeline.sandbox.core;

import com.Global;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.sandbox.check.SandboxCheck;

import java.time.LocalDate;

public record  Objective<T extends DateMutableEntity<T>> (DMEReference<T> subject, Global.TimeDirection start, TimelineChange<? super T> change, SandboxCheck<T>... toCheck) {

    public static <T extends DateMutableEntity<T>> Objective<T> build(DMEReference<? extends T> subject, Global.TimeDirection direction, TimelineChange<? super T> change, SandboxCheck<T>... toCheck){
       return new Objective<>((DMEReference<T>) subject, direction,change,toCheck);
    }
    public LocalDate getStart(){
        return change.getStart();
    }
    public LocalDate getEnd(){
        return change.getEnd();
    }

}
