package com.objects.title;

import com.base.condition.Condition;
import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.ChangeSupplier;
import com.base.datemutable.timeline.change.display.DisplayContainer;
import com.base.datemutable.timeline.change.display.ITLDisplayable;
import com.base.datemutable.timeline.error.StateError;
import com.google.common.base.Suppliers;
import com.google.gson.JsonObject;
import com.objects.character.sentient.HumanCharacter;
import com.objects.character.sentient.SentientCharacter;
import com.objects.organization.government.IGoverned;
import com.objects.culture.object.ICultureObject;
import com.objects.organization.government.GoverningEntity;
import com.objects.title.change.TitleSingleChange;
import com.objects.title.condition.CanHoldCondition;
import com.objects.title.succession.rules.SuccessionEntry;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;

public abstract class Title<T extends Title<T>> extends DateMutableEntity<T> implements ICultureObject, ITLDisplayable<T>, IGoverned<T>, IPrestiged {
    private Graph<Title<?>, DefaultEdge> titleGraph;
    private DMEReference<? extends SentientCharacter<?>> holder;
    private DMEReference<? extends Title<?>> parent;
    private SuccessionEntry<?> succession;
    private DMEReference<? extends GoverningEntity<?>> government;
    private final DisplayContainer<T> container;
    private final HashSet<DMEReference<? extends Title<?>>> linkedChildren = new HashSet<>();

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
    public final boolean isTopLevel() {
        return this.parent == null;
    }
    public final Graph<Title<?>, DefaultEdge> getTitleGraph() {
        if(isTopLevel()) {
            return titleGraph;
        } else {
            return parent.get().getTitleGraph();
        }
    }
    @Override
    public void onLink() {
        if (!isTopLevel()) {
            Title<?> parent = this.parent.get();
            parent.forceLink();
            parent.linkChild(getReference());
            parent.getTitleGraph().addVertex(this);
            parent.getTitleGraph().addEdge(parent,this);
        } else {
            titleGraph = new DirectedPseudograph<>(DefaultEdge.class);
            titleGraph.addVertex(this);
        }
        DMEReference<? extends SentientCharacter<?>> holder = this.holder;
        if (holder != null) {
            SentientCharacter<?> character = holder.get();
            if (character.getGovernment() != this.government) {
                character.setGovernment(this.government);
            }
            character.linkTitle(getReference());
        }
    }
    public final void linkChild(DMEReference<? extends Title<?>> title) {
        this.linkedChildren.add(title);
    }

    @Override
    public final Type getType() {
        return Type.Title;
    }

    protected abstract int basePrestige();
    private Supplier<Integer> lb = ()->{
        int level = 0;
        for (DMEReference<? extends Title<?>> child : linkedChildren) {
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
    public final Optional<DMEReference<? extends Title<?>>> getParent() {
        return Optional.ofNullable(parent);
    }
    public final SuccessionEntry<?> getSuccession(LocalDate date) {
        return succession;
    }
    public final List<DMEReference<? extends Title<?>>> getAllOffspring() {
        List<DMEReference<? extends Title<?>>> offspring = new ArrayList<>();
        for (DMEReference<? extends Title<?>> child : linkedChildren) {
            offspring.add(child);
            offspring.addAll(child.get().getAllOffspring());
        }
        return offspring;
    }

    public static <T extends Title<T>> Optional<StateError> canHold(DMEReference<T> title, DMEReference<? extends SentientCharacter<?>> creature, LocalDate date, List<Condition.ShouldRun> isSameState){
        for (CanHoldCondition<? super T> condition : title.get().getCanHoldConditions()){
            Optional<StateError> error = condition.check(title, creature, date,isSameState);
            if(error.isPresent()){
                return error;
            }
        }
        return Optional.empty();
    }
    public static <T extends Title<T>> Optional<StateError> canInherit(DMEReference<T> title, DMEReference<? extends SentientCharacter<?>> creature, LocalDate date, List<Condition.ShouldRun> isSameState){
        for (CanHoldCondition<? super T> condition : title.get().getCanInheritConditions()){
            Optional<StateError> error = condition.check(title, creature, date,isSameState);
            if(error.isPresent()){
                return error;
            }
        }
        return Optional.empty();
    }
    protected final List<CanHoldCondition<? super T>> getCanHoldConditions(){
        return new ArrayList<>() {
            {
                conditionsCanHold(this);
            }
        };
    };

    protected final List<CanHoldCondition<? super T>> getCanInheritConditions(){
        return new ArrayList<>() {
            {
                conditionsCanInherit(this);
            }
        };
    };
    protected abstract void conditionsCanHold(List<CanHoldCondition<? super T>> list);
    protected abstract void conditionsCanInherit(List<CanHoldCondition<? super T>> list);
    public abstract String getTitleName();


    public final void setSuccession(SuccessionEntry<?> succession) {
        getTimeline().addChange(new TitleSingleChange.setSuccessionEntry<>(getReference(),current(),succession));
    }
    public final void setParent(DMEReference<? extends Title<?>> parent) {
        getTimeline().addChange(new TitleSingleChange.setParent<>(getReference(),current(),parent));
    }
    public final void setHolder(DMEReference<HumanCharacter> holder) {
        getTimeline().addChange(new TitleSingleChange.setHolderGrant<>(getReference(),current(),holder));
    }

    @SuppressWarnings("unchecked")
    public final void internalHolder(DMEReference<? extends SentientCharacter<?>> character) {
        holder = (DMEReference<? extends SentientCharacter<?>>) character;
    }
    public final void internalParent(DMEReference<? extends Title<?>> parent) {
        this.parent = parent;
    }
    public final void internalSuccession(SuccessionEntry<?> succession) {
        this.succession = succession;
    }


    @Override
    public final void internalSetGovernment(DMEReference<? extends GoverningEntity<?>> government) {
        this.government = government;
    }

    @Override
    public final DMEReference<? extends GoverningEntity<?>> getGovernment() {
        return government;
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
