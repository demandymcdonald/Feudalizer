package com.objects.character.sentient;

import com.objects.character.genetics.GeneticContainer;

import java.util.List;
import java.util.Optional;

public class HybridSpecies<A extends SentientSpecies, B extends SentientSpecies> extends SentientSpecies {
    public HybridSpecies(String id, String name, String description, List<SentientSpecies> compatibleMating) {
        super(id, name, description, compatibleMating);
    }


    public static <A extends SentientSpecies, B extends SentientSpecies> Optional<HybridSpecies<A,B>> build(
            GeneticContainer<A> parentA, GeneticContainer<B> parentB){
        if (!canMate(parentA.species(), parentB.species())) {

        }

    }

    public static <A extends SentientSpecies, B extends SentientSpecies> boolean canMate(A a, B b){
        for(SentientSpecies s : a.getCompatibleMating()){
            if (b.getCompatibleMating().contains(s)) {
                return true;
            }
        }
        return false;
    }
}
