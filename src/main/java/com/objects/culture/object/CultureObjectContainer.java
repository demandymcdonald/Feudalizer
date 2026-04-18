package com.objects.culture.object;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.multi.MiddlemanMap;
import com.base.timeline.change.multi.TLMap;
import com.base.utilities.TLSyncedCache;
import com.objects.culture.Influencers.InfluencerInstance;
import com.objects.culture.Influencers.InfluencerRelationship;
import com.objects.culture.object.compass.InterpolatedPoliticalCompass;
import com.objects.culture.tenet.instance.TenetInstance;
import com.objects.culture.tenet.TenetManager;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.TenetReference;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CultureObjectContainer<T extends DateMutableEntity<T> & CultureObject<T>>{
    DMEReference<T> reference;
    Pair<COReference<?>, InfluencerRelationship> parent;
    TLMap<COReference<?>,InfluencerInstance> influencers;
    TLMap<TenetReference,TenetInstance<T>> opinions;
    InterpolatedPoliticalCompass<T> compass;
    TLSyncedCache<TenetReference, Double> influencedCache = new TLSyncedCache<>(50L, TimeUnit.MINUTES,10L,null);


    public TenetInstance<T> getOpinion(TenetReference tenet){
        return opinions.get(tenet);
    }


    public List<TenetReference> getByGroup(TenetGroup group){
        List<TenetReference> result = new ArrayList<>();
        for(TenetReference t : opinions.getKeys()){
            if (t.getGroup() == group){
                result.add(t);
            }
        }
        return result;
    }
    public List<TenetReference> getByPillar(TenetManager.Group.Pillar tenet){
        return getByGroup(tenet.getGroup());
    }
    public List<TenetReference> getByGroupTree(TenetGroup group){
        List<TenetReference> result = new ArrayList<>();
        for(TenetReference t : opinions.getKeys()){
            if (t.getGroup().isDescendantOf(group)){
                result.add(t);
            }
        }
        return result;
    }
    public List<TenetReference> getPillarTree(TenetManager.Group.Pillar tenet){
        return getByGroupTree(tenet.getGroup());
    }



    public CultureObjectContainer(DMEReference<T> reference){
        this.reference = reference;
    }

    public InterpolatedPoliticalCompass<T> getCompass() {
        return compass;
    }

    public void setCompass(InterpolatedPoliticalCompass<T> compass) {
        this.compass = compass;
    }

    public TLSyncedCache<TenetReference, Double> getInfluencedCache() {
        return influencedCache;
    }

    public void setInfluencedCache(TLSyncedCache<TenetReference, Double> influencedCache) {
        this.influencedCache = influencedCache;
    }

    public TLMap<COReference<?>,InfluencerInstance> getInfluencers() {
        return influencers;
    }

    public void setInfluencers(TLMap<COReference<?>,InfluencerInstance> influencers) {
        this.influencers = influencers;
    }

    public TLMap<TenetReference,TenetInstance<T>> getOpinions() {
        return opinions;
    }

    public void setOpinions(TLMap<TenetReference,TenetInstance<T>> opinions) {
        this.opinions = opinions;
    }

    public Pair<COReference<?>, InfluencerRelationship> getParent() {
        return parent;
    }
    public void setParent(COReference<?> influencer, InfluencerRelationship relationship){
        this.parent = Pair.of(influencer,relationship);
    }
    public void setParent(Pair<COReference<?>, InfluencerRelationship> parent) {
        this.parent = parent;
    }

    public DMEReference<T> getReference() {
        return reference;
    }

    public void setReference(DMEReference<T> reference) {
        this.reference = reference;
    }
}
