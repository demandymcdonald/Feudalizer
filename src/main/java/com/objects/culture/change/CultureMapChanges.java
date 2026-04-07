package com.objects.culture.change;

import com.base.reference.DMEReference;
import com.base.timeline.Timeline;
import com.base.timeline.change.ChangeID;
import com.base.timeline.change.map.TimelineMapChange;
import com.base.timeline.change.condition.apply.ApplyCondition;
import com.base.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.timeline.change.condition.nullify.NullifyCondition;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.objects.culture.Culture;
import com.objects.culture.tenet.Tenet;
import com.objects.culture.tenet.TenetInstance;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class CultureMapChanges {



    public static class TenetMapChange extends TimelineMapChange<TenetMapChange,DMEReference<Tenet<?>>, TenetInstance, Culture> {

        public TenetMapChange(DMEReference<Culture> owner, LocalDate date) {
            super(owner, date);
        }

        public TenetMapChange(DMEReference<Culture> owner, LocalDate date, Pair<DMEReference<Tenet<?>>, TenetInstance>... changes) {
            super(owner, date, changes);
        }

        @Override
        protected Map<DMEReference<Tenet<?>>, TenetInstance> getMapFromObject(DMEReference<? extends Culture> object) {
            return object.get().getTenets();
        }


        public TenetInstance getFromFuture(ChangeID current, TenetInstance currentInstance){
            Timeline<? extends Culture> timeline = getOwner().get().getTimeline();
            TimelineMapChange<?,?, ?,?> c = (TimelineMapChange<?,DMEReference<Tenet<?>>, TenetInstance,?>) timeline.followBreadcrumb(current);
            Map<DMEReference<Tenet<?>>, TenetInstance> m = TimelineMapChange.getFirstFromFuture(timeline, c, currentInstance.getTenet(), null);
            return m.get(currentInstance.getTenet());
        }








        @Override
        protected boolean hasEndingChanges() {
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
        protected JsonElement serializeK(DMEReference<Tenet<?>> tenet) {
            return tenet.serialize();
        }

        @Override
        protected JsonElement serializeV(TenetInstance tenetInstance) {
            return tenetInstance.serialize();
        }

        @Override
        protected DMEReference<Tenet<?>> deserializeK(JsonElement m) {
            return DMEReference.deserialize(m.getAsJsonObject());
        }

        @Override
        protected TenetInstance deserializeV(JsonElement m) {
            TenetInstance t = new TenetInstance();
            t.deserialize(m.getAsJsonObject());
            return t;
        }

        @Override
        public void additionalSave(JsonObject data) {

        }

        @Override
        public void additionalLoad(JsonObject data) {

        }
    }
}
