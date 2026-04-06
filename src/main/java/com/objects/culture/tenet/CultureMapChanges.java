package com.objects.culture.tenet;

import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.map.TimelineMapChange;
import com.base.timeline.change.condition.apply.ApplyCondition;
import com.base.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.timeline.change.condition.nullify.NullifyCondition;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.objects.culture.Culture;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.List;

public class CultureMapChanges {



    public static class TenetMapChange extends TimelineMapChange<TenetMapChange,Tenet<?>, Culture.TenetInstance,Culture> {

        protected TenetMapChange(DMEReference<Culture> owner, LocalDate date) {
            super(owner, date);
        }

        protected TenetMapChange(DMEReference<Culture> owner, LocalDate date, Pair<Tenet<?>, Culture.TenetInstance>... changes) {
            super(owner, date, changes);
        }

        @Override
        protected boolean hasEndingChanges() {
            return false;
        }


        @Override
        protected JsonElement serializeV(Culture.TenetInstance tenetInstance) {
            return null;
        }

        @Override
        protected void onApply(DMEReference<? extends Culture> entity, TimelineState<? extends Culture> currentState) {

        }

        @Override
        public List<Class<TimelineChange<? super Culture>>> oppositeChanges() {
            return List.of();
        }

        @Override
        public boolean isPositive() {
            return false;
        }

        @Override
        protected String getText() {
            return "";
        }

        @Override
        protected void applyConditions(List<ApplyCondition<? super Culture>> list) {

        }

        @Override
        protected void nullifyConditions(List<NullifyCondition<? super Culture>> list) {

        }

        @Override
        protected void deactivateConditions(List<DeactivateCondition<? super Culture>> list) {

        }
        @Override
        protected JsonElement serializeK(Tenet<?> tenet) {
            return null;
        }

        @Override
        protected Tenet<?> deserializeK(JsonElement m) {
            return null;
        }

        @Override
        protected Culture.TenetInstance deserializeV(JsonElement m) {
            return null;
        }

        @Override
        public void additionalSave(JsonObject data) {

        }

        @Override
        public void additionalLoad(JsonObject data) {

        }
    }
}
