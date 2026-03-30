package com.simulation.character;

import com.base.*;
import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineChange;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.simulation.people.Family;
import com.simulation.people.House;
import com.simulation.title.Title;

import java.time.LocalDate;
import java.util.*;

public class BookCharacter extends DateMutableEntity<BookCharacter> {
    @Override
    public void saveAdditional(JsonObject data) {

    }

    @Override
    public void loadAdditional(JsonObject data) {

    }

    public enum Gender {
        Male,
        Female;
    }
    private String givenName;
    private String surname;
    private Gender gender;
    private Optional<House> linked_house;
    private final Map<Family, Family.Relationship> linked_families = Maps.newHashMap();
    private final List<Title<?>> linked_titles = new ArrayList<>();

    private List<Title<?>> Titles = new ArrayList<>();
    public BookCharacter(UUID id, String givenName, String surname, LocalDate dateOfBirth, LocalDate dateOfDeath, Gender gender) {
        super(id,dateOfBirth,dateOfDeath,);
        this.givenName = givenName;
        this.surname = surname;
        this.gender = gender;
    }
    private static List<TimelineChange<BookCharacter>> buildBase(String name, LocalDate dateOfBirth, LocalDate dateOfDeath, Gender gender){

    }
    public BookCharacter(DMEReference<BookCharacter> ref) {
        super(ref);
    }

    @Override
    public void onLink() {
        //I Don't think Character will ever call out to any objects. Most objects should populate it?
    }


    @Override
    public final ObjectType getObjectType() {
        return ObjectType.CHARACTER;
    }
    @Override
    public void onStateChange() {
        linked_house = Optional.empty();
        linked_families.clear();
        linked_titles.clear();
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
}
