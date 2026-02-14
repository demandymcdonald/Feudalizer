package com.people;

import com.base.AbstractMutableManager;
import com.google.gson.JsonObject;

import java.util.UUID;

public class HouseManager extends AbstractMutableManager<House.HouseState,House> {
    @Override
    public House deserializer(UUID id, JsonObject json) {
        return null;
    }
}
