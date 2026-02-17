package com.divisions;

import com.google.gson.JsonObject;
import com.people.Character;
import com.resources.HabitableLand;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class County extends HabitableLand<County> {


    public County(UUID id, Date created, Date ended, JsonObject additionalData) {
        super(id, created, ended, additionalData);
    }

    public County(JsonObject payload) {
        super(payload);
    }

    @Override
    public boolean canInherit(Character person) {
        return false;
    }

    @Override
    public boolean isInheritable() {
        return false;
    }

    @Override
    public boolean isSubPropagating() {
        return false;
    }

    @Override
    public List<Character> getAllClaimants() {
        return List.of();
    }


    @Override
    protected JsonObject getPassthroughData() {
        return super.getPassthroughData();
    }

    @Override
    protected void onNewStateLoad(JsonObject passthrough) {
        super.onNewStateLoad(passthrough);
    }

    @Override
    protected void onRelink() {

    }
}
