package com.objects.family;

import com.Feudalizer;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.reference.StateReference;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.multi.TimelineMap;
import com.google.gson.JsonObject;
import com.objects.CauseOfEnd;
import com.objects.character.sentient.HumanCharacter;
import com.objects.character.sentient.SentientCharacter;
import com.utilities.number.DateUtilities;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.*;

/**
 * Represents a FamilyGroups entity which includes information about spouses, children, and
 * associated houses. The `FamilyGroups` class also provides utility functions to manage
 * relationships and retrieve information about the family members.
 */
public class Family extends DateMutableEntity<Family> {

    public enum MemberType {
        HEAD,
        PARTNER,
        OFFSPRING
    }
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
        public MemberType getType() {
            return type;
        }
    }

    //######
    private TimelineMap<DMEReference<? extends SentientCharacter<?>>, Relationship,Family> members;
    private StateReference customName;
    private Family(UUID id, LocalDate created, @Nullable LocalDate ended, DMEReference<? extends SentientCharacter<?>> head) {
        super(created, LocalDate.MAX, List.of(

        );
    }
    public Family(DMEReference<Family> dme) {
        super(dme);
    }
    protected static Pair<DMEReference<? extends SentientCharacter<?>>,Relationship> buildPair(DMEReference<? extends SentientCharacter<?>> member, Relationship rel){
        return Pair.of(member,rel);
    }
    public DMEReference<? extends SentientCharacter<?>> getHeadofFamily() {
        return getMembersMatching(Relationship.HEAD_OF_FAMILY)[0];
    }
    public DMEReference<? extends SentientCharacter<?>> getPartner() {
        return getMembersMatching(Relationship.SPOUSE)[0];
    }
    public List<DMEReference<? extends SentientCharacter<?>>> getParents() {
        return Arrays.asList(getHeadofFamily(),getPartner());
    }
    private DMEReference<? extends SentientCharacter<?>>[] getMembersMatching(Relationship rel) {
        return members.entrySet().stream().filter(
                e -> e.getValue() == rel).map(Map.Entry::getKey).toArray(DMEReference[]::new);
    };
    public TimelineMap<DMEReference<? extends SentientCharacter<?>>,Relationship,Family> getFamilyMap(){
        return members;
    }
    public int getNumberOf(MemberType type) {
        return (int) members.entrySet().stream().filter(e -> e.getValue().type == type).count();
    }
    public int getMaxAllowed(Relationship rel) {
        return rel.maxInUnit;
    }
    public boolean hasSpaceFor(Relationship rel) {
        return getNumberOf(rel.type) < getMaxAllowed(rel);
    }
    public int getSlotsLeftFor(Relationship rel) {
        return getMaxAllowed(rel) - getNumberOf(rel.type);
    }
    public void internal_AddMember(DMEReference<? extends SentientCharacter<?>> member, Relationship rel) {
        if (hasSpaceFor(rel)) {
            members.put(member, rel);
        } else {
            Feudalizer.LOGGER.error("FamilyGroups " + this.toString() + " is at it's limit for members of type {}!", rel.type);
        }
    }
    public void internal_SetMap(TimelineMap<DMEReference<? extends SentientCharacter<?>>, Relationship,Family> map) {
        this.members = map;
    }
    @Override
    protected void onLink() {
        for (DMEReference<? extends SentientCharacter<?>> member : members.keySet()) {
            member.get().linkFamily(this,members.get(member));
        }
        calculateIsEnded();
    }

    @Override
    public void doDateChange() {

    }

    @Override
    public TimelineChange<Family> getBirthChange(DMEReference<Family> dme, LocalDate date) {
        return null;
    }

    @Override
    public TimelineChange<Family> getDeathChange(DMEReference<Family> dme, LocalDate date, CauseOfEnd<? super Family> cOd) {
        return null;
    }

    @Override
    public CauseOfEnd<? super Family> defaultDeathCause() {
        return null;
    }



    @Override
    public void additionalSave(JsonObject data) {}

    @Override
    public void additionalLoad(JsonObject data) {}
    public List<DMEReference<? extends SentientCharacter<?>>> getMembers(){
        return members.keySet().stream().toList();
    }

    private boolean calculateIsEnded(){
        LocalDate maxEnded = LocalDate.MIN;
        for (DMEReference<? extends SentientCharacter<?>> member : members.keySet()) {
            if (member.get().isAlive()) {
                return false;
            } else {
                maxEnded = DateUtilities.ceiling(member.get().getEnded(),maxEnded);
            }
        }
        this.getTimeline().moveEnd(maxEnded);
        return true;
    }






















}
