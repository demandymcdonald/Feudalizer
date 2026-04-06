package com.base.timeline.variable;

import com.Global;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.Timeline;
import com.base.timeline.change.ChangeID;
import com.base.timeline.change.TimelineChange;
import com.google.gson.JsonObject;
import com.utilities.JsonSerializable;
import com.utilities.SuperclassSerializable;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;

public abstract class TimelineEasingVariable<C extends TimelineChange<? super T>, T extends DateMutableEntity<T>> implements SuperclassSerializable<TimelineEasingVariable<C,T>> {
    DMEReference<T> owner;
    ChangeID thisChange;
    EasingType easeType;
    ChangeID nextChange;

    public TimelineEasingVariable(DMEReference<T> owner, ChangeID thisChange, EasingType easeType, ChangeID nextChange) {
        this.owner = owner;
        this.thisChange = thisChange;
        this.easeType = easeType;
        this.nextChange = nextChange;
    }
    public TimelineEasingVariable() {}
    public enum EasingType {
        LERP(new EasingFunction(){
            @Override
            protected double apply(double a, double b, double t) {
                return a + (b - a) * t;
            }
        }),
        QUAD(new EasingFunction(){
            @Override
            protected double apply(double a, double b, double t) {
                return a + (b - a) * (t * t);
            }
        }),
        CUBIC(new EasingFunction(){
            @Override
            protected double apply(double a, double b, double t) {
                return a + (b - a) * (t * t * t);
            }
        }),
        EXPONENTIAL(new EasingFunction(){
            @Override
            protected double apply(double a, double b, double t) {
                return a * (b / a) * (Math.pow(2,(10D * (t - 1D))));
            }
        }),
        SINE(new EasingFunction(){
            @Override
            protected double apply(double a, double b, double t) {
                return a + (b - a) * (1 - Math.cos(t * Math.PI / 2));
            }
        }),
        ;

        private final EasingFunction easingFunction;

        EasingType(EasingFunction easingFunction) {
            this.easingFunction = easingFunction;
        }
        public EasingFunction get() {
            return easingFunction;
        }
    }


    protected abstract double getCurrentValue();
    protected abstract double getValueFromNextChange(C nextChange);

    public final double getValue(){
        final Timeline<T> changeTimeline = owner.get().getTimeline();
        final LocalDate start = changeTimeline.followBreadcrumb(thisChange).getStart();
        C timelineChange = (C) changeTimeline.followBreadcrumb(nextChange);
        LocalDate end = timelineChange.getStart();
        return easeType.get().calculate(getCurrentValue(),getValueFromNextChange(timelineChange),start,end);

    }

    public EasingType getEaseType() {
        return easeType;
    }

    public ChangeID getNextChange() {
        return nextChange;
    }


    public DMEReference<T> getOwner() {
        return owner;
    }

    public ChangeID getThisChange() {
        return thisChange;
    }


    public static abstract class EasingFunction{
        public final double calculate(double start, double end, LocalDate startDate, LocalDate endDate){
            double progress;
            if (startDate.isAfter(endDate)){
                progress = (double) startDate.toEpochDay() / endDate.toEpochDay();
                return apply(start,end,progress);
            } else {
                progress  = (double) endDate.toEpochDay() / startDate.toEpochDay();
                return apply(end,start,progress);
            }
        }
        protected abstract double apply(double a, double b, double t);
    }
    private static <C extends TimelineChange<? super T>, T extends DateMutableEntity<T>> C findC(DMEReference<T> ref, ChangeID existingChange){
        Timeline<T> timeline = ref.get().getTimeline();
        C existing = timeline.followBreadcrumb(existingChange);
        return (C) timeline.findChangeByClassID(timeline,existing.getStart(), Global.TimeDirection.FORWARD, existing.getClass(), false).getFirst();
    }
    protected static <C extends TimelineChange<? super T>, T extends DateMutableEntity<T>> C getC(DMEReference<T> ref, ChangeID change){
        Timeline<T> timeline = ref.get().getTimeline();
        return (C) timeline.followBreadcrumb(change);
    }

    @Override
    public final void mainSave(JsonObject object) {
        object.addProperty("easeType",easeType.name());
        object.add("owner",owner.serialize());
        object.add("thisChange",thisChange.toJson());
        if (nextChange != null){
            object.add("nextChange",nextChange.toJson());
        }
    }

    @Override
    public final void mainLoad(JsonObject object) {
        easeType = EasingType.valueOf(object.get("easeType").getAsString());
        owner = DMEReference.deserialize(object.getAsJsonObject("owner"));
        thisChange = ChangeID.fromJson(object.getAsJsonObject("thisChange"));
        if (object.has("nextChange")){
            nextChange = ChangeID.fromJson(object.getAsJsonObject("nextChange"));
        } else {
            nextChange = findC(owner,thisChange).getID();
        }
    }
}
