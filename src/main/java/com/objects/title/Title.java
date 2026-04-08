package com.objects.title;

import com.base.*;
import com.base.condition.Condition;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.error.StateError;
import com.google.common.base.Suppliers;
import com.google.gson.JsonObject;
import com.objects.character.human.HumanCharacter;
import com.objects.character.LivingCreature;
import com.objects.government.GoverningEntity;
import com.objects.title.change.TitleSingleChange;
import com.objects.title.condition.CanHoldCondition;
import com.objects.title.condition.CanInheritCondition;
import com.objects.title.succession.rules.SuccessionEntry;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;

public abstract class Title<T extends Title<T>> extends DateMutableEntity<T> {
    private DMEReference<? extends GoverningEntity<?>> governing_entity;
    private DMEReference<HumanCharacter> holder;
    private DMEReference<? extends Title<?>> parent;
    private SuccessionEntry<?> succession;


    private final HashSet<DMEReference<? extends Title<?>>> linkedChildren = new HashSet<>();

    public Title(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(id, created, ended, initialState);
    }

    public Title(LocalDate created, LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(created, ended, initialState);
    }

    public Title(DMEReference<T> dme) {
        super(dme);
    }




    @Override
    public void onDateChange() {
        super.onDateChange();
        linkedChildren.clear();
        levelsBelow = Suppliers.memoize(() -> {return lb.get();});
    }

    @Override
    protected void onLink() {
        Title<?> parent = this.parent.get();
        parent.linkChild(getReference());
        DMEReference<HumanCharacter> holder = this.holder;
        if (holder != null) {
            HumanCharacter character = holder.get();
            character.linkTitle(getReference());
            if (governing_entity != null) {

            }
        }
    }
    public void linkChild(DMEReference<? extends Title<?>> title) {
        this.linkedChildren.add(title);
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
    public Optional<DMEReference<HumanCharacter>> getHolder() {
        if(holder == null){
            return Optional.empty();
        }
        return Optional.of(holder);
    }
    public Optional<DMEReference<? extends Title<?>>> getParent() {
        return Optional.ofNullable(parent);
    }
    public SuccessionEntry<?> getSuccession(LocalDate date) {
        return succession;
    }
    public List<DMEReference<? extends Title<?>>> getAllOffspring() {
        List<DMEReference<? extends Title<?>>> offspring = new ArrayList<>();
        for (DMEReference<? extends Title<?>> child : linkedChildren) {
            offspring.add(child);
            offspring.addAll(child.get().getAllOffspring());
        }
        return offspring;
    }

    public static <T extends Title<T>> Optional<StateError> canHold(DMEReference<T> t, DMEReference<? extends LivingCreature<?>> creature, LocalDate date, List<Condition.ShouldRun> isSameState){
        for (CanHoldCondition<? super T> condition : t.get().getCanHoldConditions()){
            Optional<StateError> error = condition.check(t, creature, date,isSameState);
            if(error.isPresent()){
                return error;
            }
        }
        return Optional.empty();
    }
    public static <T extends Title<T>> Optional<StateError> canInherit(DMEReference<T> t, DMEReference<? extends LivingCreature<?>> creature, LocalDate date, List<Condition.ShouldRun> isSameState){
        for (CanHoldCondition<? super T> condition : t.get().getCanInheritConditions()){
            Optional<StateError> error = condition.check(t, creature, date,isSameState);
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


    public void setSuccession(SuccessionEntry<?> succession) {
        getTimeline().addChange(new TitleSingleChange.setSuccessionEntry<>(getReference(),current(),succession));
    }
    public void setParent(DMEReference<? extends Title<?>> parent) {
        getTimeline().addChange(new TitleSingleChange.setParent<>(getReference(),current(),parent));
    }
    public void setHolder(DMEReference<HumanCharacter> holder) {
        getTimeline().addChange(new TitleSingleChange.setHolderGrant<>(getReference(),current(),holder));
    }
    public void setGoverningEntity(DMEReference<? extends GoverningEntity<?>> governingEntity) {
        getTimeline().addChange(new TitleSingleChange.setGoverningEntity<>(getReference(),current(),governingEntity));
    }

    @SuppressWarnings("unchecked")
    public void internalHolder(DMEReference<? extends HumanCharacter> character) {
        holder = (DMEReference<HumanCharacter>) character;
    }
    public void internalParent(DMEReference<? extends Title<?>> parent) {
        this.parent = parent;
    }
    public void internalSuccession(SuccessionEntry<?> succession) {
        this.succession = succession;
    }
    public void internalGoverningEntity(DMEReference<? extends GoverningEntity<?>> governingEntity) {
        governing_entity = governingEntity;
    }

    public abstract boolean isChartered();




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
