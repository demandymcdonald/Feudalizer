package com.base.timeline.change;

import com.base.DateMutableEntity;
import com.base.ObjectType;
import com.base.flags.StateError;
import com.base.reference.DMEReference;
import com.base.timeline.TimelineState;
import com.google.common.collect.HashMultimap;
import com.google.gson.JsonObject;
import com.simulation.people.BookCharacter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.base.flags.Errors.newHolderDead;


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
public abstract class TimelineChange<T extends DateMutableEntity<T,?>> {
    // Rules: TimelineChange implementations should only save/use StateReferences! Never use actual objects. This keeps them sandbox safe. Include this fact in documentation
    private final LocalDate date;

    protected TimelineChange(LocalDate date) {
        this.date = date;
    }

    protected enum ChangeTags{
        RELATIONSHIP_CHANGE,
        MARRIAGE_CHANGE,
        FAMILY_MEMBERSHIP_CHANGE,
        TITLE_CHANGE,
        HOUSE_EMPLOYMENT_CHANGE,
        CHARACTER_DEATH;
    }
    /**
     * Applies changes to the specified entity by invoking the {@code onApply} method to handle
     * timeline state transitions. Additional logic can be implemented here if necessary.
     *
     * @param entity the entity on which the apply operation is being performed. This represents
     *               the target for timeline changes, ensuring accurate and consistent state updates.
     */
    public void apply(T entity){
        TimelineState<T> state = onApply(entity);
        //may merge with onApply if I don't need any additional logic in here
    }
    /**
     * Performs an overwrite operation on the provided entity. This includes executing the overwrite logic,
     * nullifying any applicable state transitions, and saving the updated state change for the entity
     * to ensure the timeline's consistency and accuracy.
     *
     * @param entity the entity on which the overwrite operation is being performed. This entity serves as the
     *               target for state updates in the timeline.
     */
    public void overwrite(T entity){
        TimelineState<T> state = onOverwrite(entity);
        doNullify(state);
        entity.saveStateChange(state);
    }
    /**
     * Handles the application operation for a specific entity. This method is used to create or
     * update the timeline state when changes are applied to the entity. It ensures the proper
     * handling of any state transitions or updates required for the timeline to maintain consistency
     * and accuracy.
     *
     * @param entity the entity for which the apply operation is being performed. This represents
     *               the target of the timeline change, and any state changes will reflect on this entity.
     * @return a new or updated {@code TimelineState<T,C>} instance representing the state of the
     *         timeline after the apply operation.
     */
    protected abstract TimelineState<T> onApply(T entity);
    /**
     * Handles an overwrite operation for a specific entity. This method is used to create or update
     * the timeline state after an overwrite operation, ensuring proper handling of state changes.
     * Note: anyAddStateChange must be set to false to avoid duplication in saving or causing issues
     * in subsequent operations.
     *
     * @param entity the entity for which the overwrite operation is being performed. This represents the target of the timeline change.
     * @return a new or updated {@code TimelineState<T,C>} instance representing the state of the timeline after the overwrite operation.
     */
    protected TimelineState<T> onOverwrite(T entity){
        return onApply(entity);
    };
    protected boolean isOpposite(TimelineChange<?> state){
        return validOpposites().contains(state.getClass());
    }
    protected List<Class<? extends TimelineChange<?>>> validOpposites(){
        return new ArrayList<>();
    }
    public void onContinue(){}
    protected abstract ChangeTags[] getTags();
    public boolean canNullify(TimelineChange<?> state){
        return (isOpposite(state) && this.date.equals(state.getDate())) && shouldNullify(state);
    };
    public Optional<StateError> doesConflict(TimelineChange<?> state){
        Optional<StateError> base = baseChecks(state);
        if (base.isPresent()) return base;
        if (!shouldCheck(state)) return Optional.empty();
        return checkConflict(state);
    };
    protected boolean shouldNullify(TimelineChange<?> change){
        return true;
    }
    protected abstract boolean shouldCheck(TimelineChange<?> t);
    public abstract Optional<StateError> checkConflict(TimelineChange<?> state);
    public void doNullify(TimelineState<?> state){
        state.changeLog().remove(this);
    };
    public abstract HashMultimap<ObjectType, UUID> getScope();
    public StateError[] doesTimelineConflict(TimelineState<?> state){
        List<StateError> errors = new ArrayList<>();
        for (TimelineChange<?> t : state.changeLog()) {
            Optional<StateError> error = checkConflict(t);
            error.ifPresent(errors::add);
        }
        return errors.toArray(new StateError[0]);
    };
    protected abstract String getText();
    protected abstract JsonObject toJson();
    public final JsonObject serialize() {
        String type = this.getClass().getSimpleName();
        JsonObject payload = toJson();
        payload.addProperty("type", type);
        return payload;
    }
    public static <R extends TimelineChange<T>,T extends DateMutableEntity<T,?>> T deserialize(JsonObject payload) {
        String type = payload.get("type").getAsString();
        switch (type) {
            default: throw new IllegalArgumentException("Unknown timeline change type: " + type);
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
        return date.equals(state.date);
    }
    public Optional<StateError> baseChecks(TimelineChange<?> state){
        if (state instanceof TitleTLChanges<?> te){
            Optional<DMEReference<BookCharacter>> holder = te.getHolder();
            if (holder.isPresent() && !holder.get().link().isAlive()){
                return Optional.of(newHolderDead(te.getTitle().link()));
            }
        }
        return Optional.empty();
    }
    public LocalDate getDate() {
        return date;
    }
}
