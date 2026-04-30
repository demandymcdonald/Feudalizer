package com.objects.succession.held;

import com.Global;
import com.base.condition.Condition;
import com.base.datemutable.DateMutableEntity;
import com.base.datemutable.timeline.error.StateError;
import com.base.reference.DMEReference;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.object.ICultureObject;
import com.objects.organization.government.GoverningEntity;
import com.objects.organization.government.IGoverned;
import com.objects.succession.change.HolderChanges;
import com.objects.title.IPrestige;
import com.objects.succession.condition.CanHoldCondition;
import com.objects.succession.condition.CanInheritCondition;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;

import java.time.LocalDate;
import java.util.*;

public interface ICharacterHeld<T extends DateMutableEntity<T> & ICharacterHeld<T>> extends IGoverned<T>, IPrestige, ICultureObject {
    DMEReference<T> getReference();
    Optional<DMEReference<? extends ICharacterHeld<?>>> getParent();
    void internalSetParent(DMEReference<? extends ICharacterHeld<?>> newParent);
    Set<DMEReference<? extends ICharacterHeld<?>>> internalGetChildrenSet();
    default Set<DMEReference<? extends ICharacterHeld<?>>> getChildren(){
        return new HashSet<>(internalGetChildrenSet());
    }
    default Set<DMEReference<? extends ICharacterHeld<?>>> getAllOffspring() {
        Set<DMEReference<? extends ICharacterHeld<?>>> offspring = new HashSet<>();
        for (DMEReference<? extends ICharacterHeld<?>> child : getChildren()) {
            offspring.add(child);
            offspring.addAll(child.get().getAllOffspring());
        }
        return offspring;
    }
    @SuppressWarnings("unchecked")
    default <C extends Condition<?,StateError, DMEReference<? super T>,DMEReference<? extends SentientCharacter<?>>, LocalDate>> Optional<StateError> canInherit(DMEReference<? extends SentientCharacter<?>> creature, LocalDate date, List<Condition.ShouldRun> isSameState){
        return handleConditions((Set<C>) getInheritConditions(),getReference(),creature,date,isSameState);
    };
    @SuppressWarnings("unchecked")
    default <C extends Condition<?,StateError, DMEReference<? super T>,DMEReference<? extends SentientCharacter<?>>, LocalDate>> Optional<StateError> canHold(DMEReference<? extends SentientCharacter<?>> creature, LocalDate date, List<Condition.ShouldRun> isSameState){
        return handleConditions((Set<C>) getHoldConditions(),getReference(),creature,date,isSameState);
    };


    static<T extends DateMutableEntity<T> & ICharacterHeld<T>, C extends Condition<?,StateError, DMEReference<? super T>,DMEReference<? extends SentientCharacter<?>>, LocalDate>>
    Optional<StateError> handleConditions(
            Set<C> conditions,
            DMEReference<T> self,
            DMEReference<? extends SentientCharacter<?>> creature,
            LocalDate date,
            List<Condition.ShouldRun> isSameState){
        for (C condition : conditions){
            Optional<StateError> error = condition.check(self, creature, date,isSameState);
            if(error.isPresent()){
                return error;
            }
        }
        return Optional.empty();
    }
    default Set<CanInheritCondition<? super T>> getInheritConditions(){
        Set<CanInheritCondition<? super T>> list = new HashSet<>();
        conditionsCanInherit(list);
        return list;
    }
    default Set<CanHoldCondition<? super T>> getHoldConditions(){
        Set<CanHoldCondition<? super T>> list = new HashSet<>();
        conditionsCanHold(list);
        return list;
    }
    Optional<DMEReference<? extends SentientCharacter<?>>> getHolder();
    void internalSetHolder(DMEReference<? extends SentientCharacter<?>> character);
    default void setHolder(DMEReference<? extends SentientCharacter<?>> character){
        getReference().get().getTimeline().addChange(new HolderChanges.Change<>(getReference(), Global.getDate(),character));
    }
    void conditionsCanInherit(Set<CanInheritCondition<? super T>> list);
    void conditionsCanHold(Set<CanHoldCondition<? super T>> list);

    default boolean isTopLevel() {
        return getParent().isEmpty();
    }
    Graph<ICharacterHeld<?>, DefaultEdge> internalGetGraph();
    void internalSetGraph(Graph<ICharacterHeld<?>, DefaultEdge> graph);
    default Graph<ICharacterHeld<?>, DefaultEdge> getTitleGraph() {
        if(isTopLevel()) {
            return internalGetGraph();
        } else {
            return getParent().get().get().getTitleGraph();
        }
    }
    default void onLink(){
        IGoverned.super.onLink();
        if (!isTopLevel()) {
            DMEReference<? extends ICharacterHeld<?>> parent = this.getParent().orElseThrow();
            parent.get().forceLink();
            parent.get().internalGetChildrenSet().add(this.getReference());
            Graph<ICharacterHeld<?>,DefaultEdge> graph = parent.get().getTitleGraph();
            graph.addVertex(this);
            graph.addEdge(parent.get(),this);
        } else {
            internalSetGraph(new DirectedPseudograph<>(DefaultEdge.class));
            getTitleGraph().addVertex(this);
        }
        DMEReference<? extends SentientCharacter<?>> holder = this.getHolder().orElse(null);
        if (holder != null) {
            SentientCharacter<?> character = holder.get();
            DMEReference<? extends GoverningEntity<?>> ourGov = this.getGovernment();
            if (character.getGovernment().equals(ourGov)) {
                character.setGovernment(this.getGovernment());
            }
            character.linkHeld(getReference());
        }
    }
}
