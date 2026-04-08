package com.objects.culture;

import com.objects.culture.Influencers.InfluencerWeight;

public enum InfluencerType {
    ;

    private final InfluencerWeight defaultWeights;

    InfluencerType(InfluencerWeight defaultWeights){
        this.defaultWeights = defaultWeights;
    }
    public InfluencerWeight getDefaultWeights(){
        return defaultWeights.clone();
    }
}
