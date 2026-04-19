package com.objects.character.physical;

import com.Global;
import com.base.timeline.change.multi.TLMap;
import com.google.gson.JsonObject;
import com.objects.character.physical.augment.AugmentInstance;
import com.objects.character.physical.augment.AugmentSlot;
import com.objects.character.physical.genetics.GeneticContainer;
import com.objects.character.physical.species.Species;

import java.time.LocalDate;
import java.util.Map;

public class PhysicalAppearance {
    private final Species species;
    private final GeneticContainer genetics;
    private TLMap<AugmentSlot, AugmentInstance> augmentations;
    public PhysicalAppearance(Species species, GeneticContainer genetics, TLMap<AugmentSlot, AugmentInstance> augmentations) {
        this.species = species;
        this.genetics = genetics;
        this.augmentations = augmentations;
    }
    public PhysicalAppearance(Species species, GeneticContainer genetics) {
        this.species = species;
        this.genetics = genetics;
    }
    public Species getSpecies() {
        return species;
    }

    public GeneticContainer getGenetics() {
        return genetics;
    }

    public Map<AugmentSlot, AugmentInstance> getAugmentations() {
        LocalDate date = Global.getDate();
        return augmentations.getWhere((k,m) -> {
            return m.start().isBefore(date) && m.expiration().isAfter(date);
        });
    }

    public void toJson(JsonObject object){
        object.addProperty("species", species.getID());
        JsonObject gene = new JsonObject();
        genetics.toJson(gene);
        object.add("genetics", gene);
    }
    public void internalAugmentSet(TLMap<AugmentSlot, AugmentInstance> augmentations){
        this.augmentations = augmentations;
    }
    public TLMap<AugmentSlot, AugmentInstance> internalAugmentsGet(){
        return augmentations;
    }
    public static PhysicalAppearance fromJson(JsonObject object){
        Species s = GeneManager.Species_Race.getSpecies(object.get("species").getAsString());
        GeneticContainer gc = GeneticContainer.fromJson(object.get("genetics").getAsJsonObject());
        return new PhysicalAppearance(s,gc);
    }

}
