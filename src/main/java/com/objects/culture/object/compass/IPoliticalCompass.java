package com.objects.culture.object.compass;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.AcceptanceContainer;
import com.objects.culture.TenetManager;
import com.utilities.IDisplayable;
import com.utilities.number.BoundInt;
import com.utilities.serialization.JsonSerializable;

import java.util.Arrays;
import java.util.function.Function;

import static com.objects.culture.tenet.Acceptance.MAX_VALUE;

public interface IPoliticalCompass extends JsonSerializable {
    static final int TOTAL_AXIS = (int) Arrays.stream(IPoliticalCompass.Axis.values()).count();
    static final double PERCENTAGE_TO_BE_EXTREME = 0.366666667; //67 67 funny meme lol. The goal here is to make it so that a high extremism in one axis
    // will get you like ~60-70% of the way to being extreme, which combined with the other three axis, should land you a 100%. But also some government that's
    // extreme on one axis, but extremely mild on another would be only 80% extreme.
    static final double MAX_EXTREME = TOTAL_AXIS * PERCENTAGE_TO_BE_EXTREME;
    static final int COMPASS_MAX = 512;
    enum Axis implements IDisplayable {
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
        private final Function<IPoliticalCompass,Value> getter;
        Axis(String id, String name, String description, Function<IPoliticalCompass,Value> getter){
            this.id = id;
            this.name = name;
            this.description = description;
            this.getter = getter;
        }
        public Value get(IPoliticalCompass compass){
            return getter.apply(compass);
        }
        static Value get(Axis compass, IPoliticalCompass compassObject){
            return compass.getter.apply(compassObject);
        }

        @Override
        public String getDisplayID() {
            return "";
        }

        @Override
        public String getDisplayName() {
            return "";
        }

        @Override
        public String getDescription() {
            return "";
        }
    }
    // Which rights are the natural base for all rights: individual rights or collective rights. Low values are Collectivists: advocating for rights for groups, not individuals. High values are Individualists: advocating for individual rights that should not be trampled by the needs of the collective.
    // Examples of Low values: Communists, Nazis
    // Examples of High values: Anarchists
    Value getAxisA();
    // Who matters morally and politically. Low values are Universalist: Advocating for everyone to join the ideology. High values are Particularists: Advocating for their group alone.
    // Examples of Low values: Communists, NeoLiberals,
    // Examples of High values: Nazis, Fascists
    Value getAxisB();
    // How trusting a person or ideology is in large entities that are opaque in concept and operation. Low values are Low Trust, meaning they do not trust entities they do not control. High values are High Trust, meaning they implicitly trust entities they do not understand
    // Examples of Low values: Populists
    // Examples of High values: Technocrats, Traditional Authoritarians
    Value getAxisC();
    //How much a person or ideology values vertical power structures. High values value strict hierarchy, while low values are more egalitarian.
    //Examples of low values: Libertarian Socialists, Anarchists
    //Examples of high values: Stalinists,Randian Capitalists
    Value getAxisD();
    //How tolerant an ideology is.
    Value getTolerance();
    ImmutableMap<Axis,Value> getAxisMap();
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
        double pre =  calculateExtremism(axis.get(this).get(),keepNegative);
        if (getTolerance().get() > COMPASS_MAX * .75){
            pre *= 1 - Math.clamp((double) Math.abs(getTolerance().get()) / COMPASS_MAX,0,.15);
        } else if(getTolerance().get() <  COMPASS_MAX * -.25){
            pre *= 1 + Math.clamp((double) Math.abs(getTolerance().get()) / COMPASS_MAX,0,.15);
        }
        return pre;
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
        Value axisValue = axis.get(this);
        axisValue.add(acceptance);
    }
    default void setCompass(Axis axis, int acceptance){
        Value axisValue = axis.get(this);
        axisValue.set(acceptance);
    }
    default Ideology getClosestIdeology(IPoliticalCompass compass){
        Ideology closest = null;
        double closetMatch = Double.MIN_VALUE;
        for (Ideology ideology : TenetManager.getIdeologies()) {
            double distance = this.getCompatibilityValue(ideology.getCompass(),false);
            if (distance > closetMatch) {
                closest = ideology;
                closetMatch = distance;
            }
        }
        return closest;
    }
    default Acceptance getCompatibility(IPoliticalCompass compass){
        return Acceptance.get((int) Math.round(getCompatibilityValue(compass,false)));
    }
    default double getIdeologicalDistance(Axis axis, IPoliticalCompass other){
        double axisA = calculateExtremismAxis(axis,true);
        double axisB = other.calculateExtremismAxis(axis,true);
        return 2 * Math.abs(axisA - axisB);
    }
    default double getCompatibilityValue(IPoliticalCompass other, boolean factorOtherTolerance){
        double incompatability = 0;
        final double multiA =  (double) getTolerance().get() /((double) MAX_VALUE /2); //This gives a double between -2 and 2.
        final double multiplier;
        if (!factorOtherTolerance){
            //Because sometimes it REALLY doesn't matter how tolerant one side is lol.
            multiplier = (1 + Math.abs(((multiA) - 2)/2));
        } else {
            final double multiB =  (double) other.getTolerance().get() /((double) MAX_VALUE /2);
            multiplier = (1 + Math.abs(((multiA + multiB) - 4)/4));
        }
        for (IPoliticalCompass.Axis axis : Axis.values()) {
            incompatability +=  getIdeologicalDistance(axis,other) * multiplier;
        }
        double gap = 1 - (incompatability / Math.max(2 * TOTAL_AXIS,incompatability));
        return  (gap * (MAX_VALUE * 2)) - MAX_VALUE;
    }
    default AcceptanceContainer getAcceptanceContainer(IPoliticalCompass compass, boolean factorOtherTolerance){
        return new AcceptanceContainer(getCompatibilityValue(compass,factorOtherTolerance));
    }
    default Value getByAxis(Axis axis){
        return axis.get(this);
    }
    ;
    @Override
    default JsonObject toJson() {
        JsonObject o = new JsonObject();
        o.addProperty("axisA", getAxisA().get());
        o.addProperty("axisB", getAxisB().get());
        o.addProperty("axisC", getAxisC().get());
        o.addProperty("axisD", getAxisD().get());
        o.addProperty("mod", getTolerance().get());
        return o;
    }


    IPoliticalCompass clone();

    @Override
    default void fromJson(JsonObject json) {
        getAxisA().set(json.get("axisA").getAsInt());
        getAxisB().set(json.get("axisB").getAsInt());
        getAxisC().set(json.get("axisC").getAsInt());
        getAxisD().set(json.get("axisD").getAsInt());
        getTolerance().set(json.get("mod").getAsInt());
    }
    class Value extends BoundInt {
        public Value(int number) {
            super(number);
        }

        @Override
        public int getMin() {
            return -COMPASS_MAX;
        }

        @Override
        public int getMax() {
            return COMPASS_MAX;
        }
    }
}
