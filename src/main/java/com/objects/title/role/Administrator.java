package com.objects.title.role;

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

public class Administrator extends HouseRole<Administrator> {
    public Administrator(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<Administrator, ?>> initialState) {
        super(id, created, ended, initialState);
    }

    public Administrator(LocalDate created, LocalDate ended, List<ChangeSupplier<Administrator, ?>> initialState) {
        super(created, ended, initialState);
    }

    public Administrator(DMEReference<Administrator> dme) {
        super(dme);
    }

    @Override
    protected int basePrestige() {
        return 0;
    }

    @Override
    protected List<CanHoldCondition<? super Administrator>> getCanHoldConditions() {
        return List.of();
    }

    @Override
    protected List<CanInheritCondition<? super Administrator>> getCanInheritConditions() {
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
    public TimelineChange<Administrator> getBirthChange(DMEReference<Administrator> dme, LocalDate date) {
        return null;
    }

    @Override
    public TimelineChange<Administrator> getDeathChange(DMEReference<Administrator> dme, LocalDate date, CauseOfEnd cOd) {
        return null;
    }

    @Override
    public CauseOfEnd defaultDeathCause() {
        return null;
    }
}
