package com.objects.culture.tenet.group;

import com.google.common.collect.ImmutableList;

import static com.objects.culture.tenet.group.GovernmentGroups.*;
import static com.objects.culture.tenet.group.SocietyGroups.*;
import static com.objects.culture.tenet.group.TenetGroup.*;

public class ReligionGroups {
    public static final TenetGroup RELIGION = builder(SOFT_CULTURE, "religion", "Religion", "The spiritual and institutional dimensions of faith, encompassing doctrine, practice, leadership, and its relationship to civil society.", SORT_ONLY);

    public static final TenetGroup RELIGIOUS_DOCTRINE = builder(RELIGION, "doctrine", "Religious Doctrine", "The core theological beliefs of a religion—its cosmology, ethics, eschatology, and stance on worldly power and authority.", SORT_ONLY);
    public static final TenetGroup COSMOLOGY = builder(RELIGIOUS_DOCTRINE, "cosmology", "Cosmology", "The gods, divine forces, or fundamental powers a religion affirms or rejects, and how they relate to the cosmos, earth, and humanity.", SYSTEM_LARGE);
    public static final TenetGroup AFTERLIFE_ENDTIMES = builder(RELIGIOUS_DOCTRINE, "afterlife", "Afterlife & Endtimes", "Beliefs about what awaits souls after death and how the world or current age will eventually end or be transformed.", SYSTEM_LARGE);
    public static final TenetGroup VIRTUE_AND_VICE = builder(RELIGIOUS_DOCTRINE, "virtue_and_vice", "Virtue and Vice", "The moral framework of a religion—what behaviors, attitudes, and character traits are deemed holy, sinful, or somewhere between.", SYSTEM_LARGE);
    public static final TenetGroup RELIGIOUS_SOFT_CULTURE_INTERVENTION = builder(RELIGIOUS_DOCTRINE, ImmutableList.of(SOFT_CULTURE_INTERVENTION), "soft_culture_intervention", "Religious Soft Culture Intervention", "The degree to which religious doctrine calls for reshaping family, society, and education according to religious law or values.", SYSTEM_LARGE);
    public static final TenetGroup RELIGIOUS_GOVERNMENT_INTERVENTION = builder(RELIGIOUS_DOCTRINE, ImmutableList.of(SECULARISM), "government_intervention", "Religious Government Intervention", "The role religious institutions or doctrine claims in shaping governance, legislation, and political authority.", SYSTEM_LARGE);
    public static final TenetGroup RELIGION_FAMILY_INTERVENTION = builder(RELIGIOUS_DOCTRINE, ImmutableList.of(FAMILY_INTERVENTION), "family_intervention", "Religious Family Intervention", "The extent to which religious doctrine prescribes or proscribes family structure, marriage, and the raising of children.", SYSTEM_LARGE);
    public static final TenetGroup RELIGION_LEADERSHIP = builder(RELIGIOUS_DOCTRINE, ImmutableList.of(CLASS_AND_CASTE), "leadership", "Religious Leadership", "The hierarchies and offices through which religious authority is organized, from solo prophets to vast clerical institutions.", SYSTEM_LARGE);
    public static final TenetGroup RELIGION_LEADER_SELECTION = builder(RELIGION_LEADERSHIP, ImmutableList.of(CLASS_AND_CASTE), "selection", "Religious Leader Selection", "How religious leaders are chosen—by heredity, divine appointment, election by clergy, or popular acclaim of the faithful.", SYSTEM_LARGE);
    public static final TenetGroup RELIGION_LEADER_AUTHORITY = builder(RELIGION_LEADERSHIP, ImmutableList.of(CLASS_AND_CASTE), "authority", "Religious Leader Authority", "The scope of power held by religious leaders over doctrine, practice, and the faithful, from advisory to absolute.", SYSTEM_LARGE);
    public static final TenetGroup RELIGION_LEADER_REMOVAL = builder(RELIGION_LEADERSHIP, ImmutableList.of(CLASS_AND_CASTE), "removal", "Religious Leader Removal", "The circumstances under which religious leaders may be deposed, excommunicated, or replaced, and by whose authority.", SYSTEM_LARGE);
    public static final TenetGroup RELIGION_PERMEABILITY = builder(RELIGIOUS_DOCTRINE, ImmutableList.of(CLASS_AND_CASTE), "religion_permanence", "Religion Permeability", "How open or closed a religion is to converts, apostates, and those who drift between active faith and secular life.", SYSTEM_LARGE);

    public static final TenetGroup PRACTICES = builder(RELIGION, ImmutableList.of(SECULARISM, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY), "practices", "Religious Practices", "The rituals, customs, language, art, and gatherings through which religious life is expressed and community is maintained.", SORT_ONLY);
    public static final TenetGroup LITURGICAL_LANGUAGE = builder(PRACTICES, "liturgical_language", "Liturgical Language", "The language(s) used in religious rites and sacred texts, whether accessible to all or reserved for a priestly class.", SYSTEM_LARGE);
    public static final TenetGroup RELIGIOUS_TRADITION_RITUAL = builder(PRACTICES, ImmutableList.of(TRADITION_RITUAL), "tradition_ritual", "Religious Tradition/Ritual", "The ceremonies, rites, and customs through which the faithful observe their religion's calendar and mark life milestones.", SYSTEM_LARGE);
    public static final TenetGroup RELIGIOUS_HOLIDAY_GATHERING = builder(PRACTICES, ImmutableList.of(HOLIDAY_AND_GATHERING, TRADITION_RITUAL, RELIGIOUS_TRADITION_RITUAL), "gathering", "Religious Holidays and Gatherings", "Sacred days and communal assemblies—from solemn fasts to joyous festivals—that mark the religious calendar and bind the faithful.", SYSTEM_LARGE);
    public static final TenetGroup RELIGIOUS_ART_AND_MEDIA = builder(PRACTICES, ImmutableList.of(ART_AND_MEDIA), "art_and_media", "Religious Art and Media", "Visual art, music, literature, and other media created to express or transmit religious belief, from icons to sacred scripture.", SYSTEM_LARGE);
    public static final TenetGroup RELIGIOUS_ARCHITECTURE = builder(PRACTICES, ImmutableList.of(ARCHITECTURE), "architecture", "Religious Architecture", "The design and construction of sacred spaces—temples, cathedrals, shrines—expressing a religion's nature and worldly ambitions.", SYSTEM_LARGE);
    public static final TenetGroup RELIGIOUS_FASHION = builder(PRACTICES, ImmutableList.of(FASHION), "fashion", "Religious Fashion", "Dress codes, vestments, and symbols prescribed or proscribed by religion, from clerical robes to modesty rules for the laity.", SYSTEM_LARGE);
}
