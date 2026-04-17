package com.objects.character.species.race;

import com.google.gson.JsonObject;
import com.objects.character.sentient.SentientSpecies;
import com.objects.character.species.genetics.GeneManager;
import com.objects.character.species.genetics.Gene;
import com.utilities.IDisplayable;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

public class Race<T extends SentientSpecies> implements IDisplayable {
    private final String id;
    private final String name;
    private final String description;
    private final Map<Gene<T>,Float> baseTraits;

    public Race(String id, String name, String description, Map<Gene<T>,Float> base)  {
        this.id = id;
        this.name = name;
        this.description = description;
        this.baseTraits = base;
        GeneManager.registerRace(this);
    }
    //note on the common traits: I should add some randomness to the spectrum values if we only use these (parent 0's for example).
    @Override
    public String getDisplayID() {
        return id;
    }

    public Map<Gene<T>, Float> getBaseTraits() {
        return baseTraits;
    }

    @Override
    public String getDisplayName() {
        return name;
    }

    @Override
    public String getDescription() {
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
    public static <T extends SentientSpecies> Race<T> build(String id, String name, String description, Pair<Gene<T>,Float>... baseTraits){
        Map<Gene<T>,Float> commonTraitsMap = Map.ofEntries(baseTraits);
        return new Race<T>(id, name, description, commonTraitsMap);
    }

}
