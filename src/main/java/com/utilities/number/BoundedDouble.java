package com.utilities.number;

import com.Global.*;

public class BoundedDouble extends BoundedNumber<Double>{
    public BoundedDouble(double min, double max) {
        super(min, max);
    }
    @Override
    protected Double onSet(Double value) {
        return Math.clamp(value, getMin(), getMax());
    }
}
