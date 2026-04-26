package com.base.datemutable.timeline.change.startend;

import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.TimelineChange;
import com.base.datemutable.timeline.change.condition.apply.ApplyCondition;
import com.base.datemutable.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.datemutable.timeline.change.condition.nullify.NullifyCondition;
import com.base.datemutable.timeline.state.TimelineState;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.util.List;

public abstract class CreatedChange<T extends DateMutableEntity<?>> extends TimelineChange<T> {
    protected CreatedChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }

    @Override
    protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {

    }

    @Override
    public List<Class<TimelineChange<? super T>>> oppositeChanges() {
        return List.of();
    }

    @Override
    public boolean isPositive() {
        return false;
    }

    @Override
    protected String getText() {
        return "";
    }

    @Override
    protected void applyConditions(List<ApplyCondition<? super T>> list) {

    }

    @Override
    protected void nullifyConditions(List<NullifyCondition<? super T>> list) {

    }

    @Override
    protected void deactivateConditions(List<DeactivateCondition<? super T>> list) {

    }

    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }
}
