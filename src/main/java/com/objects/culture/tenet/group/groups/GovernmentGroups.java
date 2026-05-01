package com.objects.culture.tenet.group.groups;

import com.base.component.InstanceType;
import com.objects.culture.tenet.group.TGType;
import com.objects.culture.tenet.group.TenetGroup;
import org.checkerframework.checker.nullness.qual.NonNull;

import static com.objects.culture.TenetManager.HARD_CULTURE;
import static com.objects.culture.TenetManager.SOFT_CULTURE;
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
public static class GovernmentGroup extends TenetGroup {
    public GovernmentGroup(InstanceType instType, @NonNull TGType type, Level level, String fullID, String id, String name, String description) {
        super(instType,type, level,fullID, id, name, description);
    }
}


    public static final GovernmentGroup GOVERNMENT = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SORT_ONLY, Level.PILLAR, "government", "Government", "")
            .setParent(HARD_CULTURE)
            .build();

    // Pillar
    public static final GovernmentGroup GOVERNMENT_SYSTEM = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.PILLAR_SYSTEM, Level.CATEGORY, "system", "Government System", "")
            .setParent(GOVERNMENT)
            .build();
    public static final GovernmentGroup GOVERNMENT_IDEOLOGY = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.PILLAR_IDEOLOGY, Level.CATEGORY, "ideology", "Government Ideology", "")
            .setParent(GOVERNMENT)
            .addDependent(GOVERNMENT_SYSTEM)
            .build();

    // Government System
    public static final GovernmentGroup RESOURCE_ALLOCATION = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.SUBCATEGORY, "resource_allocation", "PRODUCT Allocation", "")
            .setParent(GOVERNMENT_SYSTEM)
            .addDependent(ECONOMIC_SYSTEM)
            .build();
    public static final GovernmentGroup WELFARE = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "welfare", "Welfare", "")
            .setParent(RESOURCE_ALLOCATION)
            .build();
    public static final GovernmentGroup TAXATION = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "taxation", "Taxation", "")
            .setParent(RESOURCE_ALLOCATION)
            .build();
    public static final GovernmentGroup ECONOMIC_INTERVENTION = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "economic_intervention", "Economic Intervention", "")
            .setParent(RESOURCE_ALLOCATION)
            .addConnected(ECONOMY)
            .build();
    public static final GovernmentGroup EDUCATION_FUNDING = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "education_funding", "Education Intervention", "")
            .setParent(RESOURCE_ALLOCATION)
            .addConnected(EDUCATION)
            .build();
    public static final GovernmentGroup MILITARY_FUNDING = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "military_funding", "Military Funding", "")
            .setParent(RESOURCE_ALLOCATION)
            .build();
    public static final GovernmentGroup GOVERNMENT_FUNDING = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE,Level.NORMAL,"government_funding","Government Funding","")
            .setParent(RESOURCE_ALLOCATION)
            .build();
    public static final GovernmentGroup JUSTICE_SYSTEM = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "justice_system", "Justice System", "")
            .setParent(GOVERNMENT_SYSTEM)
            .build();
    public static final GovernmentGroup GOVERNMENT_CORRUPTION = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "corruption", "Corruption", "")
            .setParent(JUSTICE_SYSTEM)
            .build();
    public static final GovernmentGroup REGULATED_JOBS = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "regulated_jobs", "Regulated Jobs", "")
            .setParent(GOVERNMENT_SYSTEM)
            .build();
    public static final GovernmentGroup POPULATION_GROUP_RIGHTS = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SORT_ONLY, Level.SUBCATEGORY,"rights","Population Rights","")
            .setParent(GOVERNMENT_SYSTEM)
            .build();

    public static final GovernmentGroup CITIZENSHIP = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "citizenship", "Citizenship", "")
            .setParent(POPULATION_GROUP_RIGHTS)
            .build();
    public static final GovernmentGroup MARRIAGE_RIGHTS = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE,Level.NORMAL,"marriage","Marriage Rights","")
            .setParent(POPULATION_GROUP_RIGHTS)
            .build();
    public static final GovernmentGroup GOVERNMENT_PARTICIPATION = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE,Level.NORMAL,"participation","Government Participation Rights","")
            .setParent(POPULATION_GROUP_RIGHTS)
            .build();
    public static final GovernmentGroup CIVIL_LIBERTIES = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "civil", "Civil Rights", "")
            .setParent(POPULATION_GROUP_RIGHTS)
            .addConnected(JUSTICE_SYSTEM)
            .build();
    public static final GovernmentGroup LABOR_RIGHTS = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "labor", "Labor Rights", "")
            .setParent(POPULATION_GROUP_RIGHTS)
            .addConnected(STATE_ENTERPRISE, PRIVATE_ENTERPRISE, LABOR_UNION)
            .build();
    public static final GovernmentGroup CLASS_AND_CASTE = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_SORT, Level.SUBCATEGORY, "class_and_caste", "Class and Caste", "")
            .setParent(POPULATION_GROUP_RIGHTS)
            .build();
    public static final GovernmentGroup CASTE_PERMEABILITY = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "caste_permeability", "Caste Permeability", "")
            .setParent(CLASS_AND_CASTE)
            .build();
    public static final GovernmentGroup ELITE_CLASS = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM, Level.NORMAL, "elites", "Elite Class", "")
            .setParent(CLASS_AND_CASTE)
            .addConnected(CASTE_PERMEABILITY, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup PROFESSIONAL_CLASS = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM, Level.NORMAL, "professional_class", "Professional Class", "")
            .setParent(CLASS_AND_CASTE)
            .addConnected(CASTE_PERMEABILITY, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup ACADEMIC_CLASS = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM, Level.NORMAL, "academic_class", "Academic Class", "")
            .setParent(CLASS_AND_CASTE)
            .addConnected(CASTE_PERMEABILITY, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup ARTIST_CLASS = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM, Level.NORMAL, "artist_class", "Artist Class", "")
            .setParent(CLASS_AND_CASTE)
            .addConnected(CASTE_PERMEABILITY, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup OFFICER_CLASS = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM, Level.NORMAL, "officer_class", "Officer Class", "")
            .setParent(CLASS_AND_CASTE)
            .addConnected(CASTE_PERMEABILITY, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup BUSINESS_CLASS = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM, Level.NORMAL, "poor_class", "Poor Class", "")
            .setParent(CLASS_AND_CASTE)
            .addConnected(CASTE_PERMEABILITY, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup MIDDLE_CLASS = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM, Level.NORMAL, "middle_class", "Middle Class", "")
            .setParent(CLASS_AND_CASTE)
            .addConnected(CASTE_PERMEABILITY, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup SOLDIER_CLASS = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM, Level.NORMAL, "soldiers", "Soldier Ckass", "")
            .setParent(CLASS_AND_CASTE)
            .addConnected(CASTE_PERMEABILITY, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup WORKING_CLASS = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM, Level.NORMAL, "working_class", "Working Class", "")
            .setParent(CLASS_AND_CASTE)
            .addConnected(CASTE_PERMEABILITY, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup DISENFRANCHISED = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM, Level.NORMAL, "disenfranchised", "Disenfranchised", "")
            .setParent(CLASS_AND_CASTE)
            .addConnected(CASTE_PERMEABILITY, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup SLAVE = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM, Level.NORMAL, "slaves", "Slaves", "")
            .setParent(CLASS_AND_CASTE)
            .addConnected(CASTE_PERMEABILITY, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup OUTSIDER = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM, Level.NORMAL, "outsider", "Outsiders and Foreigners", "")
            .setParent(CLASS_AND_CASTE)
            .addConnected(CASTE_PERMEABILITY, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();

    public static final GovernmentGroup SEX = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_SORT, Level.SUBCATEGORY, "sex", "Assigned Sex Rights", "")
            .setParent(POPULATION_GROUP_RIGHTS)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup SEX_MALE = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM, Level.NORMAL, "male", "Male", "")
            .setParent(SEX)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup SEX_FEMALE = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM, Level.NORMAL, "female", "Female", "")
            .setParent(SEX)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup GENDER = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_SORT, Level.SUBCATEGORY, "gender", "Gender Rights", "")
            .setParent(POPULATION_GROUP_RIGHTS)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup GENDER_MALE = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM, Level.NORMAL, "male", "Male Rights", "")
            .setParent(GENDER)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup GENDER_FEMALE = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM, Level.NORMAL, "female", "Female Rights", "")
            .setParent(GENDER)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup GENDER_TRANS = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM, Level.NORMAL, "trans", "Transgender Rights", "")
            .setParent(GENDER)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup GENDER_NB_OTHER = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM, Level.NORMAL, "nb", "Non-Binary/Other Rights", "")
            .setParent(GENDER)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup ORIENTATION = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_SORT, Level.SUBCATEGORY, "orientation", "Sexual Orientation Rights", "")
            .setParent(POPULATION_GROUP_RIGHTS)
            .build();
    public static final GovernmentGroup HETERO = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM,Level.NORMAL,"hetero","Heterosexual Rights","")
            .setParent(ORIENTATION)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup HOMO = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM,Level.NORMAL,"homo","Homosexual Rights","")
            .setParent(ORIENTATION)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup BI = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM,Level.NORMAL,"bi","Bisexual Rights","")
            .setParent(ORIENTATION)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup AE = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM,Level.NORMAL,"ae","Asexual Rights","")
            .setParent(ORIENTATION)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup PAN = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM,Level.NORMAL,"pan","Pansexual Rights","")
            .setParent(ORIENTATION)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup QUESTIONING = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM,Level.NORMAL,"questioning","Questioning Rights","")
            .setParent(ORIENTATION)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup OTHER = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM,Level.NORMAL,"other","Other Rights","")
            .setParent(ORIENTATION)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup IDENTITY_GROUPS = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_SORT, Level.SUBCATEGORY, "identity_groups", "Identity Group  Rights", "")
            .setParent(POPULATION_GROUP_RIGHTS)
            .build();
    public static final GovernmentGroup RACES_ETHNICITIES = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM, Level.NORMAL, "racial_ethnicity", "Racial and Ethnic Rights", "")
            .setParent(IDENTITY_GROUPS)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup RELIGIOUS = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM, Level.NORMAL, "religious", "Religious Rights", "")
            .setParent(IDENTITY_GROUPS)
            .addConnected(RELIGIOUS_GOVERNMENT_INTERVENTION)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup CULTURAL = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM, Level.NORMAL, "cultural", "Cultural Rights", "")
            .setParent(IDENTITY_GROUPS)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup DISABILITY = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM, Level.NORMAL, "disability", "Disability Rights", "")
            .setParent(IDENTITY_GROUPS)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup POLITICAL = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM, Level.NORMAL, "political", "Political Rights", "")
            .setParent(IDENTITY_GROUPS)
            .addDependent(MARRIAGE_RIGHTS,CITIZENSHIP,LABOR_RIGHTS,CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup EDUCATION_LEVEL = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_SORT, Level.SUBCATEGORY, "education_level", "Education Level Rights", "")
            .setParent(POPULATION_GROUP_RIGHTS)
            .build();
    public static final GovernmentGroup UNEDUCATED = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM, Level.NORMAL, "uneducated", "Uneducated Rights", "")
            .setParent(EDUCATION_LEVEL)
            .addDependent(MARRIAGE_RIGHTS, CITIZENSHIP, LABOR_RIGHTS, CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup BASIC_EDUCATION = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM, Level.NORMAL, "basic_education", "Basic Education Rights", "")
            .setParent(EDUCATION_LEVEL)
            .addDependent(MARRIAGE_RIGHTS, CITIZENSHIP, LABOR_RIGHTS, CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup TRADES_EDUCATION = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM, Level.NORMAL, "trades", "Trades Education Rights", "")
            .setParent(EDUCATION_LEVEL)
            .addDependent(MARRIAGE_RIGHTS, CITIZENSHIP, LABOR_RIGHTS, CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup COLLEGE_EDUCATED = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM, Level.NORMAL, "college_educated", "College Educated Rights", "")
            .setParent(EDUCATION_LEVEL)
            .addDependent(MARRIAGE_RIGHTS, CITIZENSHIP, LABOR_RIGHTS, CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup HYPER_COLLEGE_EDUCATED = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.CASTE_SYSTEM, Level.NORMAL, "graduate_college_educated", "Post-Graduate College Educated Rights", "")
            .setParent(EDUCATION_LEVEL)
            .addDependent(MARRIAGE_RIGHTS, CITIZENSHIP, LABOR_RIGHTS, CIVIL_LIBERTIES)
            .build();
    public static final GovernmentGroup FOREIGN_POLICY = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "foreign_policy", "Foreign Policy", "")
            .setParent(GOVERNMENT_SYSTEM)
            .build();

    // Government Leadership
    public static final GovernmentGroup GOVERNMENT_LEADERSHIP = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_SORT, Level.CATEGORY, "leadership", "Leadership", "")
            .setParent(GOVERNMENT_SYSTEM)
            .build();
    public static final GovernmentGroup LEADER_CORRUPTION = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "corruption", "Leader Corruption", "")
            .setParent(GOVERNMENT_LEADERSHIP)
            .addConnected(GOVERNMENT_CORRUPTION)
            .build();
    public static final GovernmentGroup LEADER_TYPES = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_SORT, Level.SUBCATEGORY, "types", "Leader Types", "")
            .setParent(GOVERNMENT_LEADERSHIP)
            .build();
    public static final GovernmentGroup LEADER_SELECTION = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "selection", "Leadership Selection", "")
            .setParent(LEADER_TYPES)
            .addConnected(DISENFRANCHISED,CIVIL_LIBERTIES,CASTE_PERMEABILITY)
            .addDependent(GOVERNMENT_PARTICIPATION)
            .build();
    public static final GovernmentGroup LEADER_AUTHORITY = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "authority", "Leader Authority", "")
            .setParent(LEADER_TYPES)
            .build();
    public static final GovernmentGroup LEADER_REMOVAL = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "removal", "Leadership Removal", "")
            .setParent(LEADER_TYPES)
            .addConnected(GOVERNMENT_CORRUPTION, LEADER_CORRUPTION)
            .build();
    public static final GovernmentGroup LEADER_GENERAL = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "general", "Leader General", "")
            .setParent(LEADER_TYPES)
            .build();

    // Government Office
    public static final GovernmentGroup GOVERNMENT_OFFICE = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.CATEGORY, "office", "GovernmentTenet Office", "")
            .setParent(GOVERNMENT_SYSTEM)
            .build();
    public static final GovernmentGroup GOVERNMENT_OFFICE_AUTHORITY = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "authority", "GovernmentTenet Office Authority", "")
            .setParent(GOVERNMENT_OFFICE)
            .build();
    public static final GovernmentGroup GOVERNMENT_OFFICE_CREATION_REMOVAL = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "creation_removal", "GovernmentTenet Office Creation and Removal", "")
            .setParent(GOVERNMENT_OFFICE)
            .addDependent(GOVERNMENT_FUNDING)
            .build();
    // Government Officials
    public static final GovernmentGroup GOVERNMENT_OFFICIAL = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_SORT, Level.SUBCATEGORY, "official", "GovernmentTenet Official", "")
            .setParent(GOVERNMENT_OFFICE)
            .addConnected(MILITARY, GOVERNMENT_LEADERSHIP)
            .build();
    public static final GovernmentGroup OFFICIAL_TYPES = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_SORT, Level.NORMAL, "types", "Leader Types", "")
            .setParent(GOVERNMENT_OFFICIAL)
            .build();
    public static final GovernmentGroup OFFICIAL_SELECTION = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "selection", "Selection of an Official", "")
            .setParent(GOVERNMENT_OFFICIAL)
            .addConnected(CLASS_AND_CASTE, DISENFRANCHISED)
            .build();
    public static final GovernmentGroup OFFICIAL_AUTHORITY = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "authority", "An Official's Authority", "")
            .setParent(GOVERNMENT_OFFICIAL)
            .build();
    public static final GovernmentGroup OFFICIAL_TREATMENT_GOVERNMENT = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "treatment_government", "An Official's Treatment by the GovernmentTenet", "")
            .setParent(GOVERNMENT_OFFICIAL)
            .build();
    public static final GovernmentGroup OFFICIAL_TREATMENT_PEOPLE = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "treatment_people", "An Official's Treatment by the People", "")
            .setParent(GOVERNMENT_OFFICIAL)
            .build();
    public static final GovernmentGroup OFFICIAL_CORRUPTION = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "corruption", "Corruption among Officials", "")
            .setParent(GOVERNMENT_OFFICIAL)
            .addConnected(GOVERNMENT_CORRUPTION, OFFICIAL_TREATMENT_PEOPLE, OFFICIAL_TREATMENT_GOVERNMENT)
            .build();
    public static final GovernmentGroup OFFICIAL_REMOVAL = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "removal", "Removal of an Official", "")
            .setParent(GOVERNMENT_OFFICIAL)
            .addConnected(OFFICIAL_AUTHORITY, LEADER_CORRUPTION, OFFICIAL_CORRUPTION, CLASS_AND_CASTE)
            .build();
    public static final GovernmentGroup OFFICIAL_GENERAL = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "general", "General Official", "")
            .setParent(GOVERNMENT_OFFICIAL)
            .build();

    // Soft Culture Intervention
    public static final GovernmentGroup SOFT_CULTURE_INTERVENTION = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_SORT, Level.SUBCATEGORY, "soft_culture_interventionalism", "Soft Culture Intervention", "")
            .setParent(GOVERNMENT_OFFICIAL)
            .addConnected(SOFT_CULTURE)
            .build();
    public static final GovernmentGroup SECULARISM = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "secularism", "Secularism", "")
            .setParent(SOFT_CULTURE_INTERVENTION)
            .addConnected(RELIGION, GOVERNMENT_LEADERSHIP)
            .build();
    public static final GovernmentGroup GOVERNMENT_ENFORCED_CONFORMITY = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "culture_war", "Conformity", "")
            .setParent(SOFT_CULTURE_INTERVENTION)
            .addConnected(CIVIL_LIBERTIES, CLASS_AND_CASTE)
            .build();
    public static final GovernmentGroup EDUCATION_INTERVENTION = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "education_funding", "Education Intervention", "")
            .setParent(SOFT_CULTURE_INTERVENTION)
            .addConnected(EDUCATION, EDUCATION_FUNDING)
            .build();
    public static final GovernmentGroup FAMILY_INTERVENTION = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "family_intervention", "FamilyGroups Intervention", "")
            .setParent(SOFT_CULTURE_INTERVENTION)
            .addConnected(FAMILY)
            .build();

    // Espionage
    public static final GovernmentGroup ESPIONAGE = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_SORT, Level.SUBCATEGORY, "espionage", "Espionage", "")
            .setParent(GOVERNMENT_SYSTEM)
            .addConnected(MILITARY, CIVIL_LIBERTIES, JUSTICE_SYSTEM)
            .build();
    public static final GovernmentGroup SECRET_POLICE = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "secret_police", "Secret Police & Mass Surveillance", "")
            .setParent(ESPIONAGE)
            .addConnected(SOCIETY)
            .build();
    public static final GovernmentGroup FOREIGN_INTELLIGENCE_SERVICE = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "foreign_intelligence_service", "Foreign Intelligence Service", "")
            .setParent(ESPIONAGE)
            .addConnected(FOREIGN_POLICY)
            .build();
    public static final GovernmentGroup COVERT_OPERATIONS = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "covert_operations", "Covert Operations", "")
            .setParent(ESPIONAGE)
            .addConnected(SPECIAL_OPERATIONS)
            .build();
    public static final GovernmentGroup ACCOUNTABILITY = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.SYSTEM_LARGE, Level.NORMAL, "accountability", "Accountability", "")
            .setParent(ESPIONAGE)
            .addConnected(GOVERNMENT_LEADERSHIP, GOVERNMENT_CORRUPTION)
            .build();

    // Government Ideology
    public static final GovernmentGroup GOVERNMENT_VALUE = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.VALUE, Level.NORMAL, "value", "GovernmentTenet Value", "")
            .setParent(GOVERNMENT_IDEOLOGY)
            .build();
    public static final GovernmentGroup GOVERNMENT_HEROES_AND_VILLANS = new TenetGroup.Builder<GovernmentGroup>(Builder.GGF,TGType.VALUE, Level.NORMAL, "heroes_and_villains", "Heroes and Villains", "")
            .setParent(GOVERNMENT_IDEOLOGY)
            .build();

    public static void init() {

    }
}
