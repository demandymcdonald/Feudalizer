package com.base.reference;

import com.Feudalizer;

import com.base.DMRegistry;
import com.base.DateMutableEntity;
import com.base.ObjectType;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.gson.JsonObject;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class DMEReference<T extends DateMutableEntity<?>> extends StateReference {
    private final Class<T> type;
    private final UUID uuid;
    private transient ThreadLocal<T> cachedEntity = new ThreadLocal<>();
    private static final Cache<Long, DMEReference<?>> CACHE = CacheBuilder.newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .maximumSize(1000)
            .build();
    private static final Cache<String, Class<? extends DateMutableEntity<?>>> CLASS_CACHE = CacheBuilder.newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .maximumSize(1000)
            .build();
    private DMEReference(Class<T> type, UUID uuid) {
        this.type = type;
        this.uuid = uuid;
    }
    private DMEReference(T e) {
        this.type = (Class<T>) e.getClass();
        this.uuid = e.getId();
    }


    //    public <R extends DateMutableEntity<R,?>> DMEReference(R entity) {
//        this.type = (Class<T>) entity.getClass();
//        this.uuid = entity.getId();
//    }
    public T get() {
        if (cachedEntity.get() == null) {
            cachedEntity.set(DMRegistry.getEntity(this));;
        }
        return cachedEntity.get();
    }
    public JsonObject serialize(){
        JsonObject object = new JsonObject();
        object.addProperty("Type", "DMEReference");
        object.addProperty("uuid", uuid.toString());
        object.addProperty("dme_type", type.getName());
        return object;
    }
    public static <T extends DateMutableEntity<T>> DMEReference<T> deserialize(JsonObject object) {

        String name = object.get("dme_type").getAsString();
        Class<T> r = (Class<T>) CLASS_CACHE.getIfPresent(name);
        if (r == null) {
            r = buildClass(name);
        }
        String uuid = object.get("uuid").getAsString();
        if (!uuid.isEmpty() && r != null) {
            return new DMEReference<>(r,UUID.fromString(uuid));
        } else {
            throw new RuntimeException("Could not deserialize DMEReference: " + object.toString() + ".");
        }
    }
    private static <T extends DateMutableEntity<T>> Class<T> buildClass(String name){
        try {
            Class<T> t = (Class<T>) Class.forName(name);
            CLASS_CACHE.put(name,t);
        } catch (ClassNotFoundException e) {
            Feudalizer.LOGGER.error(e.getMessage());
        }
        throw new RuntimeException("Could not find class " + name);
    }
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DMEReference<?> other)) return false;
        return this.uuid.equals(other.uuid) && this.type.equals(other.type);
    }
    public boolean equals(ObjectType type, UUID uuid){
        return this.uuid.equals(uuid) && this.type.equals(type);
    }
    @Override
    public int hashCode() {
        return (int) hash();
    }
    public Class<T> getType() {
        return type;
    }
    public UUID getID() {
        return uuid;
    }
//    public ObjectType getType() {
//        return type;
//    }
//    public static <T extends Title<T>> DMEReference<T> of(T tTitle) {
//        return new DMEReference<>(tTitle,true);
//    }
    public static <T extends DateMutableEntity<T>> DMEReference<T> of(T entity){
        long h = doHash(entity.getId(),entity.getClass());
        DMEReference<T> cached = (DMEReference<T>) CACHE.getIfPresent(h);
        if (cached == null) {
            cached = new DMEReference<>(entity);
            CACHE.put(h,cached);
        }
         return cached;
    }
    public static <T extends DateMutableEntity<T>> DMEReference<T> of(Class<T> type, UUID uuid){
        long h = doHash(uuid,type);
        DMEReference<T> cached = (DMEReference<T>) CACHE.getIfPresent(h);
        if (cached == null) {
            cached = new DMEReference<T>(type,uuid);
            CACHE.put(h,cached);
        }
        return cached;
    }
    public long hash(){
        return doHash(uuid,type);
    }
    private static <T extends DateMutableEntity<?>> long doHash(UUID uuid, Class<T> type){
        Hasher hasher = Hashing.murmur3_128().newHasher();
        hasher.putLong(uuid.getMostSignificantBits());
        hasher.putLong(uuid.getLeastSignificantBits());
        hasher.putString(type.getName(), StandardCharsets.UTF_8);
        return hasher.hash().asLong();
    }
    private static long doHash(DMEReference<?> reference){
        return reference.hash();
    }
    @Override
    public String parse() {
        return get().toString();
    }
}
