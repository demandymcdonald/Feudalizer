package com.objects.culture.tenet.group.groups;

import com.google.common.collect.ImmutableList;
import com.objects.culture.tenet.group.TGType;
import com.objects.culture.tenet.group.TenetGroup;

import static com.objects.culture.tenet.TenetManager.SOFT_CULTURE;
import static com.objects.culture.tenet.group.groups.GovernmentGroups.*;
import static com.objects.culture.tenet.group.groups.SocietyGroups.*;
import static com.objects.culture.tenet.group.TenetGroup.*;
public class ReligionGroups {

    // RELIGION
    public static final TenetGroup RELIGION = new TenetGroup.Builder(TGType.SORT_ONLY, Level.PILLAR, "religion", "Religion", "")
            .setParent(SOFT_CULTURE)
            .build();

    // Pillar
    public static final TenetGroup RELIGIOUS_DOCTRINE = new TenetGroup.Builder(TGType.PILLAR_SYSTEM, Level.CATEGORY, "doctrine", "Religious Doctrine", "")
            .setParent(RELIGION)
            .build();
    public static final TenetGroup PRACTICES = new TenetGroup.Builder(TGType.PILLAR_IDEOLOGY, Level.CATEGORY, "practices", "Religious Practices", "")
            .setParent(RELIGION)
            .addConnected(SECULARISM, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
            .build();

    // Religious Doctrine
    public static final TenetGroup COSMOLOGY = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "cosmology", "Cosmology", "")
            .setParent(RELIGIOUS_DOCTRINE)
            .build();
    public static final TenetGroup AFTERLIFE_ENDTIMES = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "afterlife", "Afterlife & Endtimes", "")
            .setParent(RELIGIOUS_DOCTRINE)
            .build();
    public static final TenetGroup VIRTUE_AND_VICE = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "virtue_and_vice", "Virtue and Vice", "")
            .setParent(RELIGIOUS_DOCTRINE)
            .build();
    public static final TenetGroup RELIGIOUS_SOFT_CULTURE_INTERVENTION = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "soft_culture_intervention", "Religious Soft Culture Intervention", "")
            .setParent(RELIGIOUS_DOCTRINE)
            .addConnected(SOFT_CULTURE_INTERVENTION)
            .build();
    public static final TenetGroup RELIGIOUS_GOVERNMENT_INTERVENTION = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "government_intervention", "Religious GovernmentTenet Intervention", "")
            .setParent(RELIGIOUS_DOCTRINE)
            .addConnected(SECULARISM)
            .build();
    public static final TenetGroup RELIGION_FAMILY_INTERVENTION = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "family_intervention", "Religious FamilyGroups Intervention", "")
            .setParent(RELIGIOUS_DOCTRINE)
            .addConnected(FAMILY_INTERVENTION)
            .build();
    public static final TenetGroup RELIGIOUS_CONFORMITY = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "religion_permanence", "Religion Permanence", "")
            .setParent(RELIGIOUS_DOCTRINE)
            .addConnected(SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY)
            .build();
    public static final TenetGroup RELIGION_PERMEABILITY = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "religion_permanence", "Religion Permeability", "")
            .setParent(RELIGIOUS_DOCTRINE)
            .addConnected(CLASS_AND_CASTE, RELIGIOUS_CONFORMITY)
            .build();
    public static final TenetGroup RELIGIOUS_TOLERANCE = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "religion_tolerance", "Religion Tolerance for Other Religions", "")
            .setParent(RELIGIOUS_DOCTRINE)
            .addConnected(COSMOLOGY, RELIGIOUS_CONFORMITY)
            .build();

    // Religious Leadership
    public static final TenetGroup RELIGION_LEADERSHIP = new TenetGroup.Builder(TGType.SYSTEM_SORT, Level.SUBCATEGORY, "leadership", "Religious Leadership", "")
            .setParent(RELIGIOUS_DOCTRINE)
            .addConnected(CLASS_AND_CASTE)
            .build();
    public static final TenetGroup PRIEST_TYPES = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "types", "Priest Types", "")
    public static final TenetGroup RELIGION_LEADER_SELECTION = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "selection", "Religious Leader Selection", "")
            .setParent(RELIGION_LEADERSHIP)
            .addConnected(CLASS_AND_CASTE)
            .build();
    public static final TenetGroup RELIGION_LEADER_AUTHORITY = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "authority", "Religious Leader Authority", "")
            .setParent(RELIGION_LEADERSHIP)
            .addConnected(CLASS_AND_CASTE)
            .build();
    public static final TenetGroup RELIGION_LEADER_REMOVAL = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "removal", "Religious Leader Removal", "")
            .setParent(RELIGION_LEADERSHIP)
            .addConnected(CLASS_AND_CASTE)
            .build();
    public static final TenetGroup RELIGION_LEADER_CORRUPTION = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "corruption", "Religious Leader Corruption", "")
            .setParent(RELIGION_LEADERSHIP)
            .addConnected(RELIGION_LEADER_AUTHORITY)
            .build();

    // Religious Practices
    public static final TenetGroup LITURGICAL_LANGUAGE = new TenetGroup.Builder(TGType.LANGUAGE, Level.NORMAL, "liturgical_language", "Liturgical Language", "")
            .setParent(PRACTICES)
            .build();
    public static final TenetGroup RELIGIOUS_TRADITION_RITUAL = new TenetGroup.Builder(TGType.TRADITION, Level.NORMAL, "tradition_ritual", "Religious Tradition/Ritual", "")
            .setParent(PRACTICES)
            .addConnected(TRADITION_RITUAL)
            .build();
    public static final TenetGroup RELIGIOUS_HOLIDAY_GATHERING = new TenetGroup.Builder(TGType.TRADITION, Level.NORMAL, "gathering", "Religious Holidays and Gatherings", "")
            .setParent(PRACTICES)
            .addConnected(HOLIDAY_AND_GATHERING, TRADITION_RITUAL, RELIGIOUS_TRADITION_RITUAL)
            .build();
    public static final TenetGroup RELIGIOUS_ART_AND_MEDIA = new TenetGroup.Builder(TGType.AESTHETIC, Level.LORE_ONLY, "art_and_media", "Religious Art and Media", "")
            .setParent(PRACTICES)
            .addConnected(ART_AND_MEDIA)
            .build();
    public static final TenetGroup RELIGIOUS_ARCHITECTURE = new TenetGroup.Builder(TGType.AESTHETIC, Level.LORE_ONLY, "architecture", "Religious Architecture", "")
            .setParent(PRACTICES)
            .addConnected(ARCHITECTURE)
            .build();
    public static final TenetGroup RELIGIOUS_FASHION = new TenetGroup.Builder(TGType.AESTHETIC, Level.LORE_ONLY, "fashion", "Religious Fashion", "")
            .setParent(PRACTICES)
            .addConnected(FASHION)
            .build();



    public static void init() {

    }

}
