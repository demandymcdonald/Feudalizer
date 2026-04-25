package com.objects.culture.Influencers;

import com.google.gson.JsonObject;
import com.objects.culture.tenet.group.TenetGroup;
import com.utilities.serialization.RegistrySerializable;
import org.apache.commons.lang3.mutable.MutableBoolean;

public record InfluencerInstance(InfluencerRelationship relationship, MutableBoolean isProcedural, InfluencerWeight weight) implements RegistrySerializable {
    public InfluencerInstance(InfluencerRelationship relationship,MutableBoolean isProcedural, InfluencerWeight weight) {
        this.relationship = relationship;
        this.weight = weight;
        this.isProcedural = isProcedural;
    }
    public InfluencerInstance(InfluencerRelationship relationship, InfluencerWeight weight) {
        this(relationship, new MutableBoolean(false), weight);
    }
    public InfluencerInstance(InfluencerRelationship relationship, boolean isProcedural) {
        this(relationship,new MutableBoolean(isProcedural),relationship.getDefaultWeight());
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
        json.addProperty("relationship", relationship.get());
        json.add("weight", weight.serialize());
    }

}
