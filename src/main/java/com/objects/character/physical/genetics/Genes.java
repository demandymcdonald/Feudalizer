package com.objects.character.physical.genetics;

public class Genes {
    public static final Gene redhead = new Gene(Gene.TraitGroup.Hair_Color,"hair_red","Red Hair","", Gene.Inheritance.Recessive, 0,1028);
    public static final Gene albinism = new Gene(Gene.TraitGroup.Hair_Color,"hair_blonde","Blonde Hair","", Gene.Inheritance.Recessive, 0,1028);

    public static void init(){

    }
}
