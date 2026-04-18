package com.objects.character.physical.augment;

import com.objects.character.physical.GeneManager;
import com.objects.character.physical.aspect.BodyPart;
import com.objects.character.physical.aspect.PhysicalAspect;
import com.objects.character.physical.IGeneNode;
import com.objects.character.physical.species.Species;
import com.utilities.IDisplayable;
import com.utilities.caching.CachingSupplier;

import java.util.HashSet;
import java.util.Set;

public class AugmentSlot implements IDisplayable, IGeneNode<AugmentSlot> {
    private final String id;
    private final String name;
    private final String description;
    private final CachingSupplier<Set<PhysicalAspect>> validAspects = new CachingSupplier<>(this::buildValidAspects);
    private final boolean canSubInherit;
    //private final Supplier<Set<BodyPart>> validBodyParts = Suppliers.memoize(this::buildParts);
    public AugmentSlot(String id, String name, String description) {
        this.id = "aug_slot:"+id;
        this.name = name;
        this.description = description;
        this.canSubInherit = true;
        GeneManager.Augments.registerAugmentSlot(this);
    }
    public AugmentSlot(String id, String name, String description, boolean canSubInherit) {
        this.id = "aug_slot:"+id;
        this.name = name;
        this.description = description;
        this.canSubInherit = canSubInherit;
        GeneManager.Augments.registerAugmentSlot(this);
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
    public Set<Augment> getValidAugments(){
        return GeneManager.Augments.getAugments(this);
    }
    public boolean canSubInherit() {
        return canSubInherit;
    }
    @Override
    public Set<PhysicalAspect> getValidAspects(){
        return new HashSet<>(validAspects.get());
    }

    @Override
    public Set<BodyPart> getValidBodyParts() {
        Set<BodyPart> parts = new HashSet<>();
        if(canSubInherit()){
            for(PhysicalAspect aspect : getValidAspects()){
                parts.addAll(aspect.getValidBodyParts());
            }
        } else {
            for(PhysicalAspect aspect : getValidAspects()){
                parts.add(aspect.getMainBodyPart());
            }
        }
        return parts;
    }

    @Override
    public Set<Species> getValidSpecies(){
        //Think about caching this if performance becomes a problem
        Set<Species> parts = new HashSet<>();
        for(PhysicalAspect aspect : getValidAspects()){
            parts.addAll(aspect.getValidSpecies());
        }
        return parts;
    }
    private Set<PhysicalAspect> buildValidAspects(){
        return GeneManager.getConnectionsWhereTarget(this,PhysicalAspect.class);
    }


}
