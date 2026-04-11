package com.base.utilities;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.Timeline;
import com.base.timeline.change.ChangeID;
import com.base.timeline.change.TimelineChange;
import com.google.gson.JsonObject;
import com.utilities.serialization.SuperclassSerializable;

import java.time.LocalDate;
@Deprecated(forRemoval = true)
public abstract class TimelineEasingVariable<E extends TimelineEasingVariable<E,C,T>,C extends TimelineChange<? super T>, T extends DateMutableEntity<T>> implements SuperclassSerializable<TimelineEasingVariable<E,C,T>> {
    DMEReference<T> owner;
    ChangeID thisChange;
    EasingType easeType;
    public TimelineEasingVariable(DMEReference<T> owner, ChangeID thisChange, EasingType easeType) {
        this.owner = owner;
        this.thisChange = thisChange;
        this.easeType = easeType;
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
                return a + (b - a) * (t < 0.5 ? 2 * Math.pow(t, 2) : 1 - Math.pow(-2 * t + 2, 2) / 2);
            }
        }),
        CUBIC(new EasingFunction(){
            @Override
            protected double apply(double a, double b, double t) {
                return a + (b - a) * (t < 0.5 ? 4 * Math.pow(t, 3) : 1 - Math.pow(-2 * t + 2, 3) / 2);
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


    protected abstract double getCurrent();
    protected abstract E findE(DMEReference<T> ref, ChangeID current);

    protected final double getFinal(){
        E other = findE(owner,thisChange);
        ChangeID otherID = other.getThisChange();
        return easeType.get().calculate(getCurrent(),other.getCurrent(),getThisChange().getDate(),otherID.getDate());

    }

    public EasingType getEaseType() {
        return easeType;
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

    protected static <C extends TimelineChange<? super T>, T extends DateMutableEntity<T>> C getC(DMEReference<T> ref, ChangeID change){
        Timeline<T> timeline = ref.get().getTimeline();
        return (C) timeline.followBreadcrumb(change);
    }

    @Override
    public final void mainSave(JsonObject object) {
        object.addProperty("easeType",easeType.name());
        object.add("owner",owner.serialize());
        object.add("thisChange",thisChange.toJson());

    }

    @Override
    public final void mainLoad(JsonObject object) {
        easeType = EasingType.valueOf(object.get("easeType").getAsString());
        owner = DMEReference.deserialize(object.getAsJsonObject("owner"));
        thisChange = ChangeID.fromJson(object.getAsJsonObject("thisChange"));

    }
}
