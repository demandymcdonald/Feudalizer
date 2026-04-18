package com.objects.character.physical.genetics;

import com.objects.character.physical.aspect.GeneProperty;
import com.objects.character.physical.aspect.PhysicalAspect;
import com.utilities.Displayable;
import com.utilities.number.BoundDbl;

public abstract class Gene implements Displayable, GeneNode<Gene> {
    private final PhysicalAspect parent; // Parent physical aspect (human skin)
    private final GeneProperty geneProperty; //the thing on the PhysicalAspect that this Gene affects
    private final BoundDbl chance; //Chance of spontaneous development (0-100 Double)
    private final GeneStrength strength;// Dominant, Recessive, or Strong. Determines the strength of the gene.
    private final String id;
    private final String name;
    private final String description;
    private final boolean canBeLatent;
    public Gene(PhysicalAspect parent, GeneProperty property, String id, String name, String description, double chance, GeneStrength strength, boolean canBeLatent) {
        this.parent = parent;
        this.geneProperty = property;
        this.id = parent.getDisplayID() + id;
        this.name = name;
        this.description = description;
        this.chance = Math.clamp(chance, 0, 100);
        this.strength = strength;
        this.canBeLatent = canBeLatent;
    }

    public double getChance() {
        return chance;
    }

    public GeneStrength getStrength() {
        return strength;
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

    public boolean isCanBeLatent() {
        return canBeLatent;
    }
    public PhysicalAspect getAspect() {
        return parent;
    }
    public GeneProperty getProperty() {
        return geneProperty;
    }
}
