package com.objects.title.land.resources.node;

import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.multi.type.ChangeType;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.objects.culture.tenet.interest.InterestGroup;
import com.objects.title.land.habitable.HabitableLand;
import com.objects.title.land.resources.GoodManager;
import com.objects.title.land.resources.good.GoodToken;
import com.objects.title.land.resources.good.IGood;
import com.utilities.IDisplayable;
import com.utilities.caching.CachingSupplier;
import com.utilities.hierarchy.StateIntegrity;
import com.utilities.id.StringIdentifiable;
import com.utilities.number.BoundInt;
import com.utilities.number.BoundInts;
import org.apache.commons.lang3.mutable.MutableLong;

import java.nio.charset.StandardCharsets;
import java.util.*;

public abstract class Node implements StringIdentifiable, IDisplayable, StateIntegrity {
    private static final int threshold_level = 50;
    private static final int base_scaler = 10;
    private final DMEReference<? extends HabitableLand<?>> host;
    private final UUID runtimeID;
    private final CachingSupplier<ConsumerContainer> consumerContainer = new CachingSupplier<>(this::makeToken);
    private final CachingSupplier<Set<WorkerRequirement>> workerRequirement = new CachingSupplier<>(this::makeRequirement);
    private final Map<InterestGroup,Long> baseWorkerRequirement = new HashMap<>();
    private double baseLandRequirement = 1;
    private double workerScaleFactor = .5;
    private double consumptionScaleFactor = .6;
    private final MutableLong previous;
    private final BoundInt level = BoundInts.Custom(1,1,99);
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
    public Node(DMEReference<? extends HabitableLand<?>> host, String id, String name, String description, int level) {
        this.host = host;
        runtimeID = UUID.randomUUID(); //UUID is randomly generated per instance.
        this.id = id;
        this.name = name;
        this.description = description;
        internalSetLevel(level);
        previous = new MutableLong(getCurrent());
    }
    public static int getBase_scaler() {
        return base_scaler;
    }

    public final UUID getRuntimeID() {
        return runtimeID;
    }
    public final DMEReference<? extends HabitableLand<?>> getHost(){
        return host;
    };
    protected abstract Map<IGood,Float> activeTokenConsumption();
    protected abstract Map<IGood,Float> activeTokenProduction();
    protected double getBaseLandRequirement(){
        return baseLandRequirement;
    }; //in meters
    protected double extraLandPerLevel(){
        return .4;
    };
    public final double getLandUse(){
        return getBaseLandRequirement() + (extraLandPerLevel() * (getLevel()-1));
    }
    public final int getLevel(){
        return level.get();
    };
    public final void setLevel(int newLevel){
        host.get().internalSetChanged(ChangeType.KEY,this,(n) -> {
            n.level.set(newLevel);
            n.workerRequirement.clear();
            n.consumerContainer.clear();
        });
    }
    public final void levelUp(){
        setLevel(level.get() + 1);
    }
    public final void levelDown(){
        setLevel(level.get() - 1);
    }

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
    protected void internalSetLevel(int level){
        this.level.set(level);
    }
    protected void internalSetBaseLandRequirement(double baseLandRequirement){
        this.baseLandRequirement = baseLandRequirement;
    }
    protected void internalSetWorkerScaleFactor(double workerScaleFactor){
        this.workerScaleFactor = workerScaleFactor;
    }
    protected void internalSetConsumptionScaleFactor(double consumptionScaleFactor){
        this.consumptionScaleFactor = consumptionScaleFactor;
    }
    protected void internalSetBaseWorkerRequirement(Map<InterestGroup,Long> baseWorkerRequirement){
        this.baseWorkerRequirement.putAll(baseWorkerRequirement);
    }
    protected double applyProductionModifier(IGood good, double quantity){
        return quantity;
    }
    protected double applyConsumptionModifier(IGood good, double quantity){
        return quantity;
    }
    protected double workerScaleFactor(){
        return workerScaleFactor; //assuming some efficiciency gains per level
    };
    protected double consumptionScaleFactor(){
        return consumptionScaleFactor; //default pulled from six-tenths rule in engineering re: cost scaling.
    };
    private Set<WorkerRequirement> makeRequirement(){
        Set<WorkerRequirement> requirements = new HashSet<>();
        for(Map.Entry<InterestGroup,Long> entry : baseWorkerRequirement.entrySet()){
            long base = entry.getValue();
            long levelImpact = Math.round((base * (getLevel()-1) * workerScaleFactor()));
            requirements.add(new WorkerRequirement(entry.getKey(),Math.round(entry.getValue() + levelImpact)));
        }
        return requirements;
    }
    private ConsumerContainer makeToken(){
        Set<GoodToken> prodTokens = new HashSet<>();
        Set<GoodToken> conTokens = new HashSet<>();
        double totalProduction = 0;
        int baseProduction = 0;
        for (Map.Entry<IGood,Float> entry : activeTokenProduction().entrySet()) {
            int value = (int) Math.round(productionFormula(entry.getKey(),getLevel(),entry.getValue()));
            baseProduction += entry.getValue();
            totalProduction += value;
            prodTokens.add(new GoodToken(entry.getKey(),value));
        }
        for(Map.Entry<IGood,Float> entry : activeTokenConsumption().entrySet()){
            double baseCost = applyConsumptionModifier(entry.getKey(),entry.getValue());
            int finalCost = (int) Math.round(baseCost * Math.pow(totalProduction / baseProduction, consumptionScaleFactor()));
            conTokens.add(new GoodToken(entry.getKey(),finalCost));
        }
        return new ConsumerContainer(prodTokens,conTokens);
    }
    private double productionFormula(IGood good, int level, float quantity){
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
        return new JsonPrimitive(stringToHex(id) + "::" + stringToHex(name) + "::" + getLevel());
    }
    public static Node fromJson(DMEReference<? extends HabitableLand<?>> dme, JsonElement json) {
        String[] data = json.getAsString().split("::");
        String id = hexToString(data[0]);
        String name = hexToString(data[1]);
        int level = Integer.parseInt(data[2]);
        return GoodManager.getNode(id,dme,name,level);
    }
    protected static String stringToHex(String s){
        return HexFormat.of().formatHex(s.getBytes(StandardCharsets.UTF_8));
    }
    protected static String hexToString(String s){
        return new String(HexFormat.of().parseHex(s), StandardCharsets.UTF_8);
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
    public static abstract class NodeBuilder<T extends Node,M extends NodeBuilder<T,M>>{
        private final DMEReference<? extends HabitableLand<?>> host;
        private final String id;
        private final String name;
        private final String description;
        private int level = 1;
        private double internalMeterLandRequirement = 500;
        private final Map<InterestGroup,Long> baseWorkerRequirement = new HashMap<>();
        private double workerScaleFactor = .5;
        private double consumptionScaleFactor = .6;
        public NodeBuilder(DMEReference<? extends HabitableLand<?>> host, String id, String name, String description) {
            this.host = host;
            this.id = id;
            this.name = name;
            this.description = description;
        }
        public M setStartingLevel(int level){
            this.level = level;
            return (M) this;
        }
        public M setLandRequirementMeters(double meters){
            internalMeterLandRequirement = meters;
            return (M) this;
        }
        public M setLandRequirementFeet(double feet){
            return setLandRequirementMeters(feet * 0.3048);
        }
        public M setLandRequirementKilometers(double kilometers){
            return setLandRequirementMeters(kilometers * 1000);
        }
        public M setLandRequirementMiles(double miles){
            return setLandRequirementMeters(miles * 1609.34);
        }
        public M setLandRequirementAcre(double acre){
            return setLandRequirementMeters(acre * 4046.86);
        }
        public M addWorkerRequirement(InterestGroup group, long amount){
            baseWorkerRequirement.put(group,amount);
            return (M) this;
        }
        public M setWorkerScaleFactor(double factor){
            workerScaleFactor = factor;
            return (M) this;
        }
        public M setConsumptionScaleFactor(double factor){
            consumptionScaleFactor = factor;
            return (M) this;
        }
        protected abstract T newInstance(DMEReference<? extends HabitableLand<?>> host, String id, String name, String description);

        public T build(){
            T newInstance = newInstance(host,id,name,description);
            newInstance.internalSetLevel(level);
            newInstance.internalSetBaseLandRequirement(internalMeterLandRequirement);
            newInstance.internalSetBaseWorkerRequirement(baseWorkerRequirement);
            newInstance.internalSetWorkerScaleFactor(workerScaleFactor);
            newInstance.internalSetConsumptionScaleFactor(consumptionScaleFactor);
            return doBuild(newInstance);
        }
        protected abstract T doBuild(T starting);
    }
}
