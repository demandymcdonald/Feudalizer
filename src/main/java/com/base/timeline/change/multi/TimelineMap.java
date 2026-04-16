package com.base.timeline.change.multi;

import com.Global;
import com.base.DateMutableEntity;
import com.google.common.collect.ForwardingMap;
import com.utilities.id.Identifiable;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.BiFunction;

import static com.base.timeline.change.multi.TimelineMap.Listener.EMPTY;

public class TimelineMap<K extends Identifiable<?>,V,T extends DateMutableEntity<T>> {
    TimelineMultiChange<?,K,V,?,T> change;
    private Listener<K,V> listener = (Listener<K, V>) EMPTY;
    public TimelineMap(TimelineMultiChange<?,K,V,?,T> change) {
        this.change = change;
    }
    public V get(K key) {
        V value = change.internalGetFull().get(key);
        listener.onMapGet(key,value);
        return value;
    }
    public void put(K key, V value) {
        change.addChange(Global.getSandboxHandler() != null,Pair.of(key,value));
    }
    public void putAll(Map<K,V> map) {
        change.addChange(map);
    }
    public void setChanged(K... key){
        change.setChanged(key);
    }
    
    private static <M extends TimelineMultiChange<M,K,V,I,T>, K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>> void cascade(TimelineMultiChange<?,?,?,?,?> change, Map<?,TimelineMultiChange.ChangeType> changes){
        M m = (M) change;
        Map<K, TimelineMultiChange.ChangeType> finalChanges = (Map<K, TimelineMultiChange.ChangeType>) changes;
        m.cascadeInvalidate(m,finalChanges);
    }



}
