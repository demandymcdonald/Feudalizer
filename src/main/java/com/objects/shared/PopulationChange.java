package com.objects.shared;

import com.Global;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.TimelineObject;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.timeline.change.condition.nullify.NullifyCondition;
import com.base.timeline.change.multi.MultiCondition;
import com.base.timeline.change.multi.TimelineMap;
import com.base.timeline.change.multi.TimelineMapChange;
import com.base.timeline.state.TimelineState;
import com.base.timeline.variable.EasingChange;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.objects.culture.tenet.caste.CasteObject;
import com.objects.title.land.habitable.HabitableLand;
import com.utilities.number.BoundedInteger;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class PopulationChange<T extends DateMutableEntity<T> & IDemographicDriven<T>> extends TimelineMapChange<PopulationChange<T>, CasteObject, BoundedInteger,String,T> implements EasingChange<PopulationContainer<T>,PopulationChange<T>,T> {
    long population;

    public PopulationChange(DMEReference<? extends T> owner, LocalDate date, Map<CasteObject, BoundedInteger> initial) {
        super(owner, date, initial);
        population = 0;
    }

    protected PopulationChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }

    @Override
    public void setRuntimeMap(TimelineMap<CasteObject, BoundedInteger, T> map) {
        getOwner().get().getPopulationContainer().internalSetMap(map);
    }

    @Override
    public TimelineMap<CasteObject, BoundedInteger,T> getRuntimeMap() {
        return getOwner().get().getPopulationContainer().internalGetPopulationMap();
    }
    public void setPopulation(long population){
        this.population = population;
    }

    @Override
    public boolean hasEndingChanges() {
        return false;
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
    protected JsonElement kSerialize(CasteObject casteObject) {
        return null;
    }

    @Override
    protected CasteObject kDeserialize(JsonElement o) {
        return null;
    }


    @Override
    protected JsonElement vSerialize(BoundedInteger boundedDouble) {
        return new JsonPrimitive(boundedDouble.get());
    }

    @Override
    protected BoundedInteger vDeserialize(JsonElement o) {
        return null;
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
    public void addConditions(List<MultiCondition<PopulationChange<T>, CasteObject, BoundedInteger, String, T>> current) {

    }

    @Override
    public void removeConditions(List<MultiCondition<PopulationChange<T>, CasteObject, BoundedInteger, String, T>> current) {

    }

    @Override
    public void removeWipeFConditions(List<MultiCondition<PopulationChange<T>, CasteObject, BoundedInteger, String, T>> current) {

    }

    @Override
    public void removeWipeBConditions(List<MultiCondition<PopulationChange<T>, CasteObject, BoundedInteger, String, T>> current) {

    }

    @Override
    public void modifyKeyConditions(List<MultiCondition<PopulationChange<T>, CasteObject, BoundedInteger, String, T>> current) {

    }

    @Override
    public void modifyValueConditions(List<MultiCondition<PopulationChange<T>, CasteObject, BoundedInteger, String, T>> current) {

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
}
