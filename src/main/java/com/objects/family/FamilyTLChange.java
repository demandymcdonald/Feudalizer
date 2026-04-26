package com.objects.family;

import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.TimelineChange;
import com.base.datemutable.timeline.change.multi.TimelineMapChange;
import com.base.datemutable.timeline.change.condition.apply.ApplyCondition;
import com.base.datemutable.timeline.error.StateError;

import com.objects.character.sentient.SentientCharacter;

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
