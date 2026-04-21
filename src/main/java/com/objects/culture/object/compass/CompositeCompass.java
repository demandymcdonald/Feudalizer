package com.objects.culture.object.compass;

import com.base.utilities.TLSyncedSupplier;
import com.google.common.collect.ImmutableMap;
import com.utilities.caching.CachingSupplier;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class CompositeCompass implements IPoliticalCompass{
    private final CachingSupplier<Value> axisA;
    private final CachingSupplier<Value> axisB;
    private final CachingSupplier<Value> axisC;
    private final CachingSupplier<Value> axisD;
    private final CachingSupplier<Value> tolerance;
    private final Supplier<Map<IPoliticalCompass,Double>> politicalCompasses;
    public CompositeCompass(Supplier<Map<IPoliticalCompass,Double>> politicalCompasses) {
        this.politicalCompasses = politicalCompasses;
        axisA = new CachingSupplier<>(() -> {return compositeValue(Axis.INDIVIDUAL_COLLECTIVE,politicalCompasses.get());});
        axisB = new CachingSupplier<>(() -> {return compositeValue(Axis.UNIVERSAL_PARTICULAR,politicalCompasses.get());});
        axisC = new CachingSupplier<>(() -> {return compositeValue(Axis.TRUST_IN_OPACITY,politicalCompasses.get());});
        axisD = new CachingSupplier<>(() -> {return compositeValue(Axis.EGALITARIAN_HIERARCHY,politicalCompasses.get());});
        tolerance = new CachingSupplier<>(() -> {return compositeValue(politicalCompasses.get());});
    }
    @Override
    public Value getAxisA() {
        return axisA.get();
    }

    @Override
    public Value getAxisB() {
        return axisB.get();
    }

    @Override
    public Value getAxisC() {
        return axisC.get();
    }

    @Override
    public Value getAxisD() {
        return axisD.get();
    }

    @Override
    public Value getTolerance() {
        return tolerance.get();
    }
    public void clear(){
        axisA.clear();
        axisB.clear();
        axisC.clear();
        axisD.clear();
        tolerance.clear();
    }
    @Override
    public ImmutableMap<Axis, Value> getAxisMap() {
        return ImmutableMap.of(
            Axis.INDIVIDUAL_COLLECTIVE,this.getAxisA(),
            Axis.UNIVERSAL_PARTICULAR,this.getAxisB(),
            Axis.TRUST_IN_OPACITY,this.getAxisC(),
            Axis.EGALITARIAN_HIERARCHY,this.getAxisD());
    }
    private static Value compositeValue(Axis axis, Map<IPoliticalCompass,Double> politicalCompasses) {
        int total = 0;
        double sum = politicalCompasses.values().stream().mapToDouble(Double::doubleValue).sum();
        for (Map.Entry<IPoliticalCompass,Double> compass : politicalCompasses.entrySet()) {
            total += (int) Math.round(Objects.requireNonNull(compass.getKey().getAxisMap().get(axis)).get()
                    * (1 - (compass.getValue()/sum)));
        }
        return new Value(total/politicalCompasses.size());
    }
    private static Value compositeValue(Map<IPoliticalCompass,Double> politicalCompasses) {
        int total = 0;
        double sum = politicalCompasses.values().stream().mapToDouble(Double::doubleValue).sum();
        for (Map.Entry<IPoliticalCompass,Double> compass : politicalCompasses.entrySet()) {
            total += (int) Math.round(Objects.requireNonNull(compass.getKey().getTolerance()).get()
                    * (1 - (compass.getValue()/sum)));
        }
        return new Value(total/politicalCompasses.size());
    }
    @Override
    public IPoliticalCompass clone() {
        return new CompositeCompass(this.politicalCompasses);
    }
}
