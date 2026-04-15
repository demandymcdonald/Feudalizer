package com.objects.culture.tenet.group.groups;

import com.google.common.collect.ImmutableList;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.group.TGType;
import com.objects.culture.tenet.group.TenetGroup;

import static com.objects.culture.tenet.TenetManager.SOFT_CULTURE;
import static com.objects.culture.tenet.group.groups.EducationGroups.*;
import static com.objects.culture.tenet.group.groups.FamilyGroups.*;
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
            .addConnected(GOVERNMENT_ENFORCED_CONFORMITY)
            .build();
    public static final TenetGroup TRADITION_RITUAL = new TenetGroup.Builder(TGType.TRADITION, Level.NORMAL, "tradition_ritual", "Tradition/Ritual", "")
            .setParent(SOCIETY)
            .addConnected(CIVIL_LIBERTIES, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY)
            .build();
    public static final TenetGroup VALUES = new TenetGroup.Builder(TGType.VALUE, Level.NORMAL, "values", "Values", "")
            .setParent(SOCIETY)
            .addConnected(SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY)
            .build();
    public static final TenetGroup RACE_AND_ETHNIC_ORIGIN = new TenetGroup.Builder(TGType.BELIEF_MAJOR, Level.NORMAL, "race_and_ethnic_origin", "Race and Ethnic Origin", "")
            .setParent(SOCIETY)
            .addConnected(SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY, CLASS_AND_CASTE, CIVIL_LIBERTIES, FOREIGN_POLICY)
            .build();
    public static final TenetGroup LANGUAGE = new TenetGroup.Builder(TGType.LANGUAGE, Level.NORMAL, "language", "Language", "")
            .setParent(SOCIETY)
            .addConnected(CIVIL_LIBERTIES, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY)
            .build();
    public static final TenetGroup HOLIDAY_AND_GATHERING = new TenetGroup.Builder(TGType.TRADITION, Level.NORMAL, "holiday_and_gathering", "Holidays and Gatherings", "")
            .setParent(SOCIETY)
            .addConnected(CIVIL_LIBERTIES, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY)
            .build();
    public static final TenetGroup SOCIAL_GENDER_ROLES = new TenetGroup.Builder(TGType.BELIEF_MAJOR, Level.NORMAL, "social_gender_roles", "Social Gender Roles", "")
            .setParent(SOCIETY)
            .addConnected(GOVERNMENT_LEADERSHIP, RELIGION_LEADERSHIP, EDUCATION_LEADERSHIP, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY, RELIGIOUS_CONFORMITY, PARENT_ROLES)
            .build();

    // Aesthetics and Visual
    public static final TenetGroup AESTHETICS_AND_VISUAL = new TenetGroup.Builder(TGType.IDEOLOGY_SORT, Level.CATEGORY, "aesthetics_and_visual", "Aesthetics and Visual", "")
            .setParent(SOCIETY)
            .addConnected(CIVIL_LIBERTIES, CLASS_AND_CASTE, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY)
            .build();
    public static final TenetGroup ART_AND_MEDIA = new TenetGroup.Builder(TGType.AESTHETIC, Level.LORE_ONLY, "art_and_media", "Art and Media", "")
            .setParent(AESTHETICS_AND_VISUAL)
            .build();
    public static final TenetGroup ARCHITECTURE = new TenetGroup.Builder(TGType.AESTHETIC, Level.LORE_ONLY, "architecture", "Architecture", "")
            .setParent(AESTHETICS_AND_VISUAL)
            .build();
    public static final TenetGroup FASHION = new TenetGroup.Builder(TGType.AESTHETIC, Level.LORE_ONLY, "fashion", "Fashion", "")
            .setParent(AESTHETICS_AND_VISUAL)
            .build();


    public static final TenetGroup SOCIAL_ATTITUDE = new TenetGroup.Builder(TGType.IDEOLOGY_SORT, Level.CATEGORY, "social_attitude", "Social Attitude", "")
            .setParent(SOCIETY)
            .addConnected(CIVIL_LIBERTIES, CLASS_AND_CASTE, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY)
            .build();
        public static final TenetGroup ELITE_CLASS = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "elites", "Elite Class", "")
                .setParent(SOCIAL_ATTITUDE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES)
                .addDependent(GovernmentGroups.ELITE_CLASS)
                .build();

        public static final TenetGroup PROFESSIONAL_CLASS = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "professional_class", "Professional Class", "")
                .setParent(SOCIAL_ATTITUDE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES)
                .addDependent(GovernmentGroups.PROFESSIONAL_CLASS)
                .build();
        public static final TenetGroup ACADEMIC_CLASS = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "academic_class", "Academic Class", "")
                .setParent(SOCIAL_ATTITUDE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES)
                .addDependent(GovernmentGroups.ACADEMIC_CLASS)
                .build();
        public static final TenetGroup ARTIST_CLASS = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "artist_class", "Artist Class", "")
                .setParent(SOCIAL_ATTITUDE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES)
                .addDependent(GovernmentGroups.ARTIST_CLASS)
                .build();
        public static final TenetGroup OFFICER_CLASS = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "officer_class", "Officer Class", "")
                .setParent(SOCIAL_ATTITUDE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES)
                .addDependent(GovernmentGroups.OFFICER_CLASS)
                .build();
        public static final TenetGroup BUSINESS_CLASS = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "poor_class", "Poor Class", "")
                .setParent(SOCIAL_ATTITUDE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES)
                .addDependent(GovernmentGroups.BUSINESS_CLASS)
                .build();
        public static final TenetGroup MIDDLE_CLASS = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "middle_class", "Middle Class", "")
                .setParent(SOCIAL_ATTITUDE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES)
                .addDependent(GovernmentGroups.MIDDLE_CLASS)
                .build();
        public static final TenetGroup SOLDIER_CLASS = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "soldiers", "Soldier Ckass", "")
                .setParent(SOCIAL_ATTITUDE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES)
                .addDependent(GovernmentGroups.SOLDIER_CLASS)
                .build();
        public static final TenetGroup WORKING_CLASS = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "working_class", "Working Class", "")
                .setParent(SOCIAL_ATTITUDE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES)
                .addDependent(GovernmentGroups.WORKING_CLASS)
                .build();
        public static final TenetGroup DISENFRANCHISED = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "disenfranchised", "Disenfranchised", "")
                .setParent(SOCIAL_ATTITUDE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES)
                .addDependent(GovernmentGroups.DISENFRANCHISED)
                .build();
        public static final TenetGroup SLAVE = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "slaves", "Slaves", "")
                .setParent(SOCIAL_ATTITUDE)
                .addConnected(DISENFRANCHISED, CIVIL_LIBERTIES)
                .addDependent(GovernmentGroups.SLAVE)
                .build();
        public static final TenetGroup OUTSIDER = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "outsider", "Outsiders and Foreigners", "")
                .setParent(SOCIAL_ATTITUDE)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES)
                .addDependent(GovernmentGroups.OUTSIDER)
                .build();
        public static final TenetGroup GENDER = new TenetGroup.Builder(TGType.SYSTEM_SORT, Level.SUBCATEGORY, "gender", "Gender Rights", "")
                .setParent(SOCIAL_ATTITUDE)
                .build();
        public static final TenetGroup GENDER_MALE = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "gender_male", "Male", "")
                .setParent(GENDER)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES)
                .addDependent(GovernmentGroups.GENDER_MALE)
                .build();
        public static final TenetGroup GENDER_FEMALE = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "gender_female", "Female", "")
                .setParent(GENDER)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES)
                .addDependent(GovernmentGroups.GENDER_FEMALE)
                .build();
        public static final TenetGroup GENDER_TRANS = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "gender_trans", "Transgender", "")
                .setParent(GENDER)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES)
                .addDependent(GovernmentGroups.GENDER_TRANS)
                .build();
    public static final TenetGroup GENDER_NB_OTHER = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "gender_nb", "Non-Binary/Other", "")
            .setParent(GENDER)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES)
            .addDependent(GovernmentGroups.GENDER_TRANS)
            .build();
        public static final TenetGroup IDENTITY_GROUPS = new TenetGroup.Builder(TGType.SYSTEM_SORT, Level.SUBCATEGORY, "identity_groups", "Identity Groups", "")
                .setParent(SOCIAL_ATTITUDE)
                .addConnected(GENDER, CITIZENSHIP, CIVIL_LIBERTIES)
                .build();
        public static final TenetGroup RACIAL_ETHNIC_MAJORITY = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "racial_majority", "Racial Majority", "")
                .setParent(IDENTITY_GROUPS)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES,RACE_AND_ETHNIC_ORIGIN)
                .addDependent(GovernmentGroups.RACIAL_ETHNIC_MAJORITY)
                .build();
        public static final TenetGroup RACIAL_ETHNIC_MINORITY = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "racial_minority", "Racial Minority", "")
                .setParent(IDENTITY_GROUPS)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES,RACE_AND_ETHNIC_ORIGIN,DISENFRANCHISED)
                .addDependent(GovernmentGroups.RACIAL_ETHNIC_MINORITY)
                .build();
        public static final TenetGroup RELIGIOUS_MAJORITY = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "religious_majority", "Religious Majority", "")
                .setParent(IDENTITY_GROUPS)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES,RELIGION)
                .addDependent(GovernmentGroups.RELIGIOUS_MAJORITY)
                .build();
        public static final TenetGroup RELIGIOUS_MINORITY = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "religious_minority", "Religious Minority", "")
                .setParent(IDENTITY_GROUPS)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES,RELIGION,DISENFRANCHISED)
                .addDependent(GovernmentGroups.RELIGIOUS_MINORITY)
                .build();
        public static final TenetGroup CULTURAL_MAJORITY = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "cultural_majority", "Cultural Majority", "")
                .setParent(IDENTITY_GROUPS)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES,RACE_AND_ETHNIC_ORIGIN)
                .addDependent(GovernmentGroups.CULTURAL_MAJORITY)
                .build();
        public static final TenetGroup CULTURAL_MINORITY = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "cultural_minority", "Cultural Minority", "")
                .setParent(IDENTITY_GROUPS)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES,RACE_AND_ETHNIC_ORIGIN, DISENFRANCHISED)
                .addDependent(GovernmentGroups.CULTURAL_MINORITY)
                .build();
        public static final TenetGroup DISABILITY = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "disability", "Disabled Community", "")
                .setParent(IDENTITY_GROUPS)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, DISENFRANCHISED)
                .addDependent(GovernmentGroups.DISABILITY)
                .build();
        public static final TenetGroup POLITICAL_MAJORITY = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "political_majority", "Political Majority", "")
                .setParent(IDENTITY_GROUPS)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES)
                .addDependent(GovernmentGroups.POLITICAL_MAJORITY)
                .build();
        public static final TenetGroup POLITICAL_MINORITY = new TenetGroup.Builder(TGType.CASTE_SYSTEM, Level.NORMAL, "political_minority", "Political Minority", "")
                .setParent(IDENTITY_GROUPS)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, DISENFRANCHISED)
                .addDependent(GovernmentGroups.POLITICAL_MINORITY)
                .build();


    public static void init() {

    }

}
