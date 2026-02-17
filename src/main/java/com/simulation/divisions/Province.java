package com.simulation.divisions;

import com.google.gson.JsonObject;
import com.simulation.people.Character;
import com.simulation.resources.HabitableLand;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Province extends HabitableLand<Province> {
    public Province(UUID id, Date created, Date ended) {
        super(id, created, ended);
    }

    public Province(UUID id, Date created, Date ended, JsonObject additionalData) {
        super(id, created, ended, additionalData);
    }

    @Override
    protected void onRelink() {

    }

    public Province(JsonObject payload) {
        super(payload);
    }

    @Override
    public boolean canInherit(Character person) {
        return false;
    }

    @Override
    public List<Character> getAllClaimants() {
        return List.of();
    }
}
