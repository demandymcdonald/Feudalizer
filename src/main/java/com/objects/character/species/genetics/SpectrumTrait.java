package com.objects.character.species.genetics;

import com.google.gson.JsonObject;

public class SpectrumTrait extends Gene {
    //TODO: write once I have the shape in;

    public enum Type {

        Skin_Color(TraitGroup.Skin_Color,0,512),
        Height(TraitGroup.Height_Tendency,0,512),
        Weight(TraitGroup.Weight_Tendency,0,256),
        Hair_Blonde_Black(TraitGroup.Hair_Color,0,512),
        Hair_Curly(TraitGroup.HairQuality,0,256),
        Hair_Thickness(TraitGroup.HairQuality,0,256),
        Eye_Blue_Brown(TraitGroup.EyeColor,0,256),
        Eye_Color_Gray_Green(TraitGroup.EyeColor,0,256),
        Hair_Gray_Age(TraitGroup.Aging,0,256), //0 being at 18, 256 being at 65 or over
        Male_Baldness_Age(TraitGroup.Aging,0,256),
        ;
        private TraitGroup group;
        private int globalMin;
        private int globalMax;

        Type(TraitGroup group,int globalMin, int globalMax) {
            this.group = group;
            this.globalMin = globalMin;
            this.globalMax = globalMax;
        }

        public TraitGroup getGroup() {
            return group;
        }
        public int getGlobalMin() {
            return globalMin;
        }
        public int getGlobalMax() {
            return globalMax;
        }
    }

    private final int low;
    private final Type parent;

    public SpectrumTrait(Type group, String id, String name, String description, Inheritance strength, int low) {
        super(group.getGroup(), id, name, description, strength, group.getGlobalMin(), group.getGlobalMax());
        this.low = low;
        this.parent = group;

    }


    public Type getType() {
        return parent;
    }

    public int getLow() {
        return low;
    }
    @Override
    public JsonObject serialize(){
        JsonObject object = super.serialize();
        object.addProperty("type", "spectrumTrait");
        object.addProperty("id", getDisplayID());
        return object;
    }
}
