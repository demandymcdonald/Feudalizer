package com.objects.culture.tenet.instance;

import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.multi.type.ChangeType;
import com.base.datemutable.timeline.variable.EasingVariable;
import com.google.gson.JsonObject;
import com.objects.culture.object.CultureObject;
import com.objects.culture.object.change.InfluencerMapChange;
import com.objects.culture.object.change.OpinionChange;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.TenetReference;
import com.utilities.id.Identifiable;
import com.utilities.id.StringIdentifiable;
import com.utilities.id.UUIDIdentifiable;
import com.utilities.number.bound_double.BoundedDouble;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.objects.culture.tenet.Acceptance.MAX_VALUE;

public class TenetInstance<T extends DateMutableEntity<T> & CultureObject<T>>implements EasingVariable<TenetInstance<T>, OpinionChange<T>,T>, UUIDIdentifiable {
    private final DMEReference<T> owner;
    private TenetReference tenet;
    private UUID id;
    private final BoundedDouble opinion = new BoundedDouble(-MAX_VALUE, MAX_VALUE);
    private final BoundedDouble interpolated = new BoundedDouble(-MAX_VALUE, MAX_VALUE);
    private final MutableBoolean isActive = new MutableBoolean(false);

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
    public TenetInstance(DMEReference<? extends T> owner){
        this.owner = (DMEReference<T>) owner;
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
        object.add("reference", tenet.serialize());
        object.addProperty("id", id.toString());
    }
    @Override
    public void mainLoad(JsonObject object) {
        opinion.set(object.get("opinion").getAsDouble());
        id = UUID.fromString(object.get("id").getAsString());
        tenet = TenetReference.deserialize(object.get("reference").getAsJsonObject());
    }
    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }
    private static final String name = InfluencerMapChange.class.getName();
    @Override
    public String getChangeClassName() {
        return name;
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
