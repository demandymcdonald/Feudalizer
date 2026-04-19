package com.objects.character.physical.species.rodentia;

import com.objects.character.physical.aspect.BodyPart;
import com.objects.character.physical.aspect.GeneProperty;
import com.objects.character.physical.aspect.PhysicalAspect;
import com.objects.character.physical.augment.AugmentSlot;
import com.objects.character.physical.species.creature.CreatureAugments;
import com.objects.character.physical.species.creature.CreatureGeneProperties;

public class Sciuridae {
    // Gene Properties
    public static final GeneProperty SCIURIDAE_FUR_COLOR = new GeneProperty("sciuridae_fur_color", "Fur Color", "");
    public static final GeneProperty SCIURIDAE_FUR_THICKNESS = new GeneProperty("sciuridae_fur_thickness", "Fur Thickness", "");
    public static final GeneProperty SCIURIDAE_EYE_COLOR = new GeneProperty("sciuridae_eye_color", "Eye Color", "");
    public static final GeneProperty SCIURIDAE_BODY_SIZE = new GeneProperty("sciuridae_body_size", "Body Size", "");
    public static final GeneProperty SCIURIDAE_TAIL_SHAPE = new GeneProperty("sciuridae_tail_shape", "Tail Shape", "");
    public static final GeneProperty SCIURIDAE_CHEEK_POUCH_SIZE = new GeneProperty("sciuridae_cheek_pouch_size", "Cheek Pouch Size", "");

    // Augments
    public static final AugmentSlot SCIURIDAE_PROSTHETIC_LEG = new AugmentSlot("sciuridae_prosthetic_leg", "Prosthetic Leg", "");
    public static final AugmentSlot SCIURIDAE_PROSTHETIC_ARM = new AugmentSlot("sciuridae_prosthetic_arm", "Prosthetic Paw", "");

    // Physical Aspects
    public static final PhysicalAspect SCIURIDAE_EYES = PhysicalAspect.Builder(BodyPart.EYES, "sciuridae_eyes", "Eyes", "")
            .addValidProperty(SCIURIDAE_EYE_COLOR)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect SCIURIDAE_HEAD = PhysicalAspect.Builder(BodyPart.HEAD, "sciuridae_head", "Head", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect SCIURIDAE_CHEEK_POUCHES = PhysicalAspect.Builder(BodyPart.MOUTH, "sciuridae_cheek_pouches", "Cheek Pouches", "")
            .addValidProperty(SCIURIDAE_CHEEK_POUCH_SIZE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect SCIURIDAE_MOUTH = PhysicalAspect.Builder(BodyPart.MOUTH, "sciuridae_mouth", "Mouth", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect SCIURIDAE_NOSE = PhysicalAspect.Builder(BodyPart.NOSE, "sciuridae_nose", "Nose", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.LOSS)
            .build();
    public static final PhysicalAspect SCIURIDAE_EARS = PhysicalAspect.Builder(BodyPart.EARS, "sciuridae_ears", "Ears", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect SCIURIDAE_NECK = PhysicalAspect.Builder(BodyPart.NECK, "sciuridae_neck", "Neck", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect SCIURIDAE_BODY = PhysicalAspect.Builder(BodyPart.WHOLE_BODY, "sciuridae_body", "Body", "")
            .addValidProperty(SCIURIDAE_BODY_SIZE)
            .addValidProperty(CreatureGeneProperties.COAT_PATTERN)
            .addValidProperty(CreatureGeneProperties.T_GENE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect SCIURIDAE_FUR = PhysicalAspect.Builder(BodyPart.BODY_FUR, "sciuridae_fur", "Fur", "")
            .addValidProperty(SCIURIDAE_FUR_COLOR)
            .addValidProperty(SCIURIDAE_FUR_THICKNESS)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.GROOMING)
            .addValidAugment(CreatureAugments.LOSS)
            .build();
    public static final PhysicalAspect SCIURIDAE_TAIL = PhysicalAspect.Builder(BodyPart.TAIL, "sciuridae_tail", "Tail", "")
            .addValidProperty(SCIURIDAE_TAIL_SHAPE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect SCIURIDAE_TORSO = PhysicalAspect.Builder(BodyPart.TORSO, "sciuridae_torso", "Torso", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect SCIURIDAE_BACK = PhysicalAspect.Builder(BodyPart.BACK, "sciuridae_back", "Back", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect SCIURIDAE_FRONT_PAWS = PhysicalAspect.Builder(BodyPart.ARMS, "sciuridae_front_paws", "Front Paws", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(SCIURIDAE_PROSTHETIC_ARM)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.PARALYSIS)
            .build();
    public static final PhysicalAspect SCIURIDAE_REAR_PAWS = PhysicalAspect.Builder(BodyPart.LEGS, "sciuridae_rear_paws", "Rear Paws", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(SCIURIDAE_PROSTHETIC_LEG)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.PARALYSIS)
            .build();
    public static final PhysicalAspect SCIURIDAE_SKIN = PhysicalAspect.Builder(BodyPart.SKIN, "sciuridae_skin", "Skin", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect SCIURIDAE_VITAL_ORGANS = PhysicalAspect.Builder(BodyPart.VITAL_ORGAN, "sciuridae_vital_organs", "Vital Organs", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect SCIURIDAE_BRAIN = PhysicalAspect.Builder(BodyPart.BRAIN, "sciuridae_brain", "Brain", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidProperty(CreatureGeneProperties.INTELLECTUAL_DISABILITY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.MENTAL_ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.TAMING)
            .build();
    public static final PhysicalAspect SCIURIDAE_IMMUNE_SYSTEM = PhysicalAspect.Builder(BodyPart.IMMUNE_SYSTEM, "sciuridae_immune_system", "Immune System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect SCIURIDAE_DIGESTIVE = PhysicalAspect.Builder(BodyPart.DIGESTIVE_ORGAN, "sciuridae_digestive", "Digestive System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect SCIURIDAE_RESPIRATORY = PhysicalAspect.Builder(BodyPart.RESPIRATORY_ORGAN, "sciuridae_respiratory", "Respiratory System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect SCIURIDAE_REPRODUCTIVE = PhysicalAspect.Builder(BodyPart.GENITAL, "sciuridae_reproductive", "Reproductive Organ", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidProperty(CreatureGeneProperties.INFERTILITY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
}