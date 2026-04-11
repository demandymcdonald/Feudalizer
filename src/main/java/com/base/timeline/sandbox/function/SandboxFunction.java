package com.base.timeline.sandbox.function;

import com.base.DateMutableEntity;
import com.base.condition.Condition;
import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.error.SandboxCode;
import com.base.timeline.sandbox.core.Sandbox;
import com.base.timeline.state.TimelineState;
import com.google.common.collect.ImmutableList;

import java.time.LocalDate;
import java.util.List;

import static com.base.condition.Condition.ShouldRun.*;

public abstract class SandboxFunction<T extends DateMutableEntity<T>>{
    boolean first_entity = true;
    boolean first_state = true;
    private TimelineState<T> state;
    private static final List<Condition.ShouldRun> DEFAULT_SHOULD_RUN = ImmutableList.of(ONCE_PER_STATE,ONCE_PER_ENTITY,WHOLE_STATE_PER_ENTITY,ONCE_PER_CHANGE);
    private static final List<Condition.ShouldRun> DEFAULT_SHOULD_FIRST_STATE = ImmutableList.of(WHOLE_STATE_PER_ENTITY,ONCE_PER_CHANGE);
    private static final List<Condition.ShouldRun> DEFAULT_SHOULD_STATE = ImmutableList.of(ONCE_PER_STATE,ONCE_PER_CHANGE);
    private static final List<Condition.ShouldRun> DEFAULT_SHOULD_ENTITY = ImmutableList.of(ONCE_PER_CHANGE);
    protected abstract void onComplete(Sandbox<T> sandbox, LocalDate endDate, SandboxCode code, DMEReference<T> entity, TimelineChange<? super T> newChange);
    public final SandboxCode cycle(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<? super T> existingChange){
        List<Condition.ShouldRun> shouldRun;
        if(state != this.state){
            this.state = state;
            if (!first_entity && !first_state){
                shouldRun = DEFAULT_SHOULD_STATE;
            } else if(first_entity){
                first_entity = false;
                shouldRun = DEFAULT_SHOULD_RUN;
            } else {
                first_state = false;
                shouldRun = DEFAULT_SHOULD_STATE;
            }
        } else if (first_state){
            shouldRun = DEFAULT_SHOULD_FIRST_STATE;
        }else {
            shouldRun = DEFAULT_SHOULD_ENTITY;
        }
        return onCycle(sandbox,entity,state,newChange,existingChange,shouldRun);
    };
    public final void complete(Sandbox<T> sandbox, SandboxCode code, DMEReference<T> entity, TimelineChange<? super T> newChange){
        onComplete(sandbox,newChange.getEnd(),code,entity,newChange);
        newChange.complete(sandbox,this,code);
    }
    protected abstract SandboxCode onCycle(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<? super T> existingChange, List<Condition.ShouldRun> shouldRun);
    public abstract void onStep(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, boolean isFirstCycle);
}
