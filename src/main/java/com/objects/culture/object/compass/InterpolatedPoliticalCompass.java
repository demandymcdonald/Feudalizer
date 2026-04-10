package com.objects.culture.object.compass;

import com.Global;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.utilities.TLSyncedSupplier;
import com.base.utilities.TimelineEasingVariable;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.utilities.number.BoundedInteger;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.Objects;

public class InterpolatedPoliticalCompass<T extends DateMutableEntity<?>> implements IPoliticalCompass {
    private final BoundedInteger individualCollectiveAxis = BoundedInteger.of(-COMPASS_MAX,COMPASS_MAX);
    private final TLSyncedSupplier<PoliticalCompass> InterpolatedCompass = new TLSyncedSupplier<>(() -> easeCompass(Pair.of(this,Global.getDate()),getNext()));
    // Who matters morally and politically. Low values are Universalist: Advocating for everyone to join the ideology. High values are Particularists: Advocating for their group alone.
    // Examples of Low values: Communists, NeoLiberals,
    // Examples of High values: Nazis, Fascists
    private final BoundedInteger universalParticularAxis = BoundedInteger.of(-COMPASS_MAX,COMPASS_MAX);
    // How trusting a person or ideology is in large entities that are opaque in concept and operation. Low values are Low Trust, meaning they do not trust entities they do not control. High values are High Trust, meaning they implicitly trust entities they do not understand
    // Examples of Low values: Populists
    // Examples of High values: Technocrats, Traditional Authoritarians
    private final BoundedInteger trustInOpacityAxis = BoundedInteger.of(-COMPASS_MAX,COMPASS_MAX);
    //How much a person or ideology values vertical power structures. High values value strict hierarchy, while low values are more egalitarian.
    //Examples of low values: Libertarian Socialists, Anarchists
    //Examples of high values: Stalinists,Randian Capitalists
    private final BoundedInteger egalitarianHierarchyAxis = BoundedInteger.of(-COMPASS_MAX,COMPASS_MAX);
    private final DMEReference<T> owner;
    public InterpolatedPoliticalCompass(DMEReference<T> owner, int universalParticularAxis, int individualCollectiveAxis, int trustInOpacityAxis, int egalitarianHierarchyAxis) {
        this.owner = owner;
        this.individualCollectiveAxis.set(individualCollectiveAxis);
        this.universalParticularAxis.set(universalParticularAxis);
        this.trustInOpacityAxis.set(trustInOpacityAxis);
        this.egalitarianHierarchyAxis.set(egalitarianHierarchyAxis);
    }

    private Pair<InterpolatedPoliticalCompass<T>, LocalDate> getNext(){
        CompassChange<T> current = (CompassChange<T>) owner.get().getTimeline().findChangeByClassID(Global.getDate(), Global.TimeDirection.FORWARD,CompassChange.class.getName(),false).getFirst().getNextMatching();
        return Pair.of(current.getCompass(),current.getStart());
    }

    @Override
    public BoundedInteger getICAxis() {
        return InterpolatedCompass.get().getICAxis();
    }

    @Override
    public BoundedInteger getUPAxis() {
        return InterpolatedCompass.get().getUPAxis();
    }

    @Override
    public BoundedInteger getTOSAxis() {
        return InterpolatedCompass.get().getTOSAxis();
    }

    @Override
    public BoundedInteger getEHAxis() {
        return InterpolatedCompass.get().getEHAxis();
    }

    @Override
    public ImmutableMap<Axis, BoundedInteger> getAxisMap() {
        return ImmutableMap.of(
                Axis.INDIVIDUAL_COLLECTIVE, individualCollectiveAxis,
                Axis.UNIVERSAL_PARTICULAR, universalParticularAxis,
                Axis.TRUST_IN_OPACITY, trustInOpacityAxis,
                Axis.EGALITARIAN_HIERARCHY, egalitarianHierarchyAxis
        );
    }
    private static <T extends DateMutableEntity<?>> PoliticalCompass easeCompass(Pair<InterpolatedPoliticalCompass<T>,LocalDate> first, Pair<InterpolatedPoliticalCompass<T>,LocalDate> second){
        final InterpolatedPoliticalCompass<?> C1 = first.getLeft();
        final InterpolatedPoliticalCompass<?> C2 = second.getLeft();
        final PoliticalCompass compass = new PoliticalCompass();
        final LocalDate D1 = first.getRight();
        final LocalDate D2 = second.getRight();

        for (Axis axis : first.getLeft().getAxisMap().keySet()) {
            double easedValue = TimelineEasingVariable.EasingType.QUAD.get().calculate(Objects.requireNonNull(C1.getAxisMap().get(axis)).get(), Objects.requireNonNull(C2.getAxisMap().get(axis)).get(),D1,D2);
            compass.amendCompass(axis,(int) Math.round(easedValue));
        }
        return compass;
    }

    @Override
    public JsonObject toJson() {
        JsonObject jo =  IPoliticalCompass.super.toJson();
        jo.add("owner",owner.serialize());
        return jo;
    }
    @Override
    public void fromJson(JsonObject json) {
        universalParticularAxis.set(json.get("axisA").getAsInt());
        individualCollectiveAxis.set(json.get("axisB").getAsInt());
        trustInOpacityAxis.set(json.get("axisC").getAsInt());
        egalitarianHierarchyAxis.set(json.get("axisD").getAsInt());
    }
    public static <T extends DateMutableEntity<T>> InterpolatedPoliticalCompass<T> build(JsonObject json) {
        DMEReference<T> owner = DMEReference.deserialize(json.getAsJsonObject("owner"));
        return new InterpolatedPoliticalCompass<>(owner,json.get("axisA").getAsInt(),json.get("axisB").getAsInt(),json.get("axisC").getAsInt(),json.get("axisD").getAsInt());
    }
    @Override
    public IPoliticalCompass clone() {
        return new InterpolatedPoliticalCompass<>(owner,universalParticularAxis.get(),individualCollectiveAxis.get(),trustInOpacityAxis.get(),egalitarianHierarchyAxis.get());
    }
}
