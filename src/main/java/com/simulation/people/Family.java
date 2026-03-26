package com.simulation.people;

import com.Feudalizer;

import com.base.DateMutableEntity;
import com.GlobalVars;
import com.base.StateChangeKey;
import com.base.reference.DMEReference;
import com.google.gson.JsonObject;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.*;

/**
 * Represents a Family entity which includes information about spouses, children, and
 * associated houses. The `Family` class also provides utility functions to manage
 * relationships and retrieve information about the family members.
 */
public class Family extends DateMutableEntity<Family,FamilyState> {
    private BookCharacter PrimarySpouse;
    private Optional<BookCharacter> SecondarySpouse;
    private  Optional<House> PrimaryHouse;
    private  Optional<House> SecondaryHouse;
    private  List<BookCharacter> Children = new ArrayList<>();

    public Family(JsonObject payload) {
        super(payload);
        init();
    }

    public Family(UUID id, LocalDate foundingDate, BookCharacter PrimarySpouse, BookCharacter secondarySpouse, List<BookCharacter> Children) {
        super(id,foundingDate,null);
        this.PrimarySpouse = PrimarySpouse;
        if (secondarySpouse != null) {
            this.SecondarySpouse = Optional.of(secondarySpouse);
            SecondaryHouse = secondarySpouse.getHouse();
        } else {
            SecondaryHouse = Optional.empty();
            this.SecondarySpouse = Optional.empty();
        }

        PrimaryHouse = PrimarySpouse.getHouse();
        this.Children = Children;
        init();
    }
    public Family(UUID id, LocalDate foundingDate, BookCharacter primarySpouse){
        this(id,foundingDate,primarySpouse,null,new ArrayList<>());
        init();
    }
    public BookCharacter getPrimarySpouse() {
        return PrimarySpouse;
    }
    public Optional<BookCharacter> getSecondarySpouse() {
        return SecondarySpouse;
    }
    public Optional<House> getPrimaryHouse() {
        return PrimaryHouse;
    }
    public Optional<House> getSecondaryHouse() {
        return SecondaryHouse;
    }
    public List<BookCharacter> getChildren() {
        return Children;
    }
    public BookCharacter[] getSpouses() {
        return SecondarySpouse.map(bookCharacter -> new BookCharacter[]{PrimarySpouse, bookCharacter}).orElseGet(() -> new BookCharacter[]{PrimarySpouse});
    }

    public FamilyRelationship getFamilyRelationship(BookCharacter person) {
        FamilyRelationship relationship;
        if (Children.stream().anyMatch(p -> p.getId() == person.getId())) {
            return FamilyRelationship.CHILD;
        }
        else if (person == PrimarySpouse) {
            return FamilyRelationship.PRIMARY_SPOUSE;
        } else if (SecondarySpouse.isPresent() && person == SecondarySpouse.get()) {
            return FamilyRelationship.SECONDARY_SPOUSE;
        } else {
            Feudalizer.LOGGER.error(person + " is not in family: " + this.toString());
            return FamilyRelationship.ERROR;
        }
    }
    public boolean isMember(BookCharacter person) {
        return  person == PrimarySpouse || person == SecondarySpouse.orElse(PrimarySpouse) || Children.stream().anyMatch(p -> p.getId() == person.getId());
    };

    @Override
    public String toString() {
        return PrimarySpouse.getSurname();
    }

    @Override
    protected FamilyState getCurrentContainer() {
        return FamilyState.builder(PrimarySpouse, SecondarySpouse.orElse(null), Children);
    }

    @Override
    public void relink(FamilyState state) {

    }

    public List<BookCharacter> getChildrenOrdered(){
        return (getChildrenOrdered(true));
    }
    public List<BookCharacter> getChildrenOrdered(boolean oldestToYoungest) {
        List<BookCharacter> children = new ArrayList<>(Children);
        if (oldestToYoungest) {
            return children;
        } else {
            children.sort(Comparator.comparing(BookCharacter::getCreated).reversed());
        }
        Feudalizer.LOGGER.info("Children for " + PrimarySpouse.getGivenName() + ": " + children.size());
        return children;
    }
    public void haveChild(BookCharacter.Gender gender, String name, boolean primarySurname) {
        if (!SecondarySpouse.isPresent()) {
            //TODO add flag for no spouse in family
            Feudalizer.LOGGER.error("No spouse in family: " + this.toString());
            return;
        }else if (PrimarySpouse.getGender() == SecondarySpouse.get().getGender()){
            Feudalizer.LOGGER.error("Spouses are of same gender: " + this.toString());
            //TODO add flag to either override or delegate to adopt function. No in-vitro here :(
            return;
        } else if (!SecondarySpouse.get().isAlive() || !PrimarySpouse.isAlive()) {
            Feudalizer.LOGGER.error("Spouses are dead: " + this.toString());
            //TODO add flag because necrophilia doesn't produce kids (No Gideon of the Ninth necromancy)
            return;
        }
        BookCharacter secSpouse = SecondarySpouse.get();
        Feudalizer.LOGGER.info("Adding child: " + name + " to family: " + this.toString());
        Pair<Family,FamilyRelationship> defaultFam = new Pair<>(this,FamilyRelationship.CHILD);
        //Feudalizer.LOGGER.info("family tie established");
        BookCharacter child;
        if (primarySurname || (PrimarySpouse.isNoble() && !secSpouse.isNoble())) {
            child = new BookCharacter(UUID.randomUUID(),name,PrimarySpouse.getSurname(),this.PrimaryHouse.orElse(null), GlobalVars.CURRENT_DATE(),null,gender,defaultFam);
            Children.add(child);
        } else {
            child = new BookCharacter(UUID.randomUUID(),name,secSpouse.getSurname(),this.PrimaryHouse.orElse(null), GlobalVars.CURRENT_DATE(),null,gender,defaultFam);
            Children.add(child);
        }
        doChildReorder();
        addStateChange(GlobalVars.CURRENT_DATE(), StateChangeKey.hadChild(PrimarySpouse,SecondarySpouse.orElse(null),child));
    }
    public static List<BookCharacter> orderByAge(boolean oldestToYoungest, Collection<BookCharacter> toBeOrdered) {
        List<BookCharacter> ordered = new ArrayList<>(toBeOrdered);
        if (oldestToYoungest){
            ordered.sort(Comparator.comparing(BookCharacter::getCreated));
        } else {
            ordered.sort(Comparator.comparing(BookCharacter::getCreated).reversed());
        }
        return ordered;
    }
    private void doChildReorder(){
        Children.sort(Comparator.comparing(BookCharacter::getCreated));
    }
    @Override
    protected JsonObject serializeData(FamilyState data) {
        return data.getSerialized();
    }

    @Override
    protected FamilyState buildState(JsonObject o) {
        return FamilyState.deserialize(o);
    }

    @Override
    public StateChangeKey defaultKey() {
        return new StateChangeKey(StateChangeKey.StateChangeType.TITLE_CREATED,new DMEReference<>(this));
    }

}
