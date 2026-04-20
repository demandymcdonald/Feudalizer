package com.base.timeline.change.multi;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.timeline.change.multi.wrapper.TLMap;
import com.base.timeline.change.multi.wrapper.TLSet;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.utilities.id.Identifiable;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
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
    public abstract TLSet<K> getRuntimeSet();
    public abstract void setRuntimeSet(TLSet<K> set);
    @Override
    public final void apply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
        super.apply(entity, currentState);
        setRuntimeSet(new TLSet<>(getFullMap()));
    }
    protected static <V extends Identifiable<I>,I> Map<V,Boolean> setToMap(Set<V> set){
        Map<V,Boolean> toReturn = new HashMap<>();
        for (V v : set) {
            toReturn.put(v,true);
        }
        return toReturn;
    }
}
