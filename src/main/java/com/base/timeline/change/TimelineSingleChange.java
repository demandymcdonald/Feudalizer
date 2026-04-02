package com.base.timeline.change;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonObject;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;

public abstract class TimelineSingleChange<T extends DateMutableEntity<T>> extends TimelineChange<T> {
    protected TimelineSingleChange(DMEReference<T> owner, LocalDate date) {
        super(owner, date);
    }

    @Override
    public final void advance(DMEReference<? extends T> entity, TimelineState<? extends T> currentState, TimelineChange<? super T> newState, boolean isFirstAdvance) {
        super.advance(currentState, newState, isFirstAdvance);
    }

    @Override
    public final void apply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
        super.apply(entity, currentState);
    }

    @Override
    public final void deactivate(boolean sandbox) {
        super.deactivate(sandbox);
    }

    @Override
    public final void nullify(DMEReference<? extends T> entity, TimelineState<? extends T> state, TimelineChange<? super T> changeToNullify) {
        super.nullify(entity, state, changeToNullify);
    }

    @Override
    public final void moveChange(@Nullable LocalDate newStart, @Nullable LocalDate newEnd) {
        super.moveChange(newStart, newEnd);
    }

    @Override
    public final void overwrite(TimelineState<? extends T> currentState, TimelineChange<? super T> beingOverwritten, boolean destructive) {
        super.overwrite(currentState, beingOverwritten, destructive);
    }

    @Override
    public final void reactivate(boolean sandbox) {
        super.reactivate(sandbox);
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
