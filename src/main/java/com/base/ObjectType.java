package com.base;

import com.objects.character.sentient.HumanCharacter;
import com.objects.family.Family;
import com.objects.government.House;
import com.objects.title.Title;

public enum ObjectType {
    CHARACTER("book_character","Character", HumanCharacter.class),
    FAMILY("family","FamilyGroups","Famlies", Family.class),
    HOUSE("house","House","Houses", House.class),
    TITLE("title","Title", (Class<? extends DateMutableEntity<?>>) Title.class);
    //CULTURE("culture","Culture",Culture.class),
    //CULTURE_OBJECT("culture_object","Culture Object",(Class<? extends DateMutableEntity<?>>) CultureObject.class),
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
