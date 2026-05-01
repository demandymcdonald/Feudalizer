package com.base.reference;

import com.Feudalizer;

import com.base.DMRegistry;
import com.base.datemutable.DateMutableEntity;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.utilities.id.UUIDIdentifiable;
import com.utilities.serialization.StringToHex;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unchecked")
public class DMEReference<T extends DateMutableEntity<?>> implements UUIDIdentifiable, IReference<DMEReference<T>,T,UUID> {
    private static final String DELIMITER = ":DME:";
    public static final String DME_SR_TYPE = "DMEReference";
    private final Class<T> type;
    private final UUID uuid;
    private transient ThreadLocal<T> cachedEntity = new ThreadLocal<>();
    private static final Cache<Long, DMEReference<?>> CACHE = CacheBuilder.newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .maximumSize(4000)
            .build();
    private static final Cache<String, Class<? extends DateMutableEntity<?>>> CLASS_CACHE = CacheBuilder.newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .maximumSize(4000)
            .build();
    private DMEReference(Class<T> type, UUID uuid) {
        this.type = type;
        this.uuid = uuid;
    }
    private DMEReference(T e) {
        this.type = (Class<T>) e.getClass();
        this.uuid = e.getID();
    }
    public T get() {
        if (cachedEntity.get() == null) {
            cachedEntity.set(DMRegistry.getEntity(this));
        }
        return cachedEntity.get();
    }
    public JsonElement serialize(){
        return new JsonPrimitive(StringToHex.encode(type.getName())+DELIMITER+StringToHex.encode(uuid.toString()));
    }
    public static <T extends DateMutableEntity<?>> DMEReference<T> deserialize(JsonElement object)  {
        String[] splits = object.getAsString().split(DELIMITER);
        String name = StringToHex.decode(splits[0]);
        UUID uuid = UUID.fromString(StringToHex.decode(splits[1]));
        try {
            Class<T> r = (Class<T>) CLASS_CACHE.get(name, () -> buildClass(name));
            long hash = doHash(uuid, r);
            return (DMEReference<T>) CACHE.get(hash, () -> DMEReference.of(r, uuid));
        } catch (ExecutionException e) {
            Feudalizer.LOGGER.error("Failed to deserialize DMEReference: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    private static <T extends DateMutableEntity<?>> Class<T> buildClass(String name){
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
    public static <T extends DateMutableEntity<?>> DMEReference<T> of(T entity){
        long h = doHash(entity.getID(),entity.getClass());
        DMEReference<T> cached = (DMEReference<T>) CACHE.getIfPresent(h);
        if (cached == null) {
            cached = new DMEReference<>(entity);
            CACHE.put(h,cached);
        }
         return cached;
    }
    public static <T extends DateMutableEntity<?>> DMEReference<T> of(Class<T> type, UUID uuid){
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
}
