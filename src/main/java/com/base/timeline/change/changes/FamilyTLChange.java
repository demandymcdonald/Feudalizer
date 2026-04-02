package com.base.timeline.change.changes;

import com.base.reference.DMEReference;
import com.base.timeline.state.TimelineState;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.conditions.Condition;
import com.base.timeline.change.conditions.ConditionResult;
import com.base.timeline.flags.StateError;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.objects.character.BookCharacter;
import com.objects.people.Family;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.*;

public abstract class FamilyTLChange extends TimelineChange<Family> {
    private LinkedHashMap<DMEReference<BookCharacter>, Family.Relationship> involved = new LinkedHashMap<>();


    protected FamilyTLChange(DMEReference<Family> owner, LocalDate date, Pair<DMEReference<BookCharacter>, Family.Relationship>... involves) {
        super(owner, date);
        for (Pair<DMEReference<BookCharacter>, Family.Relationship> ref : involves) {
            involved.put(ref.getKey(),ref.getValue());
        }
        //this.involved = ImmutableList.copyOf(involved);
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
        toReturn.addAll(getOwner().get().getMembers());
        return toReturn;
    }
    public DMEReference<BookCharacter> getCharacter(int index) {
        List<DMEReference<BookCharacter>> list = new ArrayList<>(involved.keySet());
        return list.get(index);
    }
    public DMEReference<BookCharacter>[] getCharacter(Family.Relationship index) {
        List<DMEReference<BookCharacter>> list = new ArrayList<>();
        for (Map.Entry<DMEReference<BookCharacter>, Family.Relationship> entry : involved.entrySet()) {
            if (entry.getValue() == index) list.add(entry.getKey());
        }
        return list.toArray(new DMEReference[0]);
    }
    @Override
    public JsonObject additionalLoad(JsonObject data) {
        data.getAsJsonArray("FamilyTLData").forEach(o -> {
            JsonObject o2 = (JsonObject) o;
            involved.put(DMEReference.deserialize(o2.getAsJsonObject("character")), Family.Relationship.valueOf(o2.get("relationship").getAsString()));
        });
        return data;
    }

    @Override
    public void additionalSave(JsonObject data) {
        JsonArray o = new JsonArray();
        for (Map.Entry<DMEReference<BookCharacter>, Family.Relationship> entry : involved.entrySet()) {
            JsonObject o2 = new JsonObject();
            o2.add("character",entry.getKey().serialize());
            o2.addProperty("relationship",entry.getValue().name());
            o.add(o2);
        }
        data.add("FamilyTLData",o);
    }
    public static class MemberChange extends FamilyTLChange {
        public MemberChange(DMEReference<Family> owner, LocalDate date, Pair<DMEReference<BookCharacter>, Family.Relationship>... involves) {
            super(owner, date, involves);
        }
        @Override
        protected void onApply(Family entity, TimelineState<Family> currentState) {

        }

        @Override
        public List<Class<? extends TimelineChange<Family>>> oppositeChanges() {
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
        protected List<Condition<StateError, ?>> buildApplyConditions() {
            return List.of();
        }

        @Override
        protected List<Condition<ConditionResult.Nullify, ?>> buildNullifyConditions() {
            return List.of();
        }
    }
}
