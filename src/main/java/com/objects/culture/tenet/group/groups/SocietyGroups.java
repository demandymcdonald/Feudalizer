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

//    public static final TenetGroup SOCIETY = builder(SOFT_CULTURE, "society", "Society", "", SORT_ONLY);
//
//    public static final TenetGroup SOCIETY_ENFORCED_CONFORMITY = builder(SOCIETY, ImmutableList.of(GOVERNMENT_ENFORCED_CONFORMITY), "conformity", "Social Conformity", "", SYSTEM_LARGE);
//    public static final TenetGroup TRADITION_RITUAL = builder(SOCIETY, ImmutableList.of(CIVIL_LIBERTIES, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY), "tradition_ritual", "Tradition/Ritual", "", SYSTEM_LARGE);
//    public static final TenetGroup VALUES = builder(SOCIETY, ImmutableList.of(SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY), "values", "Values", "", SYSTEM_LARGE);
//    public static final TenetGroup RACE_AND_ETHNIC_ORIGIN = builder(SOCIETY, ImmutableList.of(SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY, CLASS_AND_CASTE, CIVIL_LIBERTIES, FOREIGN_POLICY), "race_and_ethnic_origin", "Race and Ethnic Origin", "", SYSTEM_LARGE);
//    public static final TenetGroup LANGUAGE = builder(SOCIETY, ImmutableList.of(CIVIL_LIBERTIES, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY), "language", "Language", "", new AcceptanceContainer(1, Acceptance.CORE_FANATIC), new AcceptanceContainer(3, Acceptance.CORE));
//    public static final TenetGroup HOLIDAY_AND_GATHERING = builder(SOCIETY, ImmutableList.of(CIVIL_LIBERTIES, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY), "holiday_and_gathering", "Holidays and Gatherings", "", SYSTEM_LARGE);
//    public static final TenetGroup AESTHETICS_AND_VISUAL = builder(SOCIETY, ImmutableList.of(CIVIL_LIBERTIES, CLASS_AND_CASTE, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY), "aesthetics_and_visual", "Aesthetics and Visual", "", SORT_ONLY);
//    public static final TenetGroup ART_AND_MEDIA = builder(AESTHETICS_AND_VISUAL, "art_and_media", "Art and Media", "", SYSTEM_LARGE);
//    public static final TenetGroup ARCHITECTURE = builder(AESTHETICS_AND_VISUAL, "architecture", "Architecture", "", SYSTEM_LARGE);
//    public static final TenetGroup FASHION = builder(AESTHETICS_AND_VISUAL, "fashion", "Fashion", "", SYSTEM_LARGE);

    // SOCIETY
    public static final TenetGroup SOCIETY = builder(TGType.PILLAR_IDEOLOGY, SOFT_CULTURE, "society", "Society", "");

    // Society
    public static final TenetGroup SOCIETY_ENFORCED_CONFORMITY = builder(TGType.BELIEF_MAJOR, SOCIETY, ImmutableList.of(GOVERNMENT_ENFORCED_CONFORMITY), "conformity", "Social Conformity", "");
    public static final TenetGroup TRADITION_RITUAL = builder(TGType.TRADITION, SOCIETY, ImmutableList.of(CIVIL_LIBERTIES, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY), "tradition_ritual", "Tradition/Ritual", ""); // TODO: update to TRADITION type when added
    public static final TenetGroup VALUES = builder(TGType.VALUE, SOCIETY, ImmutableList.of(SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY), "values", "Values", "");
    public static final TenetGroup RACE_AND_ETHNIC_ORIGIN = builder(TGType.BELIEF_MAJOR, SOCIETY, ImmutableList.of(SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY, CLASS_AND_CASTE, CIVIL_LIBERTIES, FOREIGN_POLICY), "race_and_ethnic_origin", "Race and Ethnic Origin", "");
    public static final TenetGroup LANGUAGE = builder(TGType.LANGUAGE, SOCIETY, ImmutableList.of(CIVIL_LIBERTIES, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY), "language", "Language", ""); // TODO: update to LANGUAGE type when added
    public static final TenetGroup HOLIDAY_AND_GATHERING = builder(TGType.TRADITION, SOCIETY, ImmutableList.of(CIVIL_LIBERTIES, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY), "holiday_and_gathering", "Holidays and Gatherings", ""); // TODO: update to TRADITION type when added
    public static final TenetGroup SOCIAL_GENDER_ROLES = builder(TGType.BELIEF_MAJOR, SOCIETY, ImmutableList.of(GOVERNMENT_LEADERSHIP,RELIGION_LEADERSHIP,EDUCATION_LEADERSHIP,SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY,RELIGIOUS_CONFORMITY,PARENT_ROLES), "social_gender_roles", "Social Gender Roles", "");
    // Aesthetics and Visual
    public static final TenetGroup AESTHETICS_AND_VISUAL = builder(TGType.IDEOLOGY_SORT, SOCIETY, ImmutableList.of(CIVIL_LIBERTIES, CLASS_AND_CASTE, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY), "aesthetics_and_visual", "Aesthetics and Visual", "");
    public static final TenetGroup ART_AND_MEDIA = builder(TGType.AESTHETIC, AESTHETICS_AND_VISUAL, "art_and_media", "Art and Media", "");
    public static final TenetGroup ARCHITECTURE = builder(TGType.AESTHETIC, AESTHETICS_AND_VISUAL, "architecture", "Architecture", "");
    public static final TenetGroup FASHION = builder(TGType.AESTHETIC, AESTHETICS_AND_VISUAL, "fashion", "Fashion", "");
    public static void init() {

    }

}
