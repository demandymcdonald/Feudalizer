package com.objects.culture.object.change;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.TimelineSingleChange;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonObject;
import com.objects.culture.Culture;
import com.objects.culture.object.MinorCultureObject;

import java.time.LocalDate;
import java.util.List;

public class MinorCultureChange<T extends DateMutableEntity<T> & MinorCultureObject<T>> extends TimelineSingleChange<T> {
    private DMEReference<Culture> parent;
    public MinorCultureChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }
    public MinorCultureChange(DMEReference<? extends T> owner, LocalDate date, DMEReference<Culture> parent) {
        super(owner, date);
        this.parent = parent;
    }

    @Override
    protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
        entity.get().internalSetParent(parent);
    }

    @Override
    public List<Class<TimelineChange<? super T>>> oppositeChanges() {
        return List.of();
    }

    @Override
    public boolean isPositive() {
        return true;
    }

    @Override
    protected String getText() {
        return "minor_culture_change";
    }

    @Override
    public void additionalSave(JsonObject data) {
        data.add("culture_parent", parent.serialize());
    }

    @Override
    public void additionalLoad(JsonObject data) {
        parent = DMEReference.deserialize(data.get("culture_parent").getAsJsonObject());
    }
}
