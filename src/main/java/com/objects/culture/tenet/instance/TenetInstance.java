package com.objects.culture.tenet.instance;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.multi.type.ChangeType;
import com.base.timeline.variable.EasingVariable;
import com.google.gson.JsonObject;
import com.objects.culture.object.CultureObject;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.TenetReference;
import com.utilities.id.Identifiable;
import com.utilities.id.StringIdentifiable;
import com.utilities.id.UUIDIdentifiable;
import com.utilities.number.BoundedDouble;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.objects.culture.tenet.Acceptance.MAX_VALUE;
//public class TenetInstance<TI extends TenetInstance<TI,T,TC>,T extends DateMutableEntity<T> & CultureObject<T>,TC extends TenetInstanceChange<TC,T,TI>> implements EasingVariable<TI,TC,T>
public class TenetInstance<T extends DateMutableEntity<T> & CultureObject<T>>implements EasingVariable<TenetInstance<T>,TenetInstanceChange<T>,T>, UUIDIdentifiable {
//    private static final double b = .23; //apathy peak as percent from start
//    private static final double c = 0.00022; //apathy decay
//    private static final double z = .125; // zealotry peak as percent from end. Should hit right as the they pass the Fanatic mark
//    private static final double f = .69; // zealotry drop-off  target
//    private static final double g = 2.2; //zealotry drop-off steepness
//    private static final double k = .05; //kernal floor
//    private static final int pf = 2; //crushing power for normalization
    private DMEReference<T> owner;
    private TenetReference tenet;
    private UUID id;
    private final BoundedDouble opinion = new BoundedDouble(-MAX_VALUE, MAX_VALUE);
    private final BoundedDouble interpolated = new BoundedDouble(-MAX_VALUE, MAX_VALUE);
    private MutableBoolean isActive = new MutableBoolean(false);

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
    public TenetInstance(TenetReference reference, DMEReference<? extends T> owner, double opinion, boolean active){
        this.owner = (DMEReference<T>) owner;
        this.tenet = reference;
        this.opinion.set(opinion);
        this.id = UUID.randomUUID();
        this.isActive.setValue(active);
    }
    public TenetInstance(TenetReference reference, DMEReference<? extends T> owner, double opinion){
        this.owner = (DMEReference<T>) owner;
        this.tenet = reference;
        this.opinion.set(opinion);
        this.id = UUID.randomUUID();
    }
    public TenetInstance(){
        this.owner = null;
    }
    public Acceptance getAcceptance(){
        return Acceptance.get((int) Math.round(opinion.get()));
    }
    public double get(){
        return interpolated.get();
    }
    public double getRaw(){
        return opinion.get();
    }
    public void set(double opinion){
        Consumer<TenetInstance<T>> consumer = (value) -> {
            value.opinion.set(opinion);
            value.calculateVariables();
        };
        owner.get().getContainer().getOpinions().setChanged(true,ChangeType.VALUE,Map.of(this,consumer));
    }
    public void add(double opinion){
        Consumer<TenetInstance<T>> consumer = (value) -> {
            value.opinion.add(opinion);
            value.calculateVariables();
        };
        owner.get().getContainer().getOpinions().setChanged(true,ChangeType.KEY,Map.of(this,consumer));
    }
    public void setActive(){
        Consumer<TenetInstance<T>> consumer = (value) -> {
            value.isActive.setValue(true);
        };
        owner.get().getContainer().getOpinions().setChanged(true,ChangeType.VALUE,Map.of(this,consumer));
    }
    public boolean isActive(){
        return isActive.booleanValue();
    }
    public MutableBoolean getActive(){
        return isActive;
    }
    @Override
    public void mainSave(JsonObject object) {
        object.addProperty("opinion", opinion.get());
        object.add("owner", owner.serialize());
        object.add("reference", tenet.serialize());
        object.addProperty("id", id.toString());
    }

    @Override
    public void mainLoad(JsonObject object) {
        opinion.set(object.get("opinion").getAsDouble());
        id = UUID.fromString(object.get("id").getAsString());
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

    public TenetReference getTenet() {
        return tenet;
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

    @Override
    public UUID getID() {
        return id;
    }
}
