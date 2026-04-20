package com.objects.character.physical.species.fish;

import com.objects.character.physical.aspect.BodyPart;
import com.objects.character.physical.aspect.GeneProperty;
import com.objects.character.physical.aspect.PhysicalAspect;
import com.objects.character.physical.species.creature.CreatureAugments;
import com.objects.character.physical.species.creature.CreatureGeneProperties;

public class Fish {

    public static final GeneProperty FISH_SCALE_COLOR = new GeneProperty("fish_scale_color", "Scale Color", "");
    public static final GeneProperty FISH_SCALE_PATTERN = new GeneProperty("fish_scale_pattern", "Scale Pattern", "");
    public static final GeneProperty FISH_EYE_COLOR = new GeneProperty("fish_eye_color", "Eye Color", "");
    public static final GeneProperty FISH_BODY_SIZE = new GeneProperty("fish_body_size", "Body Size", "");
    public static final GeneProperty FISH_BODY_SHAPE = new GeneProperty("fish_body_shape", "Body Shape", "");
    public static final GeneProperty FISH_FIN_SHAPE = new GeneProperty("fish_fin_shape", "Fin Shape", "");

    public static final PhysicalAspect FISH_BODY = PhysicalAspect.Builder(BodyPart.WHOLE_BODY, "fish_body", "Body", "")
            .addValidProperty(FISH_BODY_SIZE)
            .addValidProperty(FISH_BODY_SHAPE)
            .addValidProperty(FISH_SCALE_COLOR)
            .addValidProperty(FISH_SCALE_PATTERN)
            .addValidProperty(CreatureGeneProperties.T_GENE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect FISH_SCALES = PhysicalAspect.Builder(BodyPart.SKIN, "fish_scales", "Scales", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect FISH_HEAD = PhysicalAspect.Builder(BodyPart.HEAD, "fish_head", "Head", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect FISH_EYES = PhysicalAspect.Builder(BodyPart.EYES, "fish_eyes", "Eyes", "")
            .addValidProperty(FISH_EYE_COLOR)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect FISH_MOUTH = PhysicalAspect.Builder(BodyPart.MOUTH, "fish_mouth", "Mouth", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    // Maps to olfactory nares — fish "smell" through these, not breathe
    public static final PhysicalAspect FISH_NOSE = PhysicalAspect.Builder(BodyPart.NOSE, "fish_nose", "Nares", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect FISH_TORSO = PhysicalAspect.Builder(BodyPart.TORSO, "fish_torso", "Torso", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect FISH_BACK = PhysicalAspect.Builder(BodyPart.BACK, "fish_back", "Back", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect FISH_TAIL_FIN = PhysicalAspect.Builder(BodyPart.TAIL, "fish_tail_fin", "Tail Fin", "")
            .addValidProperty(FISH_FIN_SHAPE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    // Pectoral fins map to ARMS — primary paired propulsion fins
    public static final PhysicalAspect FISH_PECTORAL_FINS = PhysicalAspect.Builder(BodyPart.ARMS, "fish_pectoral_fins", "Pectoral Fins", "")
            .addValidProperty(FISH_FIN_SHAPE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    // Dorsal, pelvic, and anal fins — unpaired and pelvic paired, mapped to FIN
    public static final PhysicalAspect FISH_FINS = PhysicalAspect.Builder(BodyPart.FIN, "fish_fins", "Fins", "")
            .addValidProperty(FISH_FIN_SHAPE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect FISH_VITAL_ORGANS = PhysicalAspect.Builder(BodyPart.VITAL_ORGAN, "fish_vital_organs", "Vital Organs", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect FISH_BRAIN = PhysicalAspect.Builder(BodyPart.BRAIN, "fish_brain", "Brain", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect FISH_IMMUNE_SYSTEM = PhysicalAspect.Builder(BodyPart.IMMUNE_SYSTEM, "fish_immune_system", "Immune System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect FISH_GILLS = PhysicalAspect.Builder(BodyPart.RESPIRATORY_ORGAN, "fish_gills", "Gills", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect FISH_DIGESTIVE = PhysicalAspect.Builder(BodyPart.DIGESTIVE_ORGAN, "fish_digestive", "Digestive System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect FISH_REPRODUCTIVE = PhysicalAspect.Builder(BodyPart.GENITAL, "fish_reproductive", "Reproductive Organ", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidProperty(CreatureGeneProperties.INFERTILITY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
}