package com.objects.culture.tenet;

import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeID;
import com.base.timeline.variable.TimelineEasingVariable;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.objects.culture.Culture;
import com.objects.culture.change.CultureMapChanges;
import com.utilities.number.BoundedDouble;
import com.utilities.number.BoundedInteger;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TenetInstance extends TimelineEasingVariable<TenetInstance, CultureMapChanges.TenetMapChange, Culture> {
    private final BoundedDouble opinion = new BoundedDouble(-384, 384); //256 is the last state, but I want to give the extreme
    // Acceptance levels a bit of buffer to prevent instant replacement in cases where there can only be one CORE or PERSECUTED
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
            double toReturn = opinion.get();
            List<Pair<TenetInstance, BoundedInteger>> influencers = new ArrayList<>(influencerMap.values());
            influencers.sort((o1, o2) -> o2.getRight().get() - o1.getRight().get());
            for (Pair<TenetInstance, BoundedInteger> p : influencerMap.values()) {
                if (p.getLeft() == null) {
                    continue;
                }
                int influencer = p.getRight().get();
                double influencerFactor = ((double) influencer/ 100);
                double childImpact = 1 - influencerFactor;
                toReturn = (toReturn * childImpact) + (p.getLeft().getAcceptanceValue() * influencerFactor);
                if (influencer == 100){
                    break;
                }
            }
            return toReturn;
        }
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


    public DMEReference<Tenet<?>> getTenet() {
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
