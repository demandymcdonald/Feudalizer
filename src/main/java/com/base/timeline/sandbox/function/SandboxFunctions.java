package com.base.timeline.sandbox.function;

import com.Global;
import com.base.DateMutableEntity;
import com.base.condition.Condition;
import com.base.reference.DMEReference;
import com.base.timeline.Timeline;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.multi.MultiCondition;
import com.base.timeline.change.multi.TimelineMultiChange;
import com.base.timeline.error.SandboxCode;
import com.base.timeline.error.StateError;
import com.base.timeline.sandbox.core.Sandbox;
import com.base.timeline.state.TimelineState;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.objects.character.sentient.HumanCharacter;
import com.objects.title.succession.SuccessionPlanner;
import com.utilities.id.Identifiable;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;

public class SandboxFunctions {

    public static class canNullify<T extends DateMutableEntity<T>> extends SandboxFunction<T> {
        @Override
        public void onComplete(Sandbox<T> sandbox, LocalDate endDate, SandboxCode code, DMEReference<T> entity, TimelineChange<? super T> newChange) {

        }

        @Override
        public SandboxCode onCycle(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<? super T> existingChange, List<Condition.ShouldRun> shouldRun) {
            if(newChange.canNullify(existingChange,shouldRun)){
                newChange.nullify(entity,state,existingChange);
            };
            return SandboxCode.CONTINUE;
        }

        @Override
        public void onStep(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, boolean isFirstCycle) {

        }
    }
    public static class CanAddChange<T extends DateMutableEntity<T>> extends SandboxFunction<T> {
        @Override
        public void onComplete(Sandbox<T> sandbox, LocalDate endDate, SandboxCode code, DMEReference<T> entity, TimelineChange<? super T> newChange) {
            final Timeline<T> timeline = sandbox.getSubject().get().getTimeline();
            final TimelineChange<? super T> change = sandbox.getObjective().change();
            final TimelineState<T> state = timeline.getStateAtExact(change.getStart(),true);
            switch (code){
                case END_DISCARD,CRITICAL_ERROR -> {
                    sandbox.getWorkingDirty().clear();
                    break;
                }
                case END_SAVE -> {
                    change.setEnd(endDate);
                    state.insertAndPropagateBreadcrumb(change);
                    break;
                }
            }
        }

        @Override
        public SandboxCode onCycle(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<? super T> existingChange, List<Condition.ShouldRun> shouldRun) {
            List<StateError> errors = newChange.doesConflict(existingChange,shouldRun);
            if (errors.isEmpty()) {
                return SandboxCode.CONTINUE;
            }
            return resolveStateErrors(sandbox, state, newChange, errors);
        }

        @Override
        public void onStep(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, boolean isFirstCycle) {
            if (isFirstCycle) {
                sandbox.buildDirtyMap();
            }
        }
    }
    public static class MultiChange<M extends TimelineMultiChange<M,K,V,I,T>,T extends DateMutableEntity<T>,K extends Identifiable<I>,V,I> extends SandboxFunction<T> {
        private final Map<Pair<K,V>,TimelineMultiChange.ChangeType> changes;
        private final Map<TimelineMultiChange.ChangeType,List<MultiCondition<M,K,V,I,T>>> conditions = new HashMap<>();
        private final Multimap<TimelineMultiChange.ChangeType,Pair<K,V>> changeByType = HashMultimap.create();
        private final Consumer<SandboxCode> onComplete;
        public MultiChange(Consumer<SandboxCode> onComplete, Map<Pair<K,V>,TimelineMultiChange.ChangeType> changes) {
            this.onComplete = onComplete;
            this.changes = new HashMap<>(changes);
        }
        @Override
        public SandboxCode onStartup(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange) {
            M m = (M) newChange;
            if(changes.isEmpty()){
                return SandboxCode.END_SAVE;
            } else if(changes == null){
                throw new NullPointerException("Changes cannot be null on a MultiChange Sandbox Function.");
            }
            for(Map.Entry<Pair<K,V>,TimelineMultiChange.ChangeType> entry : changes.entrySet()){
                final TimelineMultiChange.ChangeType type = entry.getValue();
                K k = m.deepCopyK(entry.getKey().getLeft());
                V v = m.deepCopyV(entry.getKey().getRight());
                if(!conditions.containsKey(type)){
                    List<MultiCondition<M,K,V,I,T>> list = new ArrayList<>();
                    switch (type){
                        case ADD -> {
                           m.addConditions(list);
                        }
                        case REMOVE -> {
                            m.removeConditions(list);
                        }
                        case REMOVE_WIPE_FORWARD -> {
                            m.removeWipeFConditions(list);
                        }
                        case REMOVE_WIPE_BACKWARD -> {
                            m.removeWipeBConditions(list);
                        }
                        case REMOVE_WIPE_BOTH -> {
                            if (sandbox.getTimeDirection() == Global.TimeDirection.FORWARD){
                                m.removeWipeFConditions(list);
                            } else {
                                m.removeWipeBConditions(list);
                            }
                        }
                        case MODIFY_BOTH -> {
                            m.modifyKeyConditions(list);
                            m.modifyValueConditions(list);
                        }
                        case MODIFY_KEY -> {
                            m.modifyKeyConditions(list);
                        }
                        case MODIFY_VALUE -> {
                            m.modifyValueConditions(list);
                        }
                    }
                    conditions.put(type,list);
                }
                changeByType.put(type,Pair.of(k,v));
            }
            changes.clear();
            return SandboxCode.CONTINUE;
        }
        @Override
        public void onComplete(Sandbox<T> sandbox, LocalDate endDate, SandboxCode code, DMEReference<T> entity, TimelineChange<? super T> newChange) {
            final Timeline<T> timeline = sandbox.getSubject().get().getTimeline();
            final TimelineChange<? super T> change = sandbox.getObjective().change();
            final TimelineState<T> state = timeline.getStateAtExact(change.getStart(),true);
            switch (code){
                case END_DISCARD,CRITICAL_ERROR -> {
                    sandbox.getWorkingDirty().clear();
                    break;
                }
                case END_SAVE -> {
                    change.setEnd(endDate);
                    state.insertAndPropagateBreadcrumb(change);
                    break;
                }
            }
            onComplete.accept(code);
        }

        @Override
        public SandboxCode onCycle(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<? super T> existingChange, List<Condition.ShouldRun> shouldRun) {
            List<StateError> errors = new ArrayList<>();
            if (existingChange.getClass() == newChange.getClass()) {
                M mNew = (M) newChange;
                M mCurrent = (M) existingChange;
                for (TimelineMultiChange.ChangeType type : changeByType.keySet()) {
                    List<Pair<K, V>> entries = new ArrayList<>(changeByType.get(type));
                    List<Pair<K, V>> currentEntries = buildCurrentList(mCurrent, entries);
                    List<MultiCondition<M, K, V, I, T>> conditions = this.conditions.get(type);
                    for (MultiCondition<M, K, V, I, T> condition : conditions) {
                        if(!shouldRun.contains(condition.shouldRun())){
                            continue;
                        }
                        Optional<StateError> error = condition.check(mNew, entries, mCurrent, currentEntries);
                        if (error.isPresent()) {
                            errors.add(error.get());
                        }
                    }
                }
            }
            return resolveStateErrors(sandbox, state, newChange, errors);
        }
        private List<Pair<K,V>> buildCurrentList(M existingChange, List<Pair<K,V>> newEntries){
            List<Pair<K,V>> entries = new ArrayList<>();
            for(Pair<K,V> entry : newEntries){
                V v = existingChange.get(entry.getKey());
                if (v != null) {
                    entries.add(Pair.of(entry.getKey(),v));
                }
            }
            return entries;
        }
        @Override
        public void onStep(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, boolean isFirstCycle) {
            if (isFirstCycle) {
                sandbox.buildDirtyMap();
            }
        }
    }
    public static class canDeactivate<T extends DateMutableEntity<T>> extends SandboxFunction<T> {
        private final Boolean isSubPart;
        public canDeactivate(boolean isSubPart) {
            this.isSubPart = isSubPart;
        }


        @Override
        public SandboxCode onCycle(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<? super T> existingChange, List<Condition.ShouldRun> shouldRun) {
            List<StateError> errors = newChange.canBeDeactivated(state,isSubPart,shouldRun);
            if (errors.isEmpty()) {
                return SandboxCode.CONTINUE;
            }
            return resolveStateErrors(sandbox, state, newChange, errors);
        }

        @Override
        public void onStep(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, boolean isFirstCycle) {

        }

        @Override
        public void onComplete(Sandbox<T> sandbox, LocalDate endDate, SandboxCode code, DMEReference<T> entity, TimelineChange<? super T> newChange) {
            switch (code){
                case END_DISCARD,CRITICAL_ERROR -> {
                    sandbox.getWorkingDirty().clear();
                    break;
                }
                case END_SAVE -> {
                    newChange.deactivate(true);
                    break;
                }
            }
        }

    }
    public static class SuccessionPlanning extends SandboxFunction<HumanCharacter>{
        @Override
        public void onComplete(Sandbox<HumanCharacter> sandbox, LocalDate endDate, SandboxCode code, DMEReference<HumanCharacter> entity, TimelineChange<? super HumanCharacter> newChange) {
            final Timeline<HumanCharacter> timeline = sandbox.getSubject().get().getTimeline();
            final TimelineChange<? super HumanCharacter> change = sandbox.getObjective().change();
            final TimelineState<HumanCharacter> state = timeline.getStateAtExact(change.getStart(),true);
            switch (code) {
                case END_DISCARD, CRITICAL_ERROR -> {
                    sandbox.getWorkingDirty().clear();
                    break;
                }
                case END_SAVE -> {
                    break;
                }
            }
        }

        @Override
        public SandboxCode onCycle(Sandbox<HumanCharacter> sandbox, DMEReference<HumanCharacter> entity, TimelineState<HumanCharacter> state, TimelineChange<? super HumanCharacter> newChange, TimelineChange<? super HumanCharacter> existingChange, List<Condition.ShouldRun> shouldRun) {
            final HumanCharacter character = entity.get();
            final LocalDate endDate = character.getEnded();
            return SuccessionPlanner.run(sandbox,character,endDate);
        }

        @Override
        public void onStep(Sandbox<HumanCharacter> sandbox, DMEReference<HumanCharacter> entity, TimelineState<HumanCharacter> state, TimelineChange<? super HumanCharacter> newChange, boolean isFirstCycle) {

        }


    }

    protected static <T extends DateMutableEntity<T>> SandboxCode resolveStateErrors(
            Sandbox<T> sandbox, TimelineState<T> state, TimelineChange<? super T> newChange, List<StateError> errors){
        return sandbox.handleErrors(state,newChange,errors);
    }

}
