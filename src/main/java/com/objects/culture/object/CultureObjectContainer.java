package com.objects.culture.object;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.multi.TimelineMap;
import com.base.utilities.TLSyncedCache;
import com.objects.culture.Influencers.InfluencerInstance;
import com.objects.culture.Influencers.InfluencerRelationship;
import com.objects.culture.object.compass.InterpolatedPoliticalCompass;
import com.objects.culture.object.instance.TenetInstance;
import com.objects.culture.tenet.TenetManager;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.reference.TenetReference;
import com.objects.culture.tenet.instance.TOReference;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CultureObjectContainer<T extends DateMutableEntity<T> & CultureObject<T>>{
    DMEReference<T> reference;
    Pair<TOReference<?>, InfluencerRelationship> parent;
    TimelineMap<TOReference<?>, InfluencerInstance, T> influencers;
    TimelineMap<TenetReference, TenetInstance<T>, T> opinions;
    InterpolatedPoliticalCompass<T> compass;
    TLSyncedCache<TenetReference, Double> influencedCache = new TLSyncedCache<>(50L, TimeUnit.MINUTES,10L,null);


    public TenetInstance<T> getOpinion(TenetReference tenet){
        return opinions.get(tenet);
    }


    public List<TenetReference> getByGroup(TenetGroup group){
        List<TenetReference> result = new ArrayList<>();
        for(TenetReference t : opinions.keySet()){
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
        for(TenetReference t : opinions.keySet()){
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

    public TimelineMap<TOReference<?>, InfluencerInstance, T> getInfluencers() {
        return influencers;
    }

    public void setInfluencers(TimelineMap<TOReference<?>, InfluencerInstance, T> influencers) {
        this.influencers = influencers;
    }

    public TimelineMap<TenetReference, TenetInstance<T>, T> getOpinions() {
        return opinions;
    }

    public void setOpinions(TimelineMap<TenetReference, TenetInstance<T>, T> opinions) {
        this.opinions = opinions;
    }

    public Pair<TOReference<?>, InfluencerRelationship> getParent() {
        return parent;
    }
    public void setParent(TOReference<?> influencer, InfluencerRelationship relationship){
        this.parent = Pair.of(influencer,relationship);
    }
    public void setParent(Pair<TOReference<?>, InfluencerRelationship> parent) {
        this.parent = parent;
    }

    public DMEReference<T> getReference() {
        return reference;
    }

    public void setReference(DMEReference<T> reference) {
        this.reference = reference;
    }
}
