package com.utilities.number;

import com.Global.*;
import com.google.gson.JsonElement;

public class BoundedInteger extends BoundedNumber<Integer> {

    public BoundedInteger(int min, int max) {
        super(min, max);
    }
    @Override
    public void add(Integer value) {
        set(get() + value);
    }
    @Override
    protected Integer onSet(Integer value) {
        return Math.clamp(value, getMin(), getMax());
    }
    @Override
    public void deserialize(JsonElement element) {
        if(element.isJsonPrimitive()){
            set( element.getAsInt());
        }
    }
}
