package com.objects.culture.Influencers;

import com.base.reference.DMEReference;
import com.google.gson.JsonObject;
import com.objects.culture.Culture;
import com.utilities.serialization.JsonSerializable;
import com.utilities.serialization.RegistrySerialManager;
import com.utilities.serialization.RegistrySerializable;

public record Influencer(DMEReference<Culture> culture,InfluencerRelationship relationship, InfluencerWeight weight) implements RegistrySerializable {
    public Influencer(DMEReference<Culture> culture,InfluencerRelationship relationship, InfluencerWeight weight) {
        this.culture = culture;
        this.relationship = relationship;
        this.weight = weight;
    }

    @Override
    public void toJson(JsonObject json) {
        json.add("culture", culture.serialize());
        json.addProperty("relationship", relationship.name());
        json.add("weight", weight.serialize());
    }

}
