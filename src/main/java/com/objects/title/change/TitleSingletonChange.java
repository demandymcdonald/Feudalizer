package com.objects.title.change;

import com.Global;
import com.base.reference.ComplexReference;
import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineSingleChange;
import com.base.timeline.change.TimelineChange;
import com.base.condition.Condition;
import com.base.condition.ConditionResult;
import com.base.timeline.change.condition.apply.ApplyCondition;
import com.base.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.timeline.change.condition.nullify.NullifyCondition;
import com.base.timeline.error.ErrorResolution;
import com.base.timeline.error.StateError;
import com.base.timeline.sandbox.check.SandboxFunctions;
import com.base.timeline.sandbox.core.Objective;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonObject;
import com.objects.character.BookCharacter;
import com.objects.title.Title;
import com.objects.title.succession.rules.SuccessionEntry;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.objects.title.Title.canHold;

public abstract class TitleSingletonChange<T extends Title<T>> extends TimelineSingleChange<T> {


    protected TitleSingletonChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }

    public static class setParent<T extends Title<T>> extends TitleSingletonChange<T> {
        DMEReference<? extends Title<?>> newParent;

        public setParent(DMEReference<? extends T> owner, LocalDate date, DMEReference<? extends Title<?>> newParent) {
            super(owner, date);
            this.newParent = newParent;
        }
        protected setParent(DMEReference<? extends T> owner, LocalDate date, DMEReference<? extends Title<?>> newParent, boolean e) {
            this(owner,date,newParent);
        }
        protected setParent(DMEReference<? extends T> owner, LocalDate date) {
            this(owner,date,null);
        }
        @Override
        protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
            entity.get().internalParent(newParent);
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
        protected void applyConditions(List<ApplyCondition<? super T>> list) {
            list.add(new HasVariable("newParent",(c) -> {
                if(c instanceof setParent<?> sp){
                    return sp.getNewParent().parse();
                }
                return null;
            }));
            list.add(new ParentLoop());
        }

        @Override
        protected void nullifyConditions(List<NullifyCondition<? super T>> list) {

        }

        @Override
        protected void deactivateConditions(List<DeactivateCondition<? super T>> list) {

        }

        @Override
        public void additionalSave(JsonObject data) {
            data.add("newParent", newParent.serialize());
        }

        @Override
        public void additionalLoad(JsonObject data) {
            newParent = DMEReference.deserialize(data.get("new_parent").getAsJsonObject());
        }
    }
    protected static class setHolder<T extends Title<T>> extends TitleSingletonChange<T> {
        DMEReference<? extends BookCharacter> newHolder;
        DMEReference<? extends BookCharacter> oldHolder;
        protected setHolder(DMEReference<? extends T> owner, LocalDate date, DMEReference<? extends BookCharacter> newHolder) {
            super(owner, date);
            this.newHolder = newHolder;
            this.oldHolder = owner.get().getHolder().orElse(null);
        }
        protected setHolder(DMEReference<? extends T> owner, LocalDate date) {
            super(owner, date);
        }
        @Override
        protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
            entity.get().internalHolder(newHolder);
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
            if(oldHolder != null){
                return "Changed title holder from " + oldHolder.get().getFullName() + " to " + newHolder.get().getFullName();
            }
            return "Changed title holder to " + newHolder.get().getFullName();
        }
        public DMEReference<? extends BookCharacter> getNewHolder() {
            return newHolder;
        }
        @Override
        protected void applyConditions(List<ApplyCondition<? super T>> list) {
            list.add(new HasVariable("grant_holder",(c) -> {
                if(c instanceof setHolderGrant<?> sp){
                    return sp.getNewHolder().parse();
                }
                return null;
            }));
            list.add(new HasVariable("inherit_holder",(c) -> {
                if(c instanceof setHolderInherit<?> sp){
                    return sp.getNewHolder().parse();
                }
                return null;
            }));
            list.add(new HolderDead());
            list.add(new CanHold());
        }

        @Override
        protected void nullifyConditions(List<NullifyCondition<? super T>> list) {

        }

        @Override
        protected void deactivateConditions(List<DeactivateCondition<? super T>> list) {

        }

        @Override
        public void additionalSave(JsonObject data) {
            data.add("new_holder", newHolder.serialize());
            data.add("old_holder", oldHolder.serialize());
        }

        @Override
        public void additionalLoad(JsonObject data) {
            newHolder = DMEReference.deserialize(data.get("new_holder").getAsJsonObject());
            oldHolder = DMEReference.deserialize(data.get("old_holder").getAsJsonObject());
        }
    }
    public static class setHolderInherit<T extends Title<T>> extends setHolder<T> {
        public setHolderInherit(DMEReference<? extends T> owner, LocalDate date, DMEReference<? extends BookCharacter> newHolder) {
            super(owner, date,newHolder);
        }
        public setHolderInherit(DMEReference<? extends T> owner, LocalDate date) {
            super(owner, date);
        }
        @Override
        protected void applyConditions(List<ApplyCondition<? super T>> list) {
            super.applyConditions(list);
        }
    }
    public static class setHolderGrant<T extends Title<T>> extends setHolder<T> {
        public setHolderGrant(DMEReference<T> owner, LocalDate date, DMEReference<? extends BookCharacter> newHolder) {
            super(owner, date, newHolder);
        }
        public setHolderGrant(DMEReference<T> owner, LocalDate date) {
            super(owner, date);
        }
        @Override
        protected void applyConditions(List<ApplyCondition<? super T>> list) {
            super.applyConditions(list);
        }
    }
    public static class setSuccessionEntry<T extends Title<T>> extends TimelineSingleChange<T> {
        SuccessionEntry<?> entry;
        public setSuccessionEntry(DMEReference<T> owner, LocalDate date, SuccessionEntry<?> newSuccessor) {
            super(owner, date);
            this.entry = newSuccessor;
        }
        public setSuccessionEntry(DMEReference<T> owner, LocalDate date) {
            super(owner, date);
        }

        @Override
        protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
            entity.get().internalSuccession(entry);
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
        protected void applyConditions(List<ApplyCondition<? super T>> list) {
            list.add(new HasVariable("succession_entry",(c) -> {
                if(c instanceof setSuccessionEntry<?> sp){
                    return sp.getEntry().toString();
                }
                return null;
            }));
        }

        @Override
        protected void nullifyConditions(List<NullifyCondition<? super T>> list) {

        }

        @Override
        protected void deactivateConditions(List<DeactivateCondition<? super T>> list) {

        }

        @Override
        public void additionalSave(JsonObject data) {
            data.add("succession",entry.serialize());
        }

        @Override
        public void additionalLoad(JsonObject data) {
            entry = SuccessionEntry.fromJson(data.get("succession").getAsJsonObject());
        }
        public SuccessionEntry<?> getEntry() {
            return entry;
        }
    }








    protected class ParentLoop extends ApplyCondition<T> {
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
                        Objective<?> parentChange = Objective.buildInChange(parentTC, Global.TimeDirection.FORWARD,new setParent<>((DMEReference<? extends T>) parentTC, Global.getDate(),null,false),new SandboxFunctions.canAddChange<>());
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
    protected class HolderDead extends ApplyCondition<T>{
        public HolderDead() {
            super("title_holder_dead");
        }
        @Override
        protected Optional<StateError> doCheck(DMEReference<? extends T> entity, TimelineChange<? extends T> thisChange, TimelineChange<?> checkAgainst) {
            if (thisChange instanceof setHolder<?> ch && !(ch.getNewHolder().get().isAlive())){
                setHolder<T> holderChange = (setHolder<T>) thisChange;
                DMEReference<? extends T> o = holderChange.getOwner();
                return Optional.of(new StateError("title_holder_dead", ComplexReference.of("{} is now dead.", entity),checkAgainst)
                        .addEndSave().addEndCancel().addOption(new ErrorResolution.SuccessionPlanning_Title(holderChange.getNewHolder())));
            }
            return Optional.empty();
        }
    }
    protected class CanHold extends ApplyCondition<T>{
        public CanHold() {
            super("title_can_still_hold");
        }
        @Override
        protected Optional<StateError> doCheck(DMEReference<? extends T> entity, TimelineChange<? extends T> thisChange, TimelineChange<?> checkAgainst) {
            if (thisChange instanceof setHolderGrant<?> ch){
                DMEReference<T> title = (DMEReference<T>) ch.getOwner();
                setHolderGrant<T> holderChange = (setHolderGrant<T>) thisChange;
                return canHold(title, holderChange.getNewHolder(),thisChange.getStart(),false);
            }
            return Optional.empty();
        }

        @Override
        public boolean singleRun() {
            return true;
        }
    }

}
