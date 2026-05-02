package com.base.datemutable.timeline.change.multi;

import com.base.datemutable.DateMutableEntity;
import com.base.datemutable.timeline.change.TLChangeRegistry;
import com.base.datemutable.timeline.change.multi.condition.MultiCondition;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.multi.wrapper.TLSet;
import com.base.datemutable.timeline.state.TimelineState;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.utilities.id.Identifiable;
import org.geotools.api.filter.Id;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class TimelineSetChange<M extends TimelineSetChange<M, K,I,T>, K extends Identifiable<I>,I,T extends DateMutableEntity<T>> extends TLMultiChange<M, K,Boolean,I,T>{
    protected TimelineSetChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }
    protected TimelineSetChange(DMEReference<? extends T> owner, LocalDate date, Set<K> initial) {
        super(owner, date, setToMap(initial));
    }
    @Override
    public boolean hasEndingChanges() {
        return true;
    }

    @Override
    protected final JsonElement vSerialize(Boolean aBoolean) {
        return new JsonPrimitive(aBoolean);
    }

    @Override
    protected final Boolean vDeserialize(JsonElement o) {
        return true;
    }
    public final TLSet<K> getRuntimeSet(){
        return getRuntimeSet(getOwner().get());
    };
    @Override
    public M getEmptyChange(DMEReference<? extends T> owner, LocalDate date) {
        return TLChangeRegistry.deserializeChange(this.getClass(),owner,date);
    }
    protected abstract TLSet<K> getRuntimeSet(T t);
    public abstract void setRuntimeSet(T t, TLSet<K> set);
    @Override
    public final void apply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
        super.apply(entity, currentState);
        setRuntimeSet(getOwner().get(),new TLSet<>(getFullMap()));
    }
    protected static <V extends Identifiable<I>,I> Map<V,Boolean> setToMap(Set<V> set){
        Map<V,Boolean> toReturn = new HashMap<>();
        for (V v : set) {
            toReturn.put(v,true);
        }
        return toReturn;
    }

    public static abstract class SetCondition<M extends TimelineSetChange<M,K,I,T>,K extends Identifiable<I>,I,T extends DateMutableEntity<T>>
            extends MultiCondition<M,K,Boolean,I,T>{
    }
}
