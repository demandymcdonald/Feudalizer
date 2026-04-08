package com.utilities.number;

import com.Global.*;
import com.google.gson.JsonElement;

public class BoundedDouble extends BoundedNumber<Double>{
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
}
