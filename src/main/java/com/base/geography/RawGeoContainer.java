package com.base.geography;

import com.base.component.AbstractComponent;
import com.base.component.InstanceType;
import com.base.geography.params.LayerType;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import org.geotools.api.data.DataStore;

import java.util.Map;

public class RawGeoContainer {
    private final LayerType layerType;
    private final String group;
    private final Map<LayerType,DataStore> stores;
    private final JsonObject additionalData;
    public RawGeoContainer(LayerType layerType, String group, Map<LayerType,DataStore> stores, JsonObject additionalData) {
        this.layerType = layerType;
        this.group = group;
        this.stores = stores;
        this.additionalData = additionalData;
    }

    public String getGroup() {
        return group;
    }

    public LayerType getLayerType() {
        return layerType;
    }

    public DataStore getStore(LayerType type) {
        return stores.get(type);
    }
    public JsonObject getSidecar() {
        return additionalData;
    }
    public Map<LayerType, DataStore> getStores() {
        return stores;
    }
    public void onExit(){
        for (Map.Entry<LayerType, DataStore> store : stores.entrySet())
            if (store.getValue() != null) store.getValue().dispose();
    }
}
