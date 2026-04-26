package com.objects.culture;

import com.base.datemutable.AbstractMutableManager;
import com.google.gson.JsonObject;
import com.utilities.Factory;

import java.util.Map;
import java.util.UUID;

public class CultureManager extends AbstractMutableManager<CultureManager,AbstractCulture<?>> {
    Map<String,>
    protected CultureManager() {
        super("culture_manager");
    }




    @Override
    public Map<Class<? extends AbstractCulture<?>>, Factory<? extends AbstractCulture<?>, AbstractCulture<?>, UUID, JsonObject>> getFactories() {
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
