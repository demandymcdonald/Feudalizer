package com.objects.character.genetics;

import java.util.Map;

public record GeneticContainer(Map<Race,Integer> racial_makeup, Map<GeneticTrait,TraitInstance> genetics) {




}
