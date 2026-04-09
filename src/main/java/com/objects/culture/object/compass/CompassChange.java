package com.objects.culture.object.compass;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.TimelineSingleChange;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonObject;
import com.objects.culture.object.CultureObject;

import java.time.LocalDate;
import java.util.List;

public class CompassChange<T extends DateMutableEntity<T>> extends TimelineSingleChange<T> {

    private PoliticalCompass compass;
    protected CompassChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }


    public CompassChange(DMEReference<?> owner, LocalDate date, PoliticalCompass compass) {
        super((DMEReference<? extends T>) owner, date);
        this.compass = compass;
    }

    @Override
    protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
        if (getOwner() instanceof CultureObject<?,?,?> to){
            to.internalSetCompass(compass);
        }
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
        return "";
    }

    @Override
    public void additionalSave(JsonObject data) {
        data.add("compass", compass.toJson());
    }

    @Override
    public void additionalLoad(JsonObject data) {
        compass = PoliticalCompass.build(data.get("compass").getAsJsonObject());
    }
}
