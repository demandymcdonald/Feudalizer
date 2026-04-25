package com.objects.character.opinion;

import com.base.reference.ComplexReference;
import com.base.reference.DMEReference;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.objects.character.LivingCreature;
import com.utilities.serialization.CompressString;

import javax.annotation.Nullable;
import java.util.Objects;

public class OpinionReference {
    private final DMEReference<? extends LivingCreature<?>> source;
    private final DMEReference<? extends LivingCreature<?>> target;
    private final OpinionString string;
    public OpinionReference(OpinionString string, DMEReference<? extends LivingCreature<?>> source, DMEReference<? extends LivingCreature<?>> target) {
        this.source = source;
        this.target = target;
        this.string = string;
    }
    @Override
    public String toString() {
        return string.parse(source, target);
    }
    public OpinionString getString() {
        return string;
    }
    public String toSerializedString(){
        JsonObject object = new JsonObject();
        object.add("source", source.serialize());
        object.add("target", target.serialize());
        object.add("opinion",string.toJson());
        return CompressString.compress(object.getAsString());
    }
    public static OpinionReference fromSerializedString(String element){
        JsonObject object = JsonParser.parseString(Objects.requireNonNull(CompressString.decompress(element))).getAsJsonObject();
        DMEReference<? extends LivingCreature<?>> source = DMEReference.fromJson(object);
        DMEReference<? extends LivingCreature<?>> target = DMEReference.fromJson(object);
        OpinionString string = OpinionString.fromJson(object);
        return new OpinionReference(string, source, target);
    }


    public record OpinionString(String text, String sourceKey, String targetKey) {


        public String parse(DMEReference<? extends LivingCreature<?>> source, DMEReference<? extends LivingCreature<?>> target){
            return text.replace(sourceKey,source.get().toString()).replace(targetKey,target.get().toString());
        }

        public JsonElement toJson(){
            JsonObject object = new JsonObject();
            object.addProperty("txt",text);
            object.addProperty("src",sourceKey);
            object.addProperty("tgt",targetKey);
            return object;
        }
        public static OpinionString fromJson(JsonObject object){
            String text = object.get("txt").getAsString();
            String sourceKey = object.get("src").getAsString();
            String targetKey = object.get("tgt").getAsString();
            return new OpinionString(text,sourceKey,targetKey);
        }
    }
}
