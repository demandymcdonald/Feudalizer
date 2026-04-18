package com.utilities.number;

import org.apache.commons.math3.util.Precision;

import java.util.Objects;

public abstract class BoundDbl implements Comparable<BoundDbl> {
    private double value;
    private int places = 5;
    BoundDbl(double value){
        this.value = doMath(value);
    }
    BoundDbl(double value, int places){
        this.places = places;
        this.value = doMath(value);
    }
    public abstract double getMin();
    public abstract double getMax();

    public final BoundDbl set(double d){
        this.value = doMath(d);
        return this;
    }
    private double doMath(double d){
        return Precision.round(Math.clamp(d,getMin(),getMax()),places);
    }
    public final BoundDbl add(double d){
        this.value = doMath(this.value + d);
        return this;
    }
    public final BoundDbl setPlaces(int places){
        this.places = Math.abs(places);
        this.value = doMath(this.value);
        return this;
    }
    public final double get(){
        return value;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BoundDbl boundDbl = (BoundDbl) o;
        return Double.compare(value, boundDbl.value) == 0 && places == boundDbl.places;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, places);
    }

    @Override
    public int compareTo(BoundDbl o) {
        return Double.compare(value,o.value);
    }
}
