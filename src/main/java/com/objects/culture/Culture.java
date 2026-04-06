package com.objects.culture;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeID;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.variable.TimelineEasingVariable;
import com.google.common.collect.BiMap;
import com.google.gson.JsonObject;
import com.objects.CauseOfEnd;
import com.objects.culture.tenet.CultureMapChanges;
import com.objects.culture.tenet.Tenet;
import com.utilities.number.BoundedInteger;
import org.apache.commons.lang3.tuple.Pair;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Culture extends CultureObject<Culture> {
    List<Culture> parentCultures;
    private BiMap<Tenet<?>,TenetInstance> tenets;






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













    public BiMap<Tenet<?>, TenetInstance> getTenets() {
        return tenets;
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
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }


    public static class TenetInstance extends TimelineEasingVariable<CultureMapChanges.TenetMapChange,Culture> {
        private final BoundedInteger parentInfluence = new BoundedInteger(-100,100);
        private double currentValue;
        public TenetInstance() {
        }

        public TenetInstance(DMEReference<Culture> owner, EasingType easeType, ChangeID thisChange, ChangeID nextChange,
                             double currentValue, Optional<TenetInstance> parent, int parentInfluence) {
            super(owner, thisChange, easeType, nextChange);
            this.currentValue = currentValue;
            parent.ifPresent(p -> this.parentInfluence.set(parentInfluence));
        }

        @Override
        protected double getCurrentValue() {
            return currentValue;
        }

        @Override
        protected double getValueFromNextChange(CultureMapChanges.TenetMapChange nextChange) {
            return nextChange;
        }
        public void setValue(double value){
            this.currentValue = value;
            onChange();
        }
        public void setParentInfluence(int parentInfluence){
            this.parentInfluence.set(parentInfluence);
        }
        private void onChange(){
            DMEReference<Culture> owner = getOwner();
            CultureMapChanges.TenetMapChange c = getC(owner, getThisChange());
            c.addChange(Pair.of(owner.get().getTenets().inverse().get(this),this));
        }


        @Override
        public void additionalSave(JsonObject data) {

        }

        @Override
        public void additionalLoad(JsonObject data) {

        }




    }
}
