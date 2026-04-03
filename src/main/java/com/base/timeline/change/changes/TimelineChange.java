package com.base.timeline.change.changes;

import com.Global;
import com.base.DateMutableEntity;
import com.base.ObjectType;
import com.base.timeline.Timeline;
import com.base.timeline.change.ChangeID;
import com.base.timeline.error.SandboxCode;
import com.base.timeline.error.StateError;
import com.base.reference.DMEReference;
import com.base.timeline.sandbox.check.SandboxChecks;
import com.base.timeline.state.TimelineState;
import com.base.timeline.change.conditions.*;
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
public abstract class TimelineChange<T extends DateMutableEntity<T>> implements SuperclassSerializable {
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
            com.base.timeline.sandbox.core.SandboxHandler<?> h = SandboxHandler.StartSandbox(Objective.build(owner,
                    Global.TimeDirection.FORWARD,this,new SandboxChecks.canDeactivate<>()),end.plusDays(2),null,null);
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
            com.base.timeline.sandbox.core.SandboxHandler<?> h = SandboxHandler.StartSandbox(Objective.build(owner,
                    Global.TimeDirection.FORWARD,this,new SandboxChecks.canAddChange<>()),end.plusDays(2),null, null);
            c = h.getEndCode().join();
        }
        if (c == SandboxCode.END_SAVE){
            onReactivate();
            deactivated = true;
        }
    }
    public void moveChange(@Nullable LocalDate newStart, @Nullable LocalDate newEnd){

        Timeline<? extends T> timeline = owner.get().getTimeline();
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
    public void addNewSave(DMEReference<?> newSave){
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



    public final boolean canNullify(TimelineChange<?> toNullify){
        boolean hasYes = false;
        for (Condition<ConditionResult.Nullify,? super T> c : nullConditions.get()) {
            final ConditionResult.Nullify result = c.check(this.getOwner(),this,toNullify).orElse(NullifyConditions.NOT_NULLIFY_NON_EXCLUSIVE);
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
    public final List<StateError> doesConflict(TimelineChange<?> checkAgainst){
        return doCheck(applyConditions.get(),this.getOwner(),this,checkAgainst);
    };
    public final List<StateError> canBeDeactivated(TimelineChange<?> checkAgainst){
        return doCheck(deactivateConditions.get(),this.getOwner(),this,checkAgainst);
    }
    private static <T extends DateMutableEntity<T>,R extends ConditionResult> List<R> doCheck(
            final List<Condition<R,? super T>> conditions, DMEReference<? extends T> entity, TimelineChange<T> change, TimelineChange<?> checkAgainst){
        List<R> results = new ArrayList<>();
        for (Condition<R,? super T> c : conditions) {
            Optional<R> result = c.check(entity,change,checkAgainst);
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



    public LocalDate getStart() {
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

    public boolean isDeactivated(){
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

    protected final Supplier<List<Condition<ConditionResult.Nullify,? super T>>> nullConditions =
            Suppliers.memoize(() -> {
                List<Condition<ConditionResult.Nullify,? super T>> conditions = new ArrayList<>();
                conditions.addAll(NullifyConditions.BaseConditions());
                conditions.addAll(this.buildNullifyConditions());
                return conditions;
            });
    protected final Supplier<List<Condition<StateError,? super T>>> applyConditions =
            Suppliers.memoize(() -> {
                List<Condition<StateError,? super T>> conditions = new ArrayList<>();
                conditions.addAll(ApplyConditions.BaseConditions());
                conditions.addAll(this.buildApplyConditions());
                return conditions;
            });
    protected final Supplier<List<Condition<StateError,? super T>>> deactivateConditions =
            Suppliers.memoize(() -> {
                List<Condition<StateError,? super T>> conditions = new ArrayList<>();
                //conditions.addAll(DeactivateConditions.BaseConditions());
                conditions.addAll(this.buildCanDeactivateConditions());
                return conditions;
            });
    protected abstract List<Condition<StateError,? super T>> buildApplyConditions();
    protected abstract List<Condition<ConditionResult.Nullify,? super T>> buildNullifyConditions();
    protected abstract List<Condition<StateError,? super T>> buildCanDeactivateConditions();











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
//    public static class SandboxBreadcrumb implements JsonSerializable<SandboxBreadcrumb>{
//
//        public SandboxBreadcrumb() {}
//
//        public void addEndPoint(LocalDate date){
//            endOfPropagation = date;
//        }
//        public void insertError(StateError error, TimelineChange<?> newChange, TimelineChange<?> existingChange, @Nullable Integer proceduralInteger, String resolutionCode) {
//            errorResolutionLog.put(error.generateID(newChange, existingChange, proceduralInteger), resolutionCode);
//        }
//        public void insertError(long fullId, String resolutionCode) {
//            errorResolutionLog.put(fullId, resolutionCode);
//        }
//        public Optional<String> getResolutionCode(long id){
//            return Optional.ofNullable(errorResolutionLog.get(id));
//        }
//        public Optional<String> getResolutionCode(StateError error, TimelineChange<?> newChange, TimelineChange<?> existingChange, @Nullable Integer proceduralInteger){
//            return getResolutionCode(error.generateID(newChange, existingChange, proceduralInteger));
//        }
//
//        public final boolean isComplete(){
//            return endOfPropagation != null;
//        }
//        public LocalDate getEndOfPropagation(){
//            return endOfPropagation;
//        }
//
//        @Override
//        public final JsonObject toJson() {
//            JsonObject object = new JsonObject();
//            if (endOfPropagation != null){
//                object.addProperty("eop",endOfPropagation.toEpochDay());
//            }
//            JsonArray errors = new JsonArray();
//            for (Map.Entry<Long, String> entry : errorResolutionLog.entrySet()) {
//                JsonObject error = new JsonObject();
//                error.addProperty("i",entry.getKey());
//                error.addProperty("r",entry.getValue());
//                errors.add(error);
//            }
//            object.add("errors",errors);
//            return object;
//        }
//
//        @Override
//        public final void fromJson(JsonObject json) {
//            if (json.has("eop")){
//                this.endOfPropagation = LocalDate.ofEpochDay(json.get("eop").getAsLong());
//            };
//            JsonArray errors = json.getAsJsonArray("errors");
//            for (int i = 0; i < errors.size(); i++) {
//                JsonObject error = errors.get(i).getAsJsonObject();
//                long id = error.get("i").getAsLong();
//                String resolution = error.get("r").getAsString();
//                errorResolutionLog.put(id,resolution);
//            }
//        }
//
//        @Override
//        public SandboxBreadcrumb empty() {
//            return new SandboxBreadcrumb();
//        }
//    }
}
