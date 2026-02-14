package com.people;

import com.GlobalData;
import com.base.DateMutableEntity;
import com.base.DMRegistry;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.util.Pair;

import java.util.*;

public class Character extends DateMutableEntity<Character.CharacterState> {
    private String givenName;
    private House house;
    private HashMap<Family, FamilyRelationship> families = new HashMap<>();
    public Character(UUID id, String givenName, House house,Date dateOfBirth, Date dateOfDeath, Pair<Family,FamilyRelationship>... families) {
        super(id,dateOfBirth,dateOfDeath, addAdditional(new JsonObject(),givenName));
        this.givenName = givenName;
        this.house = house;

        for ( Pair<Family,FamilyRelationship> pair : families) {
            this.families.put(pair.getKey(), pair.getValue());
        }
    }

    public Character(JsonObject json) {
        super(json);
        givenName = getAdditionalData().get("givenName").getAsString();
    }


    @Override
    protected CharacterState getCurrentState() {
        return CharacterState.builder(families.keySet(),house);
    }

    @Override
    public void relink(CharacterState state) {
        families.clear();
        house = DMRegistry.getHouseManager().get(state.House());
        final FamilyManager manager = DMRegistry.getFamilyManager();
        for (UUID family : state.Families){
            Family f = manager.get(family);
            families.put(f,f.getFamilyRelationship(this));
        }
    }


    @Override
    protected JsonObject serializeData(CharacterState data) {
        return data.serialize();
    }

    @Override
    protected CharacterState buildState(JsonObject o) {
        return CharacterState.deserialize(o);
    }

    public record CharacterState(Set<UUID> Families, UUID House){
        public static CharacterState builder(Set<Family> families, House house){
            Set<UUID> ids = new HashSet<>();
            for(Family family : families){
                ids.add(family.getId());
            }
            return new CharacterState(ids, house.getId());
        }
        public JsonObject serialize() {
            JsonObject json = new JsonObject();
            JsonArray array = new JsonArray();
            json.addProperty("house", House.toString());
            for (UUID id : Families) {
                array.add(id.toString());
            }
            json.add("families", array);
            return json;
        }
        public static CharacterState deserialize(JsonObject o) {
            Set<UUID> ids = new HashSet<>();
            JsonArray array = o.get("families").getAsJsonArray();
            for (JsonElement element : array) {
                ids.add(UUID.fromString(element.getAsString()));
            }
            return new CharacterState(ids, UUID.fromString(o.get("house").getAsString()));
        }
    };


    public String getGivenName() {
        return givenName;
    }
    public House getHouse() {
        return house;
    }
    public Family getParentFamily() {
      for (Map.Entry<Family, FamilyRelationship> family : families.entrySet()) {
          if (family.getValue() == FamilyRelationship.CHILD){
              return family.getKey();
          }
      }
        GlobalData.logger().error("No parent family found for {}!", givenName);
      return null;
    }
    public List<FamilyRelationship> getMarried() {
        ArrayList<FamilyRelationship> married = new ArrayList<>();
        for (Map.Entry<Family, FamilyRelationship> family : families.entrySet()) {
            if (family.getValue() == FamilyRelationship.CHILD){
                married.add(family.getValue());
            }
        }
        return married;
    }

    public String givenName() {
        return givenName;
    }
    public House house() {
        return house;
    }
    private static JsonObject addAdditional(JsonObject j, String name){
        j.addProperty("givenName", name);
        return j;
    }
    @Override
    protected JsonObject saveAdditional(JsonObject j) {
        super.saveAdditional(j);
        addAdditional(j,givenName);
        return j;
    }
}
