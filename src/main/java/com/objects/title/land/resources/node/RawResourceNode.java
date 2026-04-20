package com.objects.title.land.resources.node;

import com.base.reference.DMEReference;
import com.objects.title.land.habitable.HabitableLand;
import com.objects.title.land.resources.good.IGood;
import com.utilities.number.BoundInt;
import com.utilities.number.BoundInts;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.Map;

public abstract class RawResourceNode extends Node{
    private final Map<IGood,Integer> production;
    private BoundInt level = BoundInts.Custom(1,1,99);
    public RawResourceNode(DMEReference<? extends HabitableLand<?>> host, String id, String name, String description, Map<IGood,Integer> producers) {
        super(host, id, name, description);
        this.production = producers;
    }

    @Override
    protected Map<IGood, Integer> activeTokenConsumption() {
        return production;
    }

    @Override
    protected Map<IGood, Integer> activeTokenProduction() {
        return Map.of();
    }

    @Override
    public int getLevel() {
        return level.get();
    }
    public RawResourceNode setLevel(int level) {
        this.level.set(level);
        return this;
    }
    @Override
    protected long getPopulationImpact() {
        return 0;
    }

    @Override
    public boolean isActive() {
        return false;
    }





}
