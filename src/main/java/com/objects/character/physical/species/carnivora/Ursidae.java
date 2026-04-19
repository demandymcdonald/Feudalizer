package com.objects.character.physical.species.carnivora;

import com.objects.character.physical.aspect.BodyPart;
import com.objects.character.physical.aspect.GeneProperty;
import com.objects.character.physical.aspect.PhysicalAspect;
import com.objects.character.physical.augment.AugmentSlot;
import com.objects.character.physical.species.creature.CreatureAugments;
import com.objects.character.physical.species.creature.CreatureGeneProperties;

public class Ursidae {
    public static final AugmentSlot URSIDAE_PROSTHETIC_LEG = new AugmentSlot("ursidae_prosthetic_leg", "Prosthetic Leg", "");
    public static final AugmentSlot URSIDAE_PROSTHETIC_ARM = new AugmentSlot("ursidae_prosthetic_arm", "Prosthetic Paw", "");

    public static final GeneProperty URSIDAE_FUR_COLOR = new GeneProperty("ursidae_fur_color", "Fur Color", "");
    public static final GeneProperty URSIDAE_FUR_THICKNESS = new GeneProperty("ursidae_fur_thickness", "Fur Thickness", "");
    public static final GeneProperty URSIDAE_FUR_LENGTH = new GeneProperty("ursidae_fur_length", "Fur Length", "");
    public static final GeneProperty URSIDAE_EYE_COLOR = new GeneProperty("ursidae_eye_color", "Eye Color", "");
    public static final GeneProperty URSIDAE_BODY_SIZE = new GeneProperty("ursidae_body_size", "Body Size", "");
    public static final GeneProperty URSIDAE_BODY_SHAPE = new GeneProperty("ursidae_body_shape", "Body Shape", "");
    public static final GeneProperty URSIDAE_NOSE_SHAPE = new GeneProperty("ursidae_nose_shape", "Nose Shape", "");
    public static final GeneProperty URSIDAE_EAR_SHAPE = new GeneProperty("ursidae_ear_shape", "Ear Shape", "");
    
    public static final PhysicalAspect URSIDAE_EYES = PhysicalAspect.Builder(BodyPart.EYES, "ursidae_eye", "Eyes", "")
            .addValidProperty(URSIDAE_EYE_COLOR)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect URSIDAE_HEAD = PhysicalAspect.Builder(BodyPart.HEAD, "ursidae_head", "Head", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect URSIDAE_NOSE = PhysicalAspect.Builder(BodyPart.NOSE, "ursidae_nose", "Nose", "")
            .addValidProperty(URSIDAE_NOSE_SHAPE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.LOSS)
            .build();
    public static final PhysicalAspect URSIDAE_EARS = PhysicalAspect.Builder(BodyPart.EARS, "ursidae_ears", "Ears", "")
            .addValidProperty(URSIDAE_EAR_SHAPE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect URSIDAE_MOUTH = PhysicalAspect.Builder(BodyPart.MOUTH, "ursidae_mouth", "Mouth", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect URSIDAE_NECK = PhysicalAspect.Builder(BodyPart.NECK, "ursidae_neck", "Neck", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.COLLAR)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect URSIDAE_BODY = PhysicalAspect.Builder(BodyPart.WHOLE_BODY, "ursidae_body", "Body", "")
            .addValidProperty(URSIDAE_BODY_SIZE)
            .addValidProperty(URSIDAE_BODY_SHAPE)
            .addValidProperty(CreatureGeneProperties.COAT_PATTERN)
            .addValidProperty(CreatureGeneProperties.T_GENE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect URSIDAE_FUR = PhysicalAspect.Builder(BodyPart.BODY_FUR, "ursidae_fur", "Fur", "")
            .addValidProperty(URSIDAE_FUR_COLOR)
            .addValidProperty(URSIDAE_FUR_THICKNESS)
            .addValidProperty(URSIDAE_FUR_LENGTH)
            .addValidAugment(CreatureAugments.GROOMING)
            .addValidAugment(CreatureAugments.LOSS)
            .build();
    public static final PhysicalAspect URSIDAE_TORSO = PhysicalAspect.Builder(BodyPart.TORSO, "ursidae_torso", "Torso", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect URSIDAE_BACK = PhysicalAspect.Builder(BodyPart.BACK, "ursidae_back", "Back", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect URSIDAE_FRONT_PAWS = PhysicalAspect.Builder(BodyPart.ARMS, "ursidae_front_paws", "Front Paws", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(URSIDAE_PROSTHETIC_ARM)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.PARALYSIS)
            .build();
    public static final PhysicalAspect URSIDAE_REAR_PAWS = PhysicalAspect.Builder(BodyPart.LEGS, "ursidae_rear_paws", "Rear Paws", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(URSIDAE_PROSTHETIC_LEG)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.PARALYSIS)
            .build();
    public static final PhysicalAspect URSIDAE_SKIN = PhysicalAspect.Builder(BodyPart.SKIN, "ursidae_skin", "Skin", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect URSIDAE_VITAL_ORGANS = PhysicalAspect.Builder(BodyPart.VITAL_ORGAN, "ursidae_vital_organs", "Vital Organs", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect URSIDAE_BRAIN = PhysicalAspect.Builder(BodyPart.BRAIN, "ursidae_brain", "Brain", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidProperty(CreatureGeneProperties.INTELLECTUAL_DISABILITY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.MENTAL_ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.TAMING)
            .build();
    public static final PhysicalAspect URSIDAE_IMMUNE_SYSTEM = PhysicalAspect.Builder(BodyPart.IMMUNE_SYSTEM, "ursidae_immune_system", "Immune System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect URSIDAE_DIGESTIVE = PhysicalAspect.Builder(BodyPart.DIGESTIVE_ORGAN, "ursidae_digestive", "Digestive System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect URSIDAE_RESPIRATORY = PhysicalAspect.Builder(BodyPart.RESPIRATORY_ORGAN, "ursidae_respiratory", "Respiratory System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect URSIDAE_REPRODUCTIVE = PhysicalAspect.Builder(BodyPart.GENITAL, "ursidae_reproductive", "Reproductive Organ", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidProperty(CreatureGeneProperties.INFERTILITY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
}