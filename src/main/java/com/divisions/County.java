package com.divisions;

import com.google.gson.JsonObject;
import com.resources.HabitableLand;

import java.util.Date;
import java.util.UUID;

public class County extends HabitableLand {


    public County(UUID id, Date created, Date ended, JsonObject additionalData) {
        super(id, created, ended, additionalData);
    }

    public County(JsonObject payload) {
        super(payload);
    }


    @Override
    protected JsonObject getPassthroughData() {
        return super.getPassthroughData();
    }

    @Override
    protected void setPassthroughData(JsonObject passthrough) {
        super.setPassthroughData(passthrough);
    }

    @Override
    protected void onRelink() {

    }
}
