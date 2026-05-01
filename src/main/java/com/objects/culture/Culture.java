package com.objects.culture;

import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.ChangeSupplier;
import com.base.datemutable.timeline.change.TimelineChange;
import com.google.gson.JsonObject;
import com.objects.CauseOfEnd;
import com.objects.culture.object.CultureObjectContainer;
import com.objects.culture.object.ICultureObject;
import com.objects.culture.object.reference.COReference;
import com.objects.culture.object.CultureObject;
import com.objects.culture.tenet.AcceptanceContainer;
import com.objects.shared.IDemographicDriven;
import com.objects.shared.PopulationContainer;

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

    @Override
    public void updateProceduralInfluencers() {

    }

    @Override
    public double influencerResistance(COReference<?> influencer) {
        return 0;
    }

    @Override
    public CultureObjectContainer<Culture> getContainer() {
        return null;
    }

    @Override
    public void internalSetCulture(DMEReference<Culture> culture) {

    }

    @Override
    public Type getType() {
        return Type.Culture;
    }

    @Override
    public DMEReference<Culture> getCulture() {
        return this.getReference();
    }

    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }
    public void linkMember(ICultureObject member) {

    }
    @Override
    public AcceptanceContainer getAcceptanceObject(ICultureObject other, boolean includeInfluencers, boolean factorOtherTolerance) {
        return null;
    }
}
