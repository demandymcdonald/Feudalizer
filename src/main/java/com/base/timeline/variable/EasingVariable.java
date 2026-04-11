package com.base.timeline.variable;

import com.Global;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.Timeline;
import com.base.timeline.change.ChangeID;
import com.base.timeline.change.TimelineChange;
import com.base.utilities.TimelineSynced;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.utilities.serialization.SuperclassSerializable;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface EasingVariable<E extends EasingVariable<E,C,T>,C extends TimelineChange<? super T> & EasingChange<?,C,T>, T extends DateMutableEntity<?>> extends TimelineSynced, SuperclassSerializable<E> {
    String getChangeClassName();
    DMEReference<? extends T> getOwner();
    ImmutableList<VariableContainer> getEasingFunctions();
    Predicate<C> getMatching();
    default Timeline<? extends T> getTimeline(){
        return (Timeline<? extends T>) getOwner().get().getTimeline();
    }
    default C getCurrentChange(){
        return getTimeline().findChangeByClassID(Global.getDate().plusDays(1), Global.TimeDirection.BACKWARD, getChangeClassName(),false);
    }
    default C getNextChange() {
        return getTimeline().findChangeByClassID(Global.getDate(), Global.TimeDirection.BACKWARD, getChangeClassName(), false);
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
    private void calculateVariables(){
        final ImmutableList<VariableContainer> ourEase = this.getEasingFunctions();
        final C future = getNextChange();
        if (future == null){
            for (VariableContainer v : ourEase){
                v.consumer().accept(v.base().get());
            }
            return;
        }
        final ImmutableList<VariableContainer> otherEase = future.getEasingVariable(this.getMatching()).getEasingFunctions();
        final LocalDate startLerp = getCurrentChange().getStart();
        final LocalDate endLerp = future.getEnd();
        for(int i = 0; i < ourEase.size(); i++){
            VariableContainer our = ourEase.get(i);
            VariableContainer other = otherEase.get(i);
            our.consumer().accept(our.type().easingFunction.calculate(our.base().get(), other.base().get(), startLerp, endLerp));
        }
    }

    @Override
    default void onLoad(LocalDate date){};
    @Override
    default void afterLoad(LocalDate date){
        calculateVariables();
    };
    record VariableContainer(EasingType type,Supplier<Double> base, Consumer<Double> consumer){

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
