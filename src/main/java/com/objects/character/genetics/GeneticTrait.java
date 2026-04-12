package com.objects.character.genetics;

import com.google.gson.JsonObject;
import com.utilities.Displayable;

public class GeneticTrait implements Displayable {
    private final TraitGroup group;
    private final String id;
    private final String name;
    private final String description;
    private final Inheritance strength;
    private final int globalMin;
    private final int globalMax;

        public GeneticTrait(TraitGroup group, String id, String name, String description, Inheritance strength, int globalMin, int globalMax){
            this.group = group;
            this.id = id;
            this.name = name;
            this.description = description;
            this.strength = strength;
            this.globalMin = globalMin;
            this.globalMax = globalMax;
            GeneManager.registerGene(id,this);
        }



    public enum Inheritance {
        Dominant,
        Recessive,
        Strong_Dominant
    }
    public enum TraitGroup {
        Hair_Color,
        Eye_Color,
        Skin_Color,
        Height_Tendency, //Tend to be taller or shorter
        Weight_Tendency, //Tend to be heavier or lighter
        HairQuality(false),
        EyeColor(false),
        Freckles,
        Disease(false),
        Aging(false),
        ;
        private final boolean isExclusive;

        TraitGroup() {
            this.isExclusive = true;
        }

        TraitGroup(boolean isExclusive) {
            this.isExclusive = isExclusive;
        }

        public boolean isExclusive() {
            return isExclusive;
        }
    }

    @Override
    public String getDisplayID() {
        return id;
    }

    @Override
    public String displayName() {
        return name;
    }

    @Override
    public String description() {
        return description;
    }

    public Inheritance getStrength() {
        return strength;
    }

    public TraitGroup getGroup() {
        return group;
    }
    public JsonObject serialize(){
        JsonObject object = new JsonObject();
        object.addProperty("type", "geneticTrait");
        object.addProperty("id", id);
        return object;
    }
    public static GeneticTrait deserialize(JsonObject json){
            return GeneManager.getGene(json.get("id").getAsString());
    }
}
