package com.base.instanced;

import com.google.common.base.Suppliers;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.JsonPrimitive;
import com.utilities.serialization.StringToHex;

import java.util.function.Supplier;
@SuppressWarnings("unchecked")
public class IOReference<T extends IInstancedObject<T,?>> implements IIO{
    private static final Cache<String,IOReference<?>> cache = CacheBuilder.newBuilder().concurrencyLevel(5).maximumSize(500).build();
    private final Class<T> type;
    private final String id;
    private final String fullID;
    private final Supplier<T> supplier = Suppliers.memoize(this::locate);
    private IOReference(Class<T> type, String id) {
        this.type = type;
        this.id = id;
        this.fullID = type.getName() + "::" + id;
        cache.put(fullID,this);
    }
    private T locate(){
        IOManager<T,?> manager = IOManager.getManager(type);
        return manager.get(id);
    }
    public T get(){
        return supplier.get();
    }
    public static <T extends IInstancedObject<T,IN>,IN extends IOInstance<T,IN>> IOReference<T> of(Class<T> type, String id){
        try {
            return (IOReference<T>) cache.get(type.getName() + "::" + id, () -> new IOReference<>(type, id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static <T extends IInstancedObject<T,IN>,IN extends IOInstance<T,IN>> IOReference<T> of(T object){
        Class<T> type = (Class<T>) object.getClass();
        String id = object.getID();
        return of(type,id);
    }
    public JsonPrimitive toJson(){
        return new JsonPrimitive(StringToHex.encode(fullID));
    }
    public static <T extends IInstancedObject<T,I>,I extends IOInstance<T,I>> IOReference<T> fromJson(JsonPrimitive primitive){
        String[] split = StringToHex.decode(primitive.getAsString()).split("::");
        try {
            return of((Class<T>) Class.forName(split[0]), split[1]);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
