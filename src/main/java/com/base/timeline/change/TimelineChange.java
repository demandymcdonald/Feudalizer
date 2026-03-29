package com.base.timeline.change;

import com.base.DateMutableEntity;
import com.base.ObjectType;
import com.base.timeline.Timeline;
import com.base.timeline.TimelineHelper;
import com.base.timeline.flags.StateError;
import com.base.reference.DMEReference;
import com.base.timeline.TimelineState;
import com.base.timeline.change.conditions.*;
import com.google.common.base.Suppliers;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.utilities.JsonSerializable;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.utilities.SidecarSave;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
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
public abstract class TimelineChange<T extends DateMutableEntity<T>> implements SidecarSave {
    // Rules: TimelineChange implementations should only save/use StateReferences! Never use actual objects. This keeps them sandbox safe. Include this fact in documentation
    private LocalDate start;
    private final DMEReference<T> owner;
    //I'm not fully sold on the supplier route, may be overkill. but it works?
    private final Supplier<Long> id = Suppliers.memoize(this::generateID);
    private final List<Condition<StateError,?>> applyConditions = initApplyConditions();
    private final List<Condition<ConditionResult.Nullify,?>> nullifyConditions = initNullifyConditions();
    private Breadcrumb breadcrumb = new Breadcrumb();
    private boolean deativated = false;
    protected TimelineChange(DMEReference<T> owner, LocalDate date) {
        this.owner = owner;
        this.start = date;
    }
    protected TimelineChange(DMEReference<T> owner, LocalDate date, JsonObject additionalData) {
        this.owner = owner;
        this.start = date;
        onLoad(additionalData);
    }
    protected enum ChangeTags{
        RELATIONSHIP_CHANGE,
        MARRIAGE_CHANGE,
        FAMILY_MEMBERSHIP_CHANGE,
        TITLE_CHANGE,
        HOUSE_EMPLOYMENT_CHANGE,
        CHARACTER_DEATH;
    }
    public final long getId(){
        return id.get();
    }
    public final DMEReference<T> getOwner(){
        return owner;
    }
    /**
     * Applies changes to the specified entity by invoking the {@code onApply} method to handle
     * timeline state transitions. Additional logic can be implemented here if necessary.
     *
     * @param entity the entity on which the apply operation is being performed. This represents
     *               the target for timeline changes, ensuring accurate and consistent state updates.
     */
    public final void apply(T entity){
        onApply(entity);
        //may merge with onApply if I don't need any additional logic in here
    }

    public final void overwrite(TimelineState<T> currentState, TimelineChange<T> beingOverwritten, boolean destructive){
        onOverwrite(getOwner().link(),currentState,beingOverwritten,destructive);
        if (destructive){
            currentState.removeChange(beingOverwritten.getId());
            TimelineHelper.BreadcrumbCleanup(getOwner().link().getTimeline(),beingOverwritten.getId(),beingOverwritten.getStart(),beingOverwritten.getEnd());
        } else {
            beingOverwritten.deactivate();
        }
        currentState.insertChange(this);
    }
    public final void nullify(T entity, TimelineState<T> state, TimelineChange<T> changeToNullify){
        onNullify(entity,state,changeToNullify);
        state.
    }
    //What to do when the provided object is getting the change from this object applied to it.
    protected abstract void onApply(T entity);
    /**
     * Handles an overwrite operation for a specific entity. This method is used to create or update
     * the timeline state after an overwrite operation, ensuring proper handling of state changes.
     * Note: anyAddStateChange must be set to false to avoid duplication in saving or causing issues
     * in subsequent operations.
     *
     * @param entity the entity for which the overwrite operation is being performed. This represents the target of the timeline change.
     * @return a new or updated {@code TimelineState<T,C>} instance representing the state of the timeline after the overwrite operation.
     */
    protected TimelineState<T> onOverwrite(T entity, TimelineState<T> currentState, TimelineChange<T> beingOverwritten, boolean destructive){
        return onApply(entity, false);
    };

    protected TimelineState<T> onNullify(T entity,@Nullable TimelineChange<T> stateToNullify){
        if (stateToNullify != null){
            stateToNullify.nullify(entity,null);
        }
        return doNullify(entity.getCurrentState());
    }
    private TimelineState<T> doNullify(TimelineState<T> state){
        state.changeLog().remove(this);
        return state;
    }
    public boolean isOpposite(TimelineChange<?> state){
        return oppositeChanges().contains(state.getClass());
    }
    public boolean isSame(TimelineChange<?> state){
        return this.getClass().equals(state.getClass());
    }
    public abstract List<Class<? extends TimelineChange<T>>> oppositeChanges();
    public List<Class<? extends TimelineChange<T>>> siblingChanges(){
        return new ArrayList<>();
    };
    public abstract boolean isPositive();
    public void onContinue(){}
    protected abstract ChangeTags[] getTags();
    public final boolean canNullify(TimelineChange<?> state){
        boolean hasYes = false;
        for (Condition<ConditionResult.Nullify,?> c : nullifyConditions) {
            final ConditionResult.Nullify result = c.check(this,state, this.getSideCar()).orElse(NullifyConditions.NOT_NULLIFY_NON_EXCLUSIVE);
            if (result.canNullify()){
                hasYes = true;
                continue;
            } else if(!result.isOr()){
                return false;
            }
        }
        return hasYes;
    };
    public final List<StateError> doesConflict(TimelineChange<?> state){
        List<StateError> currentErrors = new ArrayList<>();
        for (Condition<StateError,?> c : applyConditions) {
            Optional<StateError> error = c.check(this,state, this.getSideCar());
            error.ifPresent(currentErrors::add);
        }
        return currentErrors;
    };
    protected <C extends Sidecar> C getSideCar(){
        return Sidecar.empty();
    }

    public abstract HashSet<DMEReference<?>> getScope();
    protected abstract String getText();
    public final JsonObject serialize() {
        String type = this.getClass().getSimpleName();
        JsonObject object = new JsonObject();
        JsonObject metadata = new JsonObject();
        JsonObject payload = new JsonObject();
        saveAdditional(payload);
        metadata.add("subject", owner.serialize());
        metadata.addProperty("type", type);
        metadata.addProperty("date", start.toEpochDay());
        metadata.add("breadcrumb", breadcrumb.toJson());
        object.add("payload", payload);
        object.add("metadata", metadata);
        return object;
    }
    public static <R extends TimelineChange<T>,T extends DateMutableEntity<T>> R deserialize(JsonObject payload) {
        String type = payload.get("type").getAsString();
        JsonObject payloadObject = payload.getAsJsonObject("payload");
        JsonObject metadata = payload.getAsJsonObject("metadata");
        try {
            Class<R> clazz = (Class<R>) Class.forName(type);
            LocalDate date = LocalDate.ofEpochDay(metadata.get("date").getAsLong());
            DMEReference<T> owner = DMEReference.deserialize(metadata.getAsJsonObject("subject"));
            R r = TLChanges.getChange(clazz,owner,date);
            r.onLoad(payloadObject);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not deserialize timeline change: " + type);
        }
    }

    protected boolean containsMyTags(TimelineChange<?> state){
        return containsMyTags(state.getTags());
    }
    protected boolean containsMyTags(ChangeTags[] tags){
        for (ChangeTags tag : tags) {
            if (tag.equals(this.getTags()[0])) return false;
        }
        return true;
    }
    public boolean dateMatch(TimelineChange<?> state){
        return start.equals(state.start);
    }
    public LocalDate getStart() {
        return start;
    }
    public void moveChange(@Nullable LocalDate newStart, @Nullable LocalDate newEnd){
        final LocalDate setStart = start;
        final LocalDate setEnd = breadcrumb.getEndOfPropagation();
        Timeline<T> timeline = owner.link().getTimeline();
        if (newStart != null){
            start = newStart;
            TimelineState<T> state = timeline.getOrMakeState(newStart);
            TimelineState<T> removeFrom = timeline.getStateAt(setStart);
            removeFrom.removeChange(this.getId());
            state.insertChange(this);
        }
        if (newEnd != null){
            breadcrumb.addEndPoint(newEnd);
        }
        TimelineHelper.BreadcrumbCleanup(timeline,getId(), setStart,setEnd);
        TimelineHelper.propagateBreadcrumb(timeline,this);
    }
    public LocalDate getEnd(){
        return breadcrumb.getEndOfPropagation();
    }
    /**
     * Safely adds a {@link DMEReference} of the given {@link ObjectType} and {@link UUID} to the provided
     * {@code HashSet} if it is not already present. This method ensures duplicates are avoided in the set.
     *
     * @param map the {@code HashSet} to which the {@code DMEReference} object is to be added
     * @param type the {@code ObjectType} of the entity associated with the {@code DMEReference}
     * @param uuid the {@code UUID} of the entity associated with the {@code DMEReference}
     */
    protected void safeAddToSet(HashSet<DMEReference<?>> map, ObjectType type, UUID uuid){
        //Internal method exclusively used by getScope to safely build DMES and add to the scope set
        DMEReference<?> reference = new DMEReference<>(type,uuid);
        if (map.contains(reference)) return;
        map.add(reference);
    }
    private List<Condition<StateError,?>> initApplyConditions(){
        List<Condition<StateError,?>> conditions = new ArrayList<>();
        conditions.addAll(ApplyConditions.BaseConditions());
        conditions.addAll(this.buildApplyConditions());
        return conditions;
    }
    private List<Condition<ConditionResult.Nullify,?>> initNullifyConditions(){
        List<Condition<ConditionResult.Nullify,?>> conditions = new ArrayList<>();
        conditions.addAll(NullifyConditions.BaseConditions());
        conditions.addAll(this.buildNullifyConditions());
        return conditions;
    }
    //Handling deactivation
    public boolean isDeactivated(){
        return deativated;
    }
    public void deactivate(){
        deativated = true;
        Timeline<T> tl = owner.link().getTimeline();
        TimelineHelper.BreadcrumbCleanup(tl,getId(), start,breadcrumb.getEndOfPropagation());
        TimelineChange<T> lastData = TimelineHelper.getLastValidChange(tl,tl.getStateAt(this.start),this);
        lastData.moveChange(null,breadcrumb.getEndOfPropagation());
    }
    public void reactivate(boolean sandbox){
        if (sandbox){
            //TODO have it do a quick sandbox run to check if it's all good. I want to unify the sandbox process in a handler.
        } else {
            TimelineHelper.propagateBreadcrumb(owner.link().getTimeline(),this);
        }


        deativated = false;

    }
    //Wrapper functions for breadcrumb.
    public Breadcrumb getBreadcrumb(){
        return breadcrumb;
    }
    public void addEndPoint(LocalDate date){
        breadcrumb.addEndPoint(date);
    }
    public void addError(StateError error, TimelineChange<?> newChange, TimelineChange<?> existingChange, @Nullable Integer proceduralInteger, String resolutionCode){
        breadcrumb.insertError(error,newChange,existingChange,proceduralInteger,resolutionCode);
    }
    protected abstract List<Condition<StateError,?>> buildApplyConditions();
    protected abstract List<Condition<ConditionResult.Nullify,?>> buildNullifyConditions();

    @SuppressWarnings("UnstableApiUsage")
    private long generateID(){
        Hasher hasher = Hashing.murmur3_128().newHasher();
        hasher.putLong(start.toEpochDay());
        hasher.putString(this.getClass().getSimpleName(), StandardCharsets.UTF_8);
        hasher.putLong(owner.hash());
        for (DMEReference<?> dme : additionalIDVars()) {
            hasher.putLong(dme.hash());
        }
        return hasher.hash().asLong();
    }
    protected List<DMEReference<?>> additionalIDVars(){
        return new ArrayList<>();
    }



    public static class Breadcrumb implements JsonSerializable<Breadcrumb>{
        private LocalDate endOfPropagation;
        private final HashMap<Long,String> errorResolutionLog = new HashMap<>();
        public Breadcrumb() {}

        public void addEndPoint(LocalDate date){
            endOfPropagation = date;
        }
        public void insertError(StateError error, TimelineChange<?> newChange, TimelineChange<?> existingChange, @Nullable Integer proceduralInteger, String resolutionCode) {
            errorResolutionLog.put(error.generateID(newChange, existingChange, proceduralInteger), resolutionCode);
        }
        public void insertError(long fullId, String resolutionCode) {
            errorResolutionLog.put(fullId, resolutionCode);
        }
        public Optional<String> getResolutionCode(long id){
            return Optional.ofNullable(errorResolutionLog.get(id));
        }
        public Optional<String> getResolutionCode(StateError error, TimelineChange<?> newChange, TimelineChange<?> existingChange, @Nullable Integer proceduralInteger){
            return getResolutionCode(error.generateID(newChange, existingChange, proceduralInteger));
        }
        public boolean isComplete(){
            return endOfPropagation != null;
        }
        public LocalDate getEndOfPropagation(){
            return endOfPropagation;
        }

        @Override
        public final JsonObject toJson() {
            JsonObject object = new JsonObject();
            if (endOfPropagation != null){
                object.addProperty("eop",endOfPropagation.toEpochDay());
            }
            JsonArray errors = new JsonArray();
            for (Map.Entry<Long, String> entry : errorResolutionLog.entrySet()) {
                JsonObject error = new JsonObject();
                error.addProperty("i",entry.getKey());
                error.addProperty("r",entry.getValue());
                errors.add(error);
            }
            object.add("errors",errors);
            return object;
        }

        @Override
        public final void fromJson(JsonObject json) {
            if (json.has("eop")){
                this.endOfPropagation = LocalDate.ofEpochDay(json.get("eop").getAsLong());
            };
            JsonArray errors = json.getAsJsonArray("errors");
            for (int i = 0; i < errors.size(); i++) {
                JsonObject error = errors.get(i).getAsJsonObject();
                long id = error.get("i").getAsLong();
                String resolution = error.get("r").getAsString();
                errorResolutionLog.put(id,resolution);
            }
        }

        @Override
        public Breadcrumb empty() {
            return new Breadcrumb();
        }
    }
}
