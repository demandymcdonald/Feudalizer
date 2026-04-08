package com.objects.culture.tenet.group;

import com.Global.*;
import com.google.common.collect.ImmutableList;
import com.objects.culture.tenet.Acceptance;

import static com.objects.culture.tenet.group.GovernmentGroups.GOVERNMENT_ENFORCED_CONFORMITY;
import static com.objects.culture.tenet.group.ReligionGroups.RELIGION;
import static com.objects.culture.tenet.group.ReligionGroups.RELIGION_FAMILY_INTERVENTION;
import static com.objects.culture.tenet.group.SocietyGroups.SOCIETY;
import static com.objects.culture.tenet.group.SocietyGroups.SOCIETY_ENFORCED_CONFORMITY;
import static com.objects.culture.tenet.group.TenetGroup.*;

public class Family {
    public static final TenetGroup FAMILY = builder(SOFT_CULTURE, "family", "Family", "", SORT_ONLY);
    public static final TenetGroup FAMILY_STRUCTURE = builder(FAMILY, ImmutableList.of(SOCIETY, RELIGION, RELIGION_FAMILY_INTERVENTION, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY), "structure", "Family Structure", "", PILLAR);
    public static final TenetGroup HEAD_OF_FAMILY = builder(FAMILY_STRUCTURE, "head_of_family", "Head of Family", "", SYSTEM_LARGE);
    public static final TenetGroup PARENT_ROLES = builder(FAMILY_STRUCTURE, "parent_roles", "Parent Roles", "", SYSTEM_LARGE);
    public static final TenetGroup CHILD_EXPECTATIONS = builder(FAMILY_STRUCTURE, "expectations", "Child Expectations", "", SYSTEM_LARGE);
    public static final TenetGroup NON_CONFORMING_FAMILIES = builder(FAMILY_STRUCTURE, "non_conforming", "Non-Conforming Families", "", SYSTEM_LARGE);
    public static final TenetGroup NON_CONFORMING_FAMILY_MEMBERS = builder(FAMILY_STRUCTURE, "non_conforming_members", "Non-Conforming Family Members", "", SYSTEM_LARGE);
    public static final TenetGroup INHERITANCE = builder(FAMILY_STRUCTURE, "inheritance", "Inheritance Norms", "", SYSTEM_LARGE);
    public static final TenetGroup EXTENDED_FAMILY_ROLE = builder(FAMILY_STRUCTURE, "extended_family_role", "Extended Family Role", "", SYSTEM_LARGE);
    public static final TenetGroup FAMILY_CULTURE = builder(FAMILY, ImmutableList.of(SOCIETY, RELIGION, RELIGION_FAMILY_INTERVENTION, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY), "culture", "Family Culture", "", PILLAR);
    public static final TenetGroup PARENT_VALUE = builder(FAMILY_CULTURE, "parent_value", "Parent Value", "", new AcceptanceContainer(3, Acceptance.CORE, Acceptance.CORE_FANATIC));
    public static final TenetGroup CHILD_VALUE = builder(FAMILY_CULTURE, "child_value", "Child Value", "", new AcceptanceContainer(3, Acceptance.CORE, Acceptance.CORE_FANATIC));
    public static final TenetGroup EXTENDED_FAMILY_VALUE = builder(FAMILY_CULTURE, "extended_family_value", "Extended Family Value", "", new AcceptanceContainer(3, Acceptance.CORE, Acceptance.CORE_FANATIC));
    public static final TenetGroup FAMILY_TRADITION_GATHERING = builder(FAMILY_CULTURE, "tradition_gathering", "Family Tradition/Gathering", "", SYSTEM_LARGE);
    public static final TenetGroup FAMILY_TITLES = builder(FAMILY_CULTURE, "family_titles", "Family Titles", "", SYSTEM_LARGE);
}
