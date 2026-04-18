package com.objects.culture.term;

import com.base.reference.StateReference;
import com.google.gson.JsonObject;
import com.utilities.IDisplayable;

public class CultureTerm extends StateReference implements IDisplayable {
    @Override
    public String getDisplayID() {
        return "";
    }

    @Override
    public String getDisplayName() {
        return "";
    }

    @Override
    public String getDescription() {
        return "";
    }


    @Override
    public String parse() {
        return "";
    }

    @Override
    public JsonObject serialize() {
        return null;
    }


}
