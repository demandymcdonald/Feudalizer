package com.base.timeline.change.multi.wrapper;

import com.base.timeline.change.multi.MiddlemanMap;
import com.base.timeline.change.multi.TLMultiChange;
import com.base.timeline.change.multi.type.ChangeType;
import com.base.timeline.change.multi.type.WipeType;
import com.utilities.ThreadManager;
import com.utilities.id.Identifiable;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class TLMap<K extends Identifiable<?>,V> {
    private final MiddlemanMap<?,K,V,?,?> middleman;
    private final boolean isMain = ThreadManager.isMainThread();
    public TLMap(MiddlemanMap<?, K, V, ?, ?> middleman) {
        this.middleman = middleman;
    }
    public V get(K key) {
        return middleman.get(key);
    }
    public V getOrCompute(K key, Supplier<V> defaultValue) {
        return getOrCompute(isMain,false,key,defaultValue);
    }
    public V getOrCompute(boolean wipeForward, K key, Supplier<V> defaultValue) {
        return getOrCompute(isMain,wipeForward,key,defaultValue);
    }
    public V getOrCompute(boolean doSandbox, boolean wipeForward, K key, Supplier<V> defaultValue) {
        return middleman.getOrCompute(doSandbox,wipeForward,key,defaultValue);
    }
    public Set<K> getKeys() {
        return middleman.getKeys();
    }
    public Set<V> getValues() {
        return middleman.getValues();
    }
    public Set<Map.Entry<K, V>> entrySet() {
        return middleman.entrySet();
    }
    public Map<K,V> getWhere(Predicate<K> predicate) {
        return middleman.getWhere(predicate);
    }
    public Map<K,V> getWhere(BiPredicate<K,V> predicate) {
        return middleman.getWhere(predicate);
    }
    public int getSizeWhere(BiPredicate<K,V> predicate) {
        return getWhere(predicate).size();
    }
    public void put(K key, V value) {
        middleman.put(key,value);
    }
    public void put(boolean wipeForward,K key, V value) {
        middleman.put(wipeForward,key,value);
    }
    public void put(boolean doSandbox,boolean wipeForward, K key, V value) {
        middleman.put(doSandbox,wipeForward,key,value);
    }
    public boolean containsKey(K key) {
        return middleman.containsKey(key);
    }
    public boolean containsValue(V value) {
        return middleman.containsValue(value);
    }
    public void putAll(boolean wipeForward, Pair<K,V>... pairs) {
        middleman.putAll(wipeForward,pairs);
    }
    public void putAll(boolean doSandbox, boolean wipeForward, Pair<K,V>... pairs) {
        middleman.putAll(doSandbox,wipeForward,pairs);
    }
    public void putAll(boolean wipeForward, Map<K,V> map) {
       middleman.putAll(wipeForward,map);
    }
    public void putAll(boolean doSandbox,boolean wipeForward, Map<K,V> map) {
        middleman.putAll(doSandbox,wipeForward,map);
    }
    public void remove(WipeType type, K... key) {
        middleman.remove(type,key);
    }
    public void remove(boolean doSandbox, WipeType type, K... key) {
        middleman.remove(doSandbox,type,key);
    }
    public int size(){
        return middleman.size();
    }
    public void addListener(TLMultiChange.Listener<K,V> listener) {
        middleman.addListener(listener);
    }
    public void setChanged(ChangeType type, Map<K, BiConsumer<K,V>> changes){
        setChanged(isMain,false,type,changes);
    }
    public void setChanged(boolean doPropagate, ChangeType type, Map<K, BiConsumer<K,V>> changes){
        setChanged(isMain,doPropagate,type,changes);
    }
    @SuppressWarnings("unchecked")
    public void setChanged(boolean doSandbox, boolean doWipe, ChangeType type, Map<K, BiConsumer<K,V>> changes){
        middleman.setChanged(doSandbox,doWipe,type,changes);
    }

    public void setChanged(boolean doPropagate, ChangeType type, K key, BiConsumer<K,V> consumer){
        setChanged(isMain,doPropagate,type,Map.of(key,consumer));
    }
    public Map<K,V> getMutableMap(){
        return new HashMap<>(middleman.getWhere((k,v) -> true));
    }


}
