package com.objects.culture.Influencers;

import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.instance.CultObjReference;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public enum InfluencerRelationship {




    ;

    private final InfluencerWeight defaultWeight;
    InfluencerRelationship(boolean isProcedural, InfluencerWeight defaultWeight, @Nullable Predicate<CultObjReference<?>> shouldRemove){
        this.defaultWeight = defaultWeight;
    }
    public boolean hasWeightFor(TenetGroup group){
        return defaultWeight.map().containsKey(group);
    }
    public int getWeight(TenetGroup group){
        return defaultWeight.get(group);
    }
    public InfluencerWeight getDefaultWeight(){
        return defaultWeight.clone();
    }
}
