package com.base.condition;

import com.base.timeline.change.TimelineChange;
import org.apache.commons.lang3.function.TriFunction;

import java.util.List;
import java.util.Optional;



public abstract class Condition<R extends ConditionResult, A,B,C> implements iCondition{
        private final String id;
    public enum ShouldRun {
        ONCE_PER_STATE,
        ONCE_PER_ENTITY,
        WHOLE_STATE_PER_ENTITY,
        ONCE_PER_CHANGE,
    }
    public Condition(String id) {
        this.id = id;
    }

    @SuppressWarnings("unchecked")
    public final Optional<R> check(A entity, B thisChange, C checkAgainst, List<ShouldRun> shouldRun){
        if (shouldRun.contains(whenToRun())){
            return doCheck(entity, thisChange, checkAgainst);
        }
        return Optional.empty();
    };

    protected abstract Optional<R> doCheck(A entity, B thisChange, C checkAgainst);

    @Override
    public String getCode() {
        return id;
    }
    public ShouldRun whenToRun(){
        return ShouldRun.ONCE_PER_CHANGE;
    }
}




