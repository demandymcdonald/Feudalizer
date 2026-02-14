package com.divisions;

import com.google.gson.JsonObject;
import com.resources.HabitableLand;

import java.util.Date;
import java.util.UUID;

public class Province extends HabitableLand {
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
}
