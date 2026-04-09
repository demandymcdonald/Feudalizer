package com.objects.culture.tenet;

import com.base.reference.StateReference;
import com.google.common.base.Suppliers;
import com.google.gson.JsonObject;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.types.Tenet;

import java.util.function.Supplier;

public class TenetReference extends StateReference {
    public static final String TENET_SR_TYPE = "TenetReference";

    public final Supplier<Tenet> tenet = (Supplier<Tenet>) Suppliers.memoize(this::link);
    private final String id;
    private final TenetGroup group;
    public TenetReference(Tenet tenet) {
        this.id = tenet.getID();
        this.group = tenet.getGroup();
    }
    public TenetReference(String id, String groupID) {
        this.id = id;
        this.group = TenetManager.getGroup(groupID);
    }
    @Override
    public String parse() {
        return get().getFull();
    }
    public Tenet link(){
        return TenetManager.getTenet(id);
    }
    @Override
    public JsonObject serialize() {
        JsonObject object = new JsonObject();
        object.addProperty(TYPE_VARIABLE_NAME, TENET_SR_TYPE);
        object.addProperty("id", id);
        object.addProperty("group", group.getID());
        return null;
    }
    public Tenet get(){
        return tenet.get();
    }
    public static TenetReference deserialize(JsonObject json){
        String id = json.get("id").getAsString();
        String groupID = json.get("group").getAsString();
        return new TenetReference(id,groupID);
    }
    public static  TenetReference of(Tenet te){
        return new TenetReference(te);
    }
}
