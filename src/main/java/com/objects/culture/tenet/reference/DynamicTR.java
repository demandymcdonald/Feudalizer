package com.objects.culture.tenet.reference;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.google.gson.JsonObject;
import com.objects.culture.tenet.types.DynamicTenet;

import java.util.UUID;

public class DynamicTR<T extends DynamicTenet<T>> extends TenetReference {
    private DMEReference<T> dmeReference;

    public DynamicTR(DMEReference<T> dmeReference) {
        this.dmeReference = dmeReference;
    }
    public DynamicTR(T t){
        dmeReference = t.getReference();
    }
    public DynamicTR() {}
    @Override
    public T get() {
        return dmeReference.get();
    }

    @Override
    protected void additionalSave(JsonObject object) {
        object.addProperty(ST_HEADER,DYNAMIC);
        object.add("ref",dmeReference.serialize());
    }

    @Override
    protected void additionalLoad(JsonObject object) {

        dmeReference = DMEReference.deserialize(object.get("ref").getAsJsonObject());
    }

    @Override
    public UUID getID() {
        return dmeReference.getID();
    }
}
