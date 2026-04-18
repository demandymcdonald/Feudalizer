package com.utilities.serialization;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.objects.culture.Influencers.InfluencerInstance;
import com.objects.culture.Influencers.InfluencerRelationship;
import com.objects.culture.Influencers.InfluencerWeight;
import com.objects.culture.tenet.TenetManager;
import com.objects.culture.tenet.group.TenetGroup;
import com.utilities.number.BoundedInteger;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class RegistrySerialManager {
    private static final Map<Class<? extends RegistrySerializable>, Function<JsonObject,? extends RegistrySerializable>> registry = new HashMap<>();

    static {
        registry.put(InfluencerInstance.class, RegistrySerialManager::influencerFrom);
        registry.put(InfluencerWeight.class, RegistrySerialManager::influencerWeightFrom);
    }
    private static <T extends RegistrySerializable> T get(Class<T> classRef, JsonObject json){
        Function<JsonObject,T> factory = (Function<JsonObject, T>) registry.get(classRef);
        if (factory == null){
            throw new RuntimeException("No factory registered for " + classRef.getName());
        }
        return factory.apply(json);
    }
    public static <T extends RegistrySerializable> void register(Class<? extends T> classRef, Function<JsonObject,T> factory){
        if (!registry.containsKey(classRef)){
            registry.put(classRef,factory);
        }
    }
    public static <T extends RegistrySerializable> T deserialize(JsonObject json){
        final String type = json.getAsJsonObject("type").getAsString();
        JsonObject payload = json.getAsJsonObject("payload");
        try {
            Class<T> classRef = (Class<T>) Class.forName(type);
            return get(classRef, payload);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found for deserialization", e);
        } catch (Exception e) {
            throw new RuntimeException("Exception deserializing", e);
        }
    }
    private static InfluencerInstance influencerFrom(JsonObject json){

        InfluencerRelationship relationship = InfluencerRelationship.valueOf(json.get("relationship").getAsString());
        InfluencerWeight weight = RegistrySerialManager.deserialize(json.get("weight").getAsJsonObject());
        return new InfluencerInstance(relationship,weight);
    }
    private static InfluencerWeight influencerWeightFrom(JsonObject json){
        JsonArray array = json.getAsJsonArray("influencerWeights");
        Map<TenetGroup, BoundedInteger> map = new HashMap<>();
        for (JsonElement element : array) {
            JsonObject obj = element.getAsJsonObject();
            BoundedInteger bounded = new BoundedInteger(-100,100);
            bounded.set(obj.get("weight").getAsInt());
            map.put(TenetManager.getGroup(obj.get("group").getAsString()), bounded);
        }
        return new InfluencerWeight(map);
    }
}
