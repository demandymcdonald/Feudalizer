package com.objects.shared;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.multi.TimelineMap;
import com.base.timeline.variable.EasingVariable;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.gson.JsonObject;
import com.objects.culture.tenet.caste.CasteObject;
import com.objects.culture.tenet.group.population.InterestGroup;
import com.objects.title.land.habitable.HabitableLand;
import com.utilities.ThreadManager;
import com.utilities.id.Identifiable;
import com.utilities.id.StringIdentifiable;
import com.utilities.number.BoundInt;
import com.utilities.number.BoundInts;
import com.utilities.number.BoundedDouble;
import com.utilities.number.BoundedInteger;
import org.apache.commons.lang3.mutable.MutableInt;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

import static com.objects.culture.tenet.caste.Castes.ALL_CASTES;

public class PopulationContainer<T extends DateMutableEntity<T> & IDemographicDriven<T>> implements EasingVariable<PopulationContainer<T>,PopulationChange<T>,T> {
    private DMEReference<? extends T> owner;
    private PopulationChange<T> currentChange;
    private TimelineMap<InterestGroup, BoundInt,T>  populationMap;
    private Map<InterestGroup, BoundedDouble> interpolatedPopulationMap = new HashMap<>();
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
        //TODO Figure out how I want to handle default populating the demo field. Probably by culture? But maybe we have custom presets for type/procedural presets.
        PopulationChange<T> change = new PopulationChange<>(owner, owner.get().getCreated());
        owner.get().getTimeline().internalAddChange(change);
    }

    public PopulationContainer() {}

    public void setPopulation(long population){
        if(population < 0){
            return;
        }
        if (population != this.totalPopulation) {
            this.totalPopulation = population;
            currentChange.setPopulation(population);
            this.calculateVariables();
        }
    }
    public void addPopulation(long population){
        if (population == 0){
            return;
        }
        population += this.totalPopulation;
        currentChange.setPopulation(Math.max(population,0));
        this.calculateVariables();
    }
    public void addDemographic(InterestGroup demographic, int percentage, boolean wipeForward){
        BoundInt percent = BoundInts.Int256(false);
        percent.set(percentage);
        populationMap.put(false,wipeForward,demographic,percent);
        this.calculateVariables();
    }
    public void addDemographicWeighted(InterestGroup demographic, int percentage, boolean wipeForward){
        BoundInt percent = BoundInts.Int256(false);
        double totalWeight = getTotalDemo(demographic);
        double realPercent = (double) percentage/100;
        double leftOver = 1 - realPercent;
        percent.set((int) Math.round((totalWeight * realPercent) / leftOver));
        Map<InterestGroup, BoundedDouble> demographicMap = filterByDimension(demographic);
        double totalOther;
        if (!demographicMap.isEmpty()) {
            if (demographicMap.containsKey(demographic)) {
                BoundedDouble boundedDouble = demographicMap.get(demographic);
                demographicMap.remove(demographic);
                totalOther = (getTotalDemo(demographic) - boundedDouble.get()) * leftOver;
            } else {
                totalOther = (getTotalDemo(demographic) * leftOver);
            }
            Map<InterestGroup, BoundInt> toReturn = new HashMap<>();
            for (Map.Entry<InterestGroup, BoundedDouble> entry : demographicMap.entrySet()) {
                BoundInt boundInt = BoundInts.Int256(false);
                double value = entry.getValue().get();
                boundInt.set((int) Math.round(value * (value/totalOther)));
                toReturn.put(entry.getKey(),boundInt);
            }
            populationMap.putAll(false,wipeForward,toReturn);
            this.calculateVariables();
        }
    }
    public long getNumberOfMembers(InterestGroup demographic){
        double percent = getDemographicPercentage(demographic);
        return Math.round(getPopulation() * percent);
    }
    public double getDemographicPercentage(InterestGroup demographic){
        BoundedDouble d = interpolatedPopulationMap.get(demographic);
        if (d == null){
            return 0;
        }

        return d.get()/getTotalDemo(demographic);
    }
    private double getTotalDemo(@Nullable InterestGroup demographic){
        Map<InterestGroup,BoundedDouble> map;
        if (demographic == null){
            map = new HashMap<>(interpolatedPopulationMap);
        } else {
            map = filterByDimension(demographic);
        }
        double total = 0;
        for(BoundedDouble boundedDouble : map.values()){
            total += boundedDouble.get();
        }
        return total;
    }
    private Map<InterestGroup,BoundedDouble> filterByDimension(InterestGroup demographic){
        return new HashMap<>(Maps.filterKeys(interpolatedPopulationMap, (InterestGroup key) -> {
            return key.getDimension().equals(demographic.getDimension());}));
    }

    public boolean isInMajority(InterestGroup group){
        if (!interpolatedPopulationMap.containsKey(group)) {
            return false;
        } else if (interpolatedPopulationMap.size() == 1){
            return true;
        }
        Map<InterestGroup,BoundedDouble> doubleMap = filterByDimension(group);
        List<InterestGroup> groups = Ordering.natural().reverse().onResultOf(doubleMap::get).sortedCopy(doubleMap.keySet());
        int topHalf = (groups.size()/2);
        return groups.indexOf(group) < topHalf;
    }
    @Override
    public final void calculateVariables() {
        interpolatedPopulationMap.clear();
        EasingVariable.super.calculateVariables();
    }

    public long getPopulation(){
        return interpolatedPopulation;
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
        return buildEasing();
    }
    private Map<Identifiable<?>, VariableContainer> buildEasing(){
        Map<Identifiable<?>, VariableContainer> easingFunctions = new HashMap<>();
        easingFunctions.put(popVar, new VariableContainer(EasingType.LERP, () -> (double) totalPopulation, d -> interpolatedPopulation = Math.round(d)));
        for (Map.Entry<InterestGroup, BoundInt> c : populationMap.entrySet()){
            easingFunctions.put(c.getKey(), new VariableContainer(EasingType.LERP, () -> (double) c.getValue().get(), d -> {
                BoundedDouble boundedDouble = interpolatedPopulationMap.computeIfAbsent(c.getKey(), (key) -> {return new BoundedDouble(0,256);});
                boundedDouble.set(d);
            }));
        }
        return easingFunctions;
    }
    @Override
    public Predicate<PopulationContainer<T>> getMatching() {
        return null;
    }
    public void internalSetPopulation(long newPopulation){
        totalPopulation = newPopulation;
    }
    public void internalSetMap(TimelineMap<InterestGroup, BoundInt,T>  newMap){
        populationMap = newMap;
    }
    public TimelineMap<InterestGroup, BoundInt,T> internalGetPopulationMap(){
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

    public static class SharedInt extends BoundInt{
        public SharedInt(int number) {
            super(number);
        }

        @Override
        public int getMin() {
            return 0;
        }

        @Override
        public int getMax() {
            return 100;
        }
    }
}
