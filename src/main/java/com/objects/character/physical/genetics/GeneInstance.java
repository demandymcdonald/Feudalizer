package com.objects.character.physical.genetics;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.objects.character.physical.GeneManager;
import com.utilities.number.BoundDbl;
import com.utilities.number.BoundDoubles;
import org.apache.commons.lang3.mutable.MutableBoolean;

import static com.objects.character.physical.genetics.SpectrumTrait.SPECTRUM_SIZE;

public record GeneInstance(Gene trait, MutableBoolean active, boolean spectrum, BoundDbl chance, BoundDbl value) {
    private static final int decimalPlacesChance = 10; // about 1 in 10 billion if 100 = 100%
    private static final int decimalPlacesValue = 2;
    public GeneInstance(Gene trait){
        this(trait, new MutableBoolean(true), trait instanceof SpectrumTrait || trait instanceof SpectrumTrait.SpectrumEntry, getNew(trait.getChance()),getValue(trait,null));
    }
    public void toJson(JsonObject object) {
        StringBuilder sb = new StringBuilder();
        sb.append(active.getValue() ? 1:0).append(":")
        .append(spectrum ? 1:0).append(":")
        .append(Math.round(chance.get() * Math.pow(10, decimalPlacesChance))).append(":")
        .append(Math.round(value.get() * Math.pow(10, decimalPlacesValue)));
        object.addProperty(trait.getDisplayID(), sb.toString());
    }
    public static GeneInstance fromJson(String key, JsonElement object) {
        String[] split = object.getAsString().split(":");
        boolean isActive = split[0].equals("1");
        boolean isSpectrum = split[1].equals("1");
        double chance = Double.parseDouble(split[2]) / Math.pow(10, decimalPlacesChance);
        double value = Double.parseDouble(split[3]) / Math.pow(10, decimalPlacesValue);
        Gene gene = GeneManager.Genetics.get(key);
        return new GeneInstance(gene,new MutableBoolean(isActive),isSpectrum,getNew(chance),getValue(gene,value));
    }
    private static BoundDbl getNew(double chance){
        return BoundDoubles.percent(false,chance).setPlaces(decimalPlacesChance);
    }
    private static BoundDbl getValue(Gene gene, Double value){
        BoundDbl d;
        if (gene instanceof SpectrumTrait st || gene instanceof SpectrumTrait.SpectrumEntry se){
            d = SPECTRUM_SIZE.get().setPlaces(decimalPlacesValue);
            if (value == null){
                d.set(d.getMax()/2);
            } else {
                d.set(value);
            }
        } else {
            if (value == null){
                value = (double) gene.getStrength().getStrength();
            }
            d = BoundDoubles.dbl256(false,value).setPlaces(decimalPlacesValue);
        }
        return d;
    }
}
