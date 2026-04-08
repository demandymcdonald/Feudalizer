package com.objects.culture.tenet.group;

import com.Global.*;
import com.google.common.collect.ImmutableList;
import com.objects.culture.tenet.Acceptance;

import static com.objects.culture.tenet.group.GovernmentGroups.*;
import static com.objects.culture.tenet.group.TenetGroup.*;

public class SocietyGroups {

    public static final TenetGroup SOCIETY = builder(SOFT_CULTURE, "society", "Society", "", SORT_ONLY);

    public static final TenetGroup SOCIETY_ENFORCED_CONFORMITY = builder(SOCIETY, ImmutableList.of(GOVERNMENT_ENFORCED_CONFORMITY), "conformity", "Social Conformity", "", SYSTEM_LARGE);
    public static final TenetGroup TRADITION_RITUAL = builder(SOCIETY, ImmutableList.of(CIVIL_LIBERTIES, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY), "tradition_ritual", "Tradition/Ritual", "", SYSTEM_LARGE);
    public static final TenetGroup VALUES = builder(SOCIETY, ImmutableList.of(SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY), "values", "Values", "", SYSTEM_LARGE);
    public static final TenetGroup RACE_AND_ETHNIC_ORIGIN = builder(SOCIETY, ImmutableList.of(SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY, CLASS_AND_CASTE, CIVIL_LIBERTIES, FOREIGN_POLICY), "race_and_ethnic_origin", "Race and Ethnic Origin", "", SYSTEM_LARGE);
    public static final TenetGroup LANGUAGE = builder(SOCIETY, ImmutableList.of(CIVIL_LIBERTIES, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY), "language", "Language", "", new AcceptanceContainer(1, Acceptance.CORE_FANATIC), new AcceptanceContainer(3, Acceptance.CORE));
    public static final TenetGroup HOLIDAY_AND_GATHERING = builder(SOCIETY, ImmutableList.of(CIVIL_LIBERTIES, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY), "holiday_and_gathering", "Holidays and Gatherings", "", SYSTEM_LARGE);
    public static final TenetGroup AESTHETICS_AND_VISUAL = builder(SOCIETY, ImmutableList.of(CIVIL_LIBERTIES, CLASS_AND_CASTE, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY), "aesthetics_and_visual", "Aesthetics and Visual", "", SORT_ONLY);
    public static final TenetGroup ART_AND_MEDIA = builder(AESTHETICS_AND_VISUAL, "art_and_media", "Art and Media", "", SYSTEM_LARGE);
    public static final TenetGroup ARCHITECTURE = builder(AESTHETICS_AND_VISUAL, "architecture", "Architecture", "", SYSTEM_LARGE);
    public static final TenetGroup FASHION = builder(AESTHETICS_AND_VISUAL, "fashion", "Fashion", "", SYSTEM_LARGE);

}
