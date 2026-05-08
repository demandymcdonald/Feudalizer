package com.base.geography;

import com.base.component.AbstractComponent;
import com.base.component.InstanceType;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import org.geotools.api.data.DataStore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class GeoContainer extends AbstractComponent<GeoContainer> {
    private String name;
    private String iso;
    private long areaKM;
    private int defaultNumAdminUnits;
    private String continent;
    public GeoContainer(InstanceType type, String id) {
        super(type, id);
    }

    public GeoContainer(InstanceType type, String id, String continent, int level, Map<Integer,DataStore> stores,  String name, String iso) {
        super(type, id);
        this.areaKM = areaKM;
        this.name = name;
        this.continent = continent;
        stores = ImmutableMap.copyOf(stores);
        this.iso = iso;
        this.defaultNumAdminUnits = defaultNumAdminUnits;
    }

    public long getAreaKM() {
        return areaKM;
    }

    public int getDefaultNumAdminUnits() {
        return defaultNumAdminUnits;
    }

    public String getIso() {
        return iso;
    }

    public String getName() {
        return name;
    }

    public DataStore getStore() {
        return store.get();
    }

    @Override
    public GeoContainer getNewObject(InstanceType type, String id, JsonObject data) {
        return null;
    }

    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }
    public void onExit(){
        store.get().dispose();
    }
}
