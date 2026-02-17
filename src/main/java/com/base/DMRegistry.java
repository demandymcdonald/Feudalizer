package com.base;
import com.simulation.people.*;
import com.simulation.succession.Title;
import com.simulation.succession.TitleManager;

import java.lang.Character;
import java.util.HashMap;

public class DMRegistry {
    private static final HashMap<String,AbstractMutableManager<?,?>> registry = new HashMap<>();


    public static <R, T extends DateMutableEntity<R>,M extends AbstractMutableManager<R,T>> void addEntry(String database, M manager) {
        registry.put(database, manager);
    }
    public static <R, T extends DateMutableEntity<R>,M extends AbstractMutableManager<R,T>> void registerDateMutable(T entity) {
        M manager = (M) getEntry(entity.getClass());
        assert manager != null;
        manager.register(entity);
    }
    public static <T extends DateMutableEntity<?>,M extends AbstractMutableManager<?,T>> M getEntry(Class<T> database){
        if(Title.class.isAssignableFrom(database)){
            return (M) getTitleManager();
        }
        if(Family.class.isAssignableFrom(database)){
            return (M) getFamilyManager();
        }
        if (House.class.isAssignableFrom(database)){
            return (M) getHouseManager();
        }
        if (Character.class.isAssignableFrom(database)){
            return (M) getCharacterManager();
        }
        return null;
    }

    public static <R, T extends DateMutableEntity<R>,M extends AbstractMutableManager<R,T>> M getEntry(String database) {
        return (M) registry.get(database);
    }
    static {
        addEntry("Character", new CharacterManager());
        addEntry("Family", new FamilyManager());
        addEntry("House", new HouseManager());
        addEntry("Title", new TitleManager());
    }


    public static FamilyManager getFamilyManager() {
        return (FamilyManager) registry.get(Family.class);
    }
    public static CharacterManager getCharacterManager() {
        return (CharacterManager) registry.get(Character.class);
    }
    public static HouseManager getHouseManager() {
        return (HouseManager) registry.get(House.class);
    }
    public static TitleManager getTitleManager() {
        return (TitleManager) registry.get(Title.class);
    }


//    protected record MutableEntry<R, T extends DateMutableEntity<R>,M extends AbstractMutableManager<R,T>>(M manager, Function<>) {
//
//    }
}
