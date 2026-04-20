package com.objects.character.physical.species.artiodactyla;

import com.objects.character.physical.aspect.BodyPart;
import com.objects.character.physical.aspect.GeneProperty;
import com.objects.character.physical.aspect.PhysicalAspect;
import com.objects.character.physical.augment.AugmentSlot;
import com.objects.character.physical.species.creature.CreatureAugments;
import com.objects.character.physical.species.creature.CreatureGeneProperties;

public class Cervidae {
    public static final AugmentSlot CERVIDAE_PROSTHETIC_LEG = new AugmentSlot("cervidae_prosthetic_leg", "Prosthetic Leg", "");
    public static final AugmentSlot CERVIDAE_PROSTHETIC_HOOF = new AugmentSlot("cervidae_prosthetic_hoof", "Prosthetic Hoof", "");

    public static final GeneProperty CERVIDAE_FUR_COLOR = new GeneProperty("cervidae_fur_color", "Fur Color", "");
    public static final GeneProperty CERVIDAE_FUR_THICKNESS = new GeneProperty("cervidae_fur_thickness", "Fur Thickness", "");
    public static final GeneProperty CERVIDAE_FUR_LENGTH = new GeneProperty("cervidae_fur_length", "Fur Length", "");
    public static final GeneProperty CERVIDAE_EYE_COLOR = new GeneProperty("cervidae_eye_color", "Eye Color", "");
    public static final GeneProperty CERVIDAE_BODY_SIZE = new GeneProperty("cervidae_body_size", "Body Size", "");
    public static final GeneProperty CERVIDAE_BODY_SHAPE = new GeneProperty("cervidae_body_shape", "Body Shape", "");
    public static final GeneProperty CERVIDAE_NOSE_SHAPE = new GeneProperty("cervidae_nose_shape", "Nose Shape", "");
    public static final GeneProperty CERVIDAE_EAR_SHAPE = new GeneProperty("cervidae_ear_shape", "Ear Shape", "");
    public static final GeneProperty CERVIDAE_ANTLER_SHAPE = new GeneProperty("cervidae_antler_shape", "Antler Shape", "");
    public static final GeneProperty CERVIDAE_ANTLER_SIZE = new GeneProperty("cervidae_antler_size", "Antler Size", "");
    public static final GeneProperty CERVIDAE_DEWLAP_SIZE = new GeneProperty("cervidae_dewlap_size", "Dewlap Size", "");

    public static final PhysicalAspect CERVIDAE_EYES = PhysicalAspect.Builder(BodyPart.EYES, "cervidae_eyes", "Eyes", "")
            .addValidProperty(CERVIDAE_EYE_COLOR)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect CERVIDAE_HEAD = PhysicalAspect.Builder(BodyPart.HEAD, "cervidae_head", "Head", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect CERVIDAE_NOSE = PhysicalAspect.Builder(BodyPart.NOSE, "cervidae_nose", "Nose", "")
            .addValidProperty(CERVIDAE_NOSE_SHAPE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.LOSS)
            .build();
    public static final PhysicalAspect CERVIDAE_EARS = PhysicalAspect.Builder(BodyPart.EARS, "cervidae_ears", "Ears", "")
            .addValidProperty(CERVIDAE_EAR_SHAPE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CERVIDAE_MOUTH = PhysicalAspect.Builder(BodyPart.MOUTH, "cervidae_mouth", "Mouth", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CERVIDAE_ANTLERS = PhysicalAspect.Builder(BodyPart.HORN, "cervidae_antlers", "Antlers", "")
            .addValidProperty(CERVIDAE_ANTLER_SHAPE)
            .addValidProperty(CERVIDAE_ANTLER_SIZE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CERVIDAE_NECK = PhysicalAspect.Builder(BodyPart.NECK, "cervidae_neck", "Neck", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.COLLAR)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    // Moose-specific — only added to moose species entry
    public static final PhysicalAspect CERVIDAE_DEWLAP = PhysicalAspect.Builder(BodyPart.NECK, "cervidae_dewlap", "Dewlap", "")
            .addValidProperty(CERVIDAE_DEWLAP_SIZE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CERVIDAE_BODY = PhysicalAspect.Builder(BodyPart.WHOLE_BODY, "cervidae_body", "Body", "")
            .addValidProperty(CERVIDAE_BODY_SIZE)
            .addValidProperty(CERVIDAE_BODY_SHAPE)
            .addValidProperty(CreatureGeneProperties.COAT_PATTERN)
            .addValidProperty(CreatureGeneProperties.T_GENE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect CERVIDAE_FUR = PhysicalAspect.Builder(BodyPart.BODY_FUR, "cervidae_fur", "Fur", "")
            .addValidProperty(CERVIDAE_FUR_COLOR)
            .addValidProperty(CERVIDAE_FUR_THICKNESS)
            .addValidProperty(CERVIDAE_FUR_LENGTH)
            .addValidAugment(CreatureAugments.GROOMING)
            .addValidAugment(CreatureAugments.LOSS)
            .build();
    public static final PhysicalAspect CERVIDAE_TORSO = PhysicalAspect.Builder(BodyPart.TORSO, "cervidae_torso", "Torso", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect CERVIDAE_BACK = PhysicalAspect.Builder(BodyPart.BACK, "cervidae_back", "Back", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CERVIDAE_TAIL = PhysicalAspect.Builder(BodyPart.TAIL, "cervidae_tail", "Tail", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CERVIDAE_FRONT_HOOVES = PhysicalAspect.Builder(BodyPart.ARMS, "cervidae_front_hooves", "Front Hooves", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CERVIDAE_PROSTHETIC_HOOF)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.PARALYSIS)
            .build();
    public static final PhysicalAspect CERVIDAE_REAR_HOOVES = PhysicalAspect.Builder(BodyPart.LEGS, "cervidae_rear_hooves", "Rear Hooves", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CERVIDAE_PROSTHETIC_LEG)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.PARALYSIS)
            .build();
    public static final PhysicalAspect CERVIDAE_SKIN = PhysicalAspect.Builder(BodyPart.SKIN, "cervidae_skin", "Skin", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect CERVIDAE_VITAL_ORGANS = PhysicalAspect.Builder(BodyPart.VITAL_ORGAN, "cervidae_vital_organs", "Vital Organs", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CERVIDAE_BRAIN = PhysicalAspect.Builder(BodyPart.BRAIN, "cervidae_brain", "Brain", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidProperty(CreatureGeneProperties.INTELLECTUAL_DISABILITY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.MENTAL_ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.TAMING)
            .build();
    public static final PhysicalAspect CERVIDAE_IMMUNE_SYSTEM = PhysicalAspect.Builder(BodyPart.IMMUNE_SYSTEM, "cervidae_immune_system", "Immune System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect CERVIDAE_DIGESTIVE = PhysicalAspect.Builder(BodyPart.DIGESTIVE_ORGAN, "cervidae_digestive", "Digestive System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CERVIDAE_RESPIRATORY = PhysicalAspect.Builder(BodyPart.RESPIRATORY_ORGAN, "cervidae_respiratory", "Respiratory System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CERVIDAE_REPRODUCTIVE = PhysicalAspect.Builder(BodyPart.GENITAL, "cervidae_reproductive", "Reproductive Organ", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidProperty(CreatureGeneProperties.INFERTILITY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
}