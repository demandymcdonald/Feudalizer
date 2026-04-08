package com.objects.culture.Influencers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.objects.culture.tenet.group.TenetGroup;
import com.utilities.number.BoundedInteger;
import com.utilities.serialization.RegistrySerializable;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public record InfluencerWeight(Map<TenetGroup, BoundedInteger> map) implements RegistrySerializable, Cloneable {
    public InfluencerWeight(Pair<TenetGroup,BoundedInteger>... initial) {
        this(makeMap(initial));
    }

    public void add(TenetGroup group, int amount) {
        map.getOrDefault(group, makeNew()).set(amount);
    }
    public int get(TenetGroup group){
        return map.getOrDefault(group, makeNew()).get();
    }


    private static BoundedInteger makeNew(){
        BoundedInteger bounded = new BoundedInteger(-100,100);
        bounded.set(0);
        return bounded;
    }
    private static Map<TenetGroup, BoundedInteger> makeMap(Pair<TenetGroup,BoundedInteger>... initial){
        Map<TenetGroup, BoundedInteger> map = new HashMap<>();
        for (Pair<TenetGroup, BoundedInteger> pair : initial) {
            map.put(pair.getLeft(), pair.getRight());
        }
        return map;
    }

    @Override
    public void toJson(JsonObject json) {
        JsonArray array = new JsonArray();
        for (Map.Entry<TenetGroup, BoundedInteger> entry : map.entrySet()) {
            JsonObject obj = new JsonObject();
            obj.addProperty("group", entry.getKey().name());
            obj.addProperty("weight", entry.getValue().get());
            array.add(obj);
        }
        json.add("influencerWeights", array);
    }

    @Override
    public InfluencerWeight clone(){
        Map<TenetGroup, BoundedInteger> newMap = new HashMap<>();
        for (Map.Entry<TenetGroup, BoundedInteger> entry : map.entrySet()) {
            BoundedInteger bounded = makeNew();
            bounded.set(entry.getValue().get());
            newMap.put(entry.getKey(), bounded);
        }
        return new InfluencerWeight(newMap);
    }
}
