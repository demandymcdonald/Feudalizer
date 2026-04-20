package com.objects.character.physical.species.artiodactyla;

import com.objects.character.physical.aspect.BodyPart;
import com.objects.character.physical.aspect.GeneProperty;
import com.objects.character.physical.aspect.PhysicalAspect;
import com.objects.character.physical.augment.AugmentSlot;
import com.objects.character.physical.species.creature.CreatureAugments;
import com.objects.character.physical.species.creature.CreatureGeneProperties;

public class Suidae {
    public static final AugmentSlot SUIDAE_PROSTHETIC_LEG = new AugmentSlot("suidae_prosthetic_leg", "Prosthetic Leg", "");
    public static final AugmentSlot SUIDAE_PROSTHETIC_TROTTER = new AugmentSlot("suidae_prosthetic_trotter", "Prosthetic Trotter", "");

    public static final GeneProperty SUIDAE_BRISTLE_COLOR = new GeneProperty("suidae_bristle_color", "Bristle Color", "");
    public static final GeneProperty SUIDAE_BRISTLE_THICKNESS = new GeneProperty("suidae_bristle_thickness", "Bristle Thickness", "");
    public static final GeneProperty SUIDAE_BRISTLE_LENGTH = new GeneProperty("suidae_bristle_length", "Bristle Length", "");
    public static final GeneProperty SUIDAE_EYE_COLOR = new GeneProperty("suidae_eye_color", "Eye Color", "");
    public static final GeneProperty SUIDAE_BODY_SIZE = new GeneProperty("suidae_body_size", "Body Size", "");
    public static final GeneProperty SUIDAE_BODY_SHAPE = new GeneProperty("suidae_body_shape", "Body Shape", "");
    public static final GeneProperty SUIDAE_SNOUT_SHAPE = new GeneProperty("suidae_snout_shape", "Snout Shape", "");
    public static final GeneProperty SUIDAE_EAR_SHAPE = new GeneProperty("suidae_ear_shape", "Ear Shape", "");
    public static final GeneProperty SUIDAE_TUSK_SIZE = new GeneProperty("suidae_tusk_size", "Tusk Size", "");

    public static final PhysicalAspect SUIDAE_EYES = PhysicalAspect.Builder(BodyPart.EYES, "suidae_eyes", "Eyes", "")
            .addValidProperty(SUIDAE_EYE_COLOR)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect SUIDAE_HEAD = PhysicalAspect.Builder(BodyPart.HEAD, "suidae_head", "Head", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect SUIDAE_SNOUT = PhysicalAspect.Builder(BodyPart.NOSE, "suidae_snout", "Snout", "")
            .addValidProperty(SUIDAE_SNOUT_SHAPE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.LOSS)
            .build();
    public static final PhysicalAspect SUIDAE_EARS = PhysicalAspect.Builder(BodyPart.EARS, "suidae_ears", "Ears", "")
            .addValidProperty(SUIDAE_EAR_SHAPE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect SUIDAE_MOUTH = PhysicalAspect.Builder(BodyPart.MOUTH, "suidae_mouth", "Mouth", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect SUIDAE_TUSKS = PhysicalAspect.Builder(BodyPart.TUSK, "suidae_tusks", "Tusks", "")
            .addValidProperty(SUIDAE_TUSK_SIZE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect SUIDAE_NECK = PhysicalAspect.Builder(BodyPart.NECK, "suidae_neck", "Neck", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.COLLAR)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect SUIDAE_BODY = PhysicalAspect.Builder(BodyPart.WHOLE_BODY, "suidae_body", "Body", "")
            .addValidProperty(SUIDAE_BODY_SIZE)
            .addValidProperty(SUIDAE_BODY_SHAPE)
            .addValidProperty(CreatureGeneProperties.COAT_PATTERN)
            .addValidProperty(CreatureGeneProperties.T_GENE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect SUIDAE_BRISTLES = PhysicalAspect.Builder(BodyPart.BODY_FUR, "suidae_bristles", "Bristles", "")
            .addValidProperty(SUIDAE_BRISTLE_COLOR)
            .addValidProperty(SUIDAE_BRISTLE_THICKNESS)
            .addValidProperty(SUIDAE_BRISTLE_LENGTH)
            .addValidAugment(CreatureAugments.GROOMING)
            .addValidAugment(CreatureAugments.LOSS)
            .build();
    public static final PhysicalAspect SUIDAE_TORSO = PhysicalAspect.Builder(BodyPart.TORSO, "suidae_torso", "Torso", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect SUIDAE_BACK = PhysicalAspect.Builder(BodyPart.BACK, "suidae_back", "Back", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect SUIDAE_TAIL = PhysicalAspect.Builder(BodyPart.TAIL, "suidae_tail", "Tail", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect SUIDAE_FRONT_TROTTERS = PhysicalAspect.Builder(BodyPart.ARMS, "suidae_front_trotters", "Front Trotters", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(SUIDAE_PROSTHETIC_TROTTER)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.PARALYSIS)
            .build();
    public static final PhysicalAspect SUIDAE_REAR_TROTTERS = PhysicalAspect.Builder(BodyPart.LEGS, "suidae_rear_trotters", "Rear Trotters", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(SUIDAE_PROSTHETIC_LEG)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.PARALYSIS)
            .build();
    public static final PhysicalAspect SUIDAE_SKIN = PhysicalAspect.Builder(BodyPart.SKIN, "suidae_skin", "Skin", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect SUIDAE_VITAL_ORGANS = PhysicalAspect.Builder(BodyPart.VITAL_ORGAN, "suidae_vital_organs", "Vital Organs", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect SUIDAE_BRAIN = PhysicalAspect.Builder(BodyPart.BRAIN, "suidae_brain", "Brain", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidProperty(CreatureGeneProperties.INTELLECTUAL_DISABILITY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.MENTAL_ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.TAMING)
            .addValidAugment(CreatureAugments.DOMESTICATION)
            .build();
    public static final PhysicalAspect SUIDAE_IMMUNE_SYSTEM = PhysicalAspect.Builder(BodyPart.IMMUNE_SYSTEM, "suidae_immune_system", "Immune System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect SUIDAE_DIGESTIVE = PhysicalAspect.Builder(BodyPart.DIGESTIVE_ORGAN, "suidae_digestive", "Digestive System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect SUIDAE_RESPIRATORY = PhysicalAspect.Builder(BodyPart.RESPIRATORY_ORGAN, "suidae_respiratory", "Respiratory System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect SUIDAE_REPRODUCTIVE = PhysicalAspect.Builder(BodyPart.GENITAL, "suidae_reproductive", "Reproductive Organ", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidProperty(CreatureGeneProperties.INFERTILITY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
}