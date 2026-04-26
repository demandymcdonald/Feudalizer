package com.objects.character.sentient.change;

import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.datemutable.timeline.change.multi.TimelineSetChange;
import com.base.datemutable.timeline.change.multi.condition.MultiCondition;
import com.base.datemutable.timeline.change.multi.wrapper.TLSet;
import com.base.datemutable.timeline.state.TimelineState;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.objects.character.sentient.SentientCharacter;
import com.objects.organization.education.EducationInstance;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class SentientMapChange {
    public static class EducationListChange<T extends SentientCharacter<T>> extends TimelineSetChange<EducationListChange<T>, EducationInstance,String,T> {
        public EducationListChange(DMEReference<? extends T> owner, LocalDate date) {
            super(owner, date);
        }

        protected EducationListChange(DMEReference<? extends T> owner, LocalDate date, Set<EducationInstance> initial) {
            super(owner, date, initial);
        }

        @Override
        public TLSet<EducationInstance> getRuntimeSet() {
            return getOwner().get().getEducation();
        }

        @Override
        public void setRuntimeSet(TLSet<EducationInstance> set) {
            getOwner().get().internalSetEducation(set);
        }

        @Override
        public EducationListChange<T> getEmptyChange(DMEReference<? extends T> owner, LocalDate date) {
            return new EducationListChange<>(owner, date);
        }

        @Override
        public void conditionsWipeForward(List<MultiCondition<EducationListChange<T>, EducationInstance, Boolean, String, T>> current) {

        }

        @Override
        public void conditionsWipeBackward(List<MultiCondition<EducationListChange<T>, EducationInstance, Boolean, String, T>> current) {

        }

        @Override
        protected JsonElement kSerialize(EducationInstance educationInstance) {
            return educationInstance.toJson();
        }

        @Override
        protected EducationInstance kDeserialize(JsonElement o) {
            return EducationInstance.fromJson(o);
        }

        @Override
        protected JsonElement iSerialize(String s) {
            return new JsonPrimitive(s);
        }

        @Override
        protected String iDeserialize(JsonElement o) {
            return o.getAsString();
        }

        @Override
        protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {

        }

        @Override
        protected String getText() {
            return "";
        }

        @Override
        protected void deactivateConditions(List<DeactivateCondition<? super T>> list) {

        }

        @Override
        public void additionalSave(JsonObject data) {

        }

        @Override
        public void additionalLoad(JsonObject data) {

        }
    }
}
