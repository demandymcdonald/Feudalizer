package com.objects.family;

import com.Feudalizer;
import com.Global;
import com.base.AbstractMutableManager;
import com.google.gson.JsonObject;
import com.objects.character.sentient.HumanCharacter;
import com.utilities.Factory;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
