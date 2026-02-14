package com.divisions;

import com.google.gson.JsonObject;

import java.util.Date;
import java.util.UUID;

public class Manor extends AbstractLandDivision{
    public Manor(UUID id, Date created, Date ended, JsonObject additionalData) {
        super(id, created, ended, additionalData);
    }

    public Manor(JsonObject payload) {
        super(payload);
    }

    public Manor(UUID id, Date created, Date ended) {
        super(id, created, ended);
    }

    @Override
    protected JsonObject getPassthroughData() {
        return new JsonObject();
    }

    @Override
    protected void setPassthroughData(JsonObject passthrough) {

    }

    @Override
    protected void onRelink() {

    }
}
