package com.base;

import com.simulation.character.BookCharacter;
import com.simulation.people.Family;
import com.simulation.people.House;
import com.simulation.title.Title;

public enum ObjectType {
    CHARACTER("BookCharacter","Character", BookCharacter.class),
    FAMILY("Family","Family","Famlies", Family.class),
    HOUSE("House","House","Houses", House.class),
    TITLE("Title","Title", Title.class);

    private final String regKey;
    private final Class<? extends DateMutableEntity<?>> baseClass;
    private final String displayName;
    private final String displayNamePlural;
    ObjectType(String regKey, String displayName,String displayNamePlural, Class<? extends DateMutableEntity<?>> clazz) {
        this.regKey = regKey;
        this.displayName = displayName;
        this.displayNamePlural = displayNamePlural;
        this.baseClass = clazz;
    }
    ObjectType(String regKey, String displayName,Class<? extends DateMutableEntity<?>> clazz) {
        this.regKey = regKey;
        this.displayName = displayName;
        this.displayNamePlural = displayName + "s";
        this.baseClass = clazz;
    }
    public Class<? extends DateMutableEntity<?>> getBaseClass() {
        return baseClass;
    }
    public String getRegKey() {
        return regKey;
    }
    public static ObjectType getByRegKey(String regKey){
        for (ObjectType t : values()){
            if (t.getRegKey().equals(regKey)){
                return t;
            }
        }
        throw new IllegalArgumentException("No such object type: "+regKey);
    }
    public static <T extends DateMutableEntity<T>> ObjectType getByClass(Class<T> clazz){
        for (ObjectType t : values()){
            if (t.getBaseClass().equals(clazz)){
                return t;
            }
        }
        throw new IllegalArgumentException("No such object type: "+clazz);
    }
}
