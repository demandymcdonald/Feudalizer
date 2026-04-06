package com.objects.government;

import com.base.AbstractMutableManager;
import com.google.gson.JsonObject;
import com.utilities.Factory;

import java.util.Map;
import java.util.UUID;

public class GovernmentManager extends AbstractMutableManager<GovernmentManager,Government<?>> {


    protected GovernmentManager() {
        super("government_manager");
    }


    @Override
    public Map<Class<? extends Government<?>>, Factory<? extends Government<?>, Government<?>, UUID, JsonObject>> getFactories() {
        return Map.of();
    }

    @Override
    public Class<?> instanceClass() {
        return null;
    }

    @Override
    public void onThreadInit(boolean shared) {

    }


}
