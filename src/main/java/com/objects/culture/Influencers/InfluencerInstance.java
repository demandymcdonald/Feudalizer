package com.objects.culture.Influencers;

import com.google.gson.JsonObject;
import com.objects.culture.tenet.group.TenetGroup;
import com.utilities.serialization.RegistrySerializable;

public record InfluencerInstance(InfluencerRelationship relationship, InfluencerWeight weight) implements RegistrySerializable {
    public InfluencerInstance(InfluencerRelationship relationship, InfluencerWeight weight) {
        this.relationship = relationship;
        this.weight = weight;
    }
    public InfluencerInstance(InfluencerRelationship relationship) {
        this(relationship, relationship.getDefaultWeight());
    }
    public void set(TenetGroup group, int amount){
        weight.add(group,amount);
    }
    public void modify(TenetGroup group, int amount){
        //int i = weight.get(group);
        weight.amend(group,amount);
    }
    @Override
    public void toJson(JsonObject json) {
        json.addProperty("relationship", relationship.name());
        json.add("weight", weight.serialize());
    }

}
