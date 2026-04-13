package com.objects.culture.tenet.reference;

import com.base.reference.StateReference;
import com.google.gson.JsonObject;
import com.objects.culture.tenet.TenetManager;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.types.Tenet;
import com.utilities.id.StringIdentifiable;
import com.utilities.id.UUIDIdentifiable;

public abstract class TenetReference extends StateReference implements UUIDIdentifiable {
    public static final String TENET_SR_TYPE = "TenetReference";
    @Override
    public String parse() {
        return get().getFull();
    }
    public abstract Tenet get();
    @Override
    public JsonObject serialize() {
        JsonObject object = new JsonObject();
        return null;
    }
    protected abstract void additionalSave(JsonObject object);
    protected abstract void additionalLoad(JsonObject object);
    public static <TR extends Tenet> TenetReference<TR> deserialize(JsonObject json){

    }
    public static <TR extends Tenet> TenetReference<TR> of(TR te){

    }

    @Override
    public String getID() {
        return id;
    }
}
