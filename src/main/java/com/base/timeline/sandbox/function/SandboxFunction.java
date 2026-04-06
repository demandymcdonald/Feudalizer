package com.base.timeline.sandbox.function;

import com.base.DateMutableEntity;
import com.base.condition.Condition;
import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.error.SandboxCode;
import com.base.timeline.sandbox.core.Sandbox;
import com.base.timeline.state.TimelineState;
import com.google.common.collect.HashMultimap;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.base.condition.Condition.ShouldRun.*;

public abstract class SandboxFunction<T extends DateMutableEntity<T>>{
    boolean first = true;
    private TimelineState<T> state;

    public abstract void onComplete(Sandbox<T> sandbox, LocalDate endDate, SandboxCode code, DMEReference<T> entity, TimelineChange<? super T> newChange);
    public final SandboxCode cycle(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<? super T> existingChange){
        List<Condition.ShouldRun> shouldRun;
        if(state != this.state){
            this.state = state;
            if (first){
                first = false;
                shouldRun = List.of(ONCE_PER_STATE,ONCE_PER_ENTITY,ONCE_PER_CHANGE);
            } else {
                shouldRun = List.of(ONCE_PER_STATE,ONCE_PER_ENTITY);
            }
        } else {
            shouldRun = List.of(ONCE_PER_CHANGE);
        }
        return onCycle(sandbox,entity,state,newChange,existingChange,shouldRun);
    };
    protected abstract SandboxCode onCycle(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<? super T> existingChange, List<Condition.ShouldRun> shouldRun);
    public abstract void onStep(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, boolean isFirstCycle);
}
