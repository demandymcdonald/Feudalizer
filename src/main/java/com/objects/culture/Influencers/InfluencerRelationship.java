package com.objects.culture.Influencers;

import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.object.reference.COReference;

import java.util.function.BiPredicate;

public abstract class InfluencerRelationship {
    final InfluencerWeight defaultWeight;

    public InfluencerRelationship(InfluencerWeight defaultWeight){
        this.defaultWeight = defaultWeight;
    }
    public abstract boolean shouldRemove(COReference<?> influencer, COReference<?> influenced);



    public BiPredicate<COReference<?>,COReference<?>> getPredicate(){
        return this::shouldRemove;
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
    public InfluencerInstance makeInstance(boolean isProcedural){
        return new InfluencerInstance(this,isProcedural);
    }
}
