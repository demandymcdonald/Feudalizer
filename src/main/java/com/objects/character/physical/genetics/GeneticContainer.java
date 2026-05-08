package com.objects.character.physical.genetics;

import com.google.gson.JsonObject;
import com.objects.character.physical.race.Race;
import com.utilities.number.bound_int.BoundInt;

import java.util.Map;

public record GeneticContainer (Map<Race, BoundInt> makeup, Map<GeneticTrait, GeneInstance> traits) {
    public void toJson(JsonObject object){

    }
    public static GeneticContainer fromJson(JsonObject object){

    }
}
