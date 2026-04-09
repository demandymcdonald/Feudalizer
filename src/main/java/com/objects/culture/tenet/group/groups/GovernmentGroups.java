package com.objects.culture.tenet.group.groups;

import com.google.common.collect.ImmutableList;
import com.objects.culture.tenet.group.TGType;
import com.objects.culture.tenet.group.TenetGroup;

import static com.objects.culture.tenet.TenetManager.HARD_CULTURE;
import static com.objects.culture.tenet.TenetManager.SOFT_CULTURE;
import static com.objects.culture.tenet.group.groups.EconomicGroups.ECONOMIC_SYSTEM;
import static com.objects.culture.tenet.group.groups.EconomicGroups.ECONOMY;
import static com.objects.culture.tenet.group.groups.EducationGroups.EDUCATION;
import static com.objects.culture.tenet.group.groups.FamilyGroups.FAMILY;
import static com.objects.culture.tenet.group.groups.ReligionGroups.RELIGION;
import static com.objects.culture.tenet.group.groups.SocietyGroups.SOCIETY;
import static com.objects.culture.tenet.group.TenetGroup.*;

public class GovernmentGroups {

//    public static final TenetGroup GOVERNMENT = builder(HARD_CULTURE, "government", "GovernmentTenet", "", SORT_ONLY);
//    public static final TenetGroup GOVERNMENT_SYSTEM = builder(GOVERNMENT, "system", "GovernmentTenet System", "", PILLAR);
//    public static final TenetGroup GOVERNMENT_IDEOLOGY = builder(GOVERNMENT, ImmutableList.of(GOVERNMENT_SYSTEM), "ideology", "GovernmentTenet Ideology", "", PILLAR);
//
//    public static final TenetGroup RESOURCE_ALLOCATION = builder(GOVERNMENT_SYSTEM, ImmutableList.of(ECONOMIC_SYSTEM), "resource_allocation", "Resource Allocation", "", SYSTEM_LARGE);
//    public static final TenetGroup WELFARE = builder(RESOURCE_ALLOCATION, "welfare", "Welfare", "", SYSTEM_LARGE);
//    public static final TenetGroup TAXATION = builder(RESOURCE_ALLOCATION, "taxation", "Taxation", "", SYSTEM_LARGE);
//    public static final TenetGroup ECONOMIC_INTERVENTION = builder(RESOURCE_ALLOCATION, ImmutableList.of(ECONOMY), "economic_intervention", "Economic Intervention", "", SYSTEM_LARGE);
//    public static final TenetGroup EDUCATION_FUNDING = builder(RESOURCE_ALLOCATION, ImmutableList.of(EDUCATION), "education_funding", "Education Intervention", "", SYSTEM_LARGE);
//    public static final TenetGroup MILITARY_FUNDING = builder(RESOURCE_ALLOCATION, "military_funding", "Military Funding", "", SYSTEM_LARGE);
//    public static final TenetGroup JUSTICE_SYSTEM = builder(GOVERNMENT_SYSTEM, "justice_system", "Justice System", "", SYSTEM_LARGE);
//    public static final TenetGroup GOVERNMENT_CORRUPTION = builder(JUSTICE_SYSTEM, "corruption", "Corruption", "", SYSTEM_LARGE);
//    public static final TenetGroup CLASS_AND_CASTE = builder(GOVERNMENT_SYSTEM, ImmutableList.of(WELFARE, TAXATION), "class_and_caste", "Class and Caste", "", SYSTEM_LARGE);
//    public static final TenetGroup CIVIL_LIBERTIES = builder(GOVERNMENT_SYSTEM, ImmutableList.of(JUSTICE_SYSTEM, CLASS_AND_CASTE), "civil_rights", "Civil Rights", "", SYSTEM_LARGE);
//    public static final TenetGroup CITIZENSHIP = builder(CLASS_AND_CASTE, "citizenship", "Citizenship", "", SYSTEM_LARGE);
//    public static final TenetGroup ELITE = builder(CLASS_AND_CASTE, ImmutableList.of(CITIZENSHIP), "elites", "Elites", "", SYSTEM_LARGE);
//    public static final TenetGroup MIDDLE_CLASS = builder(CLASS_AND_CASTE, ImmutableList.of(CITIZENSHIP), "middle_class", "Middle Class", "", SYSTEM_LARGE);
//    public static final TenetGroup SOLDIER = builder(CLASS_AND_CASTE, ImmutableList.of(CITIZENSHIP), "soldiers", "Soldiers", "", SYSTEM_LARGE);
//    public static final TenetGroup WORKING_CLASS = builder(CLASS_AND_CASTE, ImmutableList.of(CITIZENSHIP), "working_class", "Working Class", "", SYSTEM_LARGE);
//    public static final TenetGroup DISENFRANCHISED = builder(CLASS_AND_CASTE, "disenfranchised", "Disenfranchised", "", SYSTEM_LARGE);
//    public static final TenetGroup SLAVE = builder(CLASS_AND_CASTE, ImmutableList.of(DISENFRANCHISED), "slaves", "Slaves", "", SYSTEM_LARGE);
//    public static final TenetGroup OUTSIDER = builder(CLASS_AND_CASTE, ImmutableList.of(CITIZENSHIP), "outsider", "Outsiders and Foreigners", "", SYSTEM_LARGE);
//    public static final TenetGroup FOREIGN_POLICY = builder(GOVERNMENT_SYSTEM, "foreign_policy", "Foreign Policy", "", SYSTEM_LARGE);
//
//    public static final TenetGroup MILITARY = builder(GOVERNMENT_SYSTEM, ImmutableList.of(MILITARY_FUNDING), "military", "Military", "", PILLAR);
//    public static final TenetGroup MILITARY_INFLUENCE_ON_GOVERNMENT = builder(MILITARY, ImmutableList.of(CLASS_AND_CASTE, MILITARY), "military_influence", "Military Influence on GovernmentTenet", "", SYSTEM_LARGE);
//    public static final TenetGroup MILITARY_LOGISTICS = builder(MILITARY, ImmutableList.of(MILITARY_FUNDING), "logistics", "Military Logistics", "", SYSTEM_LARGE);
//    public static final TenetGroup MILITARY_RECRUITMENT = builder(MILITARY_LOGISTICS, ImmutableList.of(MILITARY_FUNDING), "recruitment", "Military Recruitment", "", SYSTEM_LARGE);
//    public static final TenetGroup MILITARY_TRAINING = builder(MILITARY_LOGISTICS, ImmutableList.of(MILITARY_FUNDING), "training", "Military Training", "", SYSTEM_LARGE);
//    public static final TenetGroup MILITARY_EQUIPMENT = builder(MILITARY_LOGISTICS, ImmutableList.of(MILITARY_FUNDING), "equipment", "Military Equipment and Logistics", "", SYSTEM_LARGE);
//    public static final TenetGroup MILITARY_PROCUREMENT = builder(MILITARY_LOGISTICS, ImmutableList.of(MILITARY_EQUIPMENT), "procurement", "Military Procurement", "", SYSTEM_LARGE);
//    public static final TenetGroup MILITARY_WOUNDED_AND_VETERANS = builder(MILITARY, "wounded_and_veterans", "Military Wounded and Veterans", "", SYSTEM_LARGE);
//    public static final TenetGroup MILITARY_UPWARD_MOBILITY = builder(MILITARY, "upward_mobility", "Military Upward Mobility", "", SYSTEM_LARGE);
//    public static final TenetGroup MILITARY_POLICY = builder(MILITARY, "ethics", "Military Policy and Doctrine", "", SORT_ONLY);
//    public static final TenetGroup SOLDIER_TREATMENT = builder(MILITARY_POLICY, ImmutableList.of(MILITARY_FUNDING, MILITARY_LOGISTICS, SOLDIER), "soldier_treatment", "Soldier Treatment", "", SYSTEM_LARGE);
//    public static final TenetGroup SPECIAL_OPERATIONS = builder(MILITARY_POLICY, ImmutableList.of(MILITARY_LOGISTICS), "special_operations", "Special Operations", "", SYSTEM_LARGE);
//    public static final TenetGroup ORDER_OF_BATTLE = builder(MILITARY_POLICY, ImmutableList.of(MILITARY_LOGISTICS), "order_of_battle", "Order of Battle", "", SYSTEM_LARGE);
//    public static final TenetGroup COMBAT_DOCTRINE = builder(MILITARY_POLICY, ImmutableList.of(MILITARY_LOGISTICS), "combat_doctrine", "Combat Doctrine", "", SYSTEM_LARGE);
//    public static final TenetGroup RULES_OF_ENGAGEMENT = builder(MILITARY_POLICY, "use_of_force", "Use of Force", "", SYSTEM_LARGE);
//    public static final TenetGroup MILITARY_JUSTICE = builder(MILITARY_POLICY, ImmutableList.of(JUSTICE_SYSTEM), "military_justice", "Military Justice", "", SYSTEM_LARGE);
//    public static final TenetGroup MILITARY_INTERVENTION_IN_CIVIL_LIFE = builder(MILITARY_POLICY, ImmutableList.of(JUSTICE_SYSTEM, CIVIL_LIBERTIES, COMBAT_DOCTRINE, RULES_OF_ENGAGEMENT), "intervention_in_civil_life", "Military Intervention in Civil Life", "", SYSTEM_LARGE);
//    public static final TenetGroup MILITARY_INTERVENTION_IN_FOREIGN_CONFLICT = builder(MILITARY_POLICY, ImmutableList.of(FOREIGN_POLICY, COMBAT_DOCTRINE, RULES_OF_ENGAGEMENT), "intervention_in_foreign_conflict", "Military Intervention in Foreign Conflict", "", SYSTEM_LARGE);
//    public static final TenetGroup MILITARY_LEADERSHIP = builder(MILITARY, "leadership", "Military Leadership", "", SORT_ONLY);
//    public static final TenetGroup CENTRALIZATION = builder(MILITARY_LEADERSHIP, ImmutableList.of(COMBAT_DOCTRINE, ORDER_OF_BATTLE, MILITARY_UPWARD_MOBILITY, RULES_OF_ENGAGEMENT), "command_centralization", "Command Centralization", "", SYSTEM_LARGE);
//    public static final TenetGroup OFFICER_APPOINTMENT = builder(MILITARY_LEADERSHIP, ImmutableList.of(CENTRALIZATION, MILITARY_RECRUITMENT, MILITARY_UPWARD_MOBILITY), "officer_appointment", "Officer Appointment", "", SYSTEM_LARGE);
//    public static final TenetGroup OFFICER_REMOVAL = builder(MILITARY_LEADERSHIP, ImmutableList.of(MILITARY_JUSTICE), "officer_removal", "Officer Removal", "", SYSTEM_LARGE);
//    public static final TenetGroup OFFICER_TREATMENT = builder(MILITARY_LEADERSHIP, ImmutableList.of(MILITARY_FUNDING), "officer_treatment", "Officer Treatment", "", SYSTEM_LARGE);
//    public static final TenetGroup OFFICER_TRAINING = builder(MILITARY_LEADERSHIP, ImmutableList.of(MILITARY_FUNDING, MILITARY_TRAINING), "officer_training", "Officer Training", "", SYSTEM_LARGE);
//    public static final TenetGroup MILITARY_IDEOLOGY = builder(MILITARY, "ideology", "Military Ideology", "", SORT_ONLY);
//    public static final TenetGroup MILITARY_SELF_CONCEPT = builder(MILITARY_IDEOLOGY, "self_concept", "Military Self-Concept", "", SYSTEM_LARGE);
//    public static final TenetGroup MILITARY_IDEOLOGICAL_CAPTURE = builder(MILITARY_IDEOLOGY, "ideological_capture", "Military Ideological Capture", "", SYSTEM_LARGE);
//    public static final TenetGroup SOLDIER_VALUE = builder(MILITARY_IDEOLOGY, ImmutableList.of(MILITARY_UPWARD_MOBILITY), "soldier_value", "Soldier Value", "", SYSTEM_LARGE);
//    public static final TenetGroup OFFICER_VALUE = builder(MILITARY_IDEOLOGY, ImmutableList.of(MILITARY_UPWARD_MOBILITY, CENTRALIZATION), "officer_value", "Officer Value", "", SYSTEM_LARGE);
//    public static final TenetGroup MILITARY_HEROES_AND_VILLAINS = builder(MILITARY_IDEOLOGY, "heroes_and_villains", "Heroes and Villains", "", SYSTEM_LARGE);
//    public static final TenetGroup MILITARY_LOYALTY = builder(MILITARY, ImmutableList.of(MILITARY_JUSTICE, MILITARY_IDEOLOGICAL_CAPTURE, MILITARY_SELF_CONCEPT, OFFICER_TREATMENT, SOLDIER_TREATMENT), "loyalty", "Military Loyalty", "", SORT_ONLY);
//    public static final TenetGroup MILITARY_CORRUPTION = builder(MILITARY, ImmutableList.of(GOVERNMENT_CORRUPTION, MILITARY_JUSTICE, MILITARY_IDEOLOGICAL_CAPTURE, MILITARY_SELF_CONCEPT, OFFICER_TREATMENT, SOLDIER_TREATMENT, MILITARY_LOYALTY), "corruption", "Military Corruption", "", SYSTEM_LARGE);
//
//    public static final TenetGroup GOVERNMENT_LEADERSHIP = builder(GOVERNMENT_SYSTEM, ImmutableList.of(CLASS_AND_CASTE, MILITARY), "leadership", "Leadership", "", SORT_ONLY);
//    public static final TenetGroup LEADERSHIP_SELECTION = builder(GOVERNMENT_LEADERSHIP, ImmutableList.of(CLASS_AND_CASTE, DISENFRANCHISED), "selection", "Leadership Selection", "", SYSTEM_LARGE);
//    public static final TenetGroup LEADER_AUTHORITY = builder(GOVERNMENT_LEADERSHIP, "authority", "Leader Authority", "", SYSTEM_LARGE);
//    public static final TenetGroup LEADER_CORRUPTION = builder(GOVERNMENT_LEADERSHIP, ImmutableList.of(GOVERNMENT_CORRUPTION, LEADER_AUTHORITY), "corruption", "Leader Corruption", "", SYSTEM_LARGE);
//    public static final TenetGroup LEADERSHIP_REMOVAL = builder(GOVERNMENT_LEADERSHIP, ImmutableList.of(LEADER_CORRUPTION), "removal", "Leadership Removal", "", SYSTEM_LARGE);
//    public static final TenetGroup GOVERNMENT_OFFICE = builder(GOVERNMENT_SYSTEM, "office", "GovernmentTenet Office", "", SYSTEM_LARGE);
//    public static final TenetGroup GOVERNMENT_OFFICE_AUTHORITY = builder(GOVERNMENT_OFFICE, "authority", "GovernmentTenet Office Authority", "", SYSTEM_LARGE);
//    public static final TenetGroup GOVERNMENT_OFFICE_CREATION_REMOVAL = builder(GOVERNMENT_OFFICE, "creation_removal", "GovernmentTenet Office Creation and Removal", "", SYSTEM_LARGE);
//    public static final TenetGroup GOVERNMENT_OFFICIAL = builder(GOVERNMENT_OFFICE, ImmutableList.of(MILITARY), "official", "GovernmentTenet Official", "", SORT_ONLY);
//    public static final TenetGroup OFFICIAL_SELECTION = builder(GOVERNMENT_OFFICIAL, ImmutableList.of(CLASS_AND_CASTE, DISENFRANCHISED), "selection", "Selection of an Official", "", SYSTEM_LARGE);
//    public static final TenetGroup OFFICIAL_AUTHORITY = builder(GOVERNMENT_OFFICIAL, "authority", "An Official's Authority", "", SYSTEM_LARGE);
//    public static final TenetGroup OFFICIAL_TREATMENT_GOVERNMENT = builder(GOVERNMENT_OFFICIAL, "treatment_government", "An Official's Treatment by the GovernmentTenet", "", SYSTEM_LARGE);
//    public static final TenetGroup OFFICIAL_TREATMENT_PEOPLE = builder(GOVERNMENT_OFFICIAL, "treatment_people", "An Official's Treatment by the People", "", SYSTEM_LARGE);
//    public static final TenetGroup OFFICIAL_CORRUPTION = builder(GOVERNMENT_OFFICIAL, ImmutableList.of(GOVERNMENT_CORRUPTION, OFFICIAL_AUTHORITY, OFFICIAL_TREATMENT_GOVERNMENT, OFFICIAL_TREATMENT_PEOPLE), "corruption", "Corruption among Officials", "", SYSTEM_LARGE);
//    public static final TenetGroup OFFICIAL_REMOVAL = builder(GOVERNMENT_OFFICIAL, ImmutableList.of(OFFICIAL_AUTHORITY, CLASS_AND_CASTE, DISENFRANCHISED), "removal", "Removal of an Official", "", SYSTEM_LARGE);
//    public static final TenetGroup SOFT_CULTURE_INTERVENTION = builder(GOVERNMENT_OFFICIAL, ImmutableList.of(SOFT_CULTURE), "soft_culture_interventionalism", "Soft Culture Intervention", "", SORT_ONLY);
//    public static final TenetGroup SECULARISM = builder(SOFT_CULTURE_INTERVENTION, ImmutableList.of(RELIGION, GOVERNMENT_LEADERSHIP), "secularism", "Secularism", "", SYSTEM_LARGE);
//    public static final TenetGroup GOVERNMENT_ENFORCED_CONFORMITY = builder(SOFT_CULTURE_INTERVENTION, ImmutableList.of(CIVIL_LIBERTIES, CLASS_AND_CASTE), "culture_war", "Conformity", "", SYSTEM_LARGE);
//    public static final TenetGroup EDUCATION_INTERVENTION = builder(SOFT_CULTURE_INTERVENTION, ImmutableList.of(EDUCATION, EDUCATION_FUNDING), "education_funding", "Education Intervention", "", SYSTEM_LARGE);
//    public static final TenetGroup FAMILY_INTERVENTION = builder(SOFT_CULTURE_INTERVENTION, ImmutableList.of(FAMILY), "family_intervention", "FamilyGroups Intervention", "", SYSTEM_LARGE);
//    public static final TenetGroup ESPIONAGE = builder(GOVERNMENT_SYSTEM, ImmutableList.of(MILITARY, CIVIL_LIBERTIES, JUSTICE_SYSTEM), "espionage", "Espionage", "", SORT_ONLY);
//    public static final TenetGroup SECRET_POLICE = builder(ESPIONAGE, ImmutableList.of(SOCIETY), "secret_police", "Secret Police & Mass Surveillance", "", SYSTEM_LARGE);
//    public static final TenetGroup FOREIGN_INTELLIGENCE_SERVICE = builder(ESPIONAGE, ImmutableList.of(FOREIGN_POLICY), "foreign_intelligence_service", "Foreign Intelligence Service", "", SYSTEM_LARGE);
//    public static final TenetGroup COVERT_OPERATIONS = builder(ESPIONAGE, ImmutableList.of(SPECIAL_OPERATIONS), "covert_operations", "Covert Operations", "", SYSTEM_LARGE);
//    public static final TenetGroup ACCOUNTABILITY = builder(ESPIONAGE, ImmutableList.of(GOVERNMENT_LEADERSHIP, GOVERNMENT_CORRUPTION), "accountability", "Accountability", "", SYSTEM_LARGE);
//
//    // GovernmentTenet Value Children
//    public static final TenetGroup GOVERNMENT_VALUE = builder(GOVERNMENT_IDEOLOGY, "value", "GovernmentTenet Value", "", new AcceptanceContainer(3, Acceptance.CORE, Acceptance.CORE_FANATIC));
//    public static final TenetGroup GOVERNMENT_HEROES_AND_VILLANS = builder(GOVERNMENT_IDEOLOGY, "heroes_and_villains", "Heroes and Villains", "", new AcceptanceContainer(3, Acceptance.CORE, Acceptance.CORE_FANATIC), new AcceptanceContainer(5, Acceptance.INTEGRATED));
//
// GOVERNMENT
public static final TenetGroup GOVERNMENT = builder(TGType.SORT_ONLY, HARD_CULTURE, "government", "GovernmentTenet", "");

    // Pillars
    public static final TenetGroup GOVERNMENT_SYSTEM = builder(TGType.PILLAR_SYSTEM, GOVERNMENT, "system", "GovernmentTenet System", "");
    public static final TenetGroup GOVERNMENT_IDEOLOGY = builder(TGType.PILLAR_IDEOLOGY, GOVERNMENT, ImmutableList.of(GOVERNMENT_SYSTEM), "ideology", "GovernmentTenet Ideology", "");

    // Government System
    public static final TenetGroup RESOURCE_ALLOCATION = builder(TGType.SYSTEM_LARGE, GOVERNMENT_SYSTEM, ImmutableList.of(ECONOMIC_SYSTEM), "resource_allocation", "Resource Allocation", "");
    public static final TenetGroup WELFARE = builder(TGType.SYSTEM_LARGE, RESOURCE_ALLOCATION, "welfare", "Welfare", "");
    public static final TenetGroup TAXATION = builder(TGType.SYSTEM_LARGE, RESOURCE_ALLOCATION, "taxation", "Taxation", "");
    public static final TenetGroup ECONOMIC_INTERVENTION = builder(TGType.SYSTEM_LARGE, RESOURCE_ALLOCATION, ImmutableList.of(ECONOMY), "economic_intervention", "Economic Intervention", "");
    public static final TenetGroup EDUCATION_FUNDING = builder(TGType.SYSTEM_LARGE, RESOURCE_ALLOCATION, ImmutableList.of(EDUCATION), "education_funding", "Education Intervention", "");
    public static final TenetGroup MILITARY_FUNDING = builder(TGType.SYSTEM_LARGE, RESOURCE_ALLOCATION, "military_funding", "Military Funding", "");
    public static final TenetGroup JUSTICE_SYSTEM = builder(TGType.SYSTEM_LARGE, GOVERNMENT_SYSTEM, "justice_system", "Justice System", "");
    public static final TenetGroup GOVERNMENT_CORRUPTION = builder(TGType.SYSTEM_LARGE, JUSTICE_SYSTEM, "corruption", "Corruption", "");
    public static final TenetGroup CLASS_AND_CASTE = builder(TGType.SYSTEM_SORT, GOVERNMENT_SYSTEM, ImmutableList.of(WELFARE, TAXATION), "class_and_caste", "Class and Caste", "");
    public static final TenetGroup CASTE_PERMEABILITY = builder(TGType.SYSTEM_LARGE, CLASS_AND_CASTE, "caste_permeability", "Caste Permeability", "");
    public static final TenetGroup CIVIL_LIBERTIES = builder(TGType.SYSTEM_LARGE, GOVERNMENT_SYSTEM, ImmutableList.of(JUSTICE_SYSTEM, CLASS_AND_CASTE), "civil_rights", "Civil Rights", "");
    public static final TenetGroup CITIZENSHIP = builder(TGType.SYSTEM_LARGE, CLASS_AND_CASTE, "citizenship", "Citizenship", "");
    public static final TenetGroup ELITE_CLASS = builder(TGType.CASTE_SYSTEM, CLASS_AND_CASTE, ImmutableList.of(CITIZENSHIP,CASTE_PERMEABILITY), "elites", "Elites", "");
    public static final TenetGroup PROFESSIONAL_CLASS = builder(TGType.CASTE_SYSTEM, CLASS_AND_CASTE, ImmutableList.of(CITIZENSHIP,CASTE_PERMEABILITY), "professional_class", "Professional Class", "");
    public static final TenetGroup MERCHANT_CLASS = builder(TGType.CASTE_SYSTEM, CLASS_AND_CASTE, ImmutableList.of(CITIZENSHIP,CASTE_PERMEABILITY), "poor_class", "Poor Class", "");
    public static final TenetGroup MIDDLE_CLASS = builder(TGType.CASTE_SYSTEM, CLASS_AND_CASTE, ImmutableList.of(CITIZENSHIP,CASTE_PERMEABILITY), "middle_class", "Middle Class", "");
    public static final TenetGroup SOLDIER_CLASS = builder(TGType.CASTE_SYSTEM, CLASS_AND_CASTE, ImmutableList.of(CITIZENSHIP,CASTE_PERMEABILITY), "soldiers", "Soldiers", "");
    public static final TenetGroup WORKING_CLASS = builder(TGType.CASTE_SYSTEM, CLASS_AND_CASTE, ImmutableList.of(CITIZENSHIP,CASTE_PERMEABILITY), "working_class", "Working Class", "");
    public static final TenetGroup DISENFRANCHISED = builder(TGType.CASTE_SYSTEM, CLASS_AND_CASTE,ImmutableList.of(CITIZENSHIP,CASTE_PERMEABILITY),  "disenfranchised", "Disenfranchised", "");
    public static final TenetGroup SLAVE = builder(TGType.CASTE_SYSTEM, CLASS_AND_CASTE, ImmutableList.of(DISENFRANCHISED), "slaves", "Slaves", "");
    public static final TenetGroup OUTSIDER = builder(TGType.CASTE_SYSTEM, CLASS_AND_CASTE, ImmutableList.of(CITIZENSHIP), "outsider", "Outsiders and Foreigners", "");
    public static final TenetGroup FOREIGN_POLICY = builder(TGType.SYSTEM_LARGE, GOVERNMENT_SYSTEM, "foreign_policy", "Foreign Policy", "");

    // Military
    public static final TenetGroup MILITARY = builder(TGType.SYSTEM_SORT, GOVERNMENT_SYSTEM, ImmutableList.of(MILITARY_FUNDING), "military", "Military", "");

    public static final TenetGroup MILITARY_INFLUENCE_ON_GOVERNMENT = builder(TGType.SYSTEM_LARGE, MILITARY, ImmutableList.of(CLASS_AND_CASTE, MILITARY), "military_influence", "Military Influence on GovernmentTenet", "");
    public static final TenetGroup MILITARY_LOGISTICS = builder(TGType.SYSTEM_LARGE, MILITARY, ImmutableList.of(MILITARY_FUNDING), "logistics", "Military Logistics", "");
    public static final TenetGroup MILITARY_RECRUITMENT = builder(TGType.SYSTEM_LARGE, MILITARY_LOGISTICS, ImmutableList.of(MILITARY_FUNDING), "recruitment", "Military Recruitment", "");
    public static final TenetGroup MILITARY_TRAINING = builder(TGType.SYSTEM_LARGE, MILITARY_LOGISTICS, ImmutableList.of(MILITARY_FUNDING), "training", "Military Training", "");
    public static final TenetGroup MILITARY_EQUIPMENT = builder(TGType.SYSTEM_LARGE, MILITARY_LOGISTICS, ImmutableList.of(MILITARY_FUNDING), "equipment", "Military Equipment and Logistics", "");
    public static final TenetGroup MILITARY_PROCUREMENT = builder(TGType.SYSTEM_LARGE, MILITARY_LOGISTICS, ImmutableList.of(MILITARY_EQUIPMENT), "procurement", "Military Procurement", "");
    public static final TenetGroup MILITARY_WOUNDED_AND_VETERANS = builder(TGType.SYSTEM_LARGE, MILITARY, "wounded_and_veterans", "Military Wounded and Veterans", "");
    public static final TenetGroup MILITARY_UPWARD_MOBILITY = builder(TGType.SYSTEM_LARGE, MILITARY, ImmutableList.of(CASTE_PERMEABILITY),"upward_mobility", "Military Upward Mobility", "");

    // Military Policy
    public static final TenetGroup MILITARY_POLICY = builder(TGType.SYSTEM_SORT, MILITARY, "ethics", "Military Policy and Doctrine", "");
    public static final TenetGroup SOLDIER_TREATMENT = builder(TGType.SYSTEM_LARGE, MILITARY_POLICY, ImmutableList.of(MILITARY_FUNDING, MILITARY_LOGISTICS, SOLDIER_CLASS), "soldier_treatment", "Soldier Treatment", "");
    public static final TenetGroup SPECIAL_OPERATIONS = builder(TGType.SYSTEM_LARGE, MILITARY_POLICY, ImmutableList.of(MILITARY_LOGISTICS), "special_operations", "Special Operations", "");
    public static final TenetGroup ORDER_OF_BATTLE = builder(TGType.SYSTEM_LARGE, MILITARY_POLICY, ImmutableList.of(MILITARY_LOGISTICS), "order_of_battle", "Order of Battle", "");
    public static final TenetGroup COMBAT_DOCTRINE = builder(TGType.SYSTEM_LARGE, MILITARY_POLICY, ImmutableList.of(MILITARY_LOGISTICS), "combat_doctrine", "Combat Doctrine", "");
    public static final TenetGroup RULES_OF_ENGAGEMENT = builder(TGType.SYSTEM_LARGE, MILITARY_POLICY, "use_of_force", "Use of Force", "");
    public static final TenetGroup MILITARY_JUSTICE = builder(TGType.SYSTEM_LARGE, MILITARY_POLICY, ImmutableList.of(JUSTICE_SYSTEM), "military_justice", "Military Justice", "");
    public static final TenetGroup MILITARY_INTERVENTION_IN_CIVIL_LIFE = builder(TGType.SYSTEM_LARGE, MILITARY_POLICY, ImmutableList.of(JUSTICE_SYSTEM, CIVIL_LIBERTIES, COMBAT_DOCTRINE, RULES_OF_ENGAGEMENT), "intervention_in_civil_life", "Military Intervention in Civil Life", "");
    public static final TenetGroup MILITARY_INTERVENTION_IN_FOREIGN_CONFLICT = builder(TGType.SYSTEM_LARGE, MILITARY_POLICY, ImmutableList.of(FOREIGN_POLICY, COMBAT_DOCTRINE, RULES_OF_ENGAGEMENT), "intervention_in_foreign_conflict", "Military Intervention in Foreign Conflict", "");

    // Military Leadership
    public static final TenetGroup MILITARY_LEADERSHIP = builder(TGType.SYSTEM_SORT, MILITARY, "leadership", "Military Leadership", "");
    public static final TenetGroup CENTRALIZATION = builder(TGType.SYSTEM_LARGE, MILITARY_LEADERSHIP, ImmutableList.of(COMBAT_DOCTRINE, ORDER_OF_BATTLE, MILITARY_UPWARD_MOBILITY, RULES_OF_ENGAGEMENT), "command_centralization", "Command Centralization", "");
    public static final TenetGroup OFFICER_APPOINTMENT = builder(TGType.SYSTEM_LARGE, MILITARY_LEADERSHIP, ImmutableList.of(CENTRALIZATION, MILITARY_RECRUITMENT, MILITARY_UPWARD_MOBILITY), "officer_appointment", "Officer Appointment", "");
    public static final TenetGroup OFFICER_REMOVAL = builder(TGType.SYSTEM_LARGE, MILITARY_LEADERSHIP, ImmutableList.of(MILITARY_JUSTICE), "officer_removal", "Officer Removal", "");
    public static final TenetGroup OFFICER_TREATMENT = builder(TGType.SYSTEM_LARGE, MILITARY_LEADERSHIP, ImmutableList.of(MILITARY_FUNDING), "officer_treatment", "Officer Treatment", "");
    public static final TenetGroup OFFICER_TRAINING = builder(TGType.SYSTEM_LARGE, MILITARY_LEADERSHIP, ImmutableList.of(MILITARY_FUNDING, MILITARY_TRAINING), "officer_training", "Officer Training", "");

    // Military Ideology
    public static final TenetGroup MILITARY_IDEOLOGY = builder(TGType.SYSTEM_SORT, MILITARY, "ideology", "Military Ideology", "");
    public static final TenetGroup MILITARY_SELF_CONCEPT = builder(TGType.SYSTEM_LARGE, MILITARY_IDEOLOGY, "self_concept", "Military Self-Concept", "");
    public static final TenetGroup MILITARY_IDEOLOGICAL_CAPTURE = builder(TGType.SYSTEM_LARGE, MILITARY_IDEOLOGY, "ideological_capture", "Military Ideological Capture", "");
    public static final TenetGroup SOLDIER_VALUE = builder(TGType.SYSTEM_LARGE, MILITARY_IDEOLOGY, ImmutableList.of(MILITARY_UPWARD_MOBILITY), "soldier_value", "Soldier Value", "");
    public static final TenetGroup OFFICER_VALUE = builder(TGType.SYSTEM_LARGE, MILITARY_IDEOLOGY, ImmutableList.of(MILITARY_UPWARD_MOBILITY, CENTRALIZATION), "officer_value", "Officer Value", "");
    public static final TenetGroup MILITARY_HEROES_AND_VILLAINS = builder(TGType.SYSTEM_LARGE, MILITARY_IDEOLOGY, "heroes_and_villains", "Heroes and Villains", "");

    // Military Loyalty & Corruption
    public static final TenetGroup MILITARY_LOYALTY = builder(TGType.SYSTEM_SORT, MILITARY, ImmutableList.of(MILITARY_JUSTICE, MILITARY_IDEOLOGICAL_CAPTURE, MILITARY_SELF_CONCEPT, OFFICER_TREATMENT, SOLDIER_TREATMENT), "loyalty", "Military Loyalty", "");
    public static final TenetGroup MILITARY_CORRUPTION = builder(TGType.SYSTEM_LARGE, MILITARY, ImmutableList.of(GOVERNMENT_CORRUPTION, MILITARY_JUSTICE, MILITARY_IDEOLOGICAL_CAPTURE, MILITARY_SELF_CONCEPT, OFFICER_TREATMENT, SOLDIER_TREATMENT, MILITARY_LOYALTY), "corruption", "Military Corruption", "");

    // Government Leadership
    public static final TenetGroup GOVERNMENT_LEADERSHIP = builder(TGType.SYSTEM_SORT, GOVERNMENT_SYSTEM, ImmutableList.of(CLASS_AND_CASTE, MILITARY), "leadership", "Leadership", "");
    public static final TenetGroup LEADER_CORRUPTION = builder(TGType.SYSTEM_LARGE, GOVERNMENT_LEADERSHIP, ImmutableList.of(GOVERNMENT_CORRUPTION), "corruption", "Leader Corruption", "");
    public static final TenetGroup LEADER_TYPES = builder(TGType.SYSTEM_SORT, GOVERNMENT_LEADERSHIP, "types", "Leader Types", "");
    public static final TenetGroup LEADERSHIP_SELECTION = builder(TGType.SYSTEM_LARGE, LEADER_TYPES, ImmutableList.of(DISENFRANCHISED), "selection", "Leadership Selection", "");
    public static final TenetGroup LEADER_AUTHORITY = builder(TGType.SYSTEM_LARGE, LEADER_TYPES, "authority", "Leader Authority", "");
    public static final TenetGroup LEADERSHIP_REMOVAL = builder(TGType.SYSTEM_LARGE, LEADER_TYPES, ImmutableList.of(GOVERNMENT_CORRUPTION,LEADER_CORRUPTION), "removal", "Leadership Removal", "");
    // Government Office
    public static final TenetGroup GOVERNMENT_OFFICE = builder(TGType.SYSTEM_LARGE, GOVERNMENT_SYSTEM, "office", "GovernmentTenet Office", "");
    public static final TenetGroup GOVERNMENT_OFFICE_AUTHORITY = builder(TGType.SYSTEM_LARGE, GOVERNMENT_OFFICE, "authority", "GovernmentTenet Office Authority", "");
    public static final TenetGroup GOVERNMENT_OFFICE_CREATION_REMOVAL = builder(TGType.SYSTEM_LARGE, GOVERNMENT_OFFICE, "creation_removal", "GovernmentTenet Office Creation and Removal", "");

    // Government Officials
    public static final TenetGroup GOVERNMENT_OFFICIAL = builder(TGType.SYSTEM_SORT, GOVERNMENT_OFFICE, ImmutableList.of(MILITARY,GOVERNMENT_LEADERSHIP), "official", "GovernmentTenet Official", "");
    public static final TenetGroup OFFICIAL_SELECTION = builder(TGType.SYSTEM_LARGE, GOVERNMENT_OFFICIAL, ImmutableList.of(CLASS_AND_CASTE, DISENFRANCHISED), "selection", "Selection of an Official", "");
    public static final TenetGroup OFFICIAL_AUTHORITY = builder(TGType.SYSTEM_LARGE, GOVERNMENT_OFFICIAL, "authority", "An Official's Authority", "");
    public static final TenetGroup OFFICIAL_TREATMENT_GOVERNMENT = builder(TGType.SYSTEM_LARGE, GOVERNMENT_OFFICIAL, "treatment_government", "An Official's Treatment by the GovernmentTenet", "");
    public static final TenetGroup OFFICIAL_TREATMENT_PEOPLE = builder(TGType.SYSTEM_LARGE, GOVERNMENT_OFFICIAL, "treatment_people", "An Official's Treatment by the People", "");
    public static final TenetGroup OFFICIAL_CORRUPTION = builder(TGType.SYSTEM_LARGE, GOVERNMENT_OFFICIAL, ImmutableList.of(GOVERNMENT_CORRUPTION, OFFICIAL_TREATMENT_PEOPLE, OFFICIAL_TREATMENT_GOVERNMENT, OFFICIAL_TREATMENT_PEOPLE), "corruption", "Corruption among Officials", "");
    public static final TenetGroup OFFICIAL_REMOVAL = builder(TGType.SYSTEM_LARGE, GOVERNMENT_OFFICIAL, ImmutableList.of(OFFICIAL_AUTHORITY, LEADER_CORRUPTION, OFFICIAL_CORRUPTION, CLASS_AND_CASTE), "removal", "Removal of an Official", "");

    // Soft Culture Intervention
    public static final TenetGroup SOFT_CULTURE_INTERVENTION = builder(TGType.SYSTEM_SORT, GOVERNMENT_OFFICIAL, ImmutableList.of(SOFT_CULTURE), "soft_culture_interventionalism", "Soft Culture Intervention", "");
    public static final TenetGroup SECULARISM = builder(TGType.SYSTEM_LARGE, SOFT_CULTURE_INTERVENTION, ImmutableList.of(RELIGION, GOVERNMENT_LEADERSHIP), "secularism", "Secularism", "");
    public static final TenetGroup GOVERNMENT_ENFORCED_CONFORMITY = builder(TGType.SYSTEM_LARGE, SOFT_CULTURE_INTERVENTION, ImmutableList.of(CIVIL_LIBERTIES, CLASS_AND_CASTE), "culture_war", "Conformity", "");
    public static final TenetGroup EDUCATION_INTERVENTION = builder(TGType.SYSTEM_LARGE, SOFT_CULTURE_INTERVENTION, ImmutableList.of(EDUCATION, EDUCATION_FUNDING), "education_funding", "Education Intervention", "");
    public static final TenetGroup FAMILY_INTERVENTION = builder(TGType.SYSTEM_LARGE, SOFT_CULTURE_INTERVENTION, ImmutableList.of(FAMILY), "family_intervention", "FamilyGroups Intervention", "");

    // Espionage
    public static final TenetGroup ESPIONAGE = builder(TGType.SYSTEM_SORT, GOVERNMENT_SYSTEM, ImmutableList.of(MILITARY, CIVIL_LIBERTIES, JUSTICE_SYSTEM), "espionage", "Espionage", "");
    public static final TenetGroup SECRET_POLICE = builder(TGType.SYSTEM_LARGE, ESPIONAGE, ImmutableList.of(SOCIETY), "secret_police", "Secret Police & Mass Surveillance", "");
    public static final TenetGroup FOREIGN_INTELLIGENCE_SERVICE = builder(TGType.SYSTEM_LARGE, ESPIONAGE, ImmutableList.of(FOREIGN_POLICY), "foreign_intelligence_service", "Foreign Intelligence Service", "");
    public static final TenetGroup COVERT_OPERATIONS = builder(TGType.SYSTEM_LARGE, ESPIONAGE, ImmutableList.of(SPECIAL_OPERATIONS), "covert_operations", "Covert Operations", "");
    public static final TenetGroup ACCOUNTABILITY = builder(TGType.SYSTEM_LARGE, ESPIONAGE, ImmutableList.of(GOVERNMENT_LEADERSHIP, GOVERNMENT_CORRUPTION), "accountability", "Accountability", "");

    // Government Ideology
    public static final TenetGroup GOVERNMENT_VALUE = builder(TGType.VALUE, GOVERNMENT_IDEOLOGY, "value", "GovernmentTenet Value", "");
    public static final TenetGroup GOVERNMENT_HEROES_AND_VILLANS = builder(TGType.VALUE, GOVERNMENT_IDEOLOGY, "heroes_and_villains", "Heroes and Villains", "");

    public static void init() {

    }
}
