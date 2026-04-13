package com.objects.culture.tenet.reference;

import com.google.gson.JsonObject;
import com.objects.culture.tenet.types.Tenet;

import java.util.UUID;

public class MutableTR extends TenetReference{
    private UUID id;
    public MutableTR(UUID id) {
        this.id = id;
    }
    public MutableTR() {}


    @Override
    public Tenet get() {
        return null;
    }

    @Override
    protected void additionalSave(JsonObject object) {
        object.addProperty(ST_HEADER,MUTABLE);
        object.addProperty("id",id.toString());
    }

    @Override
    protected void additionalLoad(JsonObject object) {
        id = UUID.fromString(object.get("id").getAsString());
    }

    @Override
    public UUID getID() {
        return id;
    }
}
