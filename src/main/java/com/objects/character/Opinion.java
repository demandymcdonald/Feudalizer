package com.objects.character;

import com.base.reference.DMEReference;
import com.google.gson.JsonObject;
import com.utilities.JsonSerializable;

import java.util.HashMap;
import java.util.List;

public class Opinion implements JsonSerializable<Opinion> {
    //TODO replace once I write the Basecode for psuedo-enums.
    public enum Reason{

        ;

        private final String flavor;
        private final int buff;
    }


    private DMEReference<BookCharacter> character;
    private int runningTotal;
    private int historySize;
    private HashMap<>


    @Override
    public JsonObject toJson() {
        return null;
    }

    @Override
    public void fromJson(JsonObject json) {

    }

    @Override
    public Opinion empty() {
        return null;
    }
}
