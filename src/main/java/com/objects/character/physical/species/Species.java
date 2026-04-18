package com.objects.character.physical.species;

import com.google.common.collect.ImmutableList;
import com.objects.character.physical.GeneManager;
import com.objects.character.physical.aspect.BodyPart;
import com.objects.character.physical.aspect.PhysicalAspect;
import com.objects.character.physical.IGeneNode;
import com.utilities.IDisplayable;
import com.utilities.caching.CachingSupplier;
import com.utilities.number.BoundInt;
import com.utilities.number.BoundInts;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Species implements IDisplayable, IGeneNode<Species> {
    private final String id;
    private String name;
    private String trinomialNomenclature;
    private String description;
    private final ImmutableList<PhysicalAspect> validProperties;
    private final BoundInt sentience = BoundInts.Percent(false);
    private final BoundInt magic_capacity = BoundInts.Percent(false);
    private Species(String id, String trinominal, String name, String description, ImmutableList<PhysicalAspect> parts) {
        this.id = id;
        this.name = name;
        this.trinomialNomenclature = trinominal;
        this.description = description;
        this.validProperties = parts;
        GeneManager.Species_Race.registerSpecies(this);
    }

    private final CachingSupplier<Set<BodyPart>> validParts = new CachingSupplier<>(this::buildValidParts);
    private Set<BodyPart> buildValidParts(){
        Set<BodyPart> parts = new HashSet<>();
        for(PhysicalAspect aspect : validProperties){
            parts.addAll(aspect.getValidBodyParts());
        }
        return parts;
    }
    public BoundInt getMagicCapacity() {
        return magic_capacity;
    }

    public BoundInt getSentience() {
        return sentience;
    }

    public ImmutableList<PhysicalAspect> getValidProperties() {
        return validProperties;
    }
    @Override
    public String getDisplayID() {
        return id;
    }

    @Override
    public String getDisplayName() {
        return name;
    }
    public String getTrinomialNomenclature() {
        return trinomialNomenclature;
    }
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getID() {
        return id;
    }
    public static Builder builder(String id, String trinomial, String name, String description) {
        return new Builder(id, trinomial, name, description);
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
        return new HashSet<>(validProperties);
    }
    public static class Builder {
        private final String id;
        private final String name;
        private final String trinomialNomenclature;
        private final String description;
        private final List<PhysicalAspect> validProperties = new ArrayList<>();
        private final BoundInt sentience = BoundInts.Percent(false);
        private final BoundInt magic_capacity = BoundInts.Percent(false);
        public Builder(String id,String trinomial, String name, String description) {
            this.id = id;
            this.trinomialNomenclature = trinomial;
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
        public Species build() {
            Species species = new Species(id,name,trinomialNomenclature,description, ImmutableList.copyOf(validProperties));
            species.getSentience().set(sentience.get());
            species.getMagicCapacity().set(magic_capacity.get());
            return species;
        }

    }
}
