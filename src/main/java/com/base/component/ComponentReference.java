package com.base.component;

import com.base.reference.IReference;
import com.google.common.base.Suppliers;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.JsonPrimitive;
import com.utilities.serialization.StringToHex;

import java.util.function.Supplier;
@SuppressWarnings("unchecked")
public class ComponentReference<T extends IComponent<?>> implements IComponentLogged, IReference<ComponentReference<T>,T,String> {
    private static final Cache<String, ComponentReference<?>> cache = CacheBuilder.newBuilder().concurrencyLevel(5).maximumSize(500).build();
    private final Class<T> type;
    private final String id;
    private final String fullID;
    private final Supplier<T> supplier = Suppliers.memoize(this::locate);
    private ComponentReference(Class<T> type, String id) {
        this.type = type;
        this.id = id;
        this.fullID = type.getName() + "::" + id;
        cache.put(fullID,this);
    }
    private T locate(){
        ComponentManager<T> manager = ComponentRegistry.getManager(type);
        return manager.get(id);
    }
    public T get(){
        return supplier.get();
    }

    @Override
    public Class<T> getType() {
        return type;
    }

    @Override
    public String getID() {
        return id;
    }

    public static <T extends IComponent<T>>  ComponentReference<T> of(Class<T> type, String id){
        try {
            return (ComponentReference<T>) cache.get(type.getName() + "::" + id, () -> new ComponentReference<>(type, id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static <T extends IComponent<T>>  ComponentReference<T>  of(T object){
        Class<T> type = (Class<T>) object.getClass();
        String id = object.getID();
        return of(type,id);
    }
    public JsonPrimitive toJson(){
        return new JsonPrimitive(StringToHex.encode(fullID));
    }
    public static <T extends IComponent<T>> ComponentReference<T> fromJson(JsonPrimitive primitive){
        String[] split = StringToHex.decode(primitive.getAsString()).split("::");
        try {
            return of((Class<T>) Class.forName(split[0]), split[1]);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
