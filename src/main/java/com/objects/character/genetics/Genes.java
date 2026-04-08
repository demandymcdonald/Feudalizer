package com.objects.character.genetics;

import com.Global.*;

import java.util.Map;

public class Genes {
    public static final GeneticTrait redhead = new GeneticTrait(GeneticTrait.TraitGroup.Hair_Color,"hair_red","Red Hair","", GeneticTrait.Inheritance.Recessive, 0,1028);
    public static final GeneticTrait albinism = new GeneticTrait(GeneticTrait.TraitGroup.Hair_Color,"hair_blonde","Blonde Hair","", GeneticTrait.Inheritance.Recessive, 0,1028);

    public static void init(){

    }
}
