package com.objects.character.species.aspect;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.objects.character.species.genetics.Gene;
import com.objects.character.species.genetics.GeneProperty;
import com.utilities.Displayable;

public record PhysicalAspect(BodyPart part, String id, String name, String description,ImmutableList<GeneProperty> validProperties) implements Displayable {
    public PhysicalAspect {
        Aspects.registerAspect(this);
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
