package com.objects.character.sentient.change;

import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineSingleChange;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonObject;
import com.objects.CauseOfEnd;
import com.objects.character.sentient.Gender;
import com.objects.character.sentient.SentientCharacter;
import com.objects.title.succession.rules.SuccessionEntry;

import java.time.LocalDate;

public class CharacterChanges {


    public static class Birth<T extends SentientCharacter<T>> extends TimelineSingleChange<T> {
        public Birth(DMEReference<? extends T> owner, LocalDate date) {
            super(owner, date);
        }

        @Override
        protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
            //Birth changes don't do anything, they're just for UI display stuff.
        }
        @Override
        protected String getText() {
            return "character_birth";
        }

        @Override
        public void additionalSave(JsonObject data) {

        }

        @Override
        public void additionalLoad(JsonObject data) {

        }
    }
    public static class Death<T extends SentientCharacter<T>> extends TimelineSingleChange<T> {
        CauseOfEnd<? super T> cause;
        public Death(DMEReference<? extends T> owner, LocalDate date) {
            super(owner, date);
        }
        public Death(DMEReference<? extends T> owner, LocalDate date, CauseOfEnd<? super T> cause) {
            super(owner, date);
            this.cause = cause;
        }

        @Override
        protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
            //Death changes don't do anything, they're just for UI display stuff.
        }
        @Override
        protected String getText() {
            return "character_death";
        }
        @Override
        public void additionalSave(JsonObject data) {
            data.addProperty("cause",cause.id());
        }

        @Override
        public void additionalLoad(JsonObject data) {
            CauseOfEnd.get(data.get("cause").getAsString());
        }
    }
    public static class SetForename<T extends SentientCharacter<T>> extends TimelineSingleChange<T> {
        private String newName;
        private String oldName;
        public SetForename(DMEReference<? extends T> owner, LocalDate date) {
            super(owner, date);
        }
        public SetForename(DMEReference<? extends T> owner, LocalDate date, String newName) {
            super(owner, date);
            this.newName = newName;
            this.oldName = owner.get().getForename();
        }

        @Override
        protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
            entity.get().internalSetForename(newName);
        }

        @Override
        protected String getText() {
            return "character_new_name";
        }

        @Override
        public void additionalSave(JsonObject data) {
            data.addProperty("new_name",newName);
            if (oldName != null) {
                data.addProperty("old_name",oldName);
            }
        }

        @Override
        public void additionalLoad(JsonObject data) {
            newName = data.get("new_name").getAsString();
            if(data.has("old_name")) {
                oldName = data.get("old_name").getAsString();
            }
        }
    }
    public static class SetSurname<T extends SentientCharacter<T>> extends TimelineSingleChange<T> {
        private String newName;
        private String oldName;
        public SetSurname(DMEReference<? extends T> owner, LocalDate date) {
            super(owner, date);
        }
        public SetSurname(DMEReference<? extends T> owner, LocalDate date, String newName) {
            super(owner, date);
            this.newName = newName;
            this.oldName = owner.get().getSurname();
        }

        @Override
        protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
            entity.get().internalSetSurname(newName);
        }

        @Override
        protected String getText() {
            return "character_new_name";
        }

        @Override
        public void additionalSave(JsonObject data) {
            data.addProperty("new_name",newName);
            if (oldName != null) {
                data.addProperty("old_name",oldName);
            }
        }

        @Override
        public void additionalLoad(JsonObject data) {
            newName = data.get("new_name").getAsString();
            if(data.has("old_name")) {
                oldName = data.get("old_name").getAsString();
            }
        }
    }
    public static class SetOrientation<T extends SentientCharacter<T>> extends TimelineSingleChange<T> {
        private SentientCharacter.Orientation newOrientation;
        private SentientCharacter.Orientation oldOrientation;
        public SetOrientation(DMEReference<? extends T> owner, LocalDate date) {
            super(owner, date);
        }
        public SetOrientation(DMEReference<? extends T> owner, LocalDate date, SentientCharacter.Orientation newGender) {
            super(owner, date);
            this.newOrientation = newGender;
            this.oldOrientation = owner.get().getOrientation();
        }

        @Override
        protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
            entity.get().internalSetOrientation(newOrientation);
        }

        @Override
        protected String getText() {
            return "character_new_name";
        }

        @Override
        public void additionalSave(JsonObject data) {
            data.addProperty("new_orientation", newOrientation.name());
            if (oldOrientation != null) {
                data.addProperty("old_orientation", oldOrientation.name());
            }
        }

        @Override
        public void additionalLoad(JsonObject data) {
            newOrientation = SentientCharacter.Orientation.valueOf(data.get("new_orientation").getAsString());
            if(data.has("old_orientation")) {
                oldOrientation = SentientCharacter.Orientation.valueOf(data.get("old_orientation").getAsString());
            }
        }
    }
    public static class SetGender<T extends SentientCharacter<T>> extends TimelineSingleChange<T> {
        private Gender newGender;
        private Gender oldGender;
        public SetGender(DMEReference<? extends T> owner, LocalDate date) {
            super(owner, date);
        }
        public SetGender(DMEReference<? extends T> owner, LocalDate date, Gender newGender) {
            super(owner, date);
            this.newGender = newGender;
            this.oldGender = owner.get().getGender();
        }

        @Override
        protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
            entity.get().internalSetGender(newGender);
        }

        @Override
        protected String getText() {
            return "character_new_name";
        }

        @Override
        public void additionalSave(JsonObject data) {
            data.addProperty("new_gender",newGender.name());
            if (oldGender != null) {
                data.addProperty("old_gender",oldGender.name());
            }
        }

        @Override
        public void additionalLoad(JsonObject data) {
            newGender = Gender.valueOf(data.get("new_gender").getAsString());
            if(data.has("old_gender")) {
                oldGender = Gender.valueOf(data.get("old_gender").getAsString());
            }
        }
    }
    public static class SetDefaultSuccession<T extends SentientCharacter<T>> extends TimelineSingleChange<T> {
        private SuccessionEntry<?> container;

        protected SetDefaultSuccession(DMEReference<? extends T> owner, LocalDate date) {
            super(owner, date);
        }
        public SetDefaultSuccession(DMEReference<? extends T> owner, LocalDate date, SuccessionEntry<?> container) {
            super(owner, date);
            this.container = container;
        }
        @Override
        protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
            entity.get().internalSetPreferredSuccession(container);
        }

        @Override
        protected String getText() {
            return "set_preferred_succession";
        }

        @Override
        public void additionalSave(JsonObject data) {
            data.add("default_container",container.serialize());
        }

        @Override
        public void additionalLoad(JsonObject data) {
            container = SuccessionEntry.fromJson(data.get("default_container").getAsJsonObject());
        }
    }
}
