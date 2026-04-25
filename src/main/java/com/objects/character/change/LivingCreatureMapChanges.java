package com.objects.character.change;

import com.base.reference.DMEReference;
import com.base.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.timeline.change.multi.TimelineSetChange;
import com.base.timeline.change.multi.condition.MultiCondition;
import com.base.timeline.change.multi.wrapper.TLSet;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.objects.character.LivingCreature;
import com.objects.character.opinion.Opinion;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class LivingCreatureMapChanges {
    public static class OpinionMapChange<T extends LivingCreature<T>> extends TimelineSetChange<OpinionMapChange<T>, Opinion, String,T> {
        public OpinionMapChange(DMEReference<? extends T> owner, LocalDate date) {
            super(owner, date);
        }

        public OpinionMapChange(DMEReference<? extends T> owner, LocalDate date, Set<Opinion> initial) {
            super(owner, date, initial);
        }

        @Override
        protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {

        }

        @Override
        protected String getText() {
            return "opinion_map_changes";
        }

        @Override
        protected void deactivateConditions(List<DeactivateCondition<? super T>> list) {

        }

        @Override
        public OpinionMapChange<T> getEmptyChange(DMEReference<? extends T> owner, LocalDate date) {
            return new OpinionMapChange<>(owner, date);
        }

        @Override
        public void conditionsWipeForward(List<MultiCondition<OpinionMapChange<T>, Opinion, Boolean, String, T>> current) {

        }

        @Override
        public void conditionsWipeBackward(List<MultiCondition<OpinionMapChange<T>, Opinion, Boolean, String, T>> current) {

        }
        public void internalAddRemoved(String internalID) {
            this.internalAddRemoved(internalID);
        }
        @Override
        protected JsonElement kSerialize(Opinion opinion) {
            return opinion.toJson();
        }

        @Override
        protected Opinion kDeserialize(JsonElement o) {
            return Opinion.fromJson(o.getAsJsonObject());
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
        public TLSet<Opinion> getRuntimeSet() {
            return getOwner().get().getOpinionContainer().internalGetOpinions();
        }

        @Override
        public void setRuntimeSet(TLSet<Opinion> set) {
            getOwner().get().getOpinionContainer().internalSetOpinions(set);
        }


        @Override
        public void additionalSave(JsonObject data) {

        }

        @Override
        public void additionalLoad(JsonObject data) {

        }
    }
























}
