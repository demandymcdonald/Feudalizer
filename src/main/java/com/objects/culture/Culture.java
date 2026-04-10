package com.objects.culture;

import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.TimelineChange;
import com.google.gson.JsonObject;
import com.objects.CauseOfEnd;
import com.objects.culture.Influencers.InfluencerInstance;
import com.objects.culture.Influencers.InfluencerRelationship;
import com.objects.culture.change.CultureMapChanges;
import com.objects.culture.tenet.instance.CultureTenetInstance;
import com.objects.culture.tenet.instance.TOReference;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.object.CultureObject;
import com.objects.culture.tenet.types.Tenet;
import org.apache.commons.lang3.tuple.Pair;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.*;

public class Culture extends AbstractCulture<Culture> implements CultureObject<CultureTenetInstance,CultureMapChanges.TenetMapChange,Culture> {
    //Todo, replace with Map<DMEReference<Culture>, Influence Container(Enum for relationship type, String for why, Map<TenetGroup,Int for base tenet influence)
    private final List<Culture> linkedChildCultures = new ArrayList<>();
    private final Map<Tenet,CultureTenetInstance> tenetOpinions = new HashMap<>();
    private final Map<TOReference<?>, InfluencerInstance> influencers = new HashMap<>();
    private PoliticalCompass compass = new PoliticalCompass();
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
    public Map<Tenet, CultureTenetInstance> getOpinions() {
        return tenetOpinions;
    }

    @Override
    public Optional<Pair<TOReference<?>, InfluencerRelationship>> getParentObject() {
        return Optional.empty();
    }

    @Override
    public void setParentObject(TOReference<?> influencer, InfluencerRelationship relationship) {

    }

    @Override
    public Map<TOReference<?>, InfluencerInstance> getInfluencers() {
        return influencers;
    }

    @Override
    public void updateProceduralInfluencers() {

    }


    @Override
    public double influencerResistance(TOReference<?> influencer) {
        return 0;
    }

    @Override
    public DMEReference<Culture> getOwner() {
        return null;
    }

    @Override
    public void internalSetCompass(PoliticalCompass compass) {
        this.compass = compass;
    }

    @Override
    public PoliticalCompass getCompass() {
        return compass;
    }




    @Override
    protected void onLink() {

    }


    @Override
    public void doDateChange() {
        linkedChildCultures.clear();
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
