package com.objects.character;

import com.base.timeline.state.TimelineState;
import com.base.timeline.change.TimelineChange;
import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineSingleChange;
import com.base.condition.Condition;
import com.base.condition.ConditionResult;
import com.base.timeline.error.StateError;
import com.google.gson.JsonObject;
import com.objects.CauseOfEnd;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.base.timeline.change.condition.nullify.NullifyConditions.NEVER_NULLIFY;

public abstract class CharacterSingleChange extends TimelineSingleChange<BookCharacter> {
    protected CharacterSingleChange(DMEReference<BookCharacter> primary, LocalDate date) {
        super(primary,date);

    }


    //=================================================================================================================
    // Start of Subclasses
    //=================================================================================================================

    public static class Birth extends CharacterSingleChange {
        public Birth(DMEReference<BookCharacter> primary, LocalDate date) {
            super(primary,date);
        }


        @Override
        protected void onApply(DMEReference<? extends BookCharacter> entity, TimelineState<? extends BookCharacter> currentState) {

        }

        @Override
        public List<Class<TimelineChange<? super BookCharacter>>> oppositeChanges() {
            return List.of();
        }


        @Override
        public boolean isPositive() {
            return true;
        }


        @Override
        protected List<Condition<StateError, ? super BookCharacter>> buildApplyConditions() {
            return List.of();
        }

        @Override
        protected List<Condition<ConditionResult.Nullify, ? super BookCharacter>> buildNullifyConditions() {
            return List.of((Condition<ConditionResult.Nullify,? super BookCharacter>) NEVER_NULLIFY);
        }

        @Override
        protected List<Condition<StateError, ? super BookCharacter>> buildCanDeactivateConditions() {
            return List.of();
        }


        @Override
        protected String getText() {
            return getOwner().toString() + "was born.";
        }

        @Override
        public void additionalSave(JsonObject data) {

        }

        @Override
        public void additionalLoad(JsonObject data) {

        }
    }
    public static class Death extends CharacterSingleChange {
        CauseOfEnd cause;
        public Death(DMEReference<BookCharacter> primary, LocalDate date, CauseOfEnd cause) {
            super(primary,date);
            this.cause = cause;
        }

        @Override
        protected void onApply(BookCharacter entity, TimelineState<BookCharacter> currentState) {

        }

        @Override
        public List<Class<TimelineChange<? super BookCharacter>>> oppositeChanges() {
            return List.of();
        }


        @Override
        public boolean isPositive() {
            return true;
        }

        @Override
        protected List<Condition<StateError, ? super BookCharacter>> buildApplyConditions() {
            return List.of();
        }

        @Override
        protected List<Condition<ConditionResult.Nullify, ? super BookCharacter>> buildNullifyConditions() {
            return List.of((Condition<ConditionResult.Nullify,? super BookCharacter>) NEVER_NULLIFY);
        }
        @Override
        protected String getText() {
            return getOwner().toString() + "died ";
        }

        @Override
        public void additionalSave(JsonObject data) {
            cause.serialize(data);
        }

        @Override
        public void additionalLoad(JsonObject data) {
            cause = CauseOfEnd.fromJson(data);
        }
    }
    public static class setForename extends CharacterSingleChange {
        private String forename;
        private Optional<String> old;
        public setForename(DMEReference<BookCharacter> primary, LocalDate date, String forename) {
            super(primary,date);
            this.forename = forename;
            this.old = Optional.ofNullable(primary.get().getForename());
        }
        public setForename(DMEReference<BookCharacter> primary, LocalDate date) {
            super(primary,date);
        }
        @Override
        protected void onApply(BookCharacter entity, TimelineState<BookCharacter> currentState) {
            entity.internalSetForename(forename);
        }

        @Override
        public List<Class<TimelineChange<? super BookCharacter>>> oppositeChanges() {
            return List.of();
        }

        @Override
        public boolean isPositive() {
            return true;
        }

        @Override
        protected String getText() {
            String second = getOwner().get().getSurname();
            if(old.isPresent()){
                return  old.get() + " " +second + " changed their forename to: " + forename;
            } else {
                return forename + " " + second +" changed their forename";
            }
        }

        @Override
        protected List<Condition<StateError, ? super BookCharacter>> buildApplyConditions() {
            return List.of();
        }

        @Override
        protected List<Condition<ConditionResult.Nullify, ? super BookCharacter>> buildNullifyConditions() {
            return List.of();
        }

        @Override
        public void additionalSave(JsonObject data) {
            data.addProperty("forename",forename);
            if (old.isPresent()){
                data.addProperty("old",old.get());
                return;
            }
        }
        @Override
        public void additionalLoad(JsonObject data) {
            if(data.has("forename")){
                forename = data.get("forename").getAsString();
            }
            if(data.has("old")){
                old = Optional.of(data.get("old").getAsString());
            } else {
                old = Optional.empty();
            }
        }
    }
    public static class setSurname extends CharacterSingleChange {
        private String surname;
        private Optional<String> old;
        public setSurname(DMEReference<BookCharacter> primary, LocalDate date, String surname) {
            super(primary,date);
            this.surname = surname;
            this.old = Optional.ofNullable(primary.get().getForename());
        }
        public setSurname(DMEReference<BookCharacter> primary, LocalDate date) {
            super(primary,date);
        }
        @Override
        protected void onApply(BookCharacter entity, TimelineState<BookCharacter> currentState) {
            entity.internalSetSurname(surname);
        }

        @Override
        public List<Class<TimelineChange<? super BookCharacter>>> oppositeChanges() {
            return List.of();
        }

        @Override
        public boolean isPositive() {
            return true;
        }

        @Override
        protected String getText() {
            String second = getOwner().get().getSurname();
            if(old.isPresent()){
                return  second + " " +old.get() + " changed their surname to: " + surname;
            } else {
                return second + " " + old.get() +" changed their surname.";
            }
        }

        @Override
        protected List<Condition<StateError, ? super BookCharacter>> buildApplyConditions() {
            return List.of();
        }

        @Override
        protected List<Condition<ConditionResult.Nullify, ? super BookCharacter>> buildNullifyConditions() {
            return List.of();
        }

        @Override
        public void additionalSave(JsonObject data) {
            data.addProperty("surname",surname);
            if (old.isPresent()){
                data.addProperty("old",old.get());
                return;
            }
        }
        @Override
        public void additionalLoad(JsonObject data) {
                surname = data.get("surname").getAsString();
            if(data.has("old")){
                old = Optional.of(data.get("old").getAsString());
            } else {
                old = Optional.empty();
            }
        }
    }
    public static class setGender extends CharacterSingleChange {
        private BookCharacter.Gender gender;
        private Optional<BookCharacter.Gender> old;
        public setGender(DMEReference<BookCharacter> primary, LocalDate date, BookCharacter.Gender gender) {
            super(primary,date);
            this.gender = gender;
            this.old = Optional.ofNullable(primary.get().getGender());
        }
        public setGender(DMEReference<BookCharacter> primary, LocalDate date) {
            super(primary,date);
        }
        @Override
        protected void onApply(BookCharacter entity, TimelineState<BookCharacter> currentState) {
            entity.internalSetGender(gender);
        }

        @Override
        public List<Class<TimelineChange<? super BookCharacter>>> oppositeChanges() {
            return List.of();
        }

        @Override
        public boolean isPositive() {
            return true;
        }

        @Override
        protected String getText() {
            String name = getOwner().get().getFullName();
            if(old.isPresent()){
                return  name + " changed their gender from: "+old.get()+", to: " + gender.getFlavor() + ".";
            } else {
                return name + " changed their gender to: " + gender.getFlavor()+ ".";
            }
        }

        @Override
        protected List<Condition<StateError, ? super BookCharacter>> buildApplyConditions() {
            return List.of();
        }

        @Override
        protected List<Condition<ConditionResult.Nullify, ? super BookCharacter>> buildNullifyConditions() {
            return List.of();
        }

        @Override
        public void additionalSave(JsonObject data) {
            data.addProperty("gender",gender.name());
            if (old.isPresent()){
                data.addProperty("old",old.get().name());
                return;
            }
        }
        @Override
        public void additionalLoad(JsonObject data) {
            gender = BookCharacter.Gender.valueOf(data.get("gender").getAsString());
            if(data.has("old")){
                old = Optional.of(BookCharacter.Gender.valueOf(data.get("old").getAsString()));
            } else {
                old = Optional.empty();
            }
        }
    }
    public static class setOrientation extends CharacterSingleChange {
        private BookCharacter.Orientation orientation;
        private Optional<BookCharacter.Orientation> old;
        public setOrientation(DMEReference<BookCharacter> primary, LocalDate date, BookCharacter.Orientation orientation) {
            super(primary,date);
            this.orientation = orientation;
            this.old = Optional.ofNullable(primary.get().getOrientation());
        }
        public setOrientation(DMEReference<BookCharacter> primary, LocalDate date) {
            super(primary,date);
        }
        @Override
        protected void onApply(BookCharacter entity, TimelineState<BookCharacter> currentState) {
            entity.internalSetOrientation(orientation);
        }

        @Override
        public List<Class<TimelineChange<? super BookCharacter>>> oppositeChanges() {
            return List.of();
        }

        @Override
        public boolean isPositive() {
            return true;
        }

        @Override
        protected String getText() {
            String name = getOwner().get().getFullName();
            if(old.isPresent()){
                return  name + " changed their sexual orientation from: "+old.get()+", to: " + orientation.getFlavor() + ".";
            } else {
                return name + " changed their sexual orientation to: " + orientation.getFlavor()+ ".";
            }
        }

        @Override
        protected List<Condition<StateError, ? super BookCharacter>> buildApplyConditions() {
            return List.of();
        }

        @Override
        protected List<Condition<ConditionResult.Nullify, ? super BookCharacter>> buildNullifyConditions() {
            return List.of();
        }

        @Override
        public void additionalSave(JsonObject data) {
            data.addProperty("orientation",orientation.name());
            if (old.isPresent()){
                data.addProperty("old",old.get().name());
                return;
            }
        }
        @Override
        public void additionalLoad(JsonObject data) {
            orientation = BookCharacter.Orientation.valueOf(data.get("orientation").getAsString());
            if(data.has("old")){
                old = Optional.of(BookCharacter.Orientation.valueOf(data.get("old").getAsString()));
            } else {
                old = Optional.empty();
            }
        }
    }



}
