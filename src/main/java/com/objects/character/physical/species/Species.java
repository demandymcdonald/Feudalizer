package com.objects.character.physical.species;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.objects.character.physical.GeneManager;
import com.objects.character.physical.aspect.BodyPart;
import com.objects.character.physical.aspect.GeneProperty;
import com.objects.character.physical.aspect.PhysicalAspect;
import com.objects.character.physical.IGeneNode;
import com.objects.character.physical.species.nomenclature.NameContainer;
import com.utilities.IDisplayable;
import com.utilities.caching.CachingSupplier;
import com.utilities.number.bound_int.BoundInt;
import com.utilities.number.bound_int.BoundInts;

import java.util.*;

public class Species implements IDisplayable, IGeneNode<Species> {
    private final String id;
    private String name;
    private String description;
    private final NameContainer nameContainer;
    private final SpeciesProperties properties;
    private Species(String id, String name, String description, NameContainer container, SpeciesProperties properties) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.properties = properties;
        this.nameContainer = container;
        GeneManager.Species_Race.registerSpecies(this);
    }

    private final CachingSupplier<Set<BodyPart>> validParts = new CachingSupplier<>(this::buildValidParts);
    private Set<BodyPart> buildValidParts(){
        Set<BodyPart> parts = new HashSet<>();
        for(PhysicalAspect aspect : properties.parts()){
            parts.addAll(aspect.getValidBodyParts());
        }
        return parts;
    }
    public int getMagicCapacity() {
        return properties.magicCapacity();
    }
    public int getSentience() {
        return properties.sentience();
    }

    public ImmutableList<PhysicalAspect> getValidProperties() {
        return properties.parts();
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
    public String getTrinomial(){
        return nameContainer.getTrinomial();
    }
    public String getBinomial(){
        return nameContainer.getBinomial();
    }
    public String getSpeciesName(){
        return nameContainer.getSpeciesName();
    }
    @Override
    public String getID() {
        return id;
    }
    public static Builder builder(String id, String name, String description) {
        return new Builder(id, name, description);
    }
    @Override
    public Set<BodyPart> getValidBodyParts() {
        return validParts.get();
    }
    @Override
    public Set<Species> getValidSpecies() {
        return Set.of(this);
    }
    @Override
    public Set<PhysicalAspect> getValidAspects() {
        return new HashSet<>(properties.parts());
    }
    public static class Builder {
        private final String id;
        private final String name;
        private final String description;
        private final List<PhysicalAspect> validProperties = new ArrayList<>();
        private final Map<GeneProperty,Float> maleToFemaleRatio = new HashMap<>();
        private final BoundInt sentience = BoundInts.Percent(false);
        private final BoundInt magic_capacity = BoundInts.Percent(false);
        private NameContainer nameContainer;
        private int lifeExpectancy = 10;
        private float maleToFemaleLERatio = 1;
        private int ageOfMaturity = 5;
        private int ageOfElderly = 8;
        private int ageOfInfertilityMale = 9;
        private int ageOfInfertilityFemale = 8;
        public Builder(String id,String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }
        public Builder addValidProperty(PhysicalAspect physicalAspect) {
            validProperties.add(physicalAspect);
            return this;
        }
        public Builder setSentience(int sentience) {
            this.sentience.set(sentience);
            return this;
        }
        public Builder setMagicCapacity(int magicCapacity) {
            this.magic_capacity.set(magicCapacity);

            return this;
        }
        public Builder setLifeExpectancy(int lifeExpectancy) {
            this.lifeExpectancy = lifeExpectancy;
            return this;
        }
        public Builder setGenderLifeExpectancyRatio(float maleToFemaleLERatio) {
            this.maleToFemaleLERatio = maleToFemaleLERatio;
            return this;
        }
        public Builder setAgeOfMaturity(int ageOfMaturity) {
            this.ageOfMaturity = ageOfMaturity;
            return this;
        }
        public Builder setAgeOfElderly(int ageOfElderly) {
            this.ageOfElderly = ageOfElderly;
            return this;
        }
        public Builder setAgeOfInfertilityMale(int ageOfInfertilityMale) {
            this.ageOfInfertilityMale = ageOfInfertilityMale;
            return this;
        }
        public Builder setAgeOfInfertilityFemale(int ageOfInfertilityFemale) {
            this.ageOfInfertilityFemale = ageOfInfertilityFemale;
            return this;
        }
        public Builder setNomenclature(NameContainer name){
            this.nameContainer = name;
            return this;
        }
        public Builder addGenderRatio(GeneProperty property, float ratio){
            maleToFemaleRatio.put(property,ratio);
            return this;
        }
        public SpeciesProperties buildProperties(){
            return new SpeciesProperties(sentience.get(),magic_capacity.get(),lifeExpectancy,maleToFemaleLERatio,ageOfMaturity,ageOfElderly,ageOfInfertilityMale,ageOfInfertilityFemale,ImmutableList.copyOf(validProperties),ImmutableMap.copyOf(maleToFemaleRatio));
        }
        public Species build() {
            return new Species(id,name,description,nameContainer,buildProperties());
        }

    }
    public record SpeciesProperties(int sentience, int magicCapacity, int lifeExpectancy, float maleToFemaleLERatio, int ageOfMaturity, int ageOfElderly,
                                    int ageOfInfertilityMale, int ageOfInfertilityFemale, ImmutableList<PhysicalAspect> parts, ImmutableMap<GeneProperty,Float> maleToFemaleRatio) {
    }
}
