package com.people;

import com.GlobalData;
import com.base.DateMutableEntity;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
        PrimaryHouse = PrimarySpouse.house();
        SecondaryHouse = SecondarySpouse.house();
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


    @Override
    protected JsonObject serializeData(FamilyState data) {
        return data.serialize();
    }

    @Override
    protected FamilyState buildState(JsonObject o) {
        return FamilyState.deserialize(o);
    }

}
