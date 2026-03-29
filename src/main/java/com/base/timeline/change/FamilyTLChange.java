package com.base.timeline.change;

import com.base.reference.DMEReference;
import com.base.timeline.TimelineState;
import com.base.timeline.change.conditions.Condition;
import com.base.timeline.change.conditions.ConditionResult;
import com.base.timeline.flags.StateError;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import com.simulation.people.BookCharacter;
import com.simulation.people.Family;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public abstract class FamilyTLChange extends TimelineChange<Family> {
    private ImmutableList<DMEReference<BookCharacter>> involved;


    protected FamilyTLChange(DMEReference<Family> owner, LocalDate date, DMEReference<BookCharacter>... involves) {
        super(owner, date);
        ArrayList<DMEReference<BookCharacter>> involved = new ArrayList<>();
        for (DMEReference<BookCharacter> ref : involves) {
            involved.add(ref);
        }
        this.involved = ImmutableList.copyOf(involved);
    }
    @Override
    protected ChangeTags[] getTags() {
        return new ChangeTags[0];
    }

    protected FamilyTLChange(DMEReference<Family> owner, LocalDate date) {
        super(owner, date);
    }
    @Override
    public HashSet<DMEReference<?>> getScope() {
        HashSet<DMEReference<?>> toReturn = new HashSet<>();
        toReturn.addAll(getOwner().link().getMembers());
        return toReturn;
    }
    public DMEReference<BookCharacter> getCharacter(int index) {
        return involved.get(index);
    }
    @Override
    public JsonObject onLoad(JsonObject data) {
        return null;
    }

    @Override
    public void saveAdditional(JsonObject data) {
        super
    }

    public static class addMember extends FamilyTLChange{
        private Family.Relationship relationship;
        public addMember(DMEReference<Family> owner, LocalDate date, DMEReference<BookCharacter> added, Family.Relationship relationship) {
            super(owner, date, added);
            this.relationship = relationship;
        }

        @Override
        protected TimelineState<Family> onApply(Family entity, boolean saveChangeToDiff) {
            DMEReference<BookCharacter> bc = getCharacter(0);
            entity.internal_AddMember(bc,relationship);
            return entity.
        }

        @Override
        public JsonObject onLoad(JsonObject data) {
            super.onLoad(data);
            relationship = Family.Relationship.valueOf(data.get("relationship").getAsString());
        }

        @Override
        public void saveAdditional(JsonObject data) {
            super.saveAdditional(data);
            data.addProperty("relationship",relationship.name());
        }

        @Override
        protected String getText() {
            return "";
        }

        @Override
        protected List<Condition<StateError, ?>> buildApplyConditions() {
            return List.of();
        }

        @Override
        protected List<Condition<ConditionResult.Nullify, ?>> buildNullifyConditions() {
            return List.of();
        }
    }
}
