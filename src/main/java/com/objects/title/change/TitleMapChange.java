package com.objects.title.change;

import com.Global.*;
import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineMapChange;
import com.base.timeline.change.changes.TimelineChange;
import com.base.timeline.change.conditions.Condition;
import com.base.timeline.change.conditions.ConditionResult;
import com.base.timeline.error.StateError;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.objects.title.Title;

import java.time.LocalDate;
import java.util.List;

public class TitleMapChange {


    public static class SetRelationship<T extends Title<T>> extends TimelineMapChange<SetRelationship<T>,DMEReference<? extends Title<?>>,Title.Relationship,T>{

        protected SetRelationship(DMEReference<T> owner, LocalDate date) {
            super(owner, date);
        }

        @Override
        protected JsonElement serializeK(DMEReference<? extends Title<?>> dmeReference) {
            return dmeReference.serialize();
        }
        @Override
        protected DMEReference<? extends Title<?>> deserializeK(JsonElement m) {
            return DMEReference.deserialize(m.getAsJsonObject());
        }

        @Override
        protected Title.Relationship deserializeV(JsonElement m) {
            return Title.Relationship.valueOf(m.getAsString());
        }

        @Override
        protected JsonElement serializeV(Title.Relationship relationship) {
            return new JsonPrimitive(relationship.name());
        }

        @Override
        protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {

        }

        @Override
        public List<Class<TimelineChange<? super T>>> oppositeChanges() {
            return List.of();
        }

        @Override
        public boolean isPositive() {
            return true;
        }

        @Override
        protected String getText() {
            return "De Jure Drift";
        }

        @Override
        protected List<Condition<ConditionResult.Nullify, ? super T>> buildNullifyConditions() {
            return List.of();
        }

        @Override
        protected List<Condition<StateError, ? super T>> buildCanDeactivateConditions() {
            return List.of();
        }

        @Override
        public void additionalSave(JsonObject data) {

        }

        @Override
        public void additionalLoad(JsonObject data) {

        }
    }
}
