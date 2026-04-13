package com.objects.culture.tenet.types;

import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.TimelineChange;
import com.google.common.collect.Multimap;
import com.google.gson.JsonObject;
import com.objects.CauseOfEnd;
import com.objects.culture.AbstractCulture;
import com.objects.culture.object.compass.IPoliticalCompass;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.TenetCondition;
import com.objects.culture.tenet.reference.TenetReference;
import com.objects.culture.tenet.group.TenetGroup;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class DynamicTenet<T extends DynamicTenet<T>> extends AbstractCulture<T> implements Tenet {
    public DynamicTenet(LocalDate created, LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(created, ended, initialState);
    }

    public DynamicTenet(DMEReference<T> dme) {
        super(dme);
    }

    public DynamicTenet(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(id, created, ended, initialState);
    }

    @Override
    protected void onLink() {

    }

    @Override
    public void doDateChange() {

    }

    @Override
    public TimelineChange<T> getBirthChange(DMEReference<T> dme, LocalDate date) {
        return null;
    }

    @Override
    public TimelineChange<T> getDeathChange(DMEReference<T> dme, LocalDate date, CauseOfEnd<? super T> cOd) {
        return null;
    }

    @Override
    public CauseOfEnd<? super T> defaultDeathCause() {
        return null;
    }

    @Override
    public TenetGroup getGroup() {
        return null;
    }

    @Override
    public Multimap<Class<? extends TimelineChange<?>>, TenetCondition<?, ?, ?>> getConditions() {
        return null;
    }

    @Override
    public Map<TenetReference, Acceptance> getRelated() {
        return Map.of();
    }

    @Override
    public IPoliticalCompass getCompass() {
        return null;
    }

    @Override
    public String displayName() {
        return "";
    }

    @Override
    public String description() {
        return "";
    }

    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }
}
