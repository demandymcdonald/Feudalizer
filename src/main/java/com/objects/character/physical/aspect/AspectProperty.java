package com.objects.character.physical.aspect;

import com.objects.character.physical.genetics.Gene;
import com.objects.character.physical.genetics.GeneNode;
import com.utilities.Displayable;

public record AspectProperty(String id, String name, String description) implements Displayable, GeneNode<AspectProperty> {

    @Override
    public String getDisplayID() {
        return "";
    }

    @Override
    public String displayName() {
        return "";
    }

    @Override
    public String description() {
        return "";
    }
}
