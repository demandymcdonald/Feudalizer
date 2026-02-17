package com.simulation.divisions;

import com.google.gson.JsonObject;
import com.simulation.people.Character;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Manor extends AbstractLandDivision<Manor>{
    public Manor(UUID id, Date created, Date ended, JsonObject additionalData) {
        super(id, created, ended, additionalData);
    }

    public Manor(JsonObject payload) {
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

    public Manor(UUID id, Date created, Date ended) {
        super(id, created, ended);
    }

    @Override
    protected JsonObject getPassthroughData() {
        return new JsonObject();
    }

    @Override
    protected void onNewStateLoad(JsonObject passthrough) {

    }

    @Override
    protected void onRelink() {

    }
}
