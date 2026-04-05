package com.objects.title.house.role;

import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.objects.title.Title;
import com.objects.title.house.House;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public abstract class HouseRole<T extends HouseRole<T>> extends Title<T> {
    DMEReference<House> house;

    public HouseRole(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(id, created, ended, initialState);
    }

    public HouseRole(LocalDate created, LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(created, ended, initialState);
    }

    public HouseRole(DMEReference<T> dme) {
        super(dme);
    }
}
