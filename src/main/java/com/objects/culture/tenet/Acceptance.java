package com.objects.culture.tenet;

import com.utilities.IDisplayable;

import java.util.TreeMap;

public enum Acceptance implements IDisplayable {
    CORE_FANATIC("tp_extreme_core", "Core Fanatic", "This tenet is a core part of the culture and is fervently followed.", 448),
    CORE("tp_core", "Core", "This tenet is a core part of the culture", 320),
    INTEGRATED("tp_important", "Integrated", "This tenet is a part of the culture.", 192),
    ACCEPTED("tp_accepted", "Accepted", "This tenet is accepted by the culture.", 128),
    TOLERATED("tp_lukewarm", "Lukewarm", "This tenet is lukewarm to the culture.", 64),
    NEUTRAL("tp_neutral", "Neutral", "This tenet is neutral to the culture.", 0),
    BARELY_TOLERATED("tp_tepid", "Tepid", "This tenet is tepid to the culture.", -64),
    REJECTED("tp_rejected", "Rejected", "This tenet is disliked by the culture.", -128),
    SHUNNED("tp_shunned", "Shunned", "This tenet is shunned by the culture.", -192),
    PERSECUTED("tp_persecuted", "Persecuted", "This tenet is persecuted or banned by the culture.", -320),
    FANATICAL_PERSECUTION("tp_extreme_persecution", "Fanatical Persecution", "This tenet is severely persecuted or banned by the culture. Risk of using methods like" +
            " genocide or extreme violence to eliminate those who follow this tenet :(", -448),
    ;

    private static final TreeMap<Integer, Acceptance> floorMap = new TreeMap<>();
    private final String id;
    private final String name;
    private final String description;
    private final int floor;
    public static final int MAX_VALUE = 512;
    Acceptance(String id, String name, String description, int floor) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.floor = floor;
    }

    static {
        floorMap.put(CORE_FANATIC.floor, CORE_FANATIC);
        floorMap.put(CORE.floor, CORE);
        floorMap.put(INTEGRATED.floor, INTEGRATED);
        floorMap.put(ACCEPTED.floor, ACCEPTED);
        floorMap.put(TOLERATED.floor, TOLERATED);
        floorMap.put(NEUTRAL.floor, NEUTRAL);
        floorMap.put(BARELY_TOLERATED.floor, BARELY_TOLERATED);
        floorMap.put(REJECTED.floor, REJECTED);
        floorMap.put(SHUNNED.floor, SHUNNED);
        floorMap.put(PERSECUTED.floor, PERSECUTED);
        floorMap.put(FANATICAL_PERSECUTION.floor, FANATICAL_PERSECUTION);
    }

    public static Acceptance get(int floor) {
        if (floor > 0) {
            return floorMap.floorEntry(floor).getValue();
        } else {
            return floorMap.ceilingEntry(floor).getValue();
        }
    }
    public static Acceptance getLower(Acceptance a){
        return getLower(a.floor);
    }
    public static Acceptance getLower(int floor){
        if (floor >= 0){
            return floorMap.lowerEntry(floor).getValue();
        } else {
            return floorMap.higherEntry(floor).getValue();
        }
    }
    public static Acceptance getHigher(Acceptance a){
        return getHigher(a.floor);
    }
    public static Acceptance getHigher(int floor){
        if (floor >= 0){
            return floorMap.higherEntry(floor).getValue();
        } else {
            return floorMap.lowerEntry(floor).getValue();
        }
    }
    public static int getRange(Acceptance a){
        int floor = Math.abs(a.floor);
        int ceil = Math.abs(getHigher(floor).floor) -1;
        return ceil - floor;
    }
    public static Acceptance[] getAll(){
        return floorMap.values().toArray(new Acceptance[0]);
    }
    @Override
    public String getDisplayID() {
        return id;
    }

    @Override
    public String getDisplayName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
    public int getValue() {
        return floor;
    }
}
