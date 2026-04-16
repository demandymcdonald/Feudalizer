package com.base.timeline.change.multi;

import com.Global;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.google.common.collect.Maps;
import com.utilities.ThreadManager;
import com.utilities.id.Identifiable;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;


public class TimelineMap<K extends Identifiable<?>,V,T extends DateMutableEntity<T>> {
    private class ChangeContainer {
        private final TimelineMultiChange<?,K,V,?,T> loadedChange;

        private ChangeContainer(TimelineMultiChange<?,K,V,?,T> loadedChange){
            this.loadedChange = loadedChange;
        }
        public TimelineMultiChange<?,K,V,?,T> read(){
            return loadedChange;
        }
        public TimelineMultiChange<?,K,V,?,T> write(){
            if(Global.getDate() != loadedChange.getStart()){
                DMEReference<? extends T> t = loadedChange.getOwner();
                TimelineMultiChange<?,K,V,?,T> newChange = loadedChange.getEmptyChange(t,Global.getDate());
                t.get().getTimeline().internalAddChange(newChange);
                return newChange;
            } else{
                return loadedChange;
            }
        }
    }
    private final boolean isMain = ThreadManager.isMainThread();
    private final ChangeContainer change;
    public TimelineMap(TimelineMultiChange<?,K,V,?,T> change) {
        this.change = new ChangeContainer(change);
    }
    public V get(K key) {
        return change.read().internalGetFull().get(key);
    }
    public V getOrDefault(K key, Supplier<V> defaultValue) {
        return getOrDefault(isMain,false,key,defaultValue);
    }
    public V getOrDefault(boolean wipeForward, K key, Supplier<V> defaultValue) {
        return getOrDefault(isMain,wipeForward,key,defaultValue);
    }
    public V getOrDefault(boolean doSandbox, boolean wipeForward, K key, Supplier<V> defaultValue) {
        if(containsKey(key)){
            return get(key);
        } else {
            V v = defaultValue.get();
            put(doSandbox,wipeForward,key,v);
            return v;
        }
    }
    public Set<K> getKeys() {
        return new HashSet<>(change.read().internalGetFull().keySet());
    }
    public Set<V> getValues() {
        return new HashSet<>(change.read().internalGetFull().values());
    }
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K,V>> entries = new HashSet<>();
        for (Map.Entry<K,V> entry : change.read().internalGetFull().entrySet()) {
            entries.add(Maps.immutableEntry(entry.getKey(), entry.getValue()));
        }
        return entries;
    }
    public Map<K,V> getWhere(Predicate<K> predicate) {
        return getWhere(((k, v) ->  predicate.test(k)));
    }
    public Map<K,V> getWhere(BiPredicate<K,V> predicate) {
        Map<K,V> map = change.read().internalGetFull();
        Map<K,V> toReturn = new HashMap<>();
        for (Map.Entry<K,V> entry : map.entrySet()) {
            if (predicate.test(entry.getKey(), entry.getValue())) {
                toReturn.put(entry.getKey(),entry.getValue());
            }
        }
        return toReturn;
    }
    public void put(K key, V value) {
        change.write().addChange(isMain,false,Pair.of(key,value));
    }
    public void put(boolean wipeForward,K key, V value) {
        change.write().addChange(isMain,wipeForward,Pair.of(key,value));
    }
    public void put(boolean doSandbox,boolean wipeForward, K key, V value) {
        change.write().addChange(doSandbox,wipeForward,Pair.of(key,value));
    }
    public boolean containsKey(K key) {
        return change.read().internalGetFull().containsKey(key);
    }
    public boolean containsValue(V value) {
        return change.read().internalGetFull().containsValue(value);
    }
    public void putAll(boolean wipeForward, Pair<K,V>... pairs) {
        change.write().addChange(isMain,wipeForward,pairs);
    }
    public void putAll(boolean doSandbox, boolean wipeForward, Pair<K,V>... pairs) {
        change.write().addChange(doSandbox,wipeForward,pairs);
    }
    public void putAll(boolean wipeForward, Map<K,V> map) {
        change.write().addChange(isMain,wipeForward,map);
    }
    public void putAll(boolean doSandbox,boolean wipeForward, Map<K,V> map) {
        change.write().addChange(doSandbox,wipeForward,map);
    }
    public void remove(TimelineMultiChange.WipeType type, K... key) {
        change.write().removeEntry(isMain, type, key);
    }
    public void remove(boolean doSandbox, TimelineMultiChange.WipeType type, K... key) {
        change.write().removeEntry(doSandbox, type, key);
    }
    public int size(){
        return change.read().internalGetFull().size();
    }
    public void addListener(TimelineMultiChange.Listener<K,V> listener) {
        change.read().addListener(listener);
    }
    public void setChanged(TimelineMultiChange.ChangeEntry type, K... key){
        change.write().setChanged(isMain,type,key);
    }
    public void setChanged(boolean doSandbox, TimelineMultiChange.ChangeEntry type, K... key){
        change.write().setChanged(doSandbox,type,key);
    }
}
