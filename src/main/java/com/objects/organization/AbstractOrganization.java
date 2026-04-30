package com.objects.organization;

import com.base.condition.Condition;
import com.base.datemutable.timeline.error.StateError;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.ChangeSupplier;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.Culture;
import com.objects.culture.object.ICultureOpinionated;
import com.objects.culture.tenet.dynamic.DynamicTenet;
import com.objects.culture.tenet.group.TenetGroup;

import com.objects.organization.change.OrgParentChange;
import com.objects.organization.labor.LaborUnion;
import com.objects.succession.condition.CanHoldCondition;
import com.objects.succession.condition.CanInheritCondition;
import com.objects.succession.held.ICharacterHeld;
import com.objects.title.IPrestige;
import com.objects.title.profession.Job;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractOrganization<T extends AbstractOrganization<T>> extends DynamicTenet<T> implements ICharacterHeld<T> {
    private final Map<DMEReference<? extends Job>, Optional<DMEReference<? extends SentientCharacter<?>>>> employees = new HashMap<>();
    private DMEReference<? extends ICharacterHeld<?>> parent;
    private DMEReference<? extends SentientCharacter<?>> leader;
    private Graph<ICharacterHeld<?>, DefaultEdge> titleGraph;
    private final Set<DMEReference<? extends ICharacterHeld<?>>> children = new HashSet<>();
    public AbstractOrganization(TenetGroup group, DMEReference<T> dme) {
        super(group, dme);
    }

    public AbstractOrganization(TenetGroup group, String name, LocalDate created, LocalDate ended, DMEReference<Culture> foundingCulture, List<ChangeSupplier<T, ?>> initialState) {
        super(group, name, created, ended, foundingCulture, initialState);
    }

    public AbstractOrganization(TenetGroup group, String name, UUID id, LocalDate created, @Nullable LocalDate ended, DMEReference<Culture> foundingCulture, List<ChangeSupplier<T, ?>> initialState) {
        super(group, name, id, created, ended, foundingCulture, initialState);
    }


    @Override
    public final Optional<DMEReference<? extends ICharacterHeld<?>>> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public final Set<CanHoldCondition<? super T>> getHoldConditions() {
        return ICharacterHeld.super.getHoldConditions();
    }

    @Override
    public final Optional<DMEReference<? extends SentientCharacter<?>>> getHolder() {
        return Optional.ofNullable(leader);
    }

    @Override
    public final Set<DMEReference<? extends ICharacterHeld<?>>> getChildren() {
        return ICharacterHeld.super.getChildren();
    }

    @Override
    public final Set<CanInheritCondition<? super T>> getInheritConditions() {
        return ICharacterHeld.super.getInheritConditions();
    }

    @Override
    public final Graph<ICharacterHeld<?>, DefaultEdge> getTitleGraph() {
        return ICharacterHeld.super.getTitleGraph();
    }

    @Override
    public final Set<DMEReference<? extends ICharacterHeld<?>>> internalGetChildrenSet() {
        return children;
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
    public final void internalSetHolder(DMEReference<? extends SentientCharacter<?>> character) {
        leader = character;
    }

    @Override
    public final void internalSetParent(DMEReference<? extends ICharacterHeld<?>> newParent) {
        parent = newParent;
    }

    @Override
    public void onLink() {
        ICharacterHeld.super.onLink();
    }

    @Override
    public final boolean isTopLevel() {
        return ICharacterHeld.super.isTopLevel();
    }

    @Override
    public final void setHolder(DMEReference<? extends SentientCharacter<?>> character) {
        ICharacterHeld.super.setHolder(character);
    }

    @Override
    public final <C extends Condition<?, StateError, DMEReference<? super T>, DMEReference<? extends SentientCharacter<?>>, LocalDate>> Optional<StateError> canHold(DMEReference<? extends SentientCharacter<?>> creature, LocalDate date, List<Condition.ShouldRun> isSameState) {
        return ICharacterHeld.super.canHold(creature, date, isSameState);
    }

    @Override
    public final <C extends Condition<?, StateError, DMEReference<? super T>, DMEReference<? extends SentientCharacter<?>>, LocalDate>> Optional<StateError> canInherit(DMEReference<? extends SentientCharacter<?>> creature, LocalDate date, List<Condition.ShouldRun> isSameState) {
        return ICharacterHeld.super.canInherit(creature, date, isSameState);
    }

    @Override
    public final Set<DMEReference<? extends ICharacterHeld<?>>> getAllOffspring() {
        return ICharacterHeld.super.getAllOffspring();
    }

    public abstract Set<LaborUnion> getRelevantUnions();

    public final void linkEmployee(Job job) {
        employees.put(job.getReference(),job.getHolder());
    }
    public abstract Map<ICultureOpinionated,Integer> getStakeholdersFor(ICharacterHeld<?> object);
    protected <O extends AbstractOrganization<O>> Set<O> getMemberOrganizations(Class<O> clazz, boolean includeParents){
        Set<?> set = new HashSet<>(getByType(Type.Organization).stream().filter(clazz::isInstance).collect(Collectors.toSet()));
        Set<O> orgs = (Set<O>) set;
        DMEReference<? extends ICharacterHeld<?>> parent = getParent().orElse(null);
        if (includeParents && parent != null && parent.get() instanceof AbstractOrganization<?> iao) {
                orgs.addAll(iao.getMemberOrganizations(clazz, includeParents));
        }
        return orgs;
    }

}
