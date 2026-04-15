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
//    public static final TenetGroup EDUCATION = builder(SOFT_CULTURE, "education", "Education", "", SORT_ONLY);
//    public static final TenetGroup EDUCATION_IDEOLOGY = builder(EDUCATION, ImmutableList.of(EDUCATION_INTERVENTION, RELIGIOUS_SOFT_CULTURE_INTERVENTION), "doctrine", "Education Doctrine", "", PILLAR);
//    public static final TenetGroup RELIGIOUS_EDUCATION = builder(EDUCATION_IDEOLOGY, ImmutableList.of(RELIGION), "religious_education", "Religious Education", "", SYSTEM_LARGE);
//    public static final TenetGroup EDUCATION_FOCUS = builder(EDUCATION_IDEOLOGY, "focus", "Education Focus", "", SYSTEM_LARGE);
//    public static final TenetGroup EDUCATION_ACCESS = builder(EDUCATION_IDEOLOGY, ImmutableList.of(CLASS_AND_CASTE, EDUCATION_FUNDING), "access", "Education Access", "", SYSTEM_LARGE);
//    public static final TenetGroup EDUCATOR_VALUE = builder(EDUCATION_IDEOLOGY, ImmutableList.of(EDUCATION_FUNDING), "educator_value", "Educator Value", "", new AcceptanceContainer(3, Acceptance.CORE, Acceptance.CORE_FANATIC));
//    public static final TenetGroup EDUCATED_VALUE = builder(EDUCATION_IDEOLOGY, ImmutableList.of(EDUCATION_FUNDING), "educated_value", "Educated Value", "", new AcceptanceContainer(3, Acceptance.CORE, Acceptance.CORE_FANATIC));
//    public static final TenetGroup SCHOOL_SYSTEM = builder(EDUCATION, ImmutableList.of(SOCIETY_ENFORCED_CONFORMITY, EDUCATION_INTERVENTION, EDUCATION_IDEOLOGY, EDUCATION_FUNDING), "school_system", "School System", "", PILLAR);
//    public static final TenetGroup PRIMARY_SCHOOL = builder(SCHOOL_SYSTEM, "primary_school", "Primary School", "", SYSTEM_LARGE);
//    public static final TenetGroup SECONDARY_SCHOOL = builder(SCHOOL_SYSTEM, "secondary_school", "Secondary School", "", SYSTEM_LARGE);
//    public static final TenetGroup TECHNICAL_SCHOOL = builder(SCHOOL_SYSTEM, "technical_school", "Technical School", "", SYSTEM_LARGE);
//    public static final TenetGroup POST_SECONDARY_SCHOOL = builder(SCHOOL_SYSTEM, "post_secondary_school", "Post Secondary School", "", SYSTEM_LARGE);
//    public static final TenetGroup PRIVATE_SCHOOL = builder(SCHOOL_SYSTEM, "private_school", "Private School", "", SYSTEM_LARGE);
//    public static final TenetGroup RELIGIOUS_SCHOOL = builder(SCHOOL_SYSTEM, ImmutableList.of(RELIGIOUS_SOFT_CULTURE_INTERVENTION), "religious_school", "Religious School", "", SYSTEM_LARGE);
//    public static final TenetGroup MILITARY_ACADEMY = builder(SCHOOL_SYSTEM, "military_academy", "Military Academy", "", SYSTEM_LARGE);
//    public static final TenetGroup EDUCATION_LEADERSHIP = builder(SCHOOL_SYSTEM, ImmutableList.of(SOCIETY_ENFORCED_CONFORMITY, CLASS_AND_CASTE, EDUCATION_IDEOLOGY, EDUCATION_INTERVENTION, RELIGIOUS_SOFT_CULTURE_INTERVENTION), "leadership", "Education Leadership", "", SORT_ONLY);
//    public static final TenetGroup TEACHER_SELECTION = builder(EDUCATION_LEADERSHIP, "selection", "Teacher Selection", "", SYSTEM_LARGE);
//    public static final TenetGroup TEACHER_TRAINING = builder(EDUCATION_LEADERSHIP, "training", "Teacher Training", "", SYSTEM_LARGE);
//    public static final TenetGroup TEACHER_TREATMENT = builder(EDUCATION_LEADERSHIP, "treatment", "Teacher Treatment", "", SYSTEM_LARGE);
//    public static final TenetGroup LEADER_SELECTION = builder(EDUCATION_LEADERSHIP, "leader_selection", "Leader Selection", "", SYSTEM_LARGE);

    // EDUCATION
    public static final TenetGroup EDUCATION = builder(TGType.SORT_ONLY, SOFT_CULTURE, "education", "Education", "");

    // Pillars
    public static final TenetGroup EDUCATION_IDEOLOGY = builder(TGType.PILLAR_IDEOLOGY, EDUCATION, ImmutableList.of(EDUCATION_INTERVENTION, RELIGIOUS_SOFT_CULTURE_INTERVENTION), "doctrine", "Education Doctrine", "");
    public static final TenetGroup SCHOOL_SYSTEM = builder(TGType.PILLAR_SYSTEM, EDUCATION, ImmutableList.of(SOCIETY_ENFORCED_CONFORMITY, EDUCATION_INTERVENTION, EDUCATION_IDEOLOGY, EDUCATION_FUNDING), "school_system", "School System", "");

    // Education Ideology
    public static final TenetGroup RELIGIOUS_EDUCATION = builder(TGType.BELIEF_MAJOR, EDUCATION_IDEOLOGY, ImmutableList.of(RELIGION), "religious_education", "Religious Education", "");
    public static final TenetGroup EDUCATION_FOCUS = builder(TGType.BELIEF_MAJOR, EDUCATION_IDEOLOGY, "focus", "Education Focus", "");
    public static final TenetGroup EDUCATION_ACCESS = builder(TGType.BELIEF_MAJOR, EDUCATION_IDEOLOGY, ImmutableList.of(CLASS_AND_CASTE, EDUCATION_FUNDING), "access", "Education Access", "");
    public static final TenetGroup EDUCATOR_VALUE = builder(TGType.VALUE, EDUCATION_IDEOLOGY, ImmutableList.of(EDUCATION_FUNDING), "educator_value", "Educator Value", "");
    public static final TenetGroup EDUCATED_VALUE = builder(TGType.VALUE, EDUCATION_IDEOLOGY, ImmutableList.of(EDUCATION_FUNDING), "educated_value", "Educated Value", "");

    // School System
    public static final TenetGroup PRIMARY_SCHOOL = builder(TGType.SYSTEM_LARGE, SCHOOL_SYSTEM, "primary_school", "Primary School", "");
    public static final TenetGroup SECONDARY_SCHOOL = builder(TGType.SYSTEM_LARGE, SCHOOL_SYSTEM, "secondary_school", "Secondary School", "");
    public static final TenetGroup TECHNICAL_SCHOOL = builder(TGType.SYSTEM_LARGE, SCHOOL_SYSTEM, "technical_school", "Technical School", "");
    public static final TenetGroup POST_SECONDARY_SCHOOL = builder(TGType.SYSTEM_LARGE, SCHOOL_SYSTEM, "post_secondary_school", "Post Secondary School", "");
    public static final TenetGroup PRIVATE_SCHOOL = builder(TGType.SYSTEM_LARGE, SCHOOL_SYSTEM, "private_school", "Private School", "");
    public static final TenetGroup RELIGIOUS_SCHOOL = builder(TGType.SYSTEM_LARGE, SCHOOL_SYSTEM, ImmutableList.of(RELIGIOUS_SOFT_CULTURE_INTERVENTION), "religious_school", "Religious School", "");
    public static final TenetGroup MILITARY_ACADEMY = builder(TGType.SYSTEM_LARGE, SCHOOL_SYSTEM, "military_academy", "Military Academy", "");

    // Education Leadership
    public static final TenetGroup EDUCATION_LEADERSHIP = builder(TGType.SYSTEM_SORT, SCHOOL_SYSTEM, ImmutableList.of(SOCIETY_ENFORCED_CONFORMITY, CLASS_AND_CASTE, EDUCATION_IDEOLOGY, EDUCATION_INTERVENTION, RELIGIOUS_SOFT_CULTURE_INTERVENTION), "leadership", "Education Leadership", "");
    public static final TenetGroup TEACHER_SELECTION = builder(TGType.SYSTEM_LARGE, EDUCATION_LEADERSHIP, "selection", "Teacher Selection", "");
    public static final TenetGroup TEACHER_TRAINING = builder(TGType.SYSTEM_LARGE, EDUCATION_LEADERSHIP, "training", "Teacher Training", "");
    public static final TenetGroup TEACHER_AUTHORITY = builder(TGType.SYSTEM_LARGE,EDUCATION_LEADERSHIP, "authority", "Teacher Authority", "");
    public static final TenetGroup TEACHER_TREATMENT = builder(TGType.SYSTEM_LARGE, EDUCATION_LEADERSHIP, "treatment", "Teacher Treatment", "");
    public static final TenetGroup TEACHER_REMOVAL = builder(TGType.SYSTEM_LARGE, EDUCATION_LEADERSHIP, "removal", "Teacher Removal", "");
    public static final TenetGroup TEACHER_CORRUPTION = builder(TGType.SYSTEM_LARGE,EDUCATION_LEADERSHIP,"corruption","Teacher Corruption","");
    public static void init() {

    }
}
