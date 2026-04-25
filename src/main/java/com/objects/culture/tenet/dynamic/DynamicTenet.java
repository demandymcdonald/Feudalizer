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
import com.objects.culture.tenet.instance.TenetInstance;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.AcceptanceContainer;
import com.objects.culture.tenet.TenetManager;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.object.reference.COReference;
import com.objects.culture.tenet.TenetReference;
import com.objects.culture.tenet.Tenet;
import com.utilities.caching.CachingSupplier;
import org.apache.commons.lang3.tuple.Pair;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.*;

import static com.objects.CauseOfEnd.DynamicTenets.NO_MEMBERS;

public abstract class DynamicTenet<T extends DynamicTenet<T>> extends AbstractDT<T> {
    private final TenetGroup tenetGroup;
    private final DisplayContainer<T> displayContainer;
    private final Multimap<Type, ICultureObject> members = HashMultimap.create();

    public DynamicTenet(TenetGroup group, String name, LocalDate created, LocalDate ended, DMEReference<Culture> foundingCulture, List<ChangeSupplier<T, ?>> initialState) {
        super(created, ended, foundingCulture,initialState);
        this.tenetGroup = group;
        displayContainer = new DisplayContainer<>(getReference(),buildID(group,name),name,"");


    }
    public DynamicTenet(TenetGroup group, DMEReference<T> dme) {
        super(dme);
        this.tenetGroup = group;
        displayContainer = new DisplayContainer<>(this.getReference(),null,null,null);
    }

    @Override
    public final DMEReference<T> getOwner() {
        return getReference();
    }

    public DynamicTenet(TenetGroup group, String name, UUID id, LocalDate created, @Nullable LocalDate ended, DMEReference<Culture> foundingCulture, List<ChangeSupplier<T, ?>> initialState) {
        super(id, created, ended, foundingCulture,initialState);
        this.tenetGroup = group;
        displayContainer = new DisplayContainer<>(this.getReference(),null,null,null);
    }


    @Override
    public final TenetGroup getGroup() {
        return tenetGroup;
    }

    @Override
    public final DisplayContainer<T> getDisplayable() {
        return displayContainer;
    }

    private static String buildID(TenetGroup tenetGroup, String name) {
        return tenetGroup.getDisplayID() + "/" + name.toLowerCase(Locale.ROOT);
    }

    @Override
    public final TimelineChange<T> getDeathChange(DMEReference<T> dme, LocalDate date, CauseOfEnd<? super T> cOd) {
        return new DTChange.Disbanding<>(dme, date, cOd);
    }

    @Override
    public final TimelineChange<T> getBirthChange(DMEReference<T> dme, LocalDate date) {
        return new DTChange.Founding<>(dme, date);
    }
    @Override
    public final CauseOfEnd<T> defaultDeathCause() {
        return (CauseOfEnd<T>) NO_MEMBERS;
    }
    public final void addActiveTenet(Tenet tenet){
        TenetInstance<T> ti = getTenetInstance(tenet.getTenetReference());
        if(ti == null){
            getOpinions().add(new TenetInstance<>(tenet.getTenetReference(), this.getReference(), Acceptance.getMid(Acceptance.CORE),true));
        } else if(!ti.isActive()){
            ti.setActive();
        }
    }

    @Override
    public void doDateChange() {
        super.doDateChange();
        members.clear();
    }
    public void addMember(ICultureObject follower){
        members.put(follower.getType(), follower);
    }


    public final Set<ICultureObject> getByType(Type type){
        return new HashSet<>(members.get(type));
    }

//Literally just to clean up override menu


    }
}
