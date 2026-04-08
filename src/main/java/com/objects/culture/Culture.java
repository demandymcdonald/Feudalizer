package com.objects.culture;

import com.Global;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeID;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.TimelineChange;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.JsonObject;
import com.objects.CauseOfEnd;
import com.objects.culture.change.CultureMapChanges;
import com.objects.culture.change.CultureSingleChanges;
import com.objects.culture.tenet.types.Tenet;
import com.objects.culture.tenet.TenetInstance;
import org.apache.commons.lang3.tuple.Pair;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.*;

import static com.base.timeline.variable.TimelineEasingVariable.EasingType.EXPONENTIAL;

public class Culture extends CultureObject<Culture> {
    //Todo, replace with Map<DMEReference<Culture>, Influence Container(Enum for relationship type, String for why, Map<TenetGroup,Int for base tenet influence)
    private final List<DMEReference<Culture>> parentCultures = new ArrayList<>();
    private final BiMap<Tenet<?,?>, TenetInstance> tenets = HashBiMap.create();

    private final List<Culture> linkedChildCultures = new ArrayList<>();


    public Culture(LocalDate created, LocalDate ended, List<ChangeSupplier<Culture, ?>> initialState) {
        super(created, ended, initialState);
    }

    public Culture(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<Culture, ?>> initialState) {
        super(id, created, ended, initialState);
    }

    public Culture(DMEReference<Culture> dme) {
        super(dme);
    }











    public List<DMEReference<Culture>> getParentCultures() {
        return parentCultures;
    }

    public BiMap<Tenet<?,?>, TenetInstance> getTenets() {
        return tenets;
    }
    public void amendParentInfluence(DMEReference<Culture> parent, int newInfluence, Tenet<?,?>... tenet){
        List<Tenet<?,?>> parents = new ArrayList<>();
        if(tenet == null){
            parents.addAll(getTenets().keySet());
        } else {
            parents.addAll(Arrays.asList(tenet));
        }
        for(Tenet<?,?> t : parents){
            TenetInstance instance = getTenets().get(t);
            int inf = instance.getInfluencerInfluence(parent) + newInfluence;
            instance.changeInfluencerInfluence(parent,inf);
        }
    }
    public void changeParentInfluence(DMEReference<Culture> parent, int newInfluence, Tenet<?,?>... tenet) {
        List<Tenet<?,?>> parents = new ArrayList<>();
        if(tenet == null){
            parents.addAll(getTenets().keySet());
        } else {
            parents.addAll(Arrays.asList(tenet));
        }
        for (Tenet<?,?> t : parents){
            getTenets().get(t).changeInfluencerInfluence(parent,newInfluence);
        }
    }




    @Override
    protected void onLink() {
        for (DMEReference<Culture> parent : parentCultures){
            Culture c = parent.get();
            c.forceLink();
            for (Tenet<?,?> t : parent.get().getTenets().keySet()){
                t.get().forceLink();
                if (!tenets.containsKey(t)){
                    double starting = parent.get().getTenets().get(t).getAcceptanceValue();
                    addTenet(t,starting);
                }
                TenetInstance instance = tenets.get(t);
                TenetInstance parentInstance = parent.get().getTenets().get(t);
                instance.linkInfluencer(parent,parentInstance);
            }
            c.linkChild(getReference());
        }
    }

    public void addTenet(Tenet<?,?> tenet, double starting){
        DMEReference<Culture> owner = this.getReference();
        LocalDate date = Global.getDate();
        getTimeline().addChange(new CultureMapChanges.TenetMapChange(owner, date,
                Pair.of(tenet, new TenetInstance(owner,EXPONENTIAL,new ChangeID(ChangeID.buildChangeClassID(CultureMapChanges.TenetMapChange.class.getName()),date),starting))));
    }
    public void addParent(DMEReference<Culture> parent){
        DMEReference<Culture> owner = this.getReference();
        LocalDate date = Global.getDate();
        getTimeline().addChange(new CultureSingleChanges.SetParentChange(owner, date, parent));
    }
    public void linkChild(DMEReference<Culture> child){
        linkedChildCultures.add(child.get());
    }
    public void internalSetParent(List<DMEReference<Culture>> parents){
        parents.clear();
        this.parentCultures.addAll(parents);
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
