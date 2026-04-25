package com.objects.character.change;

import com.base.reference.DMEReference;
import com.base.timeline.change.startend.CreatedChange;
import com.base.timeline.change.startend.EndingChange;
import com.google.gson.JsonObject;
import com.objects.CauseOfEnd;
import com.objects.character.LivingCreature;

import java.time.LocalDate;

public class LivingCreatureChanges {

    public static class Birth<T extends LivingCreature<T>> extends CreatedChange<T> {
        DMEReference<? extends LivingCreature<?>> parentA;
        DMEReference<? extends LivingCreature<?>> parentB;
        public Birth(DMEReference<? extends T> owner, LocalDate date) {
            super(owner, date);
        }
        public Birth(DMEReference<? extends T> owner, LocalDate date, DMEReference<? extends LivingCreature<?>> parentA, DMEReference<? extends LivingCreature<?>> parentB) {
            super(owner, date);
            this.parentA = parentA;
            this.parentB = parentB;
        }

        public DMEReference<? extends LivingCreature<?>> getParentA() {
            return parentA;
        }

        public DMEReference<? extends LivingCreature<?>> getParentB() {
            return parentB;
        }

        @Override
        public void additionalLoad(JsonObject data) {
            super.additionalLoad(data);
            this.parentA = DMEReference.deserialize(data.get("parent_a").getAsJsonObject());
            this.parentB = DMEReference.deserialize(data.get("parent_b").getAsJsonObject());
        }

        @Override
        public void additionalSave(JsonObject data) {
            super.additionalSave(data);
            data.add("parent_a",parentA.serialize());
            data.add("parent_b",parentB.serialize());
        }
    }
    public static class Death<T extends LivingCreature<T>> extends EndingChange<T> {
        public Death(DMEReference<? extends T> owner, LocalDate date, CauseOfEnd<? super T> causeOfEnd) {
            super(owner, date, causeOfEnd);
        }
    }
}
