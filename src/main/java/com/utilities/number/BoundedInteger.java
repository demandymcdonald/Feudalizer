package com.utilities.number;

import com.Global.*;

public class BoundedInteger {
    private final int min;
    private final int max;
    private int current;
    public BoundedInteger(int min, int max) {
        this.min = min;
        this.max = max;
        this.current = min;
    }
    public void set(int value){
        current = Math.clamp(value, min, max);
    }
    public int current(){
        return current;
    }
}
