package com.objects.character.physical.augment;

import com.objects.character.physical.GeneManager;
import com.objects.character.physical.IPhysicalObject;
import com.objects.character.physical.aspect.BodyPart;
import com.objects.character.physical.aspect.PhysicalAspect;
import com.objects.character.physical.species.Species;
import com.utilities.IDisplayable;
import com.utilities.id.StringIdentifiable;

import java.util.Set;

public class Augment implements IDisplayable, StringIdentifiable, IPhysicalObject {
    private final AugmentSlot slot;
    private final String id;
    private final String fullID;
    private final String name;
    private final String description;
    public Augment(AugmentSlot slot, String id, String name, String description){
        this.slot = slot;
        this.id = "aug:"+id;
        this.fullID = slot.getID()+":aug:"+this.id;
        this.name = name;
        this.description = description;
        GeneManager.Augments.registerAugment(this);
    }
    @Override
    public String getDisplayID() {
        return id;
    }

    @Override
    public String getDisplayName() {
        return fullID;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getID() {
        return id;
    }
    public AugmentSlot getSlot() {
        return slot;
    }

    @Override
    public Set<BodyPart> getValidBodyParts() {
        return slot.getValidBodyParts();
    }

    @Override
    public Set<Species> getValidSpecies() {
        return slot.getValidSpecies();
    }

    @Override
    public Set<PhysicalAspect> getValidAspects() {
        return slot.getValidAspects();
    }
}
