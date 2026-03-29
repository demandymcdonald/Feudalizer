package com.base.timeline.change.conditions;

import com.base.DateMutableEntity;
import com.simulation.title.Title;
import org.apache.commons.lang3.function.TriFunction;

import java.util.function.BiFunction;

public abstract class DMECondition<A extends DMECondition<A,T,U,V>, T extends DateMutableEntity<T>, U extends DateMutableEntity<U>, V extends DateMutableEntity<V>> implements iCondition {
    private final String id;
    private final TriFunction<T,U,V, DMEResult<T,U,V>> function;

    protected DMECondition(String id, TriFunction<T, U, V, DMEResult<T, U, V>> function) {
        this.id = id;
        this.function = function;
    }
    public abstract String getText();
    public DMEResult<T,U,V> check(T thisChange, U checkAgainst, V sidecar){
        return function.apply(thisChange,checkAgainst,sidecar);
    }
    @Override
    public String getCode() {
        return id;
    }
}
