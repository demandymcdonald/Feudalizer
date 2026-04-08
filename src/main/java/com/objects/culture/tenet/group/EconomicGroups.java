package com.objects.culture.tenet.group;

import com.Global.*;
import com.google.common.collect.ImmutableList;
import com.objects.culture.tenet.Acceptance;

import static com.objects.culture.tenet.TenetManager.HARD_CULTURE;
import static com.objects.culture.tenet.group.GovernmentGroups.*;
import static com.objects.culture.tenet.group.TenetGroup.*;

public class EconomicGroups {
    public static final TenetGroup ECONOMY = builder(HARD_CULTURE, "economy", "Economy", "", SORT_ONLY);
    public static final TenetGroup ECONOMIC_SYSTEM = builder(ECONOMY, "system", "Economic System", "", PILLAR);
    public static final TenetGroup ECONOMIC_IDEOLOGY = builder(ECONOMY, ImmutableList.of(ECONOMIC_SYSTEM), "ideology", "Economic Ideology", "", PILLAR);

    // Economy System Children
    public static final TenetGroup STATE_ENTERPRISE = builder(ECONOMIC_SYSTEM, "enterprise", "Enterprise", "", SYSTEM_LARGE);
    public static final TenetGroup PRIVATE_ENTERPRISE = builder(ECONOMIC_SYSTEM, "private_enterprise", "Private Enterprise", "", SYSTEM_LARGE);
    public static final TenetGroup ECONOMIC_MOBILITY = builder(ECONOMIC_SYSTEM, ImmutableList.of(CLASS_AND_CASTE), "mobility", "Mobility", "", SYSTEM_LARGE);
    public static final TenetGroup PROPERTY_RIGHTS = builder(ECONOMIC_SYSTEM, ImmutableList.of(STATE_ENTERPRISE, PRIVATE_ENTERPRISE), "property_rights", "Property Rights", "", SYSTEM_LARGE);
    public static final TenetGroup RESOURCE_RIGHTS = builder(ECONOMIC_SYSTEM, ImmutableList.of(STATE_ENTERPRISE, PRIVATE_ENTERPRISE), "resource_rights", "Resource Rights", "", SYSTEM_LARGE);
    public static final TenetGroup LABOR_RIGHTS = builder(ECONOMIC_SYSTEM, ImmutableList.of(STATE_ENTERPRISE, PRIVATE_ENTERPRISE, CLASS_AND_CASTE, CIVIL_LIBERTIES), "labor_rights", "Labor Rights", "", SYSTEM_LARGE);
    public static final TenetGroup LABOR_UNIONS = builder(ECONOMIC_SYSTEM, ImmutableList.of(LABOR_RIGHTS, STATE_ENTERPRISE, PRIVATE_ENTERPRISE, CLASS_AND_CASTE, CIVIL_LIBERTIES), "labor_unions", "Labor Unions", "", SYSTEM_LARGE);
    public static final TenetGroup MILITARY_INDUSTRIAL_COMPLEX = builder(ECONOMIC_SYSTEM, ImmutableList.of(MILITARY, MILITARY_PROCUREMENT, MILITARY_FUNDING, GOVERNMENT_CORRUPTION), "military_industrial_complex", "Military Industrial Complex", "", SYSTEM_LARGE);
    public static final TenetGroup FOREIGN_INVESTMENT = builder(ECONOMIC_SYSTEM, ImmutableList.of(FOREIGN_POLICY), "foreign_investment", "Foreign Investment", "", SYSTEM_LARGE);

    // Economy Value Children
    public static final TenetGroup ECONOMIC_VALUE = builder(ECONOMIC_IDEOLOGY, "value", "Economic Value", "", new AcceptanceContainer(3, Acceptance.CORE, Acceptance.CORE_FANATIC), new AcceptanceContainer(5, Acceptance.INTEGRATED));


}
