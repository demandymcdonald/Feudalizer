package com.base.datemutable.timeline.change.multi;

import com.Global;
import com.base.datemutable.DateMutableEntity;
import com.base.datemutable.timeline.change.TLChangeRegistry;
import com.base.reference.DMEReference;
import com.base.reference.SimpleReference;
import com.base.datemutable.timeline.Timeline;
import com.base.datemutable.timeline.TimelineObject;
import com.base.datemutable.timeline.change.TimelineChange;
import com.base.datemutable.timeline.change.condition.apply.ApplyCondition;
import com.base.datemutable.timeline.change.condition.nullify.NullifyCondition;
import com.base.datemutable.timeline.change.multi.condition.MultiCondition;
import com.base.datemutable.timeline.change.multi.type.ChangeType;
import com.base.datemutable.timeline.change.multi.type.Delta;
import com.base.datemutable.timeline.change.multi.type.WipeType;
import com.base.datemutable.timeline.error.SandboxCode;
import com.base.datemutable.timeline.error.StateError;
import com.base.datemutable.timeline.sandbox.core.Objective;
import com.base.datemutable.timeline.sandbox.core.Sandbox;
import com.base.datemutable.timeline.sandbox.core.SandboxHandler;
import com.base.datemutable.timeline.sandbox.function.SandboxFunction;
import com.base.datemutable.timeline.state.TimelineState;
import com.utilities.caching.CachingSupplier;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.utilities.id.Identifiable;
import org.apache.commons.lang3.tuple.Pair;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.*;

import static com.Global.TimeDirection.BACKWARD;
import static com.Global.TimeDirection.FORWARD;

@SuppressWarnings("unchecked")
public abstract class TLMultiChange<M extends TLMultiChange<M,K,V,I,T>, K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>> extends TimelineChange<T> {
    //Use of atomics here isn't an indication of thread safety per-say. I just needed a container for bools and ints. I know there are probably better ones. I don't really care
    //right now. I just want to get this working for the moment.
    public static final Logger LOGGER = LoggerFactory.getLogger(TLMultiChange.class);
    public Logger logger(){
        return LOGGER;   
    }

    private final Set<I> endingChanges = new HashSet<>();
    private final Map<K,V> activeChanges;
    private final List<Listener<K,V>> listeners = new ArrayList<>();


    private final CachingSupplier<Map<K,V>> fullMap = new CachingSupplier<>(this::buildFull);
    protected final AtomicBoolean pauseCacheChecks = new AtomicBoolean(false);

    protected TLMultiChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
        activeChanges = new HashMap<>();
    }
    protected TLMultiChange(DMEReference<? extends T> owner, LocalDate date, Map<K,V> initial) {
        super(owner, date);
        activeChanges = new HashMap<>(initial);
    }
    public int getActiveSize(){
        return  getActive().size();
    }

    public boolean shouldInvalidate(M initiator, K key, Delta type){
        if (!fullMap.isMemoized()){
            return false;
        } else if(endingChanges.contains(key.getID())) {
            return false;
        } else return type != Delta.MODIFY_VALUE || !(initiator.getActive().get(key) == this.internalGetFull().get(key));
    };

    protected boolean canAdd(K key, V value, @Nullable K currentKey,@Nullable V currentValue){
        return true;
    }

    @Override
    protected final void nullifyConditions(List<NullifyCondition<? super T>> list) {

    }
    public M getEmptyChange(DMEReference<? extends T> owner, LocalDate date) {
        return TLChangeRegistry.deserializeChange(this.getClass(),owner,date);
    }
    public abstract boolean hasEndingChanges();
    private final MultiCondition<M,K,V,I,T> condition = new MultiCondition<>() {
        @Override
        protected Optional<StateError> doCheck(Delta change, M newChange, List<Pair<K, V>> newEntries, M curChange, List<Pair<K, V>> curEntries) {
        switch (change) {
            case ADD_WIPE, MODIFY_BOTH_WIPE, MODIFY_KEY_WIPE, MODIFY_VALUE_WIPE -> {
                return Optional.empty();
            }
            default -> {
                List<Pair<K, V>> newEntriesToRemove = new ArrayList<>();
                boolean canEnd = true;
                for (Pair<K, V> entry : newEntries) {
                    for (Pair<K, V> curEntry : curEntries) {
                        if (entry.getKey().getID().equals(curEntry.getKey().getID())) {
                            newEntriesToRemove.add(entry);
                        } else {
                            canEnd = false;
                        }
                    }
                }
                newEntries.removeAll(newEntriesToRemove);
                if (canEnd) {
                    return Optional.of(new StateError("tmc_add_complete", SimpleReference.of("Complete"), curChange).addEndSave());
                } else {
                    return Optional.empty();
                }
            }
        }
        }
    };
    public void conditionsAdd(List<MultiCondition<M,K,V,I,T>> current){
        current.add(condition);
    };
    public void conditionsRemove(List<MultiCondition<M,K,V,I,T>> current){
        current.add(condition);
    };
    public abstract void conditionsWipeForward(List<MultiCondition<M,K,V,I,T>> current);
    public abstract void conditionsWipeBackward(List<MultiCondition<M,K,V,I,T>> current);
    public void conditionsModifyKey(List<MultiCondition<M,K,V,I,T>> current){
        current.add(condition);
    };
    public void conditionsModifyValue(List<MultiCondition<M,K,V,I,T>> current){
        current.add(condition);
    };


    protected void onAddEntry(Delta addType, K key, V value){}
    protected void onReplaceEntry(Delta replaceType, K key, V oldValue, V value){}
    protected void onRemoveEntry(WipeType wipe, K... key){};
    protected void onBuildMap(){};
    protected void onBuildMapStep(M stepChange, Map<K,V> map){};
    protected void onRemoveEntryStep(M stepChange, List<K> removed){};
    protected void onRemovedEntryStep(M stepChange, List<K> removed){};
    protected void onCacheInvalidation(M initiator, Map<K, Delta> changes){}
    protected void onMapEntryChange(Delta type, K key, V value){};
    public final boolean contains(K k){
        return activeChanges.containsKey(k);
    }
    public final K getYourCopy(K yourK){
        AtomicReference<K> myK = new AtomicReference<>();
        try {
            activeChanges.forEach((k, v) -> {
                if (k.getID().equals(yourK.getID())) {
                    myK.set(k);
                    throw new IllegalStateException("Duplicate key");
                }
            });
        } catch (IllegalAccessError ignored){}
        return myK.get();
    }
    @Override
    public final void complete(Sandbox<? extends T> sandbox, SandboxFunction<? extends T> function, SandboxCode code) {
        super.complete(sandbox, function, code);
        cascadeInvalidate((M) this, buildChangeTypes(new ArrayList<>(activeChanges.keySet())));
    }
    @Override
    public void link(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
        super.link(entity, currentState);
        fullMap.clear();
    }

    @Override
    public final void advanceStage(DMEReference<? extends T> entity, TimelineState<? extends T> currentState, boolean isFirstAdvance) {
        super.advanceStage(entity,currentState, isFirstAdvance);
    }

    @Override
    public final void continueSearch(DMEReference<? extends T> entity, TimelineState<? extends T> currentState, TimelineChange<?> oldChange) {
        super.continueSearch(entity, currentState, oldChange);
    }
    public void addListener(Listener<K,V> listener){
        listeners.add(listener);
    }

    @Override
    public final void nullify(DMEReference<? extends T> entity, TimelineState<? extends T> state, TimelineChange<?> changeToNullify) {
        super.nullify(entity, state, changeToNullify);
    }

    @Override
    public final void sandboxInit(Sandbox<? extends T> sandbox) {
        super.sandboxInit(sandbox);
    }

    @Override
    public final void reactivate(boolean isSandbox) {
        super.reactivate(isSandbox);
        cascadeInvalidate((M) this,null);
    }

    @Override
    public final void deactivate(boolean isSandbox) {
        cascadeInvalidate((M) this,null);
        super.deactivate(isSandbox);
    }
    @Override
    public List<Class<TimelineChange<? super T>>> oppositeChanges() {
        return List.of();
    }

    @Override
    public List<Class<TimelineChange<? super T>>> siblingChanges() {
        return super.siblingChanges();
    }
    @Override
    public boolean isPositive() {
        return true;
    }
    public final MiddlemanMap<M,K,V,I,T> getFullMap(){
        return new MiddlemanMap<>((M) this);
    }
    public final Map<K,V> internalGetFull(){
        return fullMap.get();
    }
    public final Map<K,V> internalGetFull(M original){
        if(fullMap.isMemoized()){
            return fullMap.get();
        } else {
            Map<K,V> map = buildMap((M)this,activeChanges,original);
            fullMap.set(map);
            return map;
        }
    }
    protected void internalInvalidate(){
        fullMap.clear();
    }
    private Map<K,V> buildFull(){
        return buildMap((M) this, this.getActive(),null);
    }
    public V get(K key){
        return internalGetFull().get(key);
    }
    protected void put(boolean sandbox, boolean wipeForward, Map<? extends K,? extends V> changes){
        List<Pair<K,V>> pairs = new ArrayList<>(changes.size());
        for(Map.Entry<? extends K,? extends V> entry : changes.entrySet()){
            pairs.add(Pair.of(entry.getKey(),entry.getValue()));
        }
        put(sandbox,wipeForward,pairs.toArray(new Pair[pairs.size()]));
    }
    protected void put(boolean sandbox, boolean wipeForward, Pair<K,V>... changes){
        Delta addChange = wipeForward ? Delta.ADD_WIPE : Delta.ADD;
        Delta modifyChange = wipeForward ? Delta.MODIFY_BOTH_WIPE : Delta.MODIFY_BOTH;
        if(!sandbox){
            List<K> keys = new ArrayList<>(changes.length);
            for(Pair<K,V> p : changes){
                doAddChange(addChange,p);
                keys.add(p.getKey());
            }
            if(wipeForward){
                removeEntry((M) this, FORWARD, true, false, new ArrayList<>(Arrays.stream(changes).map(Pair::getKey).toList()));
            }
            M m = (M) this;
            cascadeInvalidate(m, buildChangeTypes(keys,wipeForward ? Delta.ADD_WIPE : Delta.ADD));
        }else {
            doAddChangeSandbox(addChange,modifyChange,changes);
        }

    }
    private void doAddChange(Delta addChange, Pair<K,V> pair){
        final K key = pair.getKey();
        final V value = pair.getValue();
        if(!this.activeChanges.containsKey(key)){
            onAddEntry(addChange, key, value);
            for(Listener<K,V> listener : listeners){
                listener.onMapPut(key,value);
            }
        } else {
            final V oldV = this.activeChanges.get(key);
            onReplaceEntry(addChange, key, oldV,value);
            for(Listener<K,V> listener : listeners){
                listener.onMapReplace(key,oldV,value);
            }
        }
        this.activeChanges.put(key,value);
        if (endingChanges.contains(key.getID())) {
            endingChanges.remove(key.getID());
        }

    }
    private void doAddChangeSandbox(Delta addChange, Delta modifyChange, Pair<K,V>... changes){
        Map<K, Delta> ct = new HashMap<>();
        Map<Pair<K, V>, Delta> sandboxChanges = new HashMap<>();
        for (Pair<K, V> p : changes) {
            K key = p.getKey();
            if (!this.activeChanges.containsKey(key)) {
                sandboxChanges.put(p, addChange);
                ct.put(key, addChange);
                onAddEntry(addChange, key, p.getValue());
            } else {
                ct.put(key, modifyChange);
                sandboxChanges.put(p, modifyChange);
            }
        }
        M m = (M) this;
        cascadeInvalidate(m, ct);
        Objective<T> t = new Objective<>(getOwner(), FORWARD, (M) this, new MultiChangeSandbox<>(sandboxChanges, listeners));
        SandboxHandler.StartSandbox(t);
    }
    protected final void changed(boolean sandbox, boolean wipe, ChangeType value, List<ChangeContainer<M,K,V,I,T>> containers){
        Map<Pair<K,V>, Delta> changeMap = new HashMap<>();
        List<K> toAdd = new ArrayList<>();
        for(ChangeContainer<M,K,V,I,T> cont : containers){
            K ke = cont.getCurrentKey((M)this);
            V va = cont.getCurrentValue((M)this);
            Delta de = wipe ? value.getDeltaWipe() : value.getDelta();
            changeMap.put(Pair.of(ke,va),de);
            if(!sandbox){
                onMapEntryChange(de,ke,va);
                cont.apply(ke,va);
            }
        }
        if(!changeMap.isEmpty()){
            M m = (M) this;
            cascadeInvalidate(m,buildChangeTypes(toAdd,value.getDelta()));
            if (sandbox) {
                Objective<T> t = new Objective<>(getOwner(), FORWARD, (M) this, new MultiChangeSandbox<>(changeMap,listeners));
                SandboxHandler.StartSandbox(t);
            }
        }
    }
    protected final void remove(boolean sandbox, WipeType wipe, K... key) {
        if (key.length == 0) {
            return;
        }
        final Map<Pair<K,V>, Delta>  changes;
            if (wipe == WipeType.NO_WIPE) {
                changes = removeRegular(sandbox,key);
            } else {
                changes = removeWipe(sandbox,wipe,key);
            }
        if (!sandbox){
            Multimap<WipeType,Pair<K,V>> map = HashMultimap.create();
            for(Pair<K,V> p : changes.keySet()){
                WipeType type = changes.get(p).getWipeType();
                map.put(type, p);
            }
            doRemove(map);
        } else {
            Objective<T> t = new Objective<>(getOwner(), wipe.getDirection(), (M) this, new MultiChangeSandbox<>(changes, listeners));
            SandboxHandler.StartSandbox(t);
        }
    }
    private void doRemove(Multimap<WipeType,Pair<K,V>> map) {
        for (WipeType wipe : map.keySet()) {
            boolean isWipe = wipe != WipeType.NO_WIPE;
            List<Pair<K,V>> toRemove = new ArrayList<>(map.get(wipe));
            if(!toRemove.isEmpty()){
                List<K> removeKey = new ArrayList<>(toRemove.size());
                for (Pair<K,V> p : toRemove){
                    this.onRemoveEntry(wipe, p.getKey());
                    for(TLMultiChange.Listener<K,V> listener : listeners){
                        listener.onMapRemove(p.getKey(), p.getValue(),wipe);
                    }
                    removeKey.add(p.getKey());
                }
                removeEntry((M)this,wipe.getDirection(),isWipe,true,removeKey);
            }
        }
    }
    private Map<Pair<K,V>, Delta> removeRegular(boolean sandbox, K... key){
        Map<K, Delta> cacheChanges = new HashMap<>();
        Map<Pair<K,V>, Delta> sandboxChanges = new HashMap<>();
        List<K> toFind = new ArrayList<>();
        for (K k : key) {
            if (activeChanges.containsKey(k)) {
                V v = activeChanges.get(k);
                sandboxChanges.put(Pair.of(k, v), Delta.REMOVE);
                cacheChanges.put(k, Delta.REMOVE);
            } else {
                toFind.add(k);
            }
        }
        if (!toFind.isEmpty()) {
            M m = (M) TimelineObject.getChangeStep(this, BACKWARD,false,0, null);
            m.remove(sandbox,WipeType.NO_WIPE, (K[]) toFind.toArray(Identifiable[]::new));
        }
        cascadeInvalidate((M) this, cacheChanges);
        return sandboxChanges;
    }
    private Map<Pair<K,V>, Delta> removeWipe(boolean sandbox, WipeType wipe, K... key){
        Map<K, Delta> cacheChanges = new HashMap<>();
        Map<Pair<K,V>, Delta> sandboxChanges = new HashMap<>();
        List<K> toFind = new ArrayList<>(Arrays.stream(key).toList());
        final Delta ct = switch (wipe) {
            case FORWARD -> Delta.REMOVE_WIPE_FORWARD;
            case BACKWARD -> Delta.REMOVE_WIPE_BACKWARD;
            case BOTH -> Delta.REMOVE_WIPE_BOTH;
            default -> Delta.REMOVE;
        };
        for (K k : key) {
            if (!activeChanges.containsKey(k)) {
                toFind.add(k);
                continue;
            }
            V v = activeChanges.get(k);
            sandboxChanges.put(Pair.of(k, v), ct);
            cacheChanges.put(k, ct);
        }
        if (!toFind.isEmpty()) {
            M m = (M) TimelineObject.getChangeStep(this, BACKWARD,false,0, null);
            m.remove(sandbox,wipe, (K[]) toFind.toArray(Identifiable[]::new));
        }
        return sandboxChanges;
    }

    protected final Map<K,V> getActive(){
        return new HashMap<>(activeChanges);
    };

    protected final List<Listener<K,V>> getListeners(){
        return new ArrayList<>(listeners);
    }
    protected final int getEndingSize(){
        return endingChanges.size();
    }
    public Set<I> getEndingChanges(){
        return new HashSet<>(endingChanges);
    }

    protected void internalAddEntry(K k, V v){
        activeChanges.put(k,v);
        fullMap.clear();
    }

    protected void internalRemoveEnding(K k){
        endingChanges.remove(k.getID());
        fullMap.clear();
    }
    protected void internalAddEnding(K k){
        endingChanges.add(k.getID());
        fullMap.clear();
    }
    protected void internalRemoveChange(K k){
        activeChanges.remove(k);
        fullMap.clear();
    }
    protected Map<K, Delta> buildChangeTypes(List<K> keys){
        return buildChangeTypes(keys,null);
    }
    protected Map<K, Delta> buildChangeTypes(List<K> keys, @Nullable Delta type){
        Map<K, Delta> toReturn = new HashMap<>();
        if (type == null){
            for(K k : keys){
                if(hasEntry(BACKWARD,(M) this,k,false)){
                    toReturn.put(k, Delta.MODIFY_VALUE);
                }else if(getActive().containsKey(k)){
                    toReturn.put(k, Delta.ADD);
                } else {
                    toReturn.put(k, Delta.REMOVE);
                }
            }
        } else {
            for (K k : keys){
                toReturn.put(k,type);
            }
        }
        return toReturn;
    }



    protected static <M extends TLMultiChange<M, K,V,I,T>, K extends Identifiable<I>,V,I, T extends DateMutableEntity<T>> void removeEntry(M change, final Global.TimeDirection direction, final boolean wipe, final boolean includeCurrent, List<K> toRemove){
        MapTask<M,K,V,I,T> consumer = new MapTask<>(change) {
            @Override
            protected void onStep(M main, M current, Map<K, V> finalMap, Set<I> endingChanges) {
                List<K> removed = new ArrayList<>();
                for(K k : new ArrayList<>(toRemove)){
                    if(current.contains(k)) {
                        current.internalRemoveChange(k);
                        if (!wipe) {
                            toRemove.remove(k);
                            current.internalAddEnding(k);
                        } else {
                            current.internalRemoveEnding(k);
                        }
                        removed.add(k);
                    }
                }
                if (!removed.isEmpty()){
                    current.onRemovedEntryStep(main,new ArrayList<>(removed));
                    main.onRemoveEntryStep(current,new ArrayList<>(removed));
                }
            }

            @Override
            protected boolean checkComplete(TimelineState<? extends T> currentState, M requester, Optional<M> currentChange, Map<K, V> finalMap) {
                return currentState == null;
            }
        };
        Timeline.iterateMap(change,direction,consumer,includeCurrent);
    }

    protected static <M extends TLMultiChange<M,K,V,I,T>,K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>> Map<K,V> getOtherMap(M change, Global.TimeDirection direction, @Nullable Predicate<M> additional, int steps){
        M desiredChange = TimelineObject.getChangeStep(change, direction, false, steps, additional);
        if (desiredChange == null){
            return null;
        }
        return desiredChange.internalGetFull();
    }
    protected static <M extends TLMultiChange<M,K,V,I,T>, K extends Identifiable<I>,V,I, T extends DateMutableEntity<T>> boolean hasPastEntry(M change, K key, boolean includeCurrent){
        return hasEntry(Global.TimeDirection.BACKWARD,change,key,includeCurrent);
    }
    protected static <M extends TLMultiChange<M,K,V,I,T>, K extends Identifiable<I>,V,I, T extends DateMutableEntity<T>> boolean hasFutureEntry(M change, K key, boolean includeCurrent){
        return hasEntry(Global.TimeDirection.FORWARD,change,key,includeCurrent);
    }
    protected static <M extends TLMultiChange<M,K,V,I,T>, K extends Identifiable<I>,V,I, T extends DateMutableEntity<T>> boolean hasEntry(M change, K key, boolean includeCurrent){
        return hasEntry(FORWARD,change,key,includeCurrent) || hasEntry(BACKWARD,change,key,includeCurrent);
    }
    private static <M extends TLMultiChange<M,K,V,I,T>, K extends Identifiable<I>,V,I, T extends DateMutableEntity<T>> boolean hasEntry(Global.TimeDirection direction, M change, final K key, boolean includeCurrent){
        AtomicBoolean toReturn = new AtomicBoolean(false);
        MapTask<M,K,V,I,T> step = new MapTask<>(change) {
            @Override
            public void onStep(M requester, M current, Map<K, V> finalMap, Set<I> endingChanges) {
                if (current.contains(key)){
                    toReturn.set(true);
                }
            }
            @Override
            protected boolean checkComplete(TimelineState<? extends T> currentState, M requester, Optional<M> currentChange, Map<K, V> finalMap) {
                return currentState == null || toReturn.get();
            }
        };
        TimelineObject.iterateMap(change,direction,step,includeCurrent);
        return toReturn.get();

    }
    protected void cascadeInvalidate(M change, @Nullable final Map<K, Delta> changes){
        cascadeInvalidate(change,changes,pauseCacheChecks.get());
    }
    protected static <M extends TLMultiChange<M,K,V,I,T>,K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>> void cascadeInvalidate(M change, @Nullable final Map<K, Delta> changes, boolean isPaused){
        final boolean bypass = changes == null;
        final Map<K, Delta> finalChanges;
        if (bypass){
            finalChanges = new HashMap<>();
        } else if(changes.isEmpty() || isPaused){
            return;
        } else {
            finalChanges = new HashMap<>(changes);
        }



        MapTask<M,K,V,I,T> task = new MapTask<>(change) {
            @Override
            protected void onStep(M main, M current, Map<K, V> finalMap, Set<I> endingChanges) {
                boolean invalidate = false;
                if (!bypass){
                    for (Map.Entry<K, Delta> entry : new ArrayList<>(finalChanges.entrySet())) {
                        Delta type = entry.getValue();
                        boolean should = current.shouldInvalidate(main, entry.getKey(), type);
                        if (!should){
                            finalChanges.remove(entry.getKey());
                        } else {
                            invalidate = true;
                            //I know we could break here, but I want to check everything to catch end cases.
                        }
                    }
                } else {
                    invalidate = true;
                }
                if (invalidate){
                    current.internalInvalidate();
                    current.onCacheInvalidation(main,finalChanges);
                }
            }

            @Override
            protected boolean checkComplete(TimelineState<? extends T> currentState, M requester, Optional<M> currentChange, Map<K, V> finalMap) {
                return currentState == null;
            }
        };
        TimelineObject.iterateMap(change,FORWARD,task,true);
    }


    protected static <M extends TLMultiChange<M,K,V,I,T>,K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>> Map<K,V> buildMap(M change, Map<K,V> starting, @Nullable M original){
        change.onBuildMap();
        if(original == null){
            original = change;
        }
        final List<I> blacklist = new ArrayList<>(change.getEndingChanges());
        M leapfrogged = TimelineObject.getChangeStep(change,BACKWARD,false,0,null);
        Map<K,V> lf = leapfrogged.internalGetFull(original);
        Map<K,V> toReturn = new HashMap<>();
        for (Map.Entry<K,V> entry : lf.entrySet()){
            K key = entry.getKey();
            if(!starting.containsKey(key) && !blacklist.contains(key.getID())){
                toReturn.put(key,entry.getValue());
            }
        }
        original.onBuildMapStep(leapfrogged,toReturn);
        toReturn.putAll(starting);
        return toReturn;
    }



    public static abstract class MapTask<M extends TLMultiChange<M,K,V,I,T>, K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>>{
        private final M main;
        private final Map<K,V> map;
        public MapTask(M main){
            this.main = main;
            this.map = new HashMap<>();
        }
        public MapTask(M main, Map<K,V> map){
            this.main = main;
            this.map = map;
        }
        public final void step(M change){
            onStep(main,change,map,change.getEndingChanges());
        }
        protected abstract void onStep(M main, M current, Map<K,V> finalMap, Set<I> endingChanges);
        public boolean isComplete(TimelineState<? extends T> currentState, @Nullable M currentChange){
            return checkComplete(currentState,main,Optional.ofNullable(currentChange),map);
        }
        protected abstract boolean checkComplete(TimelineState<? extends T> currentState, M requester, Optional<M> currentChange, Map<K,V> finalMap);
    }
//==== SERIALIZERS ====
    protected abstract JsonElement kSerialize(K k);
    protected abstract K kDeserialize(JsonElement o);
    protected abstract JsonElement vSerialize(V v);
    protected abstract V vDeserialize(JsonElement o);
    protected abstract JsonElement iSerialize(I i);
    protected abstract I iDeserialize(JsonElement o);
    public K deepCopyK(K k){
        return kDeserialize(kSerialize(k));
    }
    public V deepCopyV(V v){
        return vDeserialize(vSerialize(v));
    }
    private JsonArray serializeEndList(){
        JsonArray array = new JsonArray();
        for (I id : endingChanges){
            array.add(iSerialize(id));
        }
        return array;
    }
    private Set<I> buildIList(JsonArray a){
        Set<I> list = new HashSet<>();
        for (JsonElement e : a){
            list.add(this.iDeserialize(e));
        }
        return list;
    }
    private Map<K,V> buildMap(JsonArray a){
        Map<K,V> map = new HashMap<>();
        for (JsonElement e : a){
            JsonObject o = e.getAsJsonObject();
            K k = this.kDeserialize(o.get("k"));
            V v = this.vDeserialize(o.get("v"));
            map.put(k,v);
        }
        return map;
    }
    private JsonArray serializeMap(Map<K,V> map){
        JsonArray array = new JsonArray();
        for (Map.Entry<K,V> entry : map.entrySet()){
            JsonObject o = new JsonObject();
            o.add("k",kSerialize(entry.getKey()));
            o.add("v",vSerialize(entry.getValue()));
            array.add(o);
        }
        return array;
    }
    @Override
    public final void mainSave(JsonObject o) {
        super.mainSave(o);
        JsonObject TMCData = new JsonObject();
        TMCData.add("active",serializeMap(getActive()));
        TMCData.add("endChange",serializeEndList());
        o.add("TMCData",TMCData);
    }
    @Override
    public final void mainLoad(JsonObject object) {
        super.mainLoad(object);
        JsonObject TMCData = object.getAsJsonObject("TMCData");
        endingChanges.clear();
        endingChanges.addAll(buildIList(TMCData.get("endChange").getAsJsonArray()));
        pauseCacheChecks.set(true);
        try {
            activeChanges.clear();
            activeChanges.putAll(buildMap(TMCData.get("active").getAsJsonArray()));
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            pauseCacheChecks.set(false);
        }

        //TODO, have the first change call invalidate caches to invalidate every cache on load.
    }

    @Override
    protected void applyConditions(@MonotonicNonNull Set<ApplyCondition<? super T>> list) {
        list.add(new CanMerge());
    }
    public void mergeSafe(TLMultiChange<?,?,?,?,?> other){
        merge((M) other);
    }
    public void merge(M other){
        this.activeChanges.putAll(other.getActive());
    };
    public static abstract class Listener<K extends Identifiable<?>,V>{
        public abstract void onMapPut(K key, V value);
        public abstract void onMapRemove(K key, V value, WipeType type);
        public abstract void onMapReplace(K key, V oldValue, V newValue);
        public abstract void onMapClear();
        public abstract void onMapGet(K key, V value);
        public abstract void onMapChange(Delta type, K key, V value);
    }
    public static class ChangeContainer<M extends TLMultiChange<M,K,V,I,T>, K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>>{
        private final BiConsumer<K,V> applyChange;
        private final Function<M,K> currentKey;
        private final Function<M,V> currentValue;
        public ChangeContainer(BiConsumer<K,V> applyChange, Function<M,K> currentKey, Function<M,V> currentValue){
            this.applyChange = applyChange;
            this.currentKey = currentKey;
            this.currentValue = currentValue;
        }
        public static <M extends TLMultiChange<M,K,V,I,T>, K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>> ChangeContainer<M,K,V,I,T> of(M change, BiConsumer<K,V> applyChange, K currentKey){
            return new ChangeContainer<>(applyChange, (m) ->{return currentKey;}, (m)-> {return change.get(currentKey);});
        }
        public static <M extends TLMultiChange<M,K,V,I,T>, K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>> ChangeContainer<M,K,V,I,T> of(M change, BiConsumer<K,V> applyChange, K currentKey, V currentValue){
            return new ChangeContainer<>(applyChange, (m) ->{return currentKey;}, (m)-> {return currentValue;});
        }
        public K getCurrentKey(M change){
            return currentKey.apply(change);
        }
        public V getCurrentValue(M change){
            return currentValue.apply(change);
        }
        public void apply(K k, V v){
            applyChange.accept(k, v);
        }
    }
    public class CanMerge extends ApplyCondition<T>{

        public CanMerge() {
            super("multi_merge");
        }

        @Override
        protected Optional<StateError> doCheck(DMEReference<? extends T> entity, TimelineChange<? extends T> thisChange, TimelineChange<?> checkAgainst) {
            if (thisChange.getClass().equals(checkAgainst.getClass())){
                return Optional.of(new StateError("merge_multi", SimpleReference.of("Merge the two states"),checkAgainst).addMergeContinue());
            }
            return Optional.empty();
        }

        @Override
        public ShouldRun whenToRun() {
            return ShouldRun.WHOLE_STATE_PER_ENTITY;
        }

    }
}
