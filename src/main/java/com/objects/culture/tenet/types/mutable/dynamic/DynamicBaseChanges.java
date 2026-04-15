package com.objects.culture.tenet.types.mutable.dynamic;

import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineSingleChange;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonObject;

import java.time.LocalDate;

public class DynamicBaseChanges {
    public static class setDisplayName<T extends DynamicTenet<T>> extends TimelineSingleChange<T>{

        private String displayName;
        protected setDisplayName(DMEReference<? extends T> owner, LocalDate date) {
            super(owner, date);
        }
        public setDisplayName(DMEReference<? extends T> owner, LocalDate date, String displayName) {
            super(owner, date);
            this.displayName = displayName;
        }

        @Override
        protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
            entity.get().internalDisplayName(displayName);
        }

        @Override
        protected String getText() {
            return "";
        }

        @Override
        public void additionalSave(JsonObject data) {
            data.addProperty("display_name", displayName);
        }

        @Override
        public void additionalLoad(JsonObject data) {
            displayName = data.get("display_name").getAsString();
        }
    }
    public static class setDescription<T extends DynamicTenet<T>> extends TimelineSingleChange<T>{

        private String setDescription;
        protected setDescription(DMEReference<? extends T> owner, LocalDate date) {
            super(owner, date);
        }
        public setDescription(DMEReference<? extends T> owner, LocalDate date, String description) {
            super(owner, date);
            setDescription = description;
        }
        @Override
        protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
            entity.get().internalDescription(setDescription);
        }

        @Override
        protected String getText() {
            return "";
        }

        @Override
        public void additionalSave(JsonObject data) {
            data.addProperty("description", setDescription);
        }

        @Override
        public void additionalLoad(JsonObject data) {
            setDescription = data.get("description").getAsString();
        }
    }
}
