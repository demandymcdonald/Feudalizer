package com.objects.culture.tenet.types.dynamic.tenets;

import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.TimelineChange;
import com.google.common.collect.Multimap;
import com.google.gson.JsonObject;
import com.objects.CauseOfEnd;
import com.objects.culture.Culture;
import com.objects.culture.tenet.factory.CultureCondition;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.types.dynamic.DynamicTenet;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Religion extends DynamicTenet<Religion> {
    private DMEReference<Culture> foundingCulture;
    public Religion(TenetGroup group, DMEReference<Religion> dme) {
        super(group, dme);
    }

    public Religion(TenetGroup group, String name, LocalDate created, LocalDate ended, Culture founding, List<ChangeSupplier<Religion, ?>> initialState) {
        super(group, name, created, ended, initialState);
        this.foundingCulture = founding.getReference();
    }

    public Religion(TenetGroup group, String name, UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<Religion, ?>> initialState) {
        super(group, name, id, created, ended, initialState);
    }

    @Override
    protected void onLink() {

    }

    @Override
    public void doDateChange() {

    }

    @Override
    public TimelineChange<Religion> getBirthChange(DMEReference<Religion> dme, LocalDate date) {
        return null;
    }

    @Override
    public TimelineChange<Religion> getDeathChange(DMEReference<Religion> dme, LocalDate date, CauseOfEnd<? super Religion> cOd) {
        return null;
    }

    @Override
    public CauseOfEnd<? super Religion> defaultDeathCause() {
        return null;
    }


    @Override
    public Multimap<CultureCondition.Key, CultureCondition<?, ?, ?>> getConditions() {
        return null;
    }

    @Override
    public void additionalSave(JsonObject data) {
        super.additionalSave(data);
        data.add("founding_culture",foundingCulture.serialize());
    }

    @Override
    public void additionalLoad(JsonObject data) {
        super.additionalLoad(data);
        foundingCulture = DMEReference.deserialize(data.get("founding_culture").getAsJsonObject());
    }

    public Culture getCulture() {
        return foundingCulture.get();
    }
}
