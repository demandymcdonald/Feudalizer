package com.objects;

import com.base.DateMutableEntity;
import com.google.gson.JsonObject;
import com.objects.character.sentient.HumanCharacter;
import com.objects.culture.tenet.dynamic.DynamicTenet;
import com.utilities.IDisplayable;
import com.utilities.id.StringIdentifiable;

import java.util.HashMap;
import java.util.Map;

public record CauseOfEnd<T extends DateMutableEntity<?>>(String id, String displayName, String description) implements IDisplayable, StringIdentifiable {
    private static final Map<String,CauseOfEnd<?>> CAUSE_MAP = new HashMap<>();
    @Override
    public String getDisplayID() {
        return id;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void serialize(JsonObject object){
        object.addProperty("CoE",id);
    }

    @Override
    public String getID() {
        return id;
    }

    public static class Character {

    }
    public static class DynamicTenets {
        public static final CauseOfEnd<? extends DynamicTenet<?>> NO_MEMBERS = build("tenet_no_members", "No Members", "Was dismantled due to having no members");
    }

    public static <T extends DateMutableEntity<?>> CauseOfEnd<T> build(String id, String displayName, String description){
        CauseOfEnd<T> coe = new CauseOfEnd<T>(id,displayName,description);
        CAUSE_MAP.put(id,coe);
        return coe;
    }
    public static <T extends DateMutableEntity<?>> CauseOfEnd<T> get(String id){
        return (CauseOfEnd<T>) CAUSE_MAP.get(id);
    }
    public static <T extends DateMutableEntity<?>>  CauseOfEnd<T> fromJson(JsonObject json){
        return get(json.get("CoE").getAsString());
    }
    

}
