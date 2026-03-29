package com.base.timeline.change.conditions;

import com.base.timeline.change.TimelineChange;
import org.apache.commons.lang3.function.TriFunction;

import java.util.Optional;


/**
 * Represents a condition used to evaluate whether specific timeline changes are valid
 * or applicable based on given criteria. A condition is defined by a unique identifier
 * and a functional interface that performs the evaluation logic.
 *
 * @param <T> the type of {@link ConditionResult} the condition evaluates to, specifying
 *            the result of the condition's execution.
 * @param <C> the type of {@link Sidecar} data associated with the condition, which provides
 *            supplemental information for the condition's evaluation logic.
 *
 * The evaluation logic is implemented through a {@link TriFunction} that takes the following inputs:
 * - The first parameter is the sandbox subject, represented by {@link TimelineChange<?>}.
 * - The second parameter is the existing variable to compare against, also represented by {@link TimelineChange<?>}.
 * - The third parameter is the {@link Sidecar} object determined by the subclass, which can be used
 *   to include precomputed or subject data, such as title sidecars containing prebuilt DME references.
 */
public record Condition<T extends ConditionResult, C extends Sidecar>(String id, boolean shouldInvalidate,TriFunction<TimelineChange<?>,TimelineChange<?>,C, Optional<T>> condition) implements iCondition{
    //For the trifunction, the first var is always the sandbox subject, the second var is always the existing variable. Sidecar is determined by subclass, for example: title has a title sidecar that contains the DME references prebuilt to save compute. include in documentation

    /**
     * Evaluates the provided timeline changes against a specified condition and returns
     * the result encapsulated within an {@code Optional<T>}.
     *
     * @param thisChange the timeline change object serving as the sandbox subject to be evaluated.
     * @param checkAgainst the timeline change object used as the comparison subject in the evaluation.
     * @param sidecar a sidecar instance providing supplemental data to assist in the condition's evaluation logic.
     * @return an {@code Optional<T>} containing the result of the condition's evaluation,
     *         or an empty {@code Optional} if no result is produced.
     */
    public Optional<T> check(TimelineChange<?> thisChange, TimelineChange<?> checkAgainst, C sidecar){
        return condition.apply(thisChange,checkAgainst,sidecar);
    }

    @Override
    public String getCode() {
        return id;
    }
}




