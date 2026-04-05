package com.base.timeline.sandbox.check;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.error.SandboxCode;
import com.base.timeline.sandbox.core.Sandbox;
import com.base.timeline.state.TimelineState;

import java.time.LocalDate;

public abstract class SandboxFunction<T extends DateMutableEntity<T>>{
    TimelineState<T> state;
    public abstract void onComplete(Sandbox<T> sandbox, LocalDate endDate, SandboxCode code, DMEReference<T> entity, TimelineChange<? super T> newChange);
    public final SandboxCode cycle(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<? super T> existingChange){
        if(state != this.state){
            this.state = state;
            return onCycle(sandbox,entity,state,newChange,existingChange,false);
        } else {
            return onCycle(sandbox,entity,state,newChange,existingChange,true);
        }
    };
    protected abstract SandboxCode onCycle(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<? super T> existingChange, boolean seenBefore);
    public abstract void onStep(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, boolean isFirstCycle);
}
