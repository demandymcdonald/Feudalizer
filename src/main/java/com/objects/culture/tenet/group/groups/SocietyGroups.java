package com.objects.culture.tenet.group.groups;

import com.objects.culture.tenet.group.TGType;
import com.objects.culture.tenet.group.TenetGroup;

import static com.objects.culture.tenet.TenetManager.SOFT_CULTURE;
import static com.objects.culture.tenet.group.groups.GovernmentGroups.*;
import static com.objects.culture.tenet.group.TenetGroup.*;
import static com.objects.culture.tenet.group.groups.ReligionGroups.*;

public class SocietyGroups {

    // SOCIETY
    public static final TenetGroup SOCIETY = new TenetGroup.Builder(TGType.PILLAR_IDEOLOGY, Level.PILLAR, "society", "Society", "")
            .setParent(SOFT_CULTURE)
            .build();

    // Society
    public static final TenetGroup SOCIETY_ENFORCED_CONFORMITY = new TenetGroup.Builder(TGType.BELIEF_MAJOR, Level.NORMAL, "conformity", "Social Conformity", "")
            .setParent(SOCIETY)
            .addConnected(GOVERNMENT_ENFORCED_CONFORMITY, RELIGIOUS_CONFORMITY, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
            .build();
    public static final TenetGroup TRADITION_RITUAL = new TenetGroup.Builder(TGType.TRADITION, Level.NORMAL, "tradition_ritual", "Tradition/Ritual", "")
            .setParent(SOCIETY)
            .addConnected(RELIGIOUS_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
            .build();
    public static final TenetGroup VALUES = new TenetGroup.Builder(TGType.VALUE, Level.NORMAL, "values", "Values", "")
            .setParent(SOCIETY)
            .addConnected(SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
            .build();
    public static final TenetGroup RACE_AND_ETHNIC_ORIGIN = new TenetGroup.Builder(TGType.BELIEF_MAJOR, Level.NORMAL, "race_and_ethnic_origin", "Race and Ethnic Origin", "")
            .setParent(SOCIETY)
            .addConnected(SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY, CLASS_AND_CASTE, CIVIL_LIBERTIES, FOREIGN_POLICY)
            .build();
    public static final TenetGroup LANGUAGE = new TenetGroup.Builder(TGType.LANGUAGE, Level.NORMAL, "language", "Language", "")
            .setParent(SOCIETY)
            .addConnected(RELIGIOUS_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
            .build();
    public static final TenetGroup HOLIDAY_AND_GATHERING = new TenetGroup.Builder(TGType.TRADITION, Level.NORMAL, "holiday_and_gathering", "Holidays and Gatherings", "")
            .setParent(SOCIETY)
            .addConnected(RELIGIOUS_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
            .build();

    // Aesthetics and Visual
    public static final TenetGroup AESTHETICS_AND_VISUAL = new TenetGroup.Builder(TGType.IDEOLOGY_SORT, Level.CATEGORY, "aesthetics_and_visual", "Aesthetics and Visual", "")
            .setParent(SOCIETY)

            .build();
    public static final TenetGroup ART_AND_MEDIA = new TenetGroup.Builder(TGType.AESTHETIC, Level.LORE_ONLY, "art_and_media", "Art and Media", "")
            .setParent(AESTHETICS_AND_VISUAL)
            .addConnected(CIVIL_LIBERTIES, CLASS_AND_CASTE, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
            .build();
    public static final TenetGroup ARCHITECTURE = new TenetGroup.Builder(TGType.AESTHETIC, Level.LORE_ONLY, "architecture", "Architecture", "")
            .setParent(AESTHETICS_AND_VISUAL)
            .addConnected(CIVIL_LIBERTIES, CLASS_AND_CASTE, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
            .build();
    public static final TenetGroup FASHION = new TenetGroup.Builder(TGType.AESTHETIC, Level.LORE_ONLY, "fashion", "Fashion", "")
            .setParent(AESTHETICS_AND_VISUAL)
            .addConnected(CIVIL_LIBERTIES, CLASS_AND_CASTE, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
            .build();


    public static final TenetGroup SOCIAL_ATTITUDE = new TenetGroup.Builder(TGType.IDEOLOGY_SORT, Level.CATEGORY, "social_attitude", "Social Attitude", "")
            .setParent(SOCIETY)
            .addConnected(CIVIL_LIBERTIES, CLASS_AND_CASTE, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
            .build();
    public static final TenetGroup CLASS_CASTE = new TenetGroup.Builder(TGType.SYSTEM_SORT, Level.SUBCATEGORY, "class", "Class & Caste Norms", "")
            .setParent(SOCIAL_ATTITUDE)
            .build();
        public static final TenetGroup ELITE_CLASS = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE, Level.NORMAL, "elites", "Elite Class", "")
                .setParent(CLASS_CASTE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.ELITE_CLASS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();

        public static final TenetGroup PROFESSIONAL_CLASS = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE, Level.NORMAL, "professional_class", "Professional Class", "")
                .setParent(CLASS_CASTE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.PROFESSIONAL_CLASS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final TenetGroup ACADEMIC_CLASS = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE, Level.NORMAL, "academic_class", "Academic Class", "")
                .setParent(CLASS_CASTE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.ACADEMIC_CLASS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final TenetGroup ARTIST_CLASS = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE, Level.NORMAL, "artist_class", "Artist Class", "")
                .setParent(CLASS_CASTE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.ARTIST_CLASS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final TenetGroup OFFICER_CLASS = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE, Level.NORMAL, "officer_class", "Officer Class", "")
                .setParent(CLASS_CASTE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.OFFICER_CLASS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final TenetGroup BUSINESS_CLASS = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE, Level.NORMAL, "poor_class", "Poor Class", "")
                .setParent(CLASS_CASTE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.BUSINESS_CLASS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final TenetGroup MIDDLE_CLASS = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE, Level.NORMAL, "middle_class", "Middle Class", "")
                .setParent(CLASS_CASTE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.MIDDLE_CLASS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final TenetGroup SOLDIER_CLASS = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE, Level.NORMAL, "soldiers", "Soldier Ckass", "")
                .setParent(CLASS_CASTE)
                .addConnected(CLASS_CASTE, CASTE_PERMEABILITY, RELIGIOUS_CONFORMITY)
                .addDependent(GovernmentGroups.SOLDIER_CLASS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final TenetGroup WORKING_CLASS = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE, Level.NORMAL, "working_class", "Working Class", "")
                .setParent(CLASS_CASTE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.WORKING_CLASS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final TenetGroup DISENFRANCHISED = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE, Level.NORMAL, "disenfranchised", "Disenfranchised", "")
                .setParent(CLASS_CASTE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.DISENFRANCHISED, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final TenetGroup SLAVE = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE, Level.NORMAL, "slaves", "Slaves", "")
                .setParent(CLASS_CASTE)
                .addConnected(DISENFRANCHISED, CIVIL_LIBERTIES, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.SLAVE, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final TenetGroup OUTSIDER = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE, Level.NORMAL, "outsider", "Outsiders and Foreigners", "")
                .setParent(CLASS_CASTE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.OUTSIDER, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final TenetGroup SEX = new TenetGroup.Builder(TGType.SYSTEM_SORT, Level.SUBCATEGORY, "sex", "Assigned Sex Attitudes", "")
                .setParent(CLASS_CASTE)
                .build();
        public static final TenetGroup SEX_MALE = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE, Level.NORMAL, "male", "Male", "")
                .setParent(SEX)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, MARRIAGE_RIGHTS, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.SEX_MALE, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final TenetGroup SEX_FEMALE = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE, Level.NORMAL, "female", "Female", "")
                .setParent(SEX)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, MARRIAGE_RIGHTS, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.SEX_FEMALE, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final TenetGroup GENDER = new TenetGroup.Builder(TGType.SYSTEM_SORT, Level.SUBCATEGORY, "gender", "Gender Norms", "")
                .setParent(SOCIAL_ATTITUDE)
                .build();
        public static final TenetGroup GENDER_MALE = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE, Level.NORMAL, "gender_male", "Male Norms", "")
                .setParent(GENDER)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, MARRIAGE_RIGHTS, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.GENDER_MALE, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final TenetGroup GENDER_FEMALE = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE, Level.NORMAL, "gender_female", "Female Norms", "")
                .setParent(GENDER)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, MARRIAGE_RIGHTS, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.GENDER_FEMALE, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final TenetGroup GENDER_TRANS = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE, Level.NORMAL, "gender_trans", "Transgender Norms", "")
                .setParent(GENDER)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, MARRIAGE_RIGHTS, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.GENDER_TRANS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
    public static final TenetGroup GENDER_NB_OTHER = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE, Level.NORMAL, "gender_nb", "Non-Binary/Other Norms", "")
            .setParent(GENDER)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, MARRIAGE_RIGHTS, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
            .addDependent(GovernmentGroups.GENDER_TRANS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
            .build();
    public static final TenetGroup ORIENTATION = new TenetGroup.Builder(TGType.SYSTEM_SORT, Level.SUBCATEGORY, "orientation", "Sexual Orientation Norms", "")
            .setParent(POPULATION_GROUP_RIGHTS)
            .build();
    public static final TenetGroup HETERO = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE,Level.NORMAL,"hetero","Heterosexual Norms","")
            .setParent(ORIENTATION)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, MARRIAGE_RIGHTS, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
            .addDependent(GovernmentGroups.HETERO, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
            .build();
    public static final TenetGroup HOMO = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE,Level.NORMAL,"homo","Homosexual Norms","")
            .setParent(ORIENTATION)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, MARRIAGE_RIGHTS, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
            .addDependent(GovernmentGroups.HOMO, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
            .build();
    public static final TenetGroup BI = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE,Level.NORMAL,"bi","Bisexual Norms","")
            .setParent(ORIENTATION)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, MARRIAGE_RIGHTS, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
            .addDependent(GovernmentGroups.BI, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
            .build();
    public static final TenetGroup AE = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE,Level.NORMAL,"ae","Asexual Norms","")
            .setParent(ORIENTATION)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, MARRIAGE_RIGHTS, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
            .addDependent(GovernmentGroups.AE, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
            .build();
    public static final TenetGroup PAN = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE,Level.NORMAL,"pan","Pansexual Norms","")
            .setParent(ORIENTATION)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, MARRIAGE_RIGHTS, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
            .addDependent(GovernmentGroups.PAN, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
            .build();
    public static final TenetGroup QUESTIONING = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE,Level.NORMAL,"questioning","Questioning Norms","")
            .setParent(ORIENTATION)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, MARRIAGE_RIGHTS, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
            .addDependent(GovernmentGroups.QUESTIONING, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
            .build();
    public static final TenetGroup OTHER = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE,Level.NORMAL,"other","Other Norms","")
            .setParent(ORIENTATION)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, MARRIAGE_RIGHTS, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
            .addDependent(GovernmentGroups.OTHER, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
            .build();
        public static final TenetGroup IDENTITY_GROUPS = new TenetGroup.Builder(TGType.IDEOLOGY_SORT, Level.SUBCATEGORY, "identity_groups", "Identity Group Norms", "")
                .setParent(SOCIAL_ATTITUDE)
                .build();
        public static final TenetGroup RACIAL_ETHNIC = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE, Level.NORMAL, "racial", "Racial and Ethnic Norms", "")
                .setParent(IDENTITY_GROUPS)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES,RACE_AND_ETHNIC_ORIGIN, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.RACES_ETHNICITIES, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final TenetGroup RELIGIOUS = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE, Level.NORMAL, "religious", "Religious Norms", "")
                .setParent(IDENTITY_GROUPS)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES,RELIGION,RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.RELIGIOUS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final TenetGroup CULTURE = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE, Level.NORMAL, "cultural", "Cultural Norms", "")
                .setParent(IDENTITY_GROUPS)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES,RACE_AND_ETHNIC_ORIGIN, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.CULTURAL, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();

        public static final TenetGroup DISABILITY = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE, Level.NORMAL, "disability", "Disabled Community Norms", "")
                .setParent(IDENTITY_GROUPS)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, DISENFRANCHISED, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.DISABILITY, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final TenetGroup POLITICAL_MAJORITY = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE, Level.NORMAL, "political_majority", "Political Majority", "")
                .setParent(IDENTITY_GROUPS)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES,RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.POLITICAL, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final TenetGroup EDUCATION_LEVEL = new TenetGroup.Builder(TGType.IDEOLOGY_SORT, Level.SUBCATEGORY, "education_level", "Education Level Rights", "")
                .setParent(SOCIAL_ATTITUDE)
                .build();
        public static final TenetGroup UNEDUCATED = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE, Level.NORMAL, "uneducated", "Uneducated Rights", "")
                .setParent(EDUCATION_LEVEL)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, DISENFRANCHISED, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.UNEDUCATED, LABOR_RIGHTS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final TenetGroup BASIC_EDUCATION = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE, Level.NORMAL, "basic_education", "Basic Education Rights", "")
                .setParent(EDUCATION_LEVEL)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, DISENFRANCHISED, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.BASIC_EDUCATION, LABOR_RIGHTS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final TenetGroup TRADES_EDUCATION = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE, Level.NORMAL, "trades", "Trades Education Rights", "")
                .setParent(EDUCATION_LEVEL)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, DISENFRANCHISED, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.TRADES_EDUCATION, LABOR_RIGHTS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final TenetGroup COLLEGE_EDUCATED = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE, Level.NORMAL, "college_educated", "College Educated Rights", "")
                .setParent(EDUCATION_LEVEL)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, DISENFRANCHISED, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.COLLEGE_EDUCATED, LABOR_RIGHTS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final TenetGroup HYPER_COLLEGE_EDUCATED = new TenetGroup.Builder(TGType.SOCIETY_ATTITUDE, Level.NORMAL, "graduate_college_educated", "Post-Graduate College Educated Rights", "")
                .setParent(EDUCATION_LEVEL)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, DISENFRANCHISED, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.HYPER_COLLEGE_EDUCATED, LABOR_RIGHTS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
    public static void init() {

    }

}
