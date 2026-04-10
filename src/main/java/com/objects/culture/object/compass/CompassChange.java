package com.objects.culture.object.compass;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.Timeline;
import com.base.timeline.change.ChangeID;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.TimelineSingleChange;
import com.base.timeline.change.map.TimelineMapChange;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonObject;
import com.objects.culture.Culture;
import com.objects.culture.object.CultureObject;
import com.objects.culture.tenet.instance.CultureTenetInstance;
import com.objects.culture.tenet.types.Tenet;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class CompassChange<T extends DateMutableEntity<?>> extends TimelineSingleChange<T> {

    private InterpolatedPoliticalCompass<T> compass;
    protected CompassChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }


    public CompassChange(DMEReference<?> owner, LocalDate date, InterpolatedPoliticalCompass<?> compass) {
        super((DMEReference<? extends T>) owner, date);
        this.compass = (InterpolatedPoliticalCompass<T>) compass;
    }
    public InterpolatedPoliticalCompass<? extends T> getFromFuture(ChangeID current, InterpolatedPoliticalCompass<? extends T> currentInstance){
        Timeline<? extends T> timeline = (Timeline<? extends T>) getOwner().get().getTimeline();
        CompassChange<? extends T> c = (CompassChange<? extends T>) timeline.followBreadcrumb(current);

        return c.getCompass();
    }
    @Override
    protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
        if (getOwner() instanceof CultureObject<?,?,?> to){
            to.internalSetCompass(compass);
        }
    }
    public InterpolatedPoliticalCompass<T> getCompass() {
        return compass;
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
        compass = (InterpolatedPoliticalCompass<T>) InterpolatedPoliticalCompass.build(data.get("compass").getAsJsonObject());
    }
}
