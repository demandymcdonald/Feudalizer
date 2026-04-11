package com.objects.title.land.habitable;

import com.objects.culture.object.CultureObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.objects.title.Title;
import com.objects.title.land.AbstractLandDivision;
import com.objects.title.land.resources.Resource;
import com.objects.title.land.resources.ResourceType;

import java.util.*;

public abstract class HabitableLand<R extends HabitableLand<R>> extends AbstractLandDivision<R> implements CultureObject<> {
    final Set<Resource> resources = new HashSet<>();
    PopulationContainer<R> populationContainer;



    public PopulationContainer<R> getPopulationContainer() {
        return populationContainer;
    }


}
