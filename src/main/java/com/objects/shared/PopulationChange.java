package com.objects.shared;

import com.Global;
import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.TimelineObject;
import com.base.datemutable.timeline.change.TimelineChange;
import com.base.datemutable.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.datemutable.timeline.change.multi.condition.MultiCondition;
import com.base.datemutable.timeline.change.multi.MiddlemanMap;
import com.base.datemutable.timeline.change.multi.TimelineMapChange;
import com.base.datemutable.timeline.state.TimelineState;
import com.base.datemutable.timeline.variable.EasingChange;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.objects.culture.tenet.interest.InterestGroup;
import com.utilities.number.BoundInt;
import com.utilities.number.BoundInts;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class PopulationChange<T extends DateMutableEntity<T> & IDemographicDriven<T>> extends TimelineMapChange<PopulationChange<T>, InterestGroup, BoundInt,String,T> implements EasingChange<PopulationContainer<T>,PopulationChange<T>,T> {
    long population;

    public PopulationChange(DMEReference<? extends T> owner, LocalDate date, Map<InterestGroup, BoundInt> initial) {
        super(owner, date, initial);
        population = 0;
    }

    public PopulationChange(DMEReference<? extends T> owner, LocalDate date, long population) {
        super(owner, date);
        this.population = population;
    }

    @Override
    public PopulationChange<T> getEmptyChange(DMEReference<? extends T> owner, LocalDate date) {
        return new PopulationChange<>(owner, date, owner.get().getPopulationContainer().getPopulation());
    }

    protected PopulationChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }

    @Override
    public void setRuntimeMap(MiddlemanMap<InterestGroup, BoundInt, T> map) {
        getOwner().get().getPopulationContainer().internalSetMap(map);
    }

    @Override
    public MiddlemanMap<InterestGroup, BoundInt,T> getRuntimeMap() {
        return getOwner().get().getPopulationContainer().internalGetMap();
    }
    public void setPopulation(long population){
        this.population = population;
    }

    @Override
    public boolean hasEndingChanges() {
        return true;
    }

    @Override
    protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
        PopulationContainer<T> container = entity.get().getPopulationContainer();
        container.internalSetPopulation(population);
        container.internalSetChange(this);
    }
    @Override
    public List<Class<TimelineChange<? super T>>> oppositeChanges() {
        return List.of();
    }



    @Override
    protected JsonElement kSerialize(InterestGroup InterestGroup) {
        return ;
    }

    @Override
    protected InterestGroup kDeserialize(JsonElement o) {
        return null;
    }


    @Override
    protected JsonElement vSerialize(BoundInt boundedDouble) {
        return new JsonPrimitive(boundedDouble.get());
    }

    @Override
    protected BoundInt vDeserialize(JsonElement o) {
        BoundInt tr =  BoundInts.Int256(false);
        tr.set(o.getAsInt());
        return tr;
    }

    @Override
    protected JsonElement iSerialize(String s) {
        return null;
    }

    @Override
    protected String iDeserialize(JsonElement o) {
        return "";
    }

    @Override
    public void merge(PopulationChange<T> other) {

    }

    @Override
    protected String getText() {
        return "";
    }

    @Override
    public void conditionsAdd(List<MultiCondition<PopulationChange<T>, InterestGroup, BoundInt, String, T>> current) {

    }

    @Override
    public void conditionsRemove(List<MultiCondition<PopulationChange<T>, InterestGroup, BoundInt, String, T>> current) {

    }

    @Override
    public void conditionsWipeForward(List<MultiCondition<PopulationChange<T>, InterestGroup, BoundInt, String, T>> current) {

    }

    @Override
    public void conditionsWipeBackward(List<MultiCondition<PopulationChange<T>, InterestGroup, BoundInt, String, T>> current) {

    }

    @Override
    public void conditionsModifyKey(List<MultiCondition<PopulationChange<T>, InterestGroup, BoundInt, String, T>> current) {

    }

    @Override
    public void conditionsModifyValue(List<MultiCondition<PopulationChange<T>, InterestGroup, BoundInt, String, T>> current) {

    }

    @Override
    protected void deactivateConditions(List<DeactivateCondition<? super T>> list) {

    }



    @Override
    public void additionalSave(JsonObject data) {
        data.addProperty("population",population);
    }

    @Override
    public void additionalLoad(JsonObject data) {
        population = data.get("population").getAsInt();
    }

    @Override
    public PopulationContainer<T> getEasingVariable(Predicate<PopulationContainer<T>> matching) {
        return getOwner().get().getPopulationContainer();
    }

    @Override
    public PopulationChange<T> getNext() {
        return TimelineObject.getChangeStep(this, Global.TimeDirection.FORWARD,false,1,null);
    }
    
    public class InterestGroupPercentage extends BoundInt {
        private final PopulationContainer<T> container;
        public InterestGroupPercentage(PopulationContainer<T> parent, int number) {
            super(number);
            container = parent;
        }
        @Override
        public int getMin() {
            return 0;
        }

        @Override
        public void add(int value) {
            super.add(value);
        }

        @Override
        public void set(int value) {
            super.set(value);
        }

        @Override
        public int getMax() {
            return 100;
        }
    }
}
