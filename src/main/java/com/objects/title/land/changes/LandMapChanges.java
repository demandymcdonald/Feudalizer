package com.objects.title.land.changes;

import com.base.reference.DMEReference;
import com.base.timeline.change.condition.apply.ApplyCondition;
import com.base.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.timeline.change.condition.nullify.NullifyCondition;
import com.base.timeline.change.multi.TimelineMapChange;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.objects.culture.tenet.TenetManager;
import com.objects.culture.tenet.instance.LandTenetInstance;
import com.objects.culture.tenet.mutable.MutableTenet;
import com.objects.title.land.habitable.HabitableLand;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class LandMapChanges {

    public static class LandCultureInstanceChange<T extends HabitableLand<T>> extends TimelineMapChange<LandCultureInstanceChange<T>, MutableTenet, LandTenetInstance<T>, T> {

        protected LandCultureInstanceChange(DMEReference<T> owner, LocalDate date) {
            super(owner, date);
        }

        protected LandCultureInstanceChange(DMEReference<T> owner, LocalDate date, Pair<MutableTenet, LandTenetInstance<T>>... changes) {
            super(owner, date, changes);
        }

        @Override
        protected Map<MutableTenet, LandTenetInstance<T>> getMapFromObject(DMEReference<? extends T> object) {
            return object.get().getOpinions();
        }

        @Override
        protected boolean hasEndingChanges() {
            return true;
        }

        @Override
        protected JsonElement kSerialize(MutableTenet tenet) {
            return new JsonPrimitive(tenet.getDisplayID());
        }

        @Override
        protected MutableTenet kDeserialize(JsonElement m) {
            return TenetManager.getTenet(m.getAsString());
        }

        @Override
        protected LandTenetInstance<T> vDeserialize(JsonElement m) {
            return null;
        }

        @Override
        protected JsonElement vSerialize(LandTenetInstance<T> tLandTenetInstance) {
            return null;
        }

        @Override
        protected String getText() {
            return "";
        }

        @Override
        protected void applyConditions(List<ApplyCondition<? super T>> list) {

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
