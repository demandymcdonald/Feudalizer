package com.objects.culture.tenet.reference;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.google.gson.JsonObject;
import com.objects.culture.tenet.types.DynamicTenet;

public class DynamicTR<T extends DynamicTenet<T>> extends TenetReference {
    private DMEReference<T> dmeReference;

    public DynamicTR(DMEReference<T> dmeReference) {
        this.dmeReference = dmeReference;
    }
    public DynamicTR(T t){
        dmeReference = t.getReference();
    }
    @Override
    public T get() {
        return dmeReference.get();
    }

    @Override
    protected void additionalSave(JsonObject object) {
        object.add("ref",dmeReference.serialize());
    }

    @Override
    protected void additionalLoad(JsonObject object) {
        dmeReference = DMEReference.deserialize(object);
    }
}
