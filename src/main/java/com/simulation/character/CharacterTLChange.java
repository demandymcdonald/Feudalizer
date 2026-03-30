package com.simulation.character;

import com.base.timeline.TimelineState;
import com.base.timeline.change.BoundaryChange;
import com.base.timeline.change.TimelineChange;
import com.base.reference.DMEReference;
import com.base.timeline.change.conditions.Condition;
import com.base.timeline.change.conditions.ConditionResult;
import com.base.timeline.change.conditions.NullifyConditions;
import com.base.timeline.flags.StateError;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static com.base.timeline.change.conditions.NullifyConditions.NEVER_NULLIFY;

public abstract class CharacterTLChange extends TimelineChange<BookCharacter> {
    protected CharacterTLChange(DMEReference<BookCharacter> primary, LocalDate date) {
        super(primary,date);

    }

    @Override
    protected boolean containsMyTags(ChangeTags[] tags) {
        return super.containsMyTags(tags);
    }

    @Override
    protected boolean containsMyTags(TimelineChange<?> state) {
        return super.containsMyTags(state);
    }

    @Override
    protected ChangeTags[] getTags() {
        return new ChangeTags[0];
    }

    @Override
    public HashSet<DMEReference<?>> getScope() {
        HashSet<DMEReference<?>> toReturn = new HashSet<>();
        toReturn.add(this.getOwner());
        return toReturn;
    }

    //=================================================================================================================
    // Start of Subclasses
    //=================================================================================================================

    public static class Birth extends CharacterTLChange {
        public Birth(DMEReference<BookCharacter> primary, LocalDate date) {
            super(primary,date);
        }

        @Override
        protected void onApply(BookCharacter entity, TimelineState<BookCharacter> currentState) {

        }

        @Override
        public List<Class<? super BookCharacter>> oppositeChanges() {
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
        protected List<Condition<StateError, ? super BookCharacter>> buildApplyConditions() {
            return List.of();
        }

        @Override
        protected List<Condition<ConditionResult.Nullify, ? super BookCharacter>> buildNullifyConditions() {
            return List.of((Condition<ConditionResult.Nullify,? super BookCharacter>) NEVER_NULLIFY);
        }

        @Override
        public void loadMain(JsonObject object) {

        }

        @Override
        public void saveAdditional(JsonObject data) {

        }

        @Override
        public void loadAdditional(JsonObject data) {

        }
    }

}
