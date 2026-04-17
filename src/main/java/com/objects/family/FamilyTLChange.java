package com.objects.family;

import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.multi.MiddlemanMap;
import com.base.timeline.change.multi.TimelineMapChange;
import com.base.timeline.change.condition.apply.ApplyCondition;
import com.base.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.timeline.change.condition.nullify.NullifyCondition;
import com.base.timeline.state.TimelineState;
import com.base.timeline.error.StateError;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import com.objects.character.sentient.SentientCharacter;

import java.time.LocalDate;
import java.util.*;

public abstract class FamilyTLChange {

    public static class MemberChange extends TimelineMapChange<MemberChange, DMEReference<? extends SentientCharacter<?>>, Family.Relationship, UUID, Family> {

        protected MemberChange(DMEReference<? extends Family> owner, LocalDate date) {
            super(owner, date);
        }

        public MemberChange(DMEReference<? extends Family> owner, LocalDate date, Map<DMEReference<? extends SentientCharacter<?>>, Family.Relationship> initial) {
            super(owner, date, initial);
        }

        @Override
        public void setRuntimeMap(MiddlemanMap<DMEReference<? extends SentientCharacter<?>>, Family.Relationship, Family> map) {
            getOwner().get().internal_SetMap(map);
        }

        @Override
        public MiddlemanMap<DMEReference<? extends SentientCharacter<?>>, Family.Relationship, Family> getRuntimeMap() {
            return null;
        }

        @Override
        public boolean hasEndingChanges() {
            return false;
        }

        @Override
        protected JsonElement kSerialize(DMEReference<? extends SentientCharacter<?>> dmeReference) {
            return dmeReference.serialize();
        }

        @Override
        protected DMEReference<? extends SentientCharacter<?>> kDeserialize(JsonElement o) {
            return DMEReference.deserialize(o.getAsJsonObject());
        }

        @Override
        protected JsonElement vSerialize(Family.Relationship relationship) {
            return new JsonPrimitive(relationship.name());
        }

        @Override
        protected Family.Relationship vDeserialize(JsonElement o) {
            return Family.Relationship.valueOf(o.getAsString());
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
        protected void onApply(DMEReference<? extends Family> entity, TimelineState<? extends Family> currentState) {

        }

        @Override
        protected String getText() {
            return "";
        }

        @Override
        protected void nullifyConditions(List<NullifyCondition<? super Family>> list) {

        }

        @Override
        protected void deactivateConditions(List<DeactivateCondition<? super Family>> list) {

        }

        @Override
        public void additionalSave(JsonObject data) {

        }

        @Override
        public void additionalLoad(JsonObject data) {

        }
    }
    public static class IsAlreadyBioChild extends ApplyCondition<Family> {

        public IsAlreadyBioChild() {
            super("is_already_bio_child");
        }

        @Override
        protected Optional<StateError> doCheck(DMEReference<? extends Family> entity, TimelineChange<? extends Family> thisChange, TimelineChange<?> checkAgainst) {

            return Optional.empty();
        }

        @Override
        public ShouldRun whenToRun() {
            return ShouldRun.ONCE_PER_ENTITY;
        }
    }
}
