package com.objects.culture.tenet.group.groups;

import com.base.component.InstanceType;
import com.objects.culture.tenet.group.TGType;
import com.objects.culture.tenet.group.TenetGroup;
import org.checkerframework.checker.nullness.qual.NonNull;

import static com.objects.culture.tenet.group.TenetGroup.Builder.MGF;
import static com.objects.culture.tenet.group.groups.GovernmentGroups.*;
import static com.objects.culture.tenet.group.TenetGroup.Level;

public class MilitaryGroups {
    public static class MilitaryGroup extends TenetGroup {
        public MilitaryGroup(InstanceType instType, @NonNull TGType type, Level level, String fullID, String id, String name, String description) {
            super(instType,type, level,fullID, id, name, description);
        }
    }
    
    
    public static final MilitaryGroup MILITARY = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_SORT, TenetGroup.Level.CATEGORY, "military", "Military", "")
            .setParent(GOVERNMENT)
            .addConnected(MILITARY_FUNDING)
            .build();

    public static final MilitaryGroup MILITARY_INFLUENCE_ON_GOVERNMENT = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_LARGE, Level.NORMAL, "military_influence", "Military Influence on GovernmentTenet", "")
            .setParent(MILITARY)
            .addConnected(CLASS_AND_CASTE, MILITARY)
            .build();
    public static final MilitaryGroup MILITARY_LOGISTICS = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_LARGE, Level.SUBCATEGORY, "logistics", "Military Logistics", "")
            .setParent(MILITARY)
            .addConnected(MILITARY_FUNDING)
            .build();
    public static final MilitaryGroup MILITARY_RECRUITMENT = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_LARGE, Level.NORMAL, "recruitment", "Military Recruitment", "")
            .setParent(MILITARY_LOGISTICS)
            .addConnected(MILITARY_FUNDING)
            .build();
    public static final MilitaryGroup MILITARY_TRAINING = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_LARGE, Level.NORMAL, "training", "Military Training", "")
            .setParent(MILITARY_LOGISTICS)
            .addConnected(MILITARY_FUNDING)
            .build();
    public static final MilitaryGroup MILITARY_EQUIPMENT = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_LARGE, Level.NORMAL, "equipment", "Military Equipment and Logistics", "")
            .setParent(MILITARY_LOGISTICS)
            .addConnected(MILITARY_FUNDING)
            .build();
    public static final MilitaryGroup MILITARY_PROCUREMENT = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_LARGE, Level.NORMAL, "procurement", "Military Procurement", "")
            .setParent(MILITARY_LOGISTICS)
            .addConnected(MILITARY_EQUIPMENT)
            .build();
    public static final MilitaryGroup MILITARY_WOUNDED_AND_VETERANS = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_LARGE, Level.NORMAL, "wounded_and_veterans", "Military Wounded and Veterans", "")
            .setParent(MILITARY)
            .build();
    public static final MilitaryGroup MILITARY_UPWARD_MOBILITY = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_LARGE, Level.NORMAL, "upward_mobility", "Military Upward Mobility", "")
            .setParent(MILITARY)
            .addConnected(CASTE_PERMEABILITY)
            .build();

    // Military Policy
    public static final MilitaryGroup MILITARY_POLICY = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_SORT, TenetGroup.Level.SUBCATEGORY, "ethics", "Military Policy and Doctrine", "")
            .setParent(MILITARY)
            .build();
    public static final MilitaryGroup SOLDIER_TREATMENT = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_LARGE, Level.NORMAL, "soldier_treatment", "Soldier Treatment", "")
            .setParent(MILITARY_POLICY)
            .addConnected(MILITARY_FUNDING, MILITARY_LOGISTICS, SOLDIER_CLASS)
            .build();
    public static final MilitaryGroup SPECIAL_OPERATIONS = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_LARGE, Level.NORMAL, "special_operations", "Special Operations", "")
            .setParent(MILITARY_POLICY)
            .addConnected(MILITARY_LOGISTICS)
            .build();
    public static final MilitaryGroup ORDER_OF_BATTLE = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_LARGE, Level.NORMAL, "order_of_battle", "Order of Battle", "")
            .setParent(MILITARY_POLICY)
            .addConnected(MILITARY_LOGISTICS)
            .build();
    public static final MilitaryGroup COMBAT_DOCTRINE = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_LARGE, Level.NORMAL, "combat_doctrine", "Combat Doctrine", "")
            .setParent(MILITARY_POLICY)
            .addConnected(MILITARY_LOGISTICS)
            .build();
    public static final MilitaryGroup RULES_OF_ENGAGEMENT = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_LARGE, Level.NORMAL, "use_of_force", "Use of Force", "")
            .setParent(MILITARY_POLICY)
            .build();
    public static final MilitaryGroup MILITARY_JUSTICE = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_LARGE, TenetGroup.Level.NORMAL, "military_justice", "Military Justice", "")
            .setParent(MILITARY_POLICY)
            .addConnected(JUSTICE_SYSTEM)
            .build();
    public static final MilitaryGroup MILITARY_INTERVENTION_IN_CIVIL_LIFE = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_LARGE, Level.NORMAL, "intervention_in_civil_life", "Military Intervention in Civil Life", "")
            .setParent(MILITARY_POLICY)
            .addConnected(JUSTICE_SYSTEM, CIVIL_LIBERTIES, COMBAT_DOCTRINE, RULES_OF_ENGAGEMENT)
            .build();
    public static final MilitaryGroup MILITARY_INTERVENTION_IN_FOREIGN_CONFLICT = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_LARGE, Level.NORMAL, "intervention_in_foreign_conflict", "Military Intervention in Foreign Conflict", "")
            .setParent(MILITARY_POLICY)
            .addConnected(FOREIGN_POLICY, COMBAT_DOCTRINE, RULES_OF_ENGAGEMENT)
            .build();

    // Military Leadership
    public static final MilitaryGroup MILITARY_LEADERSHIP = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_SORT, Level.SUBCATEGORY, "leadership", "Military Leadership", "")
            .setParent(MILITARY)
            .build();

    public static final MilitaryGroup OFFICER_TYPES = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_SORT, Level.NORMAL, "types", "Officer Types", "")
            .setParent(MILITARY_LEADERSHIP)
            .build();
    public static final MilitaryGroup CENTRALIZATION = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_LARGE, Level.NORMAL, "centralization", "Command Centralization", "")
            .setParent(MILITARY_LEADERSHIP)
            .addConnected(COMBAT_DOCTRINE, ORDER_OF_BATTLE, MILITARY_UPWARD_MOBILITY, RULES_OF_ENGAGEMENT)
            .build();
    public static final MilitaryGroup OFFICER_APPOINTMENT = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_LARGE, Level.NORMAL, "appointment", "Officer Appointment", "")
            .setParent(MILITARY_LEADERSHIP)
            .addConnected(CENTRALIZATION, MILITARY_RECRUITMENT, MILITARY_UPWARD_MOBILITY)
            .build();
    public static final MilitaryGroup OFFICER_REMOVAL = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_LARGE, Level.NORMAL, "removal", "Officer Removal", "")
            .setParent(MILITARY_LEADERSHIP)
            .addConnected(MILITARY_JUSTICE)
            .build();
    public static final MilitaryGroup OFFICER_TRAINING = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_LARGE, Level.NORMAL, "training", "Officer Training", "")
            .setParent(MILITARY_LEADERSHIP)
            .addConnected(MILITARY_FUNDING, MILITARY_TRAINING)
            .build();
    public static final MilitaryGroup OFFICER_TREATMENT = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_LARGE, Level.NORMAL, "treatment", "Officer Treatment", "")
            .setParent(MILITARY_LEADERSHIP)
            .build();

    // Military Ideology
    public static final MilitaryGroup MILITARY_IDEOLOGY = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_SORT, Level.SUBCATEGORY, "ideology", "Military Ideology", "")
            .setParent(MILITARY)
            .build();
    public static final MilitaryGroup MILITARY_SELF_CONCEPT = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_LARGE, Level.NORMAL, "self_concept", "Military Self-Concept", "")
            .setParent(MILITARY_IDEOLOGY)
            .build();
    public static final MilitaryGroup MILITARY_IDEOLOGICAL_CAPTURE = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_LARGE, Level.NORMAL, "ideological_capture", "Military Ideological Capture", "")
            .setParent(MILITARY_IDEOLOGY)
            .build();
    public static final MilitaryGroup SOLDIER_VALUE = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_LARGE, Level.NORMAL, "soldier_value", "Soldier Value", "")
            .setParent(MILITARY_IDEOLOGY)
            .addConnected(MILITARY_UPWARD_MOBILITY)
            .build();
    public static final MilitaryGroup OFFICER_VALUE = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_LARGE, Level.NORMAL, "officer_value", "Officer Value", "")
            .setParent(MILITARY_IDEOLOGY)
            .addConnected(MILITARY_UPWARD_MOBILITY, CENTRALIZATION)
            .build();
    public static final MilitaryGroup MILITARY_HEROES_AND_VILLAINS = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_LARGE, Level.NORMAL, "heroes_and_villains", "Heroes and Villains", "")
            .setParent(MILITARY_IDEOLOGY)
            .build();

    // Military Loyalty & Corruption
    public static final MilitaryGroup MILITARY_LOYALTY = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_SORT, Level.SUBCATEGORY, "loyalty", "Military Loyalty", "")
            .setParent(MILITARY)
            .addConnected(MILITARY_JUSTICE, MILITARY_IDEOLOGICAL_CAPTURE, MILITARY_SELF_CONCEPT, OFFICER_TREATMENT, SOLDIER_TREATMENT)
            .build();
    public static final MilitaryGroup MILITARY_CORRUPTION = new TenetGroup.Builder<MilitaryGroup>(MGF,TGType.SYSTEM_LARGE, Level.NORMAL, "corruption", "Military Corruption", "")
            .setParent(MILITARY)
            .addConnected(GOVERNMENT_CORRUPTION, MILITARY_JUSTICE, MILITARY_IDEOLOGICAL_CAPTURE, MILITARY_SELF_CONCEPT, OFFICER_TREATMENT, SOLDIER_TREATMENT, MILITARY_LOYALTY)
            .build();
}
