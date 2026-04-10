package com.objects.culture.object.compass;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.objects.culture.tenet.TenetManager;
import com.utilities.number.BoundedInteger;
import com.utilities.serialization.JsonSerializable;

import java.util.Arrays;

import static com.objects.culture.tenet.Acceptance.MAX_VALUE;

public class PoliticalCompass implements IPoliticalCompass {


    private static final int TOTAL_AXIS = (int) Arrays.stream(Axis.values()).count();
    private static final double PERCENTAGE_TO_BE_EXTREME = 0.666666667; //67 67 funny meme lol. The goal here is to make it so that a high extremism in one axis
    // will get you like ~50-60% of the way to being extreme, which combined with the other three axis, should land you a 100%. But also some government that's
    // extreme on one axis, but extremely mild on another would be only 80% extreme.
    private static final double MAX_EXTREME = TOTAL_AXIS * PERCENTAGE_TO_BE_EXTREME;
    private static final int COMPASS_MAX = 512;
    // Which rights are the natural base for all rights: individual rights or collective rights. Low values are Collectivists: advocating for rights for groups, not individuals. High values are Individualists: advocating for individual rights that should not be trampled by the needs of the collective.
    // Examples of Low values: Communists, Nazis
    // Examples of High values: Anarchists
    private final BoundedInteger individualCollectiveAxis = BoundedInteger.of(-COMPASS_MAX,COMPASS_MAX);
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
    @Override
    public ImmutableMap<Axis, BoundedInteger> getAxisMap() {
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
    public PoliticalCompass() {}
//    public int calculateTotalExtremismPercent(){
//        return (int) (calculateTotalExtremism() * 100);
//    }
//    public double calculateTotalExtremism(){
//        double axisA = calculateExtremism(individualCollectiveAxis.get(),false);
//        double axisB = calculateExtremism(universalParticularAxis.get(),false);
//        double axisC = calculateExtremism(trustInOpacityAxis.get(),false);
//        double axisD = calculateExtremism(egalitarianHierarchyAxis.get(),false);
//        double total = (axisA + axisB + axisC + axisD)/MAX_EXTREME;
//
//        return Math.min(1,total);
//    }
//    public double calculateExtremismAxis(Axis axis, boolean keepNegative){
//        return calculateExtremism(getAxis(axis).get(),keepNegative);
//    }
//    private static double calculateExtremism(double axis, boolean keepNegative){
//        double d = Math.pow(Math.abs(axis) / COMPASS_MAX,2);
//        if (keepNegative && axis < 0){
//            return d * -1;
//        } else {
//            return d;
//        }
//    }
//
//    public void amendCompass(Axis axis, int acceptance){
//        BoundedInteger axisValue = getAxis(axis);
//        axisValue.add(acceptance);
//    }
//    public Ideology getClosestIdeology(PoliticalCompass compass){
//        Ideology closest = null;
//        double closetMatch = Double.MIN_VALUE;
//        for (Ideology ideology : TenetManager.getIdeologies()) {
//            double distance = compass.getCompatibilityValue(ideology.getCompass());
//            if (distance > closetMatch) {
//                closest = ideology;
//                closetMatch = distance;
//            }
//        }
//        return closest;
//    }
//    public double getCompatibilityValue(PoliticalCompass other){
//        double incompatability = 0;
//        for (Axis axis : Axis.values()) {
//            double axisA = calculateExtremismAxis(axis,true);
//            double axisB = other.calculateExtremismAxis(axis,true);
//            double diff = 2 * Math.abs(axisA - axisB);
//            double multiplier = 1;
//            if ((axisA < 0 && axisB < 0) || (axisA > 0 && axisB > 0)){
//                //Because they're on the "same side" their hatred is slightly lesser.
//                multiplier = .9;
//            } else if (diff > 1.5){
//                multiplier = 1.1;
//                //Amp extremism slightly here;
//            }
//            incompatability +=  diff * multiplier;
//        }
//        double gap = 1 - (incompatability / Math.max(6,incompatability));
//        return  (gap * (MAX_VALUE * 2)) - MAX_VALUE;
//    }
//    public BoundedInteger getAxis(Axis axis) {
//        return switch (axis) {
//            case INDIVIDUAL_COLLECTIVE -> individualCollectiveAxis;
//            case UNIVERSAL_PARTICULAR -> universalParticularAxis;
//            case TRUST_IN_OPACITY -> trustInOpacityAxis;
//            case EGALITARIAN_HIERARCHY -> egalitarianHierarchyAxis;
//            default -> throw new IllegalArgumentException("Invalid axis: " + axis);
//        };
//    }
//    @Override
//    public JsonObject toJson() {
//        JsonObject o = new JsonObject();
//        o.addProperty("axisA", individualCollectiveAxis.get());
//        o.addProperty("axisB", universalParticularAxis.get());
//        o.addProperty("axisC", trustInOpacityAxis.get());
//        o.addProperty("axisD",egalitarianHierarchyAxis.get());
//        return o;
//    }
//    public static PoliticalCompass build(JsonObject json){
//        return new PoliticalCompass(json.get("axisA").getAsInt(),json.get("axisB").getAsInt(),json.get("axisC").getAsInt(),json.get("axisD").getAsInt());
//    }
//
//    @Override
//    protected PoliticalCompass clone() {
//        return new PoliticalCompass(individualCollectiveAxis.get(),universalParticularAxis.get(),trustInOpacityAxis.get(),egalitarianHierarchyAxis.get());
//    }
//
//    @Override
//    public void fromJson(JsonObject json) {
//        individualCollectiveAxis.set(json.get("axisA").getAsInt());
//        universalParticularAxis.set(json.get("axisB").getAsInt());
//        trustInOpacityAxis.set(json.get("axisC").getAsInt());
//        egalitarianHierarchyAxis.set(json.get("axisD").getAsInt());
//    }
    public static PoliticalCompass build(JsonObject json){
        return new PoliticalCompass(json.get("axisA").getAsInt(),json.get("axisB").getAsInt(),json.get("axisC").getAsInt(),json.get("axisD").getAsInt());
    }

    @Override
    public BoundedInteger getICAxis() {
        return individualCollectiveAxis;
    }

    @Override
    public BoundedInteger getUPAxis() {
        return universalParticularAxis;
    }

    @Override
    public BoundedInteger getTOSAxis() {
        return trustInOpacityAxis;
    }

    @Override
    public BoundedInteger getEHAxis() {
        return egalitarianHierarchyAxis;
    }

    @Override
    public IPoliticalCompass clone() {
        return new PoliticalCompass(getUPAxis().get(),getICAxis().get(),getTOSAxis().get(),getEHAxis().get());
    }
}
