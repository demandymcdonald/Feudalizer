package com.objects.culture.tenet.group.groups;

import com.objects.culture.tenet.group.TGType;
import com.objects.culture.tenet.group.TenetGroup;

import static com.objects.culture.TenetManager.SOFT_CULTURE;
import static com.objects.culture.tenet.group.groups.GovernmentGroups.*;
import static com.objects.culture.tenet.group.groups.ReligionGroups.RELIGION;
import static com.objects.culture.tenet.group.groups.ReligionGroups.RELIGIOUS_SOFT_CULTURE_INTERVENTION;
import static com.objects.culture.tenet.group.groups.SocietyGroups.SOCIETY_ENFORCED_CONFORMITY;
import static com.objects.culture.tenet.group.TenetGroup.*;

public class EducationGroups {

    // EDUCATION
    public static final TenetGroup EDUCATION = new TenetGroup.Builder(TGType.SORT_ONLY, Level.PILLAR, "education", "Education", "")
            .setParent(SOFT_CULTURE)
            .build();

    // Pillar
    public static final TenetGroup EDUCATION_IDEOLOGY = new TenetGroup.Builder(TGType.PILLAR_IDEOLOGY, Level.CATEGORY, "doctrine", "Education Doctrine", "")
            .setParent(EDUCATION)
            .addConnected(EDUCATION_INTERVENTION, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
            .build();
    public static final TenetGroup SCHOOL_SYSTEM = new TenetGroup.Builder(TGType.PILLAR_SYSTEM, Level.CATEGORY, "school_system", "School System", "")
            .setParent(EDUCATION)
            .addConnected(SOCIETY_ENFORCED_CONFORMITY, EDUCATION_INTERVENTION, EDUCATION_IDEOLOGY, EDUCATION_FUNDING)
            .build();

    // Education Ideology
    public static final TenetGroup RELIGIOUS_EDUCATION = new TenetGroup.Builder(TGType.BELIEF_MAJOR, Level.NORMAL, "religious_education", "Religious Education", "")
            .setParent(EDUCATION_IDEOLOGY)
            .addConnected(RELIGION)
            .build();
    public static final TenetGroup EDUCATION_FOCUS = new TenetGroup.Builder(TGType.BELIEF_MAJOR, Level.NORMAL, "focus", "Education Focus", "")
            .setParent(EDUCATION_IDEOLOGY)
            .build();
    public static final TenetGroup EDUCATION_ACCESS = new TenetGroup.Builder(TGType.BELIEF_MAJOR, Level.NORMAL, "access", "Education Access", "")
            .setParent(EDUCATION_IDEOLOGY)
            .addConnected(CLASS_AND_CASTE, EDUCATION_FUNDING)
            .build();
    public static final TenetGroup EDUCATOR_VALUE = new TenetGroup.Builder(TGType.VALUE, Level.NORMAL, "educator_value", "Educator Value", "")
            .setParent(EDUCATION_IDEOLOGY)
            .addConnected(EDUCATION_FUNDING)
            .build();
    public static final TenetGroup EDUCATED_VALUE = new TenetGroup.Builder(TGType.VALUE, Level.NORMAL, "educated_value", "Educated Value", "")
            .setParent(EDUCATION_IDEOLOGY)
            .addConnected(EDUCATION_FUNDING)
            .build();
    public static final TenetGroup SCHOOL_TYPE = new TenetGroup.Builder(TGType.SYSTEM_SORT, Level.SUBCATEGORY, "school_type", "School Type", "")
            .setParent(SCHOOL_SYSTEM)
            .addConnected(SOCIETY_ENFORCED_CONFORMITY, EDUCATION_INTERVENTION, EDUCATION_IDEOLOGY, EDUCATION_FUNDING)
            .build();
    // School System
    public static final TenetGroup PRIMARY_SCHOOL = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "primary_school", "Primary School", "")
            .setParent(SCHOOL_TYPE)
            .build();
    public static final TenetGroup PRIMARY_SCHOOL_EDUCATION = new TenetGroup.Builder(TGType.SYSTEM_SMALL, Level.NORMAL, "primary_edu", "Primary School Education", "")
            .setParent(PRIMARY_SCHOOL)
            .build();
    public static final TenetGroup SECONDARY_SCHOOL = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "secondary_school", "Secondary School", "")
            .setParent(SCHOOL_TYPE)
            .build();
    public static final TenetGroup SECONDARY_SCHOOL_EDUCATION = new TenetGroup.Builder(TGType.SYSTEM_SMALL, Level.NORMAL, "secondary_edu", "Secondary School Education", "")
            .setParent(SECONDARY_SCHOOL)
            .build();
    public static final TenetGroup APPRENTICESHIP = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "apprenticeship", "Technical School", "")
            .setParent(SCHOOL_TYPE)
            .build();
    public static final TenetGroup APPRENTICE_EDUCATION = new TenetGroup.Builder(TGType.SYSTEM_SMALL, Level.NORMAL, "apprenticeship_edu", "Apprenticeship Education", "")
            .setParent(APPRENTICESHIP)
            .build();
    public static final TenetGroup TECHNICAL_SCHOOL = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "technical_school", "Technical School", "")
            .setParent(SCHOOL_TYPE)
            .addDependent(APPRENTICESHIP)
            .build();
    public static final TenetGroup TECHNICAL_SCHOOL_EDUCATION = new TenetGroup.Builder(TGType.SYSTEM_SMALL, Level.NORMAL, "technical_edu", "Technical School Education", "")
            .setParent(TECHNICAL_SCHOOL)
            .addDependent(APPRENTICE_EDUCATION)
            .build();
    public static final TenetGroup POST_SECONDARY_SCHOOL = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "post_secondary_school", "Post Secondary School", "")
            .setParent(SCHOOL_TYPE)
            .build();
    public static final TenetGroup UNDERGRADUATE_EDUCATION = new TenetGroup.Builder(TGType.SYSTEM_SMALL, Level.NORMAL, "undergraduate_edu", "Undergraduate Education", "")
            .setParent(POST_SECONDARY_SCHOOL)
            .build();
    public static final TenetGroup GRADUATE_EDUCATION = new TenetGroup.Builder(TGType.SYSTEM_SMALL, Level.NORMAL, "graduate_edu", "Graduate Education", "")
            .setParent(POST_SECONDARY_SCHOOL)
            .build();
    public static final TenetGroup POST_GRADUATE_EDUCATION = new TenetGroup.Builder(TGType.SYSTEM_SMALL, Level.NORMAL, "post_graduate_edu", "Post-Graduate Education", "")
            .setParent(POST_SECONDARY_SCHOOL)
            .build();
    public static final TenetGroup PRIVATE_SCHOOL = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "private_school", "Private School", "")
            .setParent(SCHOOL_TYPE)
            .addDependent(PRIMARY_SCHOOL,SECONDARY_SCHOOL)
            .build();
    public static final TenetGroup PRIVATE_SCHOOL_PRIMARY_EDUCATION = new TenetGroup.Builder(TGType.SYSTEM_SMALL, Level.NORMAL, "primary_edu", "Private School Primary Education", "")
            .setParent(PRIVATE_SCHOOL)
            .addDependent(PRIMARY_SCHOOL_EDUCATION)
            .build();
    public static final TenetGroup PRIVATE_SCHOOL_SECONDARY_EDUCATION = new TenetGroup.Builder(TGType.SYSTEM_SMALL, Level.NORMAL, "secondary_edu", "Private School Secondary Education", "")
            .setParent(PRIVATE_SCHOOL)
            .addDependent(SECONDARY_SCHOOL_EDUCATION)
            .build();
    public static final TenetGroup RELIGIOUS_SCHOOL = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "religious_school", "Religious School", "")
            .setParent(SCHOOL_TYPE)
            .addConnected(RELIGIOUS_SOFT_CULTURE_INTERVENTION)
            .build();
    public static final TenetGroup RELIGIOUS_SCHOOL_PRIMARY_EDUCATION = new TenetGroup.Builder(TGType.SYSTEM_SMALL, Level.NORMAL, "primary_edu", "Religious School Primary Education", "")
            .setParent(RELIGIOUS_SCHOOL)
            .addDependent(PRIMARY_SCHOOL_EDUCATION)
            .build();
    public static final TenetGroup RELIGIOUS_SCHOOL_SECONDARY_EDUCATION = new TenetGroup.Builder(TGType.SYSTEM_SMALL, Level.NORMAL, "secondary_edu", "Religious School Secondary Education", "")
            .setParent(RELIGIOUS_SCHOOL)
            .addDependent(SECONDARY_SCHOOL_EDUCATION)
            .build();
    public static final TenetGroup MILITARY_ACADEMY = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "military_academy", "Military Academy", "")
            .setParent(SCHOOL_TYPE)
            .build();
    public static final TenetGroup OFFICER_EDUCATION = new TenetGroup.Builder(TGType.SYSTEM_SMALL, Level.NORMAL, "officer_edu", "Officer Training", "")
            .setParent(MILITARY_ACADEMY)
            .addDependent(MilitaryGroups.OFFICER_TRAINING,UNDERGRADUATE_EDUCATION)
            .build();
    public static final TenetGroup BOOT_CAMP = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "boot_camp", "Boot Camp", "")
            .setParent(SCHOOL_TYPE)
            .build();
    public static final TenetGroup SOLDIER_BASIC_EDUCATION = new TenetGroup.Builder(TGType.SYSTEM_SMALL, Level.NORMAL, "basic_edu", "Basic Training", "")
            .setParent(BOOT_CAMP)
            .addDependent(MilitaryGroups.MILITARY_TRAINING)
            .build();
    public static final TenetGroup SOLDIER_TECHNICAL_EDUCATION = new TenetGroup.Builder(TGType.SYSTEM_SMALL, Level.NORMAL, "technical_edu", "Technical Training", "")
            .setParent(BOOT_CAMP)
            .addDependent(MilitaryGroups.MILITARY_TRAINING,TECHNICAL_SCHOOL_EDUCATION)
            .addConnected(APPRENTICE_EDUCATION)
            .build();
    public static final TenetGroup HOMESCHOOL = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "homeschool", "Homeschooling", "")
            .setParent(SCHOOL_TYPE)
            .build();
    public static final TenetGroup HOMESCHOOL_SCHOOL_PRIMARY_EDUCATION = new TenetGroup.Builder(TGType.SYSTEM_SMALL, Level.NORMAL, "primary_edu", "Homeschooled Primary Education", "")
            .setParent(HOMESCHOOL)
            .addDependent(PRIMARY_SCHOOL_EDUCATION)
            .build();
    public static final TenetGroup HOMESCHOOL_SCHOOL_SECONDARY_EDUCATION = new TenetGroup.Builder(TGType.SYSTEM_SMALL, Level.NORMAL, "secondary_edu", "Homeschooled Secondary Education", "")
            .setParent(HOMESCHOOL)
            .addDependent(SECONDARY_SCHOOL_EDUCATION)
            .build();
    public static final TenetGroup HOMESCHOOL_DOMESTIC_EDUCATION = new TenetGroup.Builder(TGType.SYSTEM_SMALL, Level.NORMAL, "domestic_edu", "Domestic Education", "")
            .setParent(HOMESCHOOL)
            .addConnected(APPRENTICESHIP)
            .build();
    // Education Leadership
    public static final TenetGroup EDUCATION_LEADERSHIP = new TenetGroup.Builder(TGType.SYSTEM_SORT, Level.SUBCATEGORY, "leadership", "Education Leadership", "")
            .setParent(SCHOOL_SYSTEM)
            .addConnected(SOCIETY_ENFORCED_CONFORMITY, CLASS_AND_CASTE, EDUCATION_IDEOLOGY, EDUCATION_INTERVENTION, RELIGIOUS_SOFT_CULTURE_INTERVENTION)
            .build();
    public static final TenetGroup TEACHER_TYPES = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "types", "Teacher Types", "")
            .setParent(EDUCATION_LEADERSHIP)
            .build();
    public static final TenetGroup TEACHER_SELECTION = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "selection", "Teacher Selection", "")
            .setParent(EDUCATION_LEADERSHIP)
            .build();
    public static final TenetGroup TEACHER_TRAINING = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "training", "Teacher Training", "")
            .setParent(EDUCATION_LEADERSHIP)
            .build();
    public static final TenetGroup TEACHER_AUTHORITY = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "authority", "Teacher Authority", "")
            .setParent(EDUCATION_LEADERSHIP)
            .build();
    public static final TenetGroup TEACHER_TREATMENT = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "treatment", "Teacher Treatment", "")
            .setParent(EDUCATION_LEADERSHIP)
            .build();
    public static final TenetGroup TEACHER_REMOVAL = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "removal", "Teacher Removal", "")
            .setParent(EDUCATION_LEADERSHIP)
            .build();
    public static final TenetGroup TEACHER_CORRUPTION = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "corruption", "Teacher Corruption", "")
            .setParent(EDUCATION_LEADERSHIP)
            .build();


    public static void init() {

    }
}
