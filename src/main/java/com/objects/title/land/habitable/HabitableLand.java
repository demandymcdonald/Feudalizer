package com.objects.title.land.habitable;

import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.display.geography.GeometryType;
import com.objects.culture.object.PassiveCultureObject;
import com.objects.shared.PopulationContainer;
import com.objects.title.land.AbstractLandDivision;
import com.objects.title.land.resources.Resource;

import java.time.LocalDate;
import java.util.*;

public abstract class HabitableLand<R extends HabitableLand<R>> extends AbstractLandDivision<R> implements PassiveCultureObject {
    final Set<Resource> resources = new HashSet<>();
    PopulationContainer<R> populationContainer;

    public HabitableLand(LocalDate created, LocalDate ended, GeometryType type, String geoID, List<ChangeSupplier<R, ?>> initialState) {
        super(created, ended, type, geoID, initialState);
    }

    public HabitableLand(DMEReference<R> dme) {
        super(dme);
    }


    public PopulationContainer<R> getPopulationContainer() {
        return populationContainer;
    }


}
