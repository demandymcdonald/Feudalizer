package com.objects.culture.tenet.group.groups;

import com.objects.culture.tenet.group.TGType;
import com.objects.culture.tenet.group.TenetGroup;

import static com.objects.culture.TenetManager.SOFT_CULTURE;
import static com.objects.culture.tenet.group.groups.GovernmentGroups.GOVERNMENT_ENFORCED_CONFORMITY;
import static com.objects.culture.tenet.group.groups.ReligionGroups.*;
import static com.objects.culture.tenet.group.groups.SocietyGroups.SOCIETY;
import static com.objects.culture.tenet.group.groups.SocietyGroups.SOCIETY_ENFORCED_CONFORMITY;
import static com.objects.culture.tenet.group.TenetGroup.*;

public class FamilyGroups {

    public static final TenetGroup FAMILY = new TenetGroup.Builder(TGType.SORT_ONLY, Level.PILLAR, "family", "FamilyGroups", "")
            .setParent(SOFT_CULTURE)
            .build();

    // Pillar
    public static final TenetGroup FAMILY_STRUCTURE = new TenetGroup.Builder(TGType.PILLAR_SYSTEM, Level.CATEGORY, "structure", "FamilyGroups Structure", "")
            .setParent(FAMILY)
            .addConnected(SOCIETY, RELIGION, RELIGION_FAMILY_INTERVENTION, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY)
            .build();
    public static final TenetGroup FAMILY_CULTURE = new TenetGroup.Builder(TGType.PILLAR_IDEOLOGY, Level.CATEGORY, "culture", "FamilyGroups Culture", "")
            .setParent(FAMILY)
            .addConnected(SOCIETY, RELIGION, RELIGION_FAMILY_INTERVENTION, SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY)
            .build();

    // Family Structure
    public static final TenetGroup HEAD_OF_FAMILY = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "head_of_family", "Head of FamilyGroups", "")
            .setParent(FAMILY_STRUCTURE)
            .build();
    public static final TenetGroup PARENT_ROLES = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "parent_roles", "Parent Roles", "")
            .setParent(FAMILY_STRUCTURE)
            .build();
    public static final TenetGroup CHILD_EXPECTATIONS = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "expectations", "Child Expectations", "")
            .setParent(FAMILY_STRUCTURE)
            .build();
    public static final TenetGroup NON_CONFORMING_FAMILIES = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "non_conforming", "Non-Conforming Families", "")
            .setParent(FAMILY_STRUCTURE)
            .addConnected(SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY, RELIGIOUS_CONFORMITY)
            .build();
    public static final TenetGroup NON_CONFORMING_FAMILY_MEMBERS = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "non_conforming_members", "Non-Conforming FamilyGroups Members", "")
            .setParent(FAMILY_STRUCTURE)
            .addConnected(SOCIETY_ENFORCED_CONFORMITY, GOVERNMENT_ENFORCED_CONFORMITY, RELIGIOUS_CONFORMITY)
            .build();
    public static final TenetGroup INHERITANCE = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "inheritance", "Inheritance Norms", "")
            .setParent(FAMILY_STRUCTURE)
            .build();
    public static final TenetGroup EXTENDED_FAMILY_ROLE = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "extended_family_role", "Extended FamilyGroups Role", "")
            .setParent(FAMILY_STRUCTURE)
            .build();

    // Family Culture
    public static final TenetGroup PARENT_VALUE = new TenetGroup.Builder(TGType.VALUE, Level.NORMAL, "parent_value", "Parent Value", "")
            .setParent(FAMILY_CULTURE)
            .build();
    public static final TenetGroup CHILD_VALUE = new TenetGroup.Builder(TGType.VALUE, Level.NORMAL, "child_value", "Child Value", "")
            .setParent(FAMILY_CULTURE)
            .build();
    public static final TenetGroup EXTENDED_FAMILY_VALUE = new TenetGroup.Builder(TGType.VALUE, Level.NORMAL, "extended_family_value", "Extended FamilyGroups Value", "")
            .setParent(FAMILY_CULTURE)
            .build();
    public static final TenetGroup FAMILY_TRADITION_GATHERING = new TenetGroup.Builder(TGType.TRADITION, Level.NORMAL, "tradition_gathering", "FamilyGroups Tradition/Gathering", "")
            .setParent(FAMILY_CULTURE)
            .build();
    public static final TenetGroup FAMILY_TITLES = new TenetGroup.Builder(TGType.BELIEF_MAJOR, Level.NORMAL, "family_titles", "FamilyGroups Titles", "")
            .setParent(FAMILY_CULTURE)
            .build();


    public static void init() {

    }

}
