package com.objects.culture.tenet.flag;

import com.base.component.ComponentReference;
import com.base.component.instanced.AbstractComponentInstance;
import com.base.component.instanced.base.IOIBase;
import com.google.gson.JsonObject;

public class FlagInstance extends IOIBase<FlagTenet,FlagInstance> {
    public FlagInstance(ComponentReference<FlagTenet> reference) {
        super(reference);
    }

    public FlagInstance(JsonObject reference) {
        super(reference);
    }

    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }
}
