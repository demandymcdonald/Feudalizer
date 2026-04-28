package com.objects.culture.object.ideology;

import com.base.instanced.InstanceType;
import com.objects.culture.object.compass.PoliticalCompass;

public class Ideologies {
    // ============================================================
// IDEOLOGIES
// Axes reference:
//   INDIVIDUAL_COLLECTIVE : negative = Collectivist, positive = Individualist
//   UNIVERSAL_PARTICULAR  : negative = Universalist,  positive = Particularist
//   TRUST_IN_OPACITY      : negative = Low Trust,      positive = High Trust
//   EGALITARIAN_HIERARCHY : negative = Egalitarian,    positive = Hierarchical
// Constructor: (individual, universal, trust, hierarchy)
// ============================================================

// --- MONARCHIES ---

    public static final Ideology ABSOLUTE_MONARCHY = new Ideology(InstanceType.HARDCODED,
            "absolute_monarchy",
            "Absolute Monarchy",
            "Governance by a single hereditary ruler with unchecked authority, legitimized through divine right or tradition.",
            new PoliticalCompass(-200, 200, 350, 450)
    );

    public static final Ideology CONSTITUTIONAL_MONARCHY = new Ideology(InstanceType.HARDCODED,
            "constitutional_monarchy",
            "Constitutional Monarchy",
            "Hereditary rule constrained by a legal framework that guarantees individual rights and distributes political power.",
            new PoliticalCompass(150, 100, 200, 250)
    );

// --- ELITE GOVERNANCE ---

    public static final Ideology ARISTOCRACY = new Ideology(InstanceType.HARDCODED,
            "aristocracy",
            "Aristocracy / Oligarchy",
            "Political power concentrated in a hereditary or privileged class, with governance legitimized through birth, wealth, or status.",
            new PoliticalCompass(-100, 200, 300, 450)
    );

    public static final Ideology FEUDALISM = new Ideology(InstanceType.HARDCODED,
            "feudalism",
            "Feudalism",
            "A hierarchical system of land-based obligations and loyalties, with political authority distributed among lords under a sovereign.",
            new PoliticalCompass(-250, 200, 167, 500)
    );
    public static final Ideology NEOFEUDALISM = new Ideology(InstanceType.HARDCODED,
            "neofeudalism",
            "Neofeudalism",
            "A stratified order in which platform owners and capital holders extract rents from a dependent class bound by contractual obligation rather than legal title.",
            new PoliticalCompass(-100, 300, 450, 450)
    );
// --- THEOCRACY ---

    // Placed low on universal/particular because expansionist theocracies hold a universal mandate to bring all humanity under divine law.
    public static final Ideology UNIVERSALIST_THEOCRACY = new Ideology(InstanceType.HARDCODED,
            "universalist_theocracy",
            "Universalist Theocracy",
            "Divine law governs all political life, with an active mandate to extend that law to all of humanity.",
            new PoliticalCompass(-300, -400, 450, 400)
    );

    public static final Ideology PARTICULARIST_THEOCRACY = new Ideology(InstanceType.HARDCODED,
            "particularist_theocracy",
            "Particularist Theocracy",
            "Divine law governs political life exclusively for a chosen people, without a universal mandate to expand it beyond them.",
            new PoliticalCompass(-300, 300, 450, 400)
    );

// --- NATIONALISM ---

    public static final Ideology RELIGIOUS_NATIONALISM = new Ideology(InstanceType.HARDCODED,
            "religious_nationalism",
            "Religious Nationalism",
            "Shared religious identity as the primary basis for national cohesion and political legitimacy, distinct from formal theocratic rule.",
            new PoliticalCompass(-200, 400, 200, 250)
    );

    public static final Ideology CIVIC_NATIONALISM = new Ideology(InstanceType.HARDCODED,
            "civic_nationalism",
            "Civic Nationalism",
            "National identity grounded in shared political values and legal membership rather than ethnicity or religion.",
            new PoliticalCompass(100, 150, 200, 100)
    );

    public static final Ideology ETHNONATIONALISM = new Ideology(InstanceType.HARDCODED,
            "ethnonationalism",
            "Ethnonationalism",
            "Political legitimacy derived from ethnic or cultural homogeneity, with the ethnic group as the primary unit of political organization.",
            new PoliticalCompass(300, 450, -150, 200)
    );

// --- AUTHORITARIAN / EXPANSIONIST ---

    public static final Ideology FASCISM = new Ideology(InstanceType.HARDCODED,
            "fascism",
            "Fascism",
            "The nation-state as supreme collective organism, demanding total loyalty, led by an authoritarian vanguard, and defined against internal and external enemies.",
            new PoliticalCompass(500, 400, 350, 500)
    );

    public static final Ideology COLONIALISM = new Ideology(InstanceType.HARDCODED,
            "colonialism",
            "Colonialism / Imperialism",
            "Political and economic domination of foreign peoples, justified through civilizational hierarchy and the expansion of the colonizing state's power.",
            new PoliticalCompass(-150, 400, 300, 450)
    );

    public static final Ideology MILITARISM = new Ideology(InstanceType.HARDCODED,
            "militarism",
            "Militarism",
            "Military strength as the organizing principle of the state, with political life structured around martial values and national power projection.",
            new PoliticalCompass(-300, 300, 300, 450)
    );

// --- LIBERAL / PROGRESSIVE ---

    public static final Ideology LIBERALISM = new Ideology(
            InstanceType.HARDCODED,
            "liberalism",
            "Liberalism",
            "Individual rights and freedoms as the foundational basis of political legitimacy, protected by constitutional institutions and rule of law.",
            new PoliticalCompass(-400, -300, 150, -150)
    );

    public static final Ideology SOCIAL_DEMOCRACY = new Ideology(
            InstanceType.HARDCODED,
            "social_democracy",
            "Social Democracy",
            "Individual rights preserved within a democratic framework that uses state intervention to guarantee the material conditions for meaningful freedom.",
            new PoliticalCompass(200, -200, 250, -200)
    );

    public static final Ideology NEOLIBERALISM = new Ideology(
            InstanceType.HARDCODED,
            "neoliberalism",
            "Neoliberalism",
            "Free markets and global capital as optimal allocators of resources, with state institutions restructured to protect and expand market competition.",
            new PoliticalCompass(-250, -200, 350, 150)
    );

// --- SOCIALISM / COMMUNISM ---

    // Trust in opacity is positive here: the party-state is opaque by design and citizens are expected to defer to it.
    public static final Ideology AUTHORITARIAN_COMMUNISM = new Ideology(
            InstanceType.HARDCODED,
            "authoritarian_communism",
            "Authoritarian Communism",
            "Collective ownership of production enforced by a vanguard party-state, justified as a transitional phase toward a stateless, classless society.",
            new PoliticalCompass(450, -420, 350, 400)
    );

    public static final Ideology DEMOCRATIC_SOCIALISM = new Ideology(
            InstanceType.HARDCODED,
            "democratic_socialism",
            "Democratic Socialism",
            "Collective or worker ownership of production pursued through democratic electoral means rather than revolutionary seizure of the state.",
            new PoliticalCompass(250, -250, 100, -300)
    );

// --- ANARCHISM ---

    public static final Ideology ANARCHO_COMMUNISM = new Ideology(
            InstanceType.HARDCODED,
            "anarcho_communism",
            "Anarcho-Communism",
            "Abolition of both the state and private property in favor of voluntary communes and collective ownership without hierarchical authority.",
            new PoliticalCompass(300, -400, -450, -500)
    );

    public static final Ideology INDIVIDUALIST_ANARCHISM = new Ideology(InstanceType.HARDCODED,
            "individualist_anarchism",
            "Individualist Anarchism",
            "Abolition of the state and all coercive institutions in defense of absolute individual sovereignty and voluntary association.",
            new PoliticalCompass(450, -200, -450, -500)
    );

// --- CONSERVATISM ---

    public static final Ideology TRADITIONAL_CONSERVATISM = new Ideology(InstanceType.HARDCODED,
            "traditional_conservatism",
            "Traditional Conservatism",
            "Preservation of established institutions, customs, and social hierarchies as repositories of accumulated wisdom resistant to radical change.",
            new PoliticalCompass(200, -100, 400, 250)
    );

    // Trust is negative here: reactionaries distrust current modern institutions specifically, viewing them as corrupt usurpers of the legitimate old order.
    public static final Ideology REACTIONARY_CONSERVATISM = new Ideology(InstanceType.HARDCODED,
            "reactionary_conservatism",
            "Reactionary Conservatism",
            "Rejection of modern institutions as corrupt departures from a superior prior order, with an active drive to dismantle and restore.",
            new PoliticalCompass(350, -200, -200, 250)
    );

    public static final Ideology NEOCONSERVATISM = new Ideology(InstanceType.HARDCODED,
            "neoconservatism",
            "Neoconservatism",
            "Liberal democratic values enforced through strong state and military power, with a universalist mandate to actively reshape the international order.",
            new PoliticalCompass( -100, 150,350, 200)
    );

// --- POPULISM ---

    // Both populisms are low trust: populism's defining feature is distrust of opaque elite institutions.
// They differ on universal/particular: right populism targets cosmopolitan elites on behalf of a cultural "people"; left populism targets capital on behalf of an economic "people".
    public static final Ideology RIGHT_POPULISM = new Ideology(InstanceType.HARDCODED,
            "right_populism",
            "Right Populism",
            "Frames politics as a conflict between a virtuous cultural or ethnic 'people' and corrupt cosmopolitan elites, demanding nativist restoration.",
            new PoliticalCompass(300, 150, -400, -100)
    );

    public static final Ideology LEFT_POPULISM = new Ideology(InstanceType.HARDCODED,
            "left_populism",
            "Left Populism",
            "Frames politics as a conflict between a virtuous working 'people' and a corrupt capitalist elite, demanding redistribution and popular sovereignty.",
            new PoliticalCompass( 3,-150, -400, -350)
    );

// --- LIBERTARIANISM ---

    // Mildly hierarchical: libertarianism is indifferent to market-produced hierarchy, just opposed to state-coerced hierarchy.
    public static final Ideology LIBERTARIANISM = new Ideology(InstanceType.HARDCODED,
            "libertarianism",
            "Libertarianism",
            "Maximal individual freedom from state coercion, with government limited strictly to the protection of person and property.",
            new PoliticalCompass( -200,450, -200, 100)
    );

// --- TECHNOCRACY ---

    public static final Ideology TECHNOCRACY = new Ideology(InstanceType.HARDCODED,
            "technocracy",
            "Technocracy",
            "Governance by credentialed experts and technical systems, with political decisions subordinated to scientific and managerial rationality.",
            new PoliticalCompass( -200, -100,450, 300)
    );

// --- ECONOMIC DOCTRINES ---

    public static final Ideology MERCANTILISM = new Ideology(InstanceType.HARDCODED,
            "mercantilism",
            "Mercantilism",
            "National wealth maximized through state-managed trade, with economic policy subordinated to zero-sum competition between rival nation-states.",
            new PoliticalCompass(-200, 400, 300, 200)
    );

// --- PRE-STATE ---

    public static final Ideology TRIBALISM = new Ideology(InstanceType.HARDCODED,
            "tribalism",
            "Tribalism / Chieftaincy",
            "Political organization around kinship and tribal membership, with authority vested in hereditary or earned chieftaincy over competing formal institutions.",
            new PoliticalCompass(450,250,  150, 250)
    );
}
