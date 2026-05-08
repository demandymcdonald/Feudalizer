package com.objects.character.physical.genetics;

import com.objects.character.physical.aspect.GeneProperty;
import com.utilities.number.bound_double.BoundDbl;
import com.utilities.number.bound_double.BoundDoubles;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;

public class SpectrumTrait extends Gene{
    public static final Supplier<BoundDbl> SPECTRUM_SIZE = () -> {return BoundDoubles.dbl2048(false,0);};

    private final TreeMap<Integer, SpectrumEntry> spectrum = new TreeMap<>();
    protected SpectrumTrait(GeneProperty property, String id, String name, String description, double position, double spontaneousChance, GeneStrength strength) {
        super(property, id, name, description, spontaneousChance, position, strength);
    }
    public Gene getGene(int value){
        return spectrum.floorEntry(value).getValue();
    }
    protected static class SpectrumEntry extends Gene{
        private final SpectrumTrait trait;
        private SpectrumEntry(SpectrumTrait trait, GeneProperty property, String id, String name, String description, double position, double spontaneousChance, GeneStrength strength) {
            super(property, id, name, description, spontaneousChance, position, strength);
            this.trait = trait;
        }
        public SpectrumTrait getTrait(){
            return trait;
        }
        protected static class EntryBuilder{
            private final SpectrumTrait.Builder parent;
            private final String id;
            private final String name;
            private final String description;
            private final int position;
            private GeneStrength strength = GeneStrength.RECESSIVE;
            public EntryBuilder(SpectrumTrait.Builder parent, int position, String id, String name, String description){
                this.parent = parent;
                this.id = id;
                this.name = name;
                this.description = description;
                this.position = Math.abs(position);
            }
            public EntryBuilder setStrength(GeneStrength strength){
                this.strength = strength;
                return this;
            }
            public Builder finish(){
                parent.acceptEntry(this);
                return parent;
            }
            protected SpectrumEntry build(SpectrumTrait t, GeneProperty property, double position, double spontaneousChance){
                return new SpectrumEntry(t,property,id,name,description,position,spontaneousChance,strength);
            }
        }

    }



    public static class Builder{
        private final GeneProperty parent;
        private final String id;
        private final String name;
        private final String description;
        private GeneStrength strength = GeneStrength.RECESSIVE;
        private final TreeMap<Integer, SpectrumEntry.EntryBuilder> spectrum = new TreeMap<>();
        private double position = -1;
        private double spontaneousChance = -1;
        public Builder(GeneProperty property, String id, String name, String description){
            this.parent = property;
            this.id = id;
            this.name = name;
            this.description = description;
        }
        public Builder setStrength(GeneStrength strength){
            this.strength = strength;
            return this;
        }
        public Builder setDefaultPos(double defaultPos){
            this.position = defaultPos;
            return this;
        }
        public Builder setSpontaneousChance(double spontaneousChance){
            this.spontaneousChance = spontaneousChance;
            return this;
        }
        public SpectrumEntry.EntryBuilder addEntry(int position, String id, String name, String description){
            if(spectrum.containsKey(position)){
                int newPos = position;
                while(spectrum.containsKey(newPos)){
                    newPos++;
                }
                position = newPos;
            }
            return new SpectrumEntry.EntryBuilder(this,position,id,name,description);
        }
        protected Builder acceptEntry(SpectrumEntry.EntryBuilder entry){
            spectrum.put(entry.position,entry);
            return this;
        }
        private TreeMap<Integer, SpectrumEntry> buildSpectrum(SpectrumTrait trait, double interval, double chance){

            TreeMap<Integer, SpectrumEntry> toReturn = new TreeMap<>();
            for(Map.Entry<Integer,SpectrumEntry.EntryBuilder> me : spectrum.entrySet()){
                int position = me.getKey();
                toReturn.put((int) Math.round(position * interval),
                        me.getValue().build(trait,parent,(position * interval) + (interval - 1),chance));
            }
            return toReturn;
        }
        public SpectrumTrait build(){
            int specSize = (int) SPECTRUM_SIZE.get().getMax();
            double interval = (double) specSize/spectrum.size();
            if(position == -1){
                position = (double) specSize /2;
            }
            if(spontaneousChance == -1){
                spontaneousChance = 100D/parent.getValidGenes().size();
            }
            SpectrumTrait t = new SpectrumTrait(parent,id,name,description,position,spontaneousChance,strength);
            t.spectrum.putAll(buildSpectrum(t,interval,spontaneousChance/spectrum.size()));
            return t;
        }
    }
}
