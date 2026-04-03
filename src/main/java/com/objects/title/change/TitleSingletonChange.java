package com.objects.title.change;

import com.Global.*;
import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineSingleChange;
import com.base.timeline.change.changes.TimelineChange;
import com.base.timeline.change.conditions.Condition;
import com.base.timeline.change.conditions.ConditionResult;
import com.base.timeline.error.StateError;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonObject;
import com.objects.character.BookCharacter;
import com.objects.character.LivingCreature;
import com.objects.title.Title;

import java.time.LocalDate;
import java.util.List;

public abstract class TitleSingletonChange<T extends Title<T>> extends TimelineSingleChange<T> {


    protected TitleSingletonChange(DMEReference<T> owner, LocalDate date) {
        super(owner, date);
    }

    public static class ChangeHolder<T extends Title<T>> extends TitleSingletonChange<T> {
        private DMEReference<BookCharacter> holder;
        private DMEReference<BookCharacter> old_holder;
        public ChangeHolder(DMEReference<T> owner, LocalDate date, DMEReference<BookCharacter> holder, DMEReference<BookCharacter> old_holder) {
            super(owner, date);
            this.holder = holder;
            this.old_holder = old_holder;
        }
        public ChangeHolder(DMEReference<T> owner, LocalDate date) {
            super(owner, date);
        }
        @Override
        protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
            entity.get().internalHolder(holder);
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
            if(old_holder != null){
                return "Changed title holder from " + old_holder.get().getFullName() + " to " + holder.get().getFullName();
            }
            return "Changed title holder to " + holder.get().getFullName();
        }

        @Override
        protected List<Condition<StateError, ? super T>> buildApplyConditions() {
            return List.of();
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
            data.add("holder", holder.serialize());
            data.add("old_holder", old_holder.serialize());
        }

        @Override
        public void additionalLoad(JsonObject data) {
            holder = DMEReference.deserialize(data.get("holder").getAsJsonObject());
            old_holder = DMEReference.deserialize(data.get("old_holder").getAsJsonObject());
        }
    }


}
