package com.objects.character.physical.genetics;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.objects.character.physical.GeneManager;
import com.utilities.number.BoundDbl;
import com.utilities.number.BoundDoubles;

public record TraitInstance(Gene trait, boolean active, boolean inheritable, boolean spectrum, BoundDbl value) {
    private static final int decimalPlaces = 2;

    public void toJson(JsonObject object) {
        int packed = 0;
        packed |= (active ? 1 : 0);
        packed |= (inheritable ? 1 : 0) << 1;
        packed |= (spectrum ? 1 : 0) << 2;
        packed |= (int) ((Math.round(value.get() * Math.pow(10,decimalPlaces))) << 3);
        object.addProperty(trait.getDisplayID(), packed);
    }
    public static TraitInstance fromJson(String key, JsonElement object) {
        int packed = object.getAsInt();
        boolean isActive = (packed & 0x1) != 0;
        boolean isInheritable = (packed >> 1 & 0x1) != 0;
        boolean isSpectrum = (packed >> 2 & 0x1) != 0;
        double value = (packed >> 3) / Math.pow(10, decimalPlaces);
        return new TraitInstance(GeneManager.get(key),isActive,isInheritable,isSpectrum,value);
    }

    private static BoundDbl getNew(){
        return BoundDoubles.dbl1024(false).setPlaces(decimalPlaces);
    }

}
