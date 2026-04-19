package com.objects.character;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.multi.TLMap;
import com.google.gson.JsonObject;
import com.objects.character.physical.PhysicalAppearance;
import com.objects.character.physical.augment.AugmentChange;
import com.objects.character.physical.augment.AugmentInstance;
import com.objects.character.physical.augment.AugmentSlot;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.List;

public abstract class LivingCreature<T extends LivingCreature<T>> extends DateMutableEntity<T> {
    PhysicalAppearance appearance;
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
    public final void setAugments(TLMap<AugmentSlot, AugmentInstance> augmentations){
        appearance.internalAugmentSet(augmentations);
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
