package com.objects.character.genetics;

import com.google.gson.JsonObject;
import com.utilities.Displayable;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

public class Race implements Displayable{
    private final String id;
    private final String name;
    private final String description;
    private final Map<GeneticTrait,Float> baseTraits;

    public Race(String id, String name, String description, Map<GeneticTrait,Float> base)  {
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

    public Map<GeneticTrait, Float> getBaseTraits() {
        return baseTraits;
    }

    @Override
    public String displayName() {
        return name;
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
    public static Race deserialize(JsonObject json){
        return GeneManager.getRace(json.get("id").getAsString());
    }
    @SuppressWarnings("unchecked")
    public static Race build(String id, String name, String description, Pair<GeneticTrait,Float>... baseTraits){
        Map<GeneticTrait,Float> commonTraitsMap = Map.ofEntries(baseTraits);
        return new Race(id, name, description, commonTraitsMap);
    }

}
