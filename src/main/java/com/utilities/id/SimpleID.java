package com.utilities.id;

import com.Global.*;

public record SimpleID(String s) implements StringIdentifiable{

    @Override
    public String getID() {
        return s;
    }
}
