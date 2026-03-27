package com.base;

import com.simulation.people.BookCharacter;
import com.simulation.people.Family;
import com.simulation.people.House;
import com.simulation.title.Title;

public enum ObjectType {
    CHARACTER("BookCharacter", BookCharacter.class),
    FAMILY("Family", Family.class),
    HOUSE("House", House.class),
    TITLE("Title", Title.class);

    private final String regKey;
    private final Class<? extends DateMutableEntity> baseClass;

    ObjectType(String regKey, Class<? extends DateMutableEntity> clazz) {
        this.regKey = regKey;
        this.baseClass = clazz;
    }
    public Class<? extends DateMutableEntity> getBaseClass() {
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
}
