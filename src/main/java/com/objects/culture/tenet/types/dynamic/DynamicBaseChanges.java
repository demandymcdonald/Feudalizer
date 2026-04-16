package com.objects.culture.tenet.types.dynamic;

import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.TimelineSingleChange;
import com.base.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.timeline.change.condition.nullify.NullifyCondition;
import com.base.timeline.change.multi.TimelineMap;
import com.base.timeline.change.multi.TimelineMapChange;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.objects.culture.tenet.TenetManager;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.reference.TenetReference;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    public static class TenetChildrenMapChange<T extends DynamicTenet<T>> extends TimelineMapChange<TenetChildrenMapChange<T>, TenetReference, TenetGroup, UUID,T>{

        public TenetChildrenMapChange(DMEReference<? extends T> owner, LocalDate date, Map<TenetReference, TenetGroup> initial) {
            super(owner, date, initial);
        }

        protected TenetChildrenMapChange(DMEReference<? extends T> owner, LocalDate date) {
            super(owner, date);
        }

        @Override
        public void setRuntimeMap(TimelineMap<TenetReference, TenetGroup, T> map) {
            getOwner().get().internalSetChildMap(map);
        }

        @Override
        public TimelineMap<TenetReference, TenetGroup, T> getRuntimeMap() {
            return getOwner().get().getChildren();
        }

        @Override
        public boolean hasEndingChanges() {
            return true;
        }

        @Override
        protected JsonElement kSerialize(TenetReference tenetReference) {
            return tenetReference.serialize();
        }

        @Override
        protected TenetReference kDeserialize(JsonElement o) {
            return TenetReference.deserialize(o.getAsJsonObject());
        }

        @Override
        protected JsonElement vSerialize(TenetGroup tenetGroup) {
            return new JsonPrimitive(tenetGroup.id());
        }

        @Override
        protected TenetGroup vDeserialize(JsonElement o) {
            return TenetManager.Group.get(o.getAsString());
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
            return "";
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
