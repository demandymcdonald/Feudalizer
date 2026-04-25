package com.objects.culture.tenet.dynamic;

import com.Global.*;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.display.ITLDisplayable;
import com.base.timeline.change.multi.type.ChangeType;
import com.base.timeline.change.multi.wrapper.TLMap;
import com.base.timeline.change.multi.wrapper.TLSet;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.JsonObject;
import com.objects.culture.AbstractCulture;
import com.objects.culture.Culture;
import com.objects.culture.Influencers.InfluencerInstance;
import com.objects.culture.Influencers.InfluencerRelationship;
import com.objects.culture.object.CultureObject;
import com.objects.culture.object.CultureObjectContainer;
import com.objects.culture.object.compass.IPoliticalCompass;
import com.objects.culture.object.compass.InterpolatedPoliticalCompass;
import com.objects.culture.object.reference.COReference;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.AcceptanceContainer;
import com.objects.culture.tenet.Tenet;
import com.objects.culture.tenet.TenetReference;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.instance.TenetInstance;
import com.utilities.caching.CachingSupplier;
import org.apache.commons.lang3.tuple.Pair;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class AbstractDT<T extends AbstractDT<T>> extends AbstractCulture<T> implements Tenet, CultureObject<T>, ITLDisplayable<T> {
    private final CultureObjectContainer<T> container;
    private DMEReference<Culture> foundingCulture;
    private final CachingSupplier<Set<TenetInstance<T>>> activeSupplier = new CachingSupplier<>(this::getActiveTenets);
    private final Cache<Class<? extends Tenet>,Set<? extends Tenet>> activeLookup = CacheBuilder.newBuilder().build();

    public AbstractDT(LocalDate created, LocalDate ended, DMEReference<Culture> foundingCulture, List<ChangeSupplier<T, ?>> initialState) {
        super(created, ended, initialState);
        container = new CultureObjectContainer<>(this.getReference());
        this.foundingCulture = foundingCulture;
    }

    public AbstractDT(DMEReference<T> dme) {
        super(dme);
        container = new CultureObjectContainer<>(this.getReference());
    }

    public AbstractDT(UUID id, LocalDate created, @Nullable LocalDate ended, DMEReference<Culture> foundingCulture, List<ChangeSupplier<T, ?>> initialState) {
        super(id, created, ended, initialState);
        container = new CultureObjectContainer<>(this.getReference());
        this.foundingCulture = foundingCulture;
    }
    @Override
    public final TenetReference getTenetReference() {
        return TenetReference.of(this);
    }

    public final <R extends Tenet> Set<R> getActiveTenetByClass(Class<R> clazz){
        Set<? extends Tenet> set = activeLookup.getIfPresent(clazz);
        if(set == null){
            Set<TenetInstance<T>> map = getActiveTenets();
            Set<R> finalSet = new HashSet<>();
            map.forEach((tr) -> {
                if(tr.getTenet().getTenetClass().isInstance(clazz)){finalSet.add((R) tr);}
            });
            activeLookup.put(clazz, finalSet);
            return finalSet;
        } else {
            return new HashSet<>((Set<R>) set);
        }
    }
    public final boolean isAllowedTenet(Tenet tenet){
        return getGroup().isParentOf(tenet.getGroup());
    }
    @Override
    public final DMEReference<Culture> getCulture() {
        return this.foundingCulture;
    }
    @Override
    public void doDateChange() {
        CultureObject.super.doDateChange();
        activeSupplier.clear();
        activeLookup.invalidateAll();
    }

    @Override
    public void additionalSave(JsonObject data) {
        data.add("founding_culture",foundingCulture.serialize());
    }

    @Override
    public void additionalLoad(JsonObject data) {
        foundingCulture = DMEReference.deserialize(data.get("founding_culture").getAsJsonObject());
    }
    public final TenetInstance<T> getTenetInstance(TenetReference tenet){
        return getContainer().getTenetInstance(tenet);
    }





















    @Override
    public final AcceptanceContainer getAcceptanceTenet(TenetReference tenet, boolean includeInfluencers, Set<COReference<?>> blacklist) {
        return CultureObject.super.getAcceptanceTenet(tenet, includeInfluencers, blacklist);
    }

    @Override
    public final AcceptanceContainer getAcceptanceTenet(TenetReference tenet, boolean includeInfluencers) {
        return CultureObject.super.getAcceptanceTenet(tenet, includeInfluencers);
    }

    @Override
    public final void addInfluencer(COReference<?> influencer, InfluencerRelationship relationship, boolean isProcedural) {
        CultureObject.super.addInfluencer(influencer, relationship, isProcedural);
    }

    @Override
    public final void addOpinion(TenetReference tenet, double d) {
        CultureObject.super.addOpinion(tenet, d);
    }

    @Override
    public final void addOpinion(TenetReference tenet, boolean wipe, double d) {
        CultureObject.super.addOpinion(tenet, wipe, d);
    }

    @Override
    public final void amendCompass(Pair<IPoliticalCompass.Axis, Integer>... values) {
        CultureObject.super.amendCompass(values);
    }

    @Override
    public final Set<TenetInstance<T>> getActiveTenets() {
        return CultureObject.super.getActiveTenets();
    }

    @Override
    public final InterpolatedPoliticalCompass<T> getCompass() {
        return CultureObject.super.getCompass();
    }

    @Override
    public final CultureObjectContainer<T> getContainer() {
        return container;
    }

    @Override
    public final TLMap<COReference<?>, InfluencerInstance> getInfluencers() {
        return CultureObject.super.getInfluencers();
    }

    @Override
    public final Set<TenetInstance<T>> getOpinionByGroup(TenetGroup group, boolean includeDescendants) {
        return CultureObject.super.getOpinionByGroup(group, includeDescendants);
    }

    @Override
    public final TLSet<TenetInstance<T>> getOpinions() {
        return CultureObject.super.getOpinions();
    }

    @Override
    public final Optional<Pair<COReference<?>, InfluencerRelationship>> getParentObject() {
        return CultureObject.super.getParentObject();
    }

    @Override
    public final  Set<TenetInstance<T>> getTenetsByThreshold(Acceptance acceptance, boolean includeInfluencers) {
        return CultureObject.super.getTenetsByThreshold(acceptance, includeInfluencers);
    }

    @Override
    public final COReference<T> getTOReference() {
        return CultureObject.super.getTOReference();
    }


    @Override
    public final void internalParentObject(COReference<?> influencer, InfluencerRelationship relationship) {
        CultureObject.super.internalParentObject(influencer, relationship);
    }

    @Override
    public final void internalSetCompass(InterpolatedPoliticalCompass<?> compass) {
        CultureObject.super.internalSetCompass(compass);
    }

    @Override
    public void internalSetCulture(DMEReference<Culture> culture) {

    }

    @Override
    public final void internalSetInfluencers(TLMap<COReference<?>, InfluencerInstance> influencers) {
        CultureObject.super.internalSetInfluencers(influencers);
    }

    @Override
    public final void internalSetOpinions(TLSet<TenetInstance<T>> opinions) {
        CultureObject.super.internalSetOpinions(opinions);
    }

    @Override
    public final boolean isInfluencer(COReference<?> ref) {
        return CultureObject.super.isInfluencer(ref);
    }

    @Override
    public final boolean isMainstream(Tenet tenet, boolean includeInfluencers) {
        return CultureObject.super.isMainstream(tenet, includeInfluencers);
    }

    @Override
    public final boolean isMainstream(TenetReference tenet, boolean includeInfluencers) {
        return CultureObject.super.isMainstream(tenet, includeInfluencers);
    }

    @Override
    public final void modifyInfluence(COReference<?> influencer, boolean doWipe, Pair<TenetGroup, Integer>... changes) {
        CultureObject.super.modifyInfluence(influencer, doWipe, changes);
    }

    @Override
    public final void removeInfluencer(COReference<?> influencer) {
        CultureObject.super.removeInfluencer(influencer);
    }

    @Override
    public final void setInfluence(COReference<?> influencer, boolean doWipe, Pair<TenetGroup, Integer>... changes) {
        CultureObject.super.setInfluence(influencer, doWipe, changes);
    }

    @Override
    public final void setOpinion(TenetReference tenet, double d) {
        CultureObject.super.setOpinion(tenet, d);
    }

    @Override
    public final void setParentObject(COReference<?> influencer, InfluencerRelationship relationship) {
        CultureObject.super.setParentObject(influencer, relationship);
    }


}
