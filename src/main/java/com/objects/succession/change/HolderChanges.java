package com.objects.succession.change;

import com.base.datemutable.DateMutableEntity;
import com.base.datemutable.timeline.change.condition.apply.ApplyCondition;
import com.base.datemutable.timeline.change.varswap.TimelineVarChange;
import com.base.reference.DMEReference;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.objects.character.sentient.SentientCharacter;
import com.objects.succession.held.ICharacterHeld;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public abstract class HolderChanges<T extends DateMutableEntity<T> & ICharacterHeld<T>> extends TimelineVarChange<T, DMEReference<? extends SentientCharacter<?>>> {
    protected HolderChanges(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }

    protected HolderChanges(DMEReference<? extends T> owner, LocalDate date, DMEReference<? extends SentientCharacter<?>> changed) {
        super(owner, date, changed);
    }

    @Override
    protected String getText() {
        DMEReference<? extends SentientCharacter<?>> newHolder = this.getNew();
        Optional<DMEReference<? extends SentientCharacter<?>>> oldHolder = this.getOld();
        return oldHolder.map(dmeReference -> "Changed title holder from " + dmeReference.get().getFullName() + " to " + newHolder.get().getFullName()).orElseGet(() -> "Changed title holder to " + newHolder.get().getFullName());
    }

    @Override
    public final void setNew(T entity, DMEReference<? extends SentientCharacter<?>> newValue) {
        entity.internalSetHolder(newValue);
    }
    @Override
    public DMEReference<? extends SentientCharacter<?>> getCurrent(T owner) {
        return owner.getHolder().orElse(null);
    }

    @Override
    protected JsonElement serializeO(DMEReference<? extends SentientCharacter<?>> o) {
        return o.serialize();
    }

    @Override
    protected DMEReference<? extends SentientCharacter<?>> deserializeO(JsonElement json) {
        return DMEReference.deserialize(json);
    }
    @Override
    public void additionalSave(JsonObject data) {
        super.additionalSave(data);
    }

    @Override
    public void additionalLoad(JsonObject data) {
        super.additionalLoad(data);
    }


    public static class Change<T extends DateMutableEntity<T> & ICharacterHeld<T>> extends HolderChanges<T>{
        public Change(DMEReference<? extends T> owner, LocalDate date) {
            super(owner, date);
        }
        public Change(DMEReference<? extends T> owner, LocalDate date, DMEReference<? extends SentientCharacter<?>> changed) {
            super(owner, date, changed);
        }
        @Override
        protected void applyConditions(Set<ApplyCondition<? super T>> list) {
            super.applyConditions(list);
            list.add(new HolderConditions.CanHold<>());
            list.add(new HolderConditions.HolderDead<>());
        }
    }
    public static class Inherit<T extends DateMutableEntity<T> & ICharacterHeld<T>> extends HolderChanges<T>{
        public Inherit(DMEReference<? extends T> owner, LocalDate date) {
            super(owner, date);
        }
        public Inherit(DMEReference<? extends T> owner, LocalDate date, DMEReference<? extends SentientCharacter<?>> changed) {
            super(owner, date, changed);
        }
        @Override
        protected void applyConditions(Set<ApplyCondition<? super T>> list) {
            super.applyConditions(list);
            list.add(new HolderConditions.CanHold<>());
            list.add(new HolderConditions.HolderDead<>());
        }
    }
    public static class NewParent<T extends DateMutableEntity<T> & ICharacterHeld<T>> extends TimelineVarChange<T,DMEReference<? extends ICharacterHeld<?>>>{
        protected NewParent(DMEReference<? extends T> owner, LocalDate date) {
            super(owner, date);
        }
        protected NewParent(DMEReference<? extends T> owner, LocalDate date, DMEReference<? extends ICharacterHeld<?>> changed) {
            super(owner, date, changed);
        }

        @Override
        public DMEReference<? extends ICharacterHeld<?>> getCurrent(T owner) {
            return owner.getParent().orElse(null);
        }

        @Override
        protected void applyConditions(Set<ApplyCondition<? super T>> list) {
            super.applyConditions(list);
            list.add(new HolderConditions.ParentLoop<>());
        }

        @Override
        public void setNew(T entity, DMEReference<? extends ICharacterHeld<?>> newValue) {
            entity.internalSetParent(newValue);
        }

        @Override
        protected JsonElement serializeO(DMEReference<? extends ICharacterHeld<?>> o) {
            return o.serialize();
        }

        @Override
        protected DMEReference<? extends ICharacterHeld<?>> deserializeO(JsonElement json) {
            return DMEReference.deserialize(json);
        }
    }

}
