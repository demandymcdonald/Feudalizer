package com.simulation.people;

import com.base.AbstractMutableManager;
import com.google.gson.JsonObject;

import java.util.UUID;

public class FamilyManager extends AbstractMutableManager<Family.FamilyState,Family> {
    @Override
    public Family deserializer(UUID id, JsonObject json) {
        return new Family(json);
    }
}
