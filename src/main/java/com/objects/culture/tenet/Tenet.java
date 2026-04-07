package com.objects.culture.tenet;

import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.objects.culture.Culture;
import com.objects.culture.CultureObject;
import com.utilities.Displayable;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

public abstract class Tenet<T extends Tenet<T>> extends CultureObject<T> {
    public Tenet(LocalDate created, LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(created, ended, initialState);
    }

    public Tenet(DMEReference<T> dme) {
        super(dme);
    }

    public Tenet(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(id, created, ended, initialState);
    }


}
