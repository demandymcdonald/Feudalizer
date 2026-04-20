package com.objects.character.physical.species.shellfish;

import com.objects.character.physical.aspect.BodyPart;
import com.objects.character.physical.aspect.GeneProperty;
import com.objects.character.physical.aspect.PhysicalAspect;
import com.objects.character.physical.species.creature.CreatureAugments;
import com.objects.character.physical.species.creature.CreatureGeneProperties;

public class Bivalvia {

    public static final GeneProperty BIVALVE_SHELL_COLOR = new GeneProperty("bivalve_shell_color", "Shell Color", "");
    public static final GeneProperty BIVALVE_SHELL_PATTERN = new GeneProperty("bivalve_shell_pattern", "Shell Pattern", "");
    public static final GeneProperty BIVALVE_SHELL_SIZE = new GeneProperty("bivalve_shell_size", "Shell Size", "");
    public static final GeneProperty BIVALVE_SHELL_SHAPE = new GeneProperty("bivalve_shell_shape", "Shell Shape", "");

    public static final PhysicalAspect BIVALVE_BODY = PhysicalAspect.Builder(BodyPart.WHOLE_BODY, "bivalve_body", "Body", "")
            .addValidProperty(BIVALVE_SHELL_SIZE)
            .addValidProperty(CreatureGeneProperties.T_GENE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect BIVALVE_SHELL = PhysicalAspect.Builder(BodyPart.SHELL, "bivalve_shell", "Shell", "")
            .addValidProperty(BIVALVE_SHELL_COLOR)
            .addValidProperty(BIVALVE_SHELL_PATTERN)
            .addValidProperty(BIVALVE_SHELL_SHAPE)
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.LOSS)
            .build();
    // The mantle — soft tissue that secretes and lines the shell
    public static final PhysicalAspect BIVALVE_MANTLE = PhysicalAspect.Builder(BodyPart.SKIN, "bivalve_mantle", "Mantle", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    // Muscular foot used for burrowing — only included for burrowing species (clams), not sessile oysters
    public static final PhysicalAspect BIVALVE_FOOT = PhysicalAspect.Builder(BodyPart.LEGS, "bivalve_foot", "Foot", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.INJURY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    public static final PhysicalAspect BIVALVE_VITAL_ORGANS = PhysicalAspect.Builder(BodyPart.VITAL_ORGAN, "bivalve_vital_organs", "Vital Organs", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect BIVALVE_IMMUNE_SYSTEM = PhysicalAspect.Builder(BodyPart.IMMUNE_SYSTEM, "bivalve_immune_system", "Immune System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .build();
    // Ctenidia — gills that serve dual purpose of respiration and filter feeding
    public static final PhysicalAspect BIVALVE_GILLS = PhysicalAspect.Builder(BodyPart.RESPIRATORY_ORGAN, "bivalve_gills", "Gills", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect BIVALVE_DIGESTIVE = PhysicalAspect.Builder(BodyPart.DIGESTIVE_ORGAN, "bivalve_digestive", "Digestive System", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
    public static final PhysicalAspect BIVALVE_REPRODUCTIVE = PhysicalAspect.Builder(BodyPart.GENITAL, "bivalve_reproductive", "Reproductive Organ", "")
            .addValidProperty(CreatureGeneProperties.CONGENITAL_CONDITION)
            .addValidProperty(CreatureGeneProperties.INFERTILITY)
            .addValidAugment(CreatureAugments.ILLNESS)
            .addValidAugment(CreatureAugments.INJURY)
            .build();
}