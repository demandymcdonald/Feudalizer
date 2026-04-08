package com.utilities;

import com.utilities.serialization.SuperclassRegistry;
import com.utilities.serialization.SuperclassSerializable;

import java.util.*;
import java.util.function.Consumer;

public class ObjectRegistry<T extends SuperclassSerializable, OK> {
    Map<Class<? extends T>, ObjectRegistryEntry<?>> registry = Collections.synchronizedMap(new HashMap<>());

    public <Tt extends T> Tt get(Class<Tt> subclass, OK key) {
        ObjectRegistryEntry<Tt> entry = (ObjectRegistryEntry<Tt>) registry.get(subclass);
        if (entry == null){
            return null;
        }
        return entry.get(key);
    }

    public <Tt extends T> void put(Class<Tt> subclass, OK key, Tt object) {
        ObjectRegistryEntry<Tt> entry = (ObjectRegistryEntry<Tt>) registry.computeIfAbsent(subclass,
                ObjectRegistryEntry::new);
        entry.put(key,object);
    }

    public Collection<? extends T> getAllValues(){
        HashSet<T> set = new HashSet<>();
        for (ObjectRegistryEntry<?> entry : registry.values()){
            set.addAll(entry.map.values());
        }
        return set;
    }
    public Map<OK,? extends T> getAll(){
        HashMap<OK,T> map = new HashMap<>();
        for (ObjectRegistryEntry<?> entry : registry.values()){
            map.putAll(entry.map);
        }
        return map;
    }
    public void remove(OK key){
        for (ObjectRegistryEntry<?> entry : registry.values()){
            if (entry.map.containsKey(key)){
                entry.remove(key);
                return;
            }
        }
    }
    public void doIterate(Consumer<T> consumer){
        for (ObjectRegistryEntry<?> entry : registry.values()){
            entry.map.values().forEach(consumer);
        }
    }
    public <Tt extends T> void doSpecificIterate(Class<Tt> clas, Consumer<Tt> consumer){
        ObjectRegistryEntry<Tt> entry = (ObjectRegistryEntry<Tt>) registry.get(clas);
        if (entry != null){
            entry.map.values().forEach(consumer);
        } else {
            SuperclassRegistry.LOGGER.warn("Tried to iterate over missing registry " + clas.getName());
            SuperclassRegistry.LOGGER.debug("Stack trace:" + Arrays.toString(Thread.currentThread().getStackTrace()));
        }
    }
    public void remove(Class<? extends T> clas, OK key){
        ObjectRegistryEntry<?> entry = registry.get(clas);
        if (entry != null){
            entry.remove(key);
        }
    }
    public void clear(){
        registry.clear();
    }
    public void clearSingle(Class<? extends T> clas){
        registry.remove(clas);
    }
    protected class ObjectRegistryEntry<Tt extends T>{
        private Map<OK,Tt> map = Collections.synchronizedMap(new HashMap<>());

        public ObjectRegistryEntry(Class<? extends T> aClass) {}

        public Tt get(OK key){
            return map.get(key);
        }
        public void put(OK key, Tt object){
            map.put(key,object);
        }
        public void remove(OK key){
            map.remove(key);
        }
        public void clear(){
            map.clear();
        }

    }

}
