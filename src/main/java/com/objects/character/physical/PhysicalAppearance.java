package com.objects.character.physical;

import com.base.timeline.change.multi.TLMap;
import com.google.gson.JsonObject;
import com.objects.character.physical.augment.AugmentInstance;
import com.objects.character.physical.augment.AugmentSlot;
import com.objects.character.physical.genetics.GeneticContainer;
import com.objects.character.physical.species.Species;
import org.apache.commons.lang3.mutable.MutableObject;

public record PhysicalAppearance(Species species, GeneticContainer genetics, MutableObject<TLMap<AugmentSlot, AugmentInstance>> augmentations) {

    public void toJson(JsonObject object){
        object.addProperty("species", species.getID());
        JsonObject gene = new JsonObject();
        genetics.toJson(gene);
        object.add("genetics", gene);
    }
    public void setAugments(TLMap<AugmentSlot, AugmentInstance> augmentations){
        this.augmentations.setValue(augmentations);
    }
    public TLMap<AugmentSlot, AugmentInstance> internalAugments(){
        return augmentations.getValue();
    }
    public static PhysicalAppearance fromJson(JsonObject object){
        Species s = GeneManager.Species_Race.getSpecies(object.get("species").getAsString());
        GeneticContainer gc = GeneticContainer.fromJson(object.get("genetics").getAsJsonObject());
        return new PhysicalAppearance(s,gc,new MutableObject<>());
    }

}
