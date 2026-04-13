package com.objects.culture;

import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.TimelineChange;
import com.google.gson.JsonObject;
import com.objects.CauseOfEnd;
import com.objects.culture.object.CultureObjectContainer;
import com.objects.culture.tenet.instance.TOReference;
import com.objects.culture.object.CultureObject;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.*;

public class Culture extends AbstractCulture<Culture> implements CultureObject<Culture> {
    CultureObjectContainer<Culture> container;
    public Culture(LocalDate created, LocalDate ended, List<ChangeSupplier<Culture, ?>> initialState) {
        super(created, ended, initialState);
        container = new CultureObjectContainer<>(getReference());
    }

    public Culture(DMEReference<Culture> dme) {
        super(dme);
        container = new CultureObjectContainer<>(dme);
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

    public Culture(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<Culture, ?>> initialState) {
        super(id, created, ended, initialState);
    }

    @Override
    public void updateProceduralInfluencers() {

    }

    @Override
    public double influencerResistance(TOReference<?> influencer) {
        return 0;
    }

    @Override
    public CultureObjectContainer<Culture> getContainer() {
        return container;
    }

    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }
}
