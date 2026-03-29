package com.base;

import com.Feudalizer;

import com.GlobalVars;
import com.base.reference.DMEReference;
import com.base.timeline.TimelineContainer;
import com.base.timeline.TimelineState;
import com.base.timeline.change.TimelineChange;
import com.utilities.LoadingManager;
import com.google.common.collect.HashBiMap;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractMutableManager<T extends DateMutableEntity<T>> {
    private final HashBiMap<UUID, T> ItemMap = HashBiMap.create();
    private boolean isLoaded = false;
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

    public void register(DateMutableEntity<?> entity) {
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
    public int getSize() {
        return ItemMap.size();
    }
    public UUID getItemId(T entity) {
        return ItemMap.inverse().get(entity);
    }
    public HashBiMap<UUID, T> getItemMap() {
        return ItemMap;
    }
    public List<T> getWhere(Predicate<T> predicate) {
        return new ArrayList<>(ItemMap.values()).stream().filter(predicate).collect(Collectors.toList());
    }
    public void onGameStateChangeLoad(LocalDate date) {
        LoadingManager lm = GlobalVars.getLoadingManager();
        lm.setTemporaryAppend(getObjectType().name());
        for (T entity : ItemMap.values()) {
            entity.setCurrentState(date);
            lm.incrementLoading();
        }
        lm.restoreToMainState();
    }
    public void onGameStateChangeLink(){
        LoadingManager lm = GlobalVars.getLoadingManager();
        lm.setTemporaryAppend(getObjectType().name());
        for (T entity : ItemMap.values()) {
            entity.relink();
            lm.incrementLoading();
        }
        lm.restoreToMainState();
    }
    public abstract TimelineChange<T> getBirthChange(DMEReference<T> dme, LocalDate date);
    public abstract TimelineChange<T> getDeathChange(DMEReference<T> dme, LocalDate date);
    public final TimelineState<T> buildBirth(DMEReference<T> dme, LocalDate date, List<TimelineChange<T>> defaults){
        defaults.addFirst(getBirthChange(dme,date));
        return new TimelineState<T>(dme, date, date,true, defaults);
    };
    public final TimelineState<T> buildDeath(DMEReference<T> dme, LocalDate date, List<TimelineChange<T>> defaults){
        defaults.addFirst(getDeathChange(dme,date));
        return new TimelineState<T>(dme,date, date,true, defaults);
    };
}
