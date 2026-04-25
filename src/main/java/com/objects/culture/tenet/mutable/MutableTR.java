package com.objects.culture.tenet.mutable;

import com.google.gson.JsonObject;
import com.objects.culture.tenet.Tenet;
import com.objects.culture.tenet.TenetManager;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.TenetReference;

import java.util.UUID;

public class MutableTR extends TenetReference {
    private UUID id;
    private TenetGroup group;
    private Class<? extends Tenet> tenetClass;
    public MutableTR(MutableTenet tenet) {
        this.id = tenet.getID();
        this.group = tenet.getGroup();
        this.tenetClass = tenet.getClass();
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
        object.addProperty("tenetClass",tenetClass.getName());
    }

    @Override
    protected void additionalLoad(JsonObject object) {
        id = UUID.fromString(object.get("id").getAsString());
        group = TenetManager.Group.get(object.get("group").getAsString());
        try {
            tenetClass = (Class<? extends Tenet>) Class.forName(object.get("tenetClass").getAsString());
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public <TR extends Tenet> Class<TR> getTenetClass() {
        return null;
    }

    @Override
    public UUID getID() {
        return id;
    }
}
