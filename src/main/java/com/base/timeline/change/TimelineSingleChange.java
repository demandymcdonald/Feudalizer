package com.base.timeline.change;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.condition.apply.ApplyCondition;
import com.base.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.timeline.change.condition.nullify.NullifyCondition;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonObject;
import com.objects.culture.Culture;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.List;

public abstract class TimelineSingleChange<T extends DateMutableEntity<?>> extends TimelineChange<T> {
    protected TimelineSingleChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }

    @Override
    public final void override(TimelineState<? extends T> currentState, TimelineChange<?> beingOverwritten, boolean isSandbox, boolean destructive) {
        super.override(currentState, beingOverwritten, isSandbox, destructive);
    }

    @Override
    public final void nullify(DMEReference<? extends T> entity, TimelineState<? extends T> state, TimelineChange<?> changeToNullify) {
        super.nullify(entity, state, changeToNullify);
    }

    @Override
    public final void apply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
        super.apply(entity, currentState);
    }

    @Override
    public final void deactivate(boolean isSandbox) {
        super.deactivate(isSandbox);
    }



    @Override
    public final void moveChange(@Nullable LocalDate newStart, @Nullable LocalDate newEnd) {
        super.moveChange(newStart, newEnd);
    }
    @Override
    protected void nullifyConditions(List<NullifyCondition<? super T>> list) {

    }
    @Override
    protected void applyConditions(List<ApplyCondition<? super T>> list) {
        list.add(new HasVariableClass(this.getClass()));
    }
    @Override
    protected void deactivateConditions(List<DeactivateCondition<? super T>> list) {
        list.add(new IsOnlyData());
    }
    @Override
    public final void reactivate(boolean isSandbox) {
        super.reactivate(isSandbox);
    }
    @Override
    public final List<Class<TimelineChange<? super T>>> oppositeChanges() {
        return List.of();
    }

    @Override
    public final boolean isPositive() {
        return true;
    }
    @Override
    public final void mainSave(JsonObject o) {
        super.mainSave(o);
    }

    @Override
    public final void mainLoad(JsonObject object) {
        super.mainLoad(object);
    }

}
