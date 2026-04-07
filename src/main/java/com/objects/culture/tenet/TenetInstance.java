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

import java.util.HashMap;
import java.util.Map;

public class TenetInstance extends TimelineEasingVariable<TenetInstance, CultureMapChanges.TenetMapChange, Culture> {
    private final BoundedDouble opinion = new BoundedDouble(-256, 256);
    private final Map<DMEReference<Culture>, Pair<TenetInstance, BoundedInteger>> parentMap = new HashMap<>();

    public TenetInstance() {
    }

    public TenetInstance(DMEReference<Culture> owner, EasingType easeType, ChangeID thisChange, double currentValue) {
        super(owner, thisChange, easeType);
        this.opinion.set(currentValue);
    }

    @Override
    protected double getCurrent() {
        if (parentMap.isEmpty()) {
            return opinion.get();
        } else {
            double toReturn = opinion.get();
            for (Pair<TenetInstance, BoundedInteger> p : parentMap.values()) {
                if (p.getLeft() == null) {
                    continue;
                }
                toReturn += (p.getLeft().getOpinion() * ((double) p.getRight().get()/ 100)) / (parentMap.size());
            }
            return toReturn;
        }
    }
    public double getOpinion() {
        return getFinal();
    }
    public Tenet.Acceptance getAcceptance() {
        return Tenet.Acceptance.get((int) getOpinion());
    }
    public void setValue(double value) {
        this.opinion.set(value);
        onChange();
    }

    public void linkParent(DMEReference<Culture> parent, TenetInstance parentInstance) {
        if (parentMap.containsKey(parent)) {
            BoundedInteger bounded = parentMap.get(parent).getRight();
            parentMap.put(parent, Pair.of(parentInstance, bounded));
        } else {
            parentMap.put(parent, Pair.of(parentInstance, new BoundedInteger(-100, 100)));
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
        data.add("parentMap", serializeMap(parentMap));
    }

    private JsonArray serializeMap(Map<DMEReference<Culture>, Pair<TenetInstance, BoundedInteger>> map) {
        JsonArray array = new JsonArray();
        for (Map.Entry<DMEReference<Culture>, Pair<TenetInstance, BoundedInteger>> entry : map.entrySet()) {
            JsonObject obj = new JsonObject();
            obj.add("parent", entry.getKey().serialize());
            obj.addProperty("weight", entry.getValue().getRight().get());
            array.add(obj);
        }
        return array;
    }

    @Override
    public void additionalLoad(JsonObject data) {
        opinion.set(data.get("opinion").getAsDouble());
        parentMap.putAll(deserializeMap(data.get("parentMap").getAsJsonArray()));
    }

    private Map<DMEReference<Culture>, Pair<TenetInstance, BoundedInteger>> deserializeMap(JsonArray array) {
        Map<DMEReference<Culture>, Pair<TenetInstance, BoundedInteger>> map = new HashMap<>();
        for (int i = 0; i < array.size(); i++) {
            JsonObject obj = array.get(i).getAsJsonObject();
            map.put(DMEReference.deserialize(obj.get("parent").getAsJsonObject()), Pair.of(null, new BoundedInteger(obj.get("weight").getAsInt(), 100)));
        }
        return map;
    }


}
