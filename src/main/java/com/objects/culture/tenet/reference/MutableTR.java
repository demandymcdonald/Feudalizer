package com.objects.culture.tenet.reference;

import com.google.gson.JsonObject;
import com.objects.culture.tenet.TenetManager;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.types.mutable.MutableTenet;
import com.objects.culture.tenet.types.mutable.Tenet;

import java.util.UUID;

public class MutableTR extends TenetReference{
    private UUID id;
    private TenetGroup group;
    public MutableTR(MutableTenet tenet) {
        this.id = tenet.getID();
        this.group = tenet.getGroup();
    }
    public MutableTR() {}


    @Override
    public Tenet get() {
        return null;
    }

    @Override
    public TenetGroup getGroup() {
        return group;
    }

    @Override
    protected void additionalSave(JsonObject object) {
        object.addProperty(ST_HEADER,MUTABLE);
        object.addProperty("id",id.toString());
        object.addProperty("group",group.getDisplayID());
    }

    @Override
    protected void additionalLoad(JsonObject object) {
        id = UUID.fromString(object.get("id").getAsString());
        group = TenetManager.Group.get(object.get("group").getAsString());
    }

    @Override
    public UUID getID() {
        return id;
    }
}
