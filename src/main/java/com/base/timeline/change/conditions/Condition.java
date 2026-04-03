package com.base.timeline.change.conditions;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.changes.TimelineChange;
import org.apache.commons.lang3.function.TriFunction;

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
public abstract class Condition<R extends ConditionResult, T extends DateMutableEntity<T>> implements iCondition{
        private final String id;

    public Condition(String id) {
        this.id = id;
    }

    public static <R extends ConditionResult, T extends DateMutableEntity<T>> Optional<R> checkHelper(Condition<R,T> condition, DMEReference<? extends T> entity, TimelineChange<?> thisChange, TimelineChange<?> checkAgainst){
        return condition.doCheck(entity, (TimelineChange<T>) thisChange, checkAgainst);
    }

    @SuppressWarnings("unchecked")
    public final Optional<R> check(DMEReference<? extends T> entity, TimelineChange<? extends T> thisChange, TimelineChange<?> checkAgainst){
        return doCheck(entity, thisChange, checkAgainst);
    };

    protected abstract Optional<R> doCheck(DMEReference<? extends T> entity, TimelineChange<? extends T> thisChange, TimelineChange<?> checkAgainst);

    @Override
    public String getCode() {
        return id;
    }
}




