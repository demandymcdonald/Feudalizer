package com.base.reference;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.objects.culture.tenet.reference.TenetReference;

import java.util.ArrayList;
import java.util.List;

import static com.base.reference.ComplexReference.COMPLEX_SR_TYPE;
import static com.base.reference.CompoundSR.COMPOUND_SR_TYPE;
import static com.base.reference.DMEReference.DME_SR_TYPE;
import static com.base.reference.SimpleReference.SIMPLE_SR_TYPE;
import static com.objects.culture.tenet.reference.TenetReference.TENET_SR_TYPE;

public abstract class StateReference {
    public static final String TYPE_VARIABLE_NAME = "type";
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
//                case "CompoundSR": {
//                    //ref.add(CompoundSR.deserialize(o));
//                    //break;
//                }
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
    public static <T extends StateReference> T fromJson(JsonObject json){
        String type = json.get(TYPE_VARIABLE_NAME).getAsString();
        switch (type){
            case DME_SR_TYPE: {
                return (T) DMEReference.deserialize(json);
            }
            case SIMPLE_SR_TYPE: {
                return (T) SimpleReference.deserialize(json);
            }
//            case COMPOUND_SR_TYPE: {
//                return (T) CompoundSR.deserializeCompound(json);
//            }
            case COMPLEX_SR_TYPE: {
                return (T) ComplexReference.deserialize(json);
            }
            case TENET_SR_TYPE: {
                return (T) TenetReference.deserialize(json);
            }
        }
    }


}
