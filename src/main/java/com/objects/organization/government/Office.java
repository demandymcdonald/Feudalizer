package com.objects.organization.government;

import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.TimelineChange;
import com.google.gson.JsonObject;
import com.objects.CauseOfEnd;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Office extends Government<Office>{
    DMEReference<GoverningEntity<?>> parent;

    public Office(LocalDate created, LocalDate ended, List<ChangeSupplier<Office, ?>> initialState) {
        super(created, ended, initialState);
    }

    public Office(DMEReference<Office> dme) {
        super(dme);
    }

    public Office(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<Office, ?>> initialState) {
        super(id, created, ended, initialState);
    }





    @Override
    protected void onLink() {

    }

    @Override
    public void doDateChange() {

    }

    @Override
    public TimelineChange<Office> getBirthChange(DMEReference<Office> dme, LocalDate date) {
        return null;
    }

    @Override
    public TimelineChange<Office> getDeathChange(DMEReference<Office> dme, LocalDate date, CauseOfEnd<? super Office> cOd) {
        return null;
    }

    @Override
    public CauseOfEnd<? super Office> defaultDeathCause() {
        return null;
    }

    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }
}
