package com.utilities.number;

public abstract class BoundFloat{
    float value;
    public BoundFloat(float value){
        this.value = Math.clamp(value,getMin(),getMax());
    }
    public abstract float getMin();
    public abstract float getMax();
    public final float getValue(){
        return value;
    };
    public final void set(float value){
        this.value = Math.clamp(value, getMin(), getMax());
    }
    public final void add(float value){
        this.value = Math.clamp(value + this.value, getMin(), getMax());
    }
    public final float get(){
        return value;
    }
}
