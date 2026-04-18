package com.objects.character;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.google.gson.JsonObject;
import com.objects.character.physical.PhysicalAppearance;
import com.objects.character.sentient.HumanCharacter;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public abstract class LivingCreature<T extends LivingCreature<T>> extends DateMutableEntity<T> {
    PhysicalAppearance appearance;
    public LivingCreature(LocalDate created, @Nullable LocalDate ended, PhysicalAppearance appearance, List<ChangeSupplier<T,?>> initialState) {
        super(created, ended, initialState);
    }

    public LivingCreature(DMEReference<T> dme) {
        super(dme);
    }

    public LivingCreature(UUID id, LocalDate created, @Nullable LocalDate ended, PhysicalAppearance appearance, List<ChangeSupplier<T, ?>> initialState) {
        super(id, created, ended, initialState);
    }
    public abstract Sex getSex();



    @Override
    public void additionalLoad(JsonObject data) {
        appearance = PhysicalAppearance.fromJson()
    }

    @Override
    public void additionalSave(JsonObject data) {

    }
}
