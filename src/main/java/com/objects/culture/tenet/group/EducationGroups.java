package com.objects.culture.tenet.group;

import com.Global.*;
import com.google.common.collect.ImmutableList;
import com.objects.culture.tenet.Acceptance;

import static com.objects.culture.tenet.group.GovernmentGroups.*;
import static com.objects.culture.tenet.group.ReligionGroups.RELIGION;
import static com.objects.culture.tenet.group.ReligionGroups.RELIGIOUS_SOFT_CULTURE_INTERVENTION;
import static com.objects.culture.tenet.group.SocietyGroups.SOCIETY_ENFORCED_CONFORMITY;
import static com.objects.culture.tenet.group.TenetGroup.*;

public class EducationGroups {
    public static final TenetGroup EDUCATION = builder(SOFT_CULTURE, "education", "Education", "", SORT_ONLY);
    public static final TenetGroup EDUCATION_IDEOLOGY = builder(EDUCATION, ImmutableList.of(EDUCATION_INTERVENTION, RELIGIOUS_SOFT_CULTURE_INTERVENTION), "doctrine", "Education Doctrine", "", PILLAR);
    public static final TenetGroup RELIGIOUS_EDUCATION = builder(EDUCATION_IDEOLOGY, ImmutableList.of(RELIGION), "religious_education", "Religious Education", "", SYSTEM_LARGE);
    public static final TenetGroup EDUCATION_FOCUS = builder(EDUCATION_IDEOLOGY, "focus", "Education Focus", "", SYSTEM_LARGE);
    public static final TenetGroup EDUCATION_ACCESS = builder(EDUCATION_IDEOLOGY, ImmutableList.of(CLASS_AND_CASTE, EDUCATION_FUNDING), "access", "Education Access", "", SYSTEM_LARGE);
    public static final TenetGroup EDUCATOR_VALUE = builder(EDUCATION_IDEOLOGY, ImmutableList.of(EDUCATION_FUNDING), "educator_value", "Educator Value", "", new AcceptanceContainer(3, Acceptance.CORE, Acceptance.CORE_FANATIC));
    public static final TenetGroup EDUCATED_VALUE = builder(EDUCATION_IDEOLOGY, ImmutableList.of(EDUCATION_FUNDING), "educated_value", "Educated Value", "", new AcceptanceContainer(3, Acceptance.CORE, Acceptance.CORE_FANATIC));
    public static final TenetGroup SCHOOL_SYSTEM = builder(EDUCATION, ImmutableList.of(SOCIETY_ENFORCED_CONFORMITY, EDUCATION_INTERVENTION, EDUCATION_IDEOLOGY, EDUCATION_FUNDING), "school_system", "School System", "", PILLAR);
    public static final TenetGroup PRIMARY_SCHOOL = builder(SCHOOL_SYSTEM, "primary_school", "Primary School", "", SYSTEM_LARGE);
    public static final TenetGroup SECONDARY_SCHOOL = builder(SCHOOL_SYSTEM, "secondary_school", "Secondary School", "", SYSTEM_LARGE);
    public static final TenetGroup TECHNICAL_SCHOOL = builder(SCHOOL_SYSTEM, "technical_school", "Technical School", "", SYSTEM_LARGE);
    public static final TenetGroup POST_SECONDARY_SCHOOL = builder(SCHOOL_SYSTEM, "post_secondary_school", "Post Secondary School", "", SYSTEM_LARGE);
    public static final TenetGroup PRIVATE_SCHOOL = builder(SCHOOL_SYSTEM, "private_school", "Private School", "", SYSTEM_LARGE);
    public static final TenetGroup RELIGIOUS_SCHOOL = builder(SCHOOL_SYSTEM, ImmutableList.of(RELIGIOUS_SOFT_CULTURE_INTERVENTION), "religious_school", "Religious School", "", SYSTEM_LARGE);
    public static final TenetGroup MILITARY_ACADEMY = builder(SCHOOL_SYSTEM, "military_academy", "Military Academy", "", SYSTEM_LARGE);
    public static final TenetGroup EDUCATION_LEADERSHIP = builder(SCHOOL_SYSTEM, ImmutableList.of(SOCIETY_ENFORCED_CONFORMITY, CLASS_AND_CASTE, EDUCATION_IDEOLOGY, EDUCATION_INTERVENTION, RELIGIOUS_SOFT_CULTURE_INTERVENTION), "leadership", "Education Leadership", "", SORT_ONLY);
    public static final TenetGroup TEACHER_SELECTION = builder(EDUCATION_LEADERSHIP, "selection", "Teacher Selection", "", SYSTEM_LARGE);
    public static final TenetGroup TEACHER_TRAINING = builder(EDUCATION_LEADERSHIP, "training", "Teacher Training", "", SYSTEM_LARGE);
    public static final TenetGroup TEACHER_TREATMENT = builder(EDUCATION_LEADERSHIP, "treatment", "Teacher Treatment", "", SYSTEM_LARGE);
    public static final TenetGroup LEADER_SELECTION = builder(EDUCATION_LEADERSHIP, "leader_selection", "Leader Selection", "", SYSTEM_LARGE);
}
