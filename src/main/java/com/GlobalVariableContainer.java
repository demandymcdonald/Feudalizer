package com;

import com.utilities.ThreadSpecific;

public class GlobalVariableContainer extends ThreadSpecific {
    @Override
    public Type specificTypeName() {
        return Type.GLOBAL_VARIABLE_CONTAINER;
    }

    @Override
    public void onThreadInit() {

    }
}
