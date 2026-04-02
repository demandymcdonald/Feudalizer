package com.base.timeline.change;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.Timeline;
import com.base.timeline.TimelineHelper;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.tuple.Pair;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import static com.Global.TimeDirection.BACKWARD;

public abstract class TimelineMapChange<M extends TimelineMapChange<M,K,V,T>,K,V,T extends DateMutableEntity<T>> extends TimelineChange<T> {
    //Plan:
    //Each relationship change is treated as a single variable from the timeline's perspective, keeping breadcrumb count sane
    //Internally, the keyframe stores: the diff (modifiers added/removed), the calculated total opinion value, the expected diff size, and a pointer to the previous relationship change
    //To reconstruct the full relationship map at any point: jump to the pointer, walk back collecting one entry per UUID, stop when seen-count hits expected-size
    //Full modifier history is reconstructable via scrub-back if needed, but not stored redundantly in every keyframe
    //Forward propagation handles expected-size consistency automatically — adding/removing a relationship mid-timeline just increments/decrements an int during the existing propagation pass
    //The same pattern applies recursively to the diff history within each relationship entry
    private final Class<M> base;
    private Pair<Long, LocalDate> leapfrog;
    private int cumulitiveSize;
    private Map<K,V> changes = new HashMap<>();

    protected TimelineMapChange(Class<M> base,DMEReference<T> owner,  LocalDate date) {
        super(owner, date);
        this.base = base;
    }

    public final Map<K,V> buildMap(Timeline<? extends T> t){
        Map<K,V> toReturn = new HashMap<>();
        final int finalTotal = cumulitiveSize + changes.size();
        final BiConsumer<M,Map<K,V>> consumer = new BiConsumer<>() {
            @Override
            public void accept(M m, Map<K, V> kvMap) {
                Map<K,V> newMap = m.getChangeFragment();
                for (Map.Entry<K,V> e : newMap.entrySet()){
                    if (!kvMap.containsKey(e.getKey())){
                        kvMap.put(e.getKey(),e.getValue());
                    }
                }
            }
        };
        final BiFunction<M,Map<K,V>,Integer> function = (m, r) -> {
            Map<K,V> newMap = m.getChangeFragment();
            int fresh = 0;
            for (Map.Entry<K,V> e : newMap.entrySet()) {
                if (!r.containsKey(e.getKey())){
                    fresh++;
                }
            }
            return fresh;
        };
        return TimelineHelper.doMapChangeLeapFrog(t,leapfrog,function,base,finalTotal,consumer,toReturn,true,BACKWARD,true);
    }

    private void addChange(Pair<K,V>... changes){
        int totalNew = 0;
        for (Pair<K,V> p : changes){
            if (!this.changes.containsKey(p.getKey())){
                this.changes.put(p.getKey(),p.getValue());
                totalNew++;
            }
        }
        //TODO finish once I figure out how commits will work XD I think I need a commit method in TLChange.
    }
    public Map<K,V> getFullMap(){
        return changes;
    }


    public void merge(M combine){

    }
    public Pair<Long, LocalDate> getPreviousLeapFrog(){
        return leapfrog;
    }
    public Pair<Long, LocalDate> makeLeapFrog(){
        return Pair.of(this.getFullID(),this.getStart());
    }
    public void internalAddToTotal(int amount){
        this.cumulitiveSize += amount;
    }
    public void setLeapFrog(Pair<Long, LocalDate> leapFrog){
        this.leapfrog = leapFrog;
    }
    public Map<K,V> getChangeFragment(){
        return changes;
    }
    public int getTotalSize(){
        return cumulitiveSize + changes.size();
    }

    protected abstract JsonElement serializeK(K k);
    protected abstract K deserializeK(JsonElement m);
    protected abstract V deserializeV(JsonElement m);
    protected abstract JsonElement serializeV(V v);

    @Override
    public final void advance(DMEReference<? extends T> entity, TimelineState<? extends T> currentState, TimelineChange<? super T> newState, boolean isFirstAdvance) {
        if (isFirstAdvance) {
            Pair<Long,LocalDate> last = TimelineHelper.changeFindBreadcrumb(entity.get().getTimeline(), this.getClass().getName(), this.getStart(), BACKWARD,false);
            if (last != null){
                this.leapfrog = last;
                TimelineChange<? super T> tlc = (TimelineChange<? super T>) TimelineHelper.changeFollowBreadcrumb(entity.get().getTimeline(), leapfrog.getKey(),leapfrog.getValue());
                if (tlc instanceof TimelineMapChange<?,?,?,?> tmc && tmc.getClass().equals(this.getClass())){
                    this.cumulitiveSize = tmc.getTotalSize();
                }
            }
            TimelineMapChange<M,K,V,T> tlc = (TimelineMapChange<M,K,V,T>) currentState.getChangeByID(this.getClass());
            if (tlc != null){
                tlc.internalAddToTotal(this.getChangeFragment().size());
                if (Objects.equals(tlc.getPreviousLeapFrog().getKey(), this.getPreviousLeapFrog().getKey()) && tlc.getPreviousLeapFrog().getValue().equals(this.getPreviousLeapFrog().getValue())){
                    tlc.setLeapFrog(this.makeLeapFrog());
                }
            }
        }
        super.advance(entity,currentState, newState, isFirstAdvance);
    }

    @Override
    public final void apply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
        super.apply(entity, currentState);
    }

    @Override
    public final void deactivate(boolean sandbox) {
        super.deactivate(sandbox);
        DMEReference<? extends T> entity = getOwner();
        TimelineMapChange<M,K,V,T> tlc = (TimelineMapChange<M,K,V,T>) entity.get().getTimeline().getNextState(this.getStart()).getChangeByID(this.getClass());
        if (tlc != null){
            tlc.internalAddToTotal(this.getChangeFragment().size() * -1);
            tlc.setLeapFrog(this.getPreviousLeapFrog());
        }
    }
    @Override
    public final void reactivate(boolean sandbox) {
        Do opposite of reactivate
        super.reactivate(sandbox);
    }
    @Override
    public final void nullify(DMEReference<? extends T> entity, TimelineState<? extends T> state, TimelineChange<? super T> changeToNullify) {
        super.nullify(entity, state, changeToNullify);
    }

    @Override
    public final void moveChange(@Nullable LocalDate newStart, @Nullable LocalDate newEnd) {
        super.moveChange(newStart, newEnd);
    }

    @Override
    public final void overwrite(TimelineState<? extends T> currentState, TimelineChange<? super T> beingOverwritten, boolean destructive) {
        super.overwrite(currentState, beingOverwritten, destructive);
    }



    @Override
    public final void mainSave(JsonObject o) {
        super.mainSave(o);
        add stuff here
    }

    @Override
    public final void mainLoad(JsonObject object) {
        super.mainLoad(object);
        add stuff here
    }

}
