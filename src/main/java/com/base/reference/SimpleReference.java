package com.base.reference;

import com.google.gson.JsonObject;

public class SimpleReference extends StateReference {
    public static final String SIMPLE_SR_TYPE = "SimpleReference";
    public final String String;
    public SimpleReference(String string) {
        this.String = string;
    }
    @Override
    public String parse() {
        return String;
    }
    public static SimpleReference of(String string){
        return new SimpleReference(string);
    }

    @Override
    public JsonObject serialize() {
        JsonObject object = new JsonObject();
        object.addProperty(TYPE_VARIABLE_NAME,SIMPLE_SR_TYPE);
        object.addProperty("String", String);
        return object;
    }

    public static SimpleReference deserialize(JsonObject json){
        return new SimpleReference(json.get("String").getAsString());
    }
}
