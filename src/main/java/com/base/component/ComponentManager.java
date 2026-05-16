package com.base.component;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.gson.JsonObject;
import com.utilities.serialization.SuperclassSerializable;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public abstract class ComponentManager<T extends IComponent<?>> implements IComponentLogged {

    protected final AtomicBoolean isLoaded = new AtomicBoolean(false);
    protected final Map<String,T> instanceMap = Collections.synchronizedMap(new HashMap<>());
    protected final Multimap<Class<? extends T>,IComponent<?>> classInstanceMap = Multimaps.synchronizedMultimap(HashMultimap.create());

    public ComponentManager(Class<? extends T> type){
        ComponentRegistry.registerManager(this,type);
    }

    public final void register(T object){
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

    public Set<T> getAll(){
        return new HashSet<>(instanceMap.values());
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
    public final <t extends T> t getOrMake(Class<? extends T> clazz, String id, Supplier<t> factory){
        T tr = instanceMap.get(id);
        if(tr == null){
            tr = factory.get();
            register(tr);
        }
        if(tr != null && tr.getClass() == clazz){
            return (t) tr;
        }
        return null;
    }
    public final Map<String, JsonObject> save(){
        Map<String, JsonObject> saveableMap = new HashMap<>();
        for(T t : instanceMap.values()){
            if(t.getInstanceType().isSavable()){
                JsonObject json = new JsonObject();
                json.addProperty("type",t.getInstanceType().name());
                json.add("payload",t.serialize());
                saveableMap.put(t.getID(), json);
            }
            onSave(t);
        }
        return saveableMap;
    }
    public void load(Map<String, JsonObject> toLoad){
        Map<Class<? extends T>, T> templateMap = new HashMap<>();

        for(Map.Entry<String, JsonObject> entry : toLoad.entrySet()){
            Class<? extends T> clazz = SuperclassSerializable.getSSClass(entry.getValue());
            T template = null;
            JsonObject jsonObj = entry.getValue();
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
            T value = (T) template.getNewObject(InstanceType.valueOf(jsonObj.get("type").getAsString()),entry.getKey());
            value.deserialize(jsonObj.get("payload").getAsJsonObject());
            register(value);
            onLoad(value);
        }
    }
    public void onSave(T object){}
    public void onLoad(T object){}
}
