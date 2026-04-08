package com.objects.culture.change;

import com.Global.*;
import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.TimelineSingleChange;
import com.base.timeline.change.condition.apply.ApplyCondition;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.objects.culture.Culture;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CultureSingleChanges {



    public static class SetParentChange extends TimelineSingleChange<Culture> {
        List<DMEReference<Culture>> parents;
        public SetParentChange(DMEReference<? extends Culture> owner, LocalDate date, DMEReference<Culture> parent) {
            super(owner, date);
            parents = new ArrayList<>(owner.get().getParentCultures());
            parents.add(parent);
        }

        @Override
        protected void onApply(DMEReference<? extends Culture> entity, TimelineState<? extends Culture> currentState) {
            entity.get().internalSetParent(parents);
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
        public void additionalSave(JsonObject data) {
            JsonArray array = new JsonArray();
            for (DMEReference<? extends Culture> parent : parents) {
                array.add(parent.serialize());
            }
            data.add("parents", array);
        }

        @Override
        public void additionalLoad(JsonObject data) {
            List<DMEReference<Culture>> parents = new ArrayList<>();
            if (data.has("parents")) {
                JsonArray array = data.getAsJsonArray("parents");
                for (JsonElement element : array) {
                    parents.add(DMEReference.deserialize(element.getAsJsonObject()));
                }
            }
            this.parents = parents;
        }
    }
}
