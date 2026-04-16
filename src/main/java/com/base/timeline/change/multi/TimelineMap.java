package com.base.timeline.change.multi;

import com.Global;
import com.base.DateMutableEntity;
import com.google.common.collect.Maps;
import com.utilities.id.Identifiable;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;


public class TimelineMap<K extends Identifiable<?>,V,T extends DateMutableEntity<T>> {
    private final TimelineMultiChange<?,K,V,?,T> change;
    public TimelineMap(TimelineMultiChange<?,K,V,?,T> change) {
        this.change = change;
    }
    public V get(K key) {
        return change.internalGetFull().get(key);
    }
    public Set<K> getKeys() {
        return new HashSet<>(change.internalGetFull().keySet());
    }
    public Set<V> getValues() {
        return new HashSet<>(change.internalGetFull().values());
    }
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K,V>> entries = new HashSet<>();
        for (Map.Entry<K,V> entry : change.internalGetFull().entrySet()) {
            entries.add(Maps.immutableEntry(entry.getKey(), entry.getValue()));
        }
        return entries;
    }
    public Map<K,V> getWhere(Predicate<K> predicate) {
        return getWhere(((k, v) ->  predicate.test(k)));
    }
    public Map<K,V> getWhere(BiPredicate<K,V> predicate) {
        Map<K,V> map = change.internalGetFull();
        Map<K,V> toReturn = new HashMap<>();
        for (Map.Entry<K,V> entry : map.entrySet()) {
            if (predicate.test(entry.getKey(), entry.getValue())) {
                toReturn.put(entry.getKey(),entry.getValue());
            }
        }
        return toReturn;
    }
    public void put(K key, V value) {
        change.addChange(Global.getSandboxHandler() == null,Pair.of(key,value));
    }
    public void put(boolean doSandbox, K key, V value) {
        change.addChange(doSandbox,Pair.of(key,value));
    }
    public boolean containsKey(K key) {
        return change.internalGetFull().containsKey(key);
    }
    public boolean containsValue(V value) {
        return change.internalGetFull().containsValue(value);
    }
    public void putAll(Map<K,V> map) {
        change.addChange(Global.getSandboxHandler() == null,map);
    }
    public void putAll(boolean doSandbox, Map<K,V> map) {
        change.addChange(doSandbox,map);
    }
    public void remove(TimelineMultiChange.WipeType type, K... key) {
        change.removeEntry(type, key);
    }
    public int size(){
        return change.internalGetFull().size();
    }
    public void addListener(TimelineMultiChange.Listener<K,V> listener) {
        change.addListener(listener);
    }
    public void setChanged(K... key){
        change.setChanged(key);
    }
}
