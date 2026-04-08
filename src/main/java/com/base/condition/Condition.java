package com.base.condition;

import com.base.timeline.change.TimelineChange;
import org.apache.commons.lang3.function.TriFunction;

import java.util.List;
import java.util.Optional;


/**
 * Represents a condition used to evaluate whether specific timeline changes are valid
 * or applicable based on given criteria. A condition is defined by a unique identifier
 * and a functional interface that performs the evaluation logic.
 *
 * @param <T> the type of {@link ConditionResult} the condition evaluates to, specifying
 *            the result of the condition's execution.
 * The evaluation logic is implemented through a {@link TriFunction} that takes the following inputs:
 * - The first parameter is the sandbox subject, represented by {@link TimelineChange<?>}.
 * - The second parameter is the existing variable to compare against, also represented by {@link TimelineChange<?>}.
 * - The third parameter is the {@link Sidecar} object determined by the subclass, which can be used
 *   to include precomputed or subject data, such as title sidecars containing prebuilt DME references.
 */
public abstract class Condition<R extends ConditionResult, A,B,C> implements iCondition{
        private final String id;
    public enum ShouldRun {
        ONCE_PER_STATE,
        ONCE_PER_ENTITY,
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




