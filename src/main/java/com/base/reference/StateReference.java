package com.base.reference;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public abstract class StateReference {
    public abstract String parse();
    public abstract JsonObject serialize();
    public static StateReference[] buildArray(JsonArray array){
        List<StateReference> ref = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JsonObject o = array.get(i).getAsJsonObject();
            String type = o.get("Type").getAsString();
            switch (type){
                case "DMEReference": {
                    ref.add(DMEReference.deserialize(o));
                    break;
                }
                case "SimpleReference": {
                    ref.add(SimpleReference.deserialize(o));
                    break;
                }
                case "CompoundSR": {
                    ref.add(CompoundSR.deserialize(o));
                    break;
                }
            }
        }
        return ref.toArray(new DMEReference<?>[ref.size()]);
    }
    public static JsonArray buildArray(StateReference... ref){
        JsonArray array = new JsonArray();
        for (StateReference ref1 : ref) {
            array.add(ref1.serialize());
        }
        return array;
    }


}
