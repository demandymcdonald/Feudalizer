package com.objects.character.physical.species;

import com.google.common.collect.ImmutableList;
import com.objects.character.physical.GeneManager;
import com.objects.character.physical.aspect.AspectProperty;
import com.objects.character.physical.aspect.BodyPart;
import com.objects.character.physical.aspect.PhysicalAspect;
import com.objects.character.physical.genetics.GeneNode;
import com.utilities.Displayable;
import com.utilities.number.BoundInt;
import com.utilities.number.BoundInts;

public class Species implements Displayable, GeneNode<Species> {
    private final String id;
    private String name;
    private String description;
    private final ImmutableList<PhysicalAspect> validProperties;
    private final BoundInt sentience = BoundInts.Percent(false);
    private final BoundInt magic_capacity = BoundInts.Percent(false);
    public Species(String id, String name, String description, ImmutableList<PhysicalAspect> parts) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.validProperties = parts;
        GeneManager.Species_Race.registerSpecies(this);
    }


    public ImmutableList<PhysicalAspect> getValidProperties() {
        return validProperties;
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
}
