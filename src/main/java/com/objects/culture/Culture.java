package com.objects.culture;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.TimelineChange;
import com.google.gson.JsonObject;
import com.objects.CauseOfEnd;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Culture extends CultureObject<Culture> {
    public Culture(LocalDate created, LocalDate ended, List<ChangeSupplier<Culture, ?>> initialState) {
        super(created, ended, initialState);
    }

    public Culture(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<Culture, ?>> initialState) {
        super(id, created, ended, initialState);
    }

    public Culture(DMEReference<Culture> dme) {
        super(dme);
    }

    @Override
    protected void onLink() {

    }

    @Override
    public void doDateChange() {

    }

    @Override
    public TimelineChange<Culture> getBirthChange(DMEReference<Culture> dme, LocalDate date) {
        return null;
    }

    @Override
    public TimelineChange<Culture> getDeathChange(DMEReference<Culture> dme, LocalDate date, CauseOfEnd<? super Culture> cOd) {
        return null;
    }

    @Override
    public CauseOfEnd<? super Culture> defaultDeathCause() {
        return null;
    }

    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }
}
