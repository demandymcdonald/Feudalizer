package com.objects.character.physical.species.shellfish;

import com.objects.character.physical.aspect.BodyPart;
import com.objects.character.physical.aspect.GeneProperty;
import com.objects.character.physical.aspect.PhysicalAspect;
import com.objects.character.physical.species.creature.CreatureAugments;
import com.objects.character.physical.species.creature.CreatureGeneProperties;

public class Crustacean {

    public static final GeneProperty CRUSTACEAN_SHELL_COLOR = new GeneProperty("crustacean_shell_color", "Shell Color", "");
    public static final GeneProperty CRUSTACEAN_SHELL_PATTERN = new GeneProperty("crustacean_shell_pattern", "Shell Pattern", "");
    public static final GeneProperty CRUSTACEAN_EYE_COLOR = new GeneProperty("crustacean_eye_color", "Eye Color", "");
    public static final GeneProperty CRUSTACEAN_BODY_SIZE = new GeneProperty("crustacean_body_size", "Body Size", "");
    public static final GeneProperty CRUSTACEAN_BODY_SHAPE = new GeneProperty("crustacean_body_shape", "Body Shape", "");
    public static final GeneProperty CRUSTACEAN_CLAW_SIZE = new GeneProperty("crustacean_claw_size", "Claw Size", "");

    public static final PhysicalAspect CRUSTACEAN_BODY = PhysicalAspect.Builder(BodyPart.WHOLE_BODY, "crustacean_body", "Body", "")
            .addValidProperty(CRUSTACEAN_BODY_SIZE)
            .addValidProperty(CRUSTACEAN_BODY_SHAPE)
            .addValidProperty(CreatureGeneProperties.T_GENE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    // Carapace — the exoskeletal shell covering the cephalothorax
    public static final PhysicalAspect CRUSTACEAN_CARAPACE = PhysicalAspect.Builder(BodyPart.SHELL, "crustacean_carapace", "Carapace", "")
            .addValidProperty(CRUSTACEAN_SHELL_COLOR)
            .addValidProperty(CRUSTACEAN_SHELL_PATTERN)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.SCARS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    // Cephalothorax — fused head and thorax, mapped to HEAD
    public static final PhysicalAspect CRUSTACEAN_HEAD = PhysicalAspect.Builder(BodyPart.HEAD, "crustacean_head", "Head", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    // Stalked compound eyes
    public static final PhysicalAspect CRUSTACEAN_EYES = PhysicalAspect.Builder(BodyPart.EYES, "crustacean_eyes", "Eyes", "")
            .addValidProperty(CRUSTACEAN_EYE_COLOR)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect CRUSTACEAN_MOUTH = PhysicalAspect.Builder(BodyPart.MOUTH, "crustacean_mouth", "Mouth", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    // Abdomen
    public static final PhysicalAspect CRUSTACEAN_TORSO = PhysicalAspect.Builder(BodyPart.TORSO, "crustacean_torso", "Abdomen", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    // Chelipeds — large claws. Only included for species that have them (crabs, clawed lobsters — not shrimp, prawns, or spiny lobster)
    public static final PhysicalAspect CRUSTACEAN_CLAWS = PhysicalAspect.Builder(BodyPart.ARMS, "crustacean_claws", "Claws", "")
            .addValidProperty(CRUSTACEAN_CLAW_SIZE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CRUSTACEAN_WALKING_LEGS = PhysicalAspect.Builder(BodyPart.LEGS, "crustacean_walking_legs", "Walking Legs", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    // Tail fan (telson + uropods) for lobsters/shrimp; reduced abdomen for crabs — localization handles display
    public static final PhysicalAspect CRUSTACEAN_TAIL = PhysicalAspect.Builder(BodyPart.TAIL, "crustacean_tail", "Tail", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.LOSS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CRUSTACEAN_VITAL_ORGANS = PhysicalAspect.Builder(BodyPart.VITAL_ORGAN, "crustacean_vital_organs", "Vital Organs", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CRUSTACEAN_BRAIN = PhysicalAspect.Builder(BodyPart.BRAIN, "crustacean_brain", "Brain", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CRUSTACEAN_IMMUNE_SYSTEM = PhysicalAspect.Builder(BodyPart.IMMUNE_SYSTEM, "crustacean_immune_system", "Immune System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect CRUSTACEAN_GILLS = PhysicalAspect.Builder(BodyPart.RESPIRATORY_ORGAN, "crustacean_gills", "Gills", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CRUSTACEAN_DIGESTIVE = PhysicalAspect.Builder(BodyPart.DIGESTIVE_ORGAN, "crustacean_digestive", "Digestive System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect CRUSTACEAN_REPRODUCTIVE = PhysicalAspect.Builder(BodyPart.GENITAL, "crustacean_reproductive", "Reproductive Organ", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidProperty(CreatureGeneProperties.INFERTILITY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
}