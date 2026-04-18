package com.objects.character.physical;

import com.base.reference.DMEReference;
import com.objects.character.LivingCreature;
import com.objects.character.physical.aspect.BodyPart;
import com.objects.character.physical.aspect.GeneProperty;
import com.objects.character.physical.aspect.PhysicalAspect;
import com.objects.character.physical.species.Species;

import java.util.Set;

public interface IPhysicalObject {
    Set<BodyPart> getValidBodyParts();
    Set<Species> getValidSpecies();
    Set<PhysicalAspect> getValidAspects();
    default <C extends LivingCreature<C>> boolean canHave(C creature){

    }
}
