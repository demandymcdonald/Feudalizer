package com.objects.character.physical.species.nomenclature;

import com.Global.*;
import com.objects.character.physical.GeneManager;
import org.apache.commons.lang3.tuple.Triple;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;

public record NameContainer(NomenEntry animalKingdom, NomenEntry animalPhylum, NomenEntry animalClass, NomenEntry animalOrder, NomenEntry animalFamily, NomenEntry animalGenus, NomenEntry animalSpecies, @Nullable NomenEntry animalSubspecies) {

    public String getTrinomial(){
        return animalGenus.name() + " " + animalSpecies.name() + " " + animalSubspecies.name();
    }
    public String getSpeciesName(){
        return animalSpecies.name();
    }
    public String getBinomial(){
        if(animalSubspecies == null) return animalGenus.name() + " " + animalSpecies.name();
        return animalSpecies.name() + " " + animalSubspecies.name();
    }


    public static class Builder{
        private Map<NomenEntry.Type,NomenEntry> entries = new EnumMap<>(NomenEntry.Type.class);
        private Map<NomenEntry.Type, Triple<String,String,String>> toBuild = new EnumMap<>(NomenEntry.Type.class);
        public Builder addKingdom(String id){
            entries.put(NomenEntry.Type.KINGDOM, GeneManager.Species_Race.getNomenEntry(id));
            return this;
        }
        public Builder addKingdom(String id, String name, String description){
            toBuild.put(NomenEntry.Type.KINGDOM, Triple.of(id, name, description));
            return this;
        }
        public Builder addKingdom(NomenEntry kingdom){
            if(!validate(kingdom, Nomenclature.Type.KINGDOM)) throw new IllegalArgumentException(kingdom +" must be a kingdom");
            entries.put(NomenEntry.Type.KINGDOM, kingdom);
            return this;
        }
        public Builder addPhylum(String id){
            entries.put(NomenEntry.Type.PHYLUM, GeneManager.Species_Race.getNomenEntry(id));
            return this;
        }
        public Builder addPhylum(String id, String name, String description){
            toBuild.put(NomenEntry.Type.PHYLUM, Triple.of(id, name, description));
            return this;
        }
        public Builder addPhylum(NomenEntry phylum){
            if(!validate(phylum, Nomenclature.Type.PHYLUM)) throw new IllegalArgumentException(phylum +" must be a phylum");
            entries.put(NomenEntry.Type.PHYLUM, phylum);
            return this;
        }
        public Builder addClass(String id){
            entries.put(NomenEntry.Type.CLASS, GeneManager.Species_Race.getNomenEntry(id));
            return this;
        }
        public Builder addClass(String id, String name, String description){
            toBuild.put(NomenEntry.Type.CLASS, Triple.of(id, name, description));
            return this;
        }
        public Builder addClass(NomenEntry classEntry){
            if(!validate(classEntry, Nomenclature.Type.CLASS)) throw new IllegalArgumentException(classEntry +" must be a class");
            entries.put(NomenEntry.Type.CLASS, classEntry);
            return this;
        }
        public Builder addOrder(String id){
            entries.put(NomenEntry.Type.ORDER, GeneManager.Species_Race.getNomenEntry(id));
            return this;
        }
        public Builder addOrder(String id, String name, String description){
            toBuild.put(NomenEntry.Type.ORDER, Triple.of(id, name, description));
            return this;
        }
        public Builder addOrder(NomenEntry order){
            if(!validate(order, Nomenclature.Type.ORDER)) throw new IllegalArgumentException(order +" must be an order");
            entries.put(NomenEntry.Type.ORDER, order);
            return this;
        }
        public Builder addFamily(String id){
            entries.put(NomenEntry.Type.FAMILY, GeneManager.Species_Race.getNomenEntry(id));
            return this;
        }
        public Builder addFamily(String id, String name, String description){
            toBuild.put(NomenEntry.Type.FAMILY, Triple.of(id, name, description));
            return this;
        }
        public Builder addFamily(NomenEntry family){
            if(!validate(family, Nomenclature.Type.FAMILY)) throw new IllegalArgumentException(family +" must be a family");
            entries.put(NomenEntry.Type.FAMILY, family);
            return this;
        }
        public Builder addGenus(String id){
            entries.put(NomenEntry.Type.GENUS, GeneManager.Species_Race.getNomenEntry(id));
            return this;
        }
        public Builder addGenus(String id, String name, String description){
            toBuild.put(NomenEntry.Type.GENUS, Triple.of(id, name, description));
            return this;
        }
        public Builder addGenus(NomenEntry genus){
            if(!validate(genus, Nomenclature.Type.GENUS)) throw new IllegalArgumentException(genus +" must be a genus");
            entries.put(NomenEntry.Type.GENUS, genus);
            return this;
        }
        public Builder addSpecies(String id){
            entries.put(NomenEntry.Type.SPECIES, GeneManager.Species_Race.getNomenEntry(id));
            return this;
        }
        public Builder addSpecies(String id, String name, String description){
            toBuild.put(NomenEntry.Type.SPECIES, Triple.of(id, name, description));
            return this;
        }
        public Builder addSpecies(NomenEntry species){
            if(!validate(species, Nomenclature.Type.SPECIES)) throw new IllegalArgumentException(species +" must be a species");
            entries.put(NomenEntry.Type.SPECIES, species);
            return this;
        }
        public Builder addSubSpecies(String id){
            entries.put(NomenEntry.Type.SUBSPECIES, GeneManager.Species_Race.getNomenEntry(id));
            return this;
        }
        public Builder addSubSpecies(String id, String name, String description){
            toBuild.put(NomenEntry.Type.SUBSPECIES, Triple.of(id, name, description));
            return this;
        }
        public Builder addSubSpecies(NomenEntry subSpecies){
            if(!validate(subSpecies, Nomenclature.Type.SUBSPECIES)) throw new IllegalArgumentException(subSpecies +" must be a subspecies");
            entries.put(NomenEntry.Type.SUBSPECIES, subSpecies);
            return this;
        }
        public NameContainer build(){
            for(NomenEntry.Type type : NomenEntry.Type.values()){
                if(entries.containsKey(type)){
                    continue;
                }
                if(toBuild.containsKey(type)){
                    Triple<String,String,String> triple = toBuild.get(type);
                    Nomenclature.Type pt = type.getParent();
                    NomenEntry parent = null;
                    if(pt != null){
                        parent = entries.get(pt);
                    }
                    entries.put(type, NomenEntry.of(type, parent, triple.getLeft(), triple.getMiddle(), triple.getRight()));
                } else {
                    throw new IllegalArgumentException("Missing entry for type: " + type);
                }
            }
            return new NameContainer(entries.get(NomenEntry.Type.KINGDOM), entries.get(NomenEntry.Type.PHYLUM), entries.get(NomenEntry.Type.CLASS), entries.get(NomenEntry.Type.ORDER), entries.get(NomenEntry.Type.FAMILY), entries.get(NomenEntry.Type.GENUS), entries.get(NomenEntry.Type.SPECIES), entries.get(NomenEntry.Type.SUBSPECIES));
        }
        public static boolean validate(NomenEntry container, Nomenclature.Type type){
            return container.type() == type;
        }
    }
}
