package com.objects.government;

import com.Global.*;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public abstract class GoverningEntity<T extends GoverningEntity<T>> extends Government<T> {
    public GoverningEntity(LocalDate created, LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(created, ended, initialState);
    }

    public GoverningEntity(DMEReference<T> dme) {
        super(dme);
    }

    public GoverningEntity(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(id, created, ended, initialState);
    }
}
