package com.base.reference;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.helpers.MessageFormatter;

import java.util.ArrayList;
import java.util.List;

public class    ComplexReference extends StateReference{
    public static final String COMPLEX_SR_TYPE = "ComplexReference";
    private final List<Object> vars = new ArrayList<>();
    private final String string;
    public ComplexReference(String string, Object... vars) {
        this.string = string;
        this.vars.addAll(List.of(vars));
    }


    @Override
    public String parse() {
        return MessageFormatter.basicArrayFormat(string, vars.toArray());
    }

    @Override
    public JsonObject serialize() {
        JsonObject json = new JsonObject();
        json.addProperty(TYPE_VARIABLE_NAME, COMPLEX_SR_TYPE);
        json.addProperty("string", string);
        json.add("vars", packVars());
        return json;
    }
    private JsonArray packVars(){
        JsonArray vars = new JsonArray();
        for (Object var : this.vars) {
            JsonObject j = new JsonObject();
            if (var instanceof StateReference sr){

                j.addProperty("type", "StateReference");
                j.add("value", sr.serialize());

            } else {
                j.addProperty("type", "String");
                j.addProperty("value", var.toString());
            }
            vars.add(j);
        }
        return vars;
    }
    private static List<Object> unpackVars(JsonArray vars){
        List<Object> result = new ArrayList<>();
        for (JsonElement var : vars){
            JsonObject j = var.getAsJsonObject();
            if (j.get("type").getAsString().equals("StateReference")){
                result.add(StateReference.fromJson(j.get("value").getAsJsonObject()));
            } else {
                result.add(j.get("value").getAsString());
            }
        }
        return result;
    }
    public static ComplexReference of(String string, Object... vars){
        return new ComplexReference(string,vars);
    }
    public static ComplexReference deserialize(JsonObject json){
        String string = json.get("string").getAsString();
        JsonArray vars = json.getAsJsonArray("vars");
        return new ComplexReference(string,unpackVars(vars));
    }
}
