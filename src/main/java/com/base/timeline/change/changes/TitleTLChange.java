package com.base.timeline.change.changes;

import com.Global;
import com.base.ObjectType;
import com.base.timeline.error.StateError;
import com.base.reference.DMEReference;
import com.base.timeline.state.TimelineState;
import com.base.timeline.condition.Condition;
import com.base.timeline.condition.ConditionResult;
import com.base.timeline.condition.nullify.NullifyConditions;
import com.google.gson.JsonObject;
import com.objects.character.BookCharacter;
import com.objects.title.Title;
import com.objects.title.succession.rules.SuccessionEntry;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.*;

import static com.base.timeline.change.changes.TimelineChange.ChangeTags.*;

public abstract class TitleTLChange<T extends Title<T>> extends TimelineChange<T> {
    private final DMEReference<T> title;
    private final Optional<DMEReference<BookCharacter>> holder;
    protected TitleTLChange(DMEReference<T> title, Optional<DMEReference<BookCharacter>> character, LocalDate date) {
        super(date);
        this.title = title;
        this.holder = character;
    }
    protected TitleTLChange(DMEReference<T> title, LocalDate date) {
        super(title,date);
    }
    public boolean isSameTitle(TitleTLChange<?> t){
        return t.getTitle().getID().equals(title.getID());
    }

    public DMEReference<T> getTitle() {
        return title;
    }
    @Override
    protected ChangeTags[] getTags() {
        return new ChangeTags[]{TITLE_CHANGE};
    }

    public boolean isSameHolder(TitleTLChange<?> t){
        if (holder.isEmpty() && t.getHolder().isEmpty()) return true;
        if (holder.isEmpty() || t.getHolder().isEmpty()) return false;
        return holder.get().getID().equals(t.getHolder().get().getID());
    }

    @Override
    public HashSet<DMEReference<?>> getScope() {
        Title<?> t = title.get();
        HashSet<DMEReference<?>> scope = new HashSet<>();
        safeAddToSet(scope,ObjectType.TITLE, title.getID());
        for (Title<?> s : t.getChildren()){
            safeAddToSet(scope,ObjectType.TITLE,s.getId());
        }
        Optional<Title<?>> parent = t.getParent();
        if (parent.isPresent()){
            safeAddToSet(scope,ObjectType.TITLE, parent.get().getId());
        }
        if (holder.isPresent()) safeAddToSet(scope,ObjectType.CHARACTER, holder.get().getID());
        for (SuccessionEntry<?> entry : t.getSuccession().getAllEntries()){
            safeAddToSet(scope,ObjectType.CHARACTER, entry.getSubject().getID());
            for (UUID los : entry.getLoS()){
                safeAddToSet(scope,ObjectType.CHARACTER, los);
            }
        };
        return scope;
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
    public static <T extends Title<T>> Pair<DMEReference<T>,Optional<DMEReference<BookCharacter>>> getBase(JsonObject object){
        DMEReference<T> title = DMEReference.deserialize(object.getAsJsonObject("title"));
        Optional<DMEReference<BookCharacter>> holder = Optional.empty();
        if (object.has("holder")){
            holder = Optional.of(DMEReference.deserialize(object.getAsJsonObject("holder")));
        }
        return Pair.of(title,holder);
    }

    @Override
    protected List<Condition<ConditionResult.Nullify, ?>> buildNullifyConditions() {
        return new ArrayList<>(NullifyConditions.BaseTitleConditions());
    }
    public static class Grant<T extends Title<T>> extends TitleTLChange<T> {
        public Grant(DMEReference<T> title, DMEReference<BookCharacter> character, LocalDate date) {
            super(title, Optional.ofNullable(character),date);
        }

        @Override
        protected TimelineState<T> onApply(T entity, boolean saveChangeToDiff) {
            entity.setHolder(getHolder().get().get());
            return entity.getCurrentState();
        }

        @Override
        protected List<Class<? extends TimelineChange<?>>> oppositeChanges() {
            return  List.of((Class<? extends TimelineChange<?>>) Revoke.class);
        }

        @Override
        protected String getText() {
            return getTitle().parse() + " granted to " + getHolder().get().parse() + ".";
        }

        @Override
        protected List<Condition<StateError, ?>> buildApplyConditions() {
            return List.of(IS_OPPOSITE,DUPLICATE,CAN_STILL_HOLD,WAS_REGRANTED,WAS_REVOKED);
        }
        public Grant(DMEReference<T> title, LocalDate date) {
            super(title, Optional.empty(),date);
        }
        public static <T extends Title<T>> Grant<T> fromJson(LocalDate date, DMEReference<T> ref){
            return new Grant<>(ref,date);
        }


    }
    public static class Inherit<T extends Title<T>> extends TitleTLChange<T> {
        private boolean firstTime = true;
        public Inherit(DMEReference<T> title, DMEReference<BookCharacter> character, LocalDate date) {
            super(title, Optional.ofNullable(character),date);
        }

        @Override
        protected TimelineState<T> onApply(T entity, boolean saveChangeToDiff) {
            if (firstTime){
                entity.setInherit(this, Global.CURRENT_DATE(),true);
                firstTime = false;
                return entity.getCurrentState();
            }
            entity.setInherit(this, Global.CURRENT_DATE(),false);
            return entity.getCurrentState();
        }
        @Override
        protected List<Class<? extends TimelineChange<?>>> oppositeChanges() {
            return  List.of();
        }
        @Override
        protected String getText() {
            return getTitle().parse() + " granted to " + getHolder().get().parse() + ".";
        }

        @Override
        protected List<Condition<StateError, ?>> buildApplyConditions() {
            return List.of(INHERITS_TITLE(),DUPLICATE,WAS_REGRANTED,WAS_REVOKED);
        }


        public boolean isFirstTime(){
            return firstTime;
        }
        public void setFirstTime(boolean firstTime) {
            this.firstTime = firstTime;
        }
        public Inherit(DMEReference<T> title, LocalDate date) {
            super(title, Optional.empty(),date);
        }
        public static <T extends Title<T>> Inherit<T> fromJson(LocalDate date, DMEReference<T> ref){
            return new Inherit<>(ref,date);
        }

    }
    public static class Revoke<T extends Title<T>> extends TitleTLChange<T> {
        public Revoke(DMEReference<T> title, DMEReference<BookCharacter> character, LocalDate date) {
            super(title, Optional.ofNullable(character), date);
        }
        @Override
        protected TimelineState<T> onApply(T entity, boolean saveChangeToDiff) {
            if (getHolder().isPresent()){
                entity.removeHolder(getHolder().get().get());
            } else {
                entity.removeCurrentHolder();
            }
            return entity.getCurrentState();
        }
        @Override
        protected List<Class<? extends TimelineChange<?>>> oppositeChanges() {
            return  List.of((Class<? extends TimelineChange<?>>) Grant.class);
        }
        @Override
        protected String getText() {
            return  getTitle().parse() + " revoked from " + getHolder().get().parse() + ".";
        }
        @Override
        protected List<Condition<StateError, ?>> buildApplyConditions() {
            return List.of(IS_OPPOSITE,DUPLICATE);
        }
        public static <T extends Title<T>> Revoke<T> fromJson(LocalDate date, JsonObject json){
            Pair<DMEReference<T>,Optional<DMEReference<BookCharacter>>> pair = getBase(json);
            return new Revoke<>(pair.getLeft(),pair.getRight().orElse(null),date);
        }
    }
    public static class DeJureDrift<T extends Title<T>,NP extends Title<NP>> extends TitleTLChange<T> {
        private final DMEReference<NP> newParent;
        private UUID loreLast = null;
        private boolean loreFlag = false;

        @Override
        protected TimelineState<T> onApply(T entity, boolean saveChangeToDiff) {
            T newChild = this.getTitle().get();
            Title<?> parent = newChild.getParent().orElse(null);
            if (parent != null && !parent.getId().equals(newParent.get().getId())) {
                parent.removeChild(newChild,loreFlag,newParent.get());
                loreFlag = false;
            }
            getTitle().get().addChild(newChild);
            return getTitle().get().getCurrentState();
        }

        @Override
        public void onContinue() {
            loreFlag = true;
        }

        public DeJureDrift(DMEReference<T> child, DMEReference<NP> newParent, LocalDate date) {
            super(child, Optional.empty(),date);
            this.newParent = newParent;
        }


        @Override
        protected String getText() {
            return "DeJure Drift: " + getTitle().parse() + " is now a child of " + newParent.parse() + ".";
        }

        @Override
        protected JsonObject toJson() {
            JsonObject o = super.toJson();
            o.add("newParent", newParent.serialize());
            return o;
        }

        @Override
        protected List<Condition<StateError, ?>> buildApplyConditions() {
            return List.of(HAS_PARENT,DUPLICATE,TITLE_LOOP);
        }

        public static <T extends Title<T>,NP extends Title<NP>> DeJureDrift<T,NP> fromJson(LocalDate date, JsonObject json){
            Pair<DMEReference<T>,Optional<DMEReference<BookCharacter>>> pair = getBase(json);
            DMEReference<NP> newParent = DMEReference.deserialize(json.getAsJsonObject("newParent"));
            return new DeJureDrift<>(pair.getLeft(),newParent,date);
        }
        public DMEReference<NP> getNewParent() {
            return newParent;
        }

        public UUID getLoreLast() {
            return loreLast;
        }
        public boolean isLoreFlag() {
            return loreFlag;
        }
        public void setLoreFlag(boolean loreFlag) {
            this.loreFlag = loreFlag;
        }
        public void setLoreLast(UUID loreLast) {
            this.loreLast = loreLast;
        }
    }
    public static class DeJureDriftPassive<T extends Title<T>, C extends Title<C>,NP extends Title<NP>> extends TitleTLChange<T> {
        //Goes on the old parent. Not the new child like DeJureDrift
        private final DMEReference<C> newChild;
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
        protected JsonObject toJson() {
            JsonObject o = super.toJson();
            o.add("newParent", newParent.serialize());
            o.add("newChild", newChild.serialize());
            return o;
        }
        @Override
        protected List<Condition<StateError, ?>> buildApplyConditions() {
            return List.of(DRIFT_ON_GRANT);
        }
        @Override
        protected TimelineState<T> onApply(T entity, boolean saveChangeToDiff) {
            entity.removeChild(newChild.get(),false,null);
            return entity.getCurrentState();
        }
        @Override
        protected String getText() {
            return "DeJure Drift: " + newChild.parse() + " is now the parent of " + newChild.parse() + ".";
        }
        public C getChild(){
            return newChild.get();
        }

        public static <T extends Title<T>, C extends Title<C>,NP extends Title<NP>> DeJureDriftPassive<T,C,NP> fromJson(LocalDate date, JsonObject json){
            Pair<DMEReference<T>,Optional<DMEReference<BookCharacter>>> pair = getBase(json);
            DMEReference<NP> newParent = DMEReference.deserialize(json.getAsJsonObject("newParent"));
            DMEReference<C> newChild = DMEReference.deserialize(json.getAsJsonObject("newChild"));
            return new DeJureDriftPassive<>(pair.getLeft(),newChild,newParent,date);
        }
    }
}

