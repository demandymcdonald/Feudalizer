package com.utilities.number;

import com.Global.*;
import com.google.gson.JsonElement;

public class BoundedFloat extends BoundedNumber<Float>{
    public BoundedFloat(float min, float max) {
        super(min, max);
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
