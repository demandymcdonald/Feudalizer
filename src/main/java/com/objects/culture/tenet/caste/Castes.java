package com.objects.culture.tenet.caste;

import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.group.groups.GovernmentGroups;

import static com.objects.culture.tenet.group.groups.GovernmentGroups.*;

public class Castes {


    public static final CasteObject ELITE = new CasteObject(ELITE_CLASS,"elite", "Elite", "The elite class is the highest class in society, typically consisting of the wealthiest and most powerful individuals. They hold significant political and economic influence and often have access to exclusive privileges and resources.",new PoliticalCompass());
    public static final CasteObject PROFESSIONAL = new CasteObject(PROFESSIONAL_CLASS,"professional", "Professional", "The professional class consists of individuals who work in skilled and specialized occupations, often requiring education and training. They contribute to the economy through their expertise and often have a higher standard of living.",new PoliticalCompass());
    public static final CasteObject ACADEMIC = new CasteObject(ACADEMIC_CLASS,"academic", "Academic", "The academic class consists of individuals who work in research, education, and intellectual pursuits. They contribute to the advancement of knowledge and often have a strong commitment to intellectual freedom and inquiry.",new PoliticalCompass());
    public static final CasteObject ARTIST = new CasteObject(ARTIST_CLASS,"artist", "Artist", "The artist class consists of individuals who work in creative fields such as music, visual arts, literature, and performance. They contribute to the cultural and artistic life of society through their creative expression and often have a strong commitment to artistic freedom and expression.",new PoliticalCompass());
    public static final CasteObject OFFICER = new CasteObject(OFFICER_CLASS,"officer", "Officer", "The officer class consists of individuals who work in leadership and management roles, often requiring specialized training and experience. They contribute to the organization and governance of society through their expertise and often have a strong commitment to authority and order.",new PoliticalCompass());
    public static final CasteObject MERCHANT = new CasteObject(BUSINESS_CLASS,"merchant", "Merchant", "The merchant class consists of individuals who work in commerce and trade, often requiring specialized knowledge and skills. They contribute to the economic life of society through their expertise and often have a strong commitment to profit and efficiency.",new PoliticalCompass());
    public static final CasteObject MIDDLE = new CasteObject(MIDDLE_CLASS,"middle", "Middle", "The middle class consists of individuals who work in a variety of professions, often requiring specialized knowledge and skills. They contribute to the economic and social life of society through their expertise and often have a strong commitment to stability and progress.",new PoliticalCompass());
    public static final CasteObject SOLDIER = new CasteObject(SOLDIER_CLASS,"soldier", "Soldier", "The soldier class consists of individuals who work in the military, often requiring specialized training and experience. They contribute to the defense of nation-states through their expertise and often have a strong commitment to honor and victory.",new PoliticalCompass());
    public static final CasteObject WORKING = new CasteObject(WORKING_CLASS,"working", "Working", "The working class consists of individuals who work in a variety of professions, often requiring specialized knowledge and skills. They contribute to the economic and social life of society through their expertise and often have a strong commitment to stability and progress.",new PoliticalCompass());
    public static final CasteObject DISENFRANCHISED = new CasteObject(GovernmentGroups.DISENFRANCHISED,"disenfranchised", "Disenfranchised", "The disenfranchised class consists of individuals who are excluded from the political process, often due to lack of access to education, resources, or other factors. They contribute to the social life of society through their expertise and often have a strong commitment to equality and justice.",new PoliticalCompass());
    public static final CasteObject SLAVE = new CasteObject(GovernmentGroups.SLAVE,"slave", "Slave", "The slave class consists of individuals who are owned by others and are forced to work without pay. They contribute to the economic and social life of society through their labor and often have a strong commitment to freedom and justice.",new PoliticalCompass());
    public static final CasteObject OUTSIDER = new CasteObject(GovernmentGroups.OUTSIDER,"outsider", "Outsider", "The outsider class consists of individuals who are not part of the political process, often due to their cultural or religious background. They contribute to the social life of society through their expertise and often have a strong commitment to equality and justice.",new PoliticalCompass());

    public static final CasteObject[] ALL_CASTES = {ELITE,PROFESSIONAL,ACADEMIC,ARTIST,OFFICER,MERCHANT,MIDDLE,SOLDIER,WORKING,DISENFRANCHISED,SLAVE,OUTSIDER};

}
