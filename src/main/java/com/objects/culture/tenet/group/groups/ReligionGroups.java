package com.objects.culture.tenet.group.groups;

import com.base.component.InstanceType;
import com.objects.culture.tenet.group.TGType;
import com.objects.culture.tenet.group.TenetGroup;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.Function;

import static com.objects.culture.TenetManager.SOFT_CULTURE;
import static com.objects.culture.tenet.group.groups.GovernmentGroups.*;
import static com.objects.culture.tenet.group.groups.SocietyGroups.*;
import static com.objects.culture.tenet.group.TenetGroup.*;
public class ReligionGroups {
    public static class ReligionGroup extends TenetGroup {
        public ReligionGroup(InstanceType instType, @NonNull TGType type, Level level, String fullID, String id, String name, String description) {
            super(instType,type, level,fullID, id, name, description);
        }
    }

    public static final ReligionGroup RELIGION = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.SORT_ONLY, Level.PILLAR, "religion", "Religion", "")
            .setParent(SOFT_CULTURE)
            .build();

    // Pillar
    public static final ReligionGroup RELIGIOUS_DOCTRINE = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.PILLAR_IDEOLOGY, Level.CATEGORY, "doctrine", "Religious Doctrine", "")
            .setParent(RELIGION)
            .build();
    public static final ReligionGroup PRACTICES = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.PILLAR_SYSTEM, Level.CATEGORY, "practices", "Religious Practices", "")
            .setParent(RELIGION)
            .addConnected(SECULARISM, GOVERNMENT_ENFORCED_CONFORMITY, SOCIETY_ENFORCED_CONFORMITY)
            .build();

    // Religious Doctrine
    public static final ReligionGroup COSMOLOGY = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.BELIEF_MAJOR, Level.NORMAL, "cosmology", "Cosmology", "")
            .setParent(RELIGIOUS_DOCTRINE)
            .build();
    public static final ReligionGroup PANTHEON = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.IDEOLOGY_SORT, Level.NORMAL, "pantheon", "Pantheon", "")
            .setParent(RELIGIOUS_DOCTRINE)
            .build();
        public static final ReligionGroup DEMIURGE = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.DIVINE_ENTITY, Level.NORMAL, "demiurge", "Demiurge", "")
                .setParent(PANTHEON)
                .build();
    public static final ReligionGroup SUPREME_GOD = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.DIVINE_ENTITY, Level.NORMAL, "supreme", "Supreme God", "")
            .setParent(PANTHEON)
            .build();
    public static final ReligionGroup MAJOR_GOD = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.DIVINE_ENTITY, Level.NORMAL, "major", "Major God", "")
            .setParent(PANTHEON)
            .build();
    public static final ReligionGroup MINOR_GOD = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.DIVINE_ENTITY, Level.NORMAL, "minor", "Minor God", "")
            .setParent(PANTHEON)
            .build();
    public static final ReligionGroup DEMIGOD = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.DIVINE_ENTITY, Level.NORMAL, "demigod", "Demigod", "")
            .setParent(PANTHEON)
            .build();
    public static final ReligionGroup APOTHEOSIZED = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.DIVINE_ENTITY, Level.NORMAL, "apotheosized", "Apotheosized Creature", "")
            .setParent(PANTHEON)
            .build();
    public static final ReligionGroup ANGEL = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.DIVINE_ENTITY, Level.NORMAL, "angel", "Angel", "")
            .setParent(PANTHEON)
            .build();
    public static final ReligionGroup FALLEN_ANGEL = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.DIVINE_ENTITY, Level.NORMAL, "fallen_angel", "Fallen Angel", "")
            .setParent(PANTHEON)
            .build();
    public static final ReligionGroup DEMON = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.DIVINE_ENTITY, Level.NORMAL, "demon", "Demon", "")
            .setParent(PANTHEON)
            .build();
    public static final ReligionGroup INTERMEDIARY_SPIRIT = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.DIVINE_ENTITY, Level.NORMAL, "intermediary", "Intermediary Spirit", "")
            .setParent(PANTHEON)
            .build();
    public static final ReligionGroup AFTERLIFE_ENDTIMES = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.BELIEF_MAJOR, Level.NORMAL, "afterlife", "Afterlife & Endtimes", "")
            .setParent(RELIGIOUS_DOCTRINE)
            .build();
    public static final ReligionGroup VIRTUE_AND_VICE = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.SYSTEM_LARGE, Level.NORMAL, "virtue_and_vice", "Virtue and Vice", "")
            .setParent(PRACTICES)
            .build();
    public static final ReligionGroup RELIGIOUS_SOFT_CULTURE_INTERVENTION = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.SYSTEM_LARGE, Level.NORMAL, "soft_culture_intervention", "Religious Soft Culture Intervention", "")
            .setParent(PRACTICES)
            .addConnected(SOFT_CULTURE_INTERVENTION)
            .build();
    public static final ReligionGroup RELIGIOUS_GOVERNMENT_INTERVENTION = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.SYSTEM_LARGE, Level.NORMAL, "government_intervention", "Religious GovernmentTenet Intervention", "")
            .setParent(PRACTICES)
            .addConnected(SECULARISM)
            .build();
    public static final ReligionGroup RELIGION_FAMILY_INTERVENTION = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.SYSTEM_LARGE, Level.NORMAL, "family_intervention", "Religious FamilyGroups Intervention", "")
            .setParent(PRACTICES)
            .addConnected(FAMILY_INTERVENTION)
            .build();
    public static final ReligionGroup RELIGIOUS_CONFORMITY = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.SYSTEM_LARGE, Level.NORMAL, "religion_permanence", "Religion Permanence", "")
            .setParent(PRACTICES)
            .addConnected(SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY)
            .build();
    public static final ReligionGroup RELIGION_PERMEABILITY = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.SYSTEM_LARGE, Level.NORMAL, "religion_permanence", "Religion Permeability", "")
            .setParent(PRACTICES)
            .addConnected(CLASS_AND_CASTE, RELIGIOUS_CONFORMITY)
            .build();
    public static final ReligionGroup RELIGIOUS_TOLERANCE = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.SYSTEM_LARGE, Level.NORMAL, "religion_tolerance", "Religion Tolerance for Other Religions", "")
            .setParent(PRACTICES)
            .addConnected(COSMOLOGY, RELIGIOUS_CONFORMITY)
            .build();

    // Religious Leadership
    public static final ReligionGroup RELIGION_LEADERSHIP = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.SYSTEM_SORT, Level.SUBCATEGORY, "leadership", "Religious Leadership", "")
            .setParent(RELIGIOUS_DOCTRINE)
            .addConnected(CLASS_AND_CASTE)
            .build();
    public static final ReligionGroup PRIEST_TYPES = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.SYSTEM_LARGE, Level.NORMAL, "types", "Priest Types", "")
    public static final ReligionGroup RELIGION_LEADER_SELECTION = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.SYSTEM_LARGE, Level.NORMAL, "selection", "Religious Leader Selection", "")
            .setParent(RELIGION_LEADERSHIP)
            .addConnected(CLASS_AND_CASTE)
            .build();
    public static final ReligionGroup RELIGION_LEADER_AUTHORITY = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.SYSTEM_LARGE, Level.NORMAL, "authority", "Religious Leader Authority", "")
            .setParent(RELIGION_LEADERSHIP)
            .addConnected(CLASS_AND_CASTE)
            .build();
    public static final ReligionGroup RELIGION_LEADER_REMOVAL = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.SYSTEM_LARGE, Level.NORMAL, "removal", "Religious Leader Removal", "")
            .setParent(RELIGION_LEADERSHIP)
            .addConnected(CLASS_AND_CASTE)
            .build();
    public static final ReligionGroup RELIGION_LEADER_CORRUPTION = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.SYSTEM_LARGE, Level.NORMAL, "corruption", "Religious Leader Corruption", "")
            .setParent(RELIGION_LEADERSHIP)
            .addConnected(RELIGION_LEADER_AUTHORITY)
            .build();

    // Religious Practices
    public static final ReligionGroup LITURGICAL_LANGUAGE = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.LANGUAGE, Level.NORMAL, "liturgical_language", "Liturgical Language", "")
            .setParent(PRACTICES)
            .build();
    public static final ReligionGroup RELIGIOUS_TRADITION_RITUAL = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.TRADITION, Level.NORMAL, "tradition_ritual", "Religious Tradition/Ritual", "")
            .setParent(PRACTICES)
            .addConnected(TRADITION_RITUAL)
            .build();
    public static final ReligionGroup RELIGIOUS_HOLIDAY_GATHERING = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.TRADITION, Level.NORMAL, "gathering", "Religious Holidays and Gatherings", "")
            .setParent(PRACTICES)
            .addConnected(HOLIDAY_AND_GATHERING, TRADITION_RITUAL, RELIGIOUS_TRADITION_RITUAL)
            .build();
    public static final ReligionGroup RELIGIOUS_ART_AND_MEDIA = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.AESTHETIC, Level.LORE_ONLY, "art_and_media", "Religious Art and Media", "")
            .setParent(PRACTICES)
            .addConnected(ART_AND_MEDIA)
            .build();
    public static final ReligionGroup RELIGIOUS_ARCHITECTURE = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.AESTHETIC, Level.LORE_ONLY, "architecture", "Religious Architecture", "")
            .setParent(PRACTICES)
            .addConnected(ARCHITECTURE)
            .build();
    public static final ReligionGroup RELIGIOUS_FASHION = new TenetGroup.Builder<ReligionGroup>(Builder.RGF,TGType.AESTHETIC, Level.LORE_ONLY, "fashion", "Religious Fashion", "")
            .setParent(PRACTICES)
            .addConnected(FASHION)
            .build();



    public static void init() {

    }

}
