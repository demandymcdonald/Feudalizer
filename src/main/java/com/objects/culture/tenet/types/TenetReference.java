package com.objects.culture.tenet.types;

import com.base.reference.StateReference;
import com.google.gson.JsonObject;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.types.dynamic.DynamicTR;
import com.objects.culture.tenet.types.dynamic.DynamicTenet;
import com.objects.culture.tenet.types.mutable.MutableTR;
import com.objects.culture.tenet.types.mutable.MutableTenet;
import com.objects.culture.tenet.types.mutable.Tenet;
import com.utilities.id.UUIDIdentifiable;
import com.utilities.serialization.SuperclassSerializable;

public abstract class TenetReference extends StateReference implements UUIDIdentifiable {
    public static final String TENET_SR_TYPE = "TenetReference";
    protected static final String ST_HEADER = "subtype";
    protected static final String MUTABLE = "mutableTenet";
    protected static final String DYNAMIC = "dynamicTenet";
    @Override
    public String parse() {
        return get().getFull();
    }
    public abstract Tenet get();
    public abstract TenetGroup getGroup();
    @Override
    public JsonObject serialize() {
        JsonObject object = new JsonObject();
        return null;
    }
    protected abstract void additionalSave(JsonObject object);
    protected abstract void additionalLoad(JsonObject object);
    public static TenetReference deserialize(JsonObject json){
        JsonObject o = SuperclassSerializable.getAdditional(json);
        TenetReference tenetReference;
        if(o.get(ST_HEADER) != null && o.get(ST_HEADER).getAsString().equals(MUTABLE)){
            tenetReference =  new MutableTR();
        } else {
            tenetReference = new DynamicTR<>();
        }
        tenetReference.additionalLoad(o);
        return tenetReference;
    }
    public static <TR extends Tenet> TenetReference of(TR te){
        if (te instanceof MutableTenet tr) {
            return new MutableTR(tr.getID());
        } else if (te instanceof DynamicTenet<?> dtr) {
            return new DynamicTR<>(dtr);
        }
        return null;
    }

}
