package com.objects.title.land.resources.node;

import com.base.reference.DMEReference;
import com.objects.title.land.habitable.HabitableLand;
import com.objects.title.land.resources.good.IGood;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class BasicNode extends Node{
    private final Map<IGood,Float> production = new HashMap<>();
    private final Map<IGood,Float> consumption = new HashMap<>();
    private final Map<Pair<IGood,Integer>,BiFunction<IGood,Double,Double>> productionModifiersLevel = new HashMap<>();
    private final Map<Pair<IGood,Integer>,BiFunction<IGood,Double,Double>> consumptionModifiersLevel = new HashMap<>();
    //TODO in V2 factor in culture modifiers from private vs public.
    public BasicNode(DMEReference<? extends HabitableLand<?>> host, String id, String name, String description) {
        super(host, id, name, description);
    }
    public BasicNode(DMEReference<? extends HabitableLand<?>> host, String id, String name, String description, int level) {
        super(host, id, name, description, level);
    }
    @Override
    protected Map<IGood, Float> activeTokenConsumption() {
        return consumption;
    }

    @Override
    protected Map<IGood, Float> activeTokenProduction() {
        return production;
    }

    @Override
    protected double applyConsumptionModifier(IGood good, double quantity) {
        if (consumptionModifiersLevel.isEmpty()) return quantity;
        int currentLevel = getLevel();
        for (Pair<IGood,Integer> modifier : consumptionModifiersLevel.keySet()) {
            if (modifier.getKey() == good && modifier.getValue() <= currentLevel) {
                return consumptionModifiersLevel.get(modifier).apply(good,quantity);
            }
        }
        return super.applyConsumptionModifier(good, quantity);
    }

    @Override
    protected double applyProductionModifier(IGood good, double quantity) {
        if (productionModifiersLevel.isEmpty()) return quantity;
        int currentLevel = getLevel();
        for (Pair<IGood,Integer> modifier : productionModifiersLevel.keySet()) {
            if (modifier.getKey() == good && modifier.getValue() <= currentLevel) {
                return productionModifiersLevel.get(modifier).apply(good,quantity);
            }
        }
        return super.applyProductionModifier(good, quantity);
    }
    protected void internalSetProductionMap(Map<IGood,Float> production) {
        this.production.clear();
        this.production.putAll(production);
    }
    protected void internalSetConsumptionMap(Map<IGood,Float> production) {
        this.consumption.clear();
        this.consumption.putAll(production);
    }
    @Override
    public boolean isActive() {
        //TODO figure out active logic later.
        return true;
    }

    public static Builder builder(DMEReference<? extends HabitableLand<?>> host, String id, String name, String description) {
        return new Builder(host, id, name, description);
    }

    public static class Builder extends NodeBuilder<BasicNode,Builder>{
        private Map<IGood,Float> production = new HashMap<>();
        private Map<IGood,Float> consumption = new HashMap<>();
        private final Map<Pair<IGood,Integer>,BiFunction<IGood,Double,Double>> productionModifiersLevel = new HashMap<>();
        private final Map<Pair<IGood,Integer>,BiFunction<IGood,Double,Double>> consumptionModifiersLevel = new HashMap<>();
        public Builder(DMEReference<? extends HabitableLand<?>> host, String id, String name, String description) {
            super(host, id, name, description);
        }

        @Override
        protected BasicNode newInstance(DMEReference<? extends HabitableLand<?>> host, String id, String name, String description) {
            return new BasicNode(host, id, name, description);
        }
        public Builder addProduct(IGood good, float amount, @Nullable Integer levelStart, @Nullable BiFunction<IGood,Double,Double> productionModifier) {
            addProductionModifier(good,levelStart,productionModifier);
            production.put(good,amount);
            return this;
        };
        public Builder addProduct(IGood good, float amount) {
            production.put(good,amount);
            return this;
        };
        public Builder addConsumption(IGood good, float amount) {
            consumption.put(good,amount);
            return this;
        };
        public Builder addConsumption(IGood good, float amount, @Nullable Integer levelStart, @Nullable BiFunction<IGood,Double,Double> consumptionModifier) {
            addConsumptionModifier(good,levelStart,consumptionModifier);
            consumption.put(good,amount);
            return this;
        };
        public Builder addProductionModifier(IGood good, Integer levelStart, BiFunction<IGood,Double,Double> productionModifier){
            if(good != null && productionModifier != null && levelStart != null) {
                productionModifiersLevel.put(Pair.of(good, levelStart), productionModifier);
            }
            return this;
        }
        public Builder addConsumptionModifier(IGood good, Integer levelStart, BiFunction<IGood,Double,Double> consumptionModifier){
            if(good != null && consumptionModifier != null && levelStart != null) {
                productionModifiersLevel.put(Pair.of(good, levelStart), consumptionModifier);
            }
            return this;
        }
        @Override
        protected BasicNode doBuild(BasicNode starting) {
            starting.internalSetProductionMap(production);
            starting.internalSetConsumptionMap(consumption);
            starting.productionModifiersLevel.putAll(productionModifiersLevel);
            starting.consumptionModifiersLevel.putAll(consumptionModifiersLevel);
            return starting;
        }
    }

}
