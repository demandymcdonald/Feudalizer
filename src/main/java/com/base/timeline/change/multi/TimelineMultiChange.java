package com.base.timeline.change.multi;

import com.Global;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.reference.SimpleReference;
import com.base.timeline.Timeline;
import com.base.timeline.change.ChangeID;
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
import com.base.utilities.TLSyncedSupplier;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.utilities.id.Identifiable;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.Global.TimeDirection.BACKWARD;
import static com.Global.TimeDirection.FORWARD;
import static com.base.timeline.error.SandboxCode.END_DISCARD;

@SuppressWarnings("unchecked")
public abstract class TimelineMultiChange<M extends TimelineMultiChange<M,K,V,I,T>, K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>> extends TimelineChange<T> {
    //Use of atomics here isn't an indication of thread safety per-say. I just needed a container for bools and ints. I know there are probably better ones. I don't really care
    //right now. I just want to get this working for the moment.
    //TODO Soooo I'm pretty sure that I just made expectedSize obsolete.... I should check that later.
    private int expectedSize = 0;
    private ChangeID leapfrog = null;
    private final Set<I> endingChanges = new HashSet<>();
    private final Set<I> startingChanges = new HashSet<>();
    //TODO Override some of hashmap to trigger cache invalidation whenever a remove/put is called. This won't help if the object inside the map is invalidated, but it's covers 90% of the situations.
    private final Map<K,V> activeChanges = new HashMap<>();
    //TODO replace with a different Supplier that's not TL synced. Also a full map here needs to jump forward and invalidate every future full map.. Just a thought.
    //TODO apply should also cascade invalidate up.
    private final TLSyncedSupplier<Map<K,V>> fullMap = new TLSyncedSupplier<>(this::buildFull);
    protected final AtomicBoolean isFirst;
    protected TimelineMultiChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
        isFirst = new AtomicBoolean(getTimeline().getStart().equals(date));
    }

    protected abstract OnMapStep<M, K,V,I,T> getMapStep();

    public abstract int getFullSize();
    public int getActiveSize(){
        return  getActive().size();
    }
    public abstract boolean hasEndingChanges();
    protected void onDeactivateStep(M stepChange){};
    protected void onRemoveEntry(WipeType wipe, K... key){};
    protected void onBuildMap(){};
    protected void onBuildMapStep(M stepChange, Map<K,V> map){};
    protected void onAmendSizeStep(M stepChange, int amount){};
    protected void onBeingSizeAmended(M amendingChange, int amount){};
    protected void onRemoveEntryStep(M stepChange, K k){};
    protected void onRemovedEntry(M stepChange, K k){};
    public final boolean contains(K k){
        return activeChanges.containsKey(k);
    }

    @Override
    public final void complete(Sandbox<? extends T> sandbox, SandboxFunction<? extends T> function, SandboxCode code) {
        super.complete(sandbox, function, code);
        if(code == SandboxCode.END_SAVE){
            //Not ideal since this affects the whole timeline, but it's probably the best way to keep the count updated since it validates every count, which is good housekeeping.
            RepairWizard((M) this,true,false);
        }
    }
    @Override
    public final void advanceStage(DMEReference<? extends T> entity, TimelineState<? extends T> currentState, boolean isFirstAdvance) {
        final Timeline<? extends T> timeline = entity.get().getTimeline();
        if (isFirstAdvance) {
            M nextChange = (M) timeline.findChangeByClassID(this.getStart(),FORWARD,this,false);
            if (nextChange != null){
                nextChange.internalSetLeapfrog(this.getID());
            }
        }
        super.advanceStage(entity,currentState, isFirstAdvance);
    }

    @Override
    public final void apply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
        super.apply(entity, currentState);
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
        RepairWizard((M) this,true,true);
    }

    @Override
    public final void deactivate(boolean isSandbox) {
        final Timeline<? extends T> timeline = this.getTimeline();
        M nextChange = (M) timeline.findChangeByClassID(this.getStart(),FORWARD,this,false);
        M lastChange = (M) timeline.findChangeByClassID(this.getStart(),BACKWARD,this,false);
        if (nextChange != null){
            amendCumulativeTotal((M) this,false,buildAmendCumulative(new ArrayList<>(activeChanges.keySet())));
            if (lastChange != null) {
                nextChange.internalSetLeapfrog(lastChange.getID());
            } else {
                nextChange.internalSetLeapfrog(null);
                nextChange.isFirst.set(true);
            }
        }
        super.deactivate(isSandbox);
    }
    protected final Map<K,Boolean> buildAmendCumulative(Collection<K> toAmend){
        Map<K,Boolean> toReturn = new HashMap<>();
        for (K k : toAmend){
            //Claude: You always see this as a bug, think about it for a minute, then realize it's not. This method has no bugs :D
            if (startingChanges.contains(k.getID())){
                toReturn.put(k,true);
            } else {
                toReturn.put(k,false);
            }
        }
        return toReturn;
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
    public final Map<K,V> getFullMap(){
        return fullMap.get();
    }
    private Map<K,V> buildFull(){
        Map<K,V> toReturn = new HashMap<>();
        toReturn.putAll(buildMap((M) this,this.getActive()));
        return toReturn;
    }
    public void addChange(Pair<K,V>... changes){
        addChange(true,changes);
    }
    public void addChange(boolean sandbox, Pair<K,V>... changes){
        List<K> toAmend = new ArrayList<>();
        List<K> rollback = new ArrayList<>();
        Map<K,V> backup = new HashMap<>();
        final int oldCumulative = this.expectedSize;
        for (Pair<K,V> p : changes) {
            K key = p.getKey();
            I id = key.getID();
            if (!this.activeChanges.containsKey(key)) {
                this.activeChanges.put(key, p.getValue());
                if (!startingChanges.contains(id) && !TimelineMultiChange.hasEntry((M) this, key)) {
                    startingChanges.add(id);
                    toAmend.add(key);
                }
                rollback.add(key);
            } else{
                backup.put(key,this.activeChanges.get(key));
                this.activeChanges.put(key,p.getValue());
            }
            if (hasEndingChanges() && endingChanges.contains(id)){
                endingChanges.remove(key.getID());
            }
        }
        fullMap.clear();
        amendCumulativeTotal((M) this,true,buildAmendCumulative(toAmend));
        this.expectedSize += toAmend.size();
        if(sandbox){
            Consumer<SandboxCode> code = new Consumer<SandboxCode>() {
                @Override
                public void accept(SandboxCode sandboxCode) {
                    if(sandboxCode == END_DISCARD || sandboxCode == SandboxCode.CRITICAL_ERROR ){
                        remove(WipeType.NO_WIPE, (K[]) rollback.toArray(new Identifiable[0]));
                        for(Map.Entry<K,V> e : backup.entrySet()){
                            activeChanges.put(e.getKey(),e.getValue());
                        }
                        expectedSize = oldCumulative;
                    }
                }
            };
            Objective<T> t = new Objective<>(getOwner(),FORWARD,(M) this,new SandboxFunctions.MultiChange<>(code));
            SandboxHandler.StartSandbox(t);
        }
    }
    public enum WipeType {
        FORWARD,
        BACKWARD,
        BOTH,
        NO_WIPE
    }
    private final void remove(WipeType wipe, K... key){
        Set<K> startMove = new HashSet<>();
        List<K> toFind;
        if(key.length == 0){
            return;
        }
        onRemoveEntry(wipe,key);
        if (wipe == WipeType.NO_WIPE) {
            List<K> toAmend = new ArrayList<>();
            toFind = new ArrayList<>();
            for (K k : key) {
                I id = k.getID();
                if (activeChanges.containsKey(k)) {
                    activeChanges.remove(k);
                    boolean startHas = startingChanges.contains(id);
                    if (hasEndingChanges() && !hasEntryFuture((M) this, k)) {
                        endingChanges.add(id);
                        toAmend.add(k);
                        //doing it manually here for the same reason as below: amendCumulative only touches future states, not the current one.
                        //except when done on removeEntry set to go backwards.
                        expectedSize--;
                    } else if (startHas) {
                        startingChanges.remove(id);
                        startMove.add(k);
                        toAmend.add(k);
                        //doing it manually here for the same reason as below: amendCumulative only touches future states, not the current one.
                        //except when done on removeEntry set to go backwards.
                        expectedSize--;
                    }
                } else {
                    toFind.add(k);
                }
            }
            if (!toFind.isEmpty()) {
                removeEntry((M) this, BACKWARD, false,true,toFind);
            }
            if (!startMove.isEmpty()){
                updateStart((M) this, startMove);
            }
            amendCumulativeTotal((M) this,false,buildAmendCumulative(toAmend));
        } else {
            toFind = new ArrayList<>(Arrays.stream(key).toList());
            for (K k : toFind){
                activeChanges.remove(k);
                endingChanges.remove(k.getID());
                startingChanges.remove(k.getID());
            }
            switch (wipe){
                case FORWARD:
                    removeEntry((M) this, FORWARD, true,true,toFind);
                    //This is a workaround for the problem described below.
                    expectedSize -= toFind.size();
                    break;
                case BACKWARD:
                    //Not changing effectivesize because the removeEntry will take care of it.
                    //Because removeEntry on backs goes back to the oldest date with the change and adjusts the count dynamically.
                    //That means that the current change would be included in that. Forward doesn't because forward starts at the
                    //Next state after the current one.
                    removeEntry((M) this, BACKWARD, true,true,toFind);
                    break;
                case BOTH:
                    //Same case as above. The remove entry going back will amend the total properly.
                    removeEntry((M) this, FORWARD, true,false,new ArrayList<>(toFind));
                    removeEntry((M) this, BACKWARD, true,false,new ArrayList<>(toFind));
                    RepairWizard((M) this, true,false);
                    break;
            }
        }
        fullMap.clear();
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
    protected final void internalSetLeapfrog(ChangeID leapFrog){
        leapfrog = leapFrog;
        isFirst.set(leapFrog == null);
    }
    public final ChangeID getLeapfrog(){
        if (leapfrog == null && !isFirst.get()){
            M ch = (M) getTimeline().findChangeByClassID(this.getStart(), Global.TimeDirection.BACKWARD,this,false);
            if (ch == null){
                isFirst.set(true);
                return null;
            }
            leapfrog = ch.getID();
            return leapfrog;
        }
        return leapfrog;
    }

    protected final int getStartingSize(){
        return startingChanges.size();
    }
    protected final int getEndingSize(){
        return endingChanges.size();
    }
    public Set<I> getEndingChanges(){
        return new HashSet<>(endingChanges);
    }
    public Set<I> getStartingChanges(){
        return new HashSet<>(startingChanges);
    }
    protected final void internalRemoveStarting(I key){
        startingChanges.remove(key);
        fullMap.clear();
    }
    protected final void internalAddStarting(I key){
        startingChanges.add(key);
        fullMap.clear();
    }
    protected final void internalAddStarting(K key){
        startingChanges.add(key.getID());
        fullMap.clear();
    }
    protected void internalRemoveChange(K k){
        activeChanges.remove(k);
        startingChanges.remove(k.getID());
        endingChanges.remove(k.getID());
        fullMap.clear();
    }
    public int getExpectedSize(){
        return expectedSize;
    }
    protected final void stepAmendExpected(int amount){
        this.expectedSize += amount;
        fullMap.clear();
    }
    protected final void internalSetExpected(int amount){
        this.expectedSize = amount;
        fullMap.clear();
    }
    private static <M extends TimelineMultiChange<M,K,V,I,T>,K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>> Optional<Pair<BiFunction<Timeline<? extends T>,M,ChangeID>,ChangeID>> makeNext(M change, Global.TimeDirection direction){
        if (direction == FORWARD) {
            final String className = change.getClass().getName();
            M nextChange = (M) change.getTimeline().findChangeByClassID(change.getStart(), FORWARD, className, false);
            if (nextChange == null) {
                return Optional.empty();
            }
            final BiFunction<Timeline<? extends T>, M, ChangeID> buildNext = (tl, ch) -> {
                M tcl = (M) tl.findChangeByClassID(ch.getStart(), FORWARD, className, false);
                if (tcl == null) {
                    return null;
                }
                return tcl.getID();
            };
            return Optional.of(Pair.of(buildNext, nextChange.getID()));
        } else {
            ChangeID next = change.getLeapfrog();
            if (next == null) {
                return Optional.empty();
            }
            final BiFunction<Timeline<? extends T>, M, ChangeID> buildNext = (t, ch) -> {
                return ch.getLeapfrog();
            };
            return Optional.of(Pair.of(buildNext, next));
        }
    }
    protected static <M extends TimelineMultiChange<M,K,V,I,T>,K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>> List<K> amendCumulativeTotal(M change, boolean isPositive, Map<K,Boolean> toAdd){

        //Timeline<T> timeline = (Timeline<T>) t;
        final Optional<Pair<BiFunction<Timeline<? extends T>,M,ChangeID>,ChangeID>> buildNext = makeNext(change,FORWARD);
        if (!buildNext.isPresent()){
            return new ArrayList<>();
        }
        final List<K> newStarting = new ArrayList<>();
        final int mult;
            if (isPositive) {
                mult = 1;
            } else {
                mult = -1;
            }
        final AtomicInteger toAmend = new AtomicInteger(toAdd.size() * mult);
        final OnMapStep<M,K,V,I,T> consumer;
        consumer = new OnMapStep<>(change) {
            @Override
            public void onMapDo(M requester, M current, Map<K, V> map, Set<I> endingChanges) {
                for (K key : new HashSet<>(toAdd.keySet())){
                    if(current.contains(key)){
                        Set<I> starting = current.getStartingChanges();
                        I id = key.getID();
                        if (starting.contains(id) && toAdd.get(key)){
                            newStarting.add(key);
                            current.internalRemoveStarting(id);
                        }
                        toAdd.remove(key);
                    }
                }
                //We are intentionally only amending the changes that are not present in this state (or any prior states)
                //Because the count responsibility for that key now falls on the most recent change that touches it.
                //To put it another way: if I add the key Olivia Rodrigo on january 1st, and Olivia Rodrigo was added/amended
                //already on January 5th. The adding +1 to the count would now be the responsibility of the Jan 5th change,
                // not the Jan 1st change that initiated it.
                toAmend.set(toAdd.size() * mult);
                if (toAmend.get() != 0){
                    current.stepAmendExpected(toAmend.get());
                    requester.onAmendSizeStep(current,toAmend.get());
                    current.onBeingSizeAmended(requester,toAmend.get());
                }
                if (current.isFirst.get()){
                    //Because it is definitely not first if there is a change before it that is amending it's first status!
                    current.isFirst.set(false);
                }
            }
        };
        final BiPredicate<LocalDate,Map<K,V>> isComplete = (ch, finalMap) -> {
            return ch == null || Math.abs(toAmend.get()) == 0;
        };
        Timeline.iterateMap(buildNext.get().getLeft(),consumer,isComplete,change.getTimeline(),change,buildNext.get().getRight(),new HashMap<>(),new HashSet<>());
        return newStarting;
    }


    protected static <M extends TimelineMultiChange<M, K,V,I,T>, K extends Identifiable<I>,V,I, T extends DateMutableEntity<T>> void removeEntry(M change, final Global.TimeDirection direction, final boolean wipe, final boolean amendTotal, List<K> toRemove){
        List<K> done = new ArrayList<>();
        final AtomicReference<M> lastChange = new AtomicReference<>(change);
        final Optional<Pair<BiFunction<Timeline<? extends T>,M,ChangeID>,ChangeID>> buildNext = makeNext(change,direction);
        if (!buildNext.isPresent()){
            return;
        }
        final OnMapStep<M, K,V,I,T> consumer = new OnMapStep<>(change) {
            @Override
            public void onMapDo(M requester, M change, Map<K, V> map, Set<I> endingChanges) {
                List<K> toRemoveCopy = new ArrayList<>(toRemove);
                for (K k : toRemoveCopy){
                    if (change.contains(k)){
                        change.internalRemoveChange(k);
                        requester.onRemoveEntryStep(change,k);
                        change.onRemovedEntry(requester,k);
                        if (!wipe){
                            toRemove.remove(k);
                        }
                        if (!done.contains(k)){
                            done.add(k);
                        }
                    }
                }
                if(direction == BACKWARD){
                    lastChange.set(change);
                }
            }
        };
        final BiPredicate<LocalDate,Map<K,V>> isComplete = (ch, finalMap) -> {
            if(ch == null || toRemove.isEmpty()){
                if (amendTotal){
                    //I know it's not ideal, but there really isn't a better way to do this without a pretty big refactor that's not really worth it.
                    //iterateMap will never call this twice after it completes once, so that's not a concern.
                    amendCumulativeTotal(lastChange.get(),false,lastChange.get().buildAmendCumulative(done));
                }
                return true;
            };
            return false;
        };
        Timeline.iterateMap(buildNext.get().getLeft(),consumer,isComplete,change.getTimeline(),change,buildNext.get().getRight(),new HashMap<>(),new HashSet<>());
    }
    protected static <M extends TimelineMultiChange<M,K,V,I,T>, K extends Identifiable<I>,V,I, T extends DateMutableEntity<T>> Set<K> updateStart(M change, Set<K> orphanKeys){
        Map<K,V> toReturn = new HashMap<>();
        final Optional<Pair<BiFunction<Timeline<? extends T>,M,ChangeID>,ChangeID>> buildNext = makeNext(change,FORWARD);
        if (!buildNext.isPresent()){
            return orphanKeys;
        }
        final OnMapStep<M, K,V,I,T> consumer;
        consumer = new OnMapStep<>(change) {
            @Override
            public void onMapDo(M requester, M current, Map<K, V> map, Set<I> endingChanges) {
                List<K> toRemove = new ArrayList<>();
                for (K k : orphanKeys){
                    if (current.getActive().containsKey(k)){
                        current.internalAddStarting(k);
                        toRemove.add(k);
                    }
                }
                toRemove.forEach(orphanKeys::remove);
            }
        };
        final BiPredicate<LocalDate,Map<K,V>> isComplete = (ch, finalMap) -> {
            return ch == null || orphanKeys.isEmpty();
        };
        Timeline.iterateMap(buildNext.get().getLeft(),consumer,isComplete,change.getTimeline(),change,buildNext.get().getRight(),toReturn,new HashSet<>());
        //I probably don't need this return anymore. It was meant for start points that couldn't be reassigned. I might need it eventually, so I'll leave it for now.
        return orphanKeys;
    }

    protected static <M extends TimelineMultiChange<M,K,V,I,T>,K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>> Map<K,V> getOtherMap(M change, Global.TimeDirection direction, @Nullable Predicate<M> additional, int steps){
        //Steps should be how far away from change 0 we are grabbing. So 1 step is one change forward that meets the predicate (if applicable).
        Map<K,V> toReturn = new HashMap<>();
        AtomicInteger total = new AtomicInteger(0);
        final Optional<Pair<BiFunction<Timeline<? extends T>,M,ChangeID>,ChangeID>> buildNext = makeNext(change,direction);
        if (!buildNext.isPresent()){
            return toReturn;
        }
        final OnMapStep<M,K,V,I,T> consumer;
        if (additional == null){
            consumer= new OnMapStep<>(change) {
                @Override
                public void onMapDo(M requester, M current, Map<K, V> map, Set<I> endingChanges) {
                    if (total.addAndGet(1) == steps) {
                        map.putAll(current.getFullMap());
                    }
                }
            };
        } else {
            consumer=new OnMapStep<>(change) {
                @Override
                public void onMapDo(M requester, M current, Map<K, V> map, Set<I> endingChanges) {
                    if (additional.test(current) && total.addAndGet(1) == steps) {
                        map.putAll(current.getFullMap());
                    }
                }
            };
        }
        final BiPredicate<LocalDate,Map<K,V>> isComplete = (ch, finalMap) -> {
            return ch == null || total.get() >= steps;
        };
        Timeline.iterateMap(buildNext.get().getLeft(),consumer,isComplete,change.getTimeline(),change,buildNext.get().getRight(),toReturn,new HashSet<>());
        return toReturn;
    }
    protected static <M extends TimelineMultiChange<M,K,V,I,T>, K extends Identifiable<I>,V,I, T extends DateMutableEntity<T>> boolean hasEntry(M change, K k){
        final Optional<Pair<BiFunction<Timeline<? extends T>,M,ChangeID>,ChangeID>> buildNext = makeNext(change,BACKWARD);
        if (!buildNext.isPresent()){
            return false;
        }
        AtomicBoolean toReturn = new AtomicBoolean(false);
        final OnMapStep<M,K,V,I,T> step = new OnMapStep<>(change) {
            @Override
            public void onMapDo(M requester, M current, Map<K, V> map, Set<I> endingChanges) {
                if (current.contains(k)){
                    toReturn.set(true);
                }
            }
        };
        final BiPredicate<LocalDate,Map<K,V>> isComplete = (ch, finalMap) -> {
            return ch == null || toReturn.get();
        };
        Timeline.iterateMap(buildNext.get().getLeft(),step,isComplete,change.getTimeline(),change,buildNext.get().getRight(),new HashMap<>(),new HashSet<>());
        return toReturn.get();
    }
    protected static <M extends TimelineMultiChange<M,K,V,I,T>, K extends Identifiable<I>,V,I, T extends DateMutableEntity<T>> boolean hasEntryFuture(M change, K k){
        final Optional<Pair<BiFunction<Timeline<? extends T>,M,ChangeID>,ChangeID>> buildNext = makeNext(change,FORWARD);
        if (!buildNext.isPresent()){
            return false;
        }
        AtomicBoolean toReturn = new AtomicBoolean(false);
        final OnMapStep<M,K,V,I,T> step = new OnMapStep<>(change) {
            @Override
            public void onMapDo(M requester, M current, Map<K, V> map, Set<I> endingChanges) {
                if (current.contains(k)){
                    toReturn.set(true);
                }
            }
        };
        final BiPredicate<LocalDate,Map<K,V>> isComplete = (ch, finalMap) -> {
            return ch == null || toReturn.get();
        };
        Timeline.iterateMap(buildNext.get().getLeft(),step,isComplete,change.getTimeline(),change,buildNext.get().getRight(),new HashMap<>(),new HashSet<>());
        return toReturn.get();
    }
    protected static <M extends TimelineMultiChange<M,K,V,I,T>,K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>> Map<K,V> buildMap(M change,Map<K,V> starting){
        ChangeID leapfrog = change.getLeapfrog();
        final List<I> blacklist = new ArrayList<>(change.getEndingChanges());
        if (leapfrog == null){
            return new HashMap<>(starting);
        }
        M leapfrogged = change.getTimeline().followBreadcrumb(change.getLeapfrog());
        Map<K,V> lf = leapfrogged.getFullMap();
        Map<K,V> toReturn = new HashMap<>();
        for (Map.Entry<K,V> entry : lf.entrySet()){
            K key = entry.getKey();
            if(!starting.containsKey(key) && !blacklist.contains(key.getID())){
                toReturn.put(key,entry.getValue());
            }
        }
        toReturn.putAll(starting);
        return toReturn;
    }
    public static <M extends TimelineMultiChange<M,K,V,I,T>,K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>> void RepairWizard(M change, boolean repairCount, boolean repairLeapfrogs){
        if(repairLeapfrogs){
            repairLeapfrogs(change);
        }
        if(repairCount){
            repairCounts(change);
        }
    }
    private static <M extends TimelineMultiChange<M,K,V,I,T>,K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>> void repairLeapfrogs(M change){
        Timeline<? extends T> timeline = change.getTimeline();
        TimelineState<? extends T> state = timeline.getLastState();
        M current = null;
        while (state != null){
            M m = state.getChange(change.getClassID(),false);
            if(m != null){
                if (current != null){
                    current.internalSetLeapfrog(m.getID());
                }
                current = m;
            }
            state = timeline.getStateBefore(state.getStart());
        }
        if (current != null){
            current.internalSetLeapfrog(null);
        }
    }
    private static <M extends TimelineMultiChange<M,K,V,I,T>,K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>> void repairCounts(M change){
        Timeline<? extends T> timeline = change.getTimeline();
        TimelineState<? extends T> state = timeline.getFirstState();
        Map<I,K> map = new HashMap<>();
        List<I> toRemove = new ArrayList<>();
        int count = 0;
        while (state != null){
            M m = state.getChange(change.getClassID(),false);
            if(m != null){
                if(!toRemove.isEmpty()){
                    for (I id : toRemove){
                        map.remove(id);
                    }
                    count -= toRemove.size();
                    toRemove.clear();
                }
                for (K k : m.getActive().keySet()){
                    if(!map.containsKey(k.getID())){
                        map.put(k.getID(),k);
                        count++;
                    }
                }
                for (I id : m.getEndingChanges()){
                    if (map.containsKey(id)){
                        toRemove.add(id);
                    }
                }
                m.internalSetExpected(count);
            }
            state = timeline.getStateAfter(state.getStart());
        }
    }



    public static abstract class OnMapStep<M extends TimelineMultiChange<M,K,V,I,T>, K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>>{
        private final M main;
        public OnMapStep(M main){
            this.main = main;
        }
        public void mapDo(M change, Map<K,V> map, Set<I> endingChanges){
            onMapDo(main,change,map,endingChanges);
        }
        protected abstract void onMapDo(M main, M current, Map<K,V> map, Set<I> endingChanges);
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
    private JsonArray serializeStartList(){
        JsonArray array = new JsonArray();
        for (I id : startingChanges){
            array.add(iSerialize(id));
        }
        return array;
    }
    @Override
    public final void mainSave(JsonObject o) {
        super.mainSave(o);
        JsonObject TMCData = new JsonObject();
        TMCData.addProperty("expectedSize",expectedSize);
        if (leapfrog != null){
            TMCData.add("leapfrog",leapfrog.toJson());
        }
        TMCData.add("active",serializeMap(getActive()));
        TMCData.add("endChange",serializeEndList());
        TMCData.add("startChange",serializeStartList());
        o.add("TMCData",TMCData);
    }
    @Override
    public final void mainLoad(JsonObject object) {
        super.mainLoad(object);
        JsonObject TMCData = object.getAsJsonObject("TMCData");
        expectedSize = TMCData.get("expectedSize").getAsInt();
        if (TMCData.has("leapfrog")){
            leapfrog = ChangeID.fromJson(TMCData.get("leapfrog").getAsJsonObject());
        } else {
            leapfrog = null;
        }
        endingChanges.clear();
        endingChanges.addAll(buildIList(TMCData.get("endChange").getAsJsonArray()));
        startingChanges.clear();
        startingChanges.addAll(buildIList(TMCData.get("startChange").getAsJsonArray()));
        activeChanges.clear();
        activeChanges.putAll(buildMap(TMCData.get("active").getAsJsonArray()));
    }

    @Override
    protected void applyConditions(List<ApplyCondition<? super T>> list) {
        list.add(new CanMerge());
    }
    public void mergeSafe(TimelineMultiChange<?,?,?,?,?> other){
        merge((M) other);
    }
    public abstract void merge(M other);


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
