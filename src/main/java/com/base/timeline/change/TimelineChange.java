package com.base.timeline.change;

import com.Global;
import com.base.DateMutableEntity;
import com.base.ObjectType;
import com.base.reference.ComplexReference;
import com.base.timeline.Timeline;
import com.base.timeline.change.condition.apply.ApplyCondition;
import com.base.timeline.change.condition.apply.ApplyConditions;
import com.base.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.timeline.change.condition.nullify.NullifyCondition;
import com.base.timeline.change.condition.nullify.NullifyConditions;
import com.base.timeline.change.condition.nullify.NullifyResult;
import com.base.timeline.error.SandboxCode;
import com.base.timeline.error.StateError;
import com.base.reference.DMEReference;
import com.base.timeline.sandbox.check.SandboxFunctions;
import com.base.timeline.sandbox.core.Sandbox;
import com.base.timeline.state.TimelineState;
import com.base.condition.*;
import com.base.timeline.sandbox.core.Objective;
import com.base.timeline.sandbox.core.SandboxHandler;
import com.google.common.base.Suppliers;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.utilities.SuperclassSerializable;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;


/**
 * Represents an abstract base class for all types of timeline changes.
 * Subclasses of {@code TimelineChange} are responsible for defining specific
 * changes that can be applied to a timeline state. Each change should handle
 * its application, serialization, conflicts, and nullification.
 *
 * TimelineChange implementations should only save or use {@code StateReferences}
 * and never use actual objects directly to ensure sandbox execution safety.
 *
 * @param <T> the type of mutable state the change is applied to
 */
public abstract class TimelineChange<T extends DateMutableEntity<?>> implements SuperclassSerializable<TimelineChange<?>> {
    // Rules: TimelineChange implementations should only save/use StateReferences! Never use actual objects. This keeps them sandbox safe. Include this fact in documentation

    //TODO Third kind of check: once per TimelineState (For things like: is still alive, or canHoldTitle, or Does this Religion exist?

    private final DMEReference<? extends T> owner;
    private final ChangeID id;
    private LocalDate start;
    private List<DMEReference<?>> sandbox_newSaves = new ArrayList<>();
    private LocalDate end;
    private boolean deactivated = false;
    private final Map<Long,String> resolutionLog = new HashMap<>();
    protected TimelineChange(DMEReference<? extends T> owner, LocalDate date) {
        this.owner = owner;
        this.start = date;
        this.id = new ChangeID(this,date);
    }

    public final long getFullID(){
        return id.getFullID();
    }
    public final long getClassID(){
        return id.getClassID();
    }
    public final ChangeID getID(){
        return id;
    }
    public final DMEReference<? extends T> getOwner(){
        return owner;
    }

    //==================================================================================================================

    public void sandboxInit(Sandbox<? extends T> sandbox){
        onSandboxInit(sandbox);
    }

    /**
     * Applies changes to the specified entity by invoking the {@code onApply} method to handle
     * timeline state transitions. Additional logic can be implemented here if necessary.
     *
     * @param entity the entity on which the apply operation is being performed. This represents
     *               the target for timeline changes, ensuring accurate and consistent state updates.
     */
    public void apply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState){
        onApply(entity,currentState);
    }
    public void advanceStage(DMEReference<? extends T> entity, TimelineState<? extends T> currentState, boolean isFirstAdvance){
        onStageAdvance(entity, currentState,isFirstAdvance);

    }
    public void continueSearch(DMEReference<? extends T> entity, TimelineState<? extends T> currentState, TimelineChange<?> oldChange){
        onContinue(entity,currentState,oldChange);
    }
    public void override(TimelineState<? extends T> currentState, TimelineChange<?>  beingOverwritten, boolean isSandbox, boolean destructive){
        onOverwrite(getOwner(),currentState,beingOverwritten,destructive);
        if (destructive){
            currentState.removeChange(beingOverwritten.getID());
        } else {
            beingOverwritten.deactivate(true);
        }
        currentState.removeBreadcrumb(beingOverwritten.getID());
        if (!isSandbox){
            currentState.insertAndPropagateBreadcrumb(this);
        }
    }
    public void nullify(DMEReference<? extends T> entity, TimelineState<? extends T> state, TimelineChange<?> changeToNullify){
        onNullify(entity,state,changeToNullify);
        changeToNullify.deactivate(true);
    }
    public void deactivate(boolean isSandbox){
        SandboxCode c = SandboxCode.END_SAVE;
        if (!isSandbox){
            DMEReference<? extends T> owner = getOwner();
            com.base.timeline.sandbox.core.SandboxHandler<?> h = SandboxHandler.StartSandbox(Objective.buildInChange(owner,
                    Global.TimeDirection.FORWARD,this,new SandboxFunctions.canDeactivate<>()),end.plusDays(2),null,null);
            c = h.getEndCode().join();
        }
        if (c == SandboxCode.END_SAVE){
            onDeactivate();
            deactivated = true;
        }
    }
    public void reactivate(boolean isSandbox){
        SandboxCode c = SandboxCode.END_SAVE;
        if (!isSandbox){
            com.base.timeline.sandbox.core.SandboxHandler<?> h = SandboxHandler.StartSandbox(Objective.buildInChange(owner,
                    Global.TimeDirection.FORWARD,this,new SandboxFunctions.canAddChange<>()),end.plusDays(2),null, null);
            c = h.getEndCode().join();
        }
        if (c == SandboxCode.END_SAVE){
            onReactivate();
            deactivated = true;
        }
    }
    public void moveChange(@Nullable LocalDate newStart, @Nullable LocalDate newEnd){

        Timeline<? extends T> timeline = (Timeline<? extends T>) owner.get().getTimeline();
        TimelineState<? extends T> oldState = timeline.getStateAt(getStart());
        TimelineState<? extends T> newState = timeline.getStateAt(start);
        if (newStart != null){
            start = newStart;
        }
        if (newEnd != null){
            end = newEnd;
        }
        //TODO figure out if this needs to move itself over to a new state.. Right now it's internal to state so no.
        onMove(newStart,newEnd,newState,oldState);
    }
    public List<DMEReference<?>> getNewSaves(){
        List<DMEReference<?>> toReturn = new ArrayList<>(sandbox_newSaves);
        sandbox_newSaves.clear();
        return toReturn;
    }
    public final void addNewSave(DMEReference<?> newSave){
        sandbox_newSaves.add(newSave);
    }


    //==================================================================================================================
    //What to do when the provided object is getting the change from this object applied to it.
    protected abstract void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState);
    protected void onContinue(DMEReference<? extends T> entity, TimelineState<? extends T> currentState, TimelineChange<?> oldChange){}
    protected void onOverwrite(DMEReference<? extends T> entity, TimelineState<? extends T> currentState, TimelineChange<?> beingOverwritten, boolean destructive){};
    protected void onNullify(DMEReference<? extends T> entity, TimelineState<? extends T> currentState, TimelineChange<?> beingNullified){}
    protected void onDeactivate(){}
    protected void onReactivate(){}
    protected void onStageAdvance(DMEReference<? extends T> entity, TimelineState<? extends T> currentState, boolean isFirstAdvance){}
    protected void onSandboxInit(Sandbox<? extends T> sandbox){}
    protected void onMove(LocalDate newStart, LocalDate newEnd,  TimelineState<? extends T> newState, TimelineState<? extends T> oldState){}
    public boolean isOpposite(TimelineChange<?>state){
        return oppositeChanges().contains(state.getClass());
    }
    public boolean isSame(TimelineChange<?> state){
        return this.getClass().equals(state.getClass());
    }
    public abstract List<Class<TimelineChange<? super T>>> oppositeChanges();
    public List<Class<TimelineChange<? super T>>> siblingChanges(){
        return new ArrayList<>();
    };
    public abstract boolean isPositive();



    public final boolean canNullify(TimelineChange<?> toNullify, boolean sameState){
        boolean hasYes = false;
        for (NullifyCondition<? super T> c : nullConditions.get()) {
            final NullifyResult result = c.check(this.getOwner(),this,toNullify,sameState).orElse(NullifyResult.NOT_NULLIFY_EXCLUSIVE);
            if (result.canNullify()){
                hasYes = true;
                if (!result.isOr()){
                    return true;
                }
                continue;
            } else if(!result.isOr()){
                return false;
            }
        }
        return hasYes;
    };
    public final List<StateError> doesConflict(TimelineChange<?> checkAgainst, boolean sameState){
        List<StateError> results = new ArrayList<>();
        for (ApplyCondition<? super T> c : applyConditions.get()) {
            Optional<StateError> result = c.check(this.owner,this,checkAgainst,sameState);
            result.ifPresent(results::add);
        }
        return results;
    };
    public final <C extends Condition<StateError,T,TimelineChange<? super T>, TimelineChange<?>>> List<StateError> canBeDeactivated(TimelineChange<?> checkAgainst, boolean sameState){
        List<StateError> results = new ArrayList<>();
        for (DeactivateCondition<? super T> c : deactivateConditions.get()) {
            Optional<StateError> result = c.check(this.owner,this,checkAgainst,sameState);
            result.ifPresent(results::add);
        }
        return results;
    }


    public HashSet<DMEReference<?>> getScope(){
        HashSet<DMEReference<?>> toReturn = new HashSet<>();
        toReturn.add(this.getOwner());
        return toReturn;
    };
    protected abstract String getText();



    public final LocalDate getStart() {
        return start;
    }

    public final LocalDate getEnd(){
        return end;
    }
    /**
     * Safely adds a {@link DMEReference} of the given {@link ObjectType} and {@link UUID} to the provided
     * {@code HashSet} if it is not already present. This method ensures duplicates are avoided in the set.
     *
     * @param map the {@code HashSet} to which the {@code DMEReference} object is to be added
     * @param type the {@code ObjectType} of the entity associated with the {@code DMEReference}
     * @param uuid the {@code UUID} of the entity associated with the {@code DMEReference}
     */
    protected static <Tt extends DateMutableEntity<Tt>> void safeAddToSet(HashSet<DMEReference<?>> map, Class<Tt> type, UUID uuid){
        //Internal method exclusively used by getScope to safely build DMES and add to the scope set
        DMEReference<Tt> reference = DMEReference.of(type,uuid);
        if (map.contains(reference)) return;
        map.add(reference);
    }

    public final boolean isDeactivated(){
        return deactivated;
    }


    public final void setEnd(LocalDate date){
        end = date;
    }

    public String getResolutionCode(StateError error){
        return resolutionLog.get(error.getLongID());
    }
    public void addResolution(StateError error, String resolutionCode){
        resolutionLog.put(error.getLongID(),resolutionCode);
    }

    private final Supplier<List<NullifyCondition<? super T>>> nullConditions =
            Suppliers.memoize(() -> {
                List<NullifyCondition<? super T>> conditions = new ArrayList<>(NullifyConditions.BaseConditions());
                this.nullifyConditions(conditions);
                return conditions;
            });
    private final Supplier<List<ApplyCondition<? super T>>> applyConditions =
            Suppliers.memoize(() -> {
                List<ApplyCondition<? super T>> conditions = new ArrayList<>(ApplyConditions.BaseConditions());
                this.applyConditions(conditions);
                return conditions;
            });
    private final Supplier<List<DeactivateCondition<? super T>>> deactivateConditions =
            Suppliers.memoize(() -> {
                List<DeactivateCondition<? super T>> conditions = new ArrayList<>();
                //conditions.addAll(DeactivateConditions.BaseConditions());
                this.deactivateConditions(conditions);
                return conditions;
            });
    protected abstract void applyConditions(List<ApplyCondition<? super T>> list);
    protected abstract void nullifyConditions(List<NullifyCondition<? super T>> list);
    protected abstract void deactivateConditions(List<DeactivateCondition<? super T>> list);
    protected final List<ApplyCondition<? super T>> getApplyConditions(){
        return applyConditions.get();
    }
    protected final List<NullifyCondition<? super T>> getNullifyConditions(){
        return nullConditions.get();
    }
    protected final List<DeactivateCondition<? super T>> getDeactivateConditions(){
        return deactivateConditions.get();
    }



    public boolean hasMultipleApplyChecks(){
        return this.getApplyConditions().size() > 1;
    }

    public boolean hasMultipleNullifyChecks(){
        return this.getNullifyConditions().size() > 1;
    }

    public boolean hasMultipleDeactivateChecks(){
        return this.getDeactivateConditions().size() > 1;
    }


    @Override
    public void mainSave(JsonObject o) {
        o.addProperty("end",end.toEpochDay());
        o.addProperty("deactivated",deactivated);
        o.add("resLog", serializeResolutionLog());
    }
    @Override
    public void mainLoad(JsonObject object) {
        end = LocalDate.ofEpochDay(object.get("end").getAsLong());
        deactivated = object.get("deactivated").getAsBoolean();
        deserializeResolutionLog(object.getAsJsonArray("resLog"));
    }
    private JsonArray serializeResolutionLog(){
        JsonArray userChoices = new JsonArray();
        for(Map.Entry<Long, String> change : resolutionLog.entrySet()){
            JsonObject choice = new JsonObject();
            choice.addProperty("id",change.getKey());
            choice.addProperty("resolution",change.getValue());
            userChoices.add(choice);
        }
        return userChoices;
    }
    private void deserializeResolutionLog(JsonArray array){
        for(JsonElement choice : array){
            JsonObject choiceObject = choice.getAsJsonObject();
            resolutionLog.put(choiceObject.get("id").getAsLong(), choiceObject.get("resolution").getAsString());
        }
    }

    @Override
    public final void metadataSave(JsonObject o) {
        o.add("subject", owner.serialize());
        o.addProperty("date", start.toEpochDay());
    }

    //==== Generic Class ====
    public class HasVariable extends ApplyCondition<T> {
        private final Function<TimelineChange<?>,String> variableGetter;
        public HasVariable(String variableName, Function<TimelineChange<?>,String> variableGetter) {
            super(variableName + "_has_variable");
            this.variableGetter = variableGetter;
        }
        @Override
        protected Optional<StateError> doCheck(DMEReference<? extends T> entity, TimelineChange<? extends T> thisChange, TimelineChange<?> checkAgainst) {
            if (checkAgainst.getClass().equals(thisChange.getClass())){
                return Optional.of(new StateError("title_has_parent", ComplexReference.of("{} already has a value of {}", entity,variableGetter.apply(checkAgainst)),checkAgainst)
                        .addEndSave().addEndCancel().addOverride());
            }
            return Optional.empty();
        }
    }
}
