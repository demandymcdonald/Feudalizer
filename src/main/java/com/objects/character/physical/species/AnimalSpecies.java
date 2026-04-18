package com.objects.character.physical.species;

import static com.objects.character.physical.species.canine.WolfPhysicalAspects.*;

public class AnimalSpecies {

    public static final Species WOLF = Species.builder("wolf", "Canis Lupus", "Wolf", "")
            .addValidProperty(WOLF_BODY)
            .addValidProperty(WOLF_SKIN)
            .addValidProperty(WOLF_FUR)
            .addValidProperty(WOLF_HEAD)
            .addValidProperty(WOLF_EYES)
            .addValidProperty(WOLF_EARS)
            .addValidProperty(WOLF_NOSE)
            .addValidProperty(WOLF_MOUTH)
            .addValidProperty(WOLF_NECK)
            .addValidProperty(WOLF_TORSO)
            .addValidProperty(WOLF_BACK)
            .addValidProperty(WOLF_TAIL)
            .addValidProperty(WOLF_FRONT_PAWS)
            .addValidProperty(WOLF_REAR_PAWS)
            .addValidProperty(WOLF_BRAIN)
            .addValidProperty(WOLF_VITAL_ORGANS)
            .addValidProperty(WOLF_IMMUNE_SYSTEM)
            .addValidProperty(WOLF_DIGESTIVE)
            .addValidProperty(WOLF_RESPIRATORY)
            .addValidProperty(WOLF_REPRODUCTIVE)
            .setMagicCapacity(70)
            .setSentience(42)
            .build();
}
