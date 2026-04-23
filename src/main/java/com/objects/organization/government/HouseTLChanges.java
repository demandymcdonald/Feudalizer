package com.objects.organization.government;

import com.base.DMRegistry;
import com.base.ObjectType;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.error.StateError;
import com.base.reference.DMEReference;
import com.google.common.collect.HashMultimap;
import com.google.gson.JsonObject;
import com.objects.character.sentient.HumanCharacter;
import com.objects.family.Family;

import java.util.Optional;
import java.util.UUID;

import static com.base.timeline.error.Errors.DUPLICATE_STATE;

public class HouseTLChanges {

    public static class HeadOfHouseChanged extends TimelineChange<House, H> {
        DMEReference<HumanCharacter> oldHead;
        DMEReference<HumanCharacter> newHead;

        public HeadOfHouseChanged(HumanCharacter oldHead, HumanCharacter newHead) {
            this.oldHead = DMEReference.of(oldHead);
            this.newHead = DMEReference.of(newHead);
        }
        public HeadOfHouseChanged(UUID oldHead, UUID newHead) {
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

        public VassalHouseAdded(House vassalHouse) {
            this.vassalHouse = DMEReference.of(vassalHouse);
        }
        public VassalHouseAdded(UUID vassalHouse) {
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

        public VassalHouseRemoved(House vassalHouse) {
            this.vassalHouse = DMEReference.of(vassalHouse);
        }
        public VassalHouseRemoved(UUID vassalHouse) {
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

        public FamilyAdded(Family family) {
            this.family = DMEReference.of(family);
        }
        public FamilyAdded(UUID family) {
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

        public FamilyRemoved(Family family) {
            this.family = DMEReference.of(family);
        }
        public FamilyRemoved(UUID family) {
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
        DMEReference<HumanCharacter> retainer;
        House.RetainerType retainerType;

        public RetainerAdded(HumanCharacter retainer, House.RetainerType retainerType) {
            this.retainer = DMEReference.of(retainer);
            this.retainerType = retainerType;
        }
        public RetainerAdded(UUID retainer, House.RetainerType retainerType) {
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
        DMEReference<HumanCharacter> retainer;

        public RetainerRemoved(HumanCharacter retainer) {
            this.retainer = DMEReference.of(retainer);
        }
        public RetainerRemoved(UUID retainer) {
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
