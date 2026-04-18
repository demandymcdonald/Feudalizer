package com.objects.character.physical.genetics;

import com.objects.character.sentient.SentientSpecies;
import com.objects.character.physical.race.Race;

import java.util.Map;

public record GeneticContainer<T extends SentientSpecies>(T species, Map<Race<T>,Integer> racial_makeup, Map<Gene<T>,TraitInstance> genetics) {




}
