package com.objects.culture.tenet;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.objects.culture.tenet.compass.Ideology;
import com.objects.culture.tenet.compass.PoliticalCompass;
import com.objects.culture.tenet.group.TGType;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.group.groups.*;
import com.objects.culture.tenet.types.Tenet;
import com.objects.culture.tenet.tenets.ReligionTenets;

import java.util.*;

import static com.objects.culture.tenet.group.TenetGroup.builder;

public class TenetManager {
    private static final Map<String, Tenet> tenets = new HashMap<>();
    private static final Map<String, TenetGroup> groups = new HashMap<>();
    private static final Multimap<TenetGroup,Tenet> tenetsByGroup = HashMultimap.create();
    private static final Multimap<TenetGroup,TenetGroup> groupRelations = HashMultimap.create();
    private static final Multimap<TenetGroup,TenetGroup> parentRelations = HashMultimap.create();
    private static final Map<String, Ideology> ideologies = new HashMap<>();
    public static final TenetGroup CULTURE = builder(TGType.SORT_ONLY,"culture", "All_Culture", "Every Tenet");
    public static final TenetGroup HARD_CULTURE = builder(TGType.SORT_ONLY,"hard", "Hard Culture", "");
    public static final TenetGroup SOFT_CULTURE = builder(TGType.SORT_ONLY,"soft", "Soft Culture", "");

    public static void registerTenet(Tenet tenet) {
        tenets.put(tenet.getID(), tenet);
        tenetsByGroup.put(tenet.getGroup(), tenet);
    }
    public static void registerGroup(TenetGroup group) {
        String id = group.getID();
        if (groups.containsKey(id)) {
            if (groups.get(id).equals(group)) {
                return;
            } else {
                throw new RuntimeException("Duplicate group ID: " + id);
            }
        }
        groups.put(group.getID(), group);
        handleGroupRelations(group, group.connected());
        TenetGroup parent = group.parent();
        if (parent != null) {
            parentRelations.put(parent, group);
        }
    }
    public static void registerIdeology(Ideology ideology){
        if (ideologies.containsKey(ideology.getID())) {
            if (ideologies.get(ideology.getID()).equals(ideology)) {
                return;
            } else {
                throw new RuntimeException("Duplicate ideology ID: " + ideology.getID());
            }
        }
        ideologies.put(ideology.getID(), ideology);
    }
    private static void handleGroupRelations(TenetGroup group, List<TenetGroup> linkedGroups){
        for (TenetGroup linkedGroup : linkedGroups) {
            groupRelations.put(group, linkedGroup);
            groupRelations.put(linkedGroup, group);
        }
    }
    public static boolean isParentGroup(TenetGroup parent, TenetGroup child){
        Collection<TenetGroup> children = parentRelations.get(parent);
        if(children.contains(child)){
            return true;
        }
        for (TenetGroup c: children){
            if(isParentGroup(c,child)){
                return true;
            }
        }
        return false;
    }

    public static Ideology getIdeology(String id){
        return ideologies.get(id);
    }
    public static List<Ideology> getIdeologies(){
        return new ArrayList<>(ideologies.values());
    }
    public static Tenet getTenet(String id) {
        return tenets.get(id);
    }
    public static TenetGroup getGroup(String id) {
        return groups.get(id);
    }
    public static Collection<TenetGroup> getConnectedGroups(TenetGroup group){
        return groupRelations.get(group);
    }
    public static void init(){
        SocietyGroups.init();
        FamilyGroups.init();
        EducationGroups.init();
        EconomicGroups.init();
        ReligionGroups.init();
        GovernmentGroups.init();

        ReligionTenets.init();
    }
}

