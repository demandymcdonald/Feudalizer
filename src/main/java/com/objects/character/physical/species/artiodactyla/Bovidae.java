package com.objects.character.physical.species.artiodactyla;
import com.objects.character.physical.aspect.BodyPart;
import com.objects.character.physical.aspect.GeneProperty;
import com.objects.character.physical.aspect.PhysicalAspect;
import com.objects.character.physical.augment.AugmentSlot;
import com.objects.character.physical.species.creature.CreatureAugments;
import com.objects.character.physical.species.creature.CreatureGeneProperties;

public class Bovidae {
    public static final AugmentSlot BOVIDAE_PROSTHETIC_LEG = new AugmentSlot("bovidae_prosthetic_leg", "Prosthetic Leg", "");
    public static final AugmentSlot BOVIDAE_PROSTHETIC_HOOF = new AugmentSlot("bovidae_prosthetic_hoof", "Prosthetic Hoof", "");

    public static final GeneProperty BOVIDAE_FUR_COLOR = new GeneProperty("bovidae_fur_color", "Fur Color", "");
    public static final GeneProperty BOVIDAE_FUR_THICKNESS = new GeneProperty("bovidae_fur_thickness", "Fur Thickness", "");
    public static final GeneProperty BOVIDAE_FUR_LENGTH = new GeneProperty("bovidae_fur_length", "Fur Length", "");
    public static final GeneProperty BOVIDAE_EYE_COLOR = new GeneProperty("bovidae_eye_color", "Eye Color", "");
    public static final GeneProperty BOVIDAE_BODY_SIZE = new GeneProperty("bovidae_body_size", "Body Size", "");
    public static final GeneProperty BOVIDAE_BODY_SHAPE = new GeneProperty("bovidae_body_shape", "Body Shape", "");
    public static final GeneProperty BOVIDAE_NOSE_SHAPE = new GeneProperty("bovidae_nose_shape", "Nose Shape", "");
    public static final GeneProperty BOVIDAE_EAR_SHAPE = new GeneProperty("bovidae_ear_shape", "Ear Shape", "");
    public static final GeneProperty BOVIDAE_HORN_SHAPE = new GeneProperty("bovidae_horn_shape", "Horn Shape", "");
    public static final GeneProperty BOVIDAE_HORN_SIZE = new GeneProperty("bovidae_horn_size", "Horn Size", "");
    public static final GeneProperty BOVIDAE_UDDER_SIZE = new GeneProperty("bovidae_udder_size", "Udder Size", "");

    public static final PhysicalAspect BOVIDAE_EYES = PhysicalAspect.Builder(BodyPart.EYES, "bovidae_eyes", "Eyes", "")
            .addValidProperty(BOVIDAE_EYE_COLOR)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect BOVIDAE_HEAD = PhysicalAspect.Builder(BodyPart.HEAD, "bovidae_head", "Head", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect BOVIDAE_NOSE = PhysicalAspect.Builder(BodyPart.NOSE, "bovidae_nose", "Nose", "")
            .addValidProperty(BOVIDAE_NOSE_SHAPE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.LOSS)
            .build();
    public static final PhysicalAspect BOVIDAE_EARS = PhysicalAspect.Builder(BodyPart.EARS, "bovidae_ears", "Ears", "")
            .addValidProperty(BOVIDAE_EAR_SHAPE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect BOVIDAE_MOUTH = PhysicalAspect.Builder(BodyPart.MOUTH, "bovidae_mouth", "Mouth", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect BOVIDAE_HORN = PhysicalAspect.Builder(BodyPart.HORN, "bovidae_horn", "Horn", "")
            .addValidProperty(BOVIDAE_HORN_SHAPE)
            .addValidProperty(BOVIDAE_HORN_SIZE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect BOVIDAE_NECK = PhysicalAspect.Builder(BodyPart.NECK, "bovidae_neck", "Neck", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.COLLAR)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect BOVIDAE_BODY = PhysicalAspect.Builder(BodyPart.WHOLE_BODY, "bovidae_body", "Body", "")
            .addValidProperty(BOVIDAE_BODY_SIZE)
            .addValidProperty(BOVIDAE_BODY_SHAPE)
            .addValidProperty(CreatureGeneProperties.COAT_PATTERN)
            .addValidProperty(CreatureGeneProperties.T_GENE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect BOVIDAE_FUR = PhysicalAspect.Builder(BodyPart.BODY_FUR, "bovidae_fur", "Fur", "")
            .addValidProperty(BOVIDAE_FUR_COLOR)
            .addValidProperty(BOVIDAE_FUR_THICKNESS)
            .addValidProperty(BOVIDAE_FUR_LENGTH)
            .addValidAugment(CreatureAugments.GROOMING)
            .addValidAugment(CreatureAugments.LOSS)
            .build();
    public static final PhysicalAspect BOVIDAE_TORSO = PhysicalAspect.Builder(BodyPart.TORSO, "bovidae_torso", "Torso", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect BOVIDAE_BACK = PhysicalAspect.Builder(BodyPart.BACK, "bovidae_back", "Back", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect BOVIDAE_TAIL = PhysicalAspect.Builder(BodyPart.TAIL, "bovidae_tail", "Tail", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect BOVIDAE_UDDER = PhysicalAspect.Builder(BodyPart.PELVIS, "bovidae_udder", "Udder", "")
            .addValidProperty(BOVIDAE_UDDER_SIZE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect BOVIDAE_FRONT_HOOVES = PhysicalAspect.Builder(BodyPart.ARMS, "bovidae_front_hooves", "Front Hooves", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(BOVIDAE_PROSTHETIC_HOOF)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.PARALYSIS)
            .build();
    public static final PhysicalAspect BOVIDAE_REAR_HOOVES = PhysicalAspect.Builder(BodyPart.LEGS, "bovidae_rear_hooves", "Rear Hooves", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(BOVIDAE_PROSTHETIC_LEG)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.PARALYSIS)
            .build();
    public static final PhysicalAspect BOVIDAE_SKIN = PhysicalAspect.Builder(BodyPart.SKIN, "bovidae_skin", "Skin", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect BOVIDAE_VITAL_ORGANS = PhysicalAspect.Builder(BodyPart.VITAL_ORGAN, "bovidae_vital_organs", "Vital Organs", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect BOVIDAE_BRAIN = PhysicalAspect.Builder(BodyPart.BRAIN, "bovidae_brain", "Brain", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidProperty(CreatureGeneProperties.INTELLECTUAL_DISABILITY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.MENTAL_ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.TAMING)
            .addValidAugment(CreatureAugments.DOMESTICATION)
            .build();
    public static final PhysicalAspect BOVIDAE_IMMUNE_SYSTEM = PhysicalAspect.Builder(BodyPart.IMMUNE_SYSTEM, "bovidae_immune_system", "Immune System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect BOVIDAE_DIGESTIVE = PhysicalAspect.Builder(BodyPart.DIGESTIVE_ORGAN, "bovidae_digestive", "Digestive System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect BOVIDAE_RESPIRATORY = PhysicalAspect.Builder(BodyPart.RESPIRATORY_ORGAN, "bovidae_respiratory", "Respiratory System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect BOVIDAE_REPRODUCTIVE = PhysicalAspect.Builder(BodyPart.GENITAL, "bovidae_reproductive", "Reproductive Organ", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidProperty(CreatureGeneProperties.INFERTILITY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
}