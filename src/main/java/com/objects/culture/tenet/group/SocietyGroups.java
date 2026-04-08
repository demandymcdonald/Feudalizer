package com.objects.culture.tenet.group;

import com.Global.*;
import com.google.common.collect.ImmutableList;
import com.objects.culture.tenet.Acceptance;

import static com.objects.culture.tenet.group.GovernmentGroups.*;
import static com.objects.culture.tenet.group.TenetGroup.*;

public class SocietyGroups {

    public static final TenetGroup SOCIETY = builder(SOFT_CULTURE, "society", "Society", "The informal norms, shared identities, and collective life of a people beyond the formal institutions of state and religion.", SORT_ONLY);

    public static final TenetGroup SOCIETY_ENFORCED_CONFORMITY = builder(SOCIETY, ImmutableList.of(GOVERNMENT_ENFORCED_CONFORMITY), "conformity", "Social Conformity", "Social pressure and custom that compel individuals to conform to dominant norms, distinct from but reinforcing legal mandates.", SYSTEM_LARGE);
    public static final TenetGroup TRADITION_RITUAL = builder(SOCIETY, ImmutableList.of(CIVIL_LIBERTIES, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY), "tradition_ritual", "Tradition/Ritual", "The recurring practices and ceremonies through which a society marks time, transitions, and collective identity across generations.", SYSTEM_LARGE);
    public static final TenetGroup VALUES = builder(SOCIETY, ImmutableList.of(SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY), "values", "Values", "The principles and ideals a society holds dear, shaping attitudes toward work, honor, family, community, and the good life.", SYSTEM_LARGE);
    public static final TenetGroup RACE_AND_ETHNIC_ORIGIN = builder(SOCIETY, ImmutableList.of(SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY, CLASS_AND_CASTE, CIVIL_LIBERTIES, FOREIGN_POLICY), "race_and_ethnic_origin", "Race and Ethnic Origin", "The role of racial and ethnic identity in social life, including privilege, discrimination, integration, and intermarriage norms.", SYSTEM_LARGE);
    public static final TenetGroup LANGUAGE = builder(SOCIETY, ImmutableList.of(CIVIL_LIBERTIES, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY), "language", "Language", "The tongue(s) a society uses in daily life, education, and government, and the status accorded to dominant or minority language speakers.", new AcceptanceContainer(1, Acceptance.CORE_FANATIC), new AcceptanceContainer(3, Acceptance.CORE));
    public static final TenetGroup HOLIDAY_AND_GATHERING = builder(SOCIETY, ImmutableList.of(CIVIL_LIBERTIES, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY), "holiday_and_gathering", "Holidays and Gatherings", "The public celebrations, memorials, and seasonal festivals through which a society marks its calendar and shared identity.", SYSTEM_LARGE);
    public static final TenetGroup AESTHETICS_AND_VISUAL = builder(SOCIETY, ImmutableList.of(CIVIL_LIBERTIES, CLASS_AND_CASTE, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY), "aesthetics_and_visual", "Aesthetics and Visual", "The visual culture of a society—standards of beauty, artistic tradition, and the social status of creative expression.", SORT_ONLY);
    public static final TenetGroup ART_AND_MEDIA = builder(AESTHETICS_AND_VISUAL, "art_and_media", "Art and Media", "The creative works and communication channels through which a society expresses its values, tells its stories, and entertains itself.", SYSTEM_LARGE);
    public static final TenetGroup ARCHITECTURE = builder(AESTHETICS_AND_VISUAL, "architecture", "Architecture", "The design principles and styles shaping how buildings, monuments, and public spaces are constructed and what they express about society.", SYSTEM_LARGE);
    public static final TenetGroup FASHION = builder(AESTHETICS_AND_VISUAL, "fashion", "Fashion", "The clothing, adornment, and styles through which people signal identity, status, and cultural belonging within a society.", SYSTEM_LARGE);

}
