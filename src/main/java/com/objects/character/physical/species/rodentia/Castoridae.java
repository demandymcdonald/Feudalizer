package com.objects.character.physical.species.rodentia;

import com.objects.character.physical.aspect.BodyPart;
import com.objects.character.physical.aspect.GeneProperty;
import com.objects.character.physical.aspect.PhysicalAspect;
import com.objects.character.physical.augment.AugmentSlot;
import com.objects.character.physical.species.creature.CreatureAugments;
import com.objects.character.physical.species.creature.CreatureGeneProperties;

public class Castoridae {
    // Gene Properties
    public static final GeneProperty CASTORIDAE_FUR_COLOR = new GeneProperty("castoridae_fur_color", "Fur Color", "");
    public static final GeneProperty CASTORIDAE_FUR_THICKNESS = new GeneProperty("castoridae_fur_thickness", "Fur Thickness", "");
    public static final GeneProperty CASTORIDAE_EYE_COLOR = new GeneProperty("castoridae_eye_color", "Eye Color", "");
    public static final GeneProperty CASTORIDAE_BODY_SIZE = new GeneProperty("castoridae_body_size", "Body Size", "");
    public static final GeneProperty CASTORIDAE_TAIL_SIZE = new GeneProperty("castoridae_tail_size", "Tail Size", "");
    public static final GeneProperty CASTORIDAE_TAIL_SHAPE = new GeneProperty("castoridae_tail_shape", "Tail Shape", "");
    public static final GeneProperty CASTORIDAE_INCISOR_SIZE = new GeneProperty("castoridae_incisor_size", "Incisor Size", "");
    public static final GeneProperty CASTORIDAE_INCISOR_COLOR = new GeneProperty("castoridae_incisor_color", "Incisor Color", "");

    // Augments
    public static final AugmentSlot CASTORIDAE_PROSTHETIC_LEG = new AugmentSlot("castoridae_prosthetic_leg", "Prosthetic Leg", "");
    public static final AugmentSlot CASTORIDAE_PROSTHETIC_ARM = new AugmentSlot("castoridae_prosthetic_arm", "Prosthetic Paw", "");

    // Physical Aspects
    public static final PhysicalAspect CASTORIDAE_EYES = PhysicalAspect.Builder(BodyPart.EYES, "castoridae_eyes", "Eyes", "")
            .addValidProperty(CASTORIDAE_EYE_COLOR)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect CASTORIDAE_HEAD = PhysicalAspect.Builder(BodyPart.HEAD, "castoridae_head", "Head", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect CASTORIDAE_TEETH = PhysicalAspect.Builder(BodyPart.TEETH, "castoridae_teeth", "Incisors", "")
            .addValidProperty(CASTORIDAE_INCISOR_SIZE)
            .addValidProperty(CASTORIDAE_INCISOR_COLOR)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CASTORIDAE_MOUTH = PhysicalAspect.Builder(BodyPart.MOUTH, "castoridae_mouth", "Mouth", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CASTORIDAE_NOSE = PhysicalAspect.Builder(BodyPart.NOSE, "castoridae_nose", "Nose", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.LOSS)
            .build();
    public static final PhysicalAspect CASTORIDAE_EARS = PhysicalAspect.Builder(BodyPart.EARS, "castoridae_ears", "Ears", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CASTORIDAE_NECK = PhysicalAspect.Builder(BodyPart.NECK, "castoridae_neck", "Neck", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CASTORIDAE_BODY = PhysicalAspect.Builder(BodyPart.WHOLE_BODY, "castoridae_body", "Body", "")
            .addValidProperty(CASTORIDAE_BODY_SIZE)
            .addValidProperty(CreatureGeneProperties.COAT_PATTERN)
            .addValidProperty(CreatureGeneProperties.T_GENE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect CASTORIDAE_FUR = PhysicalAspect.Builder(BodyPart.BODY_FUR, "castoridae_fur", "Fur", "")
            .addValidProperty(CASTORIDAE_FUR_COLOR)
            .addValidProperty(CASTORIDAE_FUR_THICKNESS)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.GROOMING)
            .addValidAugment(CreatureAugments.LOSS)
            .build();
    public static final PhysicalAspect CASTORIDAE_TAIL = PhysicalAspect.Builder(BodyPart.TAIL, "castoridae_tail", "Tail", "")
            .addValidProperty(CASTORIDAE_TAIL_SIZE)
            .addValidProperty(CASTORIDAE_TAIL_SHAPE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CASTORIDAE_TORSO = PhysicalAspect.Builder(BodyPart.TORSO, "castoridae_torso", "Torso", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect CASTORIDAE_BACK = PhysicalAspect.Builder(BodyPart.BACK, "castoridae_back", "Back", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CASTORIDAE_FRONT_PAWS = PhysicalAspect.Builder(BodyPart.ARMS, "castoridae_front_paws", "Front Paws", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CASTORIDAE_PROSTHETIC_ARM)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.PARALYSIS)
            .build();
    public static final PhysicalAspect CASTORIDAE_REAR_PAWS = PhysicalAspect.Builder(BodyPart.LEGS, "castoridae_rear_paws", "Rear Paws", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CASTORIDAE_PROSTHETIC_LEG)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.PARALYSIS)
            .build();
    public static final PhysicalAspect CASTORIDAE_SKIN = PhysicalAspect.Builder(BodyPart.SKIN, "castoridae_skin", "Skin", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect CASTORIDAE_VITAL_ORGANS = PhysicalAspect.Builder(BodyPart.VITAL_ORGAN, "castoridae_vital_organs", "Vital Organs", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CASTORIDAE_BRAIN = PhysicalAspect.Builder(BodyPart.BRAIN, "castoridae_brain", "Brain", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidProperty(CreatureGeneProperties.INTELLECTUAL_DISABILITY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.MENTAL_ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.TAMING)
            .build();
    public static final PhysicalAspect CASTORIDAE_IMMUNE_SYSTEM = PhysicalAspect.Builder(BodyPart.IMMUNE_SYSTEM, "castoridae_immune_system", "Immune System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect CASTORIDAE_DIGESTIVE = PhysicalAspect.Builder(BodyPart.DIGESTIVE_ORGAN, "castoridae_digestive", "Digestive System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CASTORIDAE_RESPIRATORY = PhysicalAspect.Builder(BodyPart.RESPIRATORY_ORGAN, "castoridae_respiratory", "Respiratory System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CASTORIDAE_REPRODUCTIVE = PhysicalAspect.Builder(BodyPart.GENITAL, "castoridae_reproductive", "Reproductive Organ", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidProperty(CreatureGeneProperties.INFERTILITY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
}