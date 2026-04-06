package com.objects;

import com.base.DateMutableEntity;
import com.google.gson.JsonObject;
import com.objects.character.HumanCharacter;
import com.utilities.Displayable;

import java.util.HashMap;
import java.util.Map;

public record CauseOfEnd<T extends DateMutableEntity<?>>(String id, String displayName, String description) implements Displayable{
    private static final Map<String,CauseOfEnd<?>> CAUSE_MAP = new HashMap<>();
    @Override
    public String getID() {
        return id;
    }

    @Override
    public String displayName() {
        return displayName;
    }

    @Override
    public String description() {
        return description;
    }

    public void serialize(JsonObject object){
        object.addProperty("CoE",id);
    }

    public static class Character {
        public static final CauseOfEnd<HumanCharacter> CHARACTER_DISEASE_CHILD = build("char_disease_child", "Disease (Child)", "Died from disease as a child");
        public static final CauseOfEnd<HumanCharacter> CHARACTER_DISEASE_ADULT = build("char_disease_adult", "Disease (Adult)", "Died from disease as an adult");
        public static final CauseOfEnd<HumanCharacter> CHARACTER_OLD_AGE = build("char_old_age", "Old Age", "Died of old age");
        public static final CauseOfEnd<HumanCharacter> CHARACTER_ACCIDENT = build("char_accident", "Accident", "Died in an accident");
        public static final CauseOfEnd<HumanCharacter> CHARACTER_COMBAT = build("char_combat", "Combat", "Died in combat");
        public static final CauseOfEnd<HumanCharacter> CHARACTER_EXECUTION = build("char_execution", "Execution", "Died by execution");
        public static final CauseOfEnd<HumanCharacter> CHARACTER_STARVATION = build("char_starvation", "Starvation", "Died from starvation");
        public static final CauseOfEnd<HumanCharacter> CHARACTER_NATURAL_DISASTER = build("char_natural_disaster", "Natural Disaster", "Died in a natural disaster");
        public static final CauseOfEnd<HumanCharacter> CHARACTER_POISONING = build("char_poisoning", "Poisoning", "Died from poisoning");
        public static final CauseOfEnd<HumanCharacter> CHARACTER_ANIMAL_ATTACK = build("char_animal_attack", "Animal Attack", "Died from an animal attack");
        public static final CauseOfEnd<HumanCharacter> CHARACTER_CHILD_BIRTH = build("char_child_birth", "Childbirth", "Died during childbirth");
        public static final CauseOfEnd<HumanCharacter> CHARACTER_SUICIDE = build("char_suicide", "Suicide", "Died by suicide");
        public static final CauseOfEnd<HumanCharacter> CHARACTER_FAMINE = build("char_famine", "Famine", "Died from famine");
        public static final CauseOfEnd<HumanCharacter> ERROR = build("char_error", "Error", "Unknown cause of death");
        public static final CauseOfEnd<HumanCharacter> CHARACTER_OTHER = build("char_other", "Other", "Died from other causes");
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
