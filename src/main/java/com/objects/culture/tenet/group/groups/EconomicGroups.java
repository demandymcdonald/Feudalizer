package com.objects.culture.tenet.group.groups;

import com.objects.culture.tenet.group.TGType;
import com.objects.culture.tenet.group.TenetGroup;

import static com.objects.culture.TenetManager.HARD_CULTURE;
import static com.objects.culture.tenet.group.groups.GovernmentGroups.*;
import static com.objects.culture.tenet.group.TenetGroup.*;
import static com.objects.culture.tenet.group.groups.MilitaryGroups.MILITARY;
import static com.objects.culture.tenet.group.groups.MilitaryGroups.MILITARY_PROCUREMENT;

public class EconomicGroups {
    public static final TenetGroup ECONOMY = new TenetGroup.Builder(TGType.SORT_ONLY, Level.PILLAR, "economy", "Economy", "")
            .setParent(HARD_CULTURE)
            .build();

    // Pillar
    public static final TenetGroup ECONOMIC_SYSTEM = new TenetGroup.Builder(TGType.PILLAR_SYSTEM, Level.CATEGORY, "system", "Economic System", "")
            .setParent(ECONOMY)
            .build();
    public static final TenetGroup ECONOMIC_IDEOLOGY = new TenetGroup.Builder(TGType.PILLAR_IDEOLOGY, Level.CATEGORY, "ideology", "Economic Ideology", "")
            .setParent(ECONOMY)
            .addConnected(ECONOMIC_SYSTEM)
            .build();

    // Economic System
    public static final TenetGroup STATE_ENTERPRISE = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "enterprise", "Enterprise", "")
            .setParent(ECONOMIC_SYSTEM)
            .build();
    public static final TenetGroup PRIVATE_ENTERPRISE = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "private_enterprise", "Private Enterprise", "")
            .setParent(ECONOMIC_SYSTEM)
            .build();
    public static final TenetGroup ECONOMIC_MOBILITY = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "mobility", "Mobility", "")
            .setParent(ECONOMIC_SYSTEM)
            .addConnected(CLASS_AND_CASTE)
            .build();
    public static final TenetGroup PROPERTY_RIGHTS = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "property_rights", "Property Rights", "")
            .setParent(ECONOMIC_SYSTEM)
            .addConnected(STATE_ENTERPRISE, PRIVATE_ENTERPRISE)
            .build();
    public static final TenetGroup RESOURCE_RIGHTS = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "resource_rights", "PRODUCT Rights", "")
            .setParent(ECONOMIC_SYSTEM)
            .addConnected(STATE_ENTERPRISE, PRIVATE_ENTERPRISE)
            .build();
    public static final TenetGroup MILITARY_INDUSTRIAL_COMPLEX = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "military_industrial_complex", "Military Industrial Complex", "")
            .setParent(ECONOMIC_SYSTEM)
            .addConnected(MILITARY, MILITARY_PROCUREMENT, MILITARY_FUNDING, GOVERNMENT_CORRUPTION)
            .build();
    public static final TenetGroup FOREIGN_INVESTMENT = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "foreign_investment", "Foreign Investment", "")
            .setParent(ECONOMIC_SYSTEM)
            .addConnected(FOREIGN_POLICY)
            .build();


    public static final TenetGroup LABOR_UNION = new TenetGroup.Builder(TGType.PILLAR_IDEOLOGY, Level.CATEGORY, "labor", "Labor", "")
            .setParent(ECONOMIC_SYSTEM)
            .addConnected(CLASS_AND_CASTE, WORKING_CLASS, CIVIL_LIBERTIES)
            .build();
    public static final TenetGroup UNION_CORRUPTION = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "corruption", "Union Corruption", "")
            .setParent(LABOR_UNION)
            .build();
    public static final TenetGroup UNION_INCOME = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "income", "Union Income", "")
            .setParent(LABOR_UNION)
            .build();
    public static final TenetGroup UNION_AUTHORITY = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "authority", "Union Authority", "")
            .setParent(LABOR_UNION)
            .addConnected(LABOR_RIGHTS)
            .build();
    public static final TenetGroup UNION_GOVERNMENT_INFLUENCE =  new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "government_influence", "Union's Influence on Government", "")
            .setParent(LABOR_UNION)
            .addConnected(UNION_AUTHORITY,GOVERNMENT_CORRUPTION)
            .build();
    public static final TenetGroup UNION_LEADERSHIP = new TenetGroup.Builder(TGType.SYSTEM_SORT, Level.SUBCATEGORY, "leadership", "Leadership", "")
            .setParent(LABOR_UNION)
            .addConnected(CLASS_AND_CASTE)
            .build();
    public static final TenetGroup UNION_LEADER_CORRUPTION = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "corruption", "Leader Corruption", "")
            .setParent(UNION_LEADERSHIP)
            .addConnected(UNION_CORRUPTION)
            .build();
    public static final TenetGroup UNION_LEADER_TYPES = new TenetGroup.Builder(TGType.SYSTEM_SORT, Level.NORMAL, "types", "Leader Types", "")
            .setParent(UNION_LEADERSHIP)
            .build();
    public static final TenetGroup UNION_LEADER_TREATMENT = new TenetGroup.Builder(TGType.SYSTEM_SORT, Level.NORMAL, "types", "Leader Types", "")
            .setParent(UNION_LEADERSHIP)
            .build();
    public static final TenetGroup UNION_LEADER_SELECTION = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "selection", "Leadership Selection", "")
            .setParent(LEADER_TYPES)
            .addConnected(DISENFRANCHISED)
            .build();
    public static final TenetGroup UNION_LEADER_AUTHORITY = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "authority", "Leader Authority", "")
            .setParent(LEADER_TYPES)
            .build();
    public static final TenetGroup UNION_LEADER_REMOVAL = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "removal", "Leadership Removal", "")
            .setParent(LEADER_TYPES)
            .addConnected(UNION_CORRUPTION, LEADER_CORRUPTION)
            .build();
    public static final TenetGroup UNION_LEADER_GENERAL = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "general", "Leader General", "")
            .setParent(LEADER_TYPES)
            .build();


    public static final TenetGroup BUSINESS = new TenetGroup.Builder(TGType.PILLAR_IDEOLOGY, Level.CATEGORY, "business", "Business", "")
            .setParent(ECONOMIC_SYSTEM)
            .addConnected(PRIVATE_ENTERPRISE,STATE_ENTERPRISE)
            .build();
    public static final TenetGroup BUSINESS_CORRUPTION = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "corruption", "Business Corruption", "")
            .setParent(BUSINESS)
            .build();
    public static final TenetGroup BUSINESS_INCOME = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "income", "Business Income", "")
            .setParent(BUSINESS)
            .build();
    public static final TenetGroup BUSINESS_RIGHTS =  new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "corruption", "Business Rights", "")
            .setParent(BUSINESS)
            .addConnected(PROPERTY_RIGHTS,RESOURCE_RIGHTS)
            .build();
    public static final TenetGroup UNION_INFLUENCE =  new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "union_influence", "Business Rights", "")
            .setParent(BUSINESS)
            .addConnected(BUSINESS_RIGHTS,UNION_AUTHORITY)
            .build();
    public static final TenetGroup GOVERNMENT_INFLUENCE =  new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "government_influence", "Business' Influence on Government", "")
            .setParent(BUSINESS)
            .addConnected(BUSINESS_RIGHTS,MILITARY_INDUSTRIAL_COMPLEX,GOVERNMENT_CORRUPTION)
            .build();
    public static final TenetGroup BUSINESS_LEADERSHIP = new TenetGroup.Builder(TGType.SYSTEM_SORT, Level.SUBCATEGORY, "leadership", "Business Leadership", "")
            .setParent(BUSINESS)
            .addConnected(BUSINESS, BUSINESS_CLASS)
            .build();
    public static final TenetGroup BUSINESS_LEADER_CORRUPTION = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "corruption", "Leader Corruption", "")
            .setParent(BUSINESS_LEADERSHIP)
            .addConnected(UNION_CORRUPTION)
            .build();
    public static final TenetGroup BUSINESS_LEADER_TYPES = new TenetGroup.Builder(TGType.SYSTEM_SORT, Level.NORMAL, "types", "Leader Types", "")
            .setParent(BUSINESS_LEADERSHIP)
            .build();
    public static final TenetGroup BUSINESS_LEADER_SELECTION = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "selection", "Leadership Selection", "")
            .setParent(BUSINESS_LEADERSHIP)
            .addConnected(DISENFRANCHISED)
            .build();
    public static final TenetGroup BUSINESS_LEADER_AUTHORITY = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "authority", "Leader Authority", "")
            .setParent(BUSINESS_LEADERSHIP)
            .build();
    public static final TenetGroup BUSINESS_LEADER_REMOVAL = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "removal", "Leadership Removal", "")
            .setParent(BUSINESS_LEADERSHIP)
            .addConnected(BUSINESS_CORRUPTION, BUSINESS_RIGHTS)
            .build();
    public static final TenetGroup BUSINESS_LEADER_TREATMENT = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "general", "Leader Treatment", "")
            .setParent(BUSINESS_LEADERSHIP)
            .build();
    public static final TenetGroup BUSINESS_LEADER_GENERAL = new TenetGroup.Builder(TGType.SYSTEM_LARGE, Level.NORMAL, "general", "Leader General", "")
            .setParent(BUSINESS_LEADERSHIP)
            .build();



    // Economic Ideology
    public static final TenetGroup VIEW_OF_COMMERCE = new TenetGroup.Builder(TGType.BELIEF_MAJOR, Level.NORMAL, "policy", "Perception on Economies", "")
            .setParent(ECONOMIC_IDEOLOGY)
            .addConnected(BUSINESS_CLASS)
            .build();
    public static final TenetGroup TRADE_ETHICS = new TenetGroup.Builder(TGType.BELIEF_MAJOR, Level.NORMAL, "trade_ethics", "Trade Ethics", "")
            .setParent(ECONOMIC_IDEOLOGY)
            .build();
    public static final TenetGroup ECONOMIC_VALUE = new TenetGroup.Builder(TGType.VALUE, Level.NORMAL, "value", "Economic Value", "")
            .setParent(ECONOMIC_IDEOLOGY)
            .build();

    public static void init() {

    }
}
