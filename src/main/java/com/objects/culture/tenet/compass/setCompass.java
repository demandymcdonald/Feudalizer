package com.objects.culture.tenet.compass;

import com.Global.*;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.TimelineSingleChange;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonObject;
import com.objects.culture.tenet.opinionated.TenetOpinionated;

import java.time.LocalDate;
import java.util.List;

public class setCompass<T extends DateMutableEntity<T>> extends TimelineSingleChange<T> {

    private PoliticalCompass compass;
    protected setCompass(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }


    public setCompass(DMEReference<?> owner, LocalDate date, PoliticalCompass compass) {
        super((DMEReference<? extends T>) owner, date);
        this.compass = compass;
    }

    @Override
    protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
        if (getOwner() instanceof TenetOpinionated<?,?,?> to){
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
