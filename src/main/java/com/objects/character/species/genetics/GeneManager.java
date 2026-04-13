package com.objects.character.species.genetics;

import com.objects.character.sentient.SentientSpecies;
import com.objects.character.species.race.Race;
import com.objects.character.species.race.RacesEast;
import com.objects.character.species.race.RacesWest;

import java.util.*;

public class GeneManager {
    private static final Map<String, Gene<?>> traits = new HashMap<>();
    private static final Map<SpectrumTrait.Type, TreeMap<Integer, SpectrumTrait>> Spectrums = new HashMap<>();
    private static final Map<String, Race<?>> races = new HashMap<>();
    public static void registerGene(String id, Gene<?> t){
        if (t instanceof SpectrumTrait st) {
            registerSpectrum(st);
        }
        traits.put(t.getDisplayID(), t);
    }
    private static void registerSpectrum(SpectrumTrait trait){
        Spectrums.computeIfAbsent(trait.getType(), k -> new TreeMap<>()).put(trait.getLow(), trait);
    }

    public static void registerRace(Race<?> race){
        races.put(race.getDisplayID(), race);
    }
    public static <T extends SentientSpecies> Gene<T> getGeneSpectrum(SpectrumTrait.Type type, int value){
        return Spectrums.get(type).floorEntry(value).getValue();
    }
    public static <T extends SentientSpecies> Gene<T> getGene(String id){
        return traits.get(id);
    }
    public static <T extends SentientSpecies> Race<T> getRace(String id){
        return races.get(id);
    }
    public static void init(){
        Genes.init();
        RacesEast.init();
        RacesWest.init();
    }


}
