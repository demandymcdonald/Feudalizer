package com.objects.character.sentient.change;

import com.base.reference.DMEReference;
import com.base.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.timeline.change.condition.nullify.NullifyCondition;
import com.base.timeline.change.multi.MiddlemanMap;
import com.base.timeline.change.multi.TimelineMapChange;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.objects.character.opinion.Opinion;
import com.objects.character.sentient.SentientCharacter;
import com.utilities.id.SimpleUUID;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SentientMapChange {
    public static class OpinionMapChange<T extends SentientCharacter<T,?>> extends
            TimelineMapChange<OpinionMapChange<T>, SimpleUUID, Opinion, UUID,T>{
        public OpinionMapChange(DMEReference<? extends T> owner, LocalDate date, Map<SimpleUUID, Opinion> initial) {
            super(owner, date, initial);
        }

        public OpinionMapChange(DMEReference<? extends T> owner, LocalDate date) {
            super(owner, date);
        }

        @Override
        public void setRuntimeMap(MiddlemanMap<SimpleUUID, Opinion, T> map) {
            getOwner().get().internalSetOpinions(map);
        }

        @Override
        public MiddlemanMap<SimpleUUID, Opinion, T> getRuntimeMap() {
            return getOwner().get().getOpinions();
        }

        @Override
        public boolean hasEndingChanges() {
            return false;
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
        protected void nullifyConditions(List<NullifyCondition<? super T>> list) {

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
