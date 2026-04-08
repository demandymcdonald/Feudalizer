package com.objects.culture.tenet;

import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeID;
import com.base.timeline.variable.TimelineEasingVariable;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.objects.culture.Culture;
import com.objects.culture.change.CultureMapChanges;
import com.objects.culture.tenet.types.Tenet;
import com.utilities.number.BoundedDouble;
import com.utilities.number.BoundedInteger;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TenetInstance extends TimelineEasingVariable<TenetInstance, CultureMapChanges.TenetMapChange, Culture> {
    private static final double b = .23; //apathy peak as percent from start
    private static final double c = 0.00022; //apathy decay
    private static final double z = .125; // zealotry peak as percent from end. Should hit right as the they pass the Fanatic mark
    private static final double f = .69; // zealotry drop-off  target
    private static final double g = 2.2; //zealotry drop-off steepness
    private static final double k = .05; //kernal floor
    private static final int pf = 2; //crushing power for normalization
    private static final int oc = 512; // upper and lower bound for opinion values

    private final BoundedDouble opinion = new BoundedDouble(-oc, oc);
    private final Map<DMEReference<Culture>, Pair<TenetInstance, BoundedInteger>> influencerMap = new HashMap<>();

    public TenetInstance() {
    }

    public TenetInstance(DMEReference<Culture> owner, EasingType easeType, ChangeID thisChange, double currentValue) {
        super(owner, thisChange, easeType);
        this.opinion.set(currentValue);
    }

    @Override
    protected double getCurrent() {
        if (influencerMap.isEmpty()) {
            return opinion.get();
        } else {
            List<Pair<TenetInstance, BoundedInteger>> rawInfluencers = new ArrayList<>(influencerMap.values());
            rawInfluencers.sort((o1, o2) -> o2.getRight().get() - o1.getRight().get());
            if (rawInfluencers.getFirst().getRight().get() == 100 && (rawInfluencers.size() == 1 || rawInfluencers.get(1).getRight().get() != 100)) {
                return rawInfluencers.getFirst().getLeft().getAcceptanceValue();
            }
            double base = opinion.get();
            final double resistance = calcResistance(base);
            double toReturn = base * resistance;
            List<Double> influencers = handleInfluencers(rawInfluencers,1 - resistance);
            //In theory, this could all be done in buildLists, but I suspect future edge cases so I'll
            //leave it suboptimal for now... and probably ever.
            for (Double p : influencers) {
                toReturn += p;
            }
            return toReturn;
        }
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
            return Math.max(k, peakVal + (f - peakVal) * eased);
        }
    }
    private static List<Double> handleInfluencers(List<Pair<TenetInstance,BoundedInteger>> influencers, double resistance) {
        //What's next? are the influencers gonna want tickets to Cochella? \s
        int sum = 0;
        for (Pair<TenetInstance, BoundedInteger> p : influencers) {
            if (p.getLeft() == null) {
                continue;
            }
            sum += Math.abs(p.getRight().get());
        }
        final double preSum = sum * resistance;
        Map<TenetInstance,Double> normalized = new HashMap<>();
        for (Pair<TenetInstance, BoundedInteger> p : influencers) {
            if (p.getLeft() == null) {
                continue;
            }
            int base = p.getRight().get();
            double v = Math.pow(Math.abs(base) / preSum,pf);
            if (base < 0){
                v *= -1;
            }
            normalized.put(p.getLeft(),v);
        }
        return finalize(normalized);
    };
    private static List<Double> finalize(Map<TenetInstance,Double> entries){
        double sum = 0;
        for (Double d : entries.values()) {
            sum += Math.abs(d);
        }
        List<Double> normalized = new ArrayList<>();
        for (Map.Entry<TenetInstance,Double> d : entries.entrySet()) {
            normalized.add(d.getKey().getAcceptanceValue() * (d.getValue() / sum));
        }
        return normalized;
    }
    public double getAcceptanceValue() {
        return getFinal();
    }
    public Acceptance getAcceptance() {
        return Acceptance.get((int) getAcceptanceValue());
    }
    public void setValue(double value) {
        this.opinion.set(value);
        onChange();
    }
    public void changeInfluencerInfluence(DMEReference<Culture> parent, int newInfluence){
        if(influencerMap.containsKey(parent)){
            BoundedInteger bounded = influencerMap.get(parent).getRight();
            bounded.set(newInfluence);
            this.onChange();
        }
    }
    public int getInfluencerInfluence(DMEReference<Culture> parent){
        if(influencerMap.containsKey(parent)){
            return influencerMap.get(parent).getRight().get();
        }
        return 0;
    }
    public void linkInfluencer(DMEReference<Culture> parent, TenetInstance parentInstance) {
        if (influencerMap.containsKey(parent)) {
            BoundedInteger bounded = influencerMap.get(parent).getRight();
            influencerMap.put(parent, Pair.of(parentInstance, bounded));
        } else {
            BoundedInteger bound = new BoundedInteger(-100, 100);
            bound.set(100);
            influencerMap.put(parent, Pair.of(parentInstance, bound));
            onChange();
        }
    }


    public Tenet<?,?> getTenet() {
        return getOwner().get().getTenets().inverse().get(this);
    }

    private void onChange() {
        DMEReference<Culture> owner = getOwner();
        CultureMapChanges.TenetMapChange c = getC(owner, getThisChange());
        c.addChange(Pair.of(getTenet(), this));
    }

    @Override
    protected TenetInstance findE(DMEReference<Culture> ref, ChangeID current) {
        CultureMapChanges.TenetMapChange c = getC(ref, getThisChange());
        return c.getFromFuture(current, this);
    }

    @Override
    public void additionalSave(JsonObject data) {
        data.addProperty("opinion", opinion.get());
        data.add("influencerMap", serializeMap(influencerMap));
    }

    private JsonArray serializeMap(Map<DMEReference<Culture>, Pair<TenetInstance, BoundedInteger>> map) {
        JsonArray array = new JsonArray();
        for (Map.Entry<DMEReference<Culture>, Pair<TenetInstance, BoundedInteger>> entry : map.entrySet()) {
            JsonObject obj = new JsonObject();
            obj.add("influencer", entry.getKey().serialize());
            obj.addProperty("weight", entry.getValue().getRight().get());
            array.add(obj);
        }
        return array;
    }

    @Override
    public void additionalLoad(JsonObject data) {
        opinion.set(data.get("opinion").getAsDouble());
        influencerMap.putAll(deserializeMap(data.get("influencerMap").getAsJsonArray()));
    }

    private Map<DMEReference<Culture>, Pair<TenetInstance, BoundedInteger>> deserializeMap(JsonArray array) {
        Map<DMEReference<Culture>, Pair<TenetInstance, BoundedInteger>> map = new HashMap<>();
        for (int i = 0; i < array.size(); i++) {
            JsonObject obj = array.get(i).getAsJsonObject();
            map.put(DMEReference.deserialize(obj.get("influencer").getAsJsonObject()), Pair.of(null, new BoundedInteger(obj.get("weight").getAsInt(), 100)));
        }
        return map;
    }


}
