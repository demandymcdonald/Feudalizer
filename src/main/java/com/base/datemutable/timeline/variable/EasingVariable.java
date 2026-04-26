package com.base.datemutable.timeline.variable;

import com.Global;
import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.Timeline;
import com.base.datemutable.timeline.change.TimelineChange;
import com.base.datemutable.utilities.TimelineSynced;
import com.utilities.ThreadManager;
import com.utilities.id.Identifiable;
import com.utilities.serialization.SuperclassSerializable;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface EasingVariable<E extends EasingVariable<E,C,T>,C extends TimelineChange<? super T> & EasingChange<?,C,T>,
        T extends DateMutableEntity<T>> extends TimelineSynced, SuperclassSerializable<E> {
    String getChangeClassName();
    DMEReference<? extends T> getOwner();
    Map<Identifiable<?>,VariableContainer> getEasingFunctions();
    Predicate<E> getMatching();
    default Timeline<? extends T> getTimeline(){
        return (Timeline<? extends T>) getOwner().get().getTimeline();
    }
    default C getCurrentChange(){
        return getTimeline().findChangeByClassID(Global.getDate().plusDays(1), Global.TimeDirection.BACKWARD, getChangeClassName(),false);
    }
    default C getNextChange() {
        return getCurrentChange().getNext();
    }
    enum EasingType {
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
    default void calculateVariables(){
        final Map<Identifiable<?>,VariableContainer> ourEase = this.getEasingFunctions();
        if (!ThreadManager.isMainThread()){
            for(Identifiable<?> i : ourEase.keySet()){
                VariableContainer our = ourEase.get(i);
                our.consumer().accept(our.base().get());
            }
            return;
        }
        final C future = getNextChange();
        if (future == null){
            for (VariableContainer v : ourEase.values()){
                v.consumer().accept(v.base().get());
            }
            return;
        }
        final Map<Identifiable<?>,VariableContainer> otherEase = future.getEasingVariableCast(getMatching()).getEasingFunctions();
        final LocalDate startLerp = getCurrentChange().getStart();
        final LocalDate endLerp = future.getEnd();
        for(Identifiable<?> i : ourEase.keySet()){
            VariableContainer our = ourEase.get(i);
            VariableContainer other = otherEase.get(i);
            our.consumer().accept(our.type().easingFunction.calculate(our.base().get(), other.base().get(), startLerp, endLerp));
        }
    }

    @Override
    default void onLoad(LocalDate date){};
    @Override
    default void onLink(LocalDate date){
        calculateVariables();
    };
    record VariableContainer(EasingType type, Supplier<Double> base, Consumer<Double> consumer){

    }
    abstract class EasingFunction{
        public final double calculate(double start, double end, LocalDate startDate, LocalDate endDate){
            double total;
            double elapsed;
            if (startDate.isAfter(endDate)){
                total= ChronoUnit.DAYS.between(endDate, startDate);
                elapsed= ChronoUnit.DAYS.between(endDate, Global.getDate());
            } else {
                total = ChronoUnit.DAYS.between(startDate, endDate);
                elapsed = ChronoUnit.DAYS.between(startDate, Global.getDate());
            }
            return apply(start,end,elapsed/total);
        }
        protected abstract double apply(double a, double b, double t);
    }
}
