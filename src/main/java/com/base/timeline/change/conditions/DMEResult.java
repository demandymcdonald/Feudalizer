package com.base.timeline.change.conditions;

import com.base.DateMutableEntity;
import com.base.flags.StateError;
import com.base.reference.DMEReference;
import com.simulation.people.BookCharacter;
import com.simulation.title.Title;

import java.util.function.Supplier;

public record DMEResult<T extends DateMutableEntity<T,?>, U extends DateMutableEntity<U,?>, V extends DateMutableEntity<V,?>>(boolean canHold, DMEReference<T> subject, DMEReference<U> firstAdditional, DMEReference<V> secondAdditional, String reason, Supplier<StateError> resolution) implements ConditionResult {


}

