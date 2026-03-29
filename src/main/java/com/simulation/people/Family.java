package com.simulation.people;

import com.Feudalizer;

import com.base.DateMutableEntity;
import com.GlobalVars;
import com.base.ObjectType;
import com.base.StateChangeKey;
import com.base.reference.DMEReference;
import com.google.gson.JsonObject;
import javafx.util.Pair;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.*;

/**
 * Represents a Family entity which includes information about spouses, children, and
 * associated houses. The `Family` class also provides utility functions to manage
 * relationships and retrieve information about the family members.
 */
public class Family extends DateMutableEntity<Family> {
    public enum Relationship {
        HEAD_OF_HOUSE,
        SPOUSE,
        EX_SPOUSE,
        LOVER,
        EX_LOVER,
        CONCUBINE,
        EX_CONCUBINE,
        CHILD_BORN,
        CHILD_BORN_DISOWNED,
        CHILD_ADOPTED,
        CHILD_ADOPTED_DISOWNED,
        ERROR
    }

    //######
    private HashMap<DMEReference<BookCharacter>, Relationship> Members = new HashMap<>();

    public Family(LocalDate created, @Nullable LocalDate ended, DMEReference<BookCharacter> head) {
        super(created, ended, initialState);
        Members = children;
    }

    public Family(DMEReference<Family> dme, LocalDate) {
        super(dme, saveData);
        Members = children;
    }




    @Override
    public ObjectType getObjectType() {
        return null;
    }

    @Override
    public JsonObject additionalData() {
        return null;
    }

    @Override
    public JsonObject onLoad(JsonObject data) {
        return null;
    }








    public List<DMEReference<BookCharacter>> getMembers(){
        return Members.keySet().stream().toList();
    }

























    public Relationship getFamilyRelationship(BookCharacter person) {
        Relationship relationship;
        if (Members.stream().anyMatch(p -> p.getId() == person.getId())) {
            return Relationship.CHILD;
        }
        else if (person == HeadofFamily) {
            return Relationship.PRIMARY_SPOUSE;
        } else if (SecondarySpouse.isPresent() && person == SecondarySpouse.get()) {
            return Relationship.SECONDARY_SPOUSE;
        } else {
            Feudalizer.LOGGER.error(person + " is not in family: " + this.toString());
            return Relationship.ERROR;
        }
    }
    public boolean isMember(BookCharacter person) {
        return  person == HeadofFamily || person == SecondarySpouse.orElse(HeadofFamily) || Members.stream().anyMatch(p -> p.getId() == person.getId());
    };

    @Override
    public String toString() {
        return HeadofFamily.getSurname();
    }


    public List<BookCharacter> getChildrenOrdered(){
        return (getChildrenOrdered(true));
    }
    public List<BookCharacter> getChildrenOrdered(boolean oldestToYoungest) {
        List<BookCharacter> children = new ArrayList<>(Members);
        if (oldestToYoungest) {
            return children;
        } else {
            children.sort(Comparator.comparing(BookCharacter::getCreated).reversed());
        }
        Feudalizer.LOGGER.info("Children for " + HeadofFamily.getGivenName() + ": " + children.size());
        return children;
    }
    public void haveChild(BookCharacter.Gender gender, String name, boolean primarySurname) {
        if (!SecondarySpouse.isPresent()) {
            //TODO add flag for no spouse in family
            Feudalizer.LOGGER.error("No spouse in family: " + this.toString());
            return;
        }else if (HeadofFamily.getGender() == SecondarySpouse.get().getGender()){
            Feudalizer.LOGGER.error("Spouses are of same gender: " + this.toString());
            //TODO add flag to either override or delegate to adopt function. No in-vitro here :(
            return;
        } else if (!SecondarySpouse.get().isAlive() || !HeadofFamily.isAlive()) {
            Feudalizer.LOGGER.error("Spouses are dead: " + this.toString());
            //TODO add flag because necrophilia doesn't produce kids (No Gideon of the Ninth necromancy)
            return;
        }
        BookCharacter secSpouse = SecondarySpouse.get();
        Feudalizer.LOGGER.info("Adding child: " + name + " to family: " + this.toString());
        Pair<Family, Relationship> defaultFam = new Pair<>(this, Relationship.CHILD);
        //Feudalizer.LOGGER.info("family tie established");
        BookCharacter child;
        if (primarySurname || (HeadofFamily.isNoble() && !secSpouse.isNoble())) {
            child = new BookCharacter(UUID.randomUUID(),name, HeadofFamily.getSurname(),this.PrimaryHouse.orElse(null), GlobalVars.CURRENT_DATE(),null,gender,defaultFam);
            Members.add(child);
        } else {
            child = new BookCharacter(UUID.randomUUID(),name,secSpouse.getSurname(),this.PrimaryHouse.orElse(null), GlobalVars.CURRENT_DATE(),null,gender,defaultFam);
            Members.add(child);
        }
        doChildReorder();
        addStateChange(GlobalVars.CURRENT_DATE(), StateChangeKey.hadChild(HeadofFamily,SecondarySpouse.orElse(null),child));
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
        Members.sort(Comparator.comparing(BookCharacter::getCreated));
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
