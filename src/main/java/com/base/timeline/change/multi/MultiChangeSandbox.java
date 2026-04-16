package com.base.timeline.change.multi;

import com.Global.*;
import com.base.DateMutableEntity;
import com.base.condition.Condition;
import com.base.reference.DMEReference;
import com.base.timeline.Timeline;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.error.SandboxCode;
import com.base.timeline.error.StateError;
import com.base.timeline.sandbox.core.Sandbox;
import com.base.timeline.sandbox.function.SandboxFunction;
import com.base.timeline.sandbox.function.SandboxFunctions;
import com.base.timeline.state.TimelineState;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.utilities.id.Identifiable;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;

import static com.Global.TimeDirection.FORWARD;
import static com.base.timeline.change.multi.TimelineMultiChange.removeEntry;

public class MultiChangeSandbox<M extends TimelineMultiChange<M, K, V, I, T>, T extends DateMutableEntity<T>, K extends Identifiable<I>, V, I> extends SandboxFunction<T> {
    private final Map<Pair<K, V>, TimelineMultiChange.ChangeType> changes;
    private final Map<TimelineMultiChange.ChangeType, List<MultiCondition<M, K, V, I, T>>> conditions = new HashMap<>();
    private final Multimap<TimelineMultiChange.ChangeType, Pair<K, V>> changeByType = HashMultimap.create();
    private final Consumer<SandboxCode> onComplete;

    public MultiChangeSandbox(Consumer<SandboxCode> onComplete, Map<Pair<K, V>, TimelineMultiChange.ChangeType> changes) {
        this.onComplete = onComplete;
        this.changes = new HashMap<>(changes);
    }
    @Override
    public SandboxCode onStartup(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange) {
        M m = (M) newChange;
        if (changes.isEmpty()) {
            return SandboxCode.END_SAVE;
        } else if (changes == null) {
            throw new NullPointerException("Changes cannot be null on a MultiChangeSandbox Sandbox Function.");
        }
        for (Map.Entry<Pair<K, V>, TimelineMultiChange.ChangeType> entry : changes.entrySet()) {
            final TimelineMultiChange.ChangeType type = entry.getValue();
            K k = m.deepCopyK(entry.getKey().getLeft());
            V v = m.deepCopyV(entry.getKey().getRight());
            if (!conditions.containsKey(type)) {
                List<MultiCondition<M, K, V, I, T>> list = new ArrayList<>();
                switch (type) {
                    case ADD, ADD_WIPE -> {
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
                        if (sandbox.getTimeDirection() == TimeDirection.FORWARD) {
                            m.removeWipeFConditions(list);
                        } else {
                            m.removeWipeBConditions(list);
                        }
                    }
                    case MODIFY_BOTH, MODIFY_BOTH_WIPE -> {
                        m.modifyKeyConditions(list);
                        m.modifyValueConditions(list);
                    }
                    case MODIFY_KEY, MODIFY_KEY_WIPE -> {
                        m.modifyKeyConditions(list);
                    }
                    case MODIFY_VALUE, MODIFY_VALUE_WIPE -> {
                        m.modifyValueConditions(list);
                    }
                }
                conditions.put(type, list);
            }
            changeByType.put(type, Pair.of(k, v));
        }
        changes.clear();
        return SandboxCode.CONTINUE;
    }


    @Override
    public SandboxCode onCycle(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<? super T> existingChange, List<Condition.ShouldRun> shouldRun) {
        List<StateError> errors = new ArrayList<>();
        if (existingChange.getClass().equals(newChange.getClass())) {
            M mNew = (M) newChange;
            M mCurrent = (M) existingChange;
            for (TimelineMultiChange.ChangeType type : changeByType.keySet()) {
                List<Pair<K, V>> entries = new ArrayList<>(changeByType.get(type));
                int preEntries = entries.size();
                List<Pair<K, V>> currentEntries = buildCurrentList(mCurrent, entries);
                List<MultiCondition<M, K, V, I, T>> conditions = this.conditions.get(type);
                for (MultiCondition<M, K, V, I, T> condition : conditions) {
                    if (!shouldRun.contains(condition.shouldRun())) {
                        continue;
                    }
                    Optional<StateError> error = condition.check(type, mNew, entries, mCurrent, currentEntries);
                    if (error.isPresent()) {
                        errors.add(error.get());
                    }
                }
                if (entries.size() != preEntries) {
                    changeByType.removeAll(type);
                    changeByType.putAll(type, entries);
                }
            }
        }
        return SandboxFunctions.resolveStateErrors(sandbox, state, newChange, errors);
    }
    @Override
    public void onComplete(Sandbox<T> sandbox, LocalDate endDate, SandboxCode code, DMEReference<T> entity, TimelineChange<? super T> newChange) {
        final Timeline<T> timeline = sandbox.getSubject().get().getTimeline();
        final TimelineChange<? super T> change = sandbox.getObjective().change();
        final TimelineState<T> state = timeline.getStateAtExact(change.getStart(), true);
        switch (code) {
            case END_DISCARD, CRITICAL_ERROR -> {
                sandbox.getWorkingDirty().clear();
                break;
            }
            case END_SAVE -> {
                change.setEnd(endDate);
                state.insertAndPropagateBreadcrumb(change);
                doChange();
                break;
            }
        }
        onComplete.accept(code);
    }

    private List<Pair<K, V>> buildCurrentList(M existingChange, List<Pair<K, V>> newEntries) {
        List<Pair<K, V>> entries = new ArrayList<>();
        for (Pair<K, V> entry : newEntries) {
            V v = existingChange.get(entry.getKey());
            if (v != null) {
                entries.add(Pair.of(entry.getKey(), v));
            }
        }
        return entries;
    }

    private void doChange(M m) {
        for (TimelineMultiChange.ChangeType type : changeByType.keySet()) {
            List<Pair<K, V>> entries = new ArrayList<>(changeByType.get(type));
            switch (type) {
                case ADD -> {
                    doAdd(m,entries);
                }
                case ADD_WIPE -> {
                    doAddWipe(m,entries);
                }
                case REMOVE -> {
                    doRemove(m,entries);
                }
                case REMOVE_WIPE_FORWARD -> {
                    doRemoveWipeForward(m,entries);
                }
                case REMOVE_WIPE_BACKWARD -> {
                    doRemoveWipeBackward(m,entries);
                }
                case REMOVE_WIPE_BOTH -> {
                    doRemoveWipeBoth(m,entries);
                }
                case MODIFY_BOTH -> {
                    doModifyBoth(m,entries);
                }
                case MODIFY_KEY -> {
                    doModifyKey(m,entries);
                }
                case MODIFY_VALUE -> {
                    doModifyValue(m,entries);
                }
                case MODIFY_BOTH_WIPE -> {
                    doModifyBothWipe(m,entries);
                }
                case MODIFY_KEY_WIPE -> {
                    doModifyKeyWipe(m,entries);
                }
                case MODIFY_VALUE_WIPE -> {
                    doModifyValueWipe(m,entries);
                }
            }
        }
    }

    private void doAdd(M change, List<Pair<K, V>> entries) {
        for (Pair<K, V> entry : entries) {
            change.internalAddEntry(entry.getKey(), entry.getValue());
        }
    }

    private void doAddWipe(M change,List<Pair<K, V>> entries) {
        List<K> wipe = new ArrayList<>();
        for (Pair<K, V> entry : entries) {
            change.internalAddEntry(entry.getKey(), entry.getValue());
            wipe.add(entry.getKey());
            for(TimelineMultiChange.Listener<K,V> listener : change.getListeners()){
                listener.onMapPut(entry.getKey(), entry.getValue());
            }
        }
        removeEntry(change, FORWARD, true, false, wipe);
    }

    private void doRemove(M change,List<Pair<K, V>> entries) {

    }

    private void doRemoveWipeForward(M change,List<Pair<K, V>> entries) {

    }

    private void doRemoveWipeBackward(M change,List<Pair<K, V>> entries) {

    }

    private void doRemoveWipeBoth(M change,List<Pair<K, V>> entries) {

    }

    private void doModifyBoth(M change,List<Pair<K, V>> entries) {
        for (Pair<K, V> entry : entries) {
            for(TimelineMultiChange.Listener<K,V> listener : change.getListeners()){
                listener.onMapChange(TimelineMultiChange.ChangeType.MODIFY_BOTH,entry.getKey(), entry.getValue());
            }
        }
    }

    private void doModifyKey(M change,List<Pair<K, V>> entries) {
        for (Pair<K, V> entry : entries) {
            for(TimelineMultiChange.Listener<K,V> listener : change.getListeners()){
                listener.onMapChange(TimelineMultiChange.ChangeType.MODIFY_KEY,entry.getKey(), entry.getValue());
            }
        }
    }

    private void doModifyValue(M change,List<Pair<K, V>> entries) {
        for (Pair<K, V> entry : entries) {
            for(TimelineMultiChange.Listener<K,V> listener : change.getListeners()){
                listener.onMapChange(TimelineMultiChange.ChangeType.MODIFY_VALUE,entry.getKey(), entry.getValue());
            }
        }
    }

    private void doModifyBothWipe(M change,List<Pair<K, V>> entries) {
        List<K> wipe = new ArrayList<>();
        for (Pair<K, V> entry : entries) {
            wipe.add(entry.getKey());
            for(TimelineMultiChange.Listener<K,V> listener : change.getListeners()){
                listener.onMapChange(TimelineMultiChange.ChangeType.MODIFY_BOTH_WIPE,entry.getKey(), entry.getValue());
            }
        }
        removeEntry(change, FORWARD, true, false, wipe);
    }

    private void doModifyKeyWipe(M change,List<Pair<K, V>> entries) {
        List<K> wipe = new ArrayList<>();
        for (Pair<K, V> entry : entries) {
            wipe.add(entry.getKey());
            for(TimelineMultiChange.Listener<K,V> listener : change.getListeners()){
                listener.onMapChange(TimelineMultiChange.ChangeType.MODIFY_KEY_WIPE,entry.getKey(), entry.getValue());
            }
        }
        removeEntry(change, FORWARD, true, false, wipe);
    }
    private void doModifyValueWipe(M change,List<Pair<K, V>> entries) {
        List<K> wipe = new ArrayList<>();
        for (Pair<K, V> entry : entries) {
            wipe.add(entry.getKey());
            for(TimelineMultiChange.Listener<K,V> listener : change.getListeners()){
                listener.onMapChange(TimelineMultiChange.ChangeType.MODIFY_VALUE_WIPE,entry.getKey(), entry.getValue());
            }
        }
        removeEntry(change, FORWARD, true, false, wipe);
    }


    @Override
    public void onStep(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, boolean isFirstCycle) {
        if (isFirstCycle) {
            sandbox.buildDirtyMap();
        }
    }
}
