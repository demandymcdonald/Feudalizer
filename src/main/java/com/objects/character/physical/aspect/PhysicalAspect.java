package com.objects.character.physical.aspect;

import com.google.common.collect.ImmutableList;
import com.objects.character.physical.GeneManager;
import com.objects.character.physical.augment.AugmentSlot;
import com.objects.character.physical.IGeneNode;
import com.objects.character.physical.species.Species;
import com.utilities.IDisplayable;
import com.utilities.caching.CachingSupplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PhysicalAspect implements IDisplayable, IGeneNode<PhysicalAspect> {
    private final BodyPart part;
    private final String id;
    private final String name;
    private final String description;
    private final boolean canSubInherit;
    private final ImmutableList<GeneProperty> validProperties;
    private final ImmutableList<AugmentSlot> validAugmentSlots;
    private final CachingSupplier<Set<Species>> validSpeciesSupplier = new CachingSupplier<>(this::buildValidSpecies);
    private final CachingSupplier<Set<BodyPart>> validBodyParts = new CachingSupplier<>(this::buildValidBodyParts);

    private PhysicalAspect(BodyPart part, String id, String name, String description, boolean canSubInherit, ImmutableList<GeneProperty> validProperties, ImmutableList<AugmentSlot> validAugmentSlots) {
        this.part = part;
        this.id = id;
        this.name = name;
        this.description = description;
        this.validProperties = validProperties;
        this.validAugmentSlots = validAugmentSlots;
        this.canSubInherit = canSubInherit;
        GeneManager.Physical_Aspect.register(this);
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
    @Override
    public String getID() {
        return id;
    }
    public BodyPart getMainBodyPart() {
        return part;
    }
    public ImmutableList<GeneProperty> getValidProperties() {
        return validProperties;
    }
    public ImmutableList<AugmentSlot> getValidAugmentSlots() {
        return validAugmentSlots;
    }
    private Set<Species> buildValidSpecies(){
        return GeneManager.getConnectionsWhereTarget(this,Species.class);
    }

    public boolean canSubInherit() {
        return canSubInherit;
    }

    @Override
    public Set<BodyPart> getValidBodyParts() {
        return validBodyParts.get();
    }
    @Override
    public Set<Species> getValidSpecies() {
        return validSpeciesSupplier.get();
    }
    private Set<BodyPart> buildValidBodyParts(){
        return GeneManager.getConnectionsWhereTarget(this, BodyPart.class);
    }
    @Override
    public Set<PhysicalAspect> getValidAspects() {
        return Set.of(this);
    }
    public static Builder Builder(BodyPart part, String id, String name, String description) {
        return new Builder(id,name,description,part);
    }
    public static class Builder {
        private final String id;
        private final String name;
        private final String description;
        private final BodyPart bodyPart;
        private final List<GeneProperty> validProperties = new ArrayList<>();
        private final List<AugmentSlot> validAugmentSlots = new ArrayList<>();
        private boolean canSubInherit = true;
        protected Builder(String id, String name, String description, BodyPart bodyPart) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.bodyPart = bodyPart;
        }
        public Builder canSubInherit(boolean bool){
            canSubInherit = bool;
            return this;
        }
        public Builder addValidProperty(GeneProperty aspectProperty) {
            validProperties.add(aspectProperty);
            return this;
        }
        public Builder addValidAugment(AugmentSlot augmentSlot) {
            validAugmentSlots.add(augmentSlot);
            return this;
        }
        public PhysicalAspect build() {
            return new PhysicalAspect(bodyPart,id,name,description,canSubInherit,ImmutableList.copyOf(validProperties),ImmutableList.copyOf(validAugmentSlots));
        }
    }
}
