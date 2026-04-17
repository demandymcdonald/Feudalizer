package com.base.timeline.change.multi;

import com.Global.*;
import com.google.common.collect.Maps;
import com.utilities.ThreadManager;
import com.utilities.id.Identifiable;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class TimelineMap<K extends Identifiable<?>,V> {
    private final MiddlemanMap<?,K,V,?,?> middleman;
    private final boolean isMain = ThreadManager.isMainThread();
    public TimelineMap(MiddlemanMap<?, K, V, ?, ?> middleman) {
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
    public void remove(TLMultiChange.WipeType type, K... key) {
        middleman.remove(type,key);
    }
    public void remove(boolean doSandbox, TLMultiChange.WipeType type, K... key) {
        middleman.remove(doSandbox,type,key);
    }
    public int size(){
        return middleman.size();
    }
    public void addListener(TLMultiChange.Listener<K,V> listener) {
        middleman.addListener(listener);
    }
    public void setChanged(TLMultiChange.ChangeType type, Map<K, BiConsumer<K,V>> changes){
        setChanged(isMain,false,type,changes);
    }
    public void setChanged(boolean doPropagate, TLMultiChange.ChangeType type, Map<K, BiConsumer<K,V>> changes){
        setChanged(isMain,doPropagate,type,changes);
    }
    @SuppressWarnings("unchecked")
    public void setChanged(boolean doSandbox, boolean doWipe, TLMultiChange.ChangeType type, Map<K, BiConsumer<K,V>> changes){
        middleman.setChanged(doSandbox,doWipe,type,changes);
    }





}
