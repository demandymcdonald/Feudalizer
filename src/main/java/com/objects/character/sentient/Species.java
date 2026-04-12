package com.objects.character.sentient;

import java.util.List;

public class Species {
    public static class Human extends SentientSpecies {
        public Human() {
            super("human", "Homo-Sapien-Sapien", "A normal human", List.of());
        }
    }

}
