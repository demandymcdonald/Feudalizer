package com.base.timeline.change.multi;

import com.base.DateMutableEntity;
import com.utilities.id.Identifiable;

import java.util.*;
import java.util.function.BiFunction;

import static com.base.timeline.change.multi.TimelineMap.Listener.EMPTY;

public class TimelineMap_old<K extends Identifiable<?>,V,T extends DateMutableEntity<T>> extends HashMap<K,V> {
    TimelineMultiChange<?,K,V,?,T> change;
    private Listener<K,V> listener = (Listener<K, V>) EMPTY;
    public TimelineMap_old(TimelineMultiChange<?,K,V,?,T> change) {
        this.change = change;
    }
    public TimelineMap_old(TimelineMultiChange<?,K,V,?,T> change, Map<K,V> initial) {
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
        listener.onMapPut(key, value);
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
            listener.onMapPut(key, m.get(key));
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
        listener.onMapRemove((K) key, v);
        return v;
    }

    @Override
    public boolean remove(Object key, Object value) {
        boolean contains = containsKey(key);
        boolean tr = super.remove(key, value);
        try {
            if (contains) {
                cascade(change, change.buildChangeTypes(Collections.singletonList((K) key), TimelineMultiChange.ChangeType.REMOVE));
            }
            listener.onMapRemove((K) key, (V) value);
        }catch (Exception e){}
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
        listener.onMapReplace(key, oldValue, newValue);
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
        listener.onMapReplace(key, value, tr);
        return tr;
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        List<K> toAmend = new ArrayList<>();
        List<K> toAdd = new ArrayList<>();
        for (Entry<K, V> e : entrySet()) {
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

    public void setListener(Listener<K,V> listener){
        this.listener = listener;
    }

    @Override
    public void clear() {
        super.clear();
        listener.onMapClear();
    }

    @Override
    public V get(Object key) {
        V v = super.get(key);
        if(key instanceof Identifiable<?>){
            try {
                listener.onMapGet((K) key, v);
            } catch (Exception e) {}
        }
        return v;
    }

    public void setChanged(K... key){
        change.setChanged(key);
    }
    
    private static <M extends TimelineMultiChange<M,K,V,I,T>, K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>> void cascade(TimelineMultiChange<?,?,?,?,?> change, Map<?,TimelineMultiChange.ChangeType> changes){
        M m = (M) change;
        Map<K, TimelineMultiChange.ChangeType> finalChanges = (Map<K, TimelineMultiChange.ChangeType>) changes;
        m.cascadeInvalidate(m,finalChanges);
    }

    public static abstract class Listener<K extends Identifiable<?>,V>{
        public abstract void onMapPut(K key, V value);
        public abstract void onMapRemove(K key, V value);
        public abstract void onMapReplace(K key, V oldValue, V newValue);
        public abstract void onMapClear();
        public abstract void onMapGet(K key, V value);

        public static final Listener<?,?> EMPTY = new Listener<>() {
            @Override
            public void onMapPut(Identifiable<?> key, Object value) {}
            @Override
            public void onMapRemove(Identifiable<?> key, Object value) {}
            @Override
            public void onMapReplace(Identifiable<?> key, Object oldValue, Object newValue) {}
            @Override
            public void onMapClear() {}
            @Override
            public void onMapGet(Identifiable<?> key, Object value) {}
        };
    }

}
