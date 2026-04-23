package com.objects.character.sentient.change;

import com.base.reference.DMEReference;
import com.base.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.timeline.change.condition.nullify.NullifyCondition;
import com.base.timeline.change.multi.MiddlemanMap;
import com.base.timeline.change.multi.TimelineListChange;
import com.base.timeline.change.multi.TimelineMapChange;
import com.base.timeline.change.multi.TimelineSetChange;
import com.base.timeline.change.multi.condition.MultiCondition;
import com.base.timeline.change.multi.wrapper.TLMap;
import com.base.timeline.change.multi.wrapper.TLSet;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.objects.character.opinion.Opinion;
import com.objects.character.sentient.SentientCharacter;
import com.objects.organization.education.Education;
import com.objects.organization.education.EducationInstance;
import com.utilities.id.SimpleUUID;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class SentientMapChange {
    public static class OpinionMapChange<T extends SentientCharacter<T>> extends
            TimelineMapChange<OpinionMapChange<T>, SimpleUUID, Opinion, UUID,T>{
        public OpinionMapChange(DMEReference<? extends T> owner, LocalDate date, Map<SimpleUUID, Opinion> initial) {
            super(owner, date, initial);
        }

        public OpinionMapChange(DMEReference<? extends T> owner, LocalDate date) {
            super(owner, date);
        }

        @Override
        public void setRuntimeMap(TLMap<SimpleUUID, Opinion> map) {
            getOwner().get().internalSetOpinionMap(map);
        }

        @Override
        public TLMap<SimpleUUID, Opinion> getRuntimeMap() {
            return getOwner().get().getOpinion();
        }


        @Override
        public boolean hasEndingChanges() {
            return false;
        }

        @Override
        public void conditionsWipeForward(List<MultiCondition<OpinionMapChange<T>, SimpleUUID, Opinion, UUID, T>> current) {

        }

        @Override
        public void conditionsWipeBackward(List<MultiCondition<OpinionMapChange<T>, SimpleUUID, Opinion, UUID, T>> current) {

        }

        @Override
        protected JsonElement kSerialize(SimpleUUID simpleUUID) {
            return new JsonPrimitive(simpleUUID.getID().toString());
        }

        @Override
        protected SimpleUUID kDeserialize(JsonElement o) {
            return new SimpleUUID(UUID.fromString(o.getAsString()));
        }

        @Override
        protected JsonElement vSerialize(Opinion opinion) {
            return opinion.toJson();
        }

        @Override
        protected Opinion vDeserialize(JsonElement o) {
            return new Opinion(o.getAsJsonObject());
        }

        @Override
        protected JsonElement iSerialize(UUID uuid) {
            return new JsonPrimitive(uuid.toString());
        }

        @Override
        protected UUID iDeserialize(JsonElement o) {
            return UUID.fromString(o.getAsString());
        }

        @Override
        protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {

        }

        @Override
        protected String getText() {
            return "character_map_change";
        }



        @Override
        public OpinionMapChange<T> getEmptyChange(DMEReference<? extends T> owner, LocalDate date) {
            return new OpinionMapChange<>(owner, date);
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
    public static class EducationListChange<T extends SentientCharacter<T>> extends TimelineSetChange<EducationListChange<T>, EducationInstance,String,T> {
        protected EducationListChange(DMEReference<? extends T> owner, LocalDate date) {
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
