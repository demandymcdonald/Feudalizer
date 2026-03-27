package com.base.timeline.change;

import com.base.flags.StateError;
import com.base.reference.DMEReference;
import com.base.timeline.TimelineState;
import com.base.timeline.change.conditions.Condition;
import com.base.timeline.change.conditions.ConditionResult;
import com.google.gson.JsonObject;
import com.simulation.people.BookCharacter;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public abstract class CharacterTLChange extends TimelineChange<BookCharacter> {
    private final DMEReference<BookCharacter> primaryCharacter;
    private final Optional<DMEReference<BookCharacter>> secondaryCharacter;
    protected CharacterTLChange(DMEReference<BookCharacter> primary, DMEReference<BookCharacter> secondary, LocalDate date) {
        super(date);
        primaryCharacter = primary;
        secondaryCharacter = Optional.ofNullable(secondary);
    }
    public DMEReference<BookCharacter> getPrimary() {
        return primaryCharacter;
    }
    public DMEReference<BookCharacter> getSecondary() {
        return secondaryCharacter.orElse(null);
    }
    public JsonObject packageBaseJson(){
        JsonObject json = new JsonObject();
        json.add("primary", primaryCharacter.serialize() );
        if(secondaryCharacter.isEmpty()) return json;
        json.add("secondary", secondaryCharacter.get().serialize());
        return json;
    }
    public static Pair<DMEReference<BookCharacter>,DMEReference<BookCharacter>> unpackBaseJson(JsonObject object){
        DMEReference<BookCharacter> secondary;
        if (!object.has("secondary")){
            secondary = null;
        } else {
            secondary = DMEReference.deserialize(object.getAsJsonObject("primary"));
        }
        return Pair.of(DMEReference.deserialize(object.getAsJsonObject("primary")),secondary);
    }

    public static class CharacterDeath extends CharacterTLChange{

        private final CauseOfDeath death;
        public CharacterDeath(DMEReference<BookCharacter> primary, DMEReference<BookCharacter> secondary, CauseOfDeath death, LocalDate date) {
            super(primary, secondary, date);
            this.death = death;
        }

        @Override
        protected TimelineState<BookCharacter> onApply(BookCharacter entity, boolean saveChangeToDiff) {
            entity.is
        }

        @Override
        protected ChangeTags[] getTags() {
            return new ChangeTags[0];
        }

        @Override
        public HashSet<DMEReference<?>> getScope() {
            return null;
        }

        @Override
        protected String getText() {
            return "";
        }

        @Override
        protected JsonObject toJson() {
            JsonObject o = new JsonObject();
            o.addProperty("death", death.name());
            return o;
        }

        @Override
        protected List<Condition<StateError, ?>> buildApplyConditions() {
            return List.of();
        }

        @Override
        protected List<Condition<ConditionResult.Nullify, ?>> buildNullifyConditions() {
            return List.of();
        }
        public static CharacterDeath fromJson(LocalDate date, JsonObject json){
            Pair<DMEReference<BookCharacter>,DMEReference<BookCharacter>> pair = unpackBaseJson(json);
            return new CharacterDeath(pair.getLeft(),pair.getRight(), CauseOfDeath.valueOf(json.get("death").getAsString()),date);
        }
    }
}
