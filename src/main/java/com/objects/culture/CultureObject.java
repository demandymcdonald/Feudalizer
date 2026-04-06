package com.objects.culture;

import com.Global.*;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public abstract class CultureObject<T extends CultureObject<T>> extends DateMutableEntity<T> {
    public CultureObject(LocalDate created, LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(created, ended, initialState);
    }

    public CultureObject(DMEReference<T> dme) {
        super(dme);
    }

    public CultureObject(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(id, created, ended, initialState);
    }
}
