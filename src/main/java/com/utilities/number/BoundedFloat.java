package com.utilities.number;

import com.Global.*;

public class BoundedFloat extends BoundedNumber<Float>{
    public BoundedFloat(float min, float max) {
        super(min, max);
    }
    @Override
    protected Float onSet(Float value) {
        return Math.clamp(value, getMin(), getMax());
    }
}
