package com.objects.culture.tenet.dynamic.change;

import com.Global.*;
import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineSingleChange;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonObject;
import com.objects.CauseOfEnd;
import com.objects.culture.Culture;
import com.objects.culture.tenet.dynamic.DynamicTenet;

import java.time.LocalDate;

public class Boundary {
    public static class Founding<T extends DynamicTenet<T>> extends TimelineSingleChange<T> {

        public Founding(DMEReference<T> tenet, LocalDate date) {
            super(tenet, date);
        }

        @Override
        protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {

        }

        @Override
        protected String getText() {
            return getOwner().get().getFull()+" was formed on " + getStart() + ".";
        }

        @Override
        public void additionalSave(JsonObject data) {

        }

        @Override
        public void additionalLoad(JsonObject data) {

        }
    }
    public static class Disbanding<T extends DynamicTenet<T>> extends TimelineSingleChange<T> {
        private CauseOfEnd<? super T> cause;
        public Disbanding(DMEReference<T> tenet, LocalDate date, CauseOfEnd<? super T> cOd) {
            super(tenet, date);
            cause = cOd;
        }

        @Override
        protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {

        }

        @Override
        protected String getText() {
            return getOwner().get().getFull()+" was disbanded on " + getStart() + " due to " + cause + ".";
        }

        @Override
        public void additionalSave(JsonObject data) {
            data.addProperty("cause", cause.getID());
        }

        @Override
        public void additionalLoad(JsonObject data) {
            cause = CauseOfEnd.get(data.get("cause").getAsString());
        }
    }
}
