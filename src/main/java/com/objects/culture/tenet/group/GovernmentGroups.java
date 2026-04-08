package com.objects.culture.tenet.group;

import com.google.common.collect.ImmutableList;
import com.objects.culture.tenet.Acceptance;

import static com.objects.culture.tenet.TenetManager.HARD_CULTURE;
import static com.objects.culture.tenet.TenetManager.SOFT_CULTURE;
import static com.objects.culture.tenet.group.EconomicGroups.ECONOMIC_SYSTEM;
import static com.objects.culture.tenet.group.EconomicGroups.ECONOMY;
import static com.objects.culture.tenet.group.EducationGroups.EDUCATION;
import static com.objects.culture.tenet.group.FamilyGroups.FAMILY;
import static com.objects.culture.tenet.group.ReligionGroups.RELIGION;
import static com.objects.culture.tenet.group.SocietyGroups.SOCIETY;
import static com.objects.culture.tenet.group.TenetGroup.*;

public class GovernmentGroups {

    public static final TenetGroup GOVERNMENT = builder(HARD_CULTURE, "government", "GovernmentTenet", "", SORT_ONLY);
    public static final TenetGroup GOVERNMENT_SYSTEM = builder(GOVERNMENT, "system", "GovernmentTenet System", "", PILLAR);
    public static final TenetGroup GOVERNMENT_IDEOLOGY = builder(GOVERNMENT, ImmutableList.of(GOVERNMENT_SYSTEM), "ideology", "GovernmentTenet Ideology", "", PILLAR);

    public static final TenetGroup RESOURCE_ALLOCATION = builder(GOVERNMENT_SYSTEM, ImmutableList.of(ECONOMIC_SYSTEM), "resource_allocation", "Resource Allocation", "", SYSTEM_LARGE);
    public static final TenetGroup WELFARE = builder(RESOURCE_ALLOCATION, "welfare", "Welfare", "", SYSTEM_LARGE);
    public static final TenetGroup TAXATION = builder(RESOURCE_ALLOCATION, "taxation", "Taxation", "", SYSTEM_LARGE);
    public static final TenetGroup ECONOMIC_INTERVENTION = builder(RESOURCE_ALLOCATION, ImmutableList.of(ECONOMY), "economic_intervention", "Economic Intervention", "", SYSTEM_LARGE);
    public static final TenetGroup EDUCATION_FUNDING = builder(RESOURCE_ALLOCATION, ImmutableList.of(EDUCATION), "education_funding", "Education Intervention", "", SYSTEM_LARGE);
    public static final TenetGroup MILITARY_FUNDING = builder(RESOURCE_ALLOCATION, "military_funding", "Military Funding", "", SYSTEM_LARGE);
    public static final TenetGroup JUSTICE_SYSTEM = builder(GOVERNMENT_SYSTEM, "justice_system", "Justice System", "", SYSTEM_LARGE);
    public static final TenetGroup GOVERNMENT_CORRUPTION = builder(JUSTICE_SYSTEM, "corruption", "Corruption", "", SYSTEM_LARGE);
    public static final TenetGroup CLASS_AND_CASTE = builder(GOVERNMENT_SYSTEM, ImmutableList.of(WELFARE, TAXATION), "class_and_caste", "Class and Caste", "", SYSTEM_LARGE);
    public static final TenetGroup CIVIL_LIBERTIES = builder(GOVERNMENT_SYSTEM, ImmutableList.of(JUSTICE_SYSTEM, CLASS_AND_CASTE), "civil_rights", "Civil Rights", "", SYSTEM_LARGE);
    public static final TenetGroup CITIZENSHIP = builder(CLASS_AND_CASTE, "citizenship", "Citizenship", "", SYSTEM_LARGE);
    public static final TenetGroup ELITE = builder(CLASS_AND_CASTE, ImmutableList.of(CITIZENSHIP), "elites", "Elites", "", SYSTEM_LARGE);
    public static final TenetGroup MIDDLE_CLASS = builder(CLASS_AND_CASTE, ImmutableList.of(CITIZENSHIP), "middle_class", "Middle Class", "", SYSTEM_LARGE);
    public static final TenetGroup SOLDIER = builder(CLASS_AND_CASTE, ImmutableList.of(CITIZENSHIP), "soldiers", "Soldiers", "", SYSTEM_LARGE);
    public static final TenetGroup WORKING_CLASS = builder(CLASS_AND_CASTE, ImmutableList.of(CITIZENSHIP), "working_class", "Working Class", "", SYSTEM_LARGE);
    public static final TenetGroup DISENFRANCHISED = builder(CLASS_AND_CASTE, "disenfranchised", "Disenfranchised", "", SYSTEM_LARGE);
    public static final TenetGroup SLAVE = builder(CLASS_AND_CASTE, ImmutableList.of(DISENFRANCHISED), "slaves", "Slaves", "", SYSTEM_LARGE);
    public static final TenetGroup OUTSIDER = builder(CLASS_AND_CASTE, ImmutableList.of(CITIZENSHIP), "outsider", "Outsiders and Foreigners", "", SYSTEM_LARGE);
    public static final TenetGroup FOREIGN_POLICY = builder(GOVERNMENT_SYSTEM, "foreign_policy", "Foreign Policy", "", SYSTEM_LARGE);

    public static final TenetGroup MILITARY = builder(GOVERNMENT_SYSTEM, ImmutableList.of(MILITARY_FUNDING), "military", "Military", "", PILLAR);
    public static final TenetGroup MILITARY_INFLUENCE_ON_GOVERNMENT = builder(MILITARY, ImmutableList.of(CLASS_AND_CASTE, MILITARY), "military_influence", "Military Influence on GovernmentTenet", "", SYSTEM_LARGE);
    public static final TenetGroup MILITARY_LOGISTICS = builder(MILITARY, ImmutableList.of(MILITARY_FUNDING), "logistics", "Military Logistics", "", SYSTEM_LARGE);
    public static final TenetGroup MILITARY_RECRUITMENT = builder(MILITARY_LOGISTICS, ImmutableList.of(MILITARY_FUNDING), "recruitment", "Military Recruitment", "", SYSTEM_LARGE);
    public static final TenetGroup MILITARY_TRAINING = builder(MILITARY_LOGISTICS, ImmutableList.of(MILITARY_FUNDING), "training", "Military Training", "", SYSTEM_LARGE);
    public static final TenetGroup MILITARY_EQUIPMENT = builder(MILITARY_LOGISTICS, ImmutableList.of(MILITARY_FUNDING), "equipment", "Military Equipment and Logistics", "", SYSTEM_LARGE);
    public static final TenetGroup MILITARY_PROCUREMENT = builder(MILITARY_LOGISTICS, ImmutableList.of(MILITARY_EQUIPMENT), "procurement", "Military Procurement", "", SYSTEM_LARGE);
    public static final TenetGroup MILITARY_WOUNDED_AND_VETERANS = builder(MILITARY, "wounded_and_veterans", "Military Wounded and Veterans", "", SYSTEM_LARGE);
    public static final TenetGroup MILITARY_UPWARD_MOBILITY = builder(MILITARY, "upward_mobility", "Military Upward Mobility", "", SYSTEM_LARGE);
    public static final TenetGroup MILITARY_POLICY = builder(MILITARY, "ethics", "Military Policy and Doctrine", "", SORT_ONLY);
    public static final TenetGroup SOLDIER_TREATMENT = builder(MILITARY_POLICY, ImmutableList.of(MILITARY_FUNDING, MILITARY_LOGISTICS, SOLDIER), "soldier_treatment", "Soldier Treatment", "", SYSTEM_LARGE);
    public static final TenetGroup SPECIAL_OPERATIONS = builder(MILITARY_POLICY, ImmutableList.of(MILITARY_LOGISTICS), "special_operations", "Special Operations", "", SYSTEM_LARGE);
    public static final TenetGroup ORDER_OF_BATTLE = builder(MILITARY_POLICY, ImmutableList.of(MILITARY_LOGISTICS), "order_of_battle", "Order of Battle", "", SYSTEM_LARGE);
    public static final TenetGroup COMBAT_DOCTRINE = builder(MILITARY_POLICY, ImmutableList.of(MILITARY_LOGISTICS), "combat_doctrine", "Combat Doctrine", "", SYSTEM_LARGE);
    public static final TenetGroup RULES_OF_ENGAGEMENT = builder(MILITARY_POLICY, "use_of_force", "Use of Force", "", SYSTEM_LARGE);
    public static final TenetGroup MILITARY_JUSTICE = builder(MILITARY_POLICY, ImmutableList.of(JUSTICE_SYSTEM), "military_justice", "Military Justice", "", SYSTEM_LARGE);
    public static final TenetGroup MILITARY_INTERVENTION_IN_CIVIL_LIFE = builder(MILITARY_POLICY, ImmutableList.of(JUSTICE_SYSTEM, CIVIL_LIBERTIES, COMBAT_DOCTRINE, RULES_OF_ENGAGEMENT), "intervention_in_civil_life", "Military Intervention in Civil Life", "", SYSTEM_LARGE);
    public static final TenetGroup MILITARY_INTERVENTION_IN_FOREIGN_CONFLICT = builder(MILITARY_POLICY, ImmutableList.of(FOREIGN_POLICY, COMBAT_DOCTRINE, RULES_OF_ENGAGEMENT), "intervention_in_foreign_conflict", "Military Intervention in Foreign Conflict", "", SYSTEM_LARGE);
    public static final TenetGroup MILITARY_LEADERSHIP = builder(MILITARY, "leadership", "Military Leadership", "", SORT_ONLY);
    public static final TenetGroup CENTRALIZATION = builder(MILITARY_LEADERSHIP, ImmutableList.of(COMBAT_DOCTRINE, ORDER_OF_BATTLE, MILITARY_UPWARD_MOBILITY, RULES_OF_ENGAGEMENT), "command_centralization", "Command Centralization", "", SYSTEM_LARGE);
    public static final TenetGroup OFFICER_APPOINTMENT = builder(MILITARY_LEADERSHIP, ImmutableList.of(CENTRALIZATION, MILITARY_RECRUITMENT, MILITARY_UPWARD_MOBILITY), "officer_appointment", "Officer Appointment", "", SYSTEM_LARGE);
    public static final TenetGroup OFFICER_REMOVAL = builder(MILITARY_LEADERSHIP, ImmutableList.of(MILITARY_JUSTICE), "officer_removal", "Officer Removal", "", SYSTEM_LARGE);
    public static final TenetGroup OFFICER_TREATMENT = builder(MILITARY_LEADERSHIP, ImmutableList.of(MILITARY_FUNDING), "officer_treatment", "Officer Treatment", "", SYSTEM_LARGE);
    public static final TenetGroup OFFICER_TRAINING = builder(MILITARY_LEADERSHIP, ImmutableList.of(MILITARY_FUNDING, MILITARY_TRAINING), "officer_training", "Officer Training", "", SYSTEM_LARGE);
    public static final TenetGroup MILITARY_IDEOLOGY = builder(MILITARY, "ideology", "Military Ideology", "", SORT_ONLY);
    public static final TenetGroup MILITARY_SELF_CONCEPT = builder(MILITARY_IDEOLOGY, "self_concept", "Military Self-Concept", "", SYSTEM_LARGE);
    public static final TenetGroup MILITARY_IDEOLOGICAL_CAPTURE = builder(MILITARY_IDEOLOGY, "ideological_capture", "Military Ideological Capture", "", SYSTEM_LARGE);
    public static final TenetGroup SOLDIER_VALUE = builder(MILITARY_IDEOLOGY, ImmutableList.of(MILITARY_UPWARD_MOBILITY), "soldier_value", "Soldier Value", "", SYSTEM_LARGE);
    public static final TenetGroup OFFICER_VALUE = builder(MILITARY_IDEOLOGY, ImmutableList.of(MILITARY_UPWARD_MOBILITY, CENTRALIZATION), "officer_value", "Officer Value", "", SYSTEM_LARGE);
    public static final TenetGroup MILITARY_HEROES_AND_VILLAINS = builder(MILITARY_IDEOLOGY, "heroes_and_villains", "Heroes and Villains", "", SYSTEM_LARGE);
    public static final TenetGroup MILITARY_LOYALTY = builder(MILITARY, ImmutableList.of(MILITARY_JUSTICE, MILITARY_IDEOLOGICAL_CAPTURE, MILITARY_SELF_CONCEPT, OFFICER_TREATMENT, SOLDIER_TREATMENT), "loyalty", "Military Loyalty", "", SORT_ONLY);
    public static final TenetGroup MILITARY_CORRUPTION = builder(MILITARY, ImmutableList.of(GOVERNMENT_CORRUPTION, MILITARY_JUSTICE, MILITARY_IDEOLOGICAL_CAPTURE, MILITARY_SELF_CONCEPT, OFFICER_TREATMENT, SOLDIER_TREATMENT, MILITARY_LOYALTY), "corruption", "Military Corruption", "", SYSTEM_LARGE);

    public static final TenetGroup GOVERNMENT_LEADERSHIP = builder(GOVERNMENT_SYSTEM, ImmutableList.of(CLASS_AND_CASTE, MILITARY), "leadership", "Leadership", "", SORT_ONLY);
    public static final TenetGroup LEADERSHIP_SELECTION = builder(GOVERNMENT_LEADERSHIP, ImmutableList.of(CLASS_AND_CASTE, DISENFRANCHISED), "selection", "Leadership Selection", "", SYSTEM_LARGE);
    public static final TenetGroup LEADER_AUTHORITY = builder(GOVERNMENT_LEADERSHIP, "authority", "Leader Authority", "", SYSTEM_LARGE);
    public static final TenetGroup LEADER_CORRUPTION = builder(GOVERNMENT_LEADERSHIP, ImmutableList.of(GOVERNMENT_CORRUPTION, LEADER_AUTHORITY), "corruption", "Leader Corruption", "", SYSTEM_LARGE);
    public static final TenetGroup LEADERSHIP_REMOVAL = builder(GOVERNMENT_LEADERSHIP, ImmutableList.of(LEADER_CORRUPTION), "removal", "Leadership Removal", "", SYSTEM_LARGE);
    public static final TenetGroup GOVERNMENT_OFFICE = builder(GOVERNMENT_SYSTEM, "office", "GovernmentTenet Office", "", SYSTEM_LARGE);
    public static final TenetGroup GOVERNMENT_OFFICE_AUTHORITY = builder(GOVERNMENT_OFFICE, "authority", "GovernmentTenet Office Authority", "", SYSTEM_LARGE);
    public static final TenetGroup GOVERNMENT_OFFICE_CREATION_REMOVAL = builder(GOVERNMENT_OFFICE, "creation_removal", "GovernmentTenet Office Creation and Removal", "", SYSTEM_LARGE);
    public static final TenetGroup GOVERNMENT_OFFICIAL = builder(GOVERNMENT_OFFICE, ImmutableList.of(MILITARY), "official", "GovernmentTenet Official", "", SORT_ONLY);
    public static final TenetGroup OFFICIAL_SELECTION = builder(GOVERNMENT_OFFICIAL, ImmutableList.of(CLASS_AND_CASTE, DISENFRANCHISED), "selection", "Selection of an Official", "", SYSTEM_LARGE);
    public static final TenetGroup OFFICIAL_AUTHORITY = builder(GOVERNMENT_OFFICIAL, "authority", "An Official's Authority", "", SYSTEM_LARGE);
    public static final TenetGroup OFFICIAL_TREATMENT_GOVERNMENT = builder(GOVERNMENT_OFFICIAL, "treatment_government", "An Official's Treatment by the GovernmentTenet", "", SYSTEM_LARGE);
    public static final TenetGroup OFFICIAL_TREATMENT_PEOPLE = builder(GOVERNMENT_OFFICIAL, "treatment_people", "An Official's Treatment by the People", "", SYSTEM_LARGE);
    public static final TenetGroup OFFICIAL_CORRUPTION = builder(GOVERNMENT_OFFICIAL, ImmutableList.of(GOVERNMENT_CORRUPTION, OFFICIAL_AUTHORITY, OFFICIAL_TREATMENT_GOVERNMENT, OFFICIAL_TREATMENT_PEOPLE), "corruption", "Corruption among Officials", "", SYSTEM_LARGE);
    public static final TenetGroup OFFICIAL_REMOVAL = builder(GOVERNMENT_OFFICIAL, ImmutableList.of(OFFICIAL_AUTHORITY, CLASS_AND_CASTE, DISENFRANCHISED), "removal", "Removal of an Official", "", SYSTEM_LARGE);
    public static final TenetGroup SOFT_CULTURE_INTERVENTION = builder(GOVERNMENT_OFFICIAL, ImmutableList.of(SOFT_CULTURE), "soft_culture_interventionalism", "Soft Culture Intervention", "", SORT_ONLY);
    public static final TenetGroup SECULARISM = builder(SOFT_CULTURE_INTERVENTION, ImmutableList.of(RELIGION, GOVERNMENT_LEADERSHIP), "secularism", "Secularism", "", SYSTEM_LARGE);
    public static final TenetGroup GOVERNMENT_ENFORCED_CONFORMITY = builder(SOFT_CULTURE_INTERVENTION, ImmutableList.of(CIVIL_LIBERTIES, CLASS_AND_CASTE), "culture_war", "Conformity", "", SYSTEM_LARGE);
    public static final TenetGroup EDUCATION_INTERVENTION = builder(SOFT_CULTURE_INTERVENTION, ImmutableList.of(EDUCATION, EDUCATION_FUNDING), "education_funding", "Education Intervention", "", SYSTEM_LARGE);
    public static final TenetGroup FAMILY_INTERVENTION = builder(SOFT_CULTURE_INTERVENTION, ImmutableList.of(FAMILY), "family_intervention", "FamilyGroups Intervention", "", SYSTEM_LARGE);
    public static final TenetGroup ESPIONAGE = builder(GOVERNMENT_SYSTEM, ImmutableList.of(MILITARY, CIVIL_LIBERTIES, JUSTICE_SYSTEM), "espionage", "Espionage", "", SORT_ONLY);
    public static final TenetGroup SECRET_POLICE = builder(ESPIONAGE, ImmutableList.of(SOCIETY), "secret_police", "Secret Police & Mass Surveillance", "", SYSTEM_LARGE);
    public static final TenetGroup FOREIGN_INTELLIGENCE_SERVICE = builder(ESPIONAGE, ImmutableList.of(FOREIGN_POLICY), "foreign_intelligence_service", "Foreign Intelligence Service", "", SYSTEM_LARGE);
    public static final TenetGroup COVERT_OPERATIONS = builder(ESPIONAGE, ImmutableList.of(SPECIAL_OPERATIONS), "covert_operations", "Covert Operations", "", SYSTEM_LARGE);
    public static final TenetGroup ACCOUNTABILITY = builder(ESPIONAGE, ImmutableList.of(GOVERNMENT_LEADERSHIP, GOVERNMENT_CORRUPTION), "accountability", "Accountability", "", SYSTEM_LARGE);

    // GovernmentTenet Value Children
    public static final TenetGroup GOVERNMENT_VALUE = builder(GOVERNMENT_IDEOLOGY, "value", "GovernmentTenet Value", "", new AcceptanceContainer(3, Acceptance.CORE, Acceptance.CORE_FANATIC));
    public static final TenetGroup GOVERNMENT_HEROES_AND_VILLANS = builder(GOVERNMENT_IDEOLOGY, "heroes_and_villains", "Heroes and Villains", "", new AcceptanceContainer(3, Acceptance.CORE, Acceptance.CORE_FANATIC), new AcceptanceContainer(5, Acceptance.INTEGRATED));



    public static void init() {

    }
}
