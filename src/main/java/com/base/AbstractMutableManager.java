package com.base;

import com.Feudalizer;

import com.base.timeline.TimelineContainer;
import com.google.common.collect.HashBiMap;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractMutableManager<T extends DateMutableEntity<T,C>,C extends TimelineContainer<C>> {
    private final HashBiMap<UUID, T> ItemMap = HashBiMap.create();
    private boolean isLoaded = false;
    private final C emptyObject;
    public AbstractMutableManager(C empty) {
        this.emptyObject = empty;
    }
    public abstract T deserializer(UUID id, JsonObject json);
    public abstract ObjectType getObjectType();
    public void onRelink() {
        for (T entity : ItemMap.values()) {
            entity.relink(entity.getStateAt(entity.getCreated()));
        }
    }
    public T deserializeEntity(UUID id, JsonObject json) {
        T t = deserializer(id, json);
        ItemMap.put(id, t);
        return t;
    }

    public void onLoad(){
        isLoaded = true;
    }
    public void register(DateMutableEntity<?, ?> entity) {
        ItemMap.put(entity.getId(), (T) entity);
    }
    public T get(UUID id) {
        T t = ItemMap.get(id);
        if (t == null && DMRegistry.isMain()) {
            Feudalizer.LOGGER.error("{} not found for manager {}", id, getClass().getName());
        } else {
            JsonObject object = DMRegistry.addPendingLoad(getObjectType(),id).join();
            T obj = deserializer(id, object);
            ItemMap.put(id, obj);
            return obj;
        }
        return t;
    }
    public List<T> getAll() {
        return new ArrayList<>(ItemMap.values());
    }
    public UUID getItemId(T entity) {
        return ItemMap.inverse().get(entity);
    }
    public HashBiMap<UUID, T> getItemMap() {
        return ItemMap;
    }
    public boolean isLoaded() {
        Feudalizer.LOGGER.warn("{} ACCESSED BEFORE LOADING HAD COMPLETED",this.getClass().getName());
        return isLoaded;
    }
    public List<T> getWhere(Predicate<T> predicate) {
        return new ArrayList<>(ItemMap.values()).stream().filter(predicate).collect(Collectors.toList());
    }
    public void onGameStateChangeLoad(LocalDate date) {
        for (T entity : ItemMap.values()) {
            entity.setCurrentState(date);
        }
    }
    public void onGameStateChangeLink(){
        for (T entity : ItemMap.values()) {
            entity.relink();
        }
    }
    public C getEmptyObject() {
        return emptyObject;
    }
    public C deserializeContainer(JsonObject json) {
        return emptyObject.deserialize(json);
    }
}
