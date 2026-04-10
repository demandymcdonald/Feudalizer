package com.base.timeline.change;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.Timeline;
import com.base.timeline.change.condition.apply.ApplyCondition;
import com.base.timeline.sandbox.core.Sandbox;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

public abstract class TimelineCollectionChange<M extends TimelineCollectionChange<M,T,C,K>,T extends DateMutableEntity<T>,C extends Collection<K>,K> extends TimelineChange<T> {
    protected final C activeChanges;
    protected List<K> endingChanges = new ArrayList<>();
    protected int expectedSize = 0;
    protected ChangeID leapfrog = null;
    private boolean isFirst = true;
    protected TimelineCollectionChange(DMEReference<? extends T> owner, LocalDate date, C container) {
        super(owner, date);
        this.activeChanges = container;
    }


    protected static <M extends TimelineCollectionChange<M,T,C,K>,T extends DateMutableEntity<T>,C extends Collection<K>,K>
    C buildFullList(Timeline<? extends T> t, M tlChange){
        final List<K> ec = tlChange.getEndingChanges();
        tlChange.onBuildMap();
        Timeline<T> timeline = (Timeline<T>) t;
        C toReturn = tlChange.getNewList().get();
        final BiFunction<Timeline<T>,M,ChangeID> buildNext = (tl, ch) -> {
            return ch.getLeapfrog();
        };
        final BiConsumer<M,C> consumer = (ch, finalMap) -> {
            C localMap = ch.getActiveChanges();
            for (K e : localMap){
                if (!finalMap.contains(e) && !ec.contains(e)){
                    finalMap.add(e);
                }
            }
            tlChange.onBuildListStep((M) ch);
        };
        final BiPredicate<LocalDate,C> predicate = (ch, finalMap) -> {
            return finalMap.size() > tlChange.getExpectedSize();
        };
        Timeline.iterateMap(buildNext,consumer,predicate,timeline,tlChange.getLeapfrog(),toReturn,true);
        toReturn.addAll(tlChange.getActiveChanges());
        return toReturn;
    }
    //==================================================================================================================


    @Override
    protected void applyConditions(List<ApplyCondition<? super T>> list) {

    }

    protected abstract boolean hasEndingChanges();
    protected void onBuildMap(){};
    protected void onBuildListStep(M stepChange){};
    protected void onAddChangeStep(M stepChange){};
    protected void onDeactivateStep(M stepChange){};
    protected void onRemoveChangeStep(M stepChange){};
    protected void onEntryRemove(K key){};
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
    protected final ChangeID getLeapfrog(){
        return leapfrog;
    }
    protected final List<K> getEndingChanges(){
        return endingChanges;
    }
    protected final int getExpectedSize(){
        return expectedSize;
    }
    public final int getFullSize(){
        return expectedSize + activeChanges.size();
    }
    protected final void setExpectedSize(int expectedSize){
        this.expectedSize = expectedSize;
    }
    protected final void amendExpectedSize(int amount){
        this.expectedSize += amount;
    }
    protected final void resetExpectedSize(){
        this.expectedSize = 0;
    }
    public final C getActiveChanges(){
        return activeChanges;
    }
    //==================================================================================================================
    @Override
    public final void apply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
        C list = getListFromObject();
        list.clear();
        list.addAll(buildFullList(getOwner().get().getTimeline(),(M) this));
        super.apply(entity, currentState);
    }


    //==================================================================================================================
    //==== Serializers ====
    protected abstract void serializeC(C container, JsonObject containerObject);
    protected abstract void deserializeC(C container, JsonObject containerObject);
    protected abstract void serializeK(K k, JsonObject keyObject);
    protected abstract void deserializeK(K k, JsonObject keyObject);
    @Override
    public final void mainLoad(JsonObject object) {
        super.mainLoad(object);

    }
    private JsonArray serializeEndList(){
        JsonArray array = new JsonArray();
        for (K k : endingChanges){
            JsonObject obj = new JsonObject();
            serializeK(k,obj);
            array.add(obj);
        }
        return array;
    }
    @Override
    public void mainSave(JsonObject o) {
        super.mainSave(o);
        JsonObject container = new JsonObject();
        serializeC(activeChanges,container);
        o.addProperty("expectedSize",expectedSize);
        o.add("leapfrog",leapfrog.toJson());
        o.add("active_changes",container);
        o.add("ending_list",serializeEndList());
    }



    //==================================================================================================================

}
