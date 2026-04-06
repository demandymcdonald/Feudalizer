package com.objects.government;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.objects.character.LivingCreature;
import com.objects.title.Title;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class Government<T extends Government<T>> extends DateMutableEntity<T> {
    Map<DMEReference<? extends Title<?>>, DMEReference<? extends LivingCreature<?>>> linked_members = new HashMap<>();
    public Government(LocalDate created, LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(created, ended, initialState);
    }

    public Government(DMEReference<T> dme) {
        super(dme);
    }

    public Government(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(id, created, ended, initialState);
    }


    public void linkMember(DMEReference<? extends Title<?>> title, DMEReference<? extends LivingCreature<?>> creature){}

    @Override
    public void onDateChange() {
        linked_members.clear();
        super.onDateChange();
    }

    @Override
    public void relink() {
        super.relink();
    }

    @Override
    protected void onLink() {

    }
}
