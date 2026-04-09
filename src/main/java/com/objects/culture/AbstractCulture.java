package com.objects.culture;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public abstract class AbstractCulture<T extends AbstractCulture<T>> extends DateMutableEntity<T> {
    public AbstractCulture(LocalDate created, LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(created, ended, initialState);
    }

    public AbstractCulture(DMEReference<T> dme) {
        super(dme);
    }

    public AbstractCulture(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(id, created, ended, initialState);
    }
}
