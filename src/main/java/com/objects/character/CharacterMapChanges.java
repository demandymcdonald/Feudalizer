package com.objects.character;

import com.base.reference.DMEReference;
import com.base.timeline.Timeline;
import com.base.timeline.change.ChangeID;
import com.base.timeline.state.TimelineState;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.multi.TimelineMapChange;
import com.base.condition.Condition;
import com.base.condition.ConditionResult;
import com.base.timeline.error.StateError;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.objects.character.human.HumanCharacter;
import com.objects.character.opinion.Opinion;
import com.objects.character.opinion.OpinionReason;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class CharacterMapChanges {


    public static class OpinionChange extends TimelineMapChange<OpinionChange, UUID, Opinion, HumanCharacter>{


        protected OpinionChange(DMEReference<HumanCharacter> owner, LocalDate date) {
            super(owner, date);
        }
        public OpinionChange(DMEReference<HumanCharacter> owner, LocalDate date, Pair<UUID, Opinion>... changes) {
            super(owner, date, changes);
        }
        @Override
        protected void onApply(DMEReference<? extends HumanCharacter> entity, TimelineState<? extends HumanCharacter> currentState) {
            entity.get().internalSetOpinionMap(this.getFullMap());
        }
        @Override
        protected boolean onMerge(OpinionChange newChange, UUID key, Opinion value) {
            Opinion current = this.activeChanges.get(key);
            if (current != null) {
                current.addOpinions(value.getActiveReasons().toArray(new OpinionReason[0]));
                return false;
            }
            value.setParent(this);
            return true;
        }

        @Override
        protected boolean hasEndingChanges() {
            return true;
        }

        @Override
        public List<Class<TimelineChange<? super HumanCharacter>>> oppositeChanges() {
            return List.of();
        }

        @Override
        public boolean isPositive() {
            return true;
        }

        @Override
        protected String getText() {
            final int size = getActiveChanges().size();
            if(size == 1){
                return this.getOwner().parse() + " changed their opinion of " + getActiveChanges().keySet().iterator().next().toString();
            } else {
                return this.getOwner().parse() + " changed their opinion of " + size + " people.";
            }
        }

        @Override
        public void addChange(Pair<UUID, Opinion>... changes) {
            List<Pair<UUID, Opinion>> toImplement = new ArrayList<>();
            for (Pair<UUID, Opinion> pair : changes) {
                Opinion current = get(pair.getKey());
                Opinion proposed = pair.getValue();
                if (current == null){
                    toImplement.add(pair);
                    proposed.setParent(this);
                    continue;
                }
                current.addOpinions(proposed.getActiveReasons().toArray(new OpinionReason[0]));
            }
            super.addChange(toImplement.toArray(new Pair[0]));
        }
        public Multimap<LocalDate,OpinionReason> buildFullHistory(Opinion o){
            final Timeline<HumanCharacter> timeline = this.getOwner().get().getTimeline();
            final UUID otherID = o.getOther().getID();
            Map<LocalDate,List<OpinionReason>> toReturn = new HashMap<>();
            final BiFunction<Timeline<HumanCharacter>,OpinionChange, ChangeID> buildNext = (tl, ch) -> {
                return ch.getLeapfrog();
            };
            final BiConsumer<OpinionChange,Map<LocalDate,List<OpinionReason>>> consumer = (ch, finalMap) -> {
                Map<UUID,Opinion> localMap = ch.getActiveChanges();
                Opinion oo = localMap.get(otherID);
                if(oo != null){
                    finalMap.put(ch.getStart(),oo.getActiveReasons());
                }
            };
            final BiPredicate<LocalDate,Map<LocalDate,List<OpinionReason>>> predicate = (ch, finalMap) -> {
                return ch != null;
            };
            Timeline.iterateMap(buildNext,consumer,predicate,timeline,this.getLeapfrog(),toReturn,true);
            Multimap<LocalDate,OpinionReason> toReturnReal = HashMultimap.create();
            for (Map.Entry<LocalDate,List<OpinionReason>> e : toReturn.entrySet()){
                toReturnReal.putAll(e.getKey(),e.getValue());
            }
            return toReturnReal;
        }
        @Override
        protected void onBuildMap() {
            for (Opinion o : activeChanges.values()){
                o.resetPastTotal();
            }
        }

        @Override
        protected void onBuildMapStep(OpinionChange stepChange) {
            for (Map.Entry<UUID, Opinion> entry : stepChange.getActiveChanges().entrySet()) {
                Opinion current = this.activeChanges.get(entry.getKey());
                if (current != null){
                    current.amendPastTotal(entry.getValue().getActiveTotal());
                }
            }
        }


        @Override
        protected void deactivateConditions(List<Condition<StateError, ? super HumanCharacter>> list) {

        }

        @Override
        protected void nullifyConditions(List<Condition<ConditionResult.Nullify, ? super HumanCharacter>> list) {

        }

        @Override
        public void additionalSave(JsonObject data) {

        }


        @Override
        public void additionalLoad(JsonObject data) {
            for (Opinion entry : getActiveChanges().values()) {
                entry.setParent(this);
            }
        }
        @Override
        protected JsonElement vSerialize(Opinion opinion) {
            return opinion.toJson();
        }

        @Override
        protected JsonElement kSerialize(UUID uuid) {
            return new JsonPrimitive(uuid.toString());
        }

        @Override
        protected Opinion vDeserialize(JsonElement m) {
            return new Opinion(m.getAsJsonObject());
        }

        @Override
        protected UUID kDeserialize(JsonElement m) {
            return UUID.fromString(m.getAsString());
        }
    }
























}
