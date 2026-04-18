package com.base.timeline.change.multi;

import com.base.DateMutableEntity;
import com.base.condition.Condition;
import com.base.timeline.error.StateError;
import com.utilities.id.Identifiable;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

public abstract class MultiCondition<M extends TLMultiChange<M,K,V,I,T>, K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>> {
    public final Optional<StateError> check(TLMultiChange.Delta change, M newChange, List<Pair<K,V>> newEntries,
                                            M curChange, List<Pair<K,V>> curEntries){
        //TODO think about listener/subclass stuff here
        return doCheck(change,newChange, newEntries, curChange, curEntries);
    }
    protected abstract Optional<StateError> doCheck(TLMultiChange.Delta change, M newChange, List<Pair<K,V>> newEntries,
                                                    M curChange, List<Pair<K,V>> curEntries);
    public Condition.ShouldRun shouldRun(){
        return Condition.ShouldRun.ONCE_PER_CHANGE;
    }
}
