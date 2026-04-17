package com.base.timeline.change.multi;

import com.Global;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.reference.SimpleReference;
import com.base.timeline.Timeline;
import com.base.timeline.TimelineObject;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.condition.apply.ApplyCondition;
import com.base.timeline.change.condition.nullify.NullifyCondition;
import com.base.timeline.error.SandboxCode;
import com.base.timeline.error.StateError;
import com.base.timeline.sandbox.core.Objective;
import com.base.timeline.sandbox.core.Sandbox;
import com.base.timeline.sandbox.core.SandboxHandler;
import com.base.timeline.sandbox.function.SandboxFunction;
import com.base.timeline.state.TimelineState;
import com.base.utilities.CachingSupplier;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.utilities.id.Identifiable;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

import static com.Global.TimeDirection.BACKWARD;
import static com.Global.TimeDirection.FORWARD;

@SuppressWarnings("unchecked")
public abstract class TimelineMultiChange<M extends TimelineMultiChange<M,K,V,I,T>, K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>> extends TimelineChange<T> {
    //Use of atomics here isn't an indication of thread safety per-say. I just needed a container for bools and ints. I know there are probably better ones. I don't really care
    //right now. I just want to get this working for the moment.
    public static final Logger LOGGER = LoggerFactory.getLogger(TimelineMultiChange.class);
    public Logger logger(){
        return LOGGER;   
    }
    public enum WipeType {
        FORWARD(Global.TimeDirection.FORWARD),
        BACKWARD(Global.TimeDirection.BACKWARD),
        BOTH,
        NO_WIPE;

        private final Global.TimeDirection direction;
        public Global.TimeDirection getDirection(){
            return direction;
        }
        WipeType(Global.TimeDirection direction){
            this.direction = direction;
        }
        WipeType(){
            this.direction = Global.TimeDirection.FORWARD;
        }
    }
    public enum ChangeEntry {
        KEY(ChangeType.MODIFY_KEY,ChangeType.MODIFY_KEY_WIPE),
        VALUE(ChangeType.MODIFY_VALUE,ChangeType.MODIFY_VALUE_WIPE),
        BOTH(ChangeType.MODIFY_BOTH,ChangeType.MODIFY_BOTH_WIPE);

        private final ChangeType type;
        private final ChangeType typeWipe;
        ChangeEntry(ChangeType type,ChangeType typeWipe){
            this.type = type;
            this.typeWipe = typeWipe;
        }
        public ChangeType getType(){
            return type;
        }
        public ChangeType getTypeWipe(){
            return typeWipe;
        }
    }
    public enum ChangeType{
        ADD,
        ADD_WIPE(WipeType.FORWARD),
        REMOVE,
        REMOVE_WIPE_FORWARD(WipeType.FORWARD),
        REMOVE_WIPE_BACKWARD(WipeType.BACKWARD),
        REMOVE_WIPE_BOTH(WipeType.BOTH),
        MODIFY_BOTH,
        MODIFY_KEY,
        MODIFY_VALUE,
        MODIFY_BOTH_WIPE(WipeType.FORWARD),
        MODIFY_KEY_WIPE(WipeType.FORWARD),
        MODIFY_VALUE_WIPE(WipeType.FORWARD);

        private final WipeType wipeType;
        public WipeType getWipeType(){
            return wipeType;
        }
        ChangeType(WipeType wipeType){
            this.wipeType = wipeType;
        }
        ChangeType(){
            this.wipeType = WipeType.NO_WIPE;
        }
    }

    private final Set<I> endingChanges = new HashSet<>();
    private final Map<K,V> activeChanges;
    private final List<Listener<K,V>> listeners = new ArrayList<>();


    private final CachingSupplier<Map<K,V>,M> fullMap = new CachingSupplier<>(this::buildFull,null);
    protected final AtomicBoolean pauseCacheChecks = new AtomicBoolean(false);

    protected TimelineMultiChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
        activeChanges = new HashMap<>();
    }
    protected TimelineMultiChange(DMEReference<? extends T> owner, LocalDate date, Map<K,V> initial) {
        super(owner, date);
        activeChanges = new HashMap<>(initial);
    }
    public int getActiveSize(){
        return  getActive().size();
    }

    public boolean shouldInvalidate(M initiator, K key, ChangeType type){
        if (!fullMap.isMemoized()){
            return false;
        } else if(endingChanges.contains(key.getID())) {
            return false;
        } else return type != ChangeType.MODIFY_VALUE || !(initiator.getActive().get(key) == this.internalGetFull().get(key));
    };

    protected boolean canAdd(K key, V value, @Nullable K currentKey,@Nullable V currentValue){
        return true;
    }

    @Override
    protected final void nullifyConditions(List<NullifyCondition<? super T>> list) {

    }
    public abstract M getEmptyChange(DMEReference<? extends T> owner, LocalDate date);
    public abstract boolean hasEndingChanges();
    private final MultiCondition<M,K,V,I,T> condition = new MultiCondition<>() {
        @Override
        protected Optional<StateError> doCheck(TimelineMultiChange.ChangeType change,M newChange, List<Pair<K, V>> newEntries, M curChange, List<Pair<K, V>> curEntries) {
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
    public void addConditions(List<MultiCondition<M,K,V,I,T>> current){
        current.add(condition);
    };
    public void removeConditions(List<MultiCondition<M,K,V,I,T>> current){
        current.add(condition);
    };
    public abstract void removeWipeFConditions(List<MultiCondition<M,K,V,I,T>> current);
    public abstract void removeWipeBConditions(List<MultiCondition<M,K,V,I,T>> current);
    public void modifyKeyConditions(List<MultiCondition<M,K,V,I,T>> current){
        current.add(condition);
    };
    public void modifyValueConditions(List<MultiCondition<M,K,V,I,T>> current){
        current.add(condition);
    };


    protected void onAddEntry(ChangeType addType, K key, V value){}
    protected void onReplaceEntry(ChangeType replaceType, K key, V oldValue, V value){}
    protected void onRemoveEntry(WipeType wipe, K... key){};
    protected void onBuildMap(){};
    protected void onBuildMapStep(M stepChange, Map<K,V> map){};
    protected void onRemoveEntryStep(M stepChange, List<K> removed){};
    protected void onRemovedEntryStep(M stepChange, List<K> removed){};
    protected void onCacheInvalidation(M initiator, Map<K,ChangeType> changes){}
    protected void onMapEntryChange(ChangeType type, K key, V value){};
    public final boolean contains(K k){
        return activeChanges.containsKey(k);
    }

    @Override
    public final void complete(Sandbox<? extends T> sandbox, SandboxFunction<? extends T> function, SandboxCode code) {
        super.complete(sandbox, function, code);
        cascadeInvalidate((M) this, buildChangeTypes(new ArrayList<>(activeChanges.keySet())));
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
    public final TimelineMap<K,V,T> getFullMap(){
        return new TimelineMap<>(this);
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
        fullMap.clear((M) this);
    }
    private Map<K,V> buildFull(){
        return buildMap((M) this, this.getActive(),null);
    }
    public V get(K key){
        return getFullMap().get(key);
    }
    protected void addChange(boolean sandbox, boolean wipeForward, K... changes){

        addChange(sandbox,wipeForward,changes);
    }
    protected void addChange(boolean sandbox, boolean wipeForward, Map<? extends K,? extends V> changes){
        List<Pair<K,V>> pairs = new ArrayList<>(changes.size());
        for(Map.Entry<? extends K,? extends V> entry : changes.entrySet()){
            pairs.add(Pair.of(entry.getKey(),entry.getValue()));
        }
        addChange(sandbox,wipeForward,pairs.toArray(new Pair[pairs.size()]));
    }
    protected void addChange(boolean sandbox, boolean wipeForward, Pair<K,V>... changes){
        ChangeType addChange;
        ChangeType modifyChange;
        if(wipeForward){
            addChange = ChangeType.ADD_WIPE;
            modifyChange = ChangeType.MODIFY_BOTH_WIPE;
        } else {
            addChange = ChangeType.ADD;
            modifyChange = ChangeType.MODIFY_BOTH;
        }
        if(!sandbox){
            for(Pair<K,V> pair : changes){
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
        }else {
            Map<K, ChangeType> ct = new HashMap<>();
            Map<Pair<K, V>, ChangeType> sandboxChanges = new HashMap<>();
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
    }
    public final void setChanged(boolean sandbox, ChangeEntry value, K... key){
        Map<Pair<K,V>,ChangeType> changeMap = new HashMap<>();
        for(K k : key){
            changeMap.put(Pair.of(k,activeChanges.get(k)),value.getType());
            if(!sandbox){
                onMapEntryChange(value.getType(),k,activeChanges.get(k));
            }
        }
        if(!changeMap.isEmpty()){
            M m = (M) this;
            List<K> toAdd = new ArrayList<>(Arrays.stream(key).toList());
            cascadeInvalidate(m,buildChangeTypes(toAdd,value.getType()));
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
        final Map<Pair<K,V>,ChangeType>  changes;
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
                    for(TimelineMultiChange.Listener<K,V> listener : listeners){
                        listener.onMapRemove(p.getKey(), p.getValue(),wipe);
                    }
                    removeKey.add(p.getKey());
                }
                removeEntry((M)this,wipe.getDirection(),isWipe,true,removeKey);
            }
        }
    }
    
    
    
    private Map<Pair<K,V>,ChangeType> removeRegular(boolean sandbox, K... key){
        Map<K,ChangeType> cacheChanges = new HashMap<>();
        Map<Pair<K,V>,ChangeType> sandboxChanges = new HashMap<>();
        List<K> toFind = new ArrayList<>();
        for (K k : key) {
            if (activeChanges.containsKey(k)) {
                V v = activeChanges.get(k);
                sandboxChanges.put(Pair.of(k, v), ChangeType.REMOVE);
                cacheChanges.put(k, ChangeType.REMOVE);
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
    private Map<Pair<K,V>,ChangeType> removeWipe(boolean sandbox, WipeType wipe, K... key){
        Map<K,ChangeType> cacheChanges = new HashMap<>();
        Map<Pair<K,V>,ChangeType> sandboxChanges = new HashMap<>();
        List<K> toFind = new ArrayList<>(Arrays.stream(key).toList());
        final ChangeType ct = switch (wipe) {
            case FORWARD -> ChangeType.REMOVE_WIPE_FORWARD;
            case BACKWARD -> ChangeType.REMOVE_WIPE_BACKWARD;
            case BOTH -> ChangeType.REMOVE_WIPE_BOTH;
            default -> ChangeType.REMOVE;
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
    protected Map<K,ChangeType> buildChangeTypes(List<K> keys){
        return buildChangeTypes(keys,null);
    }
    protected Map<K,ChangeType> buildChangeTypes(List<K> keys, @Nullable ChangeType type){
        Map<K,ChangeType> toReturn = new HashMap<>();
        if (type == null){
            for(K k : keys){
                if(hasEntry(BACKWARD,(M) this,k,false)){
                    toReturn.put(k,ChangeType.MODIFY_VALUE);
                }else if(getActive().containsKey(k)){
                    toReturn.put(k,ChangeType.ADD);
                } else {
                    toReturn.put(k,ChangeType.REMOVE);
                }
            }
        } else {
            for (K k : keys){
                toReturn.put(k,type);
            }
        }
        return toReturn;
    }



    protected static <M extends TimelineMultiChange<M, K,V,I,T>, K extends Identifiable<I>,V,I, T extends DateMutableEntity<T>> void removeEntry(M change, final Global.TimeDirection direction, final boolean wipe, final boolean includeCurrent, List<K> toRemove){
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

    protected static <M extends TimelineMultiChange<M,K,V,I,T>,K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>> Map<K,V> getOtherMap(M change, Global.TimeDirection direction, @Nullable Predicate<M> additional, int steps){
        M desiredChange = TimelineObject.getChangeStep(change, direction, false, steps, additional);
        if (desiredChange == null){
            return null;
        }
        return desiredChange.internalGetFull();
    }
    protected static <M extends TimelineMultiChange<M,K,V,I,T>, K extends Identifiable<I>,V,I, T extends DateMutableEntity<T>> boolean hasPastEntry(M change, K key, boolean includeCurrent){
        return hasEntry(Global.TimeDirection.BACKWARD,change,key,includeCurrent);
    }
    protected static <M extends TimelineMultiChange<M,K,V,I,T>, K extends Identifiable<I>,V,I, T extends DateMutableEntity<T>> boolean hasFutureEntry(M change, K key, boolean includeCurrent){
        return hasEntry(Global.TimeDirection.FORWARD,change,key,includeCurrent);
    }
    protected static <M extends TimelineMultiChange<M,K,V,I,T>, K extends Identifiable<I>,V,I, T extends DateMutableEntity<T>> boolean hasEntry(M change, K key, boolean includeCurrent){
        return hasEntry(FORWARD,change,key,includeCurrent) || hasEntry(BACKWARD,change,key,includeCurrent);
    }
    private static <M extends TimelineMultiChange<M,K,V,I,T>, K extends Identifiable<I>,V,I, T extends DateMutableEntity<T>> boolean hasEntry(Global.TimeDirection direction, M change, final K key, boolean includeCurrent){
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
    protected void cascadeInvalidate(M change, @Nullable final Map<K,ChangeType> changes){
        cascadeInvalidate(change,changes,pauseCacheChecks.get());
    }
    protected static <M extends TimelineMultiChange<M,K,V,I,T>,K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>> void cascadeInvalidate(M change, @Nullable final Map<K,ChangeType> changes, boolean isPaused){
        final boolean bypass = changes == null;
        final Map<K,ChangeType> finalChanges;
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
                    for (Map.Entry<K, ChangeType> entry : new ArrayList<>(finalChanges.entrySet())) {
                        ChangeType type = entry.getValue();
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


    protected static <M extends TimelineMultiChange<M,K,V,I,T>,K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>> Map<K,V> buildMap(M change,Map<K,V> starting, @Nullable M original){
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



    public static abstract class MapTask<M extends TimelineMultiChange<M,K,V,I,T>, K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>>{
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
    protected void applyConditions(List<ApplyCondition<? super T>> list) {
        list.add(new CanMerge());
    }
    public void mergeSafe(TimelineMultiChange<?,?,?,?,?> other){
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
        public abstract void onMapChange(ChangeType type, K key, V value);
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
