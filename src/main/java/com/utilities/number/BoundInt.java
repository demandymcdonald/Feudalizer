package com.utilities.number;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public abstract class BoundInt {
    private int number;
    public BoundInt(int number) {
        this.number = Math.clamp(number,getMin(),getMax());
    }
    public abstract int getMin();
    public abstract int getMax();

    public void add(int value) {
        number = Math.clamp(number + value, getMin(), getMax());
    }
    public void set(int value) {
        number = Math.clamp(value, getMin(), getMax());
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
