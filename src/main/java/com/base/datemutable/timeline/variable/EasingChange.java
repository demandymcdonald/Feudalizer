package com.base.datemutable.timeline.variable;

import com.base.datemutable.DateMutableEntity;
import com.base.datemutable.timeline.change.TimelineChange;

import java.util.function.Predicate;

public interface EasingChange<E extends EasingVariable<E,C,? extends T>,C extends TimelineChange<? super T> & EasingChange<E,C,T>, T extends DateMutableEntity<T>> {
    E getEasingVariable(Predicate<E> matching);
    C getNext();
    default E getEasingVariableCast(Predicate<?> matching){
        return getEasingVariable(((Predicate<E>) matching));
    }

}
