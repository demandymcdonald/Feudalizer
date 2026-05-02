package com.objects.family;

import com.base.datemutable.DateMutableEntity;
import com.base.datemutable.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.datemutable.timeline.change.multi.condition.MultiCondition;
import com.base.datemutable.timeline.change.multi.wrapper.TLMap;
import com.base.datemutable.timeline.state.TimelineState;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.TimelineChange;
import com.base.datemutable.timeline.change.multi.TimelineMapChange;
import com.base.datemutable.timeline.change.condition.apply.ApplyCondition;
import com.base.datemutable.timeline.error.StateError;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.objects.character.sentient.SentientCharacter;
import com.utilities.id.Identifiable;

import java.time.LocalDate;
import java.util.*;

public abstract class FamilyTLChange {

    public static class MemberMapChange extends TimelineMapChange<MemberMapChange,DMEReference<? extends SentientCharacter<?>>,FamilyRelationship,UUID,Family> {

        protected MemberMapChange(DMEReference<? extends Family> owner, LocalDate date) {
            super(owner, date);
        }

        public MemberMapChange(DMEReference<? extends Family> owner, LocalDate date, Map<DMEReference<? extends SentientCharacter<?>>, FamilyRelationship> initial) {
            super(owner, date, initial);
        }

        @Override
        protected TLMap<DMEReference<? extends SentientCharacter<?>>, FamilyRelationship> getRuntimeMap(Family family) {
            return family.getRelationships();
        }

        @Override
        public void setRuntimeMap(Family family, TLMap<DMEReference<? extends SentientCharacter<?>>, FamilyRelationship> map) {
            family.internalSetRelationship(map);
        }

        @Override
        public boolean hasEndingChanges() {
            return false;
        }

        @Override
        public void conditionsWipeForward(List<MultiCondition<MemberMapChange, DMEReference<? extends SentientCharacter<?>>, FamilyRelationship, UUID, Family>> current) {

        }

        @Override
        public void conditionsWipeBackward(List<MultiCondition<MemberMapChange, DMEReference<? extends SentientCharacter<?>>, FamilyRelationship, UUID, Family>> current) {

        }

        @Override
        protected JsonElement kSerialize(DMEReference<? extends SentientCharacter<?>> dmeReference) {
            return dmeReference.serialize();
        }

        @Override
        protected DMEReference<? extends SentientCharacter<?>> kDeserialize(JsonElement o) {
            return DMEReference.deserialize(o);
        }

        @Override
        protected JsonElement vSerialize(FamilyRelationship familyRelationship) {
            return new JsonPrimitive(familyRelationship.getId());
        }

        @Override
        protected FamilyRelationship vDeserialize(JsonElement o) {
            return null;
        }

        @Override
        protected JsonElement iSerialize(UUID uuid) {
            return null;
        }

        @Override
        protected UUID iDeserialize(JsonElement o) {
            return null;
        }

        @Override
        protected void onApply(DMEReference<? extends Family> entity, TimelineState<? extends Family> currentState) {

        }

        @Override
        protected String getText() {
            return "";
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
