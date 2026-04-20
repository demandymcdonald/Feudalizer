package com.objects.title.land.resources.node;

import com.base.reference.DMEReference;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.objects.title.land.habitable.HabitableLand;
import com.objects.title.land.resources.GoodManager;
import com.objects.title.land.resources.good.GoodToken;
import com.objects.title.land.resources.good.IGood;
import com.utilities.IDisplayable;
import com.utilities.caching.CachingSupplier;
import com.utilities.hierarchy.StateIntegrity;
import com.utilities.id.StringIdentifiable;
import org.apache.commons.lang3.mutable.MutableLong;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.BiFunction;

public abstract class Node implements StringIdentifiable, IDisplayable, StateIntegrity {
    private static final int threshold_level = 50;
    private static final int base_scaler = 10;
    private final DMEReference<? extends HabitableLand<?>> host;
    private final UUID runtimeID;
    private final CachingSupplier<ConsumerContainer> consumerContainer = new CachingSupplier<>(this::makeToken);
    private final MutableLong previous;
    private final String id;
    private final String name;
    private final String description;
    public Node(DMEReference<? extends HabitableLand<?>> host, String id, String name, String description) {
        this.host = host;
        runtimeID = UUID.randomUUID(); //UUID is randomly generated per instance.
        this.id = id;
        this.name = name;
        this.description = description;
        previous = new MutableLong(getCurrent());
    }
    public final UUID getRuntimeID() {
        return runtimeID;
    }
    public final DMEReference<? extends HabitableLand<?>> getHost(){
        return host;
    };
    protected abstract Map<IGood,Integer> activeTokenConsumption();
    protected abstract Map<IGood,Integer> activeTokenProduction();
    public abstract int getLevel();
    protected double scaleFactor(){
        return .6D; //default pulled from six-tenths rule in engineering re: cost scaling.
    };
    protected abstract long getPopulationImpact();
    public abstract boolean isActive();
    public final Set<GoodToken> getTokenConsumption(){
        if(isActive()){
            return consumerContainer.get().consumes();
        } else {
            return Set.of();
        }
    }
    public final Set<GoodToken> getTokenProduction(){
        if(isActive()){
            return consumerContainer.get().produces();
        } else {
            return Set.of();
        }
    }
    protected double applyProductionModifier(IGood good, double quantity){
        return quantity;
    }
    protected double applyConsumptionModifier(IGood good, double quantity){
        return quantity;
    }
    private ConsumerContainer makeToken(){
        Set<GoodToken> prodTokens = new HashSet<>();
        Set<GoodToken> conTokens = new HashSet<>();
        double totalProduction = 0;
        int baseProduction = 0;
        for (Map.Entry<IGood,Integer> entry : activeTokenProduction().entrySet()) {
            int value = (int) Math.round(productionFormula(entry.getKey(),getLevel(),entry.getValue()));
            baseProduction += entry.getValue();
            totalProduction += value;
            prodTokens.add(new GoodToken(entry.getKey(),value));
        }

        for(Map.Entry<IGood,Integer> entry : activeTokenConsumption().entrySet()){
            double baseCost = applyConsumptionModifier(entry.getKey(),entry.getValue());
            int finalCost = (int) Math.round(baseCost * Math.pow(totalProduction / baseProduction,scaleFactor()));
            conTokens.add(new GoodToken(entry.getKey(),finalCost));
        }
        return new ConsumerContainer(prodTokens,conTokens);
    }
    private double productionFormula(IGood good, int level, int quantity){
        double baseScale = (base_scaler * (level/99D));
        double scaler = level>threshold_level ? baseScale + (-baseScale * ((level - (threshold_level/2D))/99D)) : baseScale;
        return applyProductionModifier(good,quantity * ((1 + (level + (level * scaler))/threshold_level)));
    }
    @Override
    public void onDirty() {
        StateIntegrity.super.onDirty();
        consumerContainer.clear();
    }
    @Override
    public final String getDisplayID() {
        return id;
    }

    @Override
    public final String getDisplayName() {
        return name;
    }

    @Override
    public final String getDescription() {
        return description;
    }
    @Override
    public final String getID() {
        return id;
    }
    @Override
    public final MutableLong getLast() {
        return previous;
    }

    public final JsonElement toJson() {
        return new JsonPrimitive(HexFormat.of().formatHex(id.getBytes(StandardCharsets.UTF_8)) + "::" +
                HexFormat.of().formatHex(name.getBytes(StandardCharsets.UTF_8)) + "::" +
                getLevel());
    }
    public static Node fromJson(DMEReference<? extends HabitableLand<?>> dme, JsonElement json) {
        String[] data = json.getAsString().split("::");
        String id = new String(HexFormat.of().parseHex(data[0]), StandardCharsets.UTF_8);
        String name = new String(HexFormat.of().parseHex(data[1]), StandardCharsets.UTF_8);
        int level = Integer.parseInt(data[2]);
        return GoodManager.getNode(id,dme,name,level);
    }

    @Override
    public final long getCurrent(){
        Hasher hash = Hashing.murmur3_128().newHasher();
        hash.putLong(getHost().getID().getMostSignificantBits());
        hash.putLong(getHost().getID().getLeastSignificantBits());
        hash.putLong(isActive() ? 1 : 0);
        return hash.hash().asLong();
    };
    private record ConsumerContainer(Set<GoodToken> produces, Set<GoodToken> consumes) {}
}
