package com.objects.culture.tenet;

import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.objects.culture.Culture;
import com.objects.culture.CultureObject;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public abstract class CultureTenet<T extends CultureTenet<T,C>,C extends Culture> extends CultureObject<T> {
    public CultureTenet(LocalDate created, LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(created, ended, initialState);
    }

    public CultureTenet(DMEReference<T> dme) {
        super(dme);
    }

    public CultureTenet(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(id, created, ended, initialState);
    }
}
