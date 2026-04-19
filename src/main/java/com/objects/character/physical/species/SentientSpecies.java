package com.objects.character.physical.species;

import com.objects.character.physical.species.nomenclature.AnimalNameContainers;

import static com.objects.character.physical.species.primates.HumanGeneProperties.*;
import static com.objects.character.physical.species.primates.HumanPhysicalAspects.*;

public class SentientSpecies {
    public static final Species HUMAN = Species.builder("human", "Homo-Sapien-Sapien", "Human")
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
            .setNomenclature(AnimalNameContainers.HUMAN)
            .setLifeExpectancy(80)
            .setGenderLifeExpectancyRatio(0.95f)
            .setAgeOfMaturity(18)
            .setAgeOfElderly(65)
            .setAgeOfInfertilityMale(70)
            .setAgeOfInfertilityFemale(51)
            .addGenderRatio(HUMAN_HEIGHT, 1.07f)
            .addGenderRatio(HUMAN_WEIGHT, 1.25f)
            .addGenderRatio(HUMAN_BODY_HAIR, 1.6f)
            .addGenderRatio(HUMAN_BREASTS, 0.05f)
            .build();
    public void init(){

    }
}
