package com.objects.culture.tenet.group;

import com.Global.*;
import com.base.reference.StateReference;
import com.google.gson.JsonObject;

import java.util.function.Supplier;

class TGReference extends StateReference {
    private final String id;
    private final Supplier<TenetGroup> group;
    public TGReference(TenetGroup group) {

    }


    public TenetGroup link(){
        return group.get();
    }

    @Override
    public String parse() {
        return "";
    }

    @Override
    public JsonObject serialize() {
        return null;
    }
}
