package com.utilities.number.bound_int;

import com.google.gson.JsonElement;
import com.utilities.number.BoundedNumber;

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

    public static BoundedInteger of(int min, int max){
        BoundedInteger bounded = new BoundedInteger(min, max);
        bounded.set(min + (max - min)/2);
        return bounded;
    }
}
