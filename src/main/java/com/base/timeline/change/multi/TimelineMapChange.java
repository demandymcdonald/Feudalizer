package com.base.timeline.change.multi;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeID;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.sandbox.core.Sandbox;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.tuple.Pair;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.*;

import static com.Global.TimeDirection.FORWARD;

public abstract class TimelineMapChange<M extends TimelineMapChange<M,K,V,T>,K,V,T extends DateMutableEntity<T>> extends TimelineMultiChange<M,K,V,T> {
    //Plan:
    //Each relationship change is treated as a single variable from the timeline's perspective, keeping breadcrumb count sane
    //Internally, the keyframe stores: the diff (modifiers added/removed), the calculated total opinion value, the expected diff size, and a pointer to the previous relationship change
    //To reconstruct the full relationship map at any point: jump to the pointer, walk back collecting one entry per UUID, stop when seen-count hits expected-size
    //Full modifier history is reconstructable via scrub-back if needed, but not stored redundantly in every keyframe
    //Forward propagation handles expected-size consistency automatically — adding/removing a relationship mid-timeline just increments/decrements an int during the existing propagation pass
    //The same pattern applies recursively to the diff history within each relationship entry

    //Changes in this active object
    protected Map<K,V> activeChanges = new HashMap<>();
    protected TimelineMapChange(DMEReference<T> owner,  LocalDate date) {
        super(owner, date);
    }
    protected TimelineMapChange(DMEReference<T> owner,  LocalDate date, Pair<K,V>... changes) {
        super(owner, date);
        addChange(changes);
    }


    public Map<K,V> getFullMap(){
        return buildMap(getOwner().get().getTimeline(),this);
    }



    @Override
    public final void apply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
        Map<K,V> map = getMapFromObject(entity);
        map.clear();
        map.putAll(buildMap(getOwner().get().getTimeline(),this));
        super.apply(entity, currentState);
    }








    protected abstract Map<K,V> getMapFromObject(DMEReference<? extends T> object);
    @Override
    public void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {

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



    //==== Event Methods ====

    @Override
    protected boolean containsK(K k) {
        return containsKey(k);
    }
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

    public Map<K,V> getActiveChanges(){
        return activeChanges;
    }
    @Override
    public int getActiveSize(){
        return activeChanges.size();
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
            me.add("k", kSerialize(e.getKey()));
            me.add("v", vSerialize(e.getValue()));
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
            K k = kDeserialize(me.get("k"));
            V v = vDeserialize(me.get("v"));
            activeChanges.put(k,v);
        }
    }
    protected abstract JsonElement kSerialize(K k);
    protected abstract K kDeserialize(JsonElement m);
    protected abstract V vDeserialize(JsonElement m);
    protected abstract JsonElement vSerialize(V v);
    @Override
    public void sandboxInit(Sandbox<? extends T> sandbox) {
        super.sandboxInit(sandbox);
        isFirst = true;
    }

    @Override
    protected void onStageAdvance(DMEReference<? extends T> entity, TimelineState<? extends T> currentState, boolean isFirstAdvance) {
        super.onStageAdvance(entity, currentState, isFirstAdvance);
        isFirst = false;
    }

}
