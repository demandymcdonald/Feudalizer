package com.simulation.people;

import com.Feudalizer;

import com.GlobalVars;
import com.base.*;
import com.base.reference.DMEReference;
import com.base.timeline.change.CauseOfDeath;
import com.base.timeline.change.CharacterTLChange;
import com.google.gson.JsonObject;
import com.simulation.title.Title;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.*;

import static com.simulation.people.FamilyManager.findOrCreateFamily;

public class BookCharacter extends DateMutableEntity<BookCharacter,CharacterState> {
    public enum Gender {
        Male,
        Female;
    }
    private String givenName;
    private String surname;
    private Gender gender;
    private Optional<House> house;
    private final HashMap<Family, FamilyRelationship> families = new HashMap<>();
    private List<Title<?>> Titles = new ArrayList<>();
    public BookCharacter(UUID id, String givenName, String surname, House house, LocalDate dateOfBirth, LocalDate dateOfDeath, Gender gender, Pair<Family,FamilyRelationship>... families) {
        super(id,dateOfBirth,dateOfDeath, addAdditional(new JsonObject(),givenName,surname,gender));
        this.givenName = givenName;
        this.surname = surname;
        if (house == null) {
            this.house = Optional.empty();
        } else {
            this.house = Optional.of(house);
        }
        this.gender = gender;
        for ( Pair<Family,FamilyRelationship> pair : families) {
            this.families.put(pair.getKey(), pair.getValue());
            Feudalizer.LOGGER.info("Added family " + pair.getKey().toString() + " to " + this.toString() + " As " + pair.getValue() + " relationship");
        }
        init();
    }
    public BookCharacter(UUID id, String givenName, String surname, House house, LocalDate dateOfBirth, LocalDate dateOfDeath, Gender gender) {
        super(id,dateOfBirth,dateOfDeath);
        this.givenName = givenName;
        this.surname = surname;
        if (house == null) {
            this.house = Optional.empty();
        } else {
            this.house = Optional.of(house);
        }
        this.gender = gender;
        families.put(new Family(UUID.randomUUID(),dateOfBirth,this),FamilyRelationship.PRIMARY_SPOUSE);
        init();
    }
    public BookCharacter(JsonObject json) {
        super(json);
        givenName = getAdditionalData().get("givenName").getAsString();
        surname = getAdditionalData().get("surname").getAsString();
        gender = Gender.valueOf(getAdditionalData().get("gender").getAsString());
        init();
    }


    @Override
    protected CharacterState getCurrentContainer() {
        return CharacterState.builder(families.keySet().stream().toList(),getTitles(),house);
    }

    @Override
    public void relink(CharacterState state) {
        families.clear();
        if (house.isPresent()) {
            house = Optional.of(DMRegistry.getHouseManager().get(state.House()));
        } else {
            house = Optional.empty();
        }
        final FamilyManager manager = DMRegistry.getFamilyManager();
        for (UUID family : state.Families){
            Family f = manager.get(family);
            families.put(f,f.getFamilyRelationship(this));
        }
    }
    public  boolean isAlive() {
        return getEnded().isAfter(GlobalVars.CURRENT_DATE());
    }

    @Override
    protected JsonObject serializeData(CharacterState data) {
        return data.getSerialized();
    }


    @Override
    public StateChangeKey defaultKey() {
        return new StateChangeKey(StateChangeKey.StateChangeType.WAS_BORN,new DMEReference<>(this));
    }


    ;


    public String getGivenName() {
        return givenName;
    }
    private static final String nullSurname = "Nullius-Gentis";
    public String getSurname(){
        if (surname == null || surname.isBlank()) {
            return nullSurname;
        }
        return surname;
    }
    public Optional<House> getHouse() {
        return house;
    }
    public Gender getGender() {return gender;}
    public Family getParentFamily() {
      for (Map.Entry<Family, FamilyRelationship> family : families.entrySet()) {
          if (family.getValue() == FamilyRelationship.CHILD){
              return family.getKey();
          }
      }
        Feudalizer.LOGGER.error("No parent family found for {}!", givenName);
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
            addStateChange(GlobalVars.CURRENT_DATE(), new StateChangeKey(StateChangeKey.StateChangeType.GRANT_TITLE, DMEReference.of(this),DMEReference.of(title)));
        } else {
            title.setHolder(this);
        }
    }
    public void addToTitleList(Title<?> title){
        if (title.getHolder().isPresent() && title.getHolder().get().equals(this)){
            this.Titles.add(title);
        }
    }
    public void revokeTitle(Title<?> title){
        if (!title.getHolder().isPresent()){
            this.Titles.remove(title);
            addStateChange(GlobalVars.CURRENT_DATE(), new StateChangeKey(StateChangeKey.StateChangeType.REVOKE_TITLE, DMEReference.of(this),DMEReference.of(title)));
        } else if (title.getHolder().get().equals(this)){
            title.removeHolder(this);
        }
    }
    public <T extends Title<T>> List<T> getTitlesOfType(Class<T> titleClass){
        return Titles.stream().filter(title -> titleClass.isAssignableFrom(title.getClass())).map(title -> (T) title).toList();
    }
    public void giveBirth(BookCharacter otherParent, Gender gender, String name){
        Family f = findOrCreateFamily(this,otherParent,true); //TODO check if this is okay logic wise..
        f.haveChild(gender,name,true);
    }
    public void recalculateSuccession(){

    }
    public void addFamily(Family family, FamilyRelationship relationship){
        if (!family.isMember(this)){
            Feudalizer.LOGGER.error("Tried to add family " + family.getId() + " to " + this.getId() + " but they are not members!");
        }
        families.put(family,relationship);
    }
    public void setHouse(House house){
        this.house = Optional.of(house);
        addStateChange(GlobalVars.CURRENT_DATE(),new StateChangeKey(StateChangeKey.StateChangeType.HOUSE_CHANGED,DMEReference.of(this),DMEReference.of(house)));
    }
    protected void setHouseInternal(House house){
        //TODO: Decide how to handle this... If it's a internal tool for house founding, or if it can also be an outside method to add someone to a house.. Probably the former?
        this.house = Optional.of(house);
        addStateChange(GlobalVars.CURRENT_DATE(),new StateChangeKey(StateChangeKey.StateChangeType.TREAT_AS_STATUS_QUO,DMEReference.of(this),DMEReference.of(house)));
    }
    public Optional<BookCharacter> getLiege(){
        return Optional.empty();
        //TODO Add later;
    }
    private static JsonObject addAdditional(JsonObject j, String name, String surname, Gender gender){
        j.addProperty("givenName", name);
        j.addProperty("surname", surname);
        j.addProperty("gender", gender.toString());
        return j;
    }
    public void setDeath(LocalDate date, CauseOfDeath death){
        this.setEnded(date);
        addStateChange(date,new CharacterTLChange.CharacterDeath(DMEReference.of(this),null,death,date));
    }
    @Override
    protected JsonObject saveAdditional(JsonObject j) {
        super.saveAdditional(j);
        addAdditional(j,givenName,surname,gender);
        return j;
    }
    @Override
    public String toString() {
        return givenName + " " + getSurname();
    }
    public boolean isNoble(){
        return house.isPresent();
    }

    public LocalDate getDOB(){
        return this.getCreated();
    }
    public LocalDate getDOD(){
        return this.getEnded();
    }
    public void setDOB(LocalDate date){
        this.setCreated(date);
    }
    public void setDOD(LocalDate date){
        this.setEnded(date);
    }
    public void setGivenName(String given){
        this.givenName = given;
    }
    public void setSurname(String surname){
        this.surname = surname;
    }
    public void setGender(Gender gender){
        this.gender = gender;
    }
}
