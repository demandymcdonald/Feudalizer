package com.objects.organization.religion.scope;

import com.base.component.InstanceType;
import com.base.component.immutable.ImmutableComponent;
import com.google.gson.JsonObject;
import com.objects.organization.religion.tenets.diety.DivineEntityType;

public class DivineEntityScope extends ImmutableComponent<DivineEntityScope> {

    public DivineEntityScope(InstanceType type, String id) {
        super(type, id);

    }
    public DivineEntityScope(InstanceType type, String id) {
        super(type, id);

    }



    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }

    @Override
    public DivineEntityScope getNewObject(InstanceType type, String id, JsonObject data) {
        return new DivineEntityScope(type, id);
    }
}
