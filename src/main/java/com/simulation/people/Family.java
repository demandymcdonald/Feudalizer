package com.simulation.people;

import com.GlobalData;
import com.base.DateMutableEntity;
import com.GlobalVars;
import com.base.StateChangeKey;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Family extends DateMutableEntity<Family.FamilyState> {
    private  Character PrimarySpouse;
    private  Character SecondarySpouse;
    private  House PrimaryHouse;
    private  House SecondaryHouse;
    private  HashMap<Character,String> Children;
    public record FamilyState(UUID PrimarySpouse, UUID SecondarySpouse, UUID PrimaryHouse, UUID SecondaryHouse, HashMap<UUID,String> Children) {
        public static FamilyState builder(Character primary, Character secondary, House primaryHouse, House secondaryHouse, HashMap<Character,String> children) {
            HashMap<UUID,String> Children = new HashMap<>();
            for (Map.Entry<Character,String> child : children.entrySet()) {
                Children.put(child.getKey().getId(), child.getValue());
            }
            return new FamilyState(primary.getId(),secondary.getId(),primaryHouse.getId(),secondaryHouse.getId(),Children);
        }
        public JsonObject serialize() {
            JsonObject json = new JsonObject();
            json.addProperty("PrimarySpouse", PrimarySpouse.toString());
            json.addProperty("SecondarySpouse", SecondarySpouse.toString());
            json.addProperty("PrimaryHouse", PrimaryHouse.toString());
            json.addProperty("SecondaryHouse", SecondaryHouse.toString());
            JsonArray children = new JsonArray();
            for (Map.Entry<UUID, String> entry : Children.entrySet()) {
                JsonObject child = new JsonObject();
                child.addProperty("id", entry.getKey().toString());
                child.addProperty("value", entry.getValue());
                children.add(child);
            }
            json.add("Children", children);
            return json;
        }
        public static FamilyState deserialize(JsonObject json) {
            UUID PrimarySpouse = UUID.fromString(json.get("PrimarySpouse").getAsString());
            UUID SecondarySpouse = UUID.fromString(json.get("SecondarySpouse").getAsString());
            UUID PrimaryHouse = UUID.fromString(json.get("PrimaryHouse").getAsString());
            UUID SecondaryHouse = UUID.fromString(json.get("SecondaryHouse").getAsString());
            HashMap<UUID, String> Children = new HashMap<>();
            JsonArray children = json.get("Children").getAsJsonArray();
            for (JsonElement child : children) {
                UUID id = UUID.fromString(child.getAsString());
                String value = child.getAsString();
                Children.put(id, value);
            }
            return new FamilyState(PrimarySpouse, SecondarySpouse, PrimaryHouse, SecondaryHouse, Children);
        }
    }

    public Family(JsonObject payload) {
        super(payload);
    }

    public Family(UUID id, Date foundingDate, Character PrimarySpouse, Character SecondarySpouse, HashMap<Character,String> Children) {
        super(id,foundingDate,null);
        this.PrimarySpouse = PrimarySpouse;
        this.SecondarySpouse = SecondarySpouse;
        PrimaryHouse = PrimarySpouse.getHouse();
        SecondaryHouse = SecondarySpouse.getHouse();
        this.Children = Children;
    }
    public Character getPrimarySpouse() {
        return PrimarySpouse;
    }
    public Character getSecondarySpouse() {
        return SecondarySpouse;
    }
    public House getPrimaryHouse() {
        return PrimaryHouse;
    }
    public House getSecondaryHouse() {
        return SecondaryHouse;
    }
    public HashMap<Character,String> getChildren() {
        return Children;
    }
    public Character[] getSpouses() {
        return new Character[]{PrimarySpouse, SecondarySpouse};
    }

    public FamilyRelationship getFamilyRelationship(Character person) {
        FamilyRelationship relationship;
        if (Children.containsKey(person)) {
            return FamilyRelationship.CHILD;
        }
        else if (person == PrimarySpouse) {
            return FamilyRelationship.PRIMARY_SPOUSE;
        } else if (person == SecondarySpouse) {
            return FamilyRelationship.SECONDARY_SPOUSE;
        } else {
            GlobalData.logger().error(person + " is not in family: " + this.toString());
            return FamilyRelationship.ERROR;
        }
    }
    public boolean isMember(Character person) {
        return  person == PrimarySpouse || person == SecondarySpouse || Children.containsKey(person);
    };

    @Override
    public String toString() {
        return PrimaryHouse.toString();
    }

    @Override
    protected FamilyState getCurrentState() {
        return FamilyState.builder(PrimarySpouse, SecondarySpouse, PrimaryHouse, SecondaryHouse, Children);
    }

    @Override
    public void relink(FamilyState state) {

    }
    public static List<Family> getNuclear(Character character) {
        List<Family> families = new ArrayList<>();
        for (Map.Entry<Family,FamilyRelationship> family : character.getFamilies().entrySet()) {
            if (family.getValue() != FamilyRelationship.CHILD) {
                families.add(family.getKey());
            }
        }
        families.sort(Comparator.comparing(Family::getCreated));
        return families;
    }
    public static Family getBirth(Character character) {
        for (Map.Entry<Family,FamilyRelationship> family : character.getFamilies().entrySet()) {
            if (family.getValue() != FamilyRelationship.CHILD) {
                return family.getKey();
            }
        }
        return null;
    }
    public static Family getCurrentFamily(Character character) {
        for (Family family : getNuclear(character)) {
            if (family.getEnded().after(GlobalVars.CURRENT_DATE)){
                return family;
            }
        }
        return null;
    }
    public static List<Character> getAllSpouses(Character c, boolean oldestToYoungest){
        List<Pair<Character,Date>> spouses = new ArrayList<>();
        List<Family> families = getNuclear(c);
        for (Family family : families) {
            Character spouse = Arrays.stream(family.getSpouses()).filter(sp -> sp != c).findFirst().orElse(null);
            if (spouse != null) {
                spouses.add(new Pair<>(spouse,family.getCreated()));
            }
        }
        if (oldestToYoungest) {
            spouses.sort(Comparator.comparing(Pair::getValue));
        } else {
            spouses.sort(Comparator.<Pair<Character,Date>, Date>comparing(Pair::getValue).reversed());
        }
        return spouses.stream().map(Pair::getKey).collect(Collectors.toList());
    }
    public List<Character> getChildrenOrdered(){
        return (getChildrenOrdered(true));
    }
    public List<Character> getChildrenOrdered(boolean oldestToYoungest) {
        List<Character> children = new ArrayList<>();
        children.addAll(Children.keySet());
        if (oldestToYoungest) {
            children.sort(Comparator.comparing(Character::getCreated));
        } else {
            children.sort(Comparator.comparing(Character::getCreated).reversed());
        }
        return children;
    }
    public void haveChild(Character.Gender gender, String name, boolean primarySurname) {
        if (PrimarySpouse.getGender() == SecondarySpouse.getGender()){
            //TODO add flag to either override or delegate to adopt function. No in-vitro here :(
            return;
        } else if (!SecondarySpouse.isAlive() || !PrimarySpouse.isAlive()) {
            //TODO add flag because necrophilia doesn't produce kids (No Gideon of the Ninth necromancy)
            return;
        }
        Pair<Family,FamilyRelationship> defaultFam = new Pair<>(this,FamilyRelationship.CHILD);
        Character child = new Character(UUID.randomUUID(),name,this.PrimaryHouse,GlobalVars.CURRENT_DATE,null,gender,defaultFam);
        if (primarySurname) {
            Children.put(child, PrimaryHouse.getName());
        } else {
            Children.put(child, SecondaryHouse.getName());
        }
        addStateChange(GlobalVars.CURRENT_DATE, StateChangeKey.hadChild(PrimarySpouse,SecondarySpouse,child));
    }
    @Override
    protected JsonObject serializeData(FamilyState data) {
        return data.serialize();
    }

    @Override
    protected FamilyState buildState(JsonObject o) {
        return FamilyState.deserialize(o);
    }

}
