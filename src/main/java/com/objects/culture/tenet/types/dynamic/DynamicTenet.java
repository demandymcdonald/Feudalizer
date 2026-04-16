package com.objects.culture.tenet.types.dynamic;

import com.Global;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.multi.TimelineMap;
import com.base.timeline.change.multi.TimelineMultiChange;
import com.base.utilities.TLSyncedCache;
import com.google.gson.JsonObject;
import com.objects.culture.AbstractCulture;
import com.objects.culture.Influencers.InfluencerInstance;
import com.objects.culture.Influencers.InfluencerRelationship;
import com.objects.culture.object.CultureObject;
import com.objects.culture.object.CultureObjectContainer;
import com.objects.culture.object.compass.IPoliticalCompass;
import com.objects.culture.object.compass.InterpolatedPoliticalCompass;
import com.objects.culture.object.instance.TenetInstance;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.AcceptanceContainer;
import com.objects.culture.tenet.TenetManager;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.instance.TOReference;
import com.objects.culture.tenet.reference.TenetReference;
import com.objects.culture.tenet.types.mutable.Tenet;
import org.apache.commons.lang3.tuple.Pair;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.*;

public abstract class DynamicTenet<T extends DynamicTenet<T>> extends AbstractCulture<T> implements Tenet, CultureObject<T> {
    private final TenetGroup tenetGroup;
    private final CultureObjectContainer<T> container;
    private final TimelineMultiChange.Listener<TenetReference, TenetInstance<T>> listener = new TimelineMultiChange.Listener<TenetReference, TenetInstance<T>>() {
        @Override
        public void onMapPut(TenetReference key, TenetInstance<T> value) {
            DynamicTenet.this.onOpinionAdd(key,value);
        }

        @Override
        public void onMapRemove(TenetReference key, TenetInstance<T> value, TimelineMultiChange.WipeType type) {
            DynamicTenet.this.onOpinionRemove(key,value);
        }


        @Override
        public void onMapReplace(TenetReference key, TenetInstance<T> oldValue, TenetInstance<T> newValue) {
            DynamicTenet.this.onOpinionReplace(key,oldValue,newValue);
        }

        @Override
        public void onMapClear() {
            DynamicTenet.this.onOpinionClear();
        }

        @Override
        public void onMapGet(TenetReference key, TenetInstance<T> value) {
            DynamicTenet.this.onOpinionGet(key,value);
        }
    };
    private TimelineMap<TenetReference,TenetGroup,T> children;
    String displayID;
    String displayName;
    String description;
    public DynamicTenet(TenetGroup group, String name, LocalDate created, LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(created, ended, initialState);
        this.tenetGroup = group;
        displayID = buildID(group,name);
        container = new CultureObjectContainer<>(this.getReference());
        container.getOpinions().addListener(listener);
    }

    public DynamicTenet(TenetGroup group, DMEReference<T> dme) {
        super(dme);
        this.tenetGroup = group;
        container = new CultureObjectContainer<>(this.getReference());
    }

    public DynamicTenet(TenetGroup group, String name, UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(id, created, ended, initialState);
        this.tenetGroup = group;
        displayID = buildID(group, name);
        container = new CultureObjectContainer<>(this.getReference());
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
    public TenetGroup getGroup() {
        return tenetGroup;
    }

    @Override
    public AcceptanceContainer getAcceptanceContainer(TenetReference tenet, boolean includeInfluencers) {
        return Tenet.super.getAcceptanceContainer(tenet, includeInfluencers);
    }

    @Override
    public final String displayName() {
        return displayName;
    }
    public final void internalDisplayName(String name){
        this.displayName = name;
    }
    public final void setDisplayName(String name){
        getTimeline().addChange(new DynamicBaseChanges.setDisplayName<>(getReference(), Global.getDate(), name));
    }
    @Override
    public final String description() {
        return description;
    }
    public final void internalDescription(String description){
        this.description = description;
    }
    public final void setDescription(String name){
        getTimeline().addChange(new DynamicBaseChanges.setDescription<>(getReference(), Global.getDate(), name));
    }

    public void onOpinionAdd(TenetReference key, TenetInstance<T> value){};
    public void onOpinionRemove(TenetReference key, TenetInstance<T> value){};
    public void onOpinionReplace(TenetReference key, TenetInstance<T> oldValue, TenetInstance<T> newValue){};
    public void onOpinionClear(){};
    public void onOpinionGet(TenetReference key, TenetInstance<T> value){};

    public void internalSetChildMap(TimelineMap<TenetReference,TenetGroup,T> map){
        this.children = map;
    }
    public TimelineMap<TenetReference,TenetGroup,T> getChildren(){
        return children;
    }
    @Override
    public void additionalSave(JsonObject data) {
        data.addProperty("displayID", displayID);
    }
    public final boolean isAllowedTenet(Tenet tenet){
        return getGroup().isParentOf(tenet.getGroup());
    }
    @Override
    public void additionalLoad(JsonObject data) {
        displayID = data.get("displayID").getAsString();
    }
    private static String buildID(TenetGroup tenetGroup, String name) {
        return tenetGroup.getDisplayID() + "/" + name.toLowerCase(Locale.ROOT);
    }




    //Literally just to clean up override menu


    @Override
    public final void addInfluencer(TOReference<?> influencer, InfluencerRelationship relationship) {
        CultureObject.super.addInfluencer(influencer, relationship);
    }

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
    public final double getAcceptanceValue(Tenet tenet, boolean includeInfluencers, TOReference<?>... bls) {
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
    public final TimelineMap<TOReference<?>, InfluencerInstance, T> getInfluencers() {
        return CultureObject.super.getInfluencers();
    }

    @Override
    public final List<InfluencerOpinion> getListForTenet(Tenet tenet, boolean includeParentInfluencers, TOReference<?>... bl) {
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
    public final TimelineMap<TenetReference, TenetInstance<T>, T> getOpinions() {
        return CultureObject.super.getOpinions();
    }

    @Override
    public final Optional<Pair<TOReference<?>, InfluencerRelationship>> getParentObject() {
        return CultureObject.super.getParentObject();
    }

    @Override
    public final TOReference<T> getTOReference() {
        return CultureObject.super.getTOReference();
    }

    @Override
    public final double influencerResistance(TOReference<?> influencer) {
        return 0;
    }

    @Override
    public final void internalParentObject(TOReference<?> influencer, InfluencerRelationship relationship) {
        CultureObject.super.internalParentObject(influencer, relationship);
    }

    @Override
    public final void internalSetCompass(InterpolatedPoliticalCompass<?> compass) {
        CultureObject.super.internalSetCompass(compass);
    }

    @Override
    public final void internalSetInfluencers(TimelineMap<TOReference<?>, InfluencerInstance, T> influencers) {
        CultureObject.super.internalSetInfluencers(influencers);
    }

    @Override
    public final void internalSetOpinions(TimelineMap<TenetReference, TenetInstance<T>, T> opinions) {
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
    public final boolean isInfluencer(TOReference<?> ref) {
        return CultureObject.super.isInfluencer(ref);
    }


    @Override
    public final void modifyInfluence(TOReference<?> influencer, Pair<TenetGroup, Integer>... changes) {
        CultureObject.super.modifyInfluence(influencer, changes);
    }

    @Override
    public final void removeInfluencer(TOReference<?> influencer) {
        CultureObject.super.removeInfluencer(influencer);
    }

    @Override
    public final void setInfluence(TOReference<?> influencer, Pair<TenetGroup, Integer>... changes) {
        CultureObject.super.setInfluence(influencer, changes);
    }

    @Override
    public final void setOpinion(TenetReference tenet, double d) {
        CultureObject.super.setOpinion(tenet, d);
    }

    @Override
    public final void setParentObject(TOReference<?> influencer, InfluencerRelationship relationship) {
        CultureObject.super.setParentObject(influencer, relationship);
    }

    @Override
    public final void updateProceduralInfluencers() {

    }
}
