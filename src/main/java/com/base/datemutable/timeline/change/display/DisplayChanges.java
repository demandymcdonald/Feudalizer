package com.base.datemutable.timeline.change.display;

import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.TimelineSingleChange;
import com.base.datemutable.timeline.state.TimelineState;
import com.google.gson.JsonObject;
import com.utilities.serialization.CompressString;

import java.time.LocalDate;
public class DisplayChanges {
    public static class ID<T extends DateMutableEntity<T> & ITLDisplayable<T>> extends TimelineSingleChange<T> {
        private String changed;
        private String previous;
        protected ID(DMEReference<T> owner, LocalDate date, String changed) {
            super(owner, date);
            this.changed = changed;
            String previous = owner.get().getDisplayID();
            this.previous = previous != null ? previous : "";
        }

        protected ID(DMEReference<T> owner, LocalDate date) {
            super(owner, date);
        }

        @Override
        protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
            entity.get().getDisplayable().internalID(changed);
        }

        @Override
        protected String getText() {
            return "";
        }
        @Override
        public void additionalSave(JsonObject data) {
            data.addProperty("dc", CompressString.compress(previous)+"::"+CompressString.compress(changed));
        }

        @Override
        public void additionalLoad(JsonObject data) {
            String[] changes = data.get("dc").getAsString().split("::");
            previous = CompressString.decompress(changes[0]);
            changed = CompressString.decompress(changes[1]);
        }
    }
    public static class Name<T extends DateMutableEntity<T> & ITLDisplayable<T>> extends TimelineSingleChange<T> {
        private String changed;
        private String previous;

        protected Name(DMEReference<T> owner, LocalDate date, String changed) {
            super(owner, date);
            this.changed = changed;
            String previous = owner.get().getDisplayID();
            this.previous = previous != null ? previous : "";
        }

        protected Name(DMEReference<T> owner, LocalDate date) {
            super(owner, date);
        }

        @Override
        protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
            entity.get().getDisplayable().internalID(changed);
        }

        @Override
        protected String getText() {
            return "";
        }

        @Override
        public void additionalSave(JsonObject data) {
            data.addProperty("dc", CompressString.compress(previous) + "::" + CompressString.compress(changed));
        }

        @Override
        public void additionalLoad(JsonObject data) {
            String[] changes = data.get("dc").getAsString().split("::");
            previous = CompressString.decompress(changes[0]);
            changed = CompressString.decompress(changes[1]);
        }
    }
        public static class Description<T extends DateMutableEntity<T> & ITLDisplayable<T>> extends TimelineSingleChange<T> {
            private String changed;
            private String previous;
            protected Description(DMEReference<T> owner, LocalDate date, String changed) {
                super(owner, date);
                this.changed = changed;
                String previous = owner.get().getDisplayID();
                this.previous = previous != null ? previous : "";
            }

            protected Description(DMEReference<T> owner, LocalDate date) {
                super(owner, date);
            }

            @Override
            protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
                entity.get().getDisplayable().internalID(changed);
            }

            @Override
            protected String getText() {
                return "";
            }
            @Override
            public void additionalSave(JsonObject data) {
                data.addProperty("dc", CompressString.compress(previous)+"::"+CompressString.compress(changed));
            }

            @Override
            public void additionalLoad(JsonObject data) {
                String[] changes = data.get("dc").getAsString().split("::");
                previous = CompressString.decompress(changes[0]);
                changed = CompressString.decompress(changes[1]);
            }
        }
    }
