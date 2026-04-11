package com.objects.family;

import com.base.reference.ComplexReference;
import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.multi.TimelineMapChange;
import com.base.timeline.change.condition.apply.ApplyCondition;
import com.base.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.timeline.change.condition.nullify.NullifyCondition;
import com.base.timeline.state.TimelineState;
import com.base.timeline.error.StateError;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.objects.character.human.HumanCharacter;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.*;

import static com.objects.family.Family.Relationship.CHILD_BORN;
import static com.objects.family.Family.Relationship.CHILD_BORN_DISOWNED;

public abstract class FamilyTLChange {

    public static class MemberChange extends TimelineMapChange<MemberChange, DMEReference<HumanCharacter>, Family.Relationship, Family> {
        public MemberChange(DMEReference<Family> owner, LocalDate date, Pair<DMEReference<HumanCharacter>, Family.Relationship>... involves) {
            super(owner, date, involves);
        }

        @Override
        protected boolean hasEndingChanges() {
            return true;
        }

        @Override
        protected JsonElement kSerialize(DMEReference<HumanCharacter> ref) {
            return ref.serialize();
        }

        @Override
        protected DMEReference<HumanCharacter> kDeserialize(JsonElement m) {
            return DMEReference.deserialize(m.getAsJsonObject());
        }
        @Override
        protected JsonElement vSerialize(Family.Relationship relationship) {
            return new JsonPrimitive(relationship.name());
        }
        @Override
        protected Family.Relationship vDeserialize(JsonElement m) {
            return Family.Relationship.valueOf(m.getAsString());
        }



        @Override
        protected void onApply(DMEReference<? extends Family> entity, TimelineState<? extends Family> currentState) {

        }

        @Override
        public List<Class<TimelineChange<? super Family>>> oppositeChanges() {
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
        protected void applyConditions(List<ApplyCondition<? super Family>> list) {

        }

        @Override
        protected void nullifyConditions(List<NullifyCondition<? super Family>> list) {

        }

        @Override
        protected void deactivateConditions(List<DeactivateCondition<? super Family>> list) {

        }

        @Override
        public void additionalSave(JsonObject data) {

        }

        @Override
        public void additionalLoad(JsonObject data) {

        }
    }
    public static class IsAlreadyBioChild extends ApplyCondition<Family> {

        public IsAlreadyBioChild() {
            super("is_already_bio_child");
        }

        @Override
        protected Optional<StateError> doCheck(DMEReference<? extends Family> entity, TimelineChange<? extends Family> thisChange, TimelineChange<?> checkAgainst) {
            if(thisChange instanceof MemberChange mc){
                Map<DMEReference<HumanCharacter>, Family.Relationship> changeFragment = mc.getActiveChanges();
                if(changeFragment.containsValue(CHILD_BORN) || changeFragment.containsValue(CHILD_BORN_DISOWNED)) {
                    for (Map.Entry<DMEReference<HumanCharacter>, Family.Relationship> entry : changeFragment.entrySet()) {
                        if (entry.getValue() == CHILD_BORN || entry.getValue() == CHILD_BORN_DISOWNED) {
                            HumanCharacter character = entry.getKey().get();
                            if (character.getOriginFamily(false) != null || character.getOriginFamily(false) != entity.get()) {
                                return Optional.of(new StateError("family_already_has_bio_parents",new ComplexReference("{} already has bio parents",entry.getKey()),checkAgainst).addEndCancel());
                            }
                        }
                    }
                }
            }
            return Optional.empty();
        }

        @Override
        public ShouldRun whenToRun() {
            return ShouldRun.ONCE_PER_ENTITY;
        }
    }
}
