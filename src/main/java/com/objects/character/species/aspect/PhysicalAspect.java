package com.objects.character.species.aspect;

import com.google.common.collect.ImmutableList;
import com.objects.character.species.genetics.GeneProperty;
import com.utilities.IDisplayable;

public record PhysicalAspect(BodyPart part, String id, String name, String description,ImmutableList<GeneProperty> validProperties) implements IDisplayable {
    public PhysicalAspect {
        Aspects.registerAspect(this);
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
}
