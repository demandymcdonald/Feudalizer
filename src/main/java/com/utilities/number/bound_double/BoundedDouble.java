package com.utilities.number.bound_double;

import com.google.gson.JsonElement;
import com.utilities.number.BoundedNumber;

public class BoundedDouble extends BoundedNumber<Double> implements Comparable<BoundedDouble>{
    public BoundedDouble(double min, double max) {
        super(min, max);
    }

    @Override
    public void add(Double value) {
        set(get() + value);
    }

    @Override
    protected Double onSet(Double value) {
        return Math.clamp(value, getMin(), getMax());
    }

    @Override
    public void deserialize(JsonElement element) {
        if(element.isJsonPrimitive()){
            set( element.getAsDouble());
        }
    }

    @Override
    public int compareTo(BoundedDouble o) {
        return Double.compare(get(), o.get());
    }
}
