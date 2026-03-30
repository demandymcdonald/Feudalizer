package com.base.timeline.change;

import com.base.DateMutableEntity;
import com.base.timeline.flags.StateError;
import com.base.reference.DMEReference;
import com.base.timeline.TimelineState;
import com.base.timeline.change.conditions.ApplyConditions;
import com.base.timeline.change.conditions.Condition;
import com.base.timeline.change.conditions.ConditionResult;
import com.google.gson.JsonObject;
import com.simulation.character.BookCharacter;
import com.simulation.people.Family;
import com.simulation.people.House;
import com.simulation.title.Title;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static com.base.timeline.change.CauseOfDeath.NOT_LOADED;

public abstract class BoundaryChange<B extends BoundaryChange<B,T>,T extends DateMutableEntity<T>> extends TimelineChange<T> {
    private boolean isBirth;
    protected BoundaryChange(LocalDate date, DMEReference<T> subject, boolean isBirth) {
        super(subject, date);
        this.isBirth = isBirth;
    }

    protected BoundaryChange(DMEReference<T> owner, LocalDate date) {
        super(owner, date);
    }

    @Override
    protected void onApply(T entity, TimelineState<T> currentState) {

    }

    @Override
    protected void onOverwrite(T entity, TimelineState<T> currentState, TimelineChange<T> beingOverwritten, boolean destructive) {
        super.onOverwrite(entity, currentState, beingOverwritten, destructive);
    }

    @Override
    protected ChangeTags[] getTags() {
        return new ChangeTags[0];
    }


    @Override
    protected boolean containsMyTags(ChangeTags[] tags) {
        return super.containsMyTags(tags);
    }

    @Override
    protected List<Condition<ConditionResult.Nullify, ? super T>> buildNullifyConditions() {
        return List.of();
    }

    @Override
    public void saveAdditional(JsonObject data) {

    }

    @Override
    public void loadAdditional(JsonObject data) {

    }
}
