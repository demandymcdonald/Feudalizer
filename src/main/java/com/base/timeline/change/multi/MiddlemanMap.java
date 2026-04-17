package com.base.timeline.change.multi;

import com.Global;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.google.common.collect.Maps;
import com.utilities.ThreadManager;
import com.utilities.id.Identifiable;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;


public class MiddlemanMap<M extends TLMultiChange<M,K,V,I,T>,K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>> {
    private class ChangeContainer {
        private final M loadedChange;

        private ChangeContainer(M loadedChange){
            this.loadedChange = loadedChange;
        }
        public M read(){
            return loadedChange;
        }
        public M write(){
            if(Global.getDate() != loadedChange.getStart()){
                DMEReference<? extends T> t = loadedChange.getOwner();
                M newChange = loadedChange.getEmptyChange(t,Global.getDate());
                t.get().getTimeline().internalAddChange(newChange);
                return newChange;
            } else{
                return loadedChange;
            }
        }
    }
    private final boolean isMain = ThreadManager.isMainThread();
    private final ChangeContainer change;
    protected MiddlemanMap(M change) {
        this.change = new ChangeContainer(change);
    }
    protected V get(K key) {
        return change.read().get(key);
    }
    protected V getOrCompute(K key, Supplier<V> defaultValue) {
        return getOrCompute(isMain,false,key,defaultValue);
    }
    protected V getOrCompute(boolean wipeForward, K key, Supplier<V> defaultValue) {
        return getOrCompute(isMain,wipeForward,key,defaultValue);
    }
    protected V getOrCompute(boolean doSandbox, boolean wipeForward, K key, Supplier<V> defaultValue) {
        if(containsKey(key)){
            return get(key);
        } else {
            V v = defaultValue.get();
            put(doSandbox,wipeForward,key,v);
            return v;
        }
    }
    protected Set<K> getKeys() {
        return new HashSet<>(change.read().internalGetFull().keySet());
    }
    protected Set<V> getValues() {
        return new HashSet<>(change.read().internalGetFull().values());
    }
    protected Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K,V>> entries = new HashSet<>();
        for (Map.Entry<K,V> entry : change.read().internalGetFull().entrySet()) {
            entries.add(Maps.immutableEntry(entry.getKey(), entry.getValue()));
        }
        return entries;
    }
    protected Map<K,V> getWhere(Predicate<K> predicate) {
        return getWhere(((k, v) ->  predicate.test(k)));
    }
    protected Map<K,V> getWhere(BiPredicate<K,V> predicate) {
        Map<K,V> map = change.read().internalGetFull();
        Map<K,V> toReturn = new HashMap<>();
        for (Map.Entry<K,V> entry : map.entrySet()) {
            if (predicate.test(entry.getKey(), entry.getValue())) {
                toReturn.put(entry.getKey(),entry.getValue());
            }
        }
        return toReturn;
    }
    protected int getSizeWhere(BiPredicate<K,V> predicate) {
        return getWhere(predicate).size();
    }
    protected void put(K key, V value) {
        change.write().put(isMain,false,Pair.of(key,value));
    }
    protected void put(boolean wipeForward,K key, V value) {
        change.write().put(isMain,wipeForward,Pair.of(key,value));
    }
    protected void put(boolean doSandbox,boolean wipeForward, K key, V value) {
        change.write().put(doSandbox,wipeForward,Pair.of(key,value));
    }
    protected boolean containsKey(K key) {
        return change.read().internalGetFull().containsKey(key);
    }
    protected boolean containsValue(V value) {
        return change.read().internalGetFull().containsValue(value);
    }
    protected void putAll(boolean wipeForward, Pair<K,V>... pairs) {
        change.write().put(isMain,wipeForward,pairs);
    }
    protected void putAll(boolean doSandbox, boolean wipeForward, Pair<K,V>... pairs) {
        change.write().put(doSandbox,wipeForward,pairs);
    }
    protected void putAll(boolean wipeForward, Map<K,V> map) {
        change.write().put(isMain,wipeForward,map);
    }
    protected void putAll(boolean doSandbox,boolean wipeForward, Map<K,V> map) {
        change.write().put(doSandbox,wipeForward,map);
    }
    protected void remove(TLMultiChange.WipeType type, K... key) {
        change.write().remove(isMain, type, key);
    }
    protected void remove(boolean doSandbox, TLMultiChange.WipeType type, K... key) {
        change.write().remove(doSandbox, type, key);
    }
    protected int size(){
        return change.read().internalGetFull().size();
    }
    protected void addListener(TLMultiChange.Listener<K,V> listener) {
        change.read().addListener(listener);
    }
    protected void setChanged(TLMultiChange.ChangeType type, Map<K, BiConsumer<K,V>> changes){
        setChanged(isMain,false,type,changes);
    }
    protected void setChanged(boolean doPropagate, TLMultiChange.ChangeType type, Map<K, BiConsumer<K,V>> changes){
        setChanged(isMain,doPropagate,type,changes);
    }
    @SuppressWarnings("unchecked")
    protected void setChanged(boolean doSandbox, boolean doWipe, TLMultiChange.ChangeType type, Map<K, BiConsumer<K,V>> changes){
        List<TLMultiChange.ChangeContainer<M,K,V,I,T>> containers = new ArrayList<>();
        for(Map.Entry<K, BiConsumer<K,V>> entry : changes.entrySet()){
            containers.add(TLMultiChange.ChangeContainer.of((M) change.read(), entry.getValue(),entry.getKey()));
        }
        change.write().changed(doSandbox,doWipe,type,containers);
    }

}
