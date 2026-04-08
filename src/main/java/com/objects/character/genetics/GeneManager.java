package com.objects.character.genetics;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class GeneManager {
    private static final Map<String, GeneticTrait> traits = new HashMap<>();
    private static final Map<SpectrumTrait.Type, TreeMap<Integer, SpectrumTrait>> Spectrums = new HashMap<>();
    private static final Map<String, Race> races = new HashMap<>();
    public static void registerGene(String id, GeneticTrait t){
        if (t instanceof SpectrumTrait st) {
            registerSpectrum(st);
        }
        traits.put(t.getID(), t);
    }
    private static void registerSpectrum(SpectrumTrait trait){
        Spectrums.computeIfAbsent(trait.getType(), k -> new TreeMap<>()).put(trait.getLow(), trait);
    }

    public static void registerRace(Race race){
        races.put(race.getID(), race);
    }
    public static GeneticTrait getGeneSpectrum(SpectrumTrait.Type type, int value){
        return Spectrums.get(type).floorEntry(value).getValue();
    }
    public static GeneticTrait getGene(String id){
        return traits.get(id);
    }
    public static Race getRace(String id){
        return races.get(id);
    }
    public static void init(){
        Genes.init();
        RacesEast.init();
        RacesWest.init();
    }

    public static GeneticContainer haveChild(GeneticContainer parentA, GeneticContainer parentB){
        Map<Race,Integer> racial_makeupC = handleGenetics(parentA, parentB);
        return new GeneticContainer(racial_makeupC, new HashMap<>());
    }
    private static Map<Race,Integer> handleGenetics(GeneticContainer parentA, GeneticContainer parentB){
        Map<Race,Integer> racial_makeupC = new HashMap<>();
        Map<Race,Integer> racial_makeupA = new HashMap<>(parentA.racial_makeup());
        Map<Race,Integer> racial_makeupB = new HashMap<>(parentB.racial_makeup());
        for (Map.Entry<Race,Integer> entry : racial_makeupA.entrySet()) {
            int val;
            if (racial_makeupB.containsKey(entry.getKey())){
                val = entry.getValue();
                val += racial_makeupB.get(entry.getKey());
                racial_makeupC.put(entry.getKey(), val/2);
                racial_makeupB.remove(entry.getKey());
            } else {
                racial_makeupC.put(entry.getKey(), entry.getValue()/2);
            }
        }
        for (Map.Entry<Race,Integer> entry : parentB.racial_makeup().entrySet()) {
            racial_makeupC.put(entry.getKey(), entry.getValue()/2);
        }
        return racial_makeupC;
    }
    public static Map<GeneticTrait, TraitInstance> getGeneticTraits(GeneticContainer parentA, GeneticContainer parentB){
        Multimap<GeneticTrait.TraitGroup, Pair<GeneticTrait, TraitInstance>> traitMap = HashMultimap.create();
        for (Map.Entry<GeneticTrait, TraitInstance> entry : parentA.genetics().entrySet()) {
            traitMap.put(entry.getKey().getGroup(), Pair.of(entry.getKey(), entry.getValue()));
        }
        for (Map.Entry<GeneticTrait, TraitInstance> entry : parentB.genetics().entrySet()) {
            traitMap.put(entry.getKey().getGroup(), Pair.of(entry.getKey(), entry.getValue()));
        }
        Map<GeneticTrait, TraitInstance> toReturn = new HashMap<>();
        for (GeneticTrait.TraitGroup group : traitMap.keySet()) {
            List<Pair<GeneticTrait, TraitInstance>> groupList = new ArrayList<>(traitMap.get(group));
            Map<GeneticTrait, TraitInstance> groupMap = Map.ofEntries(groupList.toArray(new Pair[0]));
            //TODO finish this logic
            boolean isExclusive = group.isExclusive();
        }



    }
}
