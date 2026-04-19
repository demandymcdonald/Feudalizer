package com.objects.character.physical.species.carnivora;

import com.Global.*;
import com.objects.character.physical.aspect.BodyPart;
import com.objects.character.physical.aspect.GeneProperty;
import com.objects.character.physical.aspect.PhysicalAspect;
import com.objects.character.physical.augment.AugmentSlot;
import com.objects.character.physical.species.creature.CreatureAugments;
import com.objects.character.physical.species.creature.CreatureGeneProperties;

import static com.objects.character.physical.species.creature.CreatureAugments.COLLAR;
import static com.objects.character.physical.species.creature.CreatureAugments.GROOMING;

public class Canidae {
    public static final AugmentSlot CANIDAE_PROSTHETIC_LEG = new AugmentSlot("canidae_prosthetic_leg", "Prosthetic Leg", "");
    public static final AugmentSlot CANIDAE_PROSTHETIC_ARM = new AugmentSlot("canidae_prosthetic_arm", "Prosthetic Paw", "");
    public static final GeneProperty CANINE_FUR_COLOR = new GeneProperty("canine_fur_color", "Fur Color", "");
    public static final GeneProperty CANINE_FUR_THICKNESS = new GeneProperty("canine_fur_thickness", "Fur Thickness", "");
    public static final GeneProperty CANINE_FUR_LENGTH = new GeneProperty("canine_fur_length", "Fur Length", "");
    public static final GeneProperty CANINE_EYE_COLOR = new GeneProperty("canine_eye_color", "Eye Color", "");
    public static final GeneProperty CANINE_BODY_SIZE = new GeneProperty("canine_body_size", "Body Size", "");
    public static final GeneProperty CANINE_BODY_SHAPE = new GeneProperty("canine_body_shape", "Body Shape", "");
    public static final GeneProperty CANINE_TAIL_SHAPE = new GeneProperty("canine_tail_shape", "Tail Shape", "");
    public static final GeneProperty CANINE_EAR_SHAPE = new GeneProperty("canine_ear_shape", "Ear Shape", "");
    public static final GeneProperty CANINE_NOSE_SHAPE = new GeneProperty("canine_nose_shape", "Nose Shape", "");

    public static final PhysicalAspect CANIDAE_EYES = PhysicalAspect.Builder(BodyPart.EYES, "CANIDAE_eye", "Wolf Eyes", "")
            .addValidProperty(CANINE_EYE_COLOR)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect CANIDAE_HEAD = PhysicalAspect.Builder(BodyPart.HEAD, "CANIDAE_head", "Wolf Head", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect CANIDAE_NOSE = PhysicalAspect.Builder(BodyPart.NOSE, "CANIDAE_nose", "Wolf Nose", "")
            .addValidProperty(CANINE_NOSE_SHAPE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.LOSS)
            .build();
    public static final PhysicalAspect CANIDAE_EARS = PhysicalAspect.Builder(BodyPart.EARS, "CANIDAE_ears", "Wolf Ears", "")
            .addValidProperty(CANINE_EAR_SHAPE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CANIDAE_MOUTH = PhysicalAspect.Builder(BodyPart.MOUTH, "CANIDAE_mouth", "Wolf Mouth", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CANIDAE_NECK = PhysicalAspect.Builder(BodyPart.NECK, "CANIDAE_neck", "Wolf Neck", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(COLLAR)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CANIDAE_BODY = PhysicalAspect.Builder(BodyPart.WHOLE_BODY, "CANIDAE_body", "Wolf Body", "")
            .addValidProperty(CANINE_BODY_SIZE)
            .addValidProperty(CANINE_BODY_SHAPE)
            .addValidProperty(CreatureGeneProperties.COAT_PATTERN)
            .addValidProperty(CreatureGeneProperties.T_GENE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect CANIDAE_FUR = PhysicalAspect.Builder(BodyPart.BODY_FUR, "CANIDAE_fur", "Wolf Fur", "")
            .addValidProperty(CANINE_FUR_COLOR)
            .addValidProperty(CANINE_FUR_THICKNESS)
            .addValidProperty(CANINE_FUR_LENGTH)
            .addValidAugment(GROOMING)
            .addValidAugment(CreatureAugments.LOSS)
            .build();
    public static final PhysicalAspect CANIDAE_TAIL = PhysicalAspect.Builder(BodyPart.TAIL, "CANIDAE_tail", "Wolf Tail", "")
            .addValidProperty(CANINE_TAIL_SHAPE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CANIDAE_TORSO = PhysicalAspect.Builder(BodyPart.TORSO, "CANIDAE_torso", "Wolf Torso", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect CANIDAE_BACK = PhysicalAspect.Builder(BodyPart.BACK, "CANIDAE_back", "Wolf Back", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CANIDAE_FRONT_PAWS = PhysicalAspect.Builder(BodyPart.HANDS, "CANIDAE_front_paws", "Wolf Front Paws", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CANIDAE_PROSTHETIC_ARM)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.PARALYSIS)
            .build();
    public static final PhysicalAspect CANIDAE_REAR_PAWS = PhysicalAspect.Builder(BodyPart.LEGS, "CANIDAE_rear_paws", "Wolf Rear Paws", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CANIDAE_PROSTHETIC_LEG)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.PARALYSIS)
            .build();
    public static final PhysicalAspect CANIDAE_SKIN = PhysicalAspect.Builder(BodyPart.SKIN, "CANIDAE_skin", "Wolf Skin", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect CANIDAE_VITAL_ORGANS = PhysicalAspect.Builder(BodyPart.VITAL_ORGAN, "CANIDAE_vital_organs", "Wolf Vital Organs", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CANIDAE_BRAIN = PhysicalAspect.Builder(BodyPart.BRAIN, "CANIDAE_brain", "Wolf Brain", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidProperty(CreatureGeneProperties.INTELLECTUAL_DISABILITY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.MENTAL_ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.DOMESTICATION)
            .addValidAugment(CreatureAugments.TRAINING)
            .addValidAugment(CreatureAugments.TAMING)
            .build();
    public static final PhysicalAspect CANIDAE_IMMUNE_SYSTEM = PhysicalAspect.Builder(BodyPart.IMMUNE_SYSTEM, "CANIDAE_immune_system", "Wolf Immune System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect CANIDAE_DIGESTIVE = PhysicalAspect.Builder(BodyPart.DIGESTIVE_ORGAN, "CANIDAE_digestive", "Wolf Digestive System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CANIDAE_RESPIRATORY = PhysicalAspect.Builder(BodyPart.RESPIRATORY_ORGAN, "CANIDAE_respiratory", "Wolf Respiratory System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CANIDAE_REPRODUCTIVE = PhysicalAspect.Builder(BodyPart.GENITAL, "CANIDAE_reproductive", "Wolf Reproductive Organ", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidProperty(CreatureGeneProperties.INFERTILITY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
}
