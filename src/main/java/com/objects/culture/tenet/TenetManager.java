package com.objects.culture.tenet;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.tenets.ReligionTenets;

import java.util.HashMap;
import java.util.Map;

public class TenetManager {
    private static final Map<String,Tenet<?,?>> tenets = new HashMap<>();
    private static final Map<String, TenetGroup> groups = new HashMap<>();
    private static final Multimap<TenetGroup,TenetGroup> groupRelations = HashMultimap.create();
    private static final Multimap<TenetGroup,TenetGroup> parentRelations = HashMultimap.create();
    public static void registerTenet(Tenet<?,?> tenet) {
        tenets.put(tenet.getID(), tenet);
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
        handleRelations(group, group.connected());
        TenetGroup parent = group.parent();
        if (parent != null) {
            parentRelations.put(parent, group);
        }

    }
    private static void handleRelations(TenetGroup group, ImmutableList<TenetGroup> linkedGroups){
        for (TenetGroup linkedGroup : linkedGroups) {
            groupRelations.put(group, linkedGroup);
            groupRelations.put(linkedGroup, group);
        }
    }

    public static Tenet<?,?> getTenet(String id) {
        return tenets.get(id);
    }

    public static void init(){
        ReligionTenets.init();
    }
}

