package com.base.timeline.change.multi;

import com.Global;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.reference.SimpleReference;
import com.base.timeline.Timeline;
import com.base.timeline.TimelineObject;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.condition.apply.ApplyCondition;
import com.base.timeline.error.SandboxCode;
import com.base.timeline.error.StateError;
import com.base.timeline.sandbox.core.Objective;
import com.base.timeline.sandbox.core.Sandbox;
import com.base.timeline.sandbox.core.SandboxHandler;
import com.base.timeline.sandbox.function.SandboxFunction;
import com.base.timeline.sandbox.function.SandboxFunctions;
import com.base.timeline.state.TimelineState;
import com.base.utilities.CachingSupplier;
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
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.LogManager;

import static com.Global.TimeDirection.BACKWARD;
import static com.Global.TimeDirection.FORWARD;
import static com.base.timeline.error.SandboxCode.END_DISCARD;

@SuppressWarnings("unchecked")
public abstract class TimelineMultiChange<M extends TimelineMultiChange<M,K,V,I,T>, K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>> extends TimelineChange<T> {
    //Use of atomics here isn't an indication of thread safety per-say. I just needed a container for bools and ints. I know there are probably better ones. I don't really care
    //right now. I just want to get this working for the moment.
    public static final Logger LOGGER = LoggerFactory.getLogger(TimelineMultiChange.class);
    public Logger logger(){
        return LOGGER;   
    }
    public enum WipeType {
        FORWARD,
        BACKWARD,
        BOTH,
        NO_WIPE
    }
    public enum ChangeType{
        ADD,
        REMOVE,
        REMOVE_WIPE,
        MODIFY_VALUE,
    }

    private final Set<I> endingChanges = new HashSet<>();
    private final Map<K,V> activeChanges;



    private final CachingSupplier<TimelineMap<K,V,T>,M> fullMap = new CachingSupplier<>(this::buildFull,null);
    protected final AtomicBoolean pauseCacheChecks = new AtomicBoolean(false);

    protected TimelineMultiChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
        activeChanges = buildChangeMap();
    }
    protected TimelineMultiChange(DMEReference<? extends T> owner, LocalDate date, Map<K,V> initial) {
        super(owner, date);
        activeChanges = buildChangeMap(initial);
    }
    protected final Map<K,V> buildChangeMap(){
        return buildChangeMap(new HashMap<>());
    }

    protected final Map<K,V> buildChangeMap(Map<K,V> initial){
        return new TimelineMap<>(this,initial);
    }
    public int getActiveSize(){
        return  getActive().size();
    }
    public abstract boolean hasEndingChanges();
    public boolean shouldInvalidate(M initiator, K key, ChangeType type){
        if (!fullMap.isMemoized()){
            return false;
        } else if(endingChanges.contains(key.getID())) {
            return false;
        } else return type != ChangeType.MODIFY_VALUE || !(initiator.getActive().get(key) == this.getFullMap().get(key));
    };
    protected void onRemoveEntry(WipeType wipe, K... key){};
    protected void onBuildMap(){};
    protected void onBuildMapStep(M stepChange, Map<K,V> map){};
    protected void onRemoveEntryStep(M stepChange, List<K> removed){};
    protected void onRemovedEntryStep(M stepChange, List<K> removed){};
    protected void onCacheInvalidation(M initiator, Map<K,ChangeType> changes){}
    public final boolean contains(K k){
        return activeChanges.containsKey(k);
    }
    public final void setChanged(K... key){
        List<K> toAdd = new ArrayList<>(Arrays.stream(key).toList());
        cascadeInvalidate((M) this,buildChangeTypes(toAdd,ChangeType.MODIFY_VALUE));
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
        return fullMap.get();
    }
    public final TimelineMap<K,V,T> getFullMap(M original){
        if (fullMap.isMemoized()){
            return fullMap.get();
        } else {
            return buildMap((M) this,getFullMap(),original);
        }
    }
    protected void internalInvalidate(){
        fullMap.clear((M) this);
    }
    private TimelineMap<K,V,T> buildFull(){
        return buildMap((M) this, this.getActive(),null);
    }
    protected void addChange(Pair<K,V>... changes){
        addChange(true,changes);
    }
    protected void addChange(boolean sandbox, Pair<K,V>... changes){
        List<K> rollback = new ArrayList<>();
        Map<K,V> backup = new HashMap<>();
        Map<K,V> toAdd = new HashMap<>();
        pauseCacheChecks.set(true);
        try {
            for (Pair<K, V> p : changes) {
                K key = p.getKey();
                I id = key.getID();
                if (!this.activeChanges.containsKey(key)) {
                    // this.activeChanges.put(key, p.getValue());
                    rollback.add(key);
                } else {
                    backup.put(key, this.activeChanges.get(key));
                    //this.activeChanges.put(key,p.getValue());
                }
                if (hasEndingChanges() && endingChanges.contains(id)) {
                    endingChanges.remove(key.getID());
                }
                toAdd.put(key, p.getValue());
            }
            activeChanges.putAll(toAdd);
            if (sandbox) {
                Consumer<SandboxCode> code = new Consumer<SandboxCode>() {
                    @Override
                    public void accept(SandboxCode sandboxCode) {
                        if (sandboxCode == END_DISCARD || sandboxCode == SandboxCode.CRITICAL_ERROR) {
                            removeEntry(WipeType.NO_WIPE, (K[]) rollback.toArray(new Identifiable[0]));
                            activeChanges.putAll(backup);
                        }
                    }
                };
                Objective<T> t = new Objective<>(getOwner(), FORWARD, (M) this, new SandboxFunctions.MultiChange<>(code));
                SandboxHandler.StartSandbox(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Map<K,ChangeType> ct = new HashMap<>();
            for (K k : toAdd.keySet()) {
                if(rollback.contains(k)){
                    ct.put(k,ChangeType.ADD);
                } else {
                    ct.put(k,ChangeType.MODIFY_VALUE);
                }
            }
            pauseCacheChecks.set(false);
            cascadeInvalidate((M) this, ct);
        }
    }
    protected final void removeEntry(WipeType wipe, K... key){
        Map<K,ChangeType> toInvalidate = new HashMap<>();
        List<K> toFind;
        if(key.length == 0){
            return;
        }
        onRemoveEntry(wipe,key);
        pauseCacheChecks.set(true);
        try {
            if (wipe == WipeType.NO_WIPE) {
                toFind = new ArrayList<>();
                for (K k : key) {
                    I id = k.getID();
                    if (activeChanges.containsKey(k)) {
                        activeChanges.remove(k);
                        if (hasEndingChanges()) {
                            endingChanges.add(id);
                        }
                    } else {
                        toFind.add(k);
                    }
                    toInvalidate.put(k, ChangeType.REMOVE);
                }
                if (!toFind.isEmpty()) {
                    removeEntry((M) this, BACKWARD, false, true, toFind);
                }
            } else {
                toFind = new ArrayList<>(Arrays.stream(key).toList());
                for (K k : toFind) {
                    activeChanges.remove(k);
                    endingChanges.remove(k.getID());
                    toInvalidate.put(k, ChangeType.REMOVE_WIPE);
                }
                switch (wipe) {
                    case FORWARD:
                        removeEntry((M) this, FORWARD, true, true, toFind);
                        //This is a workaround for the problem described below.
                        break;
                    case BACKWARD:
                        //Not changing effectivesize because the removeEntry will take care of it.
                        //Because removeEntry on backs goes back to the oldest date with the change and adjusts the count dynamically.
                        //That means that the current change would be included in that. Forward doesn't because forward starts at the
                        //Next state after the current one.
                        removeEntry((M) this, BACKWARD, true, true, toFind);
                        break;
                    case BOTH:
                        //Same case as above. The remove entry going back will amend the total properly.
                        removeEntry((M) this, FORWARD, true, false, new ArrayList<>(toFind));
                        removeEntry((M) this, BACKWARD, true, false, new ArrayList<>(toFind));
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pauseCacheChecks.set(false);
            cascadeInvalidate((M) this, toInvalidate);
        }
    }
//    private void getChangeCount(M other){
//        List<I> toFind = new ArrayList<>(startingChanges);
//        for(K k : other.getActive().keySet()){
//            if (toFind.contains(k.getID())){
//                return activeChanges.get(k).getCount();
//            }
//        }
//        return 0;
//    }
    protected final Map<K,V> getActive(){
        return new HashMap<>(activeChanges);
    };


    protected final int getEndingSize(){
        return endingChanges.size();
    }
    public Set<I> getEndingChanges(){
        return new HashSet<>(endingChanges);
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
        return desiredChange.getFullMap();
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


    protected static <M extends TimelineMultiChange<M,K,V,I,T>,K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>> TimelineMap<K,V,T> buildMap(M change,Map<K,V> starting, @Nullable M original){
        change.onBuildMap();
        if(original == null){
            original = change;
        }
        final List<I> blacklist = new ArrayList<>(change.getEndingChanges());
        M leapfrogged = TimelineObject.getChangeStep(change,BACKWARD,false,0,null);
        Map<K,V> lf = leapfrogged.getFullMap(original);
        TimelineMap<K,V,T> toReturn = new TimelineMap<>(change);
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
