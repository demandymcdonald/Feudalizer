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
import com.objects.culture.tenet.types.Tenet;
import com.objects.title.land.HabitableLand;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class LandMapChanges {

    public static class LandCultureInstanceChange<T extends HabitableLand<T>> extends TimelineMapChange<LandCultureInstanceChange<T>, Tenet, LandTenetInstance<T>, T> {

        protected LandCultureInstanceChange(DMEReference<T> owner, LocalDate date) {
            super(owner, date);
        }

        protected LandCultureInstanceChange(DMEReference<T> owner, LocalDate date, Pair<Tenet, LandTenetInstance<T>>... changes) {
            super(owner, date, changes);
        }

        @Override
        protected Map<Tenet, LandTenetInstance<T>> getMapFromObject(DMEReference<? extends T> object) {
            return object.get().getOpinions();
        }

        @Override
        protected boolean hasEndingChanges() {
            return true;
        }

        @Override
        protected JsonElement kSerialize(Tenet tenet) {
            return new JsonPrimitive(tenet.getID());
        }

        @Override
        protected Tenet kDeserialize(JsonElement m) {
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
