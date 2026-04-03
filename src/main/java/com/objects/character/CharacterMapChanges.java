package com.objects.character;

import com.base.reference.DMEReference;
import com.base.timeline.Timeline;
import com.base.timeline.change.ChangeID;
import com.base.timeline.state.TimelineState;
import com.base.timeline.change.changes.TimelineChange;
import com.base.timeline.change.TimelineMapChange;
import com.base.timeline.change.conditions.Condition;
import com.base.timeline.change.conditions.ConditionResult;
import com.base.timeline.error.StateError;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.objects.character.opinion.Opinion;
import com.objects.character.opinion.OpinionReason;
import org.apache.commons.lang3.tuple.Pair;
import org.checkerframework.checker.units.qual.K;

import java.time.LocalDate;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class CharacterMapChanges {


    public static class OpinionChange extends TimelineMapChange<OpinionChange, UUID, Opinion,BookCharacter>{


        protected OpinionChange(DMEReference<BookCharacter> owner, LocalDate date) {
            super(owner, date);
        }

        @Override
        protected void onApply(DMEReference<? extends BookCharacter> entity, TimelineState<? extends BookCharacter> currentState) {
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
        public List<Class<TimelineChange<? super BookCharacter>>> oppositeChanges() {
            return List.of();
        }

        @Override
        public boolean isPositive() {
            return true;
        }

        @Override
        protected String getText() {
            final int size = getChangeFragment().size();
            if(size == 1){
                return this.getOwner().parse() + " changed their opinion of " + getChangeFragment().keySet().iterator().next().toString();
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
            final Timeline<BookCharacter> timeline = this.getOwner().get().getTimeline();
            final UUID otherID = o.getOther().getID();
            Map<LocalDate,List<OpinionReason>> toReturn = new HashMap<>();
            final BiFunction<Timeline<BookCharacter>,OpinionChange, ChangeID> buildNext = (tl, ch) -> {
                return ch.getLeapfrog();
            };
            final BiConsumer<OpinionChange,Map<LocalDate,List<OpinionReason>>> consumer = (ch, finalMap) -> {
                Map<UUID,Opinion> localMap = ch.getChangeFragment();
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
            for (Map.Entry<UUID, Opinion> entry : stepChange.getChangeFragment().entrySet()) {
                Opinion current = this.activeChanges.get(entry.getKey());
                if (current != null){
                    current.amendPastTotal(entry.getValue().getActiveTotal());
                }
            }
        }


        @Override
        protected void deactivateConditions(List<Condition<StateError, ? super BookCharacter>> list) {

        }

        @Override
        protected void nullifyConditions(List<Condition<ConditionResult.Nullify, ? super BookCharacter>> list) {

        }

        @Override
        public void additionalSave(JsonObject data) {

        }


        @Override
        public void additionalLoad(JsonObject data) {
            for (Opinion entry : getChangeFragment().values()) {
                entry.setParent(this);
            }
        }
        @Override
        protected JsonElement serializeV(Opinion opinion) {
            return opinion.toJson();
        }

        @Override
        protected JsonElement serializeK(UUID uuid) {
            return new JsonPrimitive(uuid.toString());
        }

        @Override
        protected Opinion deserializeV(JsonElement m) {
            return new Opinion(m.getAsJsonObject());
        }

        @Override
        protected UUID deserializeK(JsonElement m) {
            return UUID.fromString(m.getAsString());
        }
    }
























}
