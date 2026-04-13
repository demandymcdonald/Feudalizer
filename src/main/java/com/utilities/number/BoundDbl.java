package com.utilities.number;

public abstract class BoundDbl {
    private double value;
    BoundDbl(double value){
        this.value = Math.clamp(value,getMin(),getMax());
    }

    public abstract double getMin();
    public abstract double getMax();

    public final void set(double d){
        this.value = Math.clamp(d,getMin(),getMax());
    }
    public final void add(double d){
        this.value = Math.clamp(d + value,getMin(),getMax());
    }
    public final double get(){
        return value;
    }
}
