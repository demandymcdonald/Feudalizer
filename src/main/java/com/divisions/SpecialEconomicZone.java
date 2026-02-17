package com.divisions;

import com.google.gson.JsonObject;
import com.people.Character;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class SpecialEconomicZone extends AbstractLandDivision<SpecialEconomicZone> {
    public SpecialEconomicZone(UUID id, Date created, Date ended) {
        super(id, created, ended);
    }

    public SpecialEconomicZone(UUID id, Date created, Date ended, JsonObject additionalData) {
        super(id, created, ended, additionalData);
    }

    public SpecialEconomicZone(JsonObject payload) {
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

    @Override
    protected void onRelink() {

    }

    @Override
    protected JsonObject getPassthroughData() {
        return null;
    }
}
