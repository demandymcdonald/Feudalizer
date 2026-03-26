package com.base.timeline.change;

import com.base.ObjectType;
import com.base.flags.Errors;
import com.base.flags.StateError;
import com.base.reference.DMEReference;
import com.base.timeline.TimelineState;
import com.google.common.collect.HashMultimap;
import com.google.gson.JsonObject;
import com.simulation.people.BookCharacter;
import com.simulation.title.Title;
import com.simulation.title.succession.rules.SuccessionEntry;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.base.flags.Errors.*;
import static com.base.timeline.change.TimelineChange.ChangeTags.*;

public abstract class TitleTLChanges<T extends Title<T>> extends TimelineChange<T> {
    private final DMEReference<T> title;
    private final Optional<DMEReference<BookCharacter>> holder;
    protected TitleTLChanges(DMEReference<T> title,Optional<DMEReference<BookCharacter>> character, LocalDate date) {
        super(date);
        this.title = title;
        this.holder = character;
    }
    public boolean isSameTitle(TitleTLChanges<?> t){
        return t.getTitle().getUuid().equals(title.getUuid());
    }

    public DMEReference<T> getTitle() {
        return title;
    }
    @Override
    protected ChangeTags[] getTags() {
        return new ChangeTags[]{TITLE_CHANGE};
    }
    @Override
    protected boolean shouldCheck(TimelineChange<?> t) {
        return t instanceof TitleTLChanges<?> st && isSameTitle(st);
    }
    public boolean isSameHolder(TitleTLChanges<?> t){
        if (holder.isEmpty() && t.getHolder().isEmpty()) return true;
        if (holder.isEmpty() || t.getHolder().isEmpty()) return false;
        return holder.get().getUuid().equals(t.getHolder().get().getUuid());
    }
    @Override
    protected boolean shouldNullify(TimelineChange<?> change) {
        return change instanceof TitleTLChanges<?> st && isSameHolder(st);
    }
    @Override
    public HashMultimap<ObjectType, UUID> getScope() {
        Title<?> t = title.link();
        HashMultimap<ObjectType, UUID> scope = HashMultimap.create();
        safeAdd(scope,ObjectType.TITLE, title.getUuid());
        for (Title<?> s : t.getChildren()){
            safeAdd(scope,ObjectType.TITLE,s.getId());
        }
        Optional<Title<?>> parent = t.getParent();
        if (parent.isPresent()){
            safeAdd(scope,ObjectType.TITLE, parent.get().getId());
        }
        if (holder.isPresent()) safeAdd(scope,ObjectType.CHARACTER, holder.get().getUuid());
        for (SuccessionEntry<?> entry : t.getSuccession().getAllEntries()){
            safeAdd(scope,ObjectType.CHARACTER, entry.getSubject().getUuid());
            for (UUID los : entry.getLoS()){
                safeAdd(scope,ObjectType.CHARACTER, los);
            }
        };
        return scope;
    }
    private void safeAdd(HashMultimap<ObjectType,UUID> map, ObjectType type, UUID uuid){
        if (map.containsKey(type) && map.get(type).contains(uuid)) return;
        map.put(type,uuid);
    }
    public Optional<DMEReference<BookCharacter>> getHolder() {
        return holder;
    }

    @Override
    protected JsonObject toJson() {
        JsonObject o = new JsonObject();
        o.add("title", title.serialize());
        if (holder.isPresent()) o.add("holder", holder.get().serialize());
        return o;
    }
    public static class GrantTitle<T extends Title<T>> extends TitleTLChanges<T>{
        public GrantTitle(DMEReference<T> title, DMEReference<BookCharacter> character, LocalDate date) {
            super(title, Optional.of(character),date);
        }
        @Override
        protected TimelineState<T> onApply(T entity) {
            entity.setHolder(getHolder().get().link());
            return entity.getCurrentState();
        }

        @Override
        protected List<Class<? extends TimelineChange<?>>> validOpposites() {
            return  List.of((Class<? extends TimelineChange<?>>) RevokeTitle.class);
        }

        @Override
        public Optional<StateError> checkConflict(TimelineChange<?> state) {
            //should only be called by the new state on the old
            TitleTLChanges<?> te = (TitleTLChanges<?>) state;
            if (te instanceof RevokeTitle<?>){
                return Optional.of(newStateNullifiedbyOldError());
            }
            Optional<DMEReference<BookCharacter>> holder = te.getHolder();
            Optional<DMEReference<BookCharacter>> myHolder = getHolder();
            if (myHolder.isEmpty()) return Optional.of(duplicateError());
            Title<?> myTitle = getTitle().link();
            if (holder.isPresent()){
                BookCharacter character = holder.get().link();
                BookCharacter myHolderChar = myHolder.get().link();
                if (state instanceof GrantTitle<?> && character.equals(myHolderChar)){
                    return Optional.of(duplicateError());
                }
            }
            return Optional.of(newInvalidatesOldError());
        }


        @Override
        protected String getText() {
            return getTitle().parse() + " granted to " + getHolder().get().parse() + ".";
        }




    }
    public static class RevokeTitle<T extends Title<T>> extends TitleTLChanges<T>{
        public RevokeTitle(DMEReference<T> title, Optional<DMEReference<BookCharacter>> character, LocalDate date) {
            super(title, character, date);
        }

        @Override
        protected TimelineState<T> onApply(T entity) {
            if (getHolder().isPresent()){
                entity.removeHolder(getHolder().get().link());
            } else {
                entity.removeCurrentHolder();
            }
            return entity.getCurrentState();
        }



        @Override
        protected List<Class<? extends TimelineChange<?>>> validOpposites() {
            return  List.of((Class<? extends TimelineChange<?>>) GrantTitle.class);
        }



        @Override
        public Optional<StateError> checkConflict(TimelineChange<?> state) {
            //should only be called by the new state on the old
            TitleTLChanges<?> te = (TitleTLChanges<?>) state;
            if (te instanceof GrantTitle<?>){
                return Optional.of(newStateNullifiedbyOldError());
            }
            Optional<DMEReference<BookCharacter>> holder = te.getHolder();
            Optional<DMEReference<BookCharacter>> myHolder = getHolder();
            if (myHolder.isEmpty()) return Optional.of(duplicateError());
            Title<?> myTitle = getTitle().link();
            BookCharacter character = holder.get().link();
            BookCharacter myHolderChar = myHolder.get().link();
            if (state instanceof RevokeTitle<?> && character.equals(myHolderChar)){
                return Optional.of(duplicateError());
            }
            return Optional.of(newInvalidatesOldError());
        }

        @Override
        protected String getText() {
            return  getTitle().parse() + " revoked from " + getHolder().get().parse() + ".";
        }

    }
    public static class DeJureDrift<T extends Title<T>,NP extends Title<NP>> extends TitleTLChanges<T>{
        private final DMEReference<NP> newParent;
        private UUID loreLast = null;
        private boolean loreFlag = false;

        @Override
        public void onContinue() {
            loreFlag = true;
        }

        public DeJureDrift(DMEReference<T> child, DMEReference<NP> newParent, LocalDate date) {
            super(child, Optional.empty(),date);
            this.newParent = newParent;
        }

        @Override
        protected  TimelineState<T> onApply(T entity) {
            T newChild = this.getTitle().link();
            Title<?> parent = newChild.getParent().orElse(null);
            if (parent != null && !parent.getId().equals(newParent.link().getId())) {
                parent.removeChild(newChild,loreFlag,newParent.link());
                loreFlag = false;
            }
            getTitle().link().addChild(newChild);
            return getTitle().link().getCurrentState();
        }

        @Override
        public Optional<StateError> checkConflict(TimelineChange<?> state) {
            Title<?> newChild = this.getTitle().link();
            Title<?> parent = newChild.getParent().orElse(null);
            if (parent != null && (loreLast == null || !parent.getId().equals(loreLast))){
                loreLast = parent.getId();
                loreFlag = false;
                return Optional.of(Errors.alreadyHasAParent(newChild,parent,newParent.link()));
            }
            return Optional.empty();
        }

        @Override
        protected String getText() {
            return "";
        }
    }
    public static class DeJureDriftPassive<T extends Title<T>, C extends Title<C>,NP extends Title<NP>> extends TitleTLChanges<T>{
        private final DMEReference<? extends Title<?>> newChild;
        private final DMEReference<NP> newParent;
        public DeJureDriftPassive(DMEReference<T> title, DMEReference<C> newChild, DMEReference<NP> newParent, LocalDate date) {
            super(title, null, date);
            this.newChild = newChild;
            this.newParent = newParent;
        }
        public DeJureDriftPassive(DMEReference<T> title, DMEReference<C> newChild, LocalDate date) {
            super(title, null, date);
            this.newChild = newChild;
            this.newParent = (DMEReference<NP>) title;
        }
        @Override
        protected TimelineState<T> onApply(T entity) {
            entity.removeChild(newChild.link(),false,null);
            return entity.getCurrentState();
        }

        @Override
        public Optional<StateError> checkConflict(TimelineChange<?> state) {
            //There may be a situation where this should check if there's some complex inheritance going on, but that seems excessive..
            return Optional.empty();
        }

        @Override
        protected String getText() {
            return "";
        }
    }
}

