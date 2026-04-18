package com.objects.character.physical;

import com.google.gson.JsonObject;
import com.objects.character.physical.augment.AugmentContainer;
import com.objects.character.physical.genetics.GeneticContainer;
import com.objects.character.physical.species.Species;

public record PhysicalAppearance(Species species, GeneticContainer genetics, AugmentContainer augmentations) {

    public void toJson(JsonObject object){
        object.addProperty("species", species.getID());
        JsonObject gene = new JsonObject();
        genetics.toJson(gene);
        object.add("genetics", gene);
    }
    public static PhysicalAppearance fromJson(AugmentContainer container, JsonObject object){
        Species s = GeneManager.Species_Race.getSpecies(object.get("species").getAsString());
        GeneticContainer gc = GeneticContainer.fromJson(object.get("genetics").getAsJsonObject());

        return new PhysicalAppearance(s,gc,container);
    }

}
