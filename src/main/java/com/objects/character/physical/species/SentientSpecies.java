package com.objects.character.physical.species;

import static com.objects.character.physical.species.human.HumanPhysicalAspects.*;

public class SentientSpecies {
    public static final Species HUMAN = Species.builder("human","Homo-Sapien-Sapien","Human","")
        .addValidProperty(HUMAN_BODY)
        .addValidProperty(HUMAN_BODY_SKIN)
        .addValidProperty(HUMAN_HEAD_HAIR)
        .addValidProperty(HUMAN_FACIAL_HAIR)
        .addValidProperty(HUMAN_BODY_HAIR_ASPECT)
        .addValidProperty(HUMAN_EYES)
        .addValidProperty(HUMAN_HEAD)
        .addValidProperty(HUMAN_BODY_FACE)
        .addValidProperty(HUMAN_NECK)
        .addValidProperty(HUMAN_NOSE)
        .addValidProperty(HUMAN_MOUTH)
        .addValidProperty(HUMAN_EARS)
        .addValidProperty(HUMAN_TEETH)
        .addValidProperty(HUMAN_TORSO)
        .addValidProperty(HUMAN_BACK)
        .addValidProperty(HUMAN_CHEST)
        .addValidProperty(HUMAN_SHOULDERS)
        .addValidProperty(HUMAN_PELVIS)
        .addValidProperty(HUMAN_REPRODUCTIVE)
        .addValidProperty(HUMAN_ARMS)
        .addValidProperty(HUMAN_HANDS)
        .addValidProperty(HUMAN_LEGS)
        .addValidProperty(HUMAN_FEET)
        .addValidProperty(HUMAN_VITAL_ORGANS)
        .addValidProperty(HUMAN_BRAIN)
        .addValidProperty(HUMAN_IMMUNE_SYSTEM)
        .addValidProperty(HUMAN_DIGESTIVE)
        .addValidProperty(HUMAN_RESPIRATORY)
        .setMagicCapacity(100)
        .setSentience(100)
        .build();
    public void init(){

    }
}
