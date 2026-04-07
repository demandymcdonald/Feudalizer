package com.objects.culture;

import com.Global;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeID;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.TimelineChange;
import com.google.common.collect.BiMap;
import com.google.gson.JsonObject;
import com.objects.CauseOfEnd;
import com.objects.culture.change.CultureMapChanges;
import com.objects.culture.change.CultureSingleChanges;
import com.objects.culture.tenet.Tenet;
import com.objects.culture.tenet.TenetInstance;
import org.apache.commons.lang3.tuple.Pair;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.*;

import static com.base.timeline.variable.TimelineEasingVariable.EasingType.EXPONENTIAL;

public class Culture extends CultureObject<Culture> {
    private List<DMEReference<Culture>> parentCultures = new ArrayList<>();
    private BiMap<DMEReference<Tenet<?>>, TenetInstance> tenets;






    List<Culture> linkedChildCultures;


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

    public BiMap<DMEReference<Tenet<?>>, TenetInstance> getTenets() {
        return tenets;
    }







    @Override
    protected void onLink() {
        for (DMEReference<Culture> parent : parentCultures){
            parent.get().forceLink();
            for (DMEReference<Tenet<?>> t : parent.get().getTenets().keySet()){
                t.get().forceLink();
                if (!tenets.containsKey(t)){
                    double starting = parent.get().getTenets().get(t).getOpinion();
                    addTenet(t,starting);
                }
                TenetInstance instance = tenets.get(t);
                TenetInstance parentInstance = parent.get().getTenets().get(t);
                instance.linkParent(parent,parentInstance);
            }
        }
    }

    public void addTenet(DMEReference<Tenet<?>> tenet, double starting){
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

    public void internalSetParent(List<DMEReference<Culture>> parents){
        this.parentCultures = parents;
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
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }


}
