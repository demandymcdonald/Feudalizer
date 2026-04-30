package com.objects.title;

import com.base.condition.Condition;
import com.base.datemutable.DateMutableEntity;
import com.base.datemutable.timeline.error.StateError;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.ChangeSupplier;
import com.base.datemutable.timeline.change.display.DisplayContainer;
import com.base.datemutable.timeline.change.display.ITLDisplayable;
import com.google.common.base.Suppliers;
import com.google.gson.JsonObject;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.object.ICultureObject;
import com.objects.organization.government.GoverningEntity;
import com.objects.succession.held.ICharacterHeld;
import com.objects.succession.condition.CanHoldCondition;
import com.objects.succession.condition.CanInheritCondition;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;

public abstract class Title<T extends Title<T>> extends DateMutableEntity<T> implements ICultureObject, ITLDisplayable<T>, ICharacterHeld<T> {
    private Graph<ICharacterHeld<?>, DefaultEdge> titleGraph;
    private DMEReference<? extends SentientCharacter<?>> holder;
    private DMEReference<? extends ICharacterHeld<?>> parent;
    private DMEReference<? extends GoverningEntity<?>> government;
    private final DisplayContainer<T> container;
    private final Set<DMEReference<? extends ICharacterHeld<?>>> linkedChildren = new HashSet<>();

    public Title(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(id, created, ended, initialState);
         container= new DisplayContainer<>(this.getReference());
    }

    public Title(LocalDate created, LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(created, ended, initialState);
        container= new DisplayContainer<>(this.getReference());
    }

    public Title(DMEReference<T> dme) {
        super(dme);
        container= new DisplayContainer<>(dme);
    }

    @Override
    public final DisplayContainer<T> getDisplayable() {
        return container;
    }
    @Override
    public void doDateChange() {
        linkedChildren.clear();
        levelsBelow = Suppliers.memoize(() -> {return lb.get();});
    }
    @Override
    public void onLink() {
        ICharacterHeld.super.onLink();
    }
    protected abstract int basePrestige();
    private Supplier<Integer> lb = ()->{
        int level = 0;
        for (DMEReference<? extends ICharacterHeld<?>> child : linkedChildren) {
            level += child.get().getPrestige();
        }
        return level;
    };
    protected Supplier<Integer> levelsBelow = Suppliers.memoize(() -> {return lb.get();});

    //#### Getters, Setters and Internals ####
    public final int getPrestige(){
        return basePrestige() * levelsBelow.get();
    }
    public final Optional<DMEReference<? extends SentientCharacter<?>>> getHolder() {
        if(holder == null){
            return Optional.empty();
        }
        return Optional.of(holder);
    }

    public abstract String getTitleName();
    public final void internalSetParent(DMEReference<? extends ICharacterHeld<?>> parent) {
        this.parent = parent;
    }
    @Override
    public final Optional<DMEReference<? extends ICharacterHeld<?>>> getParent() {
        return Optional.ofNullable(parent);
    }
    @Override
    public final Graph<ICharacterHeld<?>, DefaultEdge> internalGetGraph() {
        return titleGraph;
    }
    @Override
    public final void internalSetGraph(Graph<ICharacterHeld<?>, DefaultEdge> graph) {
        titleGraph = graph;
    }
    @Override
    public final Set<DMEReference<? extends ICharacterHeld<?>>> internalGetChildrenSet() {
        return linkedChildren;
    }
    @Override
    public final void internalSetHolder(DMEReference<? extends SentientCharacter<?>> character) {
        holder = character;
    }
    @Override
    public final Set<DMEReference<? extends ICharacterHeld<?>>> getChildren() {
        return ICharacterHeld.super.getChildren();
    }
    @Override
    public final void internalSetGovernment(DMEReference<? extends GoverningEntity<?>> government) {
        this.government = government;
    }
    @Override
    public final DMEReference<? extends GoverningEntity<?>> getGovernment() {
        return government;
    }


    @Override
    public final void setHolder(DMEReference<? extends SentientCharacter<?>> character) {
        ICharacterHeld.super.setHolder(character);
    }
    @Override
    public final boolean isTopLevel() {
        return ICharacterHeld.super.isTopLevel();
    }

    @Override
    public final Graph<ICharacterHeld<?>, DefaultEdge> getTitleGraph() {
        return ICharacterHeld.super.getTitleGraph();
    }

    @Override
    public final Set<CanInheritCondition<? super T>> getInheritConditions() {
        return ICharacterHeld.super.getInheritConditions();
    }

    @Override
    public final Set<CanHoldCondition<? super T>> getHoldConditions() {
        return ICharacterHeld.super.getHoldConditions();
    }

    @Override
    public final Set<DMEReference<? extends ICharacterHeld<?>>> getAllOffspring() {
        return ICharacterHeld.super.getAllOffspring();
    }

    @Override
    public final <C extends Condition<?, StateError, DMEReference<? super T>, DMEReference<? extends SentientCharacter<?>>, LocalDate>> Optional<StateError> canInherit(DMEReference<? extends SentientCharacter<?>> creature, LocalDate date, List<Condition.ShouldRun> isSameState) {
        return ICharacterHeld.super.canInherit(creature, date, isSameState);
    }
    @Override
    public final <C extends Condition<?, StateError, DMEReference<? super T>, DMEReference<? extends SentientCharacter<?>>, LocalDate>> Optional<StateError> canHold(DMEReference<? extends SentientCharacter<?>> creature, LocalDate date, List<Condition.ShouldRun> isSameState) {
        return ICharacterHeld.super.canHold(creature, date, isSameState);
    }

    @Override
    public final String getDescription() {
        return ITLDisplayable.super.getDescription();
    }

    @Override
    public final String getDisplayName() {
        return ITLDisplayable.super.getDisplayName();
    }

    //==== Serializers ====
    @Override
    public void additionalSave(JsonObject data) {
        //data.add("succession",succession.serialize());
    }
    @Override
    public void additionalLoad(JsonObject data) {
        //succession.deserialize(data.get("succession").getAsJsonObject());
    }
}
