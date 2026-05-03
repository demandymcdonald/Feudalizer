package com.objects.culture.tenet.group.groups;

import com.base.component.InstanceType;
import com.objects.culture.tenet.group.TGType;
import com.objects.culture.tenet.group.TenetGroup;
import org.checkerframework.checker.nullness.qual.NonNull;

import static com.objects.culture.TenetManager.SOFT_CULTURE;
import static com.objects.culture.tenet.group.TenetGroup.Builder.SGF;
import static com.objects.culture.tenet.group.groups.GovernmentGroups.*;
import static com.objects.culture.tenet.group.TenetGroup.*;
import static com.objects.culture.tenet.group.groups.ReligionGroups.*;

public class SocietyGroups {
    public static class SocietyGroup extends TenetGroup {
        public SocietyGroup(InstanceType instType, @NonNull TGType type, Level level, String fullID, String id, String name, String description) {
            super(instType,type, level,fullID, id, name, description);
        }
    }
    // SOCIETY
    public static final SocietyGroup SOCIETY = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.PILLAR_IDEOLOGY, Level.PILLAR, "society", "Society", "")
            .setParent(SOFT_CULTURE)
            .build();

    // Society
    public static final SocietyGroup SOCIETY_ENFORCED_CONFORMITY = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.BELIEF_MAJOR, Level.NORMAL, "conformity", "Social Conformity", "")
            .setParent(SOCIETY)
            .addConnected(GOVERNMENT_ENFORCED_CONFORMITY, RELIGIOUS_CONFORMITY, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
            .build();
    public static final SocietyGroup TRADITION_RITUAL = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.TRADITION, Level.NORMAL, "tradition_ritual", "Tradition/Ritual", "")
            .setParent(SOCIETY)
            .addConnected(RELIGIOUS_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
            .build();
    public static final SocietyGroup VALUES = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.VALUE, Level.NORMAL, "values", "Values", "")
            .setParent(SOCIETY)
            .addConnected(SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
            .build();
    public static final SocietyGroup RACE_AND_ETHNIC_ORIGIN = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.BELIEF_MAJOR, Level.NORMAL, "race_and_ethnic_origin", "Race and Ethnic Origin", "")
            .setParent(SOCIETY)
            .addConnected(SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY, CLASS_AND_CASTE, CIVIL_LIBERTIES, FOREIGN_POLICY)
            .build();
    public static final SocietyGroup LANGUAGE = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.LANGUAGE, Level.NORMAL, "language", "Language", "")
            .setParent(SOCIETY)
            .addConnected(RELIGIOUS_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
            .build();
    public static final SocietyGroup HOLIDAY_AND_GATHERING = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.TRADITION, Level.NORMAL, "holiday_and_gathering", "Holidays and Gatherings", "")
            .setParent(SOCIETY)
            .addConnected(RELIGIOUS_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
            .build();

    // Aesthetics and Visual
    public static final SocietyGroup AESTHETICS_AND_VISUAL = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.IDEOLOGY_SORT, Level.CATEGORY, "aesthetics_and_visual", "Aesthetics and Visual", "")
            .setParent(SOCIETY)

            .build();
    public static final SocietyGroup ART_AND_MEDIA = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.AESTHETIC, Level.LORE_ONLY, "art_and_media", "Art and Media", "")
            .setParent(AESTHETICS_AND_VISUAL)
            .addConnected(CIVIL_LIBERTIES, CLASS_AND_CASTE, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
            .build();
    public static final SocietyGroup ARCHITECTURE = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.AESTHETIC, Level.LORE_ONLY, "architecture", "Architecture", "")
            .setParent(AESTHETICS_AND_VISUAL)
            .addConnected(CIVIL_LIBERTIES, CLASS_AND_CASTE, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
            .build();
    public static final SocietyGroup FASHION = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.AESTHETIC, Level.LORE_ONLY, "fashion", "Fashion", "")
            .setParent(AESTHETICS_AND_VISUAL)
            .addConnected(CIVIL_LIBERTIES, CLASS_AND_CASTE, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
            .build();


    public static final SocietyGroup SOCIAL_ATTITUDE = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.IDEOLOGY_SORT, Level.CATEGORY, "social_attitude", "Social Attitude", "")
            .setParent(SOCIETY)
            .addConnected(CIVIL_LIBERTIES, CLASS_AND_CASTE, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
            .build();
    public static final SocietyGroup CLASS_CASTE = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SYSTEM_SORT, Level.SUBCATEGORY, "class", "Class & Caste Norms", "")
            .setParent(SOCIAL_ATTITUDE)
            .build();
        public static final SocietyGroup ELITE_CLASS = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "elites", "Elite Class", "")
                .setParent(CLASS_CASTE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.ELITE_CLASS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();

        public static final SocietyGroup PROFESSIONAL_CLASS = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "professional_class", "Professional Class", "")
                .setParent(CLASS_CASTE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.PROFESSIONAL_CLASS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final SocietyGroup ACADEMIC_CLASS = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "academic_class", "Academic Class", "")
                .setParent(CLASS_CASTE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.ACADEMIC_CLASS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final SocietyGroup ARTIST_CLASS = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "artist_class", "Artist Class", "")
                .setParent(CLASS_CASTE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.ARTIST_CLASS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final SocietyGroup OFFICER_CLASS = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "officer_class", "Officer Class", "")
                .setParent(CLASS_CASTE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.OFFICER_CLASS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final SocietyGroup BUSINESS_CLASS = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "poor_class", "Poor Class", "")
                .setParent(CLASS_CASTE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.BUSINESS_CLASS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final SocietyGroup MIDDLE_CLASS = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "middle_class", "Middle Class", "")
                .setParent(CLASS_CASTE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.MIDDLE_CLASS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final SocietyGroup SOLDIER_CLASS = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "soldiers", "Soldier Ckass", "")
                .setParent(CLASS_CASTE)
                .addConnected(CLASS_CASTE, CASTE_PERMEABILITY, RELIGIOUS_CONFORMITY)
                .addDependent(GovernmentGroups.SOLDIER_CLASS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final SocietyGroup WORKING_CLASS = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "working_class", "Working Class", "")
                .setParent(CLASS_CASTE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.WORKING_CLASS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final SocietyGroup DISENFRANCHISED = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "disenfranchised", "Disenfranchised", "")
                .setParent(CLASS_CASTE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.DISENFRANCHISED, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final SocietyGroup SLAVE = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "slaves", "Slaves", "")
                .setParent(CLASS_CASTE)
                .addConnected(DISENFRANCHISED, CIVIL_LIBERTIES, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.SLAVE, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final SocietyGroup OUTSIDER = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "outsider", "Outsiders and Foreigners", "")
                .setParent(CLASS_CASTE)
                .addConnected(CITIZENSHIP, CASTE_PERMEABILITY, CIVIL_LIBERTIES, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.OUTSIDER, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final SocietyGroup SEX = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SYSTEM_SORT, Level.SUBCATEGORY, "sex", "Assigned Sex Attitudes", "")
                .setParent(CLASS_CASTE)
                .build();
        public static final SocietyGroup SEX_MALE = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "male", "Male", "")
                .setParent(SEX)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, MARRIAGE_RIGHTS, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.SEX_MALE, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final SocietyGroup SEX_FEMALE = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "female", "Female", "")
                .setParent(SEX)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, MARRIAGE_RIGHTS, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.SEX_FEMALE, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final SocietyGroup GENDER = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SYSTEM_SORT, Level.SUBCATEGORY, "gender", "Gender Norms", "")
                .setParent(SOCIAL_ATTITUDE)
                .build();
        public static final SocietyGroup GENDER_MALE = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "gender_male", "Male Norms", "")
                .setParent(GENDER)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, MARRIAGE_RIGHTS, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.GENDER_MALE, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final SocietyGroup GENDER_FEMALE = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "gender_female", "Female Norms", "")
                .setParent(GENDER)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, MARRIAGE_RIGHTS, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.GENDER_FEMALE, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final SocietyGroup GENDER_TRANS = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "gender_trans", "Transgender Norms", "")
                .setParent(GENDER)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, MARRIAGE_RIGHTS, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.GENDER_TRANS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
    public static final SocietyGroup GENDER_NB_OTHER = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "gender_nb", "Non-Binary/Other Norms", "")
            .setParent(GENDER)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, MARRIAGE_RIGHTS, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
            .addDependent(GovernmentGroups.GENDER_TRANS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
            .build();
    public static final SocietyGroup ORIENTATION = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SYSTEM_SORT, Level.SUBCATEGORY, "orientation", "Sexual Orientation Norms", "")
            .setParent(POPULATION_GROUP_RIGHTS)
            .build();
    public static final SocietyGroup HETERO = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE,Level.NORMAL,"hetero","Heterosexual Norms","")
            .setParent(ORIENTATION)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, MARRIAGE_RIGHTS, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
            .addDependent(GovernmentGroups.HETERO, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
            .build();
    public static final SocietyGroup HOMO = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE,Level.NORMAL,"homo","Homosexual Norms","")
            .setParent(ORIENTATION)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, MARRIAGE_RIGHTS, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
            .addDependent(GovernmentGroups.HOMO, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
            .build();
    public static final SocietyGroup BI = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE,Level.NORMAL,"bi","Bisexual Norms","")
            .setParent(ORIENTATION)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, MARRIAGE_RIGHTS, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
            .addDependent(GovernmentGroups.BI, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
            .build();
    public static final SocietyGroup AE = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE,Level.NORMAL,"ae","Asexual Norms","")
            .setParent(ORIENTATION)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, MARRIAGE_RIGHTS, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
            .addDependent(GovernmentGroups.AE, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
            .build();
    public static final SocietyGroup PAN = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE,Level.NORMAL,"pan","Pansexual Norms","")
            .setParent(ORIENTATION)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, MARRIAGE_RIGHTS, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
            .addDependent(GovernmentGroups.PAN, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
            .build();
    public static final SocietyGroup QUESTIONING = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE,Level.NORMAL,"questioning","Questioning Norms","")
            .setParent(ORIENTATION)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, MARRIAGE_RIGHTS, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
            .addDependent(GovernmentGroups.QUESTIONING, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
            .build();
    public static final SocietyGroup OTHER = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE,Level.NORMAL,"other","Other Norms","")
            .setParent(ORIENTATION)
            .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, MARRIAGE_RIGHTS, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
            .addDependent(GovernmentGroups.OTHER, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
            .build();
        public static final SocietyGroup IDENTITY_GROUPS = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.IDEOLOGY_SORT, Level.SUBCATEGORY, "identity_groups", "Identity Group Norms", "")
                .setParent(SOCIAL_ATTITUDE)
                .build();
        public static final SocietyGroup RACIAL_ETHNIC = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "racial", "Racial and Ethnic Norms", "")
                .setParent(IDENTITY_GROUPS)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES,RACE_AND_ETHNIC_ORIGIN, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.RACES_ETHNICITIES, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final SocietyGroup RELIGIOUS = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "religious", "Religious Norms", "")
                .setParent(IDENTITY_GROUPS)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES,RELIGION,RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.RELIGIOUS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final SocietyGroup CULTURE = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "cultural", "Cultural Norms", "")
                .setParent(IDENTITY_GROUPS)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES,RACE_AND_ETHNIC_ORIGIN, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.CULTURAL, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final SocietyGroup PROFESSION = new TenetGroup.Builder<>(Builder.SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "profession", "IGProfession-Based Rights", "")
                .setParent(IDENTITY_GROUPS)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES,RACE_AND_ETHNIC_ORIGIN, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.CULTURAL, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final SocietyGroup DISABILITY = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "disability", "Disabled Community Norms", "")
                    .setParent(IDENTITY_GROUPS)
                    .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, DISENFRANCHISED, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                    .addDependent(GovernmentGroups.DISABILITY, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                    .build();
        public static final SocietyGroup POLITICAL_MAJORITY = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "political_majority", "Political Majority", "")
                .setParent(IDENTITY_GROUPS)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES,RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.POLITICAL, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final SocietyGroup EDUCATION_LEVEL = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.IDEOLOGY_SORT, Level.SUBCATEGORY, "education_level", "Education Level Rights", "")
                .setParent(SOCIAL_ATTITUDE)
                .build();
        public static final SocietyGroup UNEDUCATED = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "uneducated", "Uneducated Rights", "")
                .setParent(EDUCATION_LEVEL)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, DISENFRANCHISED, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.UNEDUCATED, LABOR_RIGHTS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final SocietyGroup BASIC_EDUCATION = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "basic_education", "Basic Education Rights", "")
                .setParent(EDUCATION_LEVEL)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, DISENFRANCHISED, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.BASIC_EDUCATION, LABOR_RIGHTS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final SocietyGroup TRADES_EDUCATION = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "trades", "Trades Education Rights", "")
                .setParent(EDUCATION_LEVEL)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, DISENFRANCHISED, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.TRADES_EDUCATION, LABOR_RIGHTS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final SocietyGroup COLLEGE_EDUCATED = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "college_educated", "College Educated Rights", "")
                .setParent(EDUCATION_LEVEL)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, DISENFRANCHISED, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.COLLEGE_EDUCATED, LABOR_RIGHTS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
        public static final SocietyGroup HYPER_COLLEGE_EDUCATED = new TenetGroup.Builder<SocietyGroup>(SGF,TGType.SOCIETY_ATTITUDE, Level.NORMAL, "graduate_college_educated", "Post-Graduate College Educated Rights", "")
                .setParent(EDUCATION_LEVEL)
                .addConnected(CITIZENSHIP, CIVIL_LIBERTIES, DISENFRANCHISED, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
                .addDependent(GovernmentGroups.HYPER_COLLEGE_EDUCATED, LABOR_RIGHTS, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
                .build();
    public static void init() {

    }

}
