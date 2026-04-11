package com.base.timeline.change.multi;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.condition.apply.ApplyCondition;
import com.base.timeline.sandbox.core.Sandbox;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.utilities.id.Identifiable;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;

public abstract class TimelineCollectionChange<M extends TimelineCollectionChange<M,T,C, V,I>,T extends DateMutableEntity<T>,C extends Collection<V>, V extends Identifiable<I>,I> extends TimelineMultiChange<M,I, V,T> {
    protected final C activeChanges;
    private boolean isFirst = true;
    protected TimelineCollectionChange(DMEReference<? extends T> owner, LocalDate date, C container) {
        super(owner, date);
        this.activeChanges = container;
    }


    //==================================================================================================================


    @Override
    protected void applyConditions(List<ApplyCondition<? super T>> list) {
        super.applyConditions(list);
    }


    protected Comparator<? super V> getComparator(){
        return null;
    }
    //==================================================================================================================
    //==== Overrides ====
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
    //==================================================================================================================
    //==== Abstract Methods ====
    protected abstract Supplier<C> getNewList();
    protected abstract C getListFromObject();

    //==================================================================================================================
    //==== Getters and Setters ====
    @Override
    public final int getFullSize(){
        return getExpectedSize() + activeChanges.size();
    }

    public final C getActiveChanges(){
        return activeChanges;
    }
    //==================================================================================================================
    @Override
    public final void apply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
        C list = getListFromObject();
        list.clear();
        list.addAll(buildMap(getOwner().get().getTimeline(),(M) this).values());
        super.apply(entity, currentState);
    }


    //==================================================================================================================
    //==== Serializers ====
    protected abstract JsonObject vSerialize(V v);
    protected abstract V deserializeV(JsonObject m);
    private JsonArray serializeC(C c){
        JsonArray array = new JsonArray();
        for (V v : c){
            array.add(vSerialize(v));
        }
        return array;
    }
    private C buildC(JsonArray a){
        C c = getNewList().get();
        for (JsonElement e : a){
            c.add(deserializeV(e.getAsJsonObject()));
        }
        return c;
    }
    @Override
    public final void mainSave(JsonObject o) {
        super.mainSave(o);
        o.add("active_changes",serializeC(activeChanges));
    }
    @Override
    public final void mainLoad(JsonObject object) {
        super.mainLoad(object);
        activeChanges.clear();
        activeChanges.addAll(buildC(object.getAsJsonArray("active_changes")));
    }


    //==================================================================================================================

}
