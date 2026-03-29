package com.simulation.people;

import com.Feudalizer;

import com.base.DateMutableEntity;
import com.GlobalVars;
import com.base.ObjectType;
import com.base.StateChangeKey;
import com.base.reference.DMEReference;
import com.base.reference.StateReference;
import com.base.timeline.TimelineState;
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
        HEAD_OF_FAMILY(1, MemberType.HEAD),
        SPOUSE(1, MemberType.PARTNER),
        EX_SPOUSE(1, MemberType.PARTNER),
        LOVER(1, MemberType.PARTNER),
        EX_LOVER(1, MemberType.PARTNER),
        CONCUBINE(1, MemberType.PARTNER),
        EX_CONCUBINE(1, MemberType.PARTNER),
        CHILD_BORN(99, MemberType.OFFSPRING),
        CHILD_BORN_DISOWNED(99, MemberType.OFFSPRING),
        CHILD_ADOPTED(99, MemberType.OFFSPRING),
        CHILD_ADOPTED_DISOWNED(99, MemberType.OFFSPRING);

        private final int maxInUnit;
        private final MemberType type;
        Relationship(int maxInUnit, MemberType type) {
            this.maxInUnit = maxInUnit;
            this.type = type;
        }

    }
    public enum MemberType {
        HEAD,
        PARTNER,
        OFFSPRING
    }

    //######
    private HashMap<DMEReference<BookCharacter>, Relationship> Members = new HashMap<>();
    private StateReference customName;
    public Family(LocalDate created, @Nullable LocalDate ended, DMEReference<BookCharacter> head) {
        super(created, ended, );
        Members = children;
    }

    public Family(DMEReference<Family> dme, LocalDate) {
        super(dme, saveData);
        Members = children;
    }

    public DMEReference<BookCharacter> getHeadofFamily() {
        return getMembersMatching(Relationship.HEAD_OF_FAMILY)[0];
    }
    public DMEReference<BookCharacter> getSpouse() {
        return getMembersMatching(Relationship.SPOUSE)[0];
    }
    private DMEReference<BookCharacter>[] getMembersMatching(Relationship rel) {
        return Members.entrySet().stream().filter(
                e -> e.getValue() == rel).map(Map.Entry::getKey).toArray(DMEReference[]::new);
    };
    public int getNumberOf(MemberType type) {
        return (int) Members.entrySet().stream().filter(e -> e.getValue().type == type).count();
    }
    public int getMaxAllowed(Relationship rel) {
        return rel.maxInUnit;
    }
    public boolean hasSpaceFor(Relationship rel) {
        return getNumberOf(rel.type) < getMaxAllowed(rel);
    }
    public void internal_AddMember(DMEReference<BookCharacter> member, Relationship rel) {
        if (hasSpaceFor(rel)) {
            Members.put(member, rel);
        } else {
            Feudalizer.LOGGER.error("Family " + this.toString() + " is at it's limit for members of type {}!", rel.type);
        }
    }
    @Override
    public ObjectType getObjectType() {
        return ObjectType.FAMILY;
    }

    @Override
    public void saveAdditional(JsonObject data) {

    }
    private static TimelineState<Family> buildInitial(Family dme, LocalDate created, @Nullable LocalDate ended, DMEReference<BookCharacter> head) {

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
