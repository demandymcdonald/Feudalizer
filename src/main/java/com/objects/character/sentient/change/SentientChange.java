package com.objects.character.sentient.change;

import com.base.datemutable.timeline.change.startend.CreatedChange;
import com.base.datemutable.timeline.change.startend.EndingChange;
import com.base.datemutable.timeline.change.varswap.TimelineVarChange;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.TimelineSingleChange;
import com.base.datemutable.timeline.state.TimelineState;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.objects.CauseOfEnd;
import com.objects.character.sentient.Gender;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.tenet.interest.groups.Orientation;
import com.objects.succession.SuccessionPlan;

import java.time.LocalDate;

public class SentientChange {


    public static class Birth<T extends SentientCharacter<T>> extends CreatedChange<T> {
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

    }
    public static class Death<T extends SentientCharacter<T>> extends EndingChange<T> {
        CauseOfEnd<? super T> cause;
        public Death(DMEReference<? extends T> owner, LocalDate date) {
            super(owner, date);
        }
        public Death(DMEReference<? extends T> owner, LocalDate date, CauseOfEnd<? super T> cause) {
            super(owner, date,cause);
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
    public static class SetForename<T extends SentientCharacter<T>> extends TimelineVarChange<T,String> {

        public SetForename(DMEReference<? extends T> owner, LocalDate date) {
            super(owner, date);
        }
        public SetForename(DMEReference<? extends T> owner, LocalDate date, String newName) {
            super(owner, date,newName);
        }

        @Override
        public String getCurrent(T owner) {
            return owner.getForename();
        }

        @Override
        public void setNew(T entity, String newValue) {
            entity.internalSetForename(newValue);
        }

        @Override
        public void onVariableLink(T entity, String changed, String former) {

        }

        @Override
        protected JsonElement serializeO(String o) {
            return new JsonPrimitive(o);
        }

        @Override
        protected String deserializeO(JsonElement json) {
            return json.getAsString();
        }
    }
    public static class SetSurname<T extends SentientCharacter<T>> extends TimelineVarChange<T,String> {
        public SetSurname(DMEReference<? extends T> owner, LocalDate date) {
            super(owner, date);
        }

        public SetSurname(DMEReference<? extends T> owner, LocalDate date, String newName) {
            super(owner, date,newName);
        }

        @Override
        public String getCurrent(T owner) {
            return owner.getSurname();
        }

        @Override
        public void setNew(T entity, String newValue) {
            entity.internalSetSurname(newValue);
        }

        @Override
        public void onVariableLink(T entity, String changed, String former) {

        }

        @Override
        protected JsonElement serializeO(String o) {
            return new JsonPrimitive(o);
        }

        @Override
        protected String deserializeO(JsonElement json) {
            return json.getAsString();
        }


    }
    public static class SetOrientation<T extends SentientCharacter<T>> extends TimelineVarChange<T, SentientCharacter.Orientation> {
        public SetOrientation(DMEReference<? extends T> owner, LocalDate date) {
            super(owner, date);
        }
        public SetOrientation(DMEReference<? extends T> owner, LocalDate date, SentientCharacter.Orientation newOrientation) {
            super(owner, date,newOrientation);

        }
        @Override
        public SentientCharacter.Orientation getCurrent(T owner) {
            return owner.getOrientation();
        }

        @Override
        public void setNew(T entity, SentientCharacter.Orientation newValue) {
            entity.internalSetOrientation(newValue);
        }

        @Override
        public void onVariableLink(T entity, SentientCharacter.Orientation changed, SentientCharacter.Orientation former) {

        }

        @Override
        protected JsonElement serializeO(SentientCharacter.Orientation o) {
            return new JsonPrimitive(o.name());
        }

        @Override
        protected SentientCharacter.Orientation deserializeO(JsonElement json) {
            return SentientCharacter.Orientation.valueOf(json.getAsString());
        }




    }
    public static class SetGender<T extends SentientCharacter<T>> extends TimelineVarChange<T,Gender> {
        public SetGender(DMEReference<? extends T> owner, LocalDate date) {
            super(owner, date);
        }

        @Override
        public Gender getCurrent(T owner) {
            return owner.getGender();
        }

        @Override
        public void setNew(T entity, Gender newValue) {
            entity.internalSetGender(newValue);
        }

        public SetGender(DMEReference<? extends T> owner, LocalDate date, Gender newGender) {
            super(owner, date);
        }


        @Override
        public void onVariableLink(T entity, Gender changed, Gender former) {

        }

        @Override
        protected String getText() {
            return "character_new_gender";
        }

        @Override
        protected JsonElement serializeO(Gender o) {
            return new JsonPrimitive(o.name());
        }

        @Override
        protected Gender deserializeO(JsonElement json) {
            return Gender.valueOf(json.getAsString());
        }

    }
//
}
