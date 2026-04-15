package com.objects.culture.tenet.reference;

import com.base.reference.DMEReference;
import com.google.gson.JsonObject;
import com.objects.culture.tenet.TenetManager;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.types.dynamic.DynamicTenet;

import java.util.UUID;

public class DynamicTR<T extends DynamicTenet<T>> extends TenetReference {
    private DMEReference<T> dmeReference;
    private TenetGroup group;
    public DynamicTR(DMEReference<T> dmeReference) {
        this.dmeReference = dmeReference;
        this.group = dmeReference.get().getGroup();
    }
    public DynamicTR(T t){
        dmeReference = t.getReference();
        group = t.getGroup();
    }
    public DynamicTR() {}
    @Override
    public T get() {
        return dmeReference.get();
    }

    @Override
    public TenetGroup getGroup() {
        return group;
    }

    @Override
    protected void additionalSave(JsonObject object) {
        object.addProperty(ST_HEADER,DYNAMIC);
        object.add("ref",dmeReference.serialize());
        object.addProperty("group",group.getDisplayID());
    }

    @Override
    protected void additionalLoad(JsonObject object) {
        group = TenetManager.Group.get(object.get("group").getAsString());
        dmeReference = DMEReference.deserialize(object.get("ref").getAsJsonObject());
    }

    @Override
    public UUID getID() {
        return dmeReference.getID();
    }
}
