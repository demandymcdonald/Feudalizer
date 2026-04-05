package com.base.timeline.sandbox.core;

import com.Global;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineChange;
import com.base.condition.Condition;
import com.base.condition.ConditionResult;
import com.base.timeline.error.StateError;
import com.base.timeline.sandbox.check.SandboxFunction;
import com.base.timeline.sandbox.check.SandboxFunctions;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonObject;
import com.objects.character.BookCharacter;

import java.time.LocalDate;
import java.util.List;

public record  Objective<T extends DateMutableEntity<T>> (DMEReference<T> subject, Global.TimeDirection start, TimelineChange<? super T> change, SandboxFunction<T>... toCheck) {
    @SafeVarargs
    public static <T extends DateMutableEntity<T>> Objective<?> build(DMEReference<? extends T> subject, Global.TimeDirection direction, TimelineChange<? super T> change, SandboxFunction<T>... toCheck){
       return new Objective<>((DMEReference<T>) subject, direction,change,toCheck);
    }
    @SafeVarargs
    public static Objective<?> buildInChange(DMEReference<?> subject, Global.TimeDirection direction, TimelineChange<?> change, SandboxFunction<?>... toCheck){
        return new Objective<>(subject, direction,change,toCheck);
    }

    public static Objective<BookCharacter> buildSuccession(DMEReference<? extends BookCharacter> subject){
        return new Objective<>(subject, Global.TimeDirection.FORWARD,buildEmpty(subject,subject.get().getEnded()),new SandboxFunctions.SuccessionPlanning());
    }

    private static <T extends DateMutableEntity<T>> TimelineChange<? super T> buildEmpty(DMEReference<? extends T> subject, LocalDate start){
        return new TimelineChange<T>(subject,start) {
            @Override
            protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {

            }

            @Override
            public List<Class<TimelineChange<? super T>>> oppositeChanges() {
                return List.of();
            }

            @Override
            public boolean isPositive() {
                return false;
            }

            @Override
            protected String getText() {
                return "";
            }

            @Override
            protected void applyConditions(List<Condition<StateError, ? super T>> list) {

            }

            @Override
            protected void nullifyConditions(List<Condition<ConditionResult.Nullify, ? super T>> list) {

            }

            @Override
            protected void deactivateConditions(List<Condition<StateError, ? super T>> list) {

            }

            @Override
            public void additionalSave(JsonObject data) {

            }

            @Override
            public void additionalLoad(JsonObject data) {

            }
        };
    }


    public LocalDate getStart(){
        return change.getStart();
    }
    public LocalDate getEnd(){
        return change.getEnd();
    }

}
