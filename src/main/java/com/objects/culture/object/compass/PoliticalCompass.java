package com.objects.culture.object.compass;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.objects.culture.object.ideology.Ideology;
import com.objects.culture.tenet.group.CategoryModifier;
import com.objects.culture.tenet.group.TenetGroup;
import com.utilities.number.BoundedInteger;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PoliticalCompass implements IPoliticalCompass {
    private final Value individualCollectiveAxis = new Value(0);
    private final Value universalParticularAxis = new Value(0);
    private final Value trustInOpacityAxis = new Value(0);
    private final Value egalitarianHierarchyAxis = new Value(0);
    private final Value tolerance = new Value(0);
    @Override
    public ImmutableMap<Axis, Value> getAxisMap() {
        return ImmutableMap.of(
                Axis.INDIVIDUAL_COLLECTIVE, individualCollectiveAxis,
                Axis.UNIVERSAL_PARTICULAR, universalParticularAxis,
                Axis.TRUST_IN_OPACITY, trustInOpacityAxis,
                Axis.EGALITARIAN_HIERARCHY, egalitarianHierarchyAxis
        );
    }
    public PoliticalCompass(int individualCollectiveAxis, int universalParticularAxis,int trustInOpacityAxis, int egalitarianHierarchyAxis) {
        this.individualCollectiveAxis.set(individualCollectiveAxis);
        this.universalParticularAxis.set(universalParticularAxis);
        this.trustInOpacityAxis.set(trustInOpacityAxis);
        this.egalitarianHierarchyAxis.set(egalitarianHierarchyAxis);
    }
    public PoliticalCompass(int individualCollectiveAxis, int universalParticularAxis,int trustInOpacityAxis, int egalitarianHierarchyAxis, int tolerance) {
        this.individualCollectiveAxis.set(individualCollectiveAxis);
        this.universalParticularAxis.set(universalParticularAxis);
        this.trustInOpacityAxis.set(trustInOpacityAxis);
        this.egalitarianHierarchyAxis.set(egalitarianHierarchyAxis);
        this.tolerance.set(tolerance);
    }
    public PoliticalCompass() {}
    public static PoliticalCompass build(JsonObject json){
        return new PoliticalCompass(json.get("axisA").getAsInt(),json.get("axisB").getAsInt(),json.get("axisC").getAsInt(),json.get("axisD").getAsInt());
    }

    @Override
    public Value getAxisA() {
        return individualCollectiveAxis;
    }

    @Override
    public Value getAxisB() {
        return universalParticularAxis;
    }

    @Override
    public Value getAxisC() {
        return trustInOpacityAxis;
    }

    @Override
    public Value getAxisD() {
        return egalitarianHierarchyAxis;
    }
    @Override
    public Value getTolerance() {
        return tolerance;
    }

    @Override
    public IPoliticalCompass clone() {
        return new PoliticalCompass(getAxisB().get(), getAxisA().get(), getAxisC().get(), getAxisD().get());
    }
    public static PoliticalCompass of(PoliticalCompass... compasses){
        double axisA = 0;
        double axisB = 0;
        double axisC = 0;
        double axisD = 0;
        double tolerance = 0;
        for (PoliticalCompass compass : compasses) {
            axisA += compass.getAxisA().get();
            axisB += compass.getAxisB().get();
            axisC += compass.getAxisC().get();
            axisD += compass.getAxisD().get();
            tolerance += compass.getTolerance().get();
        }
        return new PoliticalCompass((int) Math.round(axisA/compasses.length), (int) Math.round(axisB/compasses.length), (int) Math.round(axisC/compasses.length), (int) Math.round(axisD/compasses.length), (int) Math.round(tolerance/compasses.length));
    }
    public static PoliticalCompass of(PoliticalCompass base, Map<Ideology,Integer>  opinions){
        return PoliticalCompass.of(base,PoliticalCompass.of(opinions));
    }
    public static PoliticalCompass of(PoliticalCompass base, Set<PoliticalCompass.IdeologyEntry> opinions){
        return PoliticalCompass.of(base,PoliticalCompass.of(opinions));
    }
    public static PoliticalCompass of(Map<Ideology,Integer> ideologies){
        return of(IdeologyEntry.of(ideologies));
    }
    public static PoliticalCompass of(Set<IdeologyEntry> entries){
        double totalWeight = 0;
        Map<Axis,Double> axisMap = new HashMap<>();
        double tolerance = 0;
        Map<Ideology, Double> ideologyOpinionMap = new HashMap<>();
        for (IdeologyEntry entry : entries) {
            int rawOp = entry.opinion();
            ideologyOpinionMap.put(entry.ideology(), (double) rawOp);
            totalWeight += Math.abs(rawOp);
        }
        for (Ideology ideology : ideologyOpinionMap.keySet()) {
            double weight = ideologyOpinionMap.get(ideology) / totalWeight;
            for (Map.Entry<Axis,Value> a : ideology.getCompass().getAxisMap().entrySet()) {
                double value = a.getValue().get() * weight;
                axisMap.compute(a.getKey(), (k,v) -> v == null ? value : v + value);
            }
            tolerance += ideology.getCompass().getTolerance().get() * weight;
        }
        double a = axisMap.get(Axis.INDIVIDUAL_COLLECTIVE);
        double b = axisMap.get(Axis.UNIVERSAL_PARTICULAR);
        double c = axisMap.get(Axis.TRUST_IN_OPACITY);
        double d = axisMap.get(Axis.EGALITARIAN_HIERARCHY);
        return new PoliticalCompass((int) Math.round(a), (int) Math.round(b), (int) Math.round(c), (int) Math.round(d), (int) Math.round(tolerance));
    }
    public record IdeologyEntry(Ideology ideology, int opinion){
        public static IdeologyEntry of(Ideology ideology, int opinion){
            return new IdeologyEntry(ideology, Math.clamp(opinion,-100,100));
        }
        public static IdeologyEntry of(Ideology ideology){
            return new IdeologyEntry(ideology, 50);
        }
        public static Set<IdeologyEntry> of(Map<Ideology,Integer> ideologies){
            return ideologies.entrySet().stream()
                    .map(e -> IdeologyEntry.of(e.getKey(), e.getValue()))
                    .collect(Collectors.toSet());
        }
    }
    @Override
    public void fromJson(JsonElement json) {

    }
}
