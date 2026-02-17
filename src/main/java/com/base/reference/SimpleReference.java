package com.base.reference;

import com.google.gson.JsonObject;

public class SimpleReference extends StateReference {
    public final String String;
    public SimpleReference(String string) {
        this.String = string;
    }
    @Override
    public String parse() {
        return String;
    }

    @Override
    public JsonObject serialize() {
        JsonObject object = new JsonObject();
        object.addProperty("Type","SimpleReference");
        object.addProperty("String", String);
        return object;
    }

    public static SimpleReference deserialize(JsonObject json){
        return new SimpleReference(json.get("String").getAsString());
    }
}
