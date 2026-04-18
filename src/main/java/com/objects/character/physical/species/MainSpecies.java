package com.objects.character.physical.species;

import com.objects.character.sentient.SentientSpecies;

import java.util.List;

public class MainSpecies {
    public static class Human extends SentientSpecies {
        public Human() {
            super("human", "Homo-Sapien-Sapien", "A normal human", List.of());
        }
    }

}
