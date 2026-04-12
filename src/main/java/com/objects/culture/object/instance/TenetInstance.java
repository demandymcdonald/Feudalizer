package com.objects.culture.object.instance;

import com.Global;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.variable.EasingVariable;
import com.google.gson.JsonObject;
import com.objects.culture.object.CultureObject;
import com.objects.culture.tenet.TenetReference;
import com.utilities.id.Identifiable;
import com.utilities.id.StringIdentifiable;
import com.utilities.number.BoundedDouble;

import java.util.Map;
import java.util.function.Predicate;

import static com.objects.culture.tenet.Acceptance.MAX_VALUE;
//public class TenetInstance<TI extends TenetInstance<TI,T,TC>,T extends DateMutableEntity<T> & CultureObject<T>,TC extends TenetInstanceChange<TC,T,TI>> implements EasingVariable<TI,TC,T>
public class TenetInstance<T extends DateMutableEntity<T> & CultureObject<T>>implements EasingVariable<TenetInstance<T>,TenetInstanceChange<T>,T> {
//    private static final double b = .23; //apathy peak as percent from start
//    private static final double c = 0.00022; //apathy decay
//    private static final double z = .125; // zealotry peak as percent from end. Should hit right as the they pass the Fanatic mark
//    private static final double f = .69; // zealotry drop-off  target
//    private static final double g = 2.2; //zealotry drop-off steepness
//    private static final double k = .05; //kernal floor
//    private static final int pf = 2; //crushing power for normalization
    private DMEReference<T> owner;
    private TenetReference tenet;
    private final BoundedDouble opinion = new BoundedDouble(-MAX_VALUE, MAX_VALUE);
    private final BoundedDouble interpolated = new BoundedDouble(-MAX_VALUE, MAX_VALUE);
    private static final StringIdentifiable doubleID = new StringIdentifiable("opinion"){
        @Override
        public String getID() {
            return "opinion";
        }

        @Override
        public String toString() {
            return "opinion";
        }
    };
    public TenetInstance(TenetReference reference, DMEReference<? extends T> owner, double opinion){
        this.owner = (DMEReference<T>) owner;
        this.tenet = reference;
        this.opinion.set(opinion);
    }
    public TenetInstance(){
        this.owner = null;
    }
    public double get(){
        return interpolated.get();
    }
    public double getRaw(){
        return opinion.get();
    }
    public void set(double opinion){
        this.opinion.set(opinion);
        onLoad(Global.getDate());
    }
    public void add(double opinion){
        this.opinion.add(opinion);
        onLoad(Global.getDate());
    }
    @Override
    public void mainSave(JsonObject object) {
        object.addProperty("opinion", opinion.get());
        object.add("owner", owner.serialize());
        object.add("reference", tenet.serialize());
    }

    @Override
    public void mainLoad(JsonObject object) {
        opinion.set(object.get("opinion").getAsDouble());
        owner = DMEReference.deserialize(object.get("owner").getAsJsonObject());
        tenet = TenetReference.deserialize(object.get("reference").getAsJsonObject());
    }

    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }

    @Override
    public String getChangeClassName() {
        return TenetInstanceChange.class.getName();
    }

    @Override
    public DMEReference<? extends T> getOwner() {
        return owner;
    }

    @Override
    public Map<Identifiable<?>, VariableContainer> getEasingFunctions() {
        return Map.of(
            doubleID,new VariableContainer(EasingType.CUBIC,()->opinion.get(),(v)->interpolated.set(v))
        );
    }

    @Override
    public Predicate<TenetInstance<T>> getMatching() {
        return (ti) -> {
            return ti.tenet.equals(tenet);
        };
    }
}
