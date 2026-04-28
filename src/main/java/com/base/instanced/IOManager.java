package com.base.instanced;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.gson.JsonObject;
import com.utilities.serialization.SuperclassSerializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class IOManager<T extends IInstancedObject<T,TI>,TI extends IOInstance<T,TI>> implements IIO{
    private static final Map<Class<? extends IInstancedObject<?,?>>,IOManager<?,?>> MANAGER_MAP = Collections.synchronizedMap(new HashMap<>());
    protected final AtomicBoolean isLoaded = new AtomicBoolean(false);
    protected final Map<String,T> instanceMap = Collections.synchronizedMap(new HashMap<>());
    protected final Multimap<Class<? extends T>,IInstancedObject<?,?>> classInstanceMap = Multimaps.synchronizedMultimap(HashMultimap.create());

    public IOManager(Class<T> type){
        MANAGER_MAP.put(type,this);
    }

    public static <T extends IInstancedObject<T,?>> IOManager<T,?> getManager(Class<T> type){
        IOManager<?,?> man = MANAGER_MAP.get(type);
        if(man == null){
            for(Class<? extends IInstancedObject<?,?>> c : MANAGER_MAP.keySet()){
                if(c.isAssignableFrom(type)){
                    man = MANAGER_MAP.get(c);
                    MANAGER_MAP.put(type,man);
                    break;
                }
            }
            throw new RuntimeException("Could not find IOManager for " + type.getName());
        }
        return (IOManager<T,?>) man;
    }

    public final <t extends T> void register(t object){
        if(instanceMap.containsKey(object.getID())){
            if(instanceMap.get(object.getID()) != object){
                throw new RuntimeException("Duplicate ID: " + object.getID() + " for " + object.getClass().getName());
            }
            LOGGER.warn("Duplicate Load: {} of class {}", object.getID(), object.getClass().getName());
            return;
        }
        instanceMap.put(object.getID(), object);
        classInstanceMap.put((Class<? extends T>) object.getClass(), object);
        onRegister(object);
        if (isLoaded.get()) {
            onLink(object);
        }
    }
    public void init(){
        onInit();
        //add loading logic
        for(T instance : instanceMap.values()){
            link(instance);
        }
        isLoaded.set(true);
    }


    public void link(T object){
        onLink(object);
    }
    protected void onInit(){}
    protected void onRegister(T object){}
    protected void onLink(T object){}
    public final <t extends T> t get(String id){
        T tr = instanceMap.get(id);
        if(tr != null){
            return (t) tr;
        }
        return null;
    }

    public final Map<String, JsonObject> save(){
        Map<String, JsonObject> saveableMap = new HashMap<>();
        for(T t : instanceMap.values()){
            if(t.getInstanceType() == InstanceType.DATA_DRIVEN){
                saveableMap.put(t.getID(), t.serialize());
            }
            onSave(t);
        }
        return saveableMap;
    }
    public final void load(Map<String, JsonObject> toLoad){
        Map<Class<? extends T>, T> templateMap = new HashMap<>();
        for(Map.Entry<String, JsonObject> entry : toLoad.entrySet()){
            Class<? extends T> clazz = SuperclassSerializable.getSSClass(entry.getValue());
            T template = null;
            if(templateMap.containsKey(clazz)){
                template= templateMap.get(clazz);
            } else {
                for(T t : instanceMap.values()){
                    if(t.getClass() == clazz){
                        template = t;
                        templateMap.put(clazz,t);
                        break;
                    }
                }
                if (template == null) throw new RuntimeException("Could not find template for " + clazz.getName());
            }
            T value = template.getNewObject(entry.getKey());
            value.deserialize(entry.getValue());
            register(value);
            onLoad(value);
        }
    }
    public void onSave(T object){}
    public void onLoad(T object){}
}
