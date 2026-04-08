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
    public static final TenetGroup EDUCATION = builder(SOFT_CULTURE, "education", "Education", "The institutions and practices through which knowledge, skills, and values are transmitted from one generation to the next.", SORT_ONLY);
    public static final TenetGroup EDUCATION_IDEOLOGY = builder(EDUCATION, ImmutableList.of(EDUCATION_INTERVENTION, RELIGIOUS_SOFT_CULTURE_INTERVENTION), "doctrine", "Education Doctrine", "The beliefs guiding what education is for—moral formation, civic loyalty, economic utility, religious observance, or critical thought.", PILLAR);
    public static final TenetGroup RELIGIOUS_EDUCATION = builder(EDUCATION_IDEOLOGY, ImmutableList.of(RELIGION), "religious_education", "Religious Education", "The role of religious instruction in schools, from mandatory prayer and doctrine to full secular separation of faith and learning.", SYSTEM_LARGE);
    public static final TenetGroup EDUCATION_FOCUS = builder(EDUCATION_IDEOLOGY, "focus", "Education Focus", "What subjects and competencies education prioritizes—classical learning, vocational skills, civic formation, or religious doctrine.", SYSTEM_LARGE);
    public static final TenetGroup EDUCATION_ACCESS = builder(EDUCATION_IDEOLOGY, ImmutableList.of(CLASS_AND_CASTE, EDUCATION_FUNDING), "access", "Education Access", "Who is permitted to receive education and at what level, shaped by class, gender, ethnicity, geography, and wealth.", SYSTEM_LARGE);
    public static final TenetGroup EDUCATOR_VALUE = builder(EDUCATION_IDEOLOGY, ImmutableList.of(EDUCATION_FUNDING), "educator_value", "Educator Value", "The traits a society admires or condemns in teachers—learned, inspiring, obedient, indoctrinatory, or independently minded.", new AcceptanceContainer(3, Acceptance.CORE, Acceptance.CORE_FANATIC));
    public static final TenetGroup EDUCATED_VALUE = builder(EDUCATION_IDEOLOGY, ImmutableList.of(EDUCATION_FUNDING), "educated_value", "Educated Value", "The traits a society admires or condemns in the educated—cultivated, arrogant, useful, subversive, pious, or practical.", new AcceptanceContainer(3, Acceptance.CORE, Acceptance.CORE_FANATIC));
    public static final TenetGroup SCHOOL_SYSTEM = builder(EDUCATION, ImmutableList.of(SOCIETY_ENFORCED_CONFORMITY, EDUCATION_INTERVENTION, EDUCATION_IDEOLOGY, EDUCATION_FUNDING), "school_system", "School System", "The network of institutions providing formal education, from primary schools to academies, and how they are organized and funded.", PILLAR);
    public static final TenetGroup PRIMARY_SCHOOL = builder(SCHOOL_SYSTEM, "primary_school", "Primary School", "Early formal education for children, covering basic literacy, numeracy, and civic or religious foundations of knowledge.", SYSTEM_LARGE);
    public static final TenetGroup SECONDARY_SCHOOL = builder(SCHOOL_SYSTEM, "secondary_school", "Secondary School", "Intermediate education following primary schooling, deepening academic or vocational preparation for adult life.", SYSTEM_LARGE);
    public static final TenetGroup TECHNICAL_SCHOOL = builder(SCHOOL_SYSTEM, "technical_school", "Technical School", "Institutions focused on practical and vocational training in trades, crafts, and skilled occupations beyond general schooling.", SYSTEM_LARGE);
    public static final TenetGroup POST_SECONDARY_SCHOOL = builder(SCHOOL_SYSTEM, "post_secondary_school", "Post Secondary School", "Advanced education beyond secondary schooling, including universities, seminaries, and professional academies.", SYSTEM_LARGE);
    public static final TenetGroup PRIVATE_SCHOOL = builder(SCHOOL_SYSTEM, "private_school", "Private School", "Educational institutions funded and governed privately, operating outside or alongside the state system with varying independence.", SYSTEM_LARGE);
    public static final TenetGroup RELIGIOUS_SCHOOL = builder(SCHOOL_SYSTEM, ImmutableList.of(RELIGIOUS_SOFT_CULTURE_INTERVENTION), "religious_school", "Religious School", "Schools operated by religious institutions, combining academic instruction with doctrinal formation and devotional practice.", SYSTEM_LARGE);
    public static final TenetGroup MILITARY_ACADEMY = builder(SCHOOL_SYSTEM, "military_academy", "Military Academy", "Institutions training officers and military leaders in tactics, discipline, leadership, and the values of the armed forces.", SYSTEM_LARGE);
    public static final TenetGroup EDUCATION_LEADERSHIP = builder(SCHOOL_SYSTEM, ImmutableList.of(SOCIETY_ENFORCED_CONFORMITY, CLASS_AND_CASTE, EDUCATION_IDEOLOGY, EDUCATION_INTERVENTION, RELIGIOUS_SOFT_CULTURE_INTERVENTION), "leadership", "Education Leadership", "The structures governing how educational institutions are led, administered, and held accountable to the state or community.", SORT_ONLY);
    public static final TenetGroup TEACHER_SELECTION = builder(EDUCATION_LEADERSHIP, "selection", "Teacher Selection", "The criteria and processes by which teachers are recruited and certified—merit, ideology, religious affiliation, or patronage.", SYSTEM_LARGE);
    public static final TenetGroup TEACHER_TRAINING = builder(EDUCATION_LEADERSHIP, "training", "Teacher Training", "The preparation and ongoing professional development provided to educators before and during their teaching careers.", SYSTEM_LARGE);
    public static final TenetGroup TEACHER_TREATMENT = builder(EDUCATION_LEADERSHIP, "treatment", "Teacher Treatment", "How the state and society compensate, regard, and protect those who teach, including pay, prestige, and job security.", SYSTEM_LARGE);
    public static final TenetGroup LEADER_SELECTION = builder(EDUCATION_LEADERSHIP, "leader_selection", "Leader Selection", "How the heads of schools and educational institutions are chosen—by state appointment, community election, or religious authority.", SYSTEM_LARGE);
}
