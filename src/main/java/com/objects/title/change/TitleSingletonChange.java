package com.objects.title.change;

import com.Global;
import com.base.reference.ComplexReference;
import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineSingleChange;
import com.base.timeline.change.changes.TimelineChange;
import com.base.timeline.change.conditions.Condition;
import com.base.timeline.change.conditions.ConditionResult;
import com.base.timeline.error.StateError;
import com.base.timeline.sandbox.check.SandboxChecks;
import com.base.timeline.sandbox.core.Objective;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonObject;
import com.objects.character.BookCharacter;
import com.objects.title.Title;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public abstract class TitleSingletonChange<T extends Title<T>> extends TimelineSingleChange<T> {


    protected TitleSingletonChange(DMEReference<T> owner, LocalDate date) {
        super(owner, date);
    }

    public static class ChangeHolder<T extends Title<T>> extends TitleSingletonChange<T> {
        private DMEReference<BookCharacter> holder;
        private DMEReference<BookCharacter> old_holder;
        public ChangeHolder(DMEReference<T> owner, LocalDate date, DMEReference<BookCharacter> holder, DMEReference<BookCharacter> old_holder) {
            super(owner, date);
            this.holder = holder;
            this.old_holder = old_holder;
        }
        public ChangeHolder(DMEReference<T> owner, LocalDate date) {
            super(owner, date);
        }
        @Override
        protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
            entity.get().internalHolder(holder);
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
            if(old_holder != null){
                return "Changed title holder from " + old_holder.get().getFullName() + " to " + holder.get().getFullName();
            }
            return "Changed title holder to " + holder.get().getFullName();
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
            data.add("holder", holder.serialize());
            data.add("old_holder", old_holder.serialize());
        }

        @Override
        public void additionalLoad(JsonObject data) {
            holder = DMEReference.deserialize(data.get("holder").getAsJsonObject());
            old_holder = DMEReference.deserialize(data.get("old_holder").getAsJsonObject());
        }
    }

    public static class setParent<T extends Title<T>> extends TitleSingletonChange<T> {
        DMEReference<? extends Title<?>> newParent;
        protected setParent(DMEReference<T> owner, LocalDate date, DMEReference<? extends Title<?>> newParent) {
            super(owner, date);
            this.newParent = newParent;
        }
        protected setParent(DMEReference<? extends Title<?>> owner, LocalDate date, DMEReference<? extends Title<?>> newParent, boolean e) {
            this((DMEReference<T>) owner,date,newParent);
        }
        @Override
        protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {

        }

        @Override
        public List<Class<TimelineChange<? super T>>> oppositeChanges() {
            return List.of();
        }
        public DMEReference<? extends Title<?>> getNewParent() {
            return newParent;
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
        protected void applyConditions(List<Condition<StateError, ? super T>> list) {
            list.add(new HasParent());
            list.add(new ParentLoop());
        }

        @Override
        protected void nullifyConditions(List<Condition<ConditionResult.Nullify, ? super T>> list) {

        }

        @Override
        protected void deactivateConditions(List<Condition<StateError, ? super T>> list) {

        }

        @Override
        public void additionalSave(JsonObject data) {
            data.add("newParent", newParent.serialize());
        }

        @Override
        public void additionalLoad(JsonObject data) {
            newParent = DMEReference.deserialize(data.get("newParent").getAsJsonObject());
        }
    }
    public static class setHolder<T extends Title<T>> extends TitleSingletonChange<T> {
        DMEReference<? extends BookCharacter> newHolder;
        protected setHolder(DMEReference<T> owner, LocalDate date, DMEReference<? extends BookCharacter> newHolder) {
            super(owner, date);
            this.newHolder = newHolder;
        }

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
    }


    public class ParentLoop extends Condition<StateError,T> {
        public ParentLoop() {
            super("title_parent_loop");
        }
        @Override
        protected Optional<StateError> doCheck(DMEReference<? extends T> entity, TimelineChange<? extends T> thisChange, TimelineChange<?> checkAgainst) {
            if ((checkAgainst instanceof setParent<?> ca && ca.getNewParent() != null) && (thisChange instanceof setParent<? extends T> tc && tc.getNewParent() != null)){
                Title<?> next = ca.getOwner().get();
                Title<?> ownerTC = tc.getOwner().get();
                final List<DMEReference<? extends Title<?>>> offspringTC = ownerTC.getAllOffspring();
                while (next != null){
                    Optional<DMEReference<? extends Title<?>>> parent = next.getParent();
                    if (parent.isEmpty()){
                        break;
                    } else if (offspringTC.contains(parent.get())){
                        DMEReference<? extends Title<?>> parentTC = parent.get();
                        Objective<?> parentChange = Objective.buildInChange(parentTC, Global.TimeDirection.FORWARD,new setParent<>( parentTC, Global.getDate(),null,false),new SandboxChecks.canAddChange<>());
                        //The third solution is to go up to the looper and remove their parent OR replace it (last safe state?). Either requires sandboxing.
                        return Optional.of(new StateError("title_looping", ComplexReference.of("{} is offspring of {}", ca.getOwner(),tc.getNewParent()),tc)
                                .addEndSave().addEndCancel().addSandbox("title_looping_fix",
                    "Remove Existing Parent","Remove the parent of " + parentTC.parse(),parentChange));
                    }
                    next = parent.get().get();
                }
            }
            return Optional.empty();
        }
    }
}
