package com.objects.character.physical.species.nomenclature;

import com.utilities.IDisplayable;
import com.utilities.id.StringIdentifiable;

import javax.annotation.Nullable;

public interface Nomenclature extends IDisplayable,StringIdentifiable {

    enum Type implements Nomenclature {
        KINGDOM(null,"kingdom","Kingdom","The broadest division of life. Animals, plants, fungi, etc."),
        PHYLUM(KINGDOM,"phylum","Phylum"," Major body plan or structural blueprint. Vertebrates, arthropods, mollusks, etc."),
        CLASS(PHYLUM,"class","Class"," Major group of organisms. Mammals, birds, reptiles, amphibians, etc."),
        ORDER(CLASS,"order","Order"," Major group of organisms. Carnivora, Amphibia, Mammalia, etc."),
        FAMILY(ORDER,"family","Family"," Major group of organisms. Canidae, Felidae, Amphibia, etc."),
        GENUS(FAMILY,"genus","Genus"," Major group of organisms. Canis, Felis, Amphibia, etc."),
        SPECIES(GENUS,"species","Species"," Major group of organisms. Canis lupus, Felis catus, Amphibia africana, etc."),
        SUBSPECIES(SPECIES,"subspecies","Subspecies","A distinct population within a species that can still interbreed with others of the same species.");
        private final @Nullable Type parent;
        private final String id;
        private final String name;
        private final String description;
        private Type(@Nullable Type parent, String id, String name, String description){
            this.parent = parent;
            this.id = "nomen_type:"+id;
            this.name = name;
            this.description =description;
        }
        public @Nullable Type getParent(){
              return parent;
        }
        @Override
        public String getDisplayID() {
            return id;
        }

        @Override
        public String getDisplayName() {
            return name;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public String getID() {
            return id;
        }
    }
}
