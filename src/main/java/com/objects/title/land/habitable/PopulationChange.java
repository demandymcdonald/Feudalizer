package com.objects.title.land.habitable;

import com.Global.*;
import com.base.reference.DMEReference;
import com.base.timeline.TimelineContainer;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.TimelineSingleChange;
import com.base.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.timeline.change.condition.nullify.NullifyCondition;
import com.base.timeline.change.multi.TimelineMapChange;
import com.base.timeline.state.TimelineState;
import com.base.timeline.variable.EasingChange;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.objects.culture.tenet.caste.CasteObject;
import com.utilities.number.BoundedDouble;
import com.utilities.number.BoundedInteger;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

public class PopulationChange<T extends HabitableLand<T>> extends TimelineMapChange<PopulationChange<T>, CasteObject, BoundedDouble,String,T> implements EasingChange<PopulationContainer<T>,PopulationChange<T>,T> {
    long population;


    protected PopulationChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }



    @Override
    public boolean hasEndingChanges() {
        return true;
    }

    @Override
    protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {

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
    protected JsonElement vSerialize(BoundedInteger boundedInteger) {
        return null;
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
    protected void nullifyConditions(List<NullifyCondition<? super T>> list) {

    }

    @Override
    protected void deactivateConditions(List<DeactivateCondition<? super T>> list) {

    }

    @Override
    public PopulationContainer<T> getEasingVariable(Predicate<PopulationChange<T>> matching) {
        return getOwner().get().getPopulationContainer();
    }

    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }
}
