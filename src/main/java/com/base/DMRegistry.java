package com.base;

import com.divisions.LandManager;
import com.people.CharacterManager;
import com.people.FamilyManager;
import com.people.HouseManager;

import java.util.HashMap;

public class DMRegistry {
    private static final HashMap<String,AbstractMutableManager<?,?>> registry = new HashMap<>();


    public static <R, T extends DateMutableEntity<R>,M extends AbstractMutableManager<R,T>> void addEntry(String database, M manager) {
        registry.put(database, manager);
    }

    public static <R, T extends DateMutableEntity<R>,M extends AbstractMutableManager<R,T>> M getEntry(String database) {
        return (M) registry.get(database);
    }
    static {
        addEntry("character", new CharacterManager());
        addEntry("family", new FamilyManager());
        addEntry("house", new HouseManager());
        addEntry("land", new LandManager());
    }


    public static FamilyManager getFamilyManager() {
        return (FamilyManager) registry.get("family");
    }
    public static CharacterManager getCharacterManager() {
        return (CharacterManager) registry.get("character");
    }
    public static HouseManager getHouseManager() {
        return (HouseManager) registry.get("house");
    }
    public static LandManager getLandManager() {
        return (LandManager) registry.get("land");
    }

//    protected record MutableEntry<R, T extends DateMutableEntity<R>,M extends AbstractMutableManager<R,T>>(M manager, Function<>) {
//
//    }
}
