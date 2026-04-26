package com.objects.family;

import com.base.datemutable.AbstractMutableManager;
import com.google.gson.JsonObject;
import com.utilities.Factory;

import java.util.*;

public class FamilyManager extends AbstractMutableManager<FamilyManager, Family> {




    protected FamilyManager() {
        super("family_manager");
    }

    @Override
    public Map<Class<? extends Family>, Factory<? extends Family, Family, UUID, JsonObject>> getFactories() {
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
