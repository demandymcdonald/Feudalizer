package com.base.datemutable.timeline.change.multi;

import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.multi.wrapper.TLMap;
import com.base.datemutable.timeline.state.TimelineState;
import com.utilities.id.Identifiable;

import java.time.LocalDate;
import java.util.*;

public abstract class TimelineMapChange<M extends TimelineMapChange<M,K,V,I,T>,K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>> extends TLMultiChange<M,K,V,I,T> {

    public TimelineMapChange(DMEReference<? extends T> owner, LocalDate date, Map<K, V> initial) {
        super(owner, date, initial);
    }

    protected TimelineMapChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }

    public abstract void setRuntimeMap(TLMap<K,V> map);
    public abstract TLMap<K,V> getRuntimeMap();
    @Override
    public final void apply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
        super.apply(entity, currentState);
        setRuntimeMap(new TLMap<>(getFullMap()));
    }
}
