package com.base.timeline.change;

import com.Global;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.reference.SimpleReference;
import com.base.timeline.Timeline;
import com.base.timeline.change.changes.TimelineChange;
import com.base.timeline.change.conditions.Condition;
import com.base.timeline.error.ErrorResolution;
import com.base.timeline.error.StateError;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.tuple.Pair;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import static com.Global.TimeDirection.BACKWARD;
import static com.Global.TimeDirection.FORWARD;

public abstract class TimelineMapChange<M extends TimelineMapChange<M,K,V,T>,K,V,T extends DateMutableEntity<T>> extends TimelineChange<T> {
    //Plan:
    //Each relationship change is treated as a single variable from the timeline's perspective, keeping breadcrumb count sane
    //Internally, the keyframe stores: the diff (modifiers added/removed), the calculated total opinion value, the expected diff size, and a pointer to the previous relationship change
    //To reconstruct the full relationship map at any point: jump to the pointer, walk back collecting one entry per UUID, stop when seen-count hits expected-size
    //Full modifier history is reconstructable via scrub-back if needed, but not stored redundantly in every keyframe
    //Forward propagation handles expected-size consistency automatically — adding/removing a relationship mid-timeline just increments/decrements an int during the existing propagation pass
    //The same pattern applies recursively to the diff history within each relationship entry
    private ChangeID leapfrog;
    private int expectedSize;
    private boolean isFirst = true;
    //Changes in this active object
    protected Map<K,V> activeChanges = new HashMap<>();
    protected List<K> endingChanges = new ArrayList<>();
    protected TimelineMapChange(DMEReference<T> owner,  LocalDate date) {
        super(owner, date);
    }



    public Map<K,V> getFullMap(){
        return buildMap(getOwner().get().getTimeline(),this);
    }



    @Override
    public final void advanceStage(DMEReference<? extends T> entity, TimelineState<? extends T> currentState, boolean isFirstAdvance) {
        final Timeline<? extends T> timeline = entity.get().getTimeline();
        if (isFirstAdvance) {
            M nextChange = (M) timeline.findChangeByClassID(this.getStart(),FORWARD,this,false).getFirst();
            if (nextChange != null){
                nextChange.setLeapFrog(this.getID());
            }
        } else {
            M change = (M) currentState.getChange(this.getClass());
            if (change != null){
                change.internalAmendExpected(getActiveSize());
            }
        }
        super.advanceStage(entity,currentState, isFirstAdvance);
    }

    @Override
    public final void apply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
        super.apply(entity, currentState);
    }

    @Override
    public final void deactivate(boolean isSandbox) {
        super.deactivate(isSandbox);
        final Timeline<? extends T> timeline = getOwner().get().getTimeline();
        M nextChange = (M) timeline.findChangeByClassID(this.getStart(),FORWARD,this,false).getFirst();
        M lastChange = (M) timeline.findChangeByClassID(this.getStart(),BACKWARD,this,false).getFirst();
        if (nextChange != null){
            if (lastChange != null) {
                nextChange.setLeapFrog(lastChange.getID());
            }
            amendCumulativeTotal(getOwner().get().getTimeline(),this,FORWARD,-1 * getActiveSize(), this::onDeactivateStep);
        }

    }
    public void addChange(Pair<K,V>... changes){
        int totalNew = 0;
        for (Pair<K,V> p : changes){
            K key = p.getKey();
            if (!this.activeChanges.containsKey(key)){
                this.activeChanges.put(key,p.getValue());
                totalNew++;
            }
            if (hasEndingChanges() && endingChanges.contains(key)){
                endingChanges.remove(key);
            }
        }
        amendCumulativeTotal(getOwner().get().getTimeline(),this,FORWARD,totalNew, this::onAddChangeStep);
        //TODO finish once I figure out how commits will work XD I think I need a commit method in TLChange.
    }
    public final void remove(K... key){
        int totalRemoved = 0;
        for (K k : key){
            if (activeChanges.containsKey(k)){
                onEntryRemove(k, activeChanges.get(k));
                totalRemoved--;
                activeChanges.remove(k);
                if (hasEndingChanges() && !endingChanges.contains(k)){
                    endingChanges.add(k);
                }
            }
        }
        amendCumulativeTotal(getOwner().get().getTimeline(),this,FORWARD,totalRemoved, this::onRemoveChangeStep);
    }
    public final void removeAll(){
        final int totalRemoved = getActiveSize() * -1;
        for (Map.Entry<K,V> e : activeChanges.entrySet()){
            onEntryRemove(e.getKey(), e.getValue());
        }
        if (hasEndingChanges()){
            endingChanges.addAll(activeChanges.keySet());
        }
        activeChanges.clear();
        amendCumulativeTotal(getOwner().get().getTimeline(),this,FORWARD,totalRemoved, this::onRemoveChangeStep);
    }


    @Override
    public final void reactivate(boolean isSandbox) {

    }

    @Override
    public final void nullify(DMEReference<? extends T> entity, TimelineState<? extends T> state, TimelineChange<?> changeToNullify) {
        super.nullify(entity, state, changeToNullify);
    }

    @Override
    public final void moveChange(@Nullable LocalDate newStart, @Nullable LocalDate newEnd) {
        super.moveChange(newStart, newEnd);
    }

    @Override
    public final void override(TimelineState<? extends T> currentState, TimelineChange<?> beingOverwritten, boolean isSandbox, boolean destructive) {
        super.override(currentState, beingOverwritten, isSandbox, destructive);
    }
    public final void merge(TimelineMapChange<?,?,?,?> newChange){
        M merger = (M) newChange;
        int total = 0;
        for (Map.Entry<K,V> e : merger.activeChanges.entrySet()){
            if (this.onMerge(merger, e.getKey(), e.getValue())){
                total++;
                this.activeChanges.put(e.getKey(), e.getValue());
            }
        }
        amendCumulativeTotal(getOwner().get().getTimeline(),this,FORWARD,total, this::onAddChangeStep);
    }

    @Override
    protected List<Condition<StateError, ? super T>> buildApplyConditions() {
        return List.of(
                new ShouldMerge()
        );
    }

    //==== Event Methods ====
    protected void onAddChangeStep(M stepChange){};
    protected void onDeactivateStep(M stepChange){};
    protected void onRemoveChangeStep(M stepChange){};
    protected void onEntryRemove(K key, V value){};
    protected void onBuildMap(){};
    protected void onBuildMapStep(M stepChange){};
    protected boolean onMerge(M newChange, K key, V value){
        return true;
    };
    public boolean containsKey(K key){
        return activeChanges.containsKey(key);
    }
    public boolean containsValue(V value){
        return activeChanges.containsValue(value);
    }
    public V get(K key){
        return activeChanges.get(key);
    }


    //==== Getters and Setters ====
    public ChangeID getLeapfrog(){
        return leapfrog;
    }
    public Pair<Long, LocalDate> makeLeapFrog(){
        return Pair.of(this.getFullID(),this.getStart());
    }
    public void internalAmendExpected(int amount){
        this.expectedSize += amount;
    }
    public void setLeapFrog(ChangeID leapFrog){
        this.leapfrog = leapFrog;
    }
    public Map<K,V> getChangeFragment(){
        return activeChanges;
    }
    public int getTotalSize(){
        return expectedSize + activeChanges.size();
    }
    public int getExpectedSize(){
        return expectedSize;
    }
    public int getActiveSize(){
        return activeChanges.size();
    }
    public boolean hasOtherApplyChecks(){
        return this.applyConditions.get().size() > 1;
    }
    public List<K> getEndingChanges(){
        return endingChanges;
    }
    protected abstract boolean hasEndingChanges();
    // ==== Function Builder Methods ====
    @SuppressWarnings("unchecked")
    protected static <T extends DateMutableEntity<T>,K,V,TC extends TimelineMapChange<TC,K,V,? super T>> Map<K,V> buildMap(Timeline<? extends T> t, TimelineMapChange<?,K,V,?> change){
        final TC tc = (TC) change;
        final List<K> ec = tc.getEndingChanges();
        tc.onBuildMap();
        Timeline<T> timeline = (Timeline<T>) t;
        Map<K,V> toReturn = new HashMap<>();

        final BiFunction<Timeline<T>,TC,ChangeID> buildNext = (tl, ch) -> {
            return ch.getLeapfrog();
        };
        final BiConsumer<TC,Map<K,V>> consumer = (ch, finalMap) -> {
            Map<K,V> localMap = ch.getChangeFragment();
            for (Map.Entry<K,V> e : localMap.entrySet()){
                if (!finalMap.containsKey(e.getKey()) && !ec.contains(e.getKey())){
                    finalMap.put(e.getKey(),e.getValue());
                }
            }
            tc.onBuildMapStep(ch);
        };
        final BiPredicate<LocalDate,Map<K,V>> predicate = (ch, finalMap) -> {
            return finalMap.size() > tc.getExpectedSize();
        };
        Timeline.iterateMap(buildNext,consumer,predicate,timeline,tc.getLeapfrog(),toReturn,true);
        toReturn.putAll(tc.getChangeFragment());
        return toReturn;
    }
    @SuppressWarnings("unchecked")
    protected static <T extends DateMutableEntity<T>,K,V,TC extends TimelineMapChange<TC,K,V,? super T>> void amendCumulativeTotal(Timeline<? extends T> t, TimelineMapChange<?,K,V,?> change, final Global.TimeDirection direction, int toAdd, @Nullable Consumer<TC> onStep){
        final TC tc = (TC) change;
        Timeline<T> timeline = (Timeline<T>) t;
        final String className = tc.getClass().getName();
        TC nextChange = (TC) timeline.findChangeByClassID(tc.getStart(),direction,className,false).getFirst();
        final BiFunction<Timeline<T>,TC,ChangeID> buildNext = (tl, ch) -> {
            TC tcl = (TC) timeline.findChangeByClassID(tc.getStart(),direction,className,false).getFirst();
            if (tcl == null){
                return null;
            }
            return tcl.getLeapfrog();
        };
        final BiConsumer<TC,Map<K,V>> consumer;
        if (onStep != null){
            consumer = (ch, finalMap) -> {
                tc.internalAmendExpected(toAdd);
                onStep.accept(tc);
            };
        } else {
            consumer = (ch, finalMap) -> {
                tc.internalAmendExpected(toAdd);
            };
        }
        final BiPredicate<LocalDate,Map<K,V>> predicate = (ch, finalMap) -> {
            return ch != null;
        };
        Timeline.iterateMap(buildNext,consumer,predicate,timeline,nextChange.getID(),new HashMap<>(),false);
    }

    //==== Serializers and Deserializers ====
    @Override
    public final void mainSave(JsonObject o) {
        super.mainSave(o);
        o.addProperty("expectedSize",expectedSize);
        o.add("leapfrog",leapfrog.toJson());
        JsonArray a = new JsonArray();
        for (Map.Entry<K,V> e : activeChanges.entrySet()){
            JsonObject me = new JsonObject();
            me.add("k",serializeK(e.getKey()));
            me.add("v",serializeV(e.getValue()));
            a.add(me);
        }
        o.add("map",a);
    }

    @Override
    public final void mainLoad(JsonObject object) {
        super.mainLoad(object);
        expectedSize = object.get("expectedSize").getAsInt();
        leapfrog = ChangeID.fromJson(object.get("leapfrog").getAsJsonObject());
        JsonArray a = object.get("map").getAsJsonArray();
        for (JsonElement e : a){
            JsonObject me = e.getAsJsonObject();
            K k = deserializeK(me.get("k"));
            V v = deserializeV(me.get("v"));
            activeChanges.put(k,v);
        }
    }
    protected abstract JsonElement serializeK(K k);
    protected abstract K deserializeK(JsonElement m);
    protected abstract V deserializeV(JsonElement m);
    protected abstract JsonElement serializeV(V v);


    //####Useful Map Conditions####
    public class ShouldMerge extends Condition<StateError, T>{
        public ShouldMerge() {
            super("map_should_merge");
        }
        @Override
        protected Optional<StateError> doCheck(DMEReference<? extends T> entity, TimelineChange<? extends T> thisChange, TimelineChange<?> checkAgainst) {
            if (thisChange.getClass().equals(checkAgainst.getClass()) && thisChange instanceof TimelineMapChange<?,?,?,?> currentTMC && checkAgainst instanceof TimelineMapChange<?,?,?,?> oldTMC){
                if(isFirst){
                    isFirst = false;
                    if (!hasOtherApplyChecks()) {
                        return Optional.of(new StateError("map_merge_compatible_end", new SimpleReference("these are the same. merge them"), checkAgainst).addOption(new ErrorResolution.MapMergeEnd()));
                    } else {
                        return Optional.of(new StateError("map_merge_compatible_continue", new SimpleReference("these are the same. merge them"), checkAgainst).addOption(new ErrorResolution.MapMergeContinue()));
                    }
                }
            }
            return Optional.empty();
        }
    }
}
