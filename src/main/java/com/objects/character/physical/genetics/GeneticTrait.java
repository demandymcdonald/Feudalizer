package com.objects.character.physical.genetics;

import com.Global.*;
import com.objects.character.physical.aspect.BodyPart;
import com.objects.character.physical.aspect.GeneProperty;

import java.util.Set;

public class GeneticTrait extends Gene{
    public GeneticTrait(GeneProperty property, String id, String name, String description, double chance, GeneStrength strength) {
        super(property, id, name, description, chance, strength);
    }
}
