package com.objects.culture.Influencers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.group.groups.*;
import com.utilities.number.bound_int.BoundInt;
import com.utilities.number.bound_int.BoundInts;
import com.utilities.serialization.RegistrySerializable;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public record InfluencerWeight(Map<TenetGroup, BoundInt> map) implements RegistrySerializable, Cloneable {
    public InfluencerWeight(Pair<TenetGroup,BoundInt>... initial) {
        this(makeMap(initial));
    }

    public void add(TenetGroup group, int amount) {
        map.getOrDefault(group, makeNew()).set(amount);
    }
    public void amend(TenetGroup group, int amount){
        if (!map.containsKey(group)) {
            add(group, amount);
        } else {
            map.get(group).add(amount);
        }
    }
    public int get(TenetGroup group){
        return map.getOrDefault(group, makeNew()).get();
    }


    private static BoundInt makeNew(){
        BoundInt bounded = BoundInts.Percent(true);
        bounded.set(0);
        return bounded;
    }
    private static Map<TenetGroup, BoundInt> makeMap(Pair<TenetGroup,BoundInt>... initial){
        Map<TenetGroup, BoundInt> map = new HashMap<>();
        for (Pair<TenetGroup, BoundInt> pair : initial) {
            map.put(pair.getLeft(), pair.getRight());
        }
        return map;
    }

    @Override
    public void toJson(JsonObject json) {
        JsonArray array = new JsonArray();
        for (Map.Entry<TenetGroup, BoundInt> entry : map.entrySet()) {
            JsonObject obj = new JsonObject();
            obj.addProperty("group", entry.getKey().name());
            obj.addProperty("weight", entry.getValue().get());
            array.add(obj);
        }
        json.add("influencerWeights", array);
    }

    @Override
    public InfluencerWeight clone(){
        Map<TenetGroup, BoundInt> newMap = new HashMap<>();
        for (Map.Entry<TenetGroup, BoundInt> entry : map.entrySet()) {
            BoundInt bounded = makeNew();
            bounded.set(entry.getValue().get());
            newMap.put(entry.getKey(), bounded);
        }
        return new InfluencerWeight(newMap);
    }
    public static Builder Builder(){
        return new Builder();
    }
    public static class Builder{
        Map<TenetGroup, BoundInt> map = new HashMap<>();

        public Builder setEconomy(int amount){
            map.put(EconomicGroups.ECONOMY, makeNew().set(amount));
            return this;
        }
        public Builder setGovernment(int amount){
            map.put(GovernmentGroups.RELIGIOUS, makeNew().set(amount));
            return this;
        }
        public Builder setGovernmentRights(int amount){
            map.put(GovernmentGroups.POPULATION_GROUP_RIGHTS, makeNew().set(amount));
            return this;
        }
        public Builder set(int amount){
            map.put(GovernmentGroups.POPULATION_GROUP_RIGHTS, makeNew().set(amount));
            return this;
        }
        public Builder setReligion(int amount){
            map.put(ReligionGroups.RELIGION, makeNew().set(amount));
            return this;
        }
        public Builder setMilitary(int amount){
            map.put(MilitaryGroups.MILITARY, makeNew().set(amount));
            return this;
        }
        public Builder setSociety(int amount){
            map.put(SocietyGroups.SOCIETY, makeNew().set(amount));
            return this;
        }
        public Builder setSocialNorms(int amount){
            map.put(SocietyGroups.SOCIAL_ATTITUDE, makeNew().set(amount));
            return this;
        }
        public Builder setFamily(int amount){
            map.put(FamilyGroups.FAMILY, makeNew().set(amount));
            return this;
        }
        public Builder setEducation(int amount){
            map.put(EducationGroups.EDUCATION, makeNew().set(amount));
            return this;
        }
        public Builder setCustom(TenetGroup group, int amount){
            map.put(group, makeNew().set(amount));
            return this;
        }
        public InfluencerWeight build(){
            return new InfluencerWeight(map);
        }
    }

}
