package com.objects.title.land.habitable;

import com.Global.*;
import com.base.reference.DMEReference;
import com.base.timeline.change.multi.TimelineMap;
import com.base.timeline.variable.EasingVariable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.objects.culture.tenet.caste.CasteObject;
import com.utilities.id.Identifiable;
import com.utilities.id.StringIdentifiable;
import com.utilities.number.BoundedDouble;
import com.utilities.number.BoundedFloat;
import com.utilities.number.BoundedInteger;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

import static com.objects.culture.tenet.caste.Castes.ALL_CASTES;

public class PopulationContainer<T extends HabitableLand<T>> implements EasingVariable<PopulationContainer<T>,PopulationChange<T>,T> {
    private DMEReference<? extends T> owner;
    private PopulationChange<T> currentChange;
    private TimelineMap<CasteObject, BoundedInteger,T>  populationMap;
    private Map<CasteObject, BoundedDouble> interpolatedPopulationMap = new HashMap<>();
    private Map<Identifiable<?>,VariableContainer> easingFunctions = buildEasing();
    private static final StringIdentifiable popVar = new StringIdentifiable("totalPopulation") {
        @Override
        public String getID() {
            return "population";
        }

    };
    long totalPopulation;
    long interpolatedPopulation;


    public PopulationContainer(DMEReference<? extends T> owner) {
        this.owner = owner;
        PopulationChange<T> change = new PopulationChange<>(owner, owner.get().getCreated(),initializeProperMap());
        owner.get().getTimeline().internalAddChange(change);
    }

    public PopulationContainer() {

    }

    public void setPopulation(long population){
        totalPopulation = population;
        currentChange.setPopulation(population);
    }


    @Override
    public String getChangeClassName() {
        return PopulationChange.class.getName();
    }

    @Override
    public DMEReference<? extends T> getOwner() {
        return owner;
    }

    @Override
    public Map<Identifiable<?>, VariableContainer> getEasingFunctions() {
        return easingFunctions;
    }
    private Map<Identifiable<?>, VariableContainer> buildEasing(){
        Map<Identifiable<?>, VariableContainer> easingFunctions = new HashMap<>();
        easingFunctions.put(popVar, new VariableContainer(EasingType.LERP, () -> (double) totalPopulation, d -> interpolatedPopulation = Math.round(d)));
        for (Map.Entry<CasteObject, BoundedInteger> c : populationMap.entrySet()){
            easingFunctions.put(c.getKey(), new VariableContainer(EasingType.LERP, () -> (double) c.getValue().get(), d -> interpolatedPopulationMap.get(c.getKey()).set(d)));
        }
        return easingFunctions;
    }
    //    @Override
//    public ImmutableList<VariableContainer> getEasingFunctions() {
//        List<VariableContainer> easingFunctions = new ArrayList<>();
//        easingFunctions.add(new VariableContainer(EasingType.LERP,() -> {return (double) totalPopulation;},(d) -> {totalPopulation = Math.round(d);}));
//        for (Map.Entry<CasteObject, BoundedInteger> c : populationMap.entrySet()){
//            easingFunctions.add(new VariableContainer(EasingType.LERP,() -> {return (double) c.getValue().get();},(d) -> {
//                Objects.requireNonNull(interpolatedPopulationMap.get(c.getKey())).set(d);}));
//        }
//        return new ImmutableList.Builder<VariableContainer>().addAll(easingFunctions).build();
//    }
    @Override
    public Predicate<PopulationContainer<T>> getMatching() {
        return null;
    }
    public void internalSetPopulation(long newPopulation){
        totalPopulation = newPopulation;
    }
    public void internalSetMap(TimelineMap<CasteObject, BoundedInteger,T>  newMap){
        populationMap = newMap;
    }
    public TimelineMap<CasteObject, BoundedInteger,T> internalGetPopulationMap(){
        return populationMap;
    }
    public void internalSetChange(PopulationChange<T> newChange){
        currentChange = newChange;
    }
    @Override
    public void mainSave(JsonObject object) {
        object.add("reference",owner.serialize());
    }
    @Override
    public void mainLoad(JsonObject object) {
        this.owner = DMEReference.deserialize(object.get("reference").getAsJsonObject());
    }
    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }


    private static Map<CasteObject, BoundedInteger> initializeProperMap() {
        Map<CasteObject, BoundedInteger> toReturn = new HashMap<>();
        final int size = 100/ALL_CASTES.length;
        for (CasteObject c : ALL_CASTES){
            BoundedInteger bi = new BoundedInteger(0,100);
            bi.set(size);
            toReturn.put(c,bi);
        }
        return toReturn;
    }
}
