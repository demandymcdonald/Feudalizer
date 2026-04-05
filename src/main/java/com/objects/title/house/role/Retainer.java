package com.objects.title.house.role;

import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.TimelineChange;
import com.objects.CauseOfEnd;
import com.objects.title.condition.CanHoldCondition;
import com.objects.title.condition.CanInheritCondition;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Retainer extends HouseRole<Retainer> {

    public Retainer(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<Retainer, ?>> initialState) {
        super(id, created, ended, initialState);
    }

    public Retainer(LocalDate created, LocalDate ended, List<ChangeSupplier<Retainer, ?>> initialState) {
        super(created, ended, initialState);
    }

    public Retainer(DMEReference<Retainer> dme) {
        super(dme);
    }

    @Override
    protected int basePrestige() {
        return 0;
    }

    @Override
    protected List<CanHoldCondition<? super Retainer>> getCanHoldConditions() {
        return List.of();
    }

    @Override
    protected List<CanInheritCondition<? super Retainer>> getCanInheritConditions() {
        return List.of();
    }

    @Override
    public String getTitleName() {
        return "";
    }

    @Override
    public void doDateChange() {

    }

    @Override
    public TimelineChange<Retainer> getBirthChange(DMEReference<Retainer> dme, LocalDate date) {
        return null;
    }

    @Override
    public TimelineChange<Retainer> getDeathChange(DMEReference<Retainer> dme, LocalDate date, CauseOfEnd cOd) {
        return null;
    }

    @Override
    public CauseOfEnd defaultDeathCause() {
        return null;
    }
}
