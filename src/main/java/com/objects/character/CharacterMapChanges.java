package com.objects.character;

import com.base.reference.DMEReference;
import com.base.timeline.TimelineState;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.TimelineMapChange;
import com.base.timeline.change.conditions.Condition;
import com.base.timeline.change.conditions.ConditionResult;
import com.base.timeline.flags.StateError;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.objects.character.opinion.Opinion;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CharacterMapChanges {


    public static class OpinionChange extends TimelineMapChange<OpinionChange, UUID, Opinion,BookCharacter>{
        public OpinionChange(DMEReference<BookCharacter> owner, LocalDate date) {
            super(owner, date);
        }

        @Override
        protected void onApply(BookCharacter entity, TimelineState<BookCharacter> currentState) {
            Map<UUID,Opinion> map = getFullMap();
            entity.getOpinions().clear();
            entity.getOpinions().putAll(map);
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
        protected ChangeTags[] getTags() {
            return new ChangeTags[0];
        }
        @Override
        public boolean shouldSandbox(){
            return true;
        }

        @Override
        protected String getText() {
            return "";
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
        public void additionalLoad(JsonObject data) {

        }

        @Override
        public void additionalSave(JsonObject data) {

        }

        @Override
        protected JsonElement serializeV(Opinion opinion) {
            return opinion.toJson();
        }

        @Override
        protected JsonElement serializeK(UUID uuid) {
            return new JsonPrimitive(uuid.toString());
        }

        @Override
        protected Opinion deserializeV(JsonElement m) {
            return new Opinion(m.getAsJsonObject());
        }

        @Override
        protected UUID deserializeK(JsonElement m) {
            return UUID.fromString(m.getAsString());
        }
    }
























}
