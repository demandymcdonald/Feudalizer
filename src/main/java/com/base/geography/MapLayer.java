package com.base.geography;

import com.Global.*;
import com.base.component.AbstractComponent;
import com.base.component.InstanceType;
import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.google.gson.JsonObject;
import org.geotools.data.store.ContentFeatureCollection;

import java.io.File;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class MapLayer<T extends DateMutableEntity<T> & IGeographyObject> extends AbstractComponent<MapLayer<T>> {
    private final Map<String, DMEReference<T>> dmeMap = Collections.synchronizedMap(new HashMap<>());
    private final AtomicReference<ContentFeatureCollection> runtime = new AtomicReference<>();
    private final int order;
    public MapLayer(InstanceType type, String id, int order) {
        super(type, id);
        this.order = GeographyManager.Layer.INSTANCE.resolveNumber(order);
        GeographyManager.Layer.INSTANCE.finishRegister(this);
    }
    public MapLayer(InstanceType type, String id) {
        super(type, id);
        this.order = GeographyManager.Layer.INSTANCE.resolveNumber(order);
        GeographyManager.Layer.INSTANCE.finishRegister(this);
    }


    public int getOrder() {
        return order;
    }

    @Override
    public MapLayer<T> getNewObject(InstanceType type, String id, JsonObject data) {
        return null;
    }

    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }
}
