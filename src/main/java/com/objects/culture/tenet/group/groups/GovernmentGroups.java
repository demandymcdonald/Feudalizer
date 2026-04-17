package com.objects.culture.tenet.group.groups;

import com.objects.culture.tenet.group.TGType;
import com.objects.culture.tenet.group.TenetGroup;

import static com.objects.culture.tenet.TenetManager.HARD_CULTURE;
import static com.objects.culture.tenet.TenetManager.SOFT_CULTURE;
import static com.objects.culture.tenet.group.groups.EconomicGroups.*;
import static com.objects.culture.tenet.group.groups.EducationGroups.EDUCATION;
import static com.objects.culture.tenet.group.groups.FamilyGroups.FAMILY;
import static com.objects.culture.tenet.group.groups.MilitaryGroups.MILITARY;
import static com.objects.culture.tenet.group.groups.MilitaryGroups.SPECIAL_OPERATIONS;
import static com.objects.culture.tenet.group.groups.ReligionGroups.RELIGION;
import static com.objects.culture.tenet.group.groups.ReligionGroups.RELIGIOUS_GOVERNMENT_INTERVENTION;
import static com.objects.culture.tenet.group.groups.SocietyGroups.SOCIETY;
import static com.objects.culture.tenet.group.TenetGroup.*;

public class GovernmentGroups {

// GOVERNMENT



    public static final TenetGroup GOVERNMENT = new TenetGroup.Builder(TGType.SORT_ONLY, Level.PILLAR, "government", "Government", "")
            .setParent(HARD_CULTURE)
            .build();

    // Pillar
    public static final TenetGroup GOVERNMENT_SYSTEM = new TenetGroup.Builder(TGType.PILLAR_SYSTEM, Level.CATEGORY, "system", "Government System", "")
            .setParent(GOVERNMENT)
            .build();
    public static final TenetGroup GOVERNMENT_IDEOLOGY = new TenetGroup.Builder(TGType.PILLAR_IDEOLOGY, Level.CATEGORY, "ideology", "Government Ideology", "")
            .setParent(GOVERNMENT)
            .addDependent(GOVERNMENT_SYSTEM)
            .build();

    // Government System
    public static final TenetGroup RESOURCE_ALLOCATION = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.SUBCATEGORY, "resource_allocation", "Resource Allocation", "")
            .setParent(GOVERNMENT_SYSTEM)
            .addDependent(ECONOMIC_SYSTEM)
            .build();
    public static final TenetGroup WELFARE = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "welfare", "Welfare", "")
            .setParent(RESOURCE_ALLOCATION)
            .build();
    public static final TenetGroup TAXATION = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "taxation", "Taxation", "")
            .setParent(RESOURCE_ALLOCATION)
            .build();
    public static final TenetGroup ECONOMIC_INTERVENTION = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "economic_intervention", "Economic Intervention", "")
            .setParent(RESOURCE_ALLOCATION)
            .addConnected(ECONOMY)
            .build();
    public static final TenetGroup EDUCATION_FUNDING = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "education_funding", "Education Intervention", "")
            .setParent(RESOURCE_ALLOCATION)
            .addConnected(EDUCATION)
            .build();
    public static final TenetGroup MILITARY_FUNDING = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "military_funding", "Military Funding", "")
            .setParent(RESOURCE_ALLOCATION)
            .build();
    public static final TenetGroup GOVERNMENT_FUNDING = new TenetGroup.Builder(TGType.SYSTEM_LARGE,Level.NORMAL,"government_funding","Government Funding","")
            .setParent(RESOURCE_ALLOCATION)
            .build();
    public static final TenetGroup JUSTICE_SYSTEM = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "justice_system", "Justice System", "")
            .setParent(GOVERNMENT_SYSTEM)
            .build();
    public static final TenetGroup GOVERNMENT_CORRUPTION = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "corruption", "Corruption", "")
            .setParent(JUSTICE_SYSTEM)
            .build();
    public static final TenetGroup POPULATION_GROUP_RIGHTS = new TenetGroup.Builder(TGType.SORT_ONLY, Level.SUBCATEGORY,"rights","Population Rights","")
            .setParent(GOVERNMENT_SYSTEM)
            .build();
    public static final TenetGroup CITIZENSHIP = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "citizenship", "Citizenship", "")
            .setParent(POPULATION_GROUP_RIGHTS)
            .build();
    public static final TenetGroup MARRIAGE_RIGHTS = new TenetGroup.Builder(TGType.SYSTEM_LARGE,Level.NORMAL,"marriage","Marriage Rights","")
            .setParent(POPULATION_GROUP_RIGHTS)
            .build();
    public static final TenetGroup GOVERNMENT_PARTICIPATION = new TenetGroup.Builder(TGType.SYSTEM_LARGE,Level.NORMAL,"participation","Government Participation Rights","")
            .setParent(POPULATION_GROUP_RIGHTS)
            .build();
    public static final TenetGroup CIVIL_LIBERTIES = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "civil", "Civil Rights", "")
            .setParent(POPULATION_GROUP_RIGHTS)
            .addConnected(JUSTICE_SYSTEM)
            .build();
    public static final TenetGroup LABOR_RIGHTS = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "labor", "Labor Rights", "")
            .setParent(POPULATION_GROUP_RIGHTS)
            .addConnected(STATE_ENTERPRISE, PRIVATE_ENTERPRISE, LABOR_UNION)
            .build();
    public static final TenetGroup CLASS_AND_CASTE = new TenetGroup.Builder(TGType.SYSTEM_SORT, Level.SUBCATEGORY, "class_and_caste", "Class and Caste", "")
            .setParent(POPULATION_GROUP_RIGHTS)
            .build();
    public static final TenetGroup CASTE_PERMEABILITY = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "caste_permeability", "Caste Permeability", "")
            .setParent(CLASS_AND_CASTE)
            .build();
    public static final TenetGroup ELITE_CLASS = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "elites", "Elite Class", "")
            .setParent(CLASS_AND_CASTE)
            .addConnected(CASTE_PERMEABILITY, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup PROFESSIONAL_CLASS = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "professional_class", "Professional Class", "")
            .setParent(CLASS_AND_CASTE)
            .addConnected(CASTE_PERMEABILITY, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup ACADEMIC_CLASS = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "academic_class", "Academic Class", "")
            .setParent(CLASS_AND_CASTE)
            .addConnected(CASTE_PERMEABILITY, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup ARTIST_CLASS = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "artist_class", "Artist Class", "")
            .setParent(CLASS_AND_CASTE)
            .addConnected(CASTE_PERMEABILITY, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup OFFICER_CLASS = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "officer_class", "Officer Class", "")
            .setParent(CLASS_AND_CASTE)
            .addConnected(CASTE_PERMEABILITY, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup BUSINESS_CLASS = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "poor_class", "Poor Class", "")
            .setParent(CLASS_AND_CASTE)
            .addConnected(CASTE_PERMEABILITY, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup MIDDLE_CLASS = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "middle_class", "Middle Class", "")
            .setParent(CLASS_AND_CASTE)
            .addConnected(CASTE_PERMEABILITY, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup SOLDIER_CLASS = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "soldiers", "Soldier Ckass", "")
            .setParent(CLASS_AND_CASTE)
            .addConnected(CASTE_PERMEABILITY, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup WORKING_CLASS = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "working_class", "Working Class", "")
            .setParent(CLASS_AND_CASTE)
            .addConnected(CASTE_PERMEABILITY, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup DISENFRANCHISED = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "disenfranchised", "Disenfranchised", "")
            .setParent(CLASS_AND_CASTE)
            .addConnected(CASTE_PERMEABILITY, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup SLAVE = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "slaves", "Slaves", "")
            .setParent(CLASS_AND_CASTE)
            .addConnected(CASTE_PERMEABILITY, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup OUTSIDER = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "outsider", "Outsiders and Foreigners", "")
            .setParent(CLASS_AND_CASTE)
            .addConnected(CASTE_PERMEABILITY, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();

    public static final TenetGroup SEX = new TenetGroup.Builder(TGType.SYSTEM_SORT, Level.SUBCATEGORY, "sex", "Assigned Sex Rights", "")
            .setParent(POPULATION_GROUP_RIGHTS)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup SEX_MALE = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "male", "Male", "")
            .setParent(SEX)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup SEX_FEMALE = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "female", "Female", "")
            .setParent(SEX)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup GENDER = new TenetGroup.Builder(TGType.SYSTEM_SORT, Level.SUBCATEGORY, "gender", "Gender Rights", "")
            .setParent(POPULATION_GROUP_RIGHTS)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup GENDER_MALE = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "male", "Male Rights", "")
            .setParent(GENDER)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup GENDER_FEMALE = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "female", "Female Rights", "")
            .setParent(GENDER)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup GENDER_TRANS = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "trans", "Transgender Rights", "")
            .setParent(GENDER)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup GENDER_NB_OTHER = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "nb", "Non-Binary/Other Rights", "")
            .setParent(GENDER)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup ORIENTATION = new TenetGroup.Builder(TGType.SYSTEM_SORT, Level.SUBCATEGORY, "orientation", "Sexual Orientation Rights", "")
            .setParent(POPULATION_GROUP_RIGHTS)
            .build();
    public static final TenetGroup HETERO = new TenetGroup.Builder(TGType.CASTE_SYSTEM,Level.NORMAL,"hetero","Heterosexual Rights","")
            .setParent(ORIENTATION)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup HOMO = new TenetGroup.Builder(TGType.CASTE_SYSTEM,Level.NORMAL,"homo","Homosexual Rights","")
            .setParent(ORIENTATION)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup BI = new TenetGroup.Builder(TGType.CASTE_SYSTEM,Level.NORMAL,"bi","Bisexual Rights","")
            .setParent(ORIENTATION)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup AE = new TenetGroup.Builder(TGType.CASTE_SYSTEM,Level.NORMAL,"ae","Asexual Rights","")
            .setParent(ORIENTATION)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup PAN = new TenetGroup.Builder(TGType.CASTE_SYSTEM,Level.NORMAL,"pan","Pansexual Rights","")
            .setParent(ORIENTATION)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup QUESTIONING = new TenetGroup.Builder(TGType.CASTE_SYSTEM,Level.NORMAL,"questioning","Questioning Rights","")
            .setParent(ORIENTATION)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup OTHER = new TenetGroup.Builder(TGType.CASTE_SYSTEM,Level.NORMAL,"other","Other Rights","")
            .setParent(ORIENTATION)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup IDENTITY_GROUPS = new TenetGroup.Builder(TGType.SYSTEM_SORT, Level.SUBCATEGORY, "identity_groups", "Identity Group  Rights", "")
            .setParent(POPULATION_GROUP_RIGHTS)
            .build();
    public static final TenetGroup RACES_ETHNICITIES = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "racial_ethnicity", "Racial and Ethnic Rights", "")
            .setParent(IDENTITY_GROUPS)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup RELIGIOUS = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "religious", "Religious Rights", "")
            .setParent(IDENTITY_GROUPS)
            .addConnected(RELIGIOUS_GOVERNMENT_INTERVENTION)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup CULTURAL = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "cultural", "Cultural Rights", "")
            .setParent(IDENTITY_GROUPS)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup DISABILITY = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "disability", "Disability Rights", "")
            .setParent(IDENTITY_GROUPS)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup POLITICAL = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "political", "Political Rights", "")
            .setParent(IDENTITY_GROUPS)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();

    public static final TenetGroup FOREIGN_POLICY = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "foreign_policy", "Foreign Policy", "")
            .setParent(GOVERNMENT_SYSTEM)
            .build();

    // Government Leadership
    public static final TenetGroup GOVERNMENT_LEADERSHIP = new TenetGroup.Builder(TGType.SYSTEM_SORT, Level.SUBCATEGORY, "leadership", "Leadership", "")
            .setParent(GOVERNMENT_SYSTEM)
            .build();
    public static final TenetGroup LEADER_CORRUPTION = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "corruption", "Leader Corruption", "")
            .setParent(GOVERNMENT_LEADERSHIP)
            .addConnected(GOVERNMENT_CORRUPTION)
            .build();
    public static final TenetGroup LEADER_TYPES = new TenetGroup.Builder(TGType.SYSTEM_SORT, Level.SUBCATEGORY, "types", "Leader Types", "")
            .setParent(GOVERNMENT_LEADERSHIP)
            .build();
    public static final TenetGroup LEADER_SELECTION = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "selection", "Leadership Selection", "")
            .setParent(LEADER_TYPES)
            .addConnected(DISENFRANCHISED,CIVIL_LIBERTIES,CASTE_PERMEABILITY)
            .addDependent(GOVERNMENT_PARTICIPATION)
            .build();
    public static final TenetGroup LEADER_AUTHORITY = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "authority", "Leader Authority", "")
            .setParent(LEADER_TYPES)
            .build();
    public static final TenetGroup LEADER_REMOVAL = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "removal", "Leadership Removal", "")
            .setParent(LEADER_TYPES)
            .addConnected(GOVERNMENT_CORRUPTION, LEADER_CORRUPTION)
            .build();
    public static final TenetGroup LEADER_GENERAL = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "general", "Leader General", "")
            .setParent(LEADER_TYPES)
            .build();

    // Government Office
    public static final TenetGroup GOVERNMENT_OFFICE = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "office", "GovernmentTenet Office", "")
            .setParent(GOVERNMENT_SYSTEM)
            .build();
    public static final TenetGroup GOVERNMENT_OFFICE_AUTHORITY = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "authority", "GovernmentTenet Office Authority", "")
            .setParent(GOVERNMENT_OFFICE)
            .build();
    public static final TenetGroup GOVERNMENT_OFFICE_CREATION_REMOVAL = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "creation_removal", "GovernmentTenet Office Creation and Removal", "")
            .setParent(GOVERNMENT_OFFICE)
            .addDependent(GOVERNMENT_FUNDING)
            .build();

    // Government Officials
    public static final TenetGroup GOVERNMENT_OFFICIAL = new TenetGroup.Builder(TGType.SYSTEM_SORT, Level.SUBCATEGORY, "official", "GovernmentTenet Official", "")
            .setParent(GOVERNMENT_OFFICE)
            .addConnected(MILITARY, GOVERNMENT_LEADERSHIP)
            .build();
    public static final TenetGroup OFFICIAL_TYPES = new TenetGroup.Builder(TGType.SYSTEM_SORT, Level.NORMAL, "types", "Leader Types", "")
            .setParent(GOVERNMENT_OFFICIAL)
            .build();
    public static final TenetGroup OFFICIAL_SELECTION = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "selection", "Selection of an Official", "")
            .setParent(GOVERNMENT_OFFICIAL)
            .addConnected(CLASS_AND_CASTE, DISENFRANCHISED)
            .build();
    public static final TenetGroup OFFICIAL_AUTHORITY = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "authority", "An Official's Authority", "")
            .setParent(GOVERNMENT_OFFICIAL)
            .build();
    public static final TenetGroup OFFICIAL_TREATMENT_GOVERNMENT = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "treatment_government", "An Official's Treatment by the GovernmentTenet", "")
            .setParent(GOVERNMENT_OFFICIAL)
            .build();
    public static final TenetGroup OFFICIAL_TREATMENT_PEOPLE = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "treatment_people", "An Official's Treatment by the People", "")
            .setParent(GOVERNMENT_OFFICIAL)
            .build();
    public static final TenetGroup OFFICIAL_CORRUPTION = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "corruption", "Corruption among Officials", "")
            .setParent(GOVERNMENT_OFFICIAL)
            .addConnected(GOVERNMENT_CORRUPTION, OFFICIAL_TREATMENT_PEOPLE, OFFICIAL_TREATMENT_GOVERNMENT)
            .build();
    public static final TenetGroup OFFICIAL_REMOVAL = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "removal", "Removal of an Official", "")
            .setParent(GOVERNMENT_OFFICIAL)
            .addConnected(OFFICIAL_AUTHORITY, LEADER_CORRUPTION, OFFICIAL_CORRUPTION, CLASS_AND_CASTE)
            .build();
    public static final TenetGroup OFFICIAL_GENERAL = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "general", "General Official", "")
            .setParent(GOVERNMENT_OFFICIAL)
            .build();

    // Soft Culture Intervention
    public static final TenetGroup SOFT_CULTURE_INTERVENTION = new TenetGroup.Builder(TGType.SYSTEM_SORT, Level.SUBCATEGORY, "soft_culture_interventionalism", "Soft Culture Intervention", "")
            .setParent(GOVERNMENT_OFFICIAL)
            .addConnected(SOFT_CULTURE)
            .build();
    public static final TenetGroup SECULARISM = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "secularism", "Secularism", "")
            .setParent(SOFT_CULTURE_INTERVENTION)
            .addConnected(RELIGION, GOVERNMENT_LEADERSHIP)
            .build();
    public static final TenetGroup GOVERNMENT_ENFORCED_CONFORMITY = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "culture_war", "Conformity", "")
            .setParent(SOFT_CULTURE_INTERVENTION)
            .addConnected(CIVIL_LIBERTIES, CLASS_AND_CASTE)
            .build();
    public static final TenetGroup EDUCATION_INTERVENTION = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "education_funding", "Education Intervention", "")
            .setParent(SOFT_CULTURE_INTERVENTION)
            .addConnected(EDUCATION, EDUCATION_FUNDING)
            .build();
    public static final TenetGroup FAMILY_INTERVENTION = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "family_intervention", "FamilyGroups Intervention", "")
            .setParent(SOFT_CULTURE_INTERVENTION)
            .addConnected(FAMILY)
            .build();

    // Espionage
    public static final TenetGroup ESPIONAGE = new TenetGroup.Builder(TGType.SYSTEM_SORT, Level.SUBCATEGORY, "espionage", "Espionage", "")
            .setParent(GOVERNMENT_SYSTEM)
            .addConnected(MILITARY, CIVIL_LIBERTIES, JUSTICE_SYSTEM)
            .build();
    public static final TenetGroup SECRET_POLICE = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "secret_police", "Secret Police & Mass Surveillance", "")
            .setParent(ESPIONAGE)
            .addConnected(SOCIETY)
            .build();
    public static final TenetGroup FOREIGN_INTELLIGENCE_SERVICE = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "foreign_intelligence_service", "Foreign Intelligence Service", "")
            .setParent(ESPIONAGE)
            .addConnected(FOREIGN_POLICY)
            .build();
    public static final TenetGroup COVERT_OPERATIONS = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "covert_operations", "Covert Operations", "")
            .setParent(ESPIONAGE)
            .addConnected(SPECIAL_OPERATIONS)
            .build();
    public static final TenetGroup ACCOUNTABILITY = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "accountability", "Accountability", "")
            .setParent(ESPIONAGE)
            .addConnected(GOVERNMENT_LEADERSHIP, GOVERNMENT_CORRUPTION)
            .build();

    // Government Ideology
    public static final TenetGroup GOVERNMENT_VALUE = new TenetGroup.Builder(TGType.VALUE, Level.NORMAL, "value", "GovernmentTenet Value", "")
            .setParent(GOVERNMENT_IDEOLOGY)
            .build();
    public static final TenetGroup GOVERNMENT_HEROES_AND_VILLANS = new TenetGroup.Builder(TGType.VALUE, Level.NORMAL, "heroes_and_villains", "Heroes and Villains", "")
            .setParent(GOVERNMENT_IDEOLOGY)
            .build();

    public static void init() {

    }
}
