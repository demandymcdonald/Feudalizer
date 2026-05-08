package com.utilities.number.bound_float;

import com.google.gson.JsonElement;
import com.utilities.number.BoundedNumber;

public class BoundFloat extends BoundedNumber<Float> {
    public BoundFloat(float min, float max) {
        super(min, max);
    }

    public BoundFloat(Float min, Float max, Float current) {
        super(min, max, current);
    }
    @Override
    public void add(Float value) {
        set(get() + value);
    }
    @Override
    protected Float onSet(Float value) {
        return Math.clamp(value, getMin(), getMax());
    }

    @Override
    public void deserialize(JsonElement element) {
        if(element.isJsonPrimitive()){
            set( element.getAsFloat());
        }
    }

}
