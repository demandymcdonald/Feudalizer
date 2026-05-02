package com.base.datemutable.timeline.change.display;

import com.base.datemutable.DateMutableEntity;
import com.base.datemutable.timeline.change.varswap.TimelineVarChange;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.state.TimelineState;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.utilities.serialization.CompressString;

import java.time.LocalDate;
public class DisplayChanges {
    public static class ID<T extends DateMutableEntity<T> & ITLDisplayable<T>> extends TimelineVarChange<T,String> {
        protected ID(DMEReference<T> owner, LocalDate date, String changed) {
            super(owner, date, changed);
        }

        protected ID(DMEReference<T> owner, LocalDate date) {
            super(owner, date);
        }

        @Override
        public String getCurrent(T owner) {
            return owner.getDisplayID();
        }

        @Override
        public void setNew(T entity, String newValue) {
            entity.getDisplayable().internalID(newValue);
        }

        @Override
        public void onVariableLink(T entity, String changed, String former) {}

        @Override
        protected JsonElement serializeO(String o) {
            return new JsonPrimitive(o);
        }

        @Override
        protected String deserializeO(JsonElement json) {
            return json.getAsString();
        }
    }
    public static class Name<T extends DateMutableEntity<T> & ITLDisplayable<T>> extends TimelineVarChange<T,String> {
        private String changed;
        private String previous;

        protected Name(DMEReference<T> owner, LocalDate date, String changed) {
            super(owner, date,changed);
        }

        @Override
        public String getCurrent(T owner) {
            return owner.getDisplayable().getName();
        }

        @Override
        public void setNew(T entity, String newValue) {
            entity.getDisplayable().internalName(newValue);
        }

        @Override
        public void onVariableLink(T entity, String changed, String former) {

        }

        @Override
        protected JsonElement serializeO(String o) {
            return new JsonPrimitive(o);
        }

        @Override
        protected String deserializeO(JsonElement json) {
            return json.getAsString();
        }
    }
        public static class Description<T extends DateMutableEntity<T> & ITLDisplayable<T>> extends TimelineVarChange<T,String> {
            protected Description(DMEReference<T> owner, LocalDate date, String changed) {
                super(owner, date, changed);

            }

            protected Description(DMEReference<T> owner, LocalDate date) {
                super(owner, date);
            }


            @Override
            public String getCurrent(T owner) {
                return owner.getDisplayable().getDescription();
            }

            @Override
            public void setNew(T entity, String newValue) {
                entity.getDisplayable().internalDescription(newValue);
            }

            @Override
            public void onVariableLink(T entity, String changed, String former) {

            }

            @Override
            protected JsonElement serializeO(String o) {
                return new JsonPrimitive(o);
            }

            @Override
            protected String deserializeO(JsonElement json) {
                return json.getAsString();
            }
        }
    }
