package com.objects.culture.object.compass;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.objects.culture.tenet.TenetManager;
import com.utilities.number.BoundedInteger;
import com.utilities.serialization.JsonSerializable;

import java.util.Arrays;
import java.util.function.Function;

import static com.objects.culture.tenet.Acceptance.MAX_VALUE;

public interface IPoliticalCompass extends JsonSerializable {
    static final int TOTAL_AXIS = (int) Arrays.stream(IPoliticalCompass.Axis.values()).count();
    static final double PERCENTAGE_TO_BE_EXTREME = 0.666666667; //67 67 funny meme lol. The goal here is to make it so that a high extremism in one axis
    // will get you like ~50-60% of the way to being extreme, which combined with the other three axis, should land you a 100%. But also some government that's
    // extreme on one axis, but extremely mild on another would be only 80% extreme.
    static final double MAX_EXTREME = TOTAL_AXIS * PERCENTAGE_TO_BE_EXTREME;
    static final int COMPASS_MAX = 512;
    enum Axis{
        INDIVIDUAL_COLLECTIVE(IPoliticalCompass::getICAxis),
        UNIVERSAL_PARTICULAR(IPoliticalCompass::getUPAxis),
        TRUST_IN_OPACITY(IPoliticalCompass::getTOSAxis),
        EGALITARIAN_HIERARCHY(IPoliticalCompass::getEHAxis);
        private final Function<IPoliticalCompass,BoundedInteger> axisGetter;
        Axis(Function<IPoliticalCompass,BoundedInteger> axisGetter){
            this.axisGetter = axisGetter;
        }
        public BoundedInteger get(IPoliticalCompass compass){
            return axisGetter.apply(compass);
        }
        static BoundedInteger get(Axis compass, IPoliticalCompass compassObject){
            return compass.axisGetter.apply(compassObject);
        }
    }
    // Which rights are the natural base for all rights: individual rights or collective rights. Low values are Collectivists: advocating for rights for groups, not individuals. High values are Individualists: advocating for individual rights that should not be trampled by the needs of the collective.
    // Examples of Low values: Communists, Nazis
    // Examples of High values: Anarchists
    BoundedInteger getICAxis();
    // Who matters morally and politically. Low values are Universalist: Advocating for everyone to join the ideology. High values are Particularists: Advocating for their group alone.
    // Examples of Low values: Communists, NeoLiberals,
    // Examples of High values: Nazis, Fascists
    BoundedInteger getUPAxis();
    // How trusting a person or ideology is in large entities that are opaque in concept and operation. Low values are Low Trust, meaning they do not trust entities they do not control. High values are High Trust, meaning they implicitly trust entities they do not understand
    // Examples of Low values: Populists
    // Examples of High values: Technocrats, Traditional Authoritarians
    BoundedInteger getTOSAxis();
    //How much a person or ideology values vertical power structures. High values value strict hierarchy, while low values are more egalitarian.
    //Examples of low values: Libertarian Socialists, Anarchists
    //Examples of high values: Stalinists,Randian Capitalists
    BoundedInteger getEHAxis();
    ImmutableMap<Axis,BoundedInteger> getAxisMap();
    default int calculateTotalExtremismPercent(){
        return (int) (calculateTotalExtremism() * 100);
    }
    default double calculateTotalExtremism(){
        double axisA = calculateExtremism(getICAxis().get(),false);
        double axisB = calculateExtremism(getUPAxis().get(),false);
        double axisC = calculateExtremism(getTOSAxis().get(),false);
        double axisD = calculateExtremism(getEHAxis().get(),false);
        double total = (axisA + axisB + axisC + axisD)/MAX_EXTREME;

        return Math.min(1,total);
    }
    default double calculateExtremismAxis(Axis axis, boolean keepNegative){
        return calculateExtremism(axis.get(this).get(),keepNegative);
    }
    private static double calculateExtremism(double axis, boolean keepNegative){
        double d = Math.pow(Math.abs(axis) / COMPASS_MAX,2);
        if (keepNegative && axis < 0){
            return d * -1;
        } else {
            return d;
        }
    }
    default void amendCompass(Axis axis, int acceptance){
        BoundedInteger axisValue = axis.get(this);
        axisValue.add(acceptance);
    }
    default Ideology getClosestIdeology(IPoliticalCompass compass){
        Ideology closest = null;
        double closetMatch = Double.MIN_VALUE;
        for (Ideology ideology : TenetManager.getIdeologies()) {
            double distance = compass.getCompatibilityValue(ideology.getCompass());
            if (distance > closetMatch) {
                closest = ideology;
                closetMatch = distance;
            }
        }
        return closest;
    }
    default double getCompatibilityValue(IPoliticalCompass other){
        double incompatability = 0;
        for (IPoliticalCompass.Axis axis : Axis.values()) {
            double axisA = calculateExtremismAxis(axis,true);
            double axisB = other.calculateExtremismAxis(axis,true);
            double diff = 2 * Math.abs(axisA - axisB);
            double multiplier = 1;
            if ((axisA < 0 && axisB < 0) || (axisA > 0 && axisB > 0)){
                //Because they're on the "same side" their hatred is slightly lesser.
                multiplier = .9;
            } else if (diff > 1.5){
                multiplier = 1.1;
                //Amp extremism slightly here;
            }
            incompatability +=  diff * multiplier;
        }
        double gap = 1 - (incompatability / Math.max(6,incompatability));
        return  (gap * (MAX_VALUE * 2)) - MAX_VALUE;
    }
    @Override
    default JsonObject toJson() {
        JsonObject o = new JsonObject();
        o.addProperty("axisA", getUPAxis().get());
        o.addProperty("axisB", getICAxis().get());
        o.addProperty("axisC", getTOSAxis().get());
        o.addProperty("axisD",getEHAxis().get());
        return o;
    }


    IPoliticalCompass clone();

    @Override
    default void fromJson(JsonObject json) {
        getUPAxis().set(json.get("axisA").getAsInt());
        getICAxis().set(json.get("axisB").getAsInt());
        getTOSAxis().set(json.get("axisC").getAsInt());
        getEHAxis().set(json.get("axisD").getAsInt());
    }
}
