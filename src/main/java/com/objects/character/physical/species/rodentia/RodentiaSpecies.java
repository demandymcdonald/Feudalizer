package com.objects.character.physical.species.rodentia;

import com.Global.*;
import com.objects.character.physical.species.Species;
import com.objects.character.physical.species.nomenclature.AnimalNameContainers;

import static com.objects.character.physical.species.rodentia.Sciuridae.*;
import static com.objects.character.physical.species.rodentia.Castoridae.*;
public class RodentiaSpecies {


        public static final Species PRAIRIE_DOG = Species.builder("prairie_dog", "Cynomys Ludovicianus Ludovicianus", "Prairie Dog")
                .addValidProperty(SCIURIDAE_BODY)
                .addValidProperty(SCIURIDAE_SKIN)
                .addValidProperty(SCIURIDAE_FUR)
                .addValidProperty(SCIURIDAE_HEAD)
                .addValidProperty(SCIURIDAE_EYES)
                .addValidProperty(SCIURIDAE_EARS)
                .addValidProperty(SCIURIDAE_NOSE)
                .addValidProperty(SCIURIDAE_MOUTH)
                .addValidProperty(SCIURIDAE_CHEEK_POUCHES)
                .addValidProperty(SCIURIDAE_NECK)
                .addValidProperty(SCIURIDAE_TORSO)
                .addValidProperty(SCIURIDAE_BACK)
                .addValidProperty(SCIURIDAE_TAIL)
                .addValidProperty(SCIURIDAE_FRONT_PAWS)
                .addValidProperty(SCIURIDAE_REAR_PAWS)
                .addValidProperty(SCIURIDAE_BRAIN)
                .addValidProperty(SCIURIDAE_VITAL_ORGANS)
                .addValidProperty(SCIURIDAE_IMMUNE_SYSTEM)
                .addValidProperty(SCIURIDAE_DIGESTIVE)
                .addValidProperty(SCIURIDAE_RESPIRATORY)
                .addValidProperty(SCIURIDAE_REPRODUCTIVE)
                .setMagicCapacity(0)
                .setSentience(37)
                .setNomenclature(AnimalNameContainers.PRAIRIE_DOG)
                .setLifeExpectancy(4)
                .setGenderLifeExpectancyRatio(1.0f)
                .setAgeOfMaturity(1)
                .setAgeOfElderly(3)
                .setAgeOfInfertilityMale(4)
                .setAgeOfInfertilityFemale(3)
                .addGenderRatio(SCIURIDAE_BODY_SIZE, 1.1f)
                .build();

        public static final Species NORTH_AMERICAN_BEAVER = Species.builder("north_american_beaver", "Castor Canadensis Canadensis", "North American Beaver")
                .addValidProperty(CASTORIDAE_BODY)
                .addValidProperty(CASTORIDAE_SKIN)
                .addValidProperty(CASTORIDAE_FUR)
                .addValidProperty(CASTORIDAE_HEAD)
                .addValidProperty(CASTORIDAE_EYES)
                .addValidProperty(CASTORIDAE_EARS)
                .addValidProperty(CASTORIDAE_NOSE)
                .addValidProperty(CASTORIDAE_MOUTH)
                .addValidProperty(CASTORIDAE_TEETH)
                .addValidProperty(CASTORIDAE_NECK)
                .addValidProperty(CASTORIDAE_TORSO)
                .addValidProperty(CASTORIDAE_BACK)
                .addValidProperty(CASTORIDAE_TAIL)
                .addValidProperty(CASTORIDAE_FRONT_PAWS)
                .addValidProperty(CASTORIDAE_REAR_PAWS)
                .addValidProperty(CASTORIDAE_BRAIN)
                .addValidProperty(CASTORIDAE_VITAL_ORGANS)
                .addValidProperty(CASTORIDAE_IMMUNE_SYSTEM)
                .addValidProperty(CASTORIDAE_DIGESTIVE)
                .addValidProperty(CASTORIDAE_RESPIRATORY)
                .addValidProperty(CASTORIDAE_REPRODUCTIVE)
                .setMagicCapacity(0)
                .setSentience(33)
                .setNomenclature(AnimalNameContainers.NORTH_AMERICAN_BEAVER)
                .setLifeExpectancy(10)
                .setGenderLifeExpectancyRatio(1.0f)
                .setAgeOfMaturity(2)
                .setAgeOfElderly(8)
                .setAgeOfInfertilityMale(10)
                .setAgeOfInfertilityFemale(9)
                .addGenderRatio(CASTORIDAE_BODY_SIZE, 1.05f)
                .build();
}
