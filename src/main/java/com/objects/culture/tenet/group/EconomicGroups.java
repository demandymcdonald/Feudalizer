package com.objects.culture.tenet.group;

import com.Global.*;
import com.google.common.collect.ImmutableList;
import com.objects.culture.tenet.Acceptance;

import static com.objects.culture.tenet.group.TenetGroup.*;

public class EconomicGroups {
    public static final TenetGroup ECONOMY = builder(HARD_CULTURE, "economy", "Economy", "The overarching domain governing how a society produces, distributes, and consumes wealth, resources, and goods.", SORT_ONLY);
    public static final TenetGroup ECONOMIC_SYSTEM = builder(ECONOMY, "system", "Economic System", "The foundational framework determining how goods, services, and capital are produced, distributed, and owned.", PILLAR);
    public static final TenetGroup ECONOMIC_IDEOLOGY = builder(ECONOMY, ImmutableList.of(ECONOMIC_SYSTEM), "ideology", "Economic Ideology", "The values and beliefs shaping a society's economic priorities, from individual prosperity and free markets to collective ownership.", PILLAR);

    // Economy System Children
    public static final TenetGroup STATE_ENTERPRISE = builder(ECONOMIC_SYSTEM, "enterprise", "Enterprise", "Government-owned and operated businesses and industries, from public utilities to full nationalization of production.", SYSTEM_LARGE);
    public static final TenetGroup PRIVATE_ENTERPRISE = builder(ECONOMIC_SYSTEM, "private_enterprise", "Private Enterprise", "Privately owned businesses and industries, ranging from small commerce to large corporations operating with varying degrees of freedom.", SYSTEM_LARGE);
    public static final TenetGroup ECONOMIC_MOBILITY = builder(ECONOMIC_SYSTEM, ImmutableList.of(CLASS_AND_CASTE), "mobility", "Mobility", "The ease with which individuals and families can move between economic classes, whether upward, downward, or not at all.", SYSTEM_LARGE);
    public static final TenetGroup PROPERTY_RIGHTS = builder(ECONOMIC_SYSTEM, ImmutableList.of(STATE_ENTERPRISE, PRIVATE_ENTERPRISE), "property_rights", "Property Rights", "Laws and norms governing who may own land, assets, and goods, and the protections or limits placed on that ownership.", SYSTEM_LARGE);
    public static final TenetGroup RESOURCE_RIGHTS = builder(ECONOMIC_SYSTEM, ImmutableList.of(STATE_ENTERPRISE, PRIVATE_ENTERPRISE), "resource_rights", "Resource Rights", "Control over natural resources such as land, minerals, and water—whether they belong to the state, private owners, or communities.", SYSTEM_LARGE);
    public static final TenetGroup LABOR_RIGHTS = builder(ECONOMIC_SYSTEM, ImmutableList.of(STATE_ENTERPRISE, PRIVATE_ENTERPRISE, CLASS_AND_CASTE, CIVIL_LIBERTIES), "labor_rights", "Labor Rights", "The protections, freedoms, and obligations of workers, including wages, hours, safety, and recourse against exploitation.", SYSTEM_LARGE);
    public static final TenetGroup LABOR_UNIONS = builder(ECONOMIC_SYSTEM, ImmutableList.of(LABOR_RIGHTS, STATE_ENTERPRISE, PRIVATE_ENTERPRISE, CLASS_AND_CASTE, CIVIL_LIBERTIES), "labor_unions", "Labor Unions", "Organizations of workers that collectively negotiate wages and conditions, ranging from legally protected bodies to banned associations.", SYSTEM_LARGE);
    public static final TenetGroup MILITARY_INDUSTRIAL_COMPLEX = builder(ECONOMIC_SYSTEM, ImmutableList.of(MILITARY, MILITARY_PROCUREMENT, MILITARY_FUNDING, GOVERNMENT_CORRUPTION), "military_industrial_complex", "Military Industrial Complex", "The entangled relationship between military institutions and private defense industries, shaping procurement, policy, and public spending.", SYSTEM_LARGE);
    public static final TenetGroup FOREIGN_INVESTMENT = builder(ECONOMIC_SYSTEM, ImmutableList.of(FOREIGN_POLICY), "foreign_investment", "Foreign Investment", "The extent to which foreign capital may enter the domestic economy, from open markets to strict protectionist restrictions.", SYSTEM_LARGE);

    // Economy Value Children
    public static final TenetGroup ECONOMIC_VALUE = builder(ECONOMIC_IDEOLOGY, "value", "Economic Value", "The traits a society admires or condemns in economic actors—industrious, exploitative, generous, ambitious, or ruthless.", new AcceptanceContainer(3, Acceptance.CORE, Acceptance.CORE_FANATIC), new AcceptanceContainer(5, Acceptance.INTEGRATED));


}
