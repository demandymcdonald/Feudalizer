package com.objects.culture.object;

import com.Global;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.multi.TimelineMap;
import com.base.utilities.TLSyncedCache;
import com.objects.culture.Influencers.InfluencerInstance;
import com.objects.culture.Influencers.InfluencerRelationship;
import com.objects.culture.object.compass.InterpolatedPoliticalCompass;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.object.compass.IPoliticalCompass;
import com.objects.culture.tenet.TenetReference;
import com.objects.culture.tenet.instance.TOReference;
import com.objects.culture.object.instance.TenetInstance;
import com.objects.culture.object.compass.CompassChange;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.types.Tenet;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

import static com.objects.culture.tenet.Acceptance.MAX_VALUE;
import static com.objects.culture.tenet.Acceptance.get;

public interface CultureObject<T extends DateMutableEntity<T> & CultureObject<T>> extends ICultureObject{
    static final double b = .23; //apathy peak as percent from start
    static final double c = 0.00022; //apathy decay
    static final double z = .125; // zealotry peak as percent from end. Should hit right as the they pass the Fanatic mark
    static final double f = .69; // zealotry drop-off  target
    static final double g = 2.2; //zealotry drop-off steepness
    static final double k = .02; //kernal floor
    static final int pf = 2; //crushing power for normalization
    static final int oc = MAX_VALUE; // upper and lower bound for opinion values



    void updateProceduralInfluencers(); //Idea here is that the code can automatically add and remove procedural influencers (like dead people, or new lieges)
    double influencerResistance(TOReference<?> influencer); // clamped between -1 and 1. Used to model things like personal opinion of an influencer for people -> people influencer,
    // or situations where a person might have less influence than the relationship suggests (power imbalance, for example).
    DMEReference<T> getReference();
    default TOReference<T> getTOReference(){
        //Weird, I know. All TenetOpinionated are DMEs, so if this class is running, it's on a DME, and the reference
        // will work becuase TOReferences are just janky wrappers for DMEReferences.
        return new TOReference<>(getReference());
    }
    default Acceptance getAcceptance(Tenet tenet, boolean includeInfluencers){
        return Acceptance.get((int) Math.round(getAcceptanceValue(tenet, includeInfluencers)));
    }
    default double getAcceptanceValue(Tenet tenet, boolean includeInfluencers){
        return getAcceptanceValue(tenet, includeInfluencers, new TOReference[0]);
    }
    default double getAcceptanceValue(Tenet tenet, boolean includeInfluencers, TOReference<?>... bls){
        TenetReference ref = new TenetReference(tenet);
        Map<TenetReference,TenetInstance<T>> opinions = getOpinions();
        double acceptance = 0;
        if (opinions.containsKey(ref)){
            acceptance = opinions.get(ref).get();
        } else {
            acceptance =  tenet.getCompassEntry().getCompatibilityValue(getCompass());
        }
        List<TOReference<?>> blacklist = new ArrayList<>(List.of(bls));
        blacklist.add(this.getTOReference()); //prevents recursion might rewrite the influencer code to settle instead of looping like this, but idk.
        if (this.getParentObject().isPresent()){
            Pair<TOReference<?>, InfluencerRelationship> parentPair = this.getParentObject().get();
            TOReference<?> parent = parentPair.getLeft();
            InfluencerRelationship parentRel = parentPair.getRight();
            if (parentRel.hasWeightFor(tenet.getGroup()) && parentRel.getWeight(tenet.getGroup()) == 1){ //Simulated hegamonic control, especially used on the systemic side of
                return parent.get().getAcceptanceValue(tenet,includeInfluencers,blacklist.toArray(TOReference[]::new));
            }
        }
        if (includeInfluencers){
            Double val = getInfluencedCache().get(ref);
            if (val != null){
                return val;
            }
            val = AdjustForInfluence((T) this,tenet,true,acceptance,blacklist.toArray(TOReference[]::new));
            getInfluencedCache().put(ref,val);
            return val;
        }
        return acceptance;
    }

    default void amendCompass(Pair<IPoliticalCompass.Axis,Integer>... values){
        InterpolatedPoliticalCompass<?> compass = getCompass();
        boolean changed = false;
        for (Pair<IPoliticalCompass.Axis,Integer> pair : values) {
            if (pair.getLeft() == null || (pair.getRight() == null || pair.getRight() == 0)){
                continue;
            }
            changed = true;
            compass.amendCompass(pair.getLeft(),pair.getRight());
        }
        if (changed){
            //DMEReference<? extends T> owner = ;
            getReference().get().getTimeline().addChange(new CompassChange<>(getReference(), Global.getDate(),getCompass()));
            invalidateCache();
        }
    }
    default void addOpinion(TenetReference tenet, double d){
        TimelineMap<TenetReference,TenetInstance<T>,T> opinions = getOpinions();
        if (opinions.containsKey(tenet)){
            opinions.get(tenet).add(d);
            opinions.setChanged(tenet);
            invalidateCache(tenet);
        }else {
            opinions.put(tenet,new TenetInstance<>(tenet,this.getReference(),d));
        }
    }
    default void setOpinion(TenetReference tenet, double d){
        TimelineMap<TenetReference,TenetInstance<T>,T> opinions = getOpinions();
        if (opinions.containsKey(tenet)){
            opinions.get(tenet).set(d);
            opinions.setChanged(tenet);
            invalidateCache(tenet);
        }else {
            opinions.put(tenet,new TenetInstance<>(tenet,this.getReference(),d));
        }
    }
    default boolean isInfluencer(TOReference<?> ref){
        return getInfluencers().containsKey(ref);
    }
    default void addInfluencer(TOReference<?> influencer, InfluencerRelationship relationship){
        getInfluencers().put(influencer,new InfluencerInstance(relationship));
        invalidateCache();
    }
    default void setInfluence(TOReference<?> influencer, Pair<TenetGroup,Integer>... changes){
        boolean changed = false;
        InfluencerInstance inst = getInfluencers().get(influencer);
        for (Pair<TenetGroup, Integer> change : changes) {
            if (change.getLeft() == null || (change.getRight() == null)){
                continue;
            }
            changed = true;

            inst.set(change.getLeft(),change.getRight());
        }
        if (changed){
            getInfluencers().setChanged(influencer);
            invalidateCache();
        }
    }
    default void modifyInfluence(TOReference<?> influencer, Pair<TenetGroup,Integer>... changes){
        boolean changed = false;
        for (Pair<TenetGroup, Integer> change : changes) {
            if (change.getLeft() == null || (change.getRight() == null || change.getRight() == 0)){
                continue;
            }
            changed = true;
            InfluencerInstance inst = getInfluencers().get(influencer);
            inst.modify(change.getLeft(),change.getRight());
        }
        if (changed){
            getInfluencers().setChanged(influencer);
            invalidateCache();
        }
    }
    default void removeInfluencer(TOReference<?> influencer){
        if (influencer == null){
            return;
        }
        getInfluencers().remove(influencer);
        invalidateCache();
    }





    default double calcResistance(double opinion) {
        double abs = Math.abs(opinion);
        double peak = (1 - z) * oc; // zealotry peak in absolute terms
        if (abs <= peak) {
            double quad = Math.pow(abs / oc, pf);
            double apathy = b * Math.exp(-c * abs * abs);
            return Math.max(k, quad + apathy);
        } else {
            // resistance at the peak point
            double peakVal = Math.pow(peak / oc, pf) + b * Math.exp(-c * peak * peak);
            peakVal = Math.max(k, peakVal);
            // ease from peakVal down to f (drop target) at oc
            double t = (abs - peak) / (oc - peak);
            double eased = Math.pow(t, g); // g is now steepness, not sharpness
            return Math.clamp((peakVal + (f - peakVal) * eased),k,1);
        }
    }

    default List<InfluencerOpinion> getListForTenet(Tenet tenet, boolean includeParentInfluencers, TOReference<?>... bl){
        List<InfluencerOpinion> toReturn = new ArrayList<>();
        List<TOReference<?>> blacklist = new ArrayList<>(Arrays.stream(bl).toList());
        //the blacklist is here to prevent mutually influencing relationships (think US <-> USSR) from infinitely looping.
        // Instead, it just gives back the raw "where the culture wants to land naturally" value, which feels like it works on a pseudo realism level.
        for(Map.Entry<TOReference<?>, InfluencerInstance> entry : getInfluencers().entrySet()){
            TOReference<?> ref = entry.getKey();
            double extra = influencerResistance(ref);
            if (blacklist.contains(ref)){
                toReturn.add(new InfluencerOpinion(tenet,false,entry.getKey(),entry.getValue(),extra));
            } else if (ref.get().isInfluencer(this.getTOReference())){
                blacklist.add(this.getTOReference());
                toReturn.add(new InfluencerOpinion(tenet,includeParentInfluencers,entry.getKey(),entry.getValue(),
                        extra,blacklist.stream().toArray(TOReference[]::new)));
            } else {
                toReturn.add(new InfluencerOpinion(tenet,includeParentInfluencers,entry.getKey(),entry.getValue(),extra));
            }

        }
        return toReturn;
    }
    static <T extends DateMutableEntity<T> & CultureObject<T>>
    double AdjustForInfluence(T tenetOpinionated, Tenet t, boolean includeInfluencer, double acceptance, TOReference<?>... blacklist){
        double resistance = tenetOpinionated.calcResistance(acceptance);
        double toReturn = acceptance * resistance;
        List<Double> d = CalculateInfluencerFactor(tenetOpinionated.getListForTenet(t,includeInfluencer,blacklist), resistance);
        for (Double dd : d) {
            toReturn += dd;
        }
        return toReturn;
    }
    static <T extends DateMutableEntity<T> & CultureObject<T>>
    List<Double> CalculateInfluencerFactor(List<InfluencerOpinion> influencers, double resistance){
        //Reimplement Hegamonic control over opinion.
        int sum = 0;
        for (InfluencerOpinion p : influencers) {
            sum += Math.abs(p.influence());
        }
        final double preSum = sum * resistance;
        Map<Integer,Pair<Double,Double>> normalized = new TreeMap<>();
        int counter = 0;
        for (InfluencerOpinion p : influencers) {
            int base = p.influence();
            double v = Math.pow(Math.abs(base) / preSum,pf);
            if (base < 0){
                v *= -1;
            }
            normalized.put(counter,Pair.of(p.opinion(),v));
            counter++;
        }
        return finalize(normalized);
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
    CultureObjectContainer<T> getContainer();
    default TLSyncedCache<TenetReference,Double> getInfluencedCache(){
        return getContainer().getInfluencedCache();
    };
    default TimelineMap<TenetReference,TenetInstance<T>,T> getOpinions(){
        return getContainer().getOpinions();
    };
    default void internalSetOpinions(TimelineMap<TenetReference,TenetInstance<T>,T> opinions){
        getContainer().setOpinions(opinions);
    };
    default Optional<Pair<TOReference<?>, InfluencerRelationship>> getParentObject(){
        return Optional.ofNullable(getContainer().getParent());
    }; //todo, implement opinion crushing for Hegamon.
    default void setParentObject(TOReference<?> influencer, InfluencerRelationship relationship){

    };
    default void internalParentObject(TOReference<?> influencer, InfluencerRelationship relationship){
        getContainer().setParent(influencer, relationship);
    };
    default TimelineMap<TOReference<?>, InfluencerInstance,T> getInfluencers(){
        return getContainer().getInfluencers();
    };
    default void internalSetInfluencers(TimelineMap<TOReference<?>, InfluencerInstance,T>  influencers){
        getContainer().setInfluencers(influencers);
    };
    default void internalSetCompass(InterpolatedPoliticalCompass<T> compass){
        getContainer().setCompass(compass);
    };
    default InterpolatedPoliticalCompass<T> getCompass(){
        return getContainer().getCompass();
    };
    default void invalidateCache(TenetReference t){
        getInfluencedCache().invalidate(t);
    }
    default void invalidateCache(){
        getInfluencedCache().invalidateAll();
    }
    record InfluencerOpinion(double opinion, int influence){
        public InfluencerOpinion(Tenet t, boolean includeInfluence, TOReference<?> ref, InfluencerInstance inst, double extra, TOReference<?>... blacklist){
            this(ref.get().getAcceptanceValue(t,includeInfluence,blacklist),(int) Math.round(inst.weight().get(t.getGroup()) * Math.clamp(extra,-1,1)));
        }
    }
}
