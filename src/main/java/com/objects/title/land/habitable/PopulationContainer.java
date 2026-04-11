package com.objects.title.land.habitable;

import com.Global.*;
import com.base.reference.DMEReference;
import com.base.timeline.variable.EasingVariable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.objects.culture.tenet.caste.CasteObject;
import com.utilities.number.BoundedDouble;
import com.utilities.number.BoundedFloat;
import com.utilities.number.BoundedInteger;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

import static com.objects.culture.tenet.caste.Castes.ALL_CASTES;

public class PopulationContainer<T extends HabitableLand<T>> implements EasingVariable<PopulationContainer<T>,PopulationChange<T>,T> {
    private DMEReference<? extends T> owner;
    ImmutableMap<CasteObject, BoundedInteger> populationMap = buildDefaultMap();
    ImmutableMap<CasteObject, BoundedInteger> interpolatedPopulationMap = buildDefaultMap();
    long totalPopulation;
    long interpolatedPopulation;



    @Override
    public String getChangeClassName() {
        return PopulationChange.class.getName();
    }

    @Override
    public DMEReference<? extends T> getOwner() {
        return owner;
    }

    @Override
    public ImmutableList<VariableContainer> getEasingFunctions() {
        List<VariableContainer> easingFunctions = new ArrayList<>();
        easingFunctions.add(new VariableContainer(EasingType.LERP,() -> {return (double) totalPopulation;},(d) -> {totalPopulation = Math.round(d);}));
        for (Map.Entry<CasteObject, BoundedInteger> c : populationMap.entrySet()){
            easingFunctions.add(new VariableContainer(EasingType.LERP,() -> {return (double) c.getValue().get();},(d) -> {
                Objects.requireNonNull(interpolatedPopulationMap.get(c.getKey())).set((int) Math.round(d));}));
        }
        return new ImmutableList.Builder<VariableContainer>().addAll(easingFunctions).build();
    }

    @Override
    public Predicate<PopulationChange<T>> getMatching() {
        return null;
    }

    @Override
    public void mainSave(JsonObject object) {

    }

    @Override
    public void mainLoad(JsonObject object) {

    }

    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }
    private ImmutableMap<CasteObject,BoundedInteger> buildDefaultMap(){
        ImmutableMap.Builder<CasteObject,BoundedInteger> easingFunctions = ImmutableMap.builder();
        for (CasteObject c : ALL_CASTES){
            easingFunctions.put(c,new BoundedInteger(0,100));
        }
        return easingFunctions.build();
    }

    private ImmutableMap<CasteObject, BoundedInteger> buildDefaultPopulation(T land) {



    }
}
