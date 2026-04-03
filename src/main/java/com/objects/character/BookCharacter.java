package com.objects.character;

import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.changes.TimelineChange;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.objects.CauseOfEnd;
import com.objects.people.Family;
import com.objects.people.House;
import com.objects.title.Title;

import java.time.LocalDate;
import java.util.*;

public class BookCharacter extends LivingCreature<BookCharacter> {


    public enum Gender {
        Male("Male"),
        Female("Female"),
        Trans_Male("Trans-Male"),
        Trans_Female("Trans-Female"),
        Non_Binary("Non-Binary"),
        Other("Other");

        private final String display;
        Gender(String d){
            display = d;
        }
        public String getFlavor(){
            return display;
        }
    }
    public enum Orientation {
        Heterosexual("Heterosexual"),
        Homosexual("Homosexual"),
        Bisexual("Bisexual"),
        Asexual("Asexual"),
        Questioning("Questioning"),
        Other("Other");

        private final String display;
        Orientation(String d){
            display = d;
        }
        public String getFlavor(){
            return display;
        }
    }
    private String givenName;
    private String surname;
    private Gender gender;
    private Orientation orientation;
    //private final Map<UUID, Opinion> opinions = new HashMap<>();

    //TODO Add: Religion, Culture, Political Ideology.

    private Optional<House> linked_house;
    private final Map<Family, Family.Relationship> linked_families = Maps.newHashMap();
    private final List<Title<?>> linked_titles = new ArrayList<>();


    public BookCharacter(String givenName, String surname, LocalDate dateOfBirth, LocalDate dateOfDeath,
                         Gender gender, Orientation orientation) {
        super(dateOfBirth,dateOfDeath,List.of(
            new ChangeSupplier<BookCharacter,CharacterSingleChange.setForename>(){
                @Override
                public CharacterSingleChange.setForename supply(LocalDate date, DMEReference<BookCharacter> subject) {
                    return new CharacterSingleChange.setForename(subject,date,givenName);
                }
            },
            new ChangeSupplier<BookCharacter,CharacterSingleChange.setSurname>(){
                @Override
                public CharacterSingleChange.setSurname supply(LocalDate date, DMEReference<BookCharacter> subject) {
                    return new CharacterSingleChange.setSurname(subject,date,surname);
                }
            },
            new ChangeSupplier<BookCharacter,CharacterSingleChange.setGender>(){
                @Override
                public CharacterSingleChange.setGender supply(LocalDate date, DMEReference<BookCharacter> subject) {
                    return new CharacterSingleChange.setGender(subject,date,gender);
                }
            },
            new ChangeSupplier<BookCharacter,CharacterSingleChange.setOrientation>(){
                @Override
                public CharacterSingleChange.setOrientation supply(LocalDate date, DMEReference<BookCharacter> subject) {
                    return new CharacterSingleChange.setOrientation(subject,date,orientation);
                }
            }
        ));
        this.givenName = givenName;
        this.surname = surname;
        this.gender = gender;
        this.orientation = orientation;
    }
    public BookCharacter(DMEReference<BookCharacter> ref) {
        super(ref);
    }

    @Override
    public void onLink() {
        //I Don't think Character will ever call out to any objects. Most objects should populate it?
    }

    @Override
    public void doDateChange() {
        linked_house = Optional.empty();
        linked_families.clear();
        linked_titles.clear();
    }

    @Override
    public TimelineChange<BookCharacter> getBirthChange(DMEReference<BookCharacter> dme, LocalDate date) {
        return new CharacterSingleChange.Birth(dme,date);
    }

    @Override
    public TimelineChange<BookCharacter> getDeathChange(DMEReference<BookCharacter> dme, LocalDate date, CauseOfEnd cOd) {
        return new CharacterSingleChange.Death(dme,date,cOd);
    }

    @Override
    public CauseOfEnd defaultDeathCause() {
        return CauseOfEnd.Character.CHARACTER_OLD_AGE;
    }


    public void setForename(String name) {
        getTimeline().addChange(new CharacterSingleChange.setForename(getReference(),current(),name));
    }
    public void setSurname(String name) {
        getTimeline().addChange(new CharacterSingleChange.setSurname(getReference(),current(),name));
    }
    public void setGender(Gender gender) {
        getTimeline().addChange(new CharacterSingleChange.setGender(getReference(),current(),gender));
    }
    public void setSexualOrientation(Orientation orientation) {
        getTimeline().addChange(new CharacterSingleChange.setOrientation(getReference(),current(),orientation));
    }



    public void linkHouse(House house){
        linked_house = Optional.of(house);
    }
    public void linkFamily(Family family, Family.Relationship rel){
        linked_families.put(family, rel);
    }
    public void linkTitle(Title<?> title){
        linked_titles.add(title);
    }



    public void internalSetForename(String forename){
        this.givenName = forename;
    }
    public void internalSetSurname(String surname){
        this.surname = surname;
    }
    public void internalSetGender(Gender gender){
        this.gender = gender;
    }
    public void internalSetOrientation(Orientation orientation){
        this.orientation = orientation;
    }
//    public void internalSetOpinion(Map<UUID, Opinion> opinions){
//        this.opinions.clear();
//        this.opinions.putAll(opinions);
//    }

    public String getFullName(){
        //TODO When culture gets implemented, we'll flip have a rule setting how this'll be handled.
        return givenName + " " + surname;
    }
    public String getForename(){
        return givenName;
    }
    public String getSurname(){
        return surname;
    }
    public Gender getGender(){
        return gender;
    }
    public Orientation getOrientation(){
        return orientation;
    }
    //public Map<UUID,Opinion> getOpinions(){
//        return opinions;
//    }

    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }
}
