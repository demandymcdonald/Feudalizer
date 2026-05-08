package com.utilities.number.bound_int;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public abstract class BoundInt {
    private int number;
    public BoundInt(int number) {
        this.number = Math.clamp(number,getMin(),getMax());
    }
    public abstract int getMin();
    public abstract int getMax();

    public BoundInt add(int value) {
        number = Math.clamp(number + value, getMin(), getMax());
        return this;
    }
    public BoundInt set(int value) {
        number = Math.clamp(value, getMin(), getMax());
        return this;
    }
    public int get() {
        return number;
    }
    public JsonElement serialize() {
        return new JsonPrimitive(number);
    }
    public void deserialize(JsonElement element) {
        number = element.getAsInt();
    }
}
