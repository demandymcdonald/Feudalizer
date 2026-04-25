package com.objects.culture.tenet.dynamic;

import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.display.DisplayContainer;
import com.base.timeline.change.display.ITLDisplayable;
import com.base.timeline.change.multi.wrapper.TLMap;
import com.base.utilities.TLSyncedCache;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonObject;
import com.objects.CauseOfEnd;
import com.objects.culture.AbstractCulture;
import com.objects.culture.Culture;
import com.objects.culture.Influencers.InfluencerInstance;
import com.objects.culture.Influencers.InfluencerRelationship;
import com.objects.culture.object.CultureObject;
import com.objects.culture.object.CultureObjectContainer;
import com.objects.culture.object.ICultureObject;
import com.objects.culture.object.compass.IPoliticalCompass;
import com.objects.culture.object.compass.InterpolatedPoliticalCompass;
import com.objects.culture.tenet.factory.CultureCondition;
import com.objects.culture.tenet.instance.TenetInstance;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.AcceptanceContainer;
import com.objects.culture.tenet.TenetManager;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.object.COReference;
import com.objects.culture.tenet.TenetReference;
import com.objects.culture.tenet.Tenet;
import com.utilities.caching.CachingSupplier;
import org.apache.commons.lang3.tuple.Pair;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.*;

import static com.objects.CauseOfEnd.DynamicTenets.NO_MEMBERS;

public abstract class DynamicTenet<T extends DynamicTenet<T>> extends AbstractCulture<T> implements Tenet, CultureObject<T>, ITLDisplayable<T> {
    private final TenetGroup tenetGroup;
    private final CultureObjectContainer<T> container;
    private DMEReference<Culture> foundingCulture;
    private final DisplayContainer<T> displayContainer;
    private final Multimap<Type, ICultureObject> members = HashMultimap.create();
    private final CachingSupplier<Map<TenetReference,TenetInstance<T>>> activeSupplier = new CachingSupplier<>(CultureObject.super::getActiveTenets);
    public DynamicTenet(TenetGroup group, String name, LocalDate created, LocalDate ended, DMEReference<Culture> foundingCulture, List<ChangeSupplier<T, ?>> initialState) {
        super(created, ended, initialState);
        this.tenetGroup = group;
        displayContainer = new DisplayContainer<>(getReference(),buildID(group,name),name,"");
        container = new CultureObjectContainer<>(this.getReference());
        this.foundingCulture = foundingCulture;
    }
    public DynamicTenet(TenetGroup group, DMEReference<T> dme) {
        super(dme);
        this.tenetGroup = group;
        container = new CultureObjectContainer<>(this.getReference());
        displayContainer = new DisplayContainer<>(this.getReference(),null,null,null);
    }

    @Override
    public final DMEReference<T> getOwner() {
        return getReference();
    }

    public DynamicTenet(TenetGroup group, String name, UUID id, LocalDate created, @Nullable LocalDate ended, DMEReference<Culture> foundingCulture, List<ChangeSupplier<T, ?>> initialState) {
        super(id, created, ended, initialState);
        this.tenetGroup = group;
        this.foundingCulture = foundingCulture;
        container = new CultureObjectContainer<>(this.getReference());
        displayContainer = new DisplayContainer<>(this.getReference(),null,null,null);
    }
    @Override
    public final TenetReference getTenetReference() {
        return TenetReference.of(this);
    }

    @Override
    public final CultureObjectContainer<T> getContainer() {
        return container;
    }

    @Override
    public final TenetGroup getGroup() {
        return tenetGroup;
    }

    @Override
    public final DisplayContainer<T> getDisplayable() {
        return displayContainer;
    }

    @Override
    public AcceptanceContainer getAcceptanceContainer(TenetReference tenet, boolean includeInfluencers) {
        return Tenet.super.getAcceptanceContainer(tenet, includeInfluencers);
    }

    @Override
    public final Map<TenetReference, TenetInstance<T>> getActiveTenets() {
        return activeSupplier.get();
    }

    @Override
    public final DMEReference<Culture> getCulture() {
        return this.foundingCulture;
    }

    @Override
    public void additionalSave(JsonObject data) {
        data.add("founding_culture",foundingCulture.serialize());
    }
    public final boolean isAllowedTenet(Tenet tenet){
        return getGroup().isParentOf(tenet.getGroup());
    }
    @Override
    public void additionalLoad(JsonObject data) {
        foundingCulture = DMEReference.deserialize(data.get("founding_culture").getAsJsonObject());
    }
    private static String buildID(TenetGroup tenetGroup, String name) {
        return tenetGroup.getDisplayID() + "/" + name.toLowerCase(Locale.ROOT);
    }
    @Override
    public void internalSetCulture(DMEReference<Culture> culture) {
        foundingCulture = culture;
    }

    @Override
    public final TimelineChange<T> getDeathChange(DMEReference<T> dme, LocalDate date, CauseOfEnd<? super T> cOd) {
        return new Boundary.Disbanding<>(dme, date, cOd);
    }

    @Override
    public final TimelineChange<T> getBirthChange(DMEReference<T> dme, LocalDate date) {
        return new Boundary.Founding<>(dme, date);
    }
    @Override
    public final CauseOfEnd<T> defaultDeathCause() {
        return (CauseOfEnd<T>) NO_MEMBERS;
    }

    public final void addActiveTenet(Tenet tenet){
        TenetInstance<T> ti = getOpinions().get(tenet.getTenetReference());
        if(ti == null){

        } else if(!ti.isActive()){
            getOpinions().get(tenet.getTenetReference()).setActive();
            
        }
    }
    @Override
    public void doDateChange() {
        members.clear();
    }
    public void addMember(ICultureObject follower){
        members.put(follower.getType(), follower);
    }
    private final Cache<Class<? extends Tenet>,Set<? extends Tenet>> activeLookup = CacheBuilder.newBuilder().build();
    public final <R extends Tenet> Set<R> getActiveTenetByClass(Class<R> clazz){
        Set<? extends Tenet> set = activeLookup.getIfPresent(clazz);
        if(set == null){
            Set<TenetReference> map = getActiveTenets().keySet();
            Set<R> finalSet = new HashSet<>();
            map.forEach((tr) -> {
                if(tr.getTenetClass().isInstance(clazz)){finalSet.add((R) tr);}
            });
            activeLookup.put(clazz, finalSet);
            return finalSet;
        } else {
            return new HashSet<>((Set<R>) set);
        }
    }

    @Override
    public void onDateChange() {
        super.onDateChange();
        activeSupplier.clear();
        activeLookup.invalidateAll();
    }
    public final Set<ICultureObject> getByType(Type type){
        return new HashSet<>(members.get(type));
    }

//Literally just to clean up override menu


    @Override
    public final void addOpinion(TenetReference tenet, double d) {
        CultureObject.super.addOpinion(tenet, d);
    }

    @Override
    public final void amendCompass(Pair<IPoliticalCompass.Axis, Integer>... values) {
        CultureObject.super.amendCompass(values);
    }

    @Override
    public final double calcResistance(double opinion) {
        return CultureObject.super.calcResistance(opinion);
    }

    @Override
    public final Acceptance getAcceptance(Tenet tenet, boolean includeInfluencers) {
        return CultureObject.super.getAcceptance(tenet, includeInfluencers);
    }

    @Override
    public final double getAcceptanceValue(Tenet tenet, boolean includeInfluencers) {
        return CultureObject.super.getAcceptanceValue(tenet, includeInfluencers);
    }

    @Override
    public final double getAcceptanceValue(Tenet tenet, boolean includeInfluencers, COReference<?>... bls) {
        return CultureObject.super.getAcceptanceValue(tenet, includeInfluencers, bls);
    }

    @Override
    public final InterpolatedPoliticalCompass<T> getCompass() {
        return CultureObject.super.getCompass();
    }

    @Override
    public final TLSyncedCache<TenetReference, Double> getInfluencedCache() {
        return CultureObject.super.getInfluencedCache();
    }

    @Override
    public final TLMap<COReference<?>, InfluencerInstance> getInfluencers() {
        return CultureObject.super.getInfluencers();
    }

    @Override
    public final List<InfluencerOpinion> getListForTenet(Tenet tenet, boolean includeParentInfluencers, COReference<?>... bl) {
        return CultureObject.super.getListForTenet(tenet, includeParentInfluencers, bl);
    }

    @Override
    public final Map<TenetReference, TenetInstance<T>> getOpinionByGroup(TenetManager.Group.Pillar group, boolean includeDescendants) {
        return CultureObject.super.getOpinionByGroup(group, includeDescendants);
    }

    @Override
    public final Map<TenetReference, TenetInstance<T>> getOpinionByGroup(TenetGroup group, boolean includeDescendants) {
        return CultureObject.super.getOpinionByGroup(group, includeDescendants);
    }

    @Override
    public final TLMap<TenetReference,TenetInstance<T>> getOpinions() {
        return CultureObject.super.getOpinions();
    }

    @Override
    public final Optional<Pair<COReference<?>, InfluencerRelationship>> getParentObject() {
        return CultureObject.super.getParentObject();
    }
    @Override
    public final COReference<T> getTOReference() {
        return CultureObject.super.getTOReference();
    }

    @Override
    public final double influencerResistance(COReference<?> influencer) {
        return 0;
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
    public final void internalSetInfluencers(TLMap<COReference<?>, InfluencerInstance> influencers) {
        CultureObject.super.internalSetInfluencers(influencers);
    }

    @Override
    public final void internalSetOpinions(TLMap<TenetReference,TenetInstance<T>> opinions) {
        CultureObject.super.internalSetOpinions(opinions);
    }
    @Override
    public final void invalidateCache() {
        CultureObject.super.invalidateCache();
    }

    @Override
    public final void invalidateCache(TenetReference t) {
        CultureObject.super.invalidateCache(t);
    }

    @Override
    public final boolean isInfluencer(COReference<?> ref) {
        return CultureObject.super.isInfluencer(ref);
    }



    @Override
    public final void removeInfluencer(COReference<?> influencer) {
        CultureObject.super.removeInfluencer(influencer);
    }


    @Override
    public final void setOpinion(TenetReference tenet, double d) {
        CultureObject.super.setOpinion(tenet, d);
    }

    @Override
    public final void setParentObject(COReference<?> influencer, InfluencerRelationship relationship) {
        CultureObject.super.setParentObject(influencer, relationship);
    }

    @Override
    public final void updateProceduralInfluencers() {

    }
}
