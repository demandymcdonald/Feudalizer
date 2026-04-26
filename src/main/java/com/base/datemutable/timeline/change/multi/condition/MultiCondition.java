package com.base.datemutable.timeline.change.multi.condition;

import com.base.datemutable.DateMutableEntity;
import com.base.condition.Condition;
import com.base.datemutable.timeline.change.multi.TLMultiChange;
import com.base.datemutable.timeline.change.multi.type.Delta;
import com.base.datemutable.timeline.error.StateError;
import com.utilities.id.Identifiable;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

public abstract class MultiCondition<M extends TLMultiChange<M,K,V,I,T>, K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>> {
    public final Optional<StateError> check(Delta change, M newChange, List<Pair<K,V>> newEntries,
                                            M curChange, List<Pair<K,V>> curEntries){
        //TODO think about listener/subclass stuff here
        return doCheck(change,newChange, newEntries, curChange, curEntries);
    }
    protected abstract Optional<StateError> doCheck(Delta change, M newChange, List<Pair<K,V>> newEntries,
                                                    M curChange, List<Pair<K,V>> curEntries);
    public Condition.ShouldRun shouldRun(){
        return Condition.ShouldRun.ONCE_PER_CHANGE;
    }
}
