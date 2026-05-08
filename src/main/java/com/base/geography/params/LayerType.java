package com.base.geography.params;

import com.Global.*;
import com.base.component.AbstractComponent;
import com.base.component.InstanceType;
import com.google.gson.JsonObject;

import java.util.*;

public class LayerType extends AbstractComponent<LayerType> {
    public LayerType(InstanceType type, String id) {
        super(type, id);
    }

    @Override
    public LayerType getNewObject(InstanceType type, String id, JsonObject data) {
        return null;
    }

    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }
}
