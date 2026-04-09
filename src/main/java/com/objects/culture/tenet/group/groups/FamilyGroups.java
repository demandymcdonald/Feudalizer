package com.objects.culture.tenet.group.groups;

import com.google.common.collect.ImmutableList;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.group.TGType;
import com.objects.culture.tenet.group.TenetGroup;

import static com.objects.culture.tenet.TenetManager.SOFT_CULTURE;
import static com.objects.culture.tenet.group.groups.GovernmentGroups.GOVERNMENT_ENFORCED_CONFORMITY;
import static com.objects.culture.tenet.group.groups.ReligionGroups.*;
import static com.objects.culture.tenet.group.groups.SocietyGroups.SOCIETY;
import static com.objects.culture.tenet.group.groups.SocietyGroups.SOCIETY_ENFORCED_CONFORMITY;
import static com.objects.culture.tenet.group.TenetGroup.*;

public class FamilyGroups {
//    public static final TenetGroup FAMILY = builder(SOFT_CULTURE, "family", "FamilyGroups", "", SORT_ONLY);
//    public static final TenetGroup FAMILY_STRUCTURE = builder(FAMILY, ImmutableList.of(SOCIETY, RELIGION, RELIGION_FAMILY_INTERVENTION, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY), "structure", "FamilyGroups Structure", "", PILLAR);
//    public static final TenetGroup HEAD_OF_FAMILY = builder(FAMILY_STRUCTURE, "head_of_family", "Head of FamilyGroups", "", SYSTEM_LARGE);
//    public static final TenetGroup PARENT_ROLES = builder(FAMILY_STRUCTURE, "parent_roles", "Parent Roles", "", SYSTEM_LARGE);
//    public static final TenetGroup CHILD_EXPECTATIONS = builder(FAMILY_STRUCTURE, "expectations", "Child Expectations", "", SYSTEM_LARGE);
//    public static final TenetGroup NON_CONFORMING_FAMILIES = builder(FAMILY_STRUCTURE, "non_conforming", "Non-Conforming Families", "", SYSTEM_LARGE);
//    public static final TenetGroup NON_CONFORMING_FAMILY_MEMBERS = builder(FAMILY_STRUCTURE, "non_conforming_members", "Non-Conforming FamilyGroups Members", "", SYSTEM_LARGE);
//    public static final TenetGroup INHERITANCE = builder(FAMILY_STRUCTURE, "inheritance", "Inheritance Norms", "", SYSTEM_LARGE);
//    public static final TenetGroup EXTENDED_FAMILY_ROLE = builder(FAMILY_STRUCTURE, "extended_family_role", "Extended FamilyGroups Role", "", SYSTEM_LARGE);
//    public static final TenetGroup FAMILY_CULTURE = builder(FAMILY, ImmutableList.of(SOCIETY, RELIGION, RELIGION_FAMILY_INTERVENTION, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY), "culture", "FamilyGroups Culture", "", PILLAR);
//    public static final TenetGroup PARENT_VALUE = builder(FAMILY_CULTURE, "parent_value", "Parent Value", "", new AcceptanceContainer(3, Acceptance.CORE, Acceptance.CORE_FANATIC));
//    public static final TenetGroup CHILD_VALUE = builder(FAMILY_CULTURE, "child_value", "Child Value", "", new AcceptanceContainer(3, Acceptance.CORE, Acceptance.CORE_FANATIC));
//    public static final TenetGroup EXTENDED_FAMILY_VALUE = builder(FAMILY_CULTURE, "extended_family_value", "Extended FamilyGroups Value", "", new AcceptanceContainer(3, Acceptance.CORE, Acceptance.CORE_FANATIC));
//    public static final TenetGroup FAMILY_TRADITION_GATHERING = builder(FAMILY_CULTURE, "tradition_gathering", "FamilyGroups Tradition/Gathering", "", SYSTEM_LARGE);
//    public static final TenetGroup FAMILY_TITLES = builder(FAMILY_CULTURE, "family_titles", "FamilyGroups Titles", "", SYSTEM_LARGE);

    // FAMILY
    public static final TenetGroup FAMILY = builder(TGType.SORT_ONLY, SOFT_CULTURE, "family", "FamilyGroups", "");

    // Pillars
    public static final TenetGroup FAMILY_STRUCTURE = builder(TGType.PILLAR_SYSTEM, FAMILY, ImmutableList.of(SOCIETY, RELIGION, RELIGION_FAMILY_INTERVENTION, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY), "structure", "FamilyGroups Structure", "");
    public static final TenetGroup FAMILY_CULTURE = builder(TGType.PILLAR_IDEOLOGY, FAMILY, ImmutableList.of(SOCIETY, RELIGION, RELIGION_FAMILY_INTERVENTION, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY), "culture", "FamilyGroups Culture", "");

    // Family Structure
    public static final TenetGroup HEAD_OF_FAMILY = builder(TGType.SYSTEM_LARGE, FAMILY_STRUCTURE, "head_of_family", "Head of FamilyGroups", "");
    public static final TenetGroup PARENT_ROLES = builder(TGType.SYSTEM_LARGE, FAMILY_STRUCTURE, "parent_roles", "Parent Roles", "");
    public static final TenetGroup CHILD_EXPECTATIONS = builder(TGType.SYSTEM_LARGE, FAMILY_STRUCTURE, "expectations", "Child Expectations", "");
    public static final TenetGroup NON_CONFORMING_FAMILIES = builder(TGType.SYSTEM_LARGE, FAMILY_STRUCTURE, ImmutableList.of(SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY,RELIGIOUS_CONFORMITY),"non_conforming", "Non-Conforming Families", "");
    public static final TenetGroup NON_CONFORMING_FAMILY_MEMBERS = builder(TGType.SYSTEM_LARGE, FAMILY_STRUCTURE, ImmutableList.of(SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY,RELIGIOUS_CONFORMITY), "non_conforming_members", "Non-Conforming FamilyGroups Members", "");
    public static final TenetGroup INHERITANCE = builder(TGType.SYSTEM_LARGE, FAMILY_STRUCTURE, "inheritance", "Inheritance Norms", "");
    public static final TenetGroup EXTENDED_FAMILY_ROLE = builder(TGType.SYSTEM_LARGE, FAMILY_STRUCTURE, "extended_family_role", "Extended FamilyGroups Role", "");

    // Family Culture
    public static final TenetGroup PARENT_VALUE = builder(TGType.VALUE, FAMILY_CULTURE, "parent_value", "Parent Value", "");
    public static final TenetGroup CHILD_VALUE = builder(TGType.VALUE, FAMILY_CULTURE, "child_value", "Child Value", "");
    public static final TenetGroup EXTENDED_FAMILY_VALUE = builder(TGType.VALUE, FAMILY_CULTURE, "extended_family_value", "Extended FamilyGroups Value", "");
    public static final TenetGroup FAMILY_TRADITION_GATHERING = builder(TGType.TRADITION, FAMILY_CULTURE, "tradition_gathering", "FamilyGroups Tradition/Gathering", "");
    public static final TenetGroup FAMILY_TITLES = builder(TGType.BELIEF_MAJOR, FAMILY_CULTURE, "family_titles", "FamilyGroups Titles", "");
    public static void init() {

    }

}
