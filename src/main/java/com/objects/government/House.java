package com.objects.government;

import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.TimelineChange;
import com.google.gson.JsonObject;
import com.objects.CauseOfEnd;
import com.objects.character.LivingCreature;
import com.objects.title.Title;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.*;

public class House extends GoverningEntity<House> {


    public House(LocalDate created, LocalDate ended, List<ChangeSupplier<House, ?>> initialState) {
        super(created, ended, initialState);
    }

    public House(DMEReference<House> dme) {
        super(dme);
    }

    public House(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<House, ?>> initialState) {
        super(id, created, ended, initialState);
    }

    @Override
    protected void onLink() {

    }

    @Override
    public void doDateChange() {

    }

    @Override
    public TimelineChange<House> getBirthChange(DMEReference<House> dme, LocalDate date) {
        return null;
    }

    @Override
    public TimelineChange<House> getDeathChange(DMEReference<House> dme, LocalDate date, CauseOfEnd<? super House> cOd) {
        return null;
    }


    @Override
    public CauseOfEnd<House> defaultDeathCause() {
        return null;
    }

    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }
}
