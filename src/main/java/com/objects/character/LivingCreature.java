package com.objects.character;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.multi.wrapper.TLMap;
import com.google.gson.JsonObject;
import com.objects.CauseOfEnd;
import com.objects.character.change.LivingCOE;
import com.objects.character.change.LivingCreatureChanges;
import com.objects.character.opinion.Opinion;
import com.objects.character.opinion.OpinionContainer;
import com.objects.character.physical.PhysicalAppearance;
import com.objects.character.physical.augment.AugmentChange;
import com.objects.character.physical.augment.AugmentInstance;
import com.objects.character.physical.augment.AugmentSlot;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public abstract class LivingCreature<T extends LivingCreature<T>> extends DateMutableEntity<T> {
    private PhysicalAppearance appearance;
    private final OpinionContainer opinions = new OpinionContainer();
    public LivingCreature(LocalDate created, @Nullable LocalDate ended, PhysicalAppearance appearance, List<ChangeSupplier<T,?>> initialState) {
        super(created, ended, initialState);
        this.appearance = appearance;
        getTimeline().internalAddChange(new AugmentChange<>(this.getReference(),created));
    }
    public LivingCreature(DMEReference<T> dme) {
        super(dme);
    }
    public abstract Sex getSex();
    public final TLMap<AugmentSlot, AugmentInstance> internalGetAugments(){
        return appearance.internalAugmentsGet();
    }
    public final PhysicalAppearance getAppearance(){
        return appearance;
    }
    public final void internalSetAugments(TLMap<AugmentSlot, AugmentInstance> augmentations){
        appearance.internalAugmentSet(augmentations);
    }
    public final OpinionContainer getOpinionContainer(){
        return opinions;
    }
    public final void addOpinion(Opinion opinion){
        opinions.addOpinion(opinion,this.getReference());
    }
    public final void removeOpinion(Opinion opinion){
        opinions.removeOpinion(opinion);
    }
    public final void removeOpinion(UUID id){
        opinions.removeByID(id);
    }
    @Override
    protected void onLink() {

    }
    @Override
    public final TimelineChange<T> getBirthChange(DMEReference<T> dme, LocalDate date) {
        return new LivingCreatureChanges.Birth<>(dme,date);
    }
    public final TimelineChange<T> getBirthChange(DMEReference<T> dme, LocalDate date,
                                                  DMEReference<? extends LivingCreature<?>> parentA,
                                                  DMEReference<? extends LivingCreature<?>> parentB) {
        return new LivingCreatureChanges.Birth<>(dme,date,parentA,parentB);
    }
    @Override
    public final TimelineChange<T> getDeathChange(DMEReference<T> dme, LocalDate date, CauseOfEnd<? super T> cOd) {
        return new LivingCreatureChanges.Death<>(dme,date,cOd);
    }

    @Override
    public final CauseOfEnd<? super T> defaultDeathCause() {
        return LivingCOE.CHARACTER_OLD_AGE;
    }


    @Override
    public void doDateChange() {
        opinions.onLoad();
    }

    @Override
    public void additionalLoad(JsonObject data) {
        appearance = PhysicalAppearance.fromJson(data.get("appearance").getAsJsonObject());
    }

    @Override
    public void additionalSave(JsonObject data) {
        JsonObject o = new JsonObject();
        appearance.toJson(o);
        data.add("appearance",o);
    }
}
