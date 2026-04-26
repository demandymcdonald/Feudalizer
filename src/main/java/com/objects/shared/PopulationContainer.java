package com.objects.shared;

import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.multi.wrapper.TLMap;
import com.base.datemutable.timeline.variable.EasingVariable;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.gson.JsonObject;
import com.objects.culture.object.compass.IPoliticalCompass;
import com.objects.culture.tenet.interest.InterestGroup;
import com.utilities.id.Identifiable;
import com.utilities.id.StringIdentifiable;
import com.utilities.number.*;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

public class PopulationContainer<T extends DateMutableEntity<T> & IDemographicDriven<T>> implements EasingVariable<PopulationContainer<T>,PopulationChange<T>,T> {
    private DMEReference<? extends T> owner;
    private PopulationChange<T> currentChange;
    private TLMap<InterestGroup, BoundInt> popMap;
    private Map<InterestGroup, BoundDbl> intPopMap = new HashMap<>();
    //private final CachingSupplier<Double> totalIG = new CachingSupplier<>(this::getTotalDouble);
    private static final StringIdentifiable popVar = new StringIdentifiable("totalPopulation") {
        @Override
        public String getID() {
            return "population";
        }

    };
    private long totalPopulation;
    private long interpolatedPopulation;


    public PopulationContainer(DMEReference<? extends T> owner) {
        this.owner = owner;
        //TODO Figure out how I want to handle default populating the demo field. Probably by culture? But maybe we have custom presets for type/procedural presets.
        PopulationChange<T> change = new PopulationChange<>(owner, owner.get().getCreated());
        owner.get().getTimeline().internalAddChange(change);
    }

    public PopulationContainer() {}
    public Map<IPoliticalCompass,Double> getCompasses() {
        Map<IPoliticalCompass,Double> map = new HashMap<>();
        for(InterestGroup interpolatedGroup : intPopMap.keySet()) {
            map.put(interpolatedGroup.get)
        }
    }
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
    public long getPopulationByDemo(InterestGroup interpolatedGroup){
        double fraction = intPopMap.containsKey(interpolatedGroup) ?
                intPopMap.get(interpolatedGroup).get() : 0;
        return Math.round(totalPopulation * (fraction));
    }
    public double getTotalDouble() {
        double l = 0;
        for(InterestGroup interpolatedGroup : intPopMap.keySet()) {
            l += intPopMap.get(interpolatedGroup).get();
        }
        return l;
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
        popMap.put(false,wipeForward,demographic,percent);
        this.calculateVariables();
    }
    public void addDemographicWeighted(InterestGroup demographic, int percentage, boolean wipeForward){
        percentage = Math.clamp(percentage, 0, 100);
        BoundInt percent = BoundInts.Int256(false);
        double totalWeight = getTotalDemo(demographic);
        double realPercent = (double) percentage/100;
        double leftOver = 1 - realPercent;
        percent.set((int) Math.round((totalWeight * realPercent) / leftOver));
        Map<InterestGroup, BoundDbl> demographicMap = filterByDimension(demographic);
        double totalOther;
        if (!demographicMap.isEmpty() && !(demographicMap.size() == 1 && demographicMap.containsKey(demographic))) {
            if (demographicMap.containsKey(demographic)) {
                BoundDbl BoundDbl = demographicMap.get(demographic);
                demographicMap.remove(demographic);
                totalOther = (getTotalDemo(demographic) - BoundDbl.get()) * leftOver;
            } else {
                totalOther = (getTotalDemo(demographic) * leftOver);
            }
            Map<InterestGroup, BoundInt> toReturn = new HashMap<>();
            toReturn.put(demographic, percent);
            for (Map.Entry<InterestGroup, BoundDbl> entry : demographicMap.entrySet()) {
                BoundInt boundInt = BoundInts.Int256(false);
                double value = entry.getValue().get();
                boundInt.set((int) Math.round(value * (leftOver/totalOther)));
                toReturn.put(entry.getKey(),boundInt);
            }
            popMap.putAll(false,wipeForward,toReturn);
            this.calculateVariables();
        }else{
            popMap.put(false,wipeForward,demographic,percent);
        }
    }

    public double getDemographicPercentage(InterestGroup demographic){
        BoundDbl d = intPopMap.get(demographic);
        if (d == null){
            return 0;
        }

        return d.get()/getTotalDemo(demographic);
    }
    private double getTotalDemo(@Nullable InterestGroup demographic){
        Map<InterestGroup,BoundDbl> map;
        if (demographic == null){
            map = new HashMap<>(intPopMap);
        } else {
            map = filterByDimension(demographic);
        }
        double total = 0;
        for(BoundDbl BoundDbl : map.values()){
            total += BoundDbl.get();
        }
        return total;
    }

    private Map<InterestGroup,BoundDbl> filterByDimension(InterestGroup demographic){
        return new HashMap<>(Maps.filterKeys(intPopMap, (InterestGroup key) -> {
            return key.getDimension().equals(demographic.getDimension());}));
    }

    public boolean isInMajority(InterestGroup group){
        if (!intPopMap.containsKey(group)) {
            return false;
        } else if (intPopMap.size() == 1){
            return true;
        }
        Map<InterestGroup,BoundDbl> doubleMap = filterByDimension(group);
        List<InterestGroup> groups = Ordering.natural().reverse().onResultOf(doubleMap::get).sortedCopy(doubleMap.keySet());
        int topHalf = (groups.size()/2);
        return groups.indexOf(group) < topHalf;
    }
    @Override
    public final void calculateVariables() {
        intPopMap.clear();
        EasingVariable.super.calculateVariables();
    }

    public long getPopulation(){
        return interpolatedPopulation;
    }
    public long getPopulation(InterestGroup demographic){
        double percent = getDemographicPercentage(demographic);
        return Math.round(getPopulation() * percent);
    }
    @Override
    public String getChangeClassName() {
        return PopulationChange.class.getName();
    }

    @Override
    public DMEReference<? extends T> getOwner() {
        return owner;
    }
    public Map<InterestGroup, BoundDbl> getPopulationMap() {
        return new HashMap<>(intPopMap);
    }
    @Override
    public Map<Identifiable<?>, VariableContainer> getEasingFunctions() {
        return buildEasing();
    }
    private Map<Identifiable<?>, VariableContainer> buildEasing(){
        Map<Identifiable<?>, VariableContainer> easingFunctions = new HashMap<>();
        easingFunctions.put(popVar, new VariableContainer(EasingType.LERP, () -> (double) totalPopulation, d -> interpolatedPopulation = Math.round(d)));
        for (Map.Entry<InterestGroup, BoundInt> c : popMap.entrySet()){
            easingFunctions.put(c.getKey(), new VariableContainer(EasingType.LERP, () -> (double) c.getValue().get(), d -> {
                BoundDbl BoundDbl = intPopMap.computeIfAbsent(c.getKey(), (key) -> {return BoundDoubles.dbl256(false);});
                BoundDbl.set(d);
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
    public void internalSetMap(TLMap<InterestGroup,BoundInt> newMap){
        popMap = newMap;
    }
    public TLMap<InterestGroup,BoundInt> internalGetMap(){
        return popMap;
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
