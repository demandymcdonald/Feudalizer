package com.objects.character.species.genetics;

import com.Feudalizer;
import com.objects.character.species.aspect.PhysicalAspect;
import com.objects.culture.object.instance.TenetInstance;
import com.utilities.Displayable;

import java.util.ArrayList;
import java.util.List;

public abstract class Gene implements Displayable {
    private final PhysicalAspect parent; // Parent physical aspect (human skin)
    private final GeneProperty geneProperty; //the thing on the PhysicalAspect that this Gene affects
    private final double chance; //Chance of spontaneous development (0-100 Double)
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

    public List<TraitInstance> makeChildTrait(List<TraitInstance> parentA, List<TraitInstance> parentB){
        List<TraitInstance> validate = new ArrayList<TraitInstance>(parentA);
        validate.addAll(parentB);
        for (TraitInstance traitInstance : validate) {
            if (traitInstance.trait().getParent() != this.getParent()){
                parentA.remove(traitInstance);
                parentB.remove(traitInstance);
                Feudalizer.LOGGER.error("Gene trait: {} was passed into {}", traitInstance.trait().getDisplayID(), this.getDisplayID());
            }
        }
        return doMakeChildTrait(parentA, parentB);
    }
    protected abstract List<TraitInstance> doMakeChildTrait(List<TraitInstance> parentA, List<TraitInstance> parentB);


    public PhysicalAspect getParent() {
        return parent;
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
    public GeneProperty getGeneProperty() {
        return geneProperty;
    }
}
