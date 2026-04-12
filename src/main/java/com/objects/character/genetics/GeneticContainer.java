package com.objects.character.genetics;

import com.objects.character.sentient.SentientCharacter;
import com.objects.character.sentient.SentientSpecies;

import java.util.Map;

public record GeneticContainer<T extends SentientSpecies>(T species, Map<Race<T>,Integer> racial_makeup, Map<GeneticTrait<T>,TraitInstance> genetics) {




}
