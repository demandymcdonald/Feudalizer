package com.objects.character.physical.aspect;

import com.google.common.collect.ImmutableList;
import com.objects.character.physical.genetics.GeneNode;
import com.objects.character.physical.species.Species;
import com.utilities.Displayable;

public record PhysicalAspect(BodyPart part, String id, String name, String description, ImmutableList<AspectProperty> validProperties) implements Displayable, GeneNode<PhysicalAspect> {
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
