package com.objects.character;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.changes.TimelineChange;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public abstract class LivingCreature<T extends LivingCreature<T>> extends DateMutableEntity<T> {
    public LivingCreature(LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<T,?>> initialState) {
        super(created, ended, initialState);
    }

    public LivingCreature(DMEReference<T> dme) {
        super(dme);
    }

    public LivingCreature(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(id, created, ended, initialState);
    }
}
