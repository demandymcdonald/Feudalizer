package com.objects.culture.object.compass;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.objects.culture.tenet.TenetManager;
import com.utilities.Displayable;
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
    enum Axis implements Displayable {
        INDIVIDUAL_COLLECTIVE(
                "axis_a_col_ind",
                "Individual vs. Collective",
                "Concerns the fundamental source of rights: whether they originate with the individual or the " +
                "group. Collectivists hold that group rights form the basis of all other rights, with individual" +
                " claims subordinate to collective need. Individualists hold that personal rights are inviolable " +
                "and cannot be legitimately overridden by collective interests. Low values represent collectivist " +
                "positions such as Communism and Fascism; high values represent individualist positions such as " +
                "Anarchism.",
                IPoliticalCompass::getAxisA),
        UNIVERSAL_PARTICULAR(
                "axis_b_uni_par",
                "Universalism vs. Particularism",
                "Concerns who is considered a legitimate subject of moral and political concern. " +
                "Universalists hold that their ideology's benefits and membership should extend to all of " +
                "humanity without distinction. Particularists hold that rights and political concern apply" +
                " exclusively to their own group. Low values represent universalist positions such as" +
                " Communism and Neoliberalism; high values represent particularist positions such " +
                "as Fascism and Nazism.",
                IPoliticalCompass::getAxisB),
        TRUST_IN_OPACITY(
                "axis_c_ltr_htr",
                "Low Institutional Trust vs. High Institutional Trust",
                " Concerns the degree of trust extended to large, opaque institutions such as governments, " +
                "markets, religions, and expert bodies. Low-trust positions hold that unaccountable power is " +
                "inherently suspect, favoring skepticism and direct oversight. High-trust positions hold that" +
                " such institutions embody accumulated wisdom or expertise beyond individual comprehension, " +
                "warranting deference or faith. Low values represent positions such as Populism; high values" +
                " represent positions such as Technocracy and Traditional Authoritarianism.",
                IPoliticalCompass::getAxisC),
        EGALITARIAN_HIERARCHY(
                "axis_d_ega_hie",
                "Egalitarianism vs. Hierarchy",
                "Concerns the legitimacy of vertical power structures. Egalitarians hold that flat, " +
                "distributed arrangements of power are more just and functional than ranked ones. Hierarchists " +
                "hold that stratified structures are natural, necessary, or both. Low values represent " +
                "positions such as Anarchism and Libertarian Socialism; high values represent positions " +
                "such as Stalinism and Randian Capitalism.",
                IPoliticalCompass::getAxisD);
        private final String id;
        private final String name;
        private final String description;
        private final Function<IPoliticalCompass,BoundedInteger> getter;
        Axis(String id, String name, String description, Function<IPoliticalCompass,BoundedInteger> getter){
            this.id = id;
            this.name = name;
            this.description = description;
            this.getter = getter;
        }
        public BoundedInteger get(IPoliticalCompass compass){
            return getter.apply(compass);
        }
        static BoundedInteger get(Axis compass, IPoliticalCompass compassObject){
            return compass.getter.apply(compassObject);
        }

        @Override
        public String getDisplayID() {
            return "";
        }

        @Override
        public String displayName() {
            return "";
        }

        @Override
        public String description() {
            return "";
        }
    }
    // Which rights are the natural base for all rights: individual rights or collective rights. Low values are Collectivists: advocating for rights for groups, not individuals. High values are Individualists: advocating for individual rights that should not be trampled by the needs of the collective.
    // Examples of Low values: Communists, Nazis
    // Examples of High values: Anarchists
    BoundedInteger getAxisA();
    // Who matters morally and politically. Low values are Universalist: Advocating for everyone to join the ideology. High values are Particularists: Advocating for their group alone.
    // Examples of Low values: Communists, NeoLiberals,
    // Examples of High values: Nazis, Fascists
    BoundedInteger getAxisB();
    // How trusting a person or ideology is in large entities that are opaque in concept and operation. Low values are Low Trust, meaning they do not trust entities they do not control. High values are High Trust, meaning they implicitly trust entities they do not understand
    // Examples of Low values: Populists
    // Examples of High values: Technocrats, Traditional Authoritarians
    BoundedInteger getAxisC();
    //How much a person or ideology values vertical power structures. High values value strict hierarchy, while low values are more egalitarian.
    //Examples of low values: Libertarian Socialists, Anarchists
    //Examples of high values: Stalinists,Randian Capitalists
    BoundedInteger getAxisD();
    ImmutableMap<Axis,BoundedInteger> getAxisMap();
    default int calculateTotalExtremismPercent(){
        return (int) (calculateTotalExtremism() * 100);
    }
    default double calculateTotalExtremism(){
        double axisA = calculateExtremism(getAxisA().get(),false);
        double axisB = calculateExtremism(getAxisB().get(),false);
        double axisC = calculateExtremism(getAxisC().get(),false);
        double axisD = calculateExtremism(getAxisD().get(),false);
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
        double gap = 1 - (incompatability / Math.max(2 * TOTAL_AXIS,incompatability));
        return  (gap * (MAX_VALUE * 2)) - MAX_VALUE;
    }
    @Override
    default JsonObject toJson() {
        JsonObject o = new JsonObject();
        o.addProperty("axisA", getAxisB().get());
        o.addProperty("axisB", getAxisA().get());
        o.addProperty("axisC", getAxisC().get());
        o.addProperty("axisD", getAxisD().get());
        return o;
    }


    IPoliticalCompass clone();

    @Override
    default void fromJson(JsonObject json) {
        getAxisB().set(json.get("axisA").getAsInt());
        getAxisA().set(json.get("axisB").getAsInt());
        getAxisC().set(json.get("axisC").getAsInt());
        getAxisD().set(json.get("axisD").getAsInt());
    }
}
