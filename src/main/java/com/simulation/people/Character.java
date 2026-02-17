package com.simulation.people;

import com.GlobalData;
import com.GlobalVars;
import com.base.*;
import com.base.reference.DMEReference;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.simulation.succession.Title;
import javafx.util.Pair;

import java.util.*;

public class Character extends DateMutableEntity<Character.CharacterState> {
    public enum Gender {
        Male,
        Female;
    }
    private String givenName;
    private Gender gender;
    private House house;
    private HashMap<Family, FamilyRelationship> families = new HashMap<>();
    private List<Title<?>> Titles = new ArrayList<>();
    public Character(UUID id, String givenName, House house,Date dateOfBirth, Date dateOfDeath, Gender gender, Pair<Family,FamilyRelationship>... families) {
        super(id,dateOfBirth,dateOfDeath, addAdditional(new JsonObject(),givenName,gender));
        this.givenName = givenName;
        this.house = house;
        this.gender = gender;
        for ( Pair<Family,FamilyRelationship> pair : families) {
            this.families.put(pair.getKey(), pair.getValue());
        }
    }

    public Character(JsonObject json) {
        super(json);
        givenName = getAdditionalData().get("givenName").getAsString();
        gender = Gender.valueOf(getAdditionalData().get("gender").getAsString());
    }


    @Override
    protected CharacterState getCurrentState() {
        return CharacterState.builder(families.keySet().stream().toList(),getTitles(),house);
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
    public  boolean isAlive() {
        return getEnded().after(GlobalVars.CURRENT_DATE);
    }

    @Override
    protected JsonObject serializeData(CharacterState data) {
        return data.serialize();
    }

    @Override
    protected CharacterState buildState(JsonObject o) {
        return CharacterState.deserialize(o);
    }

    public record CharacterState(List<UUID> Families, UUID House, List<UUID> Titles){
        public static CharacterState builder(List<Family> families, List<Title<?>> title, House house){
            List<UUID> familyID = DateMutableEntity.convert(families);
            List<UUID> titleID = DateMutableEntity.convert(title);
            return new CharacterState(familyID, house.getId(),titleID);
        }
        public JsonObject serialize() {
            JsonObject json = new JsonObject();
            JsonArray familyArray = DateMutableEntity.buildJson(Families);
            JsonArray titleArray = DateMutableEntity.buildJson(Titles);
            json.addProperty("house", House.toString());
            json.add("families", familyArray);
            json.add("titles", titleArray);
            return json;
        }
        public static CharacterState deserialize(JsonObject o) {
            List<UUID> familyID = buildUUID(o.get("families").getAsJsonArray());
            List<UUID> titleID = buildUUID(o.get("titles").getAsJsonArray());
            return new CharacterState(familyID, UUID.fromString(o.get("house").getAsString()), titleID);
        }
    };


    public String getGivenName() {
        return givenName;
    }
    public House getHouse() {
        return house;
    }
    public Gender getGender() {return gender;}
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

    public HashMap<Family, FamilyRelationship> getFamilies() {
        return families;
    }
    public List<Title<?>> getTitles(){
        //TODO: Fix
        return new ArrayList<>(this.Titles);
    }
    public void addTitle(Title<?> title){
        if (title.getHolder().isPresent() && title.getHolder().get().equals(this)){
            this.Titles.add(title);
            addStateChange(GlobalVars.CURRENT_DATE, new StateChangeKey(StateChangeKey.StateChangeType.GRANT_TITLE, DMEReference.of(this),DMEReference.of(title)));
        } else {
            title.setHolder(this);
        }
    }
    public void revokeTitle(Title<?> title){
        if (!title.getHolder().isPresent()){
            this.Titles.remove(title);
            addStateChange(GlobalVars.CURRENT_DATE, new StateChangeKey(StateChangeKey.StateChangeType.REVOKE_TITLE, DMEReference.of(this),DMEReference.of(title)));
        } else if (title.getHolder().get().equals(this)){
            title.removeHolder(this);
        }
    }
    public void giveBirth(Character otherParent, Gender gender, String name){

        Family f = findOrCreateFamily(otherParent,true); //TODO check if this is okay logic wise..


    }
    public void recalculateSuccession(){

    }

    protected Family findOrCreateFamily(Character spouse, boolean makePrimary){
        for (Family f : Family.getNuclear(this)){
            if (Arrays.asList(f.getSpouses()).contains(spouse)){
                return f;
            }
        }
        Character primary;
        Character secondary;
        if (makePrimary){
            primary = this;
            secondary = spouse;
        } else {
            primary = spouse;
            secondary = this;
        }
        return new Family(UUID.randomUUID(), GlobalVars.CURRENT_DATE, primary, secondary, new HashMap<>());
    }
    private static JsonObject addAdditional(JsonObject j, String name, Gender gender){
        j.addProperty("givenName", name);
        j.addProperty("gender", gender.toString());
        return j;
    }
    @Override
    protected JsonObject saveAdditional(JsonObject j) {
        super.saveAdditional(j);
        addAdditional(j,givenName,gender);
        return j;
    }

}
