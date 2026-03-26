package com.simulation.title;

import com.GlobalVars;
import com.base.*;
import com.base.reference.DMEReference;
import com.base.reference.StateReference;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.TitleTLChanges;
import com.google.gson.JsonObject;
import com.simulation.people.BookCharacter;
import com.simulation.people.CharacterManager;
import com.simulation.title.succession.SuccessionContainer;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.*;

public abstract class Title<T extends Title<T>> extends DateMutableEntity<T,TitleContainer> {
    private Optional<BookCharacter> Holder = Optional.empty();
    private Optional<Title<?>> Parent = Optional.empty();
    private final Set<Title<?>> Children = new HashSet<>();
    //private final Set<UUID> everyHolder = new HashSet<>();
    private SuccessionContainer Succession;
    private JsonObject passthrough;
    public Title(UUID id, LocalDate created, LocalDate ended) {
        super(id, created, ended);

    }

    public Title(UUID id, LocalDate created, LocalDate ended, JsonObject additionalData) {
        super(id, created, ended, additionalData);
        handleHolders(additionalData.get("Holders").getAsJsonArray());
    }

    public Title(JsonObject payload) {
        super(payload, );
    }

    public abstract List<BookCharacter> getAllClaimants();


    @Override
    public void relink(TitleContainer state) {
        final CharacterManager CM = DMRegistry.getCharacterManager();
        final TitleManager TM = DMRegistry.getTitleManager();
        if (state.holder().isPresent()) {
            Holder = Optional.of(CM.get(state.holder().get()));
        } else {
            Holder = Optional.empty();
        }
        if (state.parent().isPresent()) {
            Parent = Optional.of(TM.get(state.parent().get()));
        }
        for (UUID child : state.children()) {
            Children.add(TM.get(child));
        }
        onNewStateLoad(passthrough);
        onRelink();
    }

    public abstract boolean canInherit(BookCharacter person);
    public abstract boolean isInheritable();
    public abstract boolean isSubPropagating();
    public abstract boolean canHold(BookCharacter person);
    protected abstract JsonObject updateState(JsonObject j);
    protected abstract void onRelink();
    protected abstract void onNewStateLoad(JsonObject passthrough);
    protected JsonObject getPayload() {
        return passthrough;
    }


    public Optional<Title<?>> getParent() {
        return Parent;
    }



    public Optional<BookCharacter> getHolder() {
        return Holder;
    }

    public void setHolder(BookCharacter holder) {
        if (!canInherit(holder)){
            //TODO throw flag
            return;
        }
        Holder = Optional.of(holder);
        addStateChange(GlobalVars.CURRENT_DATE(), new TitleTLChanges.GrantTitle<>(DMEReference.of(this), DMEReference.of(holder), GlobalVars.CURRENT_DATE()));
        holder.addTitle(this);
    }
    public void removeHolder(BookCharacter holder){
        Holder = Optional.empty();
        addStateChange(GlobalVars.CURRENT_DATE(), new TitleTLChanges.RevokeTitle<>(DMEReference.of(this), Optional.of(DMEReference.of(holder)), GlobalVars.CURRENT_DATE()));
        holder.revokeTitle(this);
    }
    public void removeCurrentHolder(){
        if (Holder.isPresent()){
            removeHolder(Holder.get());
        }
    }
    public Set<Title<?>> getChildren() {
        return Children;
    }

    public void setChildren(Set<Title<?>> children) {
        Children.clear();
        Children.addAll(children);
    }
    public boolean hasChild(Title<?> child) {
        return Children.contains(child);
    }
    public void addChild(Title<?> child) {
        Children.add(child);
        child.setParent(this);
        final TitleTLChanges.DeJureDriftPassive<?,?,?> changes = new TitleTLChanges.DeJureDriftPassive<>(DMEReference.of(this),DMEReference.of(child),GlobalVars.CURRENT_DATE());
        addStateChange(GlobalVars.CURRENT_DATE(), (TimelineChange<T>) changes);
    }
    public  void removeChild(Title<?> child, boolean canon, @Nullable Title<?> newParent) {
        if (Children.contains(child)) {
            Children.remove(child);
            addStateChange(GlobalVars.CURRENT_DATE(),canon, new TitleTLChanges.DeJureDriftPassive<>(DMEReference.of(this),DMEReference.of(child),DMEReference.of(newParent),GlobalVars.CURRENT_DATE()));
        }
    }
    public void setParent(Title<?> parent) {
        Parent = Optional.of(parent);
        addStateChange(GlobalVars.CURRENT_DATE(), new TitleTLChanges.DeJureDrift<>(DMEReference.of(this),DMEReference.of(parent),GlobalVars.CURRENT_DATE()));
    }
    public boolean hasParent() {
        return Parent.isPresent();
    }
    public SuccessionContainer getSuccession() {
        return this.Succession;
    }
//    private void handleHolders(JsonArray holders) {
//        for (JsonElement holder : holders) {
//            everyHolder.add(UUID.fromString(holder.getAsString()));
//        }
//    }
    @Override
    protected JsonObject saveAdditional(JsonObject j) {
        j.addProperty("TitleType", this.getClass().getSimpleName());
//        JsonArray holders = new JsonArray();
//        for (UUID holder : everyHolder) {
//            holders.add(holder.toString());
//        }
        return super.saveAdditional(j);
    }
//    @Override
//    public StateChangeKey defaultKey() {
//        return new StateChangeKey(StateChangeKey.StateChangeType.GRANT_TITLE,new DMEReference<>(this));
//    }
    public abstract StateReference getTitleName();
    @Override
    public String toString() {
        return getTitleName().parse();
    }
    @Override
    public TitleContainer getCurrentContainer() {
        return TitleContainer.builder(Holder,Parent,Children, updateState(new JsonObject()));
    }
    //public UUID[] getAllHolders(){
    //    return everyHolder.toArray(UUID[]::new);
    //}
}
