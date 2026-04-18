package com.objects.character.physical.species;

import com.google.common.collect.ImmutableList;
import com.objects.character.physical.GeneManager;
import com.objects.character.physical.aspect.PhysicalAspect;
import com.objects.character.physical.genetics.GeneNode;
import com.utilities.IDisplayable;
import com.utilities.number.BoundInt;
import com.utilities.number.BoundInts;

import java.util.ArrayList;
import java.util.List;

public class Species implements IDisplayable, GeneNode<Species> {
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
