package com.objects.culture.object.compass;

import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.variable.EasingVariable;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.objects.culture.object.CultureObject;
import com.utilities.id.Identifiable;
import com.utilities.id.SimpleID;
import com.utilities.id.StringIdentifiable;

import java.util.Map;
import java.util.function.Predicate;

public class InterpolatedPoliticalCompass<T extends DateMutableEntity<T> & CultureObject<T>> implements IPoliticalCompass, EasingVariable<InterpolatedPoliticalCompass<T>,CompassChange<T>,T> {
    private static final StringIdentifiable tolerance = new StringIdentifiable() {
        @Override
        public String getID() {
            return "tolerance";
        }
    };

    private final Value axisAMain = new Value(0);
    private final Value axisAInt = new Value(0);
    private static final SimpleID axisAID = new SimpleID("ica");
    // Who matters morally and politically. Low values are Universalist: Advocating for everyone to join the ideology. High values are Particularists: Advocating for their group alone.
    // Examples of Low values: Communists, NeoLiberals,
    // Examples of High values: Nazis, Fascists
    private final Value axisBMain = new Value(0);
    private final Value axisBInt = new Value(0);
    private static final SimpleID axisBID = new SimpleID("upa");
    // How trusting a person or ideology is in large entities that are opaque in concept and operation. Low values are Low Trust, meaning they do not trust entities they do not control. High values are High Trust, meaning they implicitly trust entities they do not understand
    // Examples of Low values: Populists
    // Examples of High values: Technocrats, Traditional Authoritarians
    private final Value axisCMain = new Value(0);
    private final Value axisCInt = new Value(0);
    private static final SimpleID axisCID = new SimpleID("top");
    //How much a person or ideology values vertical power structures. High values value strict hierarchy, while low values are more egalitarian.
    //Examples of low values: Libertarian Socialists, Anarchists
    //Examples of high values: Stalinists,Randian Capitalists
    private final Value axisDMain = new Value(0);
    private final Value axisDInt = new Value(0);

    private final Value toleranceMain = new Value(0);
    private final Value toleranceInt = new Value(0);
    private static final SimpleID axisDID = new SimpleID("ehi");
    private DMEReference<T> owner;
    public InterpolatedPoliticalCompass(DMEReference<T> owner, int universalParticularAxis, int individualCollectiveAxis, int trustInOpacityAxis, int egalitarianHierarchyAxis) {
        this.owner = owner;
        this.axisAMain.set(individualCollectiveAxis);
        this.axisBMain.set(universalParticularAxis);
        this.axisCMain.set(trustInOpacityAxis);
        this.axisDMain.set(egalitarianHierarchyAxis);
    }
    public InterpolatedPoliticalCompass() {}

    @Override
    public Value getAxisA() {
        return axisAInt;
    }

    @Override
    public Value getAxisB() {
        return axisBInt;
    }

    @Override
    public Value getAxisC() {
        return axisCInt;
    }

    @Override
    public Value getAxisD() {
        return axisDInt;
    }

    @Override
    public Value getTolerance() {
        return toleranceInt;
    }

    @Override
    public ImmutableMap<Axis, Value> getAxisMap() {
        return ImmutableMap.of(
            Axis.INDIVIDUAL_COLLECTIVE, axisAInt,
            Axis.UNIVERSAL_PARTICULAR, axisBInt,
            Axis.TRUST_IN_OPACITY, axisCInt,
            Axis.EGALITARIAN_HIERARCHY, axisDInt
        );
    }


    @Override
    public JsonObject toJson() {
        return serialize();
    }

    @Override
    public void fromJson(JsonElement json) {

    }

    @Override
    public void fromJson(JsonObject json) {
        deserialize(json);
    }
    @Override
    public InterpolatedPoliticalCompass<?> clone() {
        return new InterpolatedPoliticalCompass<>(owner, axisBMain.get(), axisAMain.get(), axisCMain.get(), axisDMain.get());
    }

    @Override
    public String getChangeClassName() {
        return CompassChange.class.getName();
    }

    @Override
    public DMEReference<? extends T> getOwner() {
        return owner;
    }

    @Override
    public Map<Identifiable<?>, VariableContainer> getEasingFunctions() {
        return Map.of(
            axisAID,new VariableContainer(EasingType.QUAD,() -> {
                    return Double.valueOf(axisAMain.get());},(d) -> axisAInt.set((int) Math.round(d))),
            axisBID,new VariableContainer(EasingType.QUAD,() -> {
                    return Double.valueOf(axisBMain.get());},(d) -> axisBInt.set((int) Math.round(d))),
            axisCID,new VariableContainer(EasingType.QUAD,()->{
                    return Double.valueOf(axisCMain.get());},(d) -> axisCInt.set((int) Math.round(d))),
            axisDID,new VariableContainer(EasingType.QUAD,()->{
                    return Double.valueOf(axisDMain.get());},(d) -> axisDInt.set((int) Math.round(d))),
            tolerance,new VariableContainer(EasingType.QUAD,()->{
                    return Double.valueOf(toleranceMain.get());},(d) -> toleranceInt.set((int) Math.round(d)))
        );
    }

    @Override
    public Predicate<InterpolatedPoliticalCompass<T>> getMatching() {
        return null;
    }

    @Override
    public void mainSave(JsonObject object) {
        JsonObject jo =  IPoliticalCompass.super.toJson();
        jo.add("owner",owner.serialize());
        object.add("compass",jo);
    }

    @Override
    public void mainLoad(JsonObject json) {
        JsonObject jo = json.getAsJsonObject("compass");
        axisAMain.set(jo.get("axisA").getAsInt());
        axisBMain.set(jo.get("axisB").getAsInt());
        axisCMain.set(jo.get("axisC").getAsInt());
        axisDMain.set(jo.get("axisD").getAsInt());
        this.owner = DMEReference.deserialize(json.get("owner").getAsJsonObject());
    }

    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }
}
