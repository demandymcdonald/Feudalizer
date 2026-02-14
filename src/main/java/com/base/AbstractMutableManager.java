package com.base;

import com.GlobalData;
import com.google.common.collect.HashBiMap;
import com.google.gson.JsonObject;

import java.util.UUID;

public abstract class AbstractMutableManager<R,T extends DateMutableEntity<R>> {
    private final HashBiMap<UUID, T> ItemMap = HashBiMap.create();
    private boolean isLoaded = false;
    public AbstractMutableManager() {

    }
    public abstract T deserializer(UUID id, JsonObject json);
    public void onRelink() {
        for (T entity : ItemMap.values()) {
            entity.relink(entity.getStateAt(entity.getCreated()));
        }
    }
    public T deserialize(UUID id, JsonObject json) {
        T t = deserializer(id, json);
        ItemMap.put(id, t);
        return t;
    }
    public void onLoad(){
        isLoaded = true;
    }
    public T get(UUID id) {
        T t = ItemMap.get(id);
        if (t == null) {
            GlobalData.logger().error("{} not found for manager {}", id, getClass().getName());
        }
        return t;
    }
    public UUID getItemId(T entity) {
        return ItemMap.inverse().get(entity);
    }
    protected HashBiMap<UUID, T> getItemMap() {
        return ItemMap;
    }
    public boolean isLoaded() {
        GlobalData.logger().warn("{} ACCESSED BEFORE LOADING HAD COMPLETED",this.getClass().getName());
        return isLoaded;
    }
}
