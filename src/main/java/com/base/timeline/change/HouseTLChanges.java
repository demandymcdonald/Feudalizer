package com.base.timeline.change;

import com.base.DMRegistry;
import com.base.ObjectType;
import com.base.flags.StateError;
import com.base.reference.DMEReference;
import com.google.common.collect.HashMultimap;
import com.google.gson.JsonObject;
import com.simulation.people.BookCharacter;
import com.simulation.people.Family;
import com.simulation.people.House;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static com.base.flags.Errors.DUPLICATE_STATE;

public class HouseTLChanges {

    public static class HeadOfHouseChanged extends TimelineChange<House, H> {
        DMEReference<BookCharacter> oldHead;
        DMEReference<BookCharacter> newHead;

        public HeadOfHouseChanged(DMEReference<House> owningHouse, LocalDate date, BookCharacter oldHead, BookCharacter newHead) {
            super(date, owningHouse);
            this.oldHead = DMEReference.of(oldHead);
            this.newHead = DMEReference.of(newHead);
        }
        public HeadOfHouseChanged(DMEReference<House> owningHouse, LocalDate date, UUID oldHead, UUID newHead) {
            super(date, owningHouse);
            this.oldHead = DMEReference.of(DMRegistry.getCharacterManager().get(oldHead));
            this.newHead = DMEReference.of(DMRegistry.getCharacterManager().get(newHead));
        }

        @Override
        public void onApply(House state) {

        }

        @Override
        protected ChangeTags[] getTags() {
            return new ChangeTags[]{ChangeTags.HOUSE_EMPLOYMENT_CHANGE};
        }

        @Override
        public boolean canNullify(TimelineChange<?> state) {
            if (state.equals(this)) return true;
            // A head change nullifies another head change only if they swap back to the original
            if (state instanceof HeadOfHouseChanged h && h.newHead.equals(oldHead) && h.oldHead.equals(newHead)) return true;
            return false;
        }

        @Override
        public Optional<StateError> checkConflict(TimelineChange<?> state) {
            // Two head changes on the same date with different new heads is a conflict
            if (state instanceof HeadOfHouseChanged h && !h.newHead.equals(newHead)) {
                return Optional.of(new StateError(DUPLICATE_STATE));
            }
            return Optional.empty();
        }

        @Override
        public HashMultimap<ObjectType, JsonObject> getScope() {
            return null;
        }

        @Override
        protected String getText() {
            return "Head of house changed from " + oldHead.parse() + " to " + newHead.parse() + ".";
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof HeadOfHouseChanged h && h.oldHead.equals(oldHead) && h.newHead.equals(newHead);
        }

        @Override
        protected JsonObject toJson() {
            JsonObject o = new JsonObject();
            o.add("oldHead", oldHead.serialize());
            o.add("newHead", newHead.serialize());
            return o;
        }
    }

    public static class VassalHouseAdded extends TimelineChange<House> {
        DMEReference<House> vassalHouse;

        public VassalHouseAdded(DMEReference<House> owningHouse, LocalDate date, House vassalHouse) {
            super(date, owningHouse);
            this.vassalHouse = DMEReference.of(vassalHouse);
        }
        public VassalHouseAdded(DMEReference<House> owningHouse, LocalDate date, UUID vassalHouse) {
            super(date, owningHouse);
            this.vassalHouse = DMEReference.of(DMRegistry.getHouseManager().get(vassalHouse));
        }

        @Override
        public void onApply(House state) {
            state.
        }

        @Override
        protected ChangeTags[] getTags() {
            return new ChangeTags[]{ChangeTags.HOUSE_EMPLOYMENT_CHANGE};
        }

        @Override
        public HashMultimap<ObjectType, JsonObject> getScope() {
            return null;
        }

        @Override
        public boolean canNullify(TimelineChange state) {
            if (state.equals(this)) return true;
            if (state instanceof VassalHouseRemoved r && r.vassalHouse.equals(vassalHouse)) return true;
            return false;
        }

        @Override
        public Optional<StateError> checkConflict(TimelineChange state) {
            // Can't add the same vassal house twice without removing it first
            if (state instanceof VassalHouseAdded a && a.vassalHouse.equals(vassalHouse)) {
                return Optional.of(new StateError(DUPLICATE_STATE));
            }
            return Optional.empty();
        }

        @Override
        protected String getText() {
            return vassalHouse.parse() + " added as vassal house.";
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof VassalHouseAdded a && a.vassalHouse.equals(vassalHouse);
        }

        @Override
        protected JsonObject toJson() {
            JsonObject o = new JsonObject();
            o.add("vassalHouse", vassalHouse.serialize());
            return o;
        }
    }

    public static class VassalHouseRemoved extends TimelineChange {
        DMEReference<House> vassalHouse;

        public VassalHouseRemoved(DMEReference<House> owningHouse, LocalDate date, House vassalHouse) {
            super(date, owningHouse);
            this.vassalHouse = DMEReference.of(vassalHouse);
        }
        public VassalHouseRemoved(DMEReference<House> owningHouse, LocalDate date, UUID vassalHouse) {
            super(date, owningHouse);
            this.vassalHouse = DMEReference.of(DMRegistry.getHouseManager().get(vassalHouse));
        }

        @Override
        protected ChangeTags[] getTags() {
            return new ChangeTags[]{ChangeTags.HOUSE_EMPLOYMENT_CHANGE};
        }

        @Override
        public boolean canNullify(TimelineChange state) {
            if (state.equals(this)) return true;
            if (state instanceof VassalHouseAdded a && a.vassalHouse.equals(vassalHouse)) return true;
            return false;
        }

        @Override
        public Optional<StateError> checkConflict(TimelineChange state) {
            // If the removed vassal house receives titles implying vassalage after removal, flag it
            // For now leaving as empty — title conflict detection will live on the title side
            return Optional.empty();
        }

        @Override
        protected String getText() {
            return vassalHouse.parse() + " removed as vassal house.";
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof VassalHouseRemoved r && r.vassalHouse.equals(vassalHouse);
        }

        @Override
        protected JsonObject toJson() {
            JsonObject o = new JsonObject();
            o.add("vassalHouse", vassalHouse.serialize());
            return o;
        }
    }

    public static class FamilyAdded extends TimelineChange {
        DMEReference<Family> family;

        public FamilyAdded(DMEReference<House> owningHouse, LocalDate date, Family family) {
            super(date, owningHouse);
            this.family = DMEReference.of(family);
        }
        public FamilyAdded(DMEReference<House> owningHouse, LocalDate date, UUID family) {
            super(date, owningHouse);
            this.family = DMEReference.of(DMRegistry.getFamilyManager().get(family));
        }

        @Override
        protected ChangeTags[] getTags() {
            return new ChangeTags[]{ChangeTags.HOUSE_EMPLOYMENT_CHANGE};
        }

        @Override
        public boolean canNullify(TimelineChange state) {
            if (state.equals(this)) return true;
            if (state instanceof FamilyRemoved r && r.family.equals(family)) return true;
            return false;
        }

        @Override
        public Optional<StateError> checkConflict(TimelineChange state) {
            if (state instanceof FamilyAdded a && a.family.equals(family)) {
                return Optional.of(new StateError(DUPLICATE_STATE));
            }
            return Optional.empty();
        }

        @Override
        protected String getText() {
            return family.parse() + " joined house as direct member.";
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof FamilyAdded a && a.family.equals(family);
        }

        @Override
        protected JsonObject toJson() {
            JsonObject o = new JsonObject();
            o.add("family", family.serialize());
            return o;
        }
    }

    public static class FamilyRemoved extends TimelineChange {
        DMEReference<Family> family;

        public FamilyRemoved(DMEReference<House> owningHouse, LocalDate date, Family family) {
            super(date, owningHouse);
            this.family = DMEReference.of(family);
        }
        public FamilyRemoved(DMEReference<House> owningHouse, LocalDate date, UUID family) {
            super(date, owningHouse);
            this.family = DMEReference.of(DMRegistry.getFamilyManager().get(family));
        }

        @Override
        protected ChangeTags[] getTags() {
            return new ChangeTags[]{ChangeTags.HOUSE_EMPLOYMENT_CHANGE};
        }

        @Override
        public boolean canNullify(TimelineChange state) {
            if (state.equals(this)) return true;
            if (state instanceof FamilyAdded a && a.family.equals(family)) return true;
            return false;
        }

        @Override
        public Optional<StateError> checkConflict(TimelineChange state) {
            return Optional.empty();
        }

        @Override
        protected String getText() {
            return family.parse() + " removed from house.";
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof FamilyRemoved r && r.family.equals(family);
        }

        @Override
        protected JsonObject toJson() {
            JsonObject o = new JsonObject();
            o.add("family", family.serialize());
            return o;
        }
    }

    public static class RetainerAdded extends TimelineChange {
        DMEReference<BookCharacter> retainer;
        House.RetainerType retainerType;

        public RetainerAdded(DMEReference<House> owningHouse, LocalDate date, BookCharacter retainer, House.RetainerType retainerType) {
            super(date, owningHouse);
            this.retainer = DMEReference.of(retainer);
            this.retainerType = retainerType;
        }
        public RetainerAdded(DMEReference<House> owningHouse, LocalDate date, UUID retainer, House.RetainerType retainerType) {
            super(date, owningHouse);
            this.retainer = DMEReference.of(DMRegistry.getCharacterManager().get(retainer));
            this.retainerType = retainerType;
        }

        @Override
        protected ChangeTags[] getTags() {
            return new ChangeTags[]{ChangeTags.HOUSE_EMPLOYMENT_CHANGE};
        }

        @Override
        public boolean canNullify(TimelineChange state) {
            if (state.equals(this)) return true;
            if (state instanceof RetainerRemoved r && r.retainer.equals(retainer)) return true;
            return false;
        }

        @Override
        public Optional<StateError> checkConflict(TimelineChange state) {
            if (state instanceof RetainerAdded a && a.retainer.equals(retainer)) {
                return Optional.of(new StateError(DUPLICATE_STATE));
            }
            return Optional.empty();
        }

        @Override
        protected String getText() {
            return retainer.parse() + " added as " + retainerType + ".";
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof RetainerAdded a && a.retainer.equals(retainer) && a.retainerType == retainerType;
        }

        @Override
        protected JsonObject toJson() {
            JsonObject o = new JsonObject();
            o.add("retainer", retainer.serialize());
            o.addProperty("retainerType", retainerType.toString());
            return o;
        }
    }

    public static class RetainerRemoved extends TimelineChange {
        DMEReference<BookCharacter> retainer;

        public RetainerRemoved(DMEReference<House> owningHouse, LocalDate date, BookCharacter retainer) {
            super(date, owningHouse);
            this.retainer = DMEReference.of(retainer);
        }
        public RetainerRemoved(DMEReference<House> owningHouse, LocalDate date, UUID retainer) {
            super(date, owningHouse);
            this.retainer = DMEReference.of(DMRegistry.getCharacterManager().get(retainer));
        }

        @Override
        protected ChangeTags[] getTags() {
            return new ChangeTags[]{ChangeTags.HOUSE_EMPLOYMENT_CHANGE};
        }

        @Override
        public boolean canNullify(TimelineChange state) {
            if (state.equals(this)) return true;
            if (state instanceof RetainerAdded a && a.retainer.equals(retainer)) return true;
            return false;
        }

        @Override
        public Optional<StateError> checkConflict(TimelineChange state) {
            return Optional.empty();
        }

        @Override
        protected String getText() {
            return retainer.parse() + " removed as retainer.";
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof RetainerRemoved r && r.retainer.equals(retainer);
        }

        @Override
        protected JsonObject toJson() {
            JsonObject o = new JsonObject();
            o.add("retainer", retainer.serialize());
            return o;
        }
    }
}
