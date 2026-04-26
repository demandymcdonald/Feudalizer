package com.base.datemutable.timeline.change.multi;

import com.base.datemutable.DateMutableEntity;
import com.base.condition.Condition;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.Timeline;
import com.base.datemutable.timeline.change.TimelineChange;
import com.base.datemutable.timeline.change.multi.condition.MultiCondition;
import com.base.datemutable.timeline.change.multi.type.ChangeType;
import com.base.datemutable.timeline.change.multi.type.Delta;
import com.base.datemutable.timeline.change.multi.type.WipeType;
import com.base.datemutable.timeline.error.SandboxCode;
import com.base.datemutable.timeline.error.StateError;
import com.base.datemutable.timeline.sandbox.core.Sandbox;
import com.base.datemutable.timeline.sandbox.function.SandboxFunction;
import com.base.datemutable.timeline.sandbox.function.SandboxFunctions;
import com.base.datemutable.timeline.state.TimelineState;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.utilities.id.Identifiable;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.*;

import static com.Global.TimeDirection.BACKWARD;
import static com.base.datemutable.timeline.change.multi.TLMultiChange.removeEntry;

public class MultiChangeSandbox<M extends TLMultiChange<M, K, V, I, T>, T extends DateMutableEntity<T>, K extends Identifiable<I>, V, I> extends SandboxFunction<T> {
    private final Map<Pair<K, V>, Delta> changes;
    private final Map<Delta, List<MultiCondition<M, K, V, I, T>>> conditions = new HashMap<>();
    private final Multimap<Delta, Pair<K, V>> changeByType = HashMultimap.create();
    private final List<TLMultiChange.Listener<K,V>>  listeners;
    private final Map<K, TLMultiChange.ChangeContainer<M,K,V,I,T>> changeContainers;
    private boolean propagateFlag = false;
    private boolean wipeFlag = false;
    private boolean addWipeFlag = false;
    public MultiChangeSandbox(Map<Pair<K, V>, Delta> changes, List<TLMultiChange.Listener<K,V>> listeners, Map<K, TLMultiChange.ChangeContainer<M,K,V,I,T>> changeContainers) {
        this.changes = new HashMap<>(changes);
        this.listeners = new ArrayList<>(listeners);
        this.changeContainers = new HashMap<>(changeContainers);
    }
    public MultiChangeSandbox(Map<Pair<K, V>, Delta> changes, List<TLMultiChange.Listener<K,V>> listeners) {
        for(Delta ct : changes.values()){
            if(ct.getChangeType() != ChangeType.NO_CHANGE){
                throw new IllegalArgumentException("Change Modifications cannot be handled without a ChangeContainer!");
            }
        }
        this.changes = new HashMap<>(changes);
        this.listeners = new ArrayList<>(listeners);
        this.changeContainers = new HashMap<>();
    }
    @Override
    protected SandboxCode onStartup(Sandbox<T> sandbox, DMEReference<T> entity, TimelineChange<? super T> newChange) {
        M m = (M) newChange;
        if (changes.isEmpty()) {
            return SandboxCode.END_SAVE;
        } else if (changes == null) {
            throw new NullPointerException("Changes cannot be null on a MultiChangeSandbox Sandbox Function.");
        }
        doEntries(sandbox, entity, m);
        return SandboxCode.CONTINUE;
    }
    private void doEntries(Sandbox<T> sandbox, DMEReference<T> entity, M m){
        for (Map.Entry<Pair<K, V>, Delta> entry : changes.entrySet()) {
            final Delta type = entry.getValue();
            K k = m.deepCopyK(entry.getKey().getLeft());
            V v = m.deepCopyV(entry.getKey().getRight());
            if (type.getChangeType() != ChangeType.NO_CHANGE) {
                TLMultiChange.ChangeContainer<M,K,V,I,T> container = changeContainers.get(k);
                if (container == null) {
                    throw new NullPointerException("ChangeContainer for " + k + " is null!");
                }
                container.apply(k, v);
                if(type.getWipeType() != WipeType.NO_WIPE){
                    propagateFlag = true;
                }
            }
            if (!conditions.containsKey(type)) {
                if (type.getWipeType() == WipeType.BOTH){
                    sandbox.setCurrent(entity.get().getTimeline().getStart());
                }
                conditions.put(type, buildConditions(sandbox, m, type));
            }
            changeByType.put(type, Pair.of(k, v));
        }
        changes.clear();
    }
    private List<MultiCondition<M, K, V, I, T>> buildConditions(Sandbox<T> sandbox, M m, Delta type){
        List<MultiCondition<M, K, V, I, T>> list = new ArrayList<>();
        switch (type) {
            case ADD -> {m.conditionsAdd(list);}
            case ADD_WIPE -> {
                m.conditionsAdd(list);
                addWipeFlag = true;
            }
            case REMOVE -> {m.conditionsRemove(list);}
            case REMOVE_WIPE_FORWARD,REMOVE_WIPE_BOTH -> {
                m.conditionsRemove(list);
                m.conditionsWipeForward(list);
                wipeFlag = true;
            }
            case REMOVE_WIPE_BACKWARD -> {
                m.conditionsRemove(list);
                m.conditionsWipeBackward(list);
                wipeFlag = true;
            }
            case MODIFY_BOTH, MODIFY_BOTH_WIPE -> {
                m.conditionsModifyKey(list);
                m.conditionsModifyValue(list);
                //Wipes aren't actually wipes for modifications. More mutations broadly.
            }
            case MODIFY_KEY, MODIFY_KEY_WIPE -> {
                m.conditionsModifyKey(list);
                //Wipes aren't actually wipes for modifications. More mutations broadly.
            }
            case MODIFY_VALUE, MODIFY_VALUE_WIPE -> {
                m.conditionsModifyValue(list);
                //Wipes aren't actually wipes for modifications. More mutations broadly.
            }
        }
        return list;
    }

    @Override
    public SandboxCode onCycle(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<? super T> existingChange, List<Condition.ShouldRun> shouldRun) {
        if (!existingChange.getClass().equals(newChange.getClass())) {
            return SandboxCode.CONTINUE;
        }
        return doCycle(sandbox, state, newChange, existingChange, shouldRun);
    }
    private SandboxCode doCycle(Sandbox<T> sandbox, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<? super T> existingChange, List<Condition.ShouldRun> shouldRun){
        List<StateError> errors = new ArrayList<>();
        M mNew = (M) newChange;
        M mCurrent = (M) existingChange;
        for (Delta type : changeByType.keySet()) {
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
        return SandboxFunctions.resolveStateErrors(sandbox, state, newChange, errors);
    }
    @Override
    protected void onStep(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, boolean isFirstCycle) {
        if (isFirstCycle) {
            sandbox.buildDirtyMap();
        } else if (propagateFlag){
            propagate(state,newChange);
            return;
        }
        if (wipeFlag || (addWipeFlag && !isFirstCycle)){
            wipe(state,newChange);
            wipeFlag = true; //This is just here to basically prevent the second check from firing after the first cycle.
        }
    }
    private void wipe(TimelineState<T> state, TimelineChange<? super T> newChange){
        for(Delta type : changeByType.keySet()){
            if(type.getWipeType().equals(WipeType.NO_WIPE) || !type.getChangeType().equals(ChangeType.NO_CHANGE)){
                continue;
            }
            M m = (M) state.getChange(newChange.getClassID(),false);
            if(m == null){
                continue;
            }
            for(Pair<K,V> entry : changeByType.get(type)){
                K key = entry.getKey();
                m.internalRemoveChange(key);
                m.internalRemoveEnding(key);
            }
        }
    }
    private void propagate(TimelineState<T> state, TimelineChange<? super T> newChange){
        for(Delta type : changeByType.keySet()){
            if(type.getWipeType().equals(WipeType.NO_WIPE) || type.getChangeType().equals(ChangeType.NO_CHANGE)){
                continue;
            }
            M m = (M) state.getChange(newChange.getClassID(),false);
            if(m == null){
                continue;
            }
            for(Pair<K,V> entry : changeByType.get(type)){
                K key = entry.getKey();
                if(m.contains(key)){
                    K theirK = m.getYourCopy(key);
                    V theirV = m.get(key);
                    changeContainers.get(key).apply(theirK, theirV);
                }
            }
        }
    }
    @Override
    public void onComplete(Sandbox<T> sandbox, LocalDate endDate, SandboxCode code, DMEReference<T> entity, TimelineChange<? super T> newChange) {
        final Timeline<T> timeline = sandbox.getSubject().get().getTimeline();
        final TimelineChange<? super T> change = sandbox.getObjective().change();
        final TimelineState<T> state = timeline.getStateAtExact(change.getStart(), true);
        switch (code) {
            case END_DISCARD, CRITICAL_ERROR -> {
                sandbox.getWorkingDirty().clear();
                //Add empty change culling;
                break;
            }
            case END_SAVE -> {
                change.setEnd(endDate);
                state.insertAndPropagateBreadcrumb(change);
                doChange((M) newChange);
                break;
            }
        }
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
        for (Delta type : changeByType.keySet()) {
            List<Pair<K, V>> entries = new ArrayList<>(changeByType.get(type));
            switch (type) {
                case ADD -> {doAdd(m,entries);}
                case ADD_WIPE -> {doAddWipe(m,entries);}
                case REMOVE -> {doRemove(m,entries);}
                case REMOVE_WIPE_FORWARD -> {doRemoveWipeForward(m,entries);}
                case REMOVE_WIPE_BACKWARD -> {doRemoveWipeBackward(m,entries);}
                case REMOVE_WIPE_BOTH -> {doRemoveWipeBoth(m,entries);}
                case MODIFY_BOTH,MODIFY_BOTH_WIPE -> {
                    applyModification(m,entries);
                    doModifyBoth(m,entries);
                }
                case MODIFY_KEY,MODIFY_KEY_WIPE -> {
                    applyModification(m,entries);
                    doModifyKey(m,entries);
                }
                case MODIFY_VALUE,MODIFY_VALUE_WIPE -> {
                    applyModification(m,entries);
                    doModifyValue(m,entries);
                }
            }
        }
    }
    private void applyModification(M change, List<Pair<K, V>> entries){
        for (Pair<K, V> entry : entries) {
            change.internalAddEntry(entry.getKey(), entry.getValue());
        }
    }
    private void doAdd(M change, List<Pair<K, V>> entries) {
        for (Pair<K, V> entry : entries) {
            K key = entry.getKey();
            change.internalAddEntry(key, entry.getValue());
            I id = key.getID();
            if (change.hasEndingChanges() && change.getEndingChanges().contains(id)) {
                change.internalRemoveEnding(key);
            }
        }
    }

    private void doAddWipe(M change,List<Pair<K, V>> entries) {
        List<K> wipe = new ArrayList<>();
        for (Pair<K, V> entry : entries) {
            K key = entry.getKey();
            change.internalAddEntry(key, entry.getValue());
            wipe.add(key);
            for(TLMultiChange.Listener<K,V> listener : listeners){
                listener.onMapPut(key, entry.getValue());
            }
            I id = key.getID();
            if (change.hasEndingChanges() && change.getEndingChanges().contains(id)) {
                change.internalRemoveEnding(key);
            }
        }
        //removeEntry(change, FORWARD, true, false, wipe);
    }

    private void doRemove(M change,List<Pair<K, V>> entries) {
        List<K> wipe = new ArrayList<>();
        for (Pair<K, V> entry : entries) {
            if(change.contains(entry.getKey())){
                change.internalRemoveChange(entry.getKey());
            } else {
                wipe.add(entry.getKey());
            }
            change.onRemoveEntry(WipeType.NO_WIPE, entry.getKey());
            for(TLMultiChange.Listener<K,V> listener : listeners){
                listener.onMapRemove(entry.getKey(), entry.getValue(), WipeType.NO_WIPE);
            }
        }
        removeEntry(change, BACKWARD, false, true, wipe);
    }

    private void doRemoveWipeForward(M change,List<Pair<K, V>> entries) {
        List<K> wipe = new ArrayList<>();
        for (Pair<K, V> entry : entries) {
            wipe.add(entry.getKey());
            change.onRemoveEntry(WipeType.FORWARD, entry.getKey());
            for(TLMultiChange.Listener<K,V> listener : listeners){
                listener.onMapRemove(entry.getKey(), entry.getValue(), WipeType.FORWARD);
            }
        }
        //removeEntry(change, FORWARD, true, true, wipe);
    }

    private void doRemoveWipeBackward(M change,List<Pair<K, V>> entries) {
        List<K> wipe = new ArrayList<>();
        for (Pair<K, V> entry : entries) {
            wipe.add(entry.getKey());
            change.onRemoveEntry(WipeType.BACKWARD, entry.getKey());
            for(TLMultiChange.Listener<K,V> listener : listeners){
                listener.onMapRemove(entry.getKey(), entry.getValue(), WipeType.BACKWARD);
            }
        }
        //removeEntry(change, BACKWARD, true, true, wipe);
    }

    private void doRemoveWipeBoth(M change,List<Pair<K, V>> entries) {
        List<K> wipe = new ArrayList<>();
        for (Pair<K, V> entry : entries) {
            wipe.add(entry.getKey());
            change.onRemoveEntry(WipeType.BOTH, entry.getKey());
            for(TLMultiChange.Listener<K,V> listener : listeners){
                listener.onMapRemove(entry.getKey(), entry.getValue(), WipeType.BOTH);
            }
        }
        //removeEntry(change, FORWARD, true, false, new ArrayList<>(wipe));
        //removeEntry(change, BACKWARD, true, true, new ArrayList<>(wipe));
    }

    private void doModifyBoth(M change,List<Pair<K, V>> entries) {
        for (Pair<K, V> entry : entries) {
            for(TLMultiChange.Listener<K,V> listener : listeners){
                listener.onMapChange(Delta.MODIFY_BOTH,entry.getKey(), entry.getValue());
            }
        }
    }

    private void doModifyKey(M change,List<Pair<K, V>> entries) {
        for (Pair<K, V> entry : entries) {
            for(TLMultiChange.Listener<K,V> listener : listeners){
                listener.onMapChange(Delta.MODIFY_KEY,entry.getKey(), entry.getValue());
            }
        }
    }

    private void doModifyValue(M change,List<Pair<K, V>> entries) {
        for (Pair<K, V> entry : entries) {
            for(TLMultiChange.Listener<K,V> listener : listeners){
                listener.onMapChange(Delta.MODIFY_VALUE,entry.getKey(), entry.getValue());
            }
        }
    }
}
