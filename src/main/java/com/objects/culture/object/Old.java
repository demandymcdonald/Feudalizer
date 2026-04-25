package com.objects.culture.object;

import com.base.DateMutableEntity;
import com.base.timeline.change.multi.type.ChangeType;
import com.base.timeline.change.multi.wrapper.TLMap;
import com.objects.culture.Influencers.InfluencerInstance;
import com.objects.culture.Influencers.InfluencerRelationship;
import com.objects.culture.object.reference.COReference;
import com.objects.culture.tenet.Tenet;
import com.objects.culture.tenet.TenetReference;
import com.objects.culture.tenet.instance.TenetInstance;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.BiConsumer;

public interface Old {
    public final double getAcceptanceValue(Tenet tenet, boolean includeInfluencers, COReference<?>... bls){
        TenetReference ref = TenetReference.of(tenet);
        T self = reference.get();
        TLMap<TenetReference, TenetInstance<T>> opinions = getOpinions();
        double acceptance = 0;
        if (opinions.containsKey(ref)){
            acceptance = opinions.get(ref).get();
        } else {
            acceptance =  this.getCompass().getCompatibilityValue(tenet.getCompass(),false);
        }
        List<COReference<?>> blacklist = new ArrayList<>(List.of(bls));
        blacklist.add(self.getTOReference()); //prevents recursion might rewrite the influencer code to settle instead of looping like this, but idk.
        if (self.getParentObject().isPresent()){
            Pair<COReference<?>, InfluencerRelationship> parentPair = self.getParentObject().get();
            COReference<?> parent = parentPair.getLeft();
            InfluencerRelationship parentRel = parentPair.getRight();
            if (parentRel.hasWeightFor(tenet.getGroup()) && parentRel.getWeight(tenet.getGroup()) == 1){ //Simulated hegamonic control, especially used on the systemic side of
                return parent.get().getAcceptanceValue(tenet,includeInfluencers,blacklist.toArray(COReference[]::new));
            }
        }
        if (includeInfluencers){
            Double val = getInfluencedCache().get(ref);
            if (val != null){
                return val;
            }
            val = AdjustForInfluence(self,tenet,true,acceptance,blacklist.toArray(COReference[]::new));
            getInfluencedCache().put(ref,val);
            return val;
        }
        return acceptance;
    }
    private static <T extends DateMutableEntity<T> & CultureObject<T>>
    double AdjustForInfluence(T tenetOpinionated, Tenet t, boolean includeInfluencer, double acceptance, COReference<?>... blacklist){
        double resistance = tenetOpinionated.calcResistance(acceptance);
        double toReturn = acceptance * resistance;
        List<Double> d = CalculateInfluencerFactor(tenetOpinionated.getListForTenet(t,includeInfluencer,blacklist), resistance);
        for (Double dd : d) {
            toReturn += dd;
        }
        return toReturn;
    }
    private static <T extends DateMutableEntity<T> & CultureObject<T>>
    List<Double> CalculateInfluencerFactor(List<CultureObjectContainer.InfluencerOpinion> influencers, double resistance){
        //Reimplement Hegamonic control over opinion.
        int sum = 0;
        for (CultureObjectContainer.InfluencerOpinion p : influencers) {
            sum += Math.abs(p.influence());
        }
        final double preSum = sum * resistance;
        Map<Integer,Pair<Double,Double>> normalized = new TreeMap<>();
        int counter = 0;
        for (CultureObjectContainer.InfluencerOpinion p : influencers) {
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

    private List<CultureObjectContainer.InfluencerOpinion> getListForTenet(Tenet tenet, boolean includeParentInfluencers, COReference<?>... bl){
        List<CultureObjectContainer.InfluencerOpinion> toReturn = new ArrayList<>();
        List<COReference<?>> blacklist = new ArrayList<>(Arrays.stream(bl).toList());
        T self = reference.get();
        //the blacklist is here to prevent mutually influencing relationships (think US <-> USSR) from infinitely looping.
        // Instead, it just gives back the raw "where the culture wants to land naturally" value, which feels like it works on a pseudo realism level.
        for(Map.Entry<COReference<?>, InfluencerInstance> entry : getInfluencers().entrySet()){
            COReference<?> ref = entry.getKey();
            double extra = influencerResistance(ref);
            if (blacklist.contains(ref)){
                toReturn.add(new CultureObjectContainer.InfluencerOpinion(tenet,false,entry.getKey(),entry.getValue(),extra));
            } else if (ref.get().isInfluencer(self.getTOReference())){
                blacklist.add(self.getTOReference());
                toReturn.add(new CultureObjectContainer.InfluencerOpinion(tenet,includeParentInfluencers,entry.getKey(),entry.getValue(),
                        extra,blacklist.stream().toArray(COReference[]::new)));
            } else {
                toReturn.add(new CultureObjectContainer.InfluencerOpinion(tenet,includeParentInfluencers,entry.getKey(),entry.getValue(),extra));
            }

        }
        return toReturn;
    }
    public void setOpinion(TenetReference tenet,boolean wipe, double d){
        TLMap<TenetReference,TenetInstance<T>> opinions = getOpinions();
        if (opinions.containsKey(tenet)){
            BiConsumer<TenetReference,TenetInstance<T>> consumer = (t, i) -> i.set(d);
            opinions.setChanged(wipe, ChangeType.VALUE,Map.of(tenet,consumer));
            invalidateCache(tenet);
        }else {
            opinions.put(tenet,new TenetInstance<>(tenet,this.getReference(),d));
        }
    }
}
