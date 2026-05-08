package com.objects.character.physical.genetics;

import com.base.reference.DMEReference;
import com.objects.character.LivingCreature;
import com.objects.character.physical.GeneManager;
import com.objects.character.physical.IGeneNode;
import com.objects.character.physical.aspect.BodyPart;
import com.objects.character.physical.aspect.GeneProperty;
import com.objects.character.physical.aspect.PhysicalAspect;
import com.objects.character.physical.species.Species;
import com.utilities.IDisplayable;
import com.utilities.number.bound_double.BoundDbl;
import com.utilities.number.bound_double.BoundDoubles;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public abstract class Gene implements IDisplayable, IGeneNode<Gene> {
    private final GeneProperty geneProperty; //the thing on the PhysicalAspect that this Gene affects
    private final BoundDbl chance; //Chance of spontaneous development (0-100 Double)
    private final GeneStrength strength;// Dominant, Recessive, or Strong. Determines the strength of the gene.
    private final String fullID;
    private final String id;
    private final String name;
    private final String description;
    public Gene(GeneProperty property, String id, String name, String description, double chance, double value, GeneStrength strength) {
        this.geneProperty = property;
        this.fullID = property.getID() + "gene:" + id;
        this.id = "gene:" + id;
        this.name = name;
        this.description = description;
        this.chance = BoundDoubles.percent(false,chance);
        this.strength = strength;
        GeneManager.Genetics.register(this);
    }
    public double getChance() {
        return chance.get();
    }
    public GeneStrength getStrength() {
        return strength;
    }

    @Override
    public final String getID() {
        return id;
    }

    @Override
    public final String getDisplayID() {
        return fullID;
    }

    @Override
    public final String getDescription() {
        return description;
    }

    @Override
    public final String getDisplayName() {
        return name;
    }
    public final boolean isCanBeLatent() {
        return strength.canBeLatent();
    }
    public final GeneProperty getProperty() {
        return geneProperty;
    }
    public GeneInstance getDefaultInstance(DMEReference<? extends LivingCreature<?>> owner){
        return new GeneInstance(this,owner);
    }
    public GeneInstance getDefaultInstance(LocalDate start, LocalDate end){
        return new GeneInstance(this,start,end);
    }
    @Override
    public final Set<PhysicalAspect> getValidAspects() {
        return new HashSet<>(geneProperty.getValidAspects());
    }
    @Override
    public final Set<Species> getValidSpecies() {
        return new HashSet<>(geneProperty.getValidSpecies());
    }
    @Override
    public Set<BodyPart> getValidBodyParts() {
        return new HashSet<>(geneProperty.getValidBodyParts());
    }
}
