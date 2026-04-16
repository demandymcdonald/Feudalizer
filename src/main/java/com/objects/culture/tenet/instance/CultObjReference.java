package com.objects.culture.tenet.instance;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.reference.StateReference;
import com.google.gson.JsonObject;
import com.objects.culture.object.CultureObject;
import com.utilities.id.Identifiable;

import java.util.UUID;

public class CultObjReference<T extends DateMutableEntity<T> & CultureObject<T>> extends StateReference implements Identifiable<UUID> {
    DMEReference<T> holder;

    public static final String TO_SR_TYPE = "CultObjReference";
    public CultObjReference(T e) {
        holder = e.getReference();
    }
    public CultObjReference(DMEReference<T> holder) {
        if (holder.get() instanceof CultureObject<?>){
            this.holder = holder;
        } else {
            throw new IllegalArgumentException("Holder must be a TenetOpinionated");
        }
    }
    public T get() {
        DateMutableEntity<T> t =  holder.get();
        if (t instanceof CultureObject<?> to){
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
    public static <T extends DateMutableEntity<T> & CultureObject<T>> CultObjReference<T> deserialize(JsonObject json){
        JsonObject o = json.getAsJsonObject("holder");
        return new CultObjReference<T>(DMEReference.deserialize(o));
    }

    @Override
    public UUID getID() {
        return holder.getID();
    }
}
