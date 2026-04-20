package com.objects.title.land.resources.node;

import com.base.reference.DMEReference;
import com.objects.title.land.habitable.HabitableLand;
import com.objects.title.land.resources.good.IGood;
import org.reactfx.util.TriFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class NodeType<T extends Node> {
    private final TriFunction<DMEReference<? extends HabitableLand<?>>,String,Integer,T> newInstance;
    private final Map<IGood,Integer> tokenProduction = new HashMap<>();
    private final Map<IGood,Integer> tokenConsumption = new HashMap<>();
    private final String description;
    private final String id;

    public T build(DMEReference<? extends HabitableLand<?>> reference, String name, int level) {
        return newInstance.apply(reference, name, level);
    }
}
