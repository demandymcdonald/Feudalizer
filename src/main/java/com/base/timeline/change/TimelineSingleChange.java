package com.base.timeline.change;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.changes.TimelineChange;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonObject;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;

public abstract class TimelineSingleChange<T extends DateMutableEntity<T>> extends TimelineChange<T> {
    protected TimelineSingleChange(DMEReference<T> owner, LocalDate date) {
        super(owner, date);
    }

    @Override
    public final void advance(DMEReference<? extends T> entity, TimelineState<? extends T> currentState, TimelineChange<?> change, boolean isFirstAdvance) {
        super.advance(entity, currentState, change, isFirstAdvance);
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
    public final void reactivate(boolean isSandbox) {
        super.reactivate(isSandbox);
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
