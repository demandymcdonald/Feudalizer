package com.objects.character.physical.species.canine;

import com.objects.character.physical.aspect.BodyPart;
import com.objects.character.physical.aspect.PhysicalAspect;
import com.objects.character.physical.species.creature.CreatureAugments;
import com.objects.character.physical.species.creature.CreatureGeneProperties;


public class WolfPhysicalAspects {
    public static final PhysicalAspect WOLF_EYES = PhysicalAspect.Builder(BodyPart.EYES, "wolf_eye", "Wolf Eyes", "")
            .addValidProperty(CanineGeneProperties.CANINE_EYE_COLOR)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect WOLF_HEAD = PhysicalAspect.Builder(BodyPart.HEAD, "wolf_head", "Wolf Head", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect WOLF_NOSE = PhysicalAspect.Builder(BodyPart.NOSE, "wolf_nose", "Wolf Nose", "")
            .addValidProperty(CanineGeneProperties.CANINE_NOSE_SHAPE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.LOSS)
            .build();
    public static final PhysicalAspect WOLF_EARS = PhysicalAspect.Builder(BodyPart.EARS, "wolf_ears", "Wolf Ears", "")
            .addValidProperty(CanineGeneProperties.CANINE_EAR_SHAPE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect WOLF_MOUTH = PhysicalAspect.Builder(BodyPart.MOUTH, "wolf_mouth", "Wolf Mouth", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect WOLF_NECK = PhysicalAspect.Builder(BodyPart.NECK, "wolf_neck", "Wolf Neck", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CanineAugments.CANINE_COLLAR)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect WOLF_BODY = PhysicalAspect.Builder(BodyPart.WHOLE_BODY, "wolf_body", "Wolf Body", "")
            .addValidProperty(CanineGeneProperties.CANINE_BODY_SIZE)
            .addValidProperty(CanineGeneProperties.CANINE_BODY_SHAPE)
            .addValidProperty(CreatureGeneProperties.COAT_PATTERN)
            .addValidProperty(CreatureGeneProperties.T_GENE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect WOLF_FUR = PhysicalAspect.Builder(BodyPart.BODY_FUR, "wolf_fur", "Wolf Fur", "")
            .addValidProperty(CanineGeneProperties.CANINE_FUR_COLOR)
            .addValidProperty(CanineGeneProperties.CANINE_FUR_THICKNESS)
            .addValidProperty(CanineGeneProperties.CANINE_FUR_LENGTH)
            .addValidAugment(CanineAugments.CANINE_GROOMING)
            .addValidAugment(CreatureAugments.LOSS)
            .build();
    public static final PhysicalAspect WOLF_TAIL = PhysicalAspect.Builder(BodyPart.TAIL, "wolf_tail", "Wolf Tail", "")
            .addValidProperty(CanineGeneProperties.CANINE_TAIL_SHAPE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect WOLF_TORSO = PhysicalAspect.Builder(BodyPart.TORSO, "wolf_torso", "Wolf Torso", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect WOLF_BACK = PhysicalAspect.Builder(BodyPart.BACK, "wolf_back", "Wolf Back", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect WOLF_FRONT_PAWS = PhysicalAspect.Builder(BodyPart.HANDS, "wolf_front_paws", "Wolf Front Paws", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CanineAugments.CANINE_PROSTHETIC_ARM)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.PARALYSIS)
            .build();
    public static final PhysicalAspect WOLF_REAR_PAWS = PhysicalAspect.Builder(BodyPart.LEGS, "wolf_rear_paws", "Wolf Rear Paws", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CanineAugments.CANINE_PROSTHETIC_LEG)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.PARALYSIS)
            .build();
    public static final PhysicalAspect WOLF_SKIN = PhysicalAspect.Builder(BodyPart.SKIN, "wolf_skin", "Wolf Skin", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect WOLF_VITAL_ORGANS = PhysicalAspect.Builder(BodyPart.VITAL_ORGAN, "wolf_vital_organs", "Wolf Vital Organs", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect WOLF_BRAIN = PhysicalAspect.Builder(BodyPart.BRAIN, "wolf_brain", "Wolf Brain", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidProperty(CreatureGeneProperties.INTELLECTUAL_DISABILITY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.MENTAL_ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.DOMESTICATION)
            .addValidAugment(CreatureAugments.TRAINING)
            .addValidAugment(CreatureAugments.TAMING)
            .build();
    public static final PhysicalAspect WOLF_IMMUNE_SYSTEM = PhysicalAspect.Builder(BodyPart.IMMUNE_SYSTEM, "wolf_immune_system", "Wolf Immune System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect WOLF_DIGESTIVE = PhysicalAspect.Builder(BodyPart.DIGESTIVE_ORGAN, "wolf_digestive", "Wolf Digestive System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect WOLF_RESPIRATORY = PhysicalAspect.Builder(BodyPart.RESPIRATORY_ORGAN, "wolf_respiratory", "Wolf Respiratory System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect WOLF_REPRODUCTIVE = PhysicalAspect.Builder(BodyPart.GENITAL, "wolf_reproductive", "Wolf Reproductive Organ", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidProperty(CreatureGeneProperties.INFERTILITY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
}