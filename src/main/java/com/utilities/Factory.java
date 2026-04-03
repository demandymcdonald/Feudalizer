package com.utilities;

import com.Global.*;
import com.base.DateMutableEntity;
import com.google.gson.JsonObject;

public abstract class Factory<tT extends T, T extends SuperclassSerializable,OK,BA> {
    Class<tT> subclassReference;

    protected abstract tT create(Class<tT> classRef, BA argumentContainer);

    protected abstract tT load(Class<tT> classRef, OK key, JsonObject object);
}
