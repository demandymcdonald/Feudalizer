package com.base.timeline.change;

import com.base.DateMutableEntity;
import com.base.ObjectType;
import com.base.timeline.Timeline;
import com.base.timeline.TimelineHelper;
import com.base.timeline.flags.SandboxCode;
import com.base.timeline.flags.StateError;
import com.base.reference.DMEReference;
import com.base.timeline.state.TimelineState;
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

    //TODO Third kind of check: once per TimelineState (For things like: is still alive, or canHoldTitle, or Does this Religion exist?
    private LocalDate start;
    private final DMEReference<? extends T> owner;
    private final ChangeID id;
    private SandboxBreadcrumb breadcrumb = new SandboxBreadcrumb();
    private boolean deactivated = false;
    private boolean isStatic = false;
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
        //may merge with onApply if I don't need any additional logic in here
    }
    public void advance(DMEReference<? extends T> entity, TimelineState<? extends T> currentState, TimelineChange<?> change, boolean isFirstAdvance){
        onAdvance(currentState,change,isFirstAdvance);

    }

    public void overwrite(TimelineState<? extends T> currentState, TimelineChange<?>  beingOverwritten, boolean destructive){
        onOverwrite(getOwner(),currentState,beingOverwritten,destructive);
        if (destructive){
            currentState.removeChange(beingOverwritten.getID());
        } else {
            beingOverwritten.deactivate(true);
        }
        currentState.insertChange(this);
    }
    public void nullify(DMEReference<? extends T> entity, TimelineState<? extends T> state, TimelineChange<?> changeToNullify){
        onNullify(entity,state,changeToNullify);
        changeToNullify.deactivate(true);
    }
    public void deactivate(boolean sandbox){
        SandboxCode c = SandboxCode.END_SAVE;
        if (sandbox){
            c = SandboxHandler.SandboxApplyChange(Objective.build(owner,this),start,null);
        }
        if (c == SandboxCode.END_SAVE){
            onDeactivate();
            deactivated = true;
        }
    }
    public void reactivate(boolean sandbox){
        onReactivate();
        deactivated = false;
    }
    public void moveChange(@Nullable LocalDate newStart, @Nullable LocalDate newEnd){
        if (newStart != null){
            start = newStart;
        }
        if (newEnd != null){
            breadcrumb.addEndPoint(newEnd);
        }
    }
    public final TimelineChange<? super T> helper(TimelineChange<? super T> change){
        return change;

    }


    //==================================================================================================================
    //What to do when the provided object is getting the change from this object applied to it.
    protected abstract void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState);

    protected <T extends DateMutableEntity<T>> void onOverwrite(DMEReference<? extends T> entity, TimelineState<? extends T> currentState, TimelineChange<?> beingOverwritten, boolean destructive){};
    protected <T extends DateMutableEntity<T>> void onNullify(DMEReference<? extends T> entity, TimelineState<? extends T> currentState, TimelineChange<?> beingNullified){}
    protected <T extends DateMutableEntity<T>> void onDeactivate(){}
    protected <T extends DateMutableEntity<T>> void onReactivate(){}
    protected <T extends DateMutableEntity<T>> void onAdvance(TimelineState<? extends T> currentState, TimelineChange<?> passingtChange, boolean isFirstAdvance){}
    protected <T extends DateMutableEntity<T>> void onMove(LocalDate newStart, LocalDate newEnd,  TimelineState<T> newState, TimelineState<? super T> oldState){}

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
    public void onContinue(){}


    public final boolean canNullify(TimelineChange<?> toNullify){
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
    protected static <Tt extends DateMutableEntity<Tt>> void safeAddToSet(HashSet<DMEReference<?>> map, Class<Tt> type, UUID uuid){
        //Internal method exclusively used by getScope to safely build DMES and add to the scope set
        DMEReference<Tt> reference = DMEReference.of(type,uuid);
        if (map.contains(reference)) return;
        map.add(reference);
    }

    //Handling deactivation
    public boolean isDeactivated(){
        return deactivated;
    }


    public final Pair<Long,LocalDate> buildStateBreadcrumb(){
        return Pair.of(getFullID(),getStart());
    }
    //Wrapper functions for breadcrumb.
    public final SandboxBreadcrumb getBreadcrumb(){
        return breadcrumb;
    }
    public final void addEnd(LocalDate date){
        breadcrumb.addEndPoint(date);
    }
    public final void addError(StateError error, TimelineChange<? super T> newChange, TimelineChange<? super T> existingChange, @Nullable Integer proceduralInteger, String resolutionCode){
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
    private long generateID(){
        return generateID((Class<TimelineChange<T>>) this.getClass(),start,additionalIDVars());
    }
    public static <T extends DateMutableEntity<T>> long generateID(Class<TimelineChange<T>> c , LocalDate start, List<DMEReference<?>> additionalIDVars){
        return generateID(c.getName(),start,additionalIDVars);
    }

    @SuppressWarnings("UnstableApiUsage")
    public static <T extends DateMutableEntity<T>> long generateID(String c , LocalDate start, List<DMEReference<?>> additionalIDVars){
        Hasher hasher = Hashing.murmur3_128().newHasher();
        hasher.putLong(start.toEpochDay());
        hasher.putString(c, StandardCharsets.UTF_8);
        //hasher.putLong(owner.hash());
        //The reason I'm removing the owner long is because it might make bug tracking harder on breadcrumbs, and I
        //genuinely don't think it adds anything to the mix.
        for (DMEReference<?> dme : additionalIDVars) {
            hasher.putLong(dme.hash());
        }
        return hasher.hash().asLong();
    }
    public boolean shouldSandbox(){
        return true;
    }
    protected void setStatic(){
        isStatic = true;
    }
    public final boolean isStatic(){
        return isStatic;
    }
    public List<DMEReference<?>> additionalIDVars(){
        return new ArrayList<>();
    }
    @Override
    public void mainSave(JsonObject o) {
        o.add("breadcrumb", breadcrumb.toJson());
    }
    @Override
    public void mainLoad(JsonObject object) {
        breadcrumb.fromJson(object.getAsJsonObject("breadcrumb"));
    }
    @Override
    public final void metadataSave(JsonObject o) {
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
