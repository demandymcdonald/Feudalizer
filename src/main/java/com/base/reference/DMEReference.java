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

public class DMEReference<T extends DateMutableEntity<T>> extends StateReference {
    private final ObjectType type;
    private final UUID uuid;
    private transient T cachedEntity;
    private static final Cache<Long, DMEReference<?>> CACHE = CacheBuilder.newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .maximumSize(1000)
            .build();
    private DMEReference(ObjectType type, UUID uuid) {
        this.type = type;
        this.uuid = uuid;
    }
    private DMEReference(T entity) {
        this.type = DMRegistry.getObjectType(entity);
        this.uuid = entity.getId();
    }

    private <R extends T> DMEReference(R entity, boolean indirect) {
        this.type = DMRegistry.getObjectType(entity);
        this.uuid = entity.getId();
    }


    //    public <R extends DateMutableEntity<R,?>> DMEReference(R entity) {
//        this.type = (Class<T>) entity.getClass();
//        this.uuid = entity.getId();
//    }
    public T get() {
        if (cachedEntity == null) {
            cachedEntity = (T) DMRegistry.getEntity(type,uuid);
        }
        return cachedEntity;
    }
    public JsonObject serialize(){
        JsonObject object = new JsonObject();
        object.addProperty("Type", "DMEReference");
        object.addProperty("uuid", uuid.toString());
        object.addProperty("dme_type", type.getRegKey());
        return object;
    }
    public static <T extends DateMutableEntity<T>> DMEReference<T> deserialize(JsonObject object) {
        ObjectType r = null;
        try {
            r = ObjectType.getByRegKey(object.get("dme_type").getAsString());
        } catch (Exception e){
            Feudalizer.LOGGER.error(e.getMessage());
        }
        String uuid = object.get("uuid").getAsString();
        if (!uuid.isEmpty() && r != null) {
            return new DMEReference<>(r,UUID.fromString(uuid));
        }
        return null;
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

    public UUID getID() {
        return uuid;
    }
    public ObjectType getType() {
        return type;
    }
//    public static <T extends Title<T>> DMEReference<T> of(T tTitle) {
//        return new DMEReference<>(tTitle,true);
//    }
    public static <T extends DateMutableEntity<T>> DMEReference<T> of(T entity){
        return of(DMRegistry.getObjectType(entity),entity.getId());
    }
    public static <T extends DateMutableEntity<T>> DMEReference<T>[] of(T... entity){
        DMEReference<T>[] references = new DMEReference[entity.length];
        for (int i = 0; i < entity.length; i++) references[i] = of(entity[i]);
        return references;
    }
    public static <T extends DateMutableEntity<T>> DMEReference<T> of(ObjectType type, UUID uuid){
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
    private static long doHash(UUID uuid, ObjectType type){
        Hasher hasher = Hashing.murmur3_128().newHasher();
        hasher.putLong(uuid.getMostSignificantBits());
        hasher.putLong(uuid.getLeastSignificantBits());
        hasher.putString(type.getRegKey(), StandardCharsets.UTF_8);
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
