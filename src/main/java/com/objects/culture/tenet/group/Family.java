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
    public static final TenetGroup FAMILY = builder(SOFT_CULTURE, "family", "Family", "The fundamental social unit of kin relations, encompassing how households are structured, governed, and integrated into society.", SORT_ONLY);
    public static final TenetGroup FAMILY_STRUCTURE = builder(FAMILY, ImmutableList.of(SOCIETY, RELIGION, RELIGION_FAMILY_INTERVENTION, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY), "structure", "Family Structure", "The roles, hierarchies, and norms defining how a family is organized—who leads, who inherits, and who belongs.", PILLAR);
    public static final TenetGroup HEAD_OF_FAMILY = builder(FAMILY_STRUCTURE, "head_of_family", "Head of Family", "Who holds authority within the family unit, and the powers, duties, and expectations that come with that role.", SYSTEM_LARGE);
    public static final TenetGroup PARENT_ROLES = builder(FAMILY_STRUCTURE, "parent_roles", "Parent Roles", "The duties, expectations, and rights of mothers and fathers, including labor, discipline, and the raising of children.", SYSTEM_LARGE);
    public static final TenetGroup CHILD_EXPECTATIONS = builder(FAMILY_STRUCTURE, "expectations", "Child Expectations", "What society and family demand of children—obedience, labor, education, piety—and what children are owed in return.", SYSTEM_LARGE);
    public static final TenetGroup NON_CONFORMING_FAMILIES = builder(FAMILY_STRUCTURE, "non_conforming", "Non-Conforming Families", "Households that deviate from the dominant model—single-parent, childless, same-sex—and how society and law regard them.", SYSTEM_LARGE);
    public static final TenetGroup NON_CONFORMING_FAMILY_MEMBERS = builder(FAMILY_STRUCTURE, "non_conforming_members", "Non-Conforming Family Members", "Individuals within families who break expected roles—in gender, sexuality, faith, or vocation—and the consequences they face.", SYSTEM_LARGE);
    public static final TenetGroup INHERITANCE = builder(FAMILY_STRUCTURE, "inheritance", "Inheritance Norms", "The norms and laws governing how wealth, land, titles, and obligations pass from one generation to the next.", SYSTEM_LARGE);
    public static final TenetGroup EXTENDED_FAMILY_ROLE = builder(FAMILY_STRUCTURE, "extended_family_role", "Extended Family Role", "The place of grandparents, aunts, uncles, and cousins in family life, from distant relations to co-resident authorities.", SYSTEM_LARGE);
    public static final TenetGroup FAMILY_CULTURE = builder(FAMILY, ImmutableList.of(SOCIETY, RELIGION, RELIGION_FAMILY_INTERVENTION, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY), "culture", "Family Culture", "The values, customs, and traditions defining how a family relates internally and presents itself to the world.", PILLAR);
    public static final TenetGroup PARENT_VALUE = builder(FAMILY_CULTURE, "parent_value", "Parent Value", "The traits a society admires or condemns in parents—nurturing, strict, present, absent, self-sacrificing, or overbearing.", new AcceptanceContainer(3, Acceptance.CORE, Acceptance.CORE_FANATIC));
    public static final TenetGroup CHILD_VALUE = builder(FAMILY_CULTURE, "child_value", "Child Value", "The traits a society admires or condemns in children—obedient, curious, pious, independent, dutiful, or willful.", new AcceptanceContainer(3, Acceptance.CORE, Acceptance.CORE_FANATIC));
    public static final TenetGroup EXTENDED_FAMILY_VALUE = builder(FAMILY_CULTURE, "extended_family_value", "Extended Family Value", "The traits a society admires or condemns in extended kin—supportive, meddling, distant, protective, or burdensome.", new AcceptanceContainer(3, Acceptance.CORE, Acceptance.CORE_FANATIC));
    public static final TenetGroup FAMILY_TRADITION_GATHERING = builder(FAMILY_CULTURE, "tradition_gathering", "Family Tradition/Gathering", "The recurring customs, ceremonies, and shared activities that reinforce family bonds and transmit identity across generations.", SYSTEM_LARGE);
    public static final TenetGroup FAMILY_TITLES = builder(FAMILY_CULTURE, "family_titles", "Family Titles", "The terms of address and honorifics used within families, reflecting hierarchy, affection, and cultural identity.", SYSTEM_LARGE);
}
