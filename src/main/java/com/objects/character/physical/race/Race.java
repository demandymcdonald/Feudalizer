package com.objects.character.physical.race;

import com.google.gson.JsonObject;
import com.objects.character.physical.IGeneNode;
import com.objects.character.physical.species.Species;
import com.objects.character.sentient.SentientSpecies;
import com.objects.character.physical.GeneManager;
import com.objects.character.physical.genetics.Gene;
import com.utilities.Displayable;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

public class Race implements Displayable, IGeneNode<Race> {
    private final String id;
    private final String name;
    private final String description;
    private final Map<Gene,Float> baseTraits;
    private final Species species;
    public Race(Species species, String id, String name, String description, Map<Gene,Float> base)  {
        this.id = id;
        this.name = name;
        this.description = description;
        this.baseTraits = base;
        this.species = species;
        GeneManager.Species_Race.registerRace(this);
    }
    //note on the common traits: I should add some randomness to the spectrum values if we only use these (parent 0's for example).
    @Override
    public String getDisplayID() {
        return id;
    }

    public Map<Gene, Float> getBaseTraits() {
        return baseTraits;
    }

    @Override
    public String displayName() {
        return name;
    }
    public Species getSpecies() {
        return species;
    }
    @Override
    public String description() {
        return description;
    }
    public JsonObject serialize(){
        JsonObject object = new JsonObject();
        object.addProperty("type", "race");
        object.addProperty("id", id);
        return object;
    }
    public static <T extends SentientSpecies> Race<T> deserialize(JsonObject json){
        return GeneManager.getRace(json.get("id").getAsString());
    }
    @SuppressWarnings("unchecked")
    public static <T extends SentientSpecies> Race<T> build(String id, String name, String description, Pair<Gene,Float>... baseTraits){
        Map<Gene,Float> commonTraitsMap = Map.ofEntries(baseTraits);
        return new Race<T>(id, name, description, commonTraitsMap);
    }

}
