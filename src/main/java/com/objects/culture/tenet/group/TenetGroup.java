package com.objects.culture.tenet.group;

import com.google.common.collect.ImmutableList;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.TenetManager;
import com.utilities.Displayable;

import java.util.ArrayList;
import java.util.List;

public record TenetGroup(TenetGroup parent, ImmutableList<TenetGroup> connected, String id, String name, String description, AcceptanceContainer... constraints) implements Displayable {
    private static final int SYSTEM_MAX = 10;
    private static final int PILLAR_MAX = 1;
    private static final int VALUE_MAX = 3;
    public static final TenetGroup.AcceptanceContainer SORT_ONLY = new TenetGroup.AcceptanceContainer(0,Acceptance.getAll());
    public static final TenetGroup.AcceptanceContainer PILLAR = new TenetGroup.AcceptanceContainer(PILLAR_MAX,Acceptance.INTEGRATED,Acceptance.CORE,Acceptance.CORE_FANATIC);
    public static final TenetGroup.AcceptanceContainer SYSTEM_LARGE = new TenetGroup.AcceptanceContainer(SYSTEM_MAX,Acceptance.INTEGRATED,Acceptance.CORE,Acceptance.CORE_FANATIC,Acceptance.INTEGRATED);
    public static final TenetGroup.AcceptanceContainer SYSTEM_SMALL = new TenetGroup.AcceptanceContainer(SYSTEM_MAX/2,Acceptance.INTEGRATED,Acceptance.CORE,Acceptance.CORE_FANATIC,Acceptance.INTEGRATED);
    public static final TenetGroup.AcceptanceContainer[] VALUE = new TenetGroup.AcceptanceContainer[]{new TenetGroup.AcceptanceContainer(VALUE_MAX,Acceptance.CORE,Acceptance.CORE_FANATIC,Acceptance.INTEGRATED),new AcceptanceContainer(VALUE_MAX * 2,Acceptance.ACCEPTED)};
    //=============================================================
    public static final TenetGroup CULTURE = builder("culture", "All_Culture", "Every Tenet", SORT_ONLY);
    public static final TenetGroup HARD_CULTURE = builder(CULTURE,"hard", "Hard Culture", "", SORT_ONLY);
    public static final TenetGroup SOFT_CULTURE = builder(CULTURE,"soft", "Soft Culture", "", SORT_ONLY);

    public TenetGroup(TenetGroup parent, ImmutableList<TenetGroup> connected, String id, String name, String description, AcceptanceContainer... constraints) {
        this.parent = parent;
        this.connected = buildList(parent, connected);
        this.id = buildID(id, parent);
        this.name = name;
        this.description = description;
        this.constraints = constraints;
        TenetManager.registerGroup(this);
    }
    private static ImmutableList<TenetGroup> buildList(TenetGroup parent, ImmutableList<TenetGroup> connected){
        List<TenetGroup> list = new ArrayList<>(connected);
        TenetGroup current = parent;
        while(current.parent() != null){
            list.addAll(current.connected());
            current = current.parent();
        }
        return ImmutableList.copyOf(list);
    }
    private static String buildID(String id, TenetGroup parent){
        if (parent == null){
            return id.toLowerCase();
        } else {
            return parent.getID() + ":" + id.toLowerCase();
        }
    }
    @Override
    public String getID() {
        return id;
    }

    @Override
    public String displayName() {
        return name;
    }

    public record AcceptanceContainer(int maxNumber, Acceptance... accept){}
    public static TenetGroup builder(String id, String name, String description, AcceptanceContainer... constraints){
        return new TenetGroup(null, ImmutableList.of(), id, name, description, constraints);
    }
    public static TenetGroup builder(TenetGroup parent, String id, String name, String description, AcceptanceContainer... constraints){
        return new TenetGroup(parent, ImmutableList.of(), id, name, description, constraints);
    }
    public static TenetGroup builder(TenetGroup parent, ImmutableList<TenetGroup> connected, String id, String name, String description, AcceptanceContainer... constraints){
        return new TenetGroup(parent, connected, id, name, description, constraints);
    }

}
