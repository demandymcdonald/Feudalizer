package com.objects.culture.object.compass;

import com.Global;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.Timeline;
import com.base.timeline.TimelineObject;
import com.base.timeline.change.ChangeID;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.TimelineSingleChange;
import com.base.timeline.state.TimelineState;
import com.base.timeline.variable.EasingChange;
import com.google.gson.JsonObject;
import com.objects.culture.object.CultureObject;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

public class CompassChange<T extends DateMutableEntity<T> & CultureObject<T>> extends TimelineSingleChange<T> implements EasingChange<InterpolatedPoliticalCompass<T>,CompassChange<T>,T> {

    private InterpolatedPoliticalCompass<? extends T> compass;
    protected CompassChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }


    public CompassChange(DMEReference<? extends T> owner, LocalDate date, InterpolatedPoliticalCompass<? extends T> compass) {
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
        if (getOwner() instanceof CultureObject<?> to){
            InterpolatedPoliticalCompass<?> com = (InterpolatedPoliticalCompass<?>) compass;
            to.internalSetCompass(com);
        }
    }
    public InterpolatedPoliticalCompass<? extends T> getCompass() {
        return compass;
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
        compass = new InterpolatedPoliticalCompass<>();
        compass.deserialize(data.get("compass").getAsJsonObject());
    }

    @Override
    public InterpolatedPoliticalCompass<T> getEasingVariable(Predicate<InterpolatedPoliticalCompass<T>> matching) {
        return getOwner().get().getCompass();
    }

    @Override
    public CompassChange<T> getNext() {
        return TimelineObject.getChangeStep(this, Global.TimeDirection.FORWARD,false,1,null);
    }
}
