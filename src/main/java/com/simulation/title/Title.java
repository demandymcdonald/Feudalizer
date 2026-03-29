package com.simulation.title;

import com.GlobalVars;
import com.base.*;
import com.base.reference.DMEReference;
import com.base.reference.StateReference;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.TitleTLChange;
import com.base.timeline.change.conditions.CanHoldTitleCondition;
import com.base.timeline.change.conditions.DMEResult;
import com.google.gson.JsonObject;
import com.simulation.people.BookCharacter;
import com.simulation.people.CharacterManager;
import com.simulation.title.succession.SuccessionContainer;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class Title<T extends Title<T>> extends DateMutableEntity<T> {
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
        //handleHolders(additionalData.get("Holders").getAsJsonArray());
    }

    public Title(JsonObject payload) {
        super(payload);
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
    public int maxOfType(){
        return Integer.MAX_VALUE;
    }
    public abstract boolean canInherit(BookCharacter person);
    public abstract boolean isInheritable();
    public abstract boolean isSubPropagating();
    public boolean canHold(BookCharacter person){
        for (DMEResult<T,?,BookCharacter> result : canHoldDeep(this,person)){
            if (!result.canHold()){
                return false;
            }
        }
        return true;
    };
    public static <T extends Title<T>> List<DMEResult<T,?,BookCharacter>> canHoldDeep(Title<T> title, BookCharacter person){
        List<CanHoldTitleCondition<T,?>> base = new ArrayList<>(CanHoldTitleCondition.BaseConditions());
        base.addAll((Collection<? extends CanHoldTitleCondition<T, ?>>) title.getConditions());
        return title.runCheck(base,person);
    };
    private List<DMEResult<T,?,BookCharacter>> runCheck(List<CanHoldTitleCondition<T,?>> conditions, BookCharacter person){
        List<DMEResult<T,?,BookCharacter>> results = new ArrayList<>();
        for (CanHoldTitleCondition<T,?> t : conditions){
            results.add(t.check((T) this,null,person));
        }
        return results;
    }
    protected abstract List<CanHoldTitleCondition<T,?>> getConditions();

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
        Holder = Optional.of(holder);
        addStateChange(GlobalVars.CURRENT_DATE(), new TitleTLChange.Grant<>(DMEReference.of(this), DMEReference.of(holder), GlobalVars.CURRENT_DATE()));
        holder.addTitle(this);
    }
    public void setInherit(BookCharacter holder, LocalDate date, boolean isFirst) {
        Holder = Optional.of(holder);
        addStateChange(date,isFirst, new TitleTLChange.Inherit<>(DMEReference.of(this), DMEReference.of(holder), date));
        //holder.addTitle(this);
    }
    public void setInherit(TitleTLChange.Inherit<T> inherit, LocalDate date, boolean isFirst) {
        Holder = Optional.of(inherit.getHolder().orElseThrow().link());
        Title<?> t = inherit.getTitle().link();
        Holder.get().addToTitleList(t);
        addStateChange(date,isFirst, inherit);
        //Holder.addTitle(this);
    }
    public void removeHolder(BookCharacter holder){
        Holder = Optional.empty();
        addStateChange(GlobalVars.CURRENT_DATE(), new TitleTLChange.Revoke<>(DMEReference.of(this), DMEReference.of(holder), GlobalVars.CURRENT_DATE()));
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
    public boolean hasChildren() {
        return !Children.isEmpty();
    }
    public void addChild(Title<?> child) {
        Children.add(child);
        child.setParent(this);
        final TitleTLChange.DeJureDriftPassive<?,?,?> changes = new TitleTLChange.DeJureDriftPassive<>(DMEReference.of(this),DMEReference.of(child),GlobalVars.CURRENT_DATE());
        addStateChange(GlobalVars.CURRENT_DATE(), (TimelineChange<T>) changes);
    }
    public
    public  void removeChild(Title<?> child, boolean canon, @Nullable Title<?> newParent) {
        if (Children.contains(child)) {
            Children.remove(child);
            addStateChange(GlobalVars.CURRENT_DATE(),canon, new TitleTLChange.DeJureDriftPassive<>(DMEReference.of(this),DMEReference.of(child),DMEReference.of(newParent),GlobalVars.CURRENT_DATE()));
        }
    }
    public void setParent(Title<?> parent) {
        Parent = Optional.of(parent);
        addStateChange(GlobalVars.CURRENT_DATE(), new TitleTLChange.DeJureDrift<>(DMEReference.of(this),DMEReference.of(parent),GlobalVars.CURRENT_DATE()));
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
