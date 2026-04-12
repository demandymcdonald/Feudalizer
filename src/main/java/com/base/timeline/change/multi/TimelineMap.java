package com.base.timeline.change.multi;

import com.base.DateMutableEntity;
import com.utilities.id.Identifiable;

import java.util.*;
import java.util.function.BiFunction;

public class TimelineMap<K extends Identifiable<?>,V,T extends DateMutableEntity<T>> extends HashMap<K,V> {
    TimelineMultiChange<?,K,V,?,T> change;
    public TimelineMap(TimelineMultiChange<?,K,V,?,T> change) {
        this.change = change;
    }
    public TimelineMap(TimelineMultiChange<?,K,V,?,T> change, Map<K,V> initial) {
        this.change = change;
        change.pauseCacheChecks.set(true);
        try {
            putAll(initial);
        } catch (Exception e) {
            change.logger().error("Failed to load initial map: ",e);
        }finally {
            change.pauseCacheChecks.set(false);
        }
    }

    public V put(K key, V value) {
        boolean contained = containsKey(key);
        V v = super.put(key, value);
        if (!contained) {
            cascade( change, change.buildChangeTypes(Collections.singletonList(key), TimelineMultiChange.ChangeType.ADD));
        } else {
            cascade( change, change.buildChangeTypes(Collections.singletonList(key), TimelineMultiChange.ChangeType.MODIFY_VALUE));
        }
        return v;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        List<K> toAmend = new ArrayList<>();
        List<K> toAdd = new ArrayList<>();
        for (K key : m.keySet()) {
            if (containsKey(key)) {
                toAmend.add(key);
            } else {
                toAdd.add(key);
            }
        }
        super.putAll(m);
        cascade( change, change.buildChangeTypes(toAmend, TimelineMultiChange.ChangeType.MODIFY_VALUE));
        cascade( change, change.buildChangeTypes(toAdd, TimelineMultiChange.ChangeType.ADD));
    }

    @Override
    public V remove(Object key) {
        boolean contains = containsKey(key);
        V v = super.remove(key);
        if (contains) {
            cascade( change, change.buildChangeTypes(Collections.singletonList((K) key), TimelineMultiChange.ChangeType.REMOVE));
        }
        return v;
    }

    @Override
    public boolean remove(Object key, Object value) {
        boolean contains = containsKey(key);
        boolean tr = super.remove(key, value);
        if (contains) {
            cascade( change, change.buildChangeTypes(Collections.singletonList((K) key), TimelineMultiChange.ChangeType.REMOVE));
        }
        return tr;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        boolean contains = containsKey(key);
        boolean d = super.replace(key, oldValue, newValue);
        if (contains) {
            cascade( change, change.buildChangeTypes(Collections.singletonList((K) key), TimelineMultiChange.ChangeType.MODIFY_VALUE));
        } else {
            cascade( change, change.buildChangeTypes(Collections.singletonList((K) key), TimelineMultiChange.ChangeType.ADD));
        }
        return d;
    }

    @Override
    public V replace(K key, V value) {
        boolean contains = containsKey(key);
        V tr = super.replace(key, value);
        if (contains) {
            cascade( change, change.buildChangeTypes(Collections.singletonList((K) key), TimelineMultiChange.ChangeType.MODIFY_VALUE));
        } else {
            cascade( change, change.buildChangeTypes(Collections.singletonList((K) key), TimelineMultiChange.ChangeType.ADD));
        }
        return tr;
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        List<K> toAmend = new ArrayList<>();
        List<K> toAdd = new ArrayList<>();
        for (Map.Entry<K, V> e : entrySet()) {
            if (containsKey(e.getKey())) {
                toAmend.add(e.getKey());
            } else {
                toAdd.add(e.getKey());
            }
        }
        super.replaceAll(function);
        cascade( change, change.buildChangeTypes(toAmend, TimelineMultiChange.ChangeType.MODIFY_VALUE));
        cascade( change, change.buildChangeTypes(toAdd, TimelineMultiChange.ChangeType.ADD));
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
