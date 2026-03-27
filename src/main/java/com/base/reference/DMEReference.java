package com.base.reference;

import com.Feudalizer;

import com.base.DMRegistry;
import com.base.DateMutableEntity;
import com.base.ObjectType;
import com.google.gson.JsonObject;
import com.simulation.title.Title;

import java.util.Objects;
import java.util.UUID;

public class DMEReference<T extends DateMutableEntity<T,?>> extends StateReference {
    private final ObjectType type;
    private final UUID uuid;
    private transient T cachedEntity;
    public DMEReference(ObjectType type, UUID uuid) {
        this.type = type;
        this.uuid = uuid;
    }
    public DMEReference(T entity) {
        this.type = DMRegistry.getObjectType(entity);
        this.uuid = entity.getId();
    }

    public <R extends T> DMEReference(R entity, boolean indirect) {
        this.type = DMRegistry.getObjectType(entity);
        this.uuid = entity.getId();
    }


    //    public <R extends DateMutableEntity<R,?>> DMEReference(R entity) {
//        this.type = (Class<T>) entity.getClass();
//        this.uuid = entity.getId();
//    }
    public T link() {
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
    public static <T extends DateMutableEntity<T,?>> DMEReference<T> deserialize(JsonObject object) {
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

    @Override
    public int hashCode() {
        return Objects.hash(uuid, type);
    }

    public UUID getUuid() {
        return uuid;
    }
    public ObjectType getType() {
        return type;
    }
//    public static <T extends Title<T>> DMEReference<T> of(T tTitle) {
//        return new DMEReference<>(tTitle,true);
//    }
    public static <T extends Title<T>> DMEReference<T> of(Title<T> tTitle) {
        return new DMEReference<>(tTitle,true);
    }
    public static <T extends DateMutableEntity<T,?>> DMEReference<T> of(T entity){
        return new DMEReference<T>(entity);
    }
    public static <T extends DateMutableEntity<T,?>> DMEReference<T>[] of(T... entity){
        DMEReference<T>[] references = new DMEReference[entity.length];
        for (int i = 0; i < entity.length; i++) references[i] = of(entity[i]);
        return references;
    }

    @Override
    public String parse() {
        return link().toString();
    }
}
