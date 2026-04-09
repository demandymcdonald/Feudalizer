package com.objects.culture.instance;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.reference.StateReference;
import com.google.gson.JsonObject;
import com.objects.culture.tenet.opinionated.TenetOpinionated;

public class TOReference<T extends TenetOpinionated<?, ?, ?>> extends StateReference {
    DMEReference<?> holder;
    public static final String TO_SR_TYPE = "TOReference";
    public TOReference(T e) {
        holder = e.getOwner();
    }
    public TOReference(DMEReference<?> holder) {
        if (holder.get() instanceof TenetOpinionated<?, ?, ?>){
            this.holder = holder;
        } else {
            throw new IllegalArgumentException("Holder must be a TenetOpinionated");
        }
    }
    public T get() {
        DateMutableEntity<?> t =  holder.get();
        if (t instanceof TenetOpinionated<?,?,?> to){
            return (T) to;
        } else {
            throw new IllegalArgumentException("Holder must be a TenetOpinionated");
        }
    }

    @Override
    public String parse() {
        return holder.parse();
    }

    @Override
    public JsonObject serialize() {
        JsonObject object = new JsonObject();
        object.addProperty(StateReference.TYPE_VARIABLE_NAME, TO_SR_TYPE);
        object.add("holder", holder.serialize());
        return object;
    }
    public static<T extends TenetOpinionated<?, ?, ?>> TOReference<T> deserialize(JsonObject json){
        JsonObject o = json.getAsJsonObject("holder");
        return new TOReference<T>(DMEReference.deserialize(o));
    }
}
