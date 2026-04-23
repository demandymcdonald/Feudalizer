package com.objects.family;

import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.multi.MiddlemanMap;
import com.base.timeline.change.multi.TimelineMapChange;
import com.base.timeline.change.condition.apply.ApplyCondition;
import com.base.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.timeline.change.condition.nullify.NullifyCondition;
import com.base.timeline.state.TimelineState;
import com.base.timeline.error.StateError;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import com.objects.character.sentient.SentientCharacter;

import java.sql.Time;
import java.time.LocalDate;
import java.util.*;

public abstract class FamilyTLChange {

    public static class MemberMapChange extends TimelineMapChange<MemberMapChange,DMEReference<? extends SentientCharacter<?>>,FamilyR> {}
    public static class IsAlreadyBioChild extends ApplyCondition<Family> {

        public IsAlreadyBioChild() {
            super("is_already_bio_child");
        }

        @Override
        protected Optional<StateError> doCheck(DMEReference<? extends Family> entity, TimelineChange<? extends Family> thisChange, TimelineChange<?> checkAgainst) {

            return Optional.empty();
        }

        @Override
        public ShouldRun whenToRun() {
            return ShouldRun.ONCE_PER_ENTITY;
        }
    }
}
