package com.objects.character.physical.augment;

import com.objects.character.physical.aspect.BodyPart;
import com.objects.character.physical.genetics.GeneNode;
import com.utilities.IDisplayable;

public class Augment implements IDisplayable,GeneNode<Augment> {
    private final BodyPart bodyPart;
    private final String id;
    private final String name;
    private final String description;

    public Augment(BodyPart bodyPart, String id, String name, String description) {
        this.bodyPart = bodyPart;
        this.id = "aug_"+id;
        this.name = name;
        this.description = description;
    }
    @Override
    public String getID() {
        return id;
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
    public BodyPart getBodyPart() {
        return bodyPart;
    }
}
