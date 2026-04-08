package com.objects.culture.tenet.group;

import com.google.common.collect.ImmutableList;

import static com.objects.culture.tenet.group.GovernmentGroups.*;
import static com.objects.culture.tenet.group.SocietyGroups.*;
import static com.objects.culture.tenet.group.TenetGroup.*;

public class ReligionGroups {
    public static final TenetGroup RELIGION = builder(SOFT_CULTURE, "religion", "Religion", "", SORT_ONLY);

    public static final TenetGroup RELIGIOUS_DOCTRINE = builder(RELIGION, "doctrine", "Religious Doctrine", "", SORT_ONLY);
    public static final TenetGroup COSMOLOGY = builder(RELIGIOUS_DOCTRINE, "cosmology", "Cosmology", "", SYSTEM_LARGE);
    public static final TenetGroup AFTERLIFE_ENDTIMES = builder(RELIGIOUS_DOCTRINE, "afterlife", "Afterlife & Endtimes", "", SYSTEM_LARGE);
    public static final TenetGroup VIRTUE_AND_VICE = builder(RELIGIOUS_DOCTRINE, "virtue_and_vice", "Virtue and Vice", "", SYSTEM_LARGE);
    public static final TenetGroup RELIGIOUS_SOFT_CULTURE_INTERVENTION = builder(RELIGIOUS_DOCTRINE, ImmutableList.of(SOFT_CULTURE_INTERVENTION), "soft_culture_intervention", "Religious Soft Culture Intervention", "", SYSTEM_LARGE);
    public static final TenetGroup RELIGIOUS_GOVERNMENT_INTERVENTION = builder(RELIGIOUS_DOCTRINE, ImmutableList.of(SECULARISM), "government_intervention", "Religious Government Intervention", "", SYSTEM_LARGE);
    public static final TenetGroup RELIGION_FAMILY_INTERVENTION = builder(RELIGIOUS_DOCTRINE, ImmutableList.of(FAMILY_INTERVENTION), "family_intervention", "Religious Family Intervention", "", SYSTEM_LARGE);
    public static final TenetGroup RELIGION_LEADERSHIP = builder(RELIGIOUS_DOCTRINE, ImmutableList.of(CLASS_AND_CASTE), "leadership", "Religious Leadership", "", SYSTEM_LARGE);
    public static final TenetGroup RELIGION_LEADER_SELECTION = builder(RELIGION_LEADERSHIP, ImmutableList.of(CLASS_AND_CASTE), "selection", "Religious Leader Selection", "", SYSTEM_LARGE);
    public static final TenetGroup RELIGION_LEADER_AUTHORITY = builder(RELIGION_LEADERSHIP, ImmutableList.of(CLASS_AND_CASTE), "authority", "Religious Leader Authority", "", SYSTEM_LARGE);
    public static final TenetGroup RELIGION_LEADER_REMOVAL = builder(RELIGION_LEADERSHIP, ImmutableList.of(CLASS_AND_CASTE), "removal", "Religious Leader Removal", "", SYSTEM_LARGE);
    public static final TenetGroup RELIGION_PERMEABILITY = builder(RELIGIOUS_DOCTRINE, ImmutableList.of(CLASS_AND_CASTE), "religion_permanence", "Religion Permeability", "", SYSTEM_LARGE);

    public static final TenetGroup PRACTICES = builder(RELIGION, ImmutableList.of(SECULARISM, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY), "practices", "Religious Practices", "", SORT_ONLY);
    public static final TenetGroup LITURGICAL_LANGUAGE = builder(PRACTICES, "liturgical_language", "Liturgical Language", "", SYSTEM_LARGE);
    public static final TenetGroup RELIGIOUS_TRADITION_RITUAL = builder(PRACTICES, ImmutableList.of(TRADITION_RITUAL), "tradition_ritual", "Religious Tradition/Ritual", "", SYSTEM_LARGE);
    public static final TenetGroup RELIGIOUS_HOLIDAY_GATHERING = builder(PRACTICES, ImmutableList.of(HOLIDAY_AND_GATHERING, TRADITION_RITUAL, RELIGIOUS_TRADITION_RITUAL), "gathering", "Religious Holidays and Gatherings", "", SYSTEM_LARGE);
    public static final TenetGroup RELIGIOUS_ART_AND_MEDIA = builder(PRACTICES, ImmutableList.of(ART_AND_MEDIA), "art_and_media", "Religious Art and Media", "", SYSTEM_LARGE);
    public static final TenetGroup RELIGIOUS_ARCHITECTURE = builder(PRACTICES, ImmutableList.of(ARCHITECTURE), "architecture", "Religious Architecture", "", SYSTEM_LARGE);
    public static final TenetGroup RELIGIOUS_FASHION = builder(PRACTICES, ImmutableList.of(FASHION), "fashion", "Religious Fashion", "", SYSTEM_LARGE);
}
