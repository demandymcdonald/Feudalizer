package com.utilities.number;

import com.Global.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public abstract class BoundedNumber<N extends Number>{
    private final N min;
    private final N max;
    private N current;
    public BoundedNumber(N min, N max) {
        this.min = min;
        this.max = max;
    }
    public N getMin() {
        return min;
    }
    public N getMax() {
        return max;
    }
    public void set(N value){
        current = onSet(value);
    }
    public abstract void add(N value);
    protected abstract N onSet(N value);
    public N get(){
        return current;
    }

    public JsonElement serialize(){
        return new JsonPrimitive(current);
    }
    public abstract void deserialize(JsonElement element);


}
