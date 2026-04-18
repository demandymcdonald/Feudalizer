package com.objects.character.physical.aspect;

import com.google.common.collect.ImmutableList;
import com.objects.character.physical.GeneManager;
import com.objects.character.physical.augment.Augment;
import com.objects.character.physical.genetics.GeneNode;
import com.utilities.IDisplayable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public record PhysicalAspect(BodyPart part, String id, String name, String description, ImmutableList<GeneProperty> validProperties, ImmutableList<Augment> validAugments) implements IDisplayable, GeneNode<PhysicalAspect> {
    public PhysicalAspect {
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
    public ImmutableList<GeneProperty> getValidProperties() {
        return validProperties;
    }
    public ImmutableList<Augment> getValidAugments() {
        return validAugments;
    }
    public static Builder Builder(BodyPart part, String id, String name, String description) {
        return new Builder(id,name,description,part);
    }

    public static class Builder {
        private final String id;
        private final String name;
        private final String description;
        private final BodyPart bodyPart;
        private List<GeneProperty> validProperties = new ArrayList<>();
        private List<Augment> validAugments = new ArrayList<>();
        protected Builder(String id, String name, String description, BodyPart bodyPart) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.bodyPart = bodyPart;
        }
        public Builder addValidProperty(GeneProperty aspectProperty) {
            validProperties.add(aspectProperty);
            return this;
        }
        public Builder addValidAugment(Augment augment) {
            validAugments.add(augment);
            return this;
        }
        public PhysicalAspect build() {
            return new PhysicalAspect(bodyPart,id,name,description,ImmutableList.copyOf(validProperties),ImmutableList.copyOf(validAugments));
        }
    }
}
