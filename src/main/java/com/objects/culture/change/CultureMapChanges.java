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
import com.google.gson.JsonPrimitive;
import com.objects.culture.Culture;
import com.objects.culture.instance.CultureTenetInstance;
import com.objects.culture.tenet.types.Tenet;
import com.objects.culture.tenet.TenetManager;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class CultureMapChanges {



    public static class TenetMapChange extends TimelineMapChange<TenetMapChange,Tenet, CultureTenetInstance, Culture> {

        public TenetMapChange(DMEReference<Culture> owner, LocalDate date) {
            super(owner, date);
        }

        public TenetMapChange(DMEReference<Culture> owner, LocalDate date, Pair<Tenet, CultureTenetInstance>... changes) {
            super(owner, date, changes);
        }

        @Override
        protected Map<Tenet, CultureTenetInstance> getMapFromObject(DMEReference<? extends Culture> object) {
            return object.get().getTenets();
        }


        public CultureTenetInstance getFromFuture(ChangeID current, CultureTenetInstance currentInstance){
            Timeline<? extends Culture> timeline = getOwner().get().getTimeline();
            TimelineMapChange<?,?, ?,?> c = (TimelineMapChange<?,?, ?,?>) timeline.followBreadcrumb(current);
            Map<Tenet, CultureTenetInstance> m = TimelineMapChange.getFirstFromFuture(timeline, c, currentInstance.getTenet(), null);
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
        protected JsonElement serializeK(Tenet tenet) {
            return new JsonPrimitive(tenet.getID());
        }

        @Override
        protected JsonElement serializeV(CultureTenetInstance tenetInstance) {
            return tenetInstance.serialize();
        }

        @Override
        protected Tenet deserializeK(JsonElement m) {
            return TenetManager.getTenet(m.getAsString());
        }

        @Override
        protected CultureTenetInstance deserializeV(JsonElement m) {
            CultureTenetInstance t = new CultureTenetInstance();
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
