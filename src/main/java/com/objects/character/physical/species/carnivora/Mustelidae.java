package com.objects.character.physical.species.carnivora;

import com.Global.*;
import com.objects.character.physical.aspect.BodyPart;
import com.objects.character.physical.aspect.GeneProperty;
import com.objects.character.physical.aspect.PhysicalAspect;
import com.objects.character.physical.augment.AugmentSlot;
import com.objects.character.physical.species.creature.CreatureAugments;
import com.objects.character.physical.species.creature.CreatureGeneProperties;

public class Mustelidae {
    public static final GeneProperty MUSTELIDAE_FUR_COLOR = new GeneProperty("mustelidae_fur_color", "Fur Color", "");
    public static final GeneProperty MUSTELIDAE_FUR_THICKNESS = new GeneProperty("mustelidae_fur_thickness", "Fur Thickness", "");
    public static final GeneProperty MUSTELIDAE_FUR_LENGTH = new GeneProperty("mustelidae_fur_length", "Fur Length", "");
    public static final GeneProperty MUSTELIDAE_EYE_COLOR = new GeneProperty("mustelidae_eye_color", "Eye Color", "");
    public static final GeneProperty MUSTELIDAE_BODY_SIZE = new GeneProperty("mustelidae_body_size", "Body Size", "");
    public static final GeneProperty MUSTELIDAE_BODY_SHAPE = new GeneProperty("mustelidae_body_shape", "Body Shape", "");
    public static final GeneProperty MUSTELIDAE_TAIL_SHAPE = new GeneProperty("mustelidae_tail_shape", "Tail Shape", "");
    public static final GeneProperty MUSTELIDAE_NOSE_SHAPE = new GeneProperty("mustelidae_nose_shape", "Nose Shape", "");
    public static final GeneProperty MUSTELIDAE_WHISKER_LENGTH = new GeneProperty("mustelidae_whisker_length", "Whisker Length", "");

    public static final AugmentSlot MUSTELIDAE_PROSTHETIC_LEG = new AugmentSlot("mustelidae_prosthetic_leg", "Prosthetic Leg", "");
    public static final AugmentSlot MUSTELIDAE_PROSTHETIC_ARM = new AugmentSlot("mustelidae_prosthetic_arm", "Prosthetic Paw", "");

    public static final PhysicalAspect MUSTELIDAE_EYES = PhysicalAspect.Builder(BodyPart.EYES, "mustelidae_eyes", "Eyes", "")
            .addValidProperty(MUSTELIDAE_EYE_COLOR)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect MUSTELIDAE_HEAD = PhysicalAspect.Builder(BodyPart.HEAD, "mustelidae_head", "Head", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect MUSTELIDAE_NOSE = PhysicalAspect.Builder(BodyPart.NOSE, "mustelidae_nose", "Nose", "")
            .addValidProperty(MUSTELIDAE_NOSE_SHAPE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.LOSS)
            .build();
    public static final PhysicalAspect MUSTELIDAE_EARS = PhysicalAspect.Builder(BodyPart.EARS, "mustelidae_ears", "Ears", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect MUSTELIDAE_MOUTH = PhysicalAspect.Builder(BodyPart.MOUTH, "mustelidae_mouth", "Mouth", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect MUSTELIDAE_WHISKERS = PhysicalAspect.Builder(BodyPart.FACE, "mustelidae_whiskers", "Whiskers", "")
            .addValidProperty(MUSTELIDAE_WHISKER_LENGTH)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.LOSS)
            .build();
    public static final PhysicalAspect MUSTELIDAE_NECK = PhysicalAspect.Builder(BodyPart.NECK, "mustelidae_neck", "Neck", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect MUSTELIDAE_BODY = PhysicalAspect.Builder(BodyPart.WHOLE_BODY, "mustelidae_body", "Body", "")
            .addValidProperty(MUSTELIDAE_BODY_SIZE)
            .addValidProperty(MUSTELIDAE_BODY_SHAPE)
            .addValidProperty(CreatureGeneProperties.COAT_PATTERN)
            .addValidProperty(CreatureGeneProperties.T_GENE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect MUSTELIDAE_FUR = PhysicalAspect.Builder(BodyPart.BODY_FUR, "mustelidae_fur", "Fur", "")
            .addValidProperty(MUSTELIDAE_FUR_COLOR)
            .addValidProperty(MUSTELIDAE_FUR_THICKNESS)
            .addValidProperty(MUSTELIDAE_FUR_LENGTH)
            .addValidAugment(CreatureAugments.GROOMING)
            .addValidAugment(CreatureAugments.LOSS)
            .build();
    public static final PhysicalAspect MUSTELIDAE_TAIL = PhysicalAspect.Builder(BodyPart.TAIL, "mustelidae_tail", "Tail", "")
            .addValidProperty(MUSTELIDAE_TAIL_SHAPE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect MUSTELIDAE_TORSO = PhysicalAspect.Builder(BodyPart.TORSO, "mustelidae_torso", "Torso", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect MUSTELIDAE_BACK = PhysicalAspect.Builder(BodyPart.BACK, "mustelidae_back", "Back", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect MUSTELIDAE_FRONT_PAWS = PhysicalAspect.Builder(BodyPart.ARMS, "mustelidae_front_paws", "Front Paws", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(MUSTELIDAE_PROSTHETIC_ARM)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.PARALYSIS)
            .build();
    public static final PhysicalAspect MUSTELIDAE_REAR_PAWS = PhysicalAspect.Builder(BodyPart.LEGS, "mustelidae_rear_paws", "Rear Paws", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(MUSTELIDAE_PROSTHETIC_LEG)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.PARALYSIS)
            .build();
    public static final PhysicalAspect MUSTELIDAE_SKIN = PhysicalAspect.Builder(BodyPart.SKIN, "mustelidae_skin", "Skin", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect MUSTELIDAE_VITAL_ORGANS = PhysicalAspect.Builder(BodyPart.VITAL_ORGAN, "mustelidae_vital_organs", "Vital Organs", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect MUSTELIDAE_BRAIN = PhysicalAspect.Builder(BodyPart.BRAIN, "mustelidae_brain", "Brain", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidProperty(CreatureGeneProperties.INTELLECTUAL_DISABILITY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.MENTAL_ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.TAMING)
            .build();
    public static final PhysicalAspect MUSTELIDAE_IMMUNE_SYSTEM = PhysicalAspect.Builder(BodyPart.IMMUNE_SYSTEM, "mustelidae_immune_system", "Immune System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect MUSTELIDAE_DIGESTIVE = PhysicalAspect.Builder(BodyPart.DIGESTIVE_ORGAN, "mustelidae_digestive", "Digestive System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect MUSTELIDAE_RESPIRATORY = PhysicalAspect.Builder(BodyPart.RESPIRATORY_ORGAN, "mustelidae_respiratory", "Respiratory System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect MUSTELIDAE_REPRODUCTIVE = PhysicalAspect.Builder(BodyPart.GENITAL, "mustelidae_reproductive", "Reproductive Organ", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidProperty(CreatureGeneProperties.INFERTILITY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    
}
