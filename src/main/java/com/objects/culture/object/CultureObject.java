package com.objects.culture.object;

import com.Global;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.map.TimelineMapChange;
import com.base.timeline.state.TimelineState;
import com.objects.culture.Influencers.InfluencerInstance;
import com.objects.culture.Influencers.InfluencerRelationship;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.instance.TOReference;
import com.objects.culture.instance.TenetInstance;
import com.objects.culture.object.compass.CompassChange;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.types.Tenet;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

import static com.objects.culture.tenet.Acceptance.MAX_VALUE;

public interface CultureObject<
        I extends TenetInstance<I, C1,T>,
        C1 extends TimelineMapChange<?, Tenet, I,T>,
        T extends DateMutableEntity<T>
        >{
    static final double b = .23; //apathy peak as percent from start
    static final double c = 0.00022; //apathy decay
    static final double z = .125; // zealotry peak as percent from end. Should hit right as the they pass the Fanatic mark
    static final double f = .69; // zealotry drop-off  target
    static final double g = 2.2; //zealotry drop-off steepness
    static final double k = .02; //kernal floor
    static final int pf = 2; //crushing power for normalization
    static final int oc = MAX_VALUE; // upper and lower bound for opinion values



    Map<Tenet,I> getOpinions();
    Map<TOReference<?>, InfluencerInstance> getInfluencers();
    Map<TOReference<?>, InfluencerRelationship> getStandardInfluencers();
    double influencerResistance(TOReference<?> influencer); // clamped between -1 and 1. Used to model things like personal opinion of an influencer for people -> people influencer,
    // or situations where a person might have less influence than the relationship suggests (power imbalance, for example).
    DMEReference<T> getOwner();
    default TOReference<?> getTOReference(){
        //Weird, I know. All TenetOpinionated are DMEs, so if this class is running, it's on a DME, and the reference
        // will work becuase TOReferences are just janky wrappers for DMEReferences.
        return new TOReference<>(getOwner());
    }
    default Acceptance getAcceptance(Tenet tenet, boolean includeInfluencers){
        return Acceptance.get((int) Math.round(getAcceptanceValue(tenet, includeInfluencers)));
    }
    default double getAcceptanceValue(Tenet tenet, boolean includeInfluencers, TOReference<?>... blacklist){

        Map<Tenet,I> opinions = getOpinions();
        double acceptance = 0;
        if (opinions.containsKey(tenet)){
            acceptance = opinions.get(tenet).getCurrent();
        } else {
            acceptance =  tenet.getCompassEntry().getCompatibilityValue(getCompass());
        }
        if (includeInfluencers){
            return AdjustForInfluence(this,tenet,true,acceptance,blacklist);
        }
        return acceptance;
    }
    void internalSetCompass(PoliticalCompass compass);
    PoliticalCompass getCompass();
    default void amendCompass(Pair<PoliticalCompass.Axis,Integer>... values){
        PoliticalCompass compass = getCompass();
        boolean changed = false;
        for (Pair<PoliticalCompass.Axis,Integer> pair : values) {
            if (pair.getLeft() == null || (pair.getRight() == null || pair.getRight() == 0)){
                continue;
            }
            changed = true;
            compass.amendCompass(pair.getLeft(),pair.getRight());
        }
        if (changed){
            DMEReference<?> owner = getOwner();
            owner.get().getTimeline().addChange(new CompassChange<>(owner, Global.getDate(),compass));
        }
    }
    default boolean isInfluencer(TOReference<?> ref){
        return getInfluencers().containsKey(ref);
    }
    default void addInfluencer(TOReference<?> influencer, InfluencerRelationship relationship){
        Pair<TOReference<?>,InfluencerInstance> pair = Pair.of(influencer,new InfluencerInstance(relationship));
        setChanged(pair);
    }
    default void setInfluence(TOReference<?> influencer, Pair<TenetGroup,Integer>... changes){
        boolean changed = false;
        for (Pair<TenetGroup, Integer> change : changes) {
            if (change.getLeft() == null || (change.getRight() == null)){
                continue;
            }
            changed = true;
            InfluencerInstance inst = getInfluencerChange().get(influencer);
            inst.set(change.getLeft(),change.getRight());
        }
        if (changed){
            setChanged(Pair.of(influencer,getInfluencers().get(influencer)));
        }
    }
    default void modifyInfluence(TOReference<?> influencer, Pair<TenetGroup,Integer>... changes){
        boolean changed = false;
        for (Pair<TenetGroup, Integer> change : changes) {
            if (change.getLeft() == null || (change.getRight() == null || change.getRight() == 0)){
                continue;
            }
            changed = true;
            InfluencerInstance inst = getInfluencerChange().get(influencer);
            inst.modify(change.getLeft(),change.getRight());
        }
        if (changed){
            setChanged(Pair.of(influencer,getInfluencers().get(influencer)));
        }
    }
    default void removeInfluencer(TOReference<?> influencer){
        if (influencer == null){
            return;
        }
        getInfluencerChange().remove(influencer);
    }

    default void setChanged(Pair<TOReference<?>,InfluencerInstance>... insts){
        InfluencerMapChange<T> ch = getInfluencerChange();
        for (Pair<TOReference<?>,InfluencerInstance> inst : insts){
            ch.addChange(inst);
        }
    }
    default InfluencerMapChange<T> getInfluencerChange(){
        DMEReference<T> owner = getOwner();
        TimelineState<T> s =owner.get().getTimeline().getOrMakeState(Global.getDate());
        InfluencerMapChange<T> change = s.getChange(InfluencerMapChange.class);
        if (change == null){
            owner.get().getTimeline().addChange(new InfluencerMapChange<>(owner,Global.getDate()));
            change = s.getChange(InfluencerMapChange.class);
        }
        return change;
    }











    private double calcResistance(double opinion) {
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
    static <I extends TenetInstance<I,C,T>,C extends TimelineMapChange<?, Tenet, I,T>,T extends DateMutableEntity<T>>
    double AdjustForInfluence(CultureObject<?,?,?> tenetOpinionated, Tenet t, boolean includeInfluencer, double acceptance, TOReference<?>... blacklist){
        double resistance = tenetOpinionated.calcResistance(acceptance);
        double toReturn = acceptance * resistance;
        List<Double> d = CalculateInfluencerFactor(tenetOpinionated.getListForTenet(t,includeInfluencer,blacklist), resistance);
        for (Double dd : d) {
            toReturn += dd;
        }
        return toReturn;
    }
    static <I extends TenetInstance<I,C,T>,C extends TimelineMapChange<?, Tenet, I,T>,T extends DateMutableEntity<T>>
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
    record InfluencerOpinion(double opinion, int influence){
        public InfluencerOpinion(Tenet t, boolean includeInfluence, TOReference<?> ref, InfluencerInstance inst, double extra, TOReference<?>... blacklist){
            this(ref.get().getAcceptanceValue(t,includeInfluence,blacklist),(int) Math.round(inst.weight().get(t.getGroup()) * Math.clamp(extra,-1,1)));
        }



    }
}
