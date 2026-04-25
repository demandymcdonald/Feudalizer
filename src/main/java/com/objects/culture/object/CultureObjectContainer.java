package com.objects.culture.object;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.multi.wrapper.TLMap;
import com.base.timeline.change.multi.wrapper.TLSet;
import com.base.utilities.TLSyncedCache;
import com.objects.culture.Influencers.InfluencerInstance;
import com.objects.culture.Influencers.InfluencerRelationship;
import com.objects.culture.object.compass.InterpolatedPoliticalCompass;
import com.objects.culture.object.reference.COReference;
import com.objects.culture.tenet.AcceptanceContainer;
import com.objects.culture.tenet.Tenet;
import com.objects.culture.tenet.instance.TenetInstance;
import com.objects.culture.tenet.TenetManager;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.TenetReference;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class CultureObjectContainer<T extends DateMutableEntity<T> & CultureObject<T>> implements CultureObjectVars{

    DMEReference<T> reference;
    Pair<COReference<?>, InfluencerInstance> parent;
    TLMap<COReference<?>,InfluencerInstance> influencers;
    TLSet<TenetInstance<T>> opinions;
    InterpolatedPoliticalCompass<T> compass;
    TLSyncedCache<TenetReference, Double> influencedCache = new TLSyncedCache<>(50L, TimeUnit.MINUTES,10L,null);
    public CultureObjectContainer(DMEReference<T> reference){
        this.reference = reference;
    }

    public TenetInstance<T> getOpinion(TenetReference tenet){
        return opinions.getWhere((ti) -> ti.getTenet().equals(tenet)).stream().findFirst().orElse(null);
    }
    public Set<TenetInstance<T>> getByGroup(TenetGroup group, boolean includeDescendants){
        Set<TenetInstance<T>> result = new HashSet<>();
        for(TenetInstance<T> t : opinions.asSet()){
            TenetGroup oGroup = t.getTenet().getGroup();
            if (oGroup.equals(group)){
                result.add(t);
            } else if(includeDescendants && oGroup.isDescendantOf(group)){
                result.add(t);
            }
        }
        return result;
    }
    public Set<TenetInstance<T>> getByGroupTree(TenetGroup group){
        return getByGroup(group,true);
    }
    public Set<TenetInstance<T>> getPillarTree(TenetManager.Group.Pillar tenet){
        return getByGroupTree(tenet.getGroup());
    }
    protected TenetInstance<T> getTenetInstance(TenetReference tr){
        return opinions.getWhere((ti) -> ti.getTenet().equals(tr)).stream().findFirst().orElse(null);
    }
    public AcceptanceContainer getOpinionTenet(TenetReference tenet, boolean includeInfluencers){
        return getOpinionTenet(tenet,includeInfluencers,new HashSet<>());
    }
    public AcceptanceContainer getOpinionTenet(TenetReference tenet, boolean includeInfluencers, Set<COReference<?>> blacklist){
        T self = reference.get();
        double acceptance = getBase(tenet);
        if(includeInfluencers){
            blacklist.add(self.getTOReference());
            final double rawAcceptance = acceptance;
            final double resistance = calculateResistance(rawAcceptance);
            final double influencerPercentage = 1 - resistance;
            List<Double> influencerFactors = CalculateInfluencerFactor(influencerPercentage,blacklist,self,tenet);
            for (Double d : influencerFactors) {
                if (d > 0){
                    acceptance += (d - acceptance);
                } else {
                    acceptance += (acceptance - d);
                }
            }
        }
        return new AcceptanceContainer(acceptance);
    }
    private double getBase(TenetReference search){
        TenetInstance<T> opinion = getTenetInstance(search);
        if (opinion != null){
            return opinion.getAcceptance().getValue();
        } else {
            return this.getCompass().getCompatibilityValue(search.get().getCompass(),false);
        }
    }
    private double calculateResistance(double opinion) {
        double abs = Math.abs(opinion);
        double peak = (1 - z) * oc; // zealotry peak in absolute terms
        if (abs <= peak) {
            double quad = Math.pow(abs / oc, pf);
            double apathy = b * Math.exp(-c * abs * abs);
            return Math.max(floor, quad + apathy);
        } else {
            // resistance at the peak point
            double peakVal = Math.pow(peak / oc, pf) + b * Math.exp(-c * peak * peak);
            peakVal = Math.max(floor, peakVal);
            // ease from peakVal down to f (drop target) at oc
            double t = (abs - peak) / (oc - peak);
            double eased = Math.pow(t, g); // g is now steepness, not sharpness
            return Math.clamp((peakVal + (f - peakVal) * eased), floor,1);
        }
    }

    private List<Double> CalculateInfluencerFactor(double influencerShare, Set<COReference<?>> blacklist,
                                                   T self, TenetReference tenet) {
        Pair<Map<Double,Double>,Double> extracted = extract(this.getInfluencers().getMutableMap(), blacklist, self, tenet);
        Map<Integer,Pair<Double,Double>> normalized = normalizeFirst(extracted, influencerShare);
        return finalize(normalized);
    }
    private Pair<Map<Double,Double>,Double> extract(Map<COReference<?>,InfluencerInstance> iMap, Set<COReference<?>> blacklist,
                                                    T self, TenetReference tenet){
        Map<Double,Double> influencerMap = new HashMap<>();
        double opinionSum = 0;
        if (parent != null){
            iMap.put(parent.getKey(),parent.getValue());
        }
        for(COReference<?> influencer : iMap.keySet()){
            AcceptanceContainer raw;

            double factor = iMap.get(influencer).weight().get(tenet.getGroup()) * (1 + Math.clamp(self.influencerResistance(influencer),-1,1));
            if (blacklist.contains(influencer)) {
                raw = influencer.get().getOpinion(tenet, false, new HashSet<>());
            } else {
                raw = influencer.get().getOpinion(tenet, true, blacklist);
            }
            opinionSum += raw.value();
            influencerMap.put(raw.value(), factor);
        }
        return Pair.of(influencerMap,opinionSum);
    }
    private static Map<Integer,Pair<Double,Double>> normalizeFirst(Pair<Map<Double,Double>,Double> extracted, double influencerShare){
        double totalNormalized = extracted.getRight() * influencerShare;
        Map<Integer,Pair<Double,Double>> normalized = new TreeMap<>();
        int counter = 0;
        for(Map.Entry<Double,Double> influencer : extracted.getLeft().entrySet()){
            double base = influencer.getKey();
            double v = Math.pow(Math.abs(base) / totalNormalized,pf);
            if (base < 0){
                v *= -1;
            }
            normalized.put(counter,Pair.of(v,influencer.getValue()));
            counter++;
        }
        return normalized;
    }
    private static List<Double> finalize(Map<Integer,Pair<Double,Double>> entries){
        double sum = 0;
        for (Pair<Double,Double> d : entries.values()) {
            sum += Math.abs(d.getRight());
        }
        List<Double> normalized = new ArrayList<>();
        for (Pair<Double,Double> d : entries.values()) {
            normalized.add(d.getLeft() * (d.getRight() / sum));
        }
        return normalized;
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

    public TLSet<TenetInstance<T>> getOpinions() {
        return opinions;
    }

    public void setOpinions(TLSet<TenetInstance<T>> opinions) {
        this.opinions = opinions;
    }

    public Pair<COReference<?>, InfluencerInstance> getParent() {
        return parent;
    }
    public void setParent(COReference<?> influencer, InfluencerRelationship relationship){
        this.parent = Pair.of(influencer,relationship.makeInstance(false));
    }
    public void setParent(Pair<COReference<?>, InfluencerInstance> parent) {
        this.parent = parent;
    }

    public DMEReference<T> getReference() {
        return reference;
    }

    public void setReference(DMEReference<T> reference) {
        this.reference = reference;
    }

    public void invalidateCache(TenetReference t){
        getInfluencedCache().invalidate(t);
    }
    public void invalidateCache(){
        getInfluencedCache().invalidateAll();
    }
}
