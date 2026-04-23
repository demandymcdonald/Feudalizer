package com.objects.culture.object.compass;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.utilities.number.BoundedInteger;

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
    public PoliticalCompass(int universalParticularAxis,int individualCollectiveAxis, int trustInOpacityAxis, int egalitarianHierarchyAxis) {
        this.individualCollectiveAxis.set(individualCollectiveAxis);
        this.universalParticularAxis.set(universalParticularAxis);
        this.trustInOpacityAxis.set(trustInOpacityAxis);
        this.egalitarianHierarchyAxis.set(egalitarianHierarchyAxis);
    }
    public PoliticalCompass(int universalParticularAxis,int individualCollectiveAxis, int trustInOpacityAxis, int egalitarianHierarchyAxis, int tolerance) {
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
}
