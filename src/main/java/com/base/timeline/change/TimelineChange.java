package com.base.timeline.change;

import com.base.DateMutableEntity;
import com.base.ObjectType;
import com.base.timeline.Timeline;
import com.base.timeline.TimelineHelper;
import com.base.timeline.flags.SandboxCode;
import com.base.timeline.flags.StateError;
import com.base.reference.DMEReference;
import com.base.timeline.TimelineState;
import com.base.timeline.change.conditions.*;
import com.base.timeline.sandbox.core.Objective;
import com.base.timeline.sandbox.core.SandboxHandler;
import com.google.common.base.Suppliers;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.utilities.JsonSerializable;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.utilities.SuperclassSerializable;
import org.apache.commons.lang3.tuple.Pair;

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
public abstract class TimelineChange<T extends DateMutableEntity<T>> implements SuperclassSerializable {
    // Rules: TimelineChange implementations should only save/use StateReferences! Never use actual objects. This keeps them sandbox safe. Include this fact in documentation
    private LocalDate start;
    private final DMEReference<T> owner;
    private final Supplier<Long> id = Suppliers.memoize(this::generateID);
    private SandboxBreadcrumb breadcrumb = new SandboxBreadcrumb();
    private boolean deactivated = false;
    private boolean isStatic = false;
    protected TimelineChange(DMEReference<T> owner, LocalDate date) {
        this.owner = owner;
        this.start = date;
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
    public final void apply(T entity, TimelineState<T> currentState){
        onApply(entity,currentState);
        //may merge with onApply if I don't need any additional logic in here
    }
    public final void overwrite(TimelineState<T> currentState, TimelineChange<T> beingOverwritten, boolean destructive){
        onOverwrite(getOwner().get(),currentState,beingOverwritten,destructive);
        if (destructive){
            currentState.removeChange(beingOverwritten.getId());
            TimelineHelper.BreadcrumbCleanup(getOwner().get().getTimeline(),beingOverwritten.getId(),beingOverwritten.getStart(),beingOverwritten.getEnd());
        } else {
            beingOverwritten.deactivate();
        }
        currentState.insertChange(this);
    }
    public final void nullify(T entity, TimelineState<T> state, TimelineChange<T> changeToNullify){
        onNullify(entity,state,changeToNullify);
        changeToNullify.deactivate();
    }
    public final void deactivate(boolean sandbox){
        SandboxCode c = SandboxCode.CONTINUE;

        if (sandbox){
            SandboxHandler.SandboxApplyChange(new Objective<>(owner,this),start,null);
        } else {

        }
        onDeactivate();
        deactivated = true;
        Timeline<T> tl = owner.get().getTimeline();
        TimelineHelper.BreadcrumbCleanup(tl,getId(), start,breadcrumb.getEndOfPropagation());
        TimelineChange<T> lastData = TimelineHelper.getLastValidChange(tl,tl.getStateAt(this.start),this);
        lastData.moveChange(null,breadcrumb.getEndOfPropagation());
    }
    public void reactivate(boolean sandbox){
        onReactivate();
        deactivated = false;
    }
    public void moveChange(@Nullable LocalDate newStart, @Nullable LocalDate newEnd){
        final LocalDate setStart = start;
        final LocalDate setEnd = breadcrumb.getEndOfPropagation();
        Timeline<T> timeline = owner.get().getTimeline();
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
    //What to do when the provided object is getting the change from this object applied to it.
    protected abstract void onApply(T entity, TimelineState<T> currentState);

    protected void onOverwrite(T entity, TimelineState<T> currentState, TimelineChange<T> beingOverwritten, boolean destructive){

    };
    protected void onNullify(T entity, TimelineState<T> currentState, TimelineChange<T> beingOverwritten){

    }
    protected void onDeactivate(){}
    protected void onReactivate(){}
    protected void onMove(LocalDate newStart, LocalDate newEnd, TimelineState<T> oldState, TimelineState<T> newState){}

    public boolean isOpposite(TimelineChange<?> state){
        return oppositeChanges().contains(state.getClass());
    }
    public boolean isSame(TimelineChange<?> state){
        return this.getClass().equals(state.getClass());
    }
    public abstract List<Class<TimelineChange<? super T>>> oppositeChanges();
    public List<Class<? super T>> siblingChanges(){
        return new ArrayList<>();
    };
    public abstract boolean isPositive();
    public void onContinue(){}
    protected abstract ChangeTags[] getTags();
    public final boolean canNullify(TimelineChange<? super T> toNullify){
        boolean hasYes = false;
        for (Condition<ConditionResult.Nullify,? super T> c : nullConditions.get()) {
            final ConditionResult.Nullify result = c.check(this.getOwner().get(),this,toNullify).orElse(NullifyConditions.NOT_NULLIFY_NON_EXCLUSIVE);
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
    public final List<StateError> doesConflict(TimelineChange<?> state){
        List<StateError> currentErrors = new ArrayList<>();
        for (Condition<StateError,? super T> c : applyConditions.get()) {
            Optional<StateError> error = c.check(this.getOwner().get(),this,state);
            error.ifPresent(currentErrors::add);
        }
        return currentErrors;
    };
    public abstract HashSet<DMEReference<?>> getScope();
    protected abstract String getText();


    protected boolean containsMyTags(TimelineChange<?> state){
        return containsMyTags(state.getTags());
    }
    protected boolean containsMyTags(ChangeTags[] tags){
        for (ChangeTags tag : tags) {
            if (tag.equals(this.getTags()[0])) return false;
        }
        return true;
    }
    public LocalDate getStart() {
        return start;
    }

    public final LocalDate getEnd(){
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
    protected static void safeAddToSet(HashSet<DMEReference<?>> map, ObjectType type, UUID uuid){
        //Internal method exclusively used by getScope to safely build DMES and add to the scope set
        DMEReference<?> reference = DMEReference.of(type,uuid);
        if (map.contains(reference)) return;
        map.add(reference);
    }

    //Handling deactivation
    public boolean isDeactivated(){
        return deactivated;
    }


    public final Pair<Long,LocalDate> buildStateBreadcrumb(){
        return Pair.of(getId(),getStart());
    }
    //Wrapper functions for breadcrumb.
    public final SandboxBreadcrumb getBreadcrumb(){
        return breadcrumb;
    }
    public final void addEnd(LocalDate date){
        breadcrumb.addEndPoint(date);
    }
    public final void addError(StateError error, TimelineChange<?> newChange, TimelineChange<?> existingChange, @Nullable Integer proceduralInteger, String resolutionCode){
        breadcrumb.insertError(error,newChange,existingChange,proceduralInteger,resolutionCode);
    }

    private final Supplier<List<Condition<ConditionResult.Nullify,? super T>>> nullConditions =
            Suppliers.memoize(() -> {
                List<Condition<ConditionResult.Nullify,? super T>> conditions = new ArrayList<>();
                conditions.addAll(NullifyConditions.BaseConditions());
                conditions.addAll(this.buildNullifyConditions());
                return conditions;
            });
    private final Supplier<List<Condition<StateError,? super T>>> applyConditions =
            Suppliers.memoize(() -> {
                List<Condition<StateError,? super T>> conditions = new ArrayList<>();
                conditions.addAll(ApplyConditions.BaseConditions());
                conditions.addAll(this.buildApplyConditions());
                return conditions;
            });
    protected abstract List<Condition<StateError,? super T>> buildApplyConditions();
    protected abstract List<Condition<ConditionResult.Nullify,? super T>> buildNullifyConditions();

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
    protected void setStatic(){
        isStatic = true;
    }
    public final boolean isStatic(){
        return isStatic;
    }
    protected List<DMEReference<?>> additionalIDVars(){
        return new ArrayList<>();
    }
    @Override
    public final void saveMain(JsonObject o) {
        o.add("breadcrumb", breadcrumb.toJson());
    }

    @Override
    public final void saveMetadata(JsonObject o) {
        o.add("subject", owner.serialize());
        o.addProperty("date", start.toEpochDay());
    }
    public static class SandboxBreadcrumb implements JsonSerializable<SandboxBreadcrumb>{
        private LocalDate endOfPropagation;
        private final HashMap<Long,String> errorResolutionLog = new HashMap<>();
        public SandboxBreadcrumb() {}

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

        public final boolean isComplete(){
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
        public SandboxBreadcrumb empty() {
            return new SandboxBreadcrumb();
        }
    }
}
