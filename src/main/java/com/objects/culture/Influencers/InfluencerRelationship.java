package com.objects.culture.Influencers;

import java.util.function.Supplier;

public enum InfluencerRelationship {




    ;

    private final InfluencerWeight defaultWeight;
    InfluencerRelationship(InfluencerWeight defaultWeight){
        this.defaultWeight = defaultWeight;
    }

    public InfluencerWeight getDefaultWeight(){
        return defaultWeight.clone();
    }
}
