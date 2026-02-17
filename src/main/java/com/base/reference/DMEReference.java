package com.base.reference;

import com.GlobalData;
import com.base.DMRegistry;
import com.base.DateMutableEntity;
import com.google.gson.JsonObject;

import java.util.Objects;
import java.util.UUID;

public class DMEReference<T extends DateMutableEntity<?>> extends StateReference {
    private final Class<T> type;
    private final UUID uuid;
    private transient T cachedEntity;
    public DMEReference(Class<T> type, UUID uuid) {
        this.type = type;
        this.uuid = uuid;
    }
    public T link() {
        if (cachedEntity == null) {
            cachedEntity = DMRegistry.getEntry(type).get(uuid);
        }
        return cachedEntity;
    }
    public JsonObject serialize(){
        JsonObject object = new JsonObject();
        object.addProperty("Type", "DMEReference");
        object.addProperty("uuid", uuid.toString());
        object.addProperty("type", type.getName());
        return object;
    }
    public static <T extends DateMutableEntity<?>> DMEReference<T> deserialize(JsonObject object) {
        Class<T> r = null;
        try {
            r = (Class<T>) Class.forName(object.get("type").getAsString());
        } catch (Exception e){
            GlobalData.logger().error(e.getMessage());
        }
        String uuid = object.get("uuid").getAsString();
        if (!uuid.equals("") && r != null) {
            return new DMEReference<T>(r,UUID.fromString(uuid));
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


    public static <T extends DateMutableEntity<?>> DMEReference<T> of(T entity){
        return new DMEReference<T>((Class<T>) entity.getClass(),entity.getId());
    }

    @Override
    public String parse() {
        return link().toString();
    }
}
