package com.objects.culture.display;

import com.base.reference.StateReference;
import com.google.gson.JsonObject;
import com.utilities.Displayable;

public class CultureTerm extends StateReference implements Displayable {
    @Override
    public String getID() {
        return "";
    }

    @Override
    public String displayName() {
        return "";
    }

    @Override
    public String description() {
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
