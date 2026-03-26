package com.base.timeline.change;

import com.base.DMRegistry;
import com.base.ObjectType;
import com.base.flags.Errors;
import com.base.flags.StateError;
import com.base.reference.DMEReference;
import com.google.common.collect.HashMultimap;
import com.google.gson.JsonObject;
import com.simulation.people.BookCharacter;
import com.simulation.people.CharacterState;
import com.simulation.title.Title;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static com.base.flags.Errors.TITLE_HOLDER_DEAD;
import static com.base.timeline.change.TimelineChange.ChangeTags.*;

public class TLChanges {
    /**
     * Represents a marriage change event within a timeline. It marks the relationship
     * change where two characters get married and provides functionality to manage
     * associated states or conflicts in the timeline.
     */
    public static class Marriage extends TimelineChange {
        DMEReference<BookCharacter> host;
        DMEReference<BookCharacter> spouse;

        public Marriage(UUID host, UUID spouse){
            this.host = DMEReference.of(DMRegistry.getCharacterManager().get(host));
            this.spouse = DMEReference.of(DMRegistry.getCharacterManager().get(spouse));
        }
        public Marriage (BookCharacter host, BookCharacter spouse){
            this.host = DMEReference.of(host);
            this.spouse = DMEReference.of(spouse);
        }

        @Override
        protected ChangeTags[] getTags() {
            return new ChangeTags[]{ChangeTags.RELATIONSHIP_CHANGE,MARRIAGE_CHANGE};
        }

        @Override
        public boolean canNullify(TimelineChange state) {
            if (containsMyTags(state.getTags())){
                return false;
            } else if ((state instanceof Divorce m) && ((m.host.equals(host) || m.fmrSpouse.equals(host)) && (m.host.equals(spouse) || m.fmrSpouse.equals(spouse)))){
                return true;
            } else if (state.equals(this)){
                return true;
            }
            return false;
        }

        @Override
        public Optional<StateError> checkConflict(TimelineChange state) {
            //TODO: The only thing I can think of that could be a conflict is if the new family is a different family
            // to the first, but that feels like something that'd be caught in the actual marriage function's logic:
            // i.e. FamilyManager pulls family matching both spouses automatically.. Maybe throw a polygamy flag?
            return Optional.empty();
        }

        @Override
        protected String getText() {
            return host.parse() + " married to " + spouse.parse() + ".";
        }
        @Override
        public boolean equals(Object obj) {
            return obj instanceof Marriage m && ((m.host.equals(host) && m.spouse.equals(spouse)) || (m.host.equals(spouse) && m.spouse.equals(host)));
        }
        @Override
        protected JsonObject toJson() {
            JsonObject o = new JsonObject();
            o.add("host",host.serialize());
            o.add("fmrSpouse",spouse.serialize());
            return o;
        }
    }
    /**
     * Represents a divorce event in a timeline. This class models the termination of a marriage
     * relationship between two characters within the timeline. The divorce event specifies the individual
     * initiating the divorce (host) and their former spouse (fmrSpouse). It overrides necessary methods
     * to handle its unique behavior in the timeline.
     *
     * This class extends `TimelineChange` and provides implementations for necessary behavior
     * such as serialization, tag handling, equality, conflict resolution, and nullification.
     */
    public static class Divorce extends TimelineChange {
        DMEReference<BookCharacter> host;
        DMEReference<BookCharacter> fmrSpouse;

        public Divorce(UUID host, UUID fmr_spouse){
            this.host = DMEReference.of(DMRegistry.getCharacterManager().get(host));
            this.fmrSpouse = DMEReference.of(DMRegistry.getCharacterManager().get(fmr_spouse));
        }
        public Divorce (BookCharacter host, BookCharacter fmr_spouse){
            this.host = DMEReference.of(host);
            this.fmrSpouse = DMEReference.of(fmr_spouse);
        }

        @Override
        protected ChangeTags[] getTags() {
            return new ChangeTags[]{ChangeTags.RELATIONSHIP_CHANGE,MARRIAGE_CHANGE};
        }

        @Override
        public boolean canNullify(TimelineChange state) {
            if (containsMyTags(state.getTags())){
                return false;
            } else if (state instanceof Marriage m && ((m.host.equals(host) || m.spouse.equals(host)) && (m.host.equals(fmrSpouse) || m.spouse.equals(fmrSpouse)))){
                return true;
            }else if (state.equals(this)){
                return true;
            }
            return false;
        }

        @Override
        public Optional<StateError> checkConflict(TimelineChange state) {
            //TODO Same deal here? Since divorce (outside of nullifcations) in practice only changes some UI stuff and
            // stops your former spouse from being listed, I don't think there are any conflicts here?
            return Optional.empty();
        }

        @Override
        protected String getText() {
            return host.parse() + " divorced from " + fmrSpouse.parse() + ".";
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Divorce m && ((m.host.equals(host) && m.fmrSpouse.equals(fmrSpouse)) || (m.host.equals(fmrSpouse) && m.fmrSpouse.equals(host)));
        }
        @Override
        protected JsonObject toJson() {
            JsonObject o = new JsonObject();
            o.add("host",host.serialize());
            o.add("fmrSpouse",fmrSpouse.serialize());
            return o;
        }
    }


    /**
     * Represents a change to the timeline where a character experiences death.
     * This class is responsible for defining the death event of a specific character
     * and ensuring consistency and conflict resolution in the timeline state.
     */
    public static class CharacterDeath extends TimelineChange<BookCharacter, CharacterState> {
        DMEReference<BookCharacter> dead;
        public CharacterDeath(UUID dead){
            this.dead = DMEReference.of(DMRegistry.getCharacterManager().get(dead));
        }
        public CharacterDeath (BookCharacter dead){
            this.dead = DMEReference.of(dead);
        }

        @Override
        public void onApply(BookCharacter state, CharacterState current) {
            if (state.getId().equals(dead.getUuid())){
                current.
            }
        }

        @Override
        protected ChangeTags[] getTags() {
            return new ChangeTags[]{FAMILY_MEMBERSHIP_CHANGE,CHARACTER_DEATH};
        }

        @Override
        public HashMultimap<ObjectType, JsonObject> getScope() {
            return null;
        }

        @Override
        public boolean canNullify(TimelineChange<?> state) {
            return false;
        }

        @Override
        public Optional<StateError> checkConflict(TimelineChange<?> state) {
            return Optional.of(new StateError(TITLE_HOLDER_DEAD));
        }

        @Override
        protected String getText() {
            return "%s died.";
        }

        @Override
        protected JsonObject toJson() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("dead", dead.serialize());
            return jsonObject;
        }
    }
    public static class UndoDeath extends TimelineChange {
        DMEReference<BookCharacter> dead;
        public UndoDeath(UUID dead){
            this.dead = DMEReference.of(DMRegistry.getCharacterManager().get(dead));
        }
        public UndoDeath (BookCharacter dead){
            this.dead = DMEReference.of(dead);
        }
        @Override
        protected ChangeTags[] getTags() {
            return new ChangeTags[]{FAMILY_MEMBERSHIP_CHANGE,CHARACTER_DEATH};
        }

        @Override
        public boolean canNullify(TimelineChange state) {
            return state instanceof CharacterDeath;
        }

        @Override
        public Optional<StateError> checkConflict(TimelineChange state) {
            return Optional.empty();
        }

        @Override
        protected String getText() {
            return "";
        }

        @Override
        protected JsonObject toJson() {
            return null;
        }
    }
        public static class ChildAdded extends TimelineChange {
            DMEReference<BookCharacter> child;

            public ChildAdded(UUID child) {
                this.child = DMEReference.of(DMRegistry.getCharacterManager().get(child));
            }
            public ChildAdded(BookCharacter child) {
                this.child = DMEReference.of(child);
            }

            @Override
            protected ChangeTags[] getTags() {
                return new ChangeTags[]{ChangeTags.FAMILY_MEMBERSHIP_CHANGE};
            }

            @Override
            public boolean canNullify(TimelineChange state) {
                if (containsMyTags(state.getTags())) {
                    return false;
                } else if (state instanceof ChildDeleted d && d.child.equals(child)) {
                    return true;
                } else if (state.equals(this)) {
                    return true;
                }
                return false;
            }

            @Override
            public Optional<StateError> checkConflict(TimelineChange state) {
                // No future state conflicts with a child having been born
                return Optional.empty();
            }

            @Override
            protected String getText() {
                return child.parse() + " was born.";
            }

            @Override
            public boolean equals(Object obj) {
                return obj instanceof ChildAdded c && c.child.equals(child);
            }

            @Override
            protected JsonObject toJson() {
                JsonObject o = new JsonObject();
                o.add("child", child.serialize());
                return o;
            }
        }

        public static class ChildDeleted extends TimelineChange {
            DMEReference<BookCharacter> child;

            public ChildDeleted(UUID child) {
                this.child = DMEReference.of(DMRegistry.getCharacterManager().get(child));
            }
            public ChildDeleted(BookCharacter child) {
                this.child = DMEReference.of(child);
            }

            @Override
            protected ChangeTags[] getTags() {
                return new ChangeTags[]{ChangeTags.FAMILY_MEMBERSHIP_CHANGE};
            }

            @Override
            public boolean canNullify(TimelineChange state) {
                if (containsMyTags(state.getTags())) {
                    return false;
                } else if (state instanceof ChildAdded a && a.child.equals(child)) {
                    return true;
                } else if (state.equals(this)) {
                    return true;
                }
                return false;
            }

            @Override
            public Optional<StateError> checkConflict(TimelineChange state) {
                // Deleting a child doesn't conflict with any other state changes
                return Optional.empty();
            }

            @Override
            protected String getText() {
                return child.parse() + " was removed from existence.";
            }

            @Override
            public boolean equals(Object obj) {
                return obj instanceof ChildDeleted d && d.child.equals(child);
            }

            @Override
            protected JsonObject toJson() {
                JsonObject o = new JsonObject();
                o.add("child", child.serialize());
                return o;
            }
        }
}
