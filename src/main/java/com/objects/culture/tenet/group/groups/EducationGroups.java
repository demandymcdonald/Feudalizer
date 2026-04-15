package com.objects.culture.tenet.group.groups;

import com.google.common.collect.ImmutableList;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.group.TGType;
import com.objects.culture.tenet.group.TenetGroup;

import static com.objects.culture.tenet.TenetManager.SOFT_CULTURE;
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

    // School System
    public static final TenetGroup PRIMARY_SCHOOL = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "primary_school", "Primary School", "")
            .setParent(SCHOOL_SYSTEM)
            .build();
    public static final TenetGroup SECONDARY_SCHOOL = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "secondary_school", "Secondary School", "")
            .setParent(SCHOOL_SYSTEM)
            .build();
    public static final TenetGroup TECHNICAL_SCHOOL = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "technical_school", "Technical School", "")
            .setParent(SCHOOL_SYSTEM)
            .build();
    public static final TenetGroup POST_SECONDARY_SCHOOL = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "post_secondary_school", "Post Secondary School", "")
            .setParent(SCHOOL_SYSTEM)
            .build();
    public static final TenetGroup PRIVATE_SCHOOL = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "private_school", "Private School", "")
            .setParent(SCHOOL_SYSTEM)
            .build();
    public static final TenetGroup RELIGIOUS_SCHOOL = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "religious_school", "Religious School", "")
            .setParent(SCHOOL_SYSTEM)
            .addConnected(RELIGIOUS_SOFT_CULTURE_INTERVENTION)
            .build();
    public static final TenetGroup MILITARY_ACADEMY = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "military_academy", "Military Academy", "")
            .setParent(SCHOOL_SYSTEM)
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
