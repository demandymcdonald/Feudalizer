package com.objects.character.physical.aspect;

import com.objects.character.physical.genetics.GeneNode;
import com.utilities.IDisplayable;

public record GeneProperty(String id, String name, String description) implements IDisplayable, GeneNode<GeneProperty> {

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
