package com.objects.title;

import com.base.*;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.changes.TimelineChange;
import com.base.timeline.error.StateError;
import com.google.common.base.Suppliers;
import com.google.gson.JsonObject;
import com.objects.character.BookCharacter;
import com.objects.title.change.TitleSingletonChange;
import com.objects.title.condition.CanHoldCondition;
import com.objects.title.condition.CanInheritCondition;
import com.objects.title.succession.SuccessionContainer;
import com.objects.title.succession.rules.SuccessionEntry;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;

public abstract class Title<T extends Title<T>> extends DateMutableEntity<T> {
    private DMEReference<BookCharacter> holder = null;
    private DMEReference<? extends Title<?>> parent = null;
    private HashSet<DMEReference<? extends Title<?>>> children = new HashSet<>();
    private SuccessionEntry<?> succession;
;
    public Title(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(id, created, ended, initialState);
    }

    public Title(LocalDate created, LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(created, ended, initialState);
    }

    public Title(DMEReference<T> dme) {
        super(dme);
    }
    public static <T extends Title<T>> Optional<StateError> canHold(DMEReference<T> t, TitleSingletonChange.setHolderGrant<T> thisChange, TimelineChange<?> checkAgainst){
        for (CanHoldCondition<? super T> condition : t.get().getCanHoldConditions()){
            Optional<StateError> error = condition.check(t, thisChange, checkAgainst);
            if(error.isPresent()){
                return error;
            }
        }
        return Optional.empty();
    }
    public static <T extends Title<T>> Optional<StateError> canInherit(DMEReference<T> t, TitleSingletonChange.setHolderGrant<T> thisChange, TimelineChange<?> checkAgainst){
        for (CanInheritCondition<? super T> condition : t.get().getCanInheritConditions()){
            Optional<StateError> error = condition.check(t, thisChange, checkAgainst);
            if(error.isPresent()){
                return error;
            }
        }
        return Optional.empty();
    }

    protected abstract List<CanHoldCondition<? super T>> getCanHoldConditions();
    protected abstract List<CanInheritCondition<? super T>> getCanInheritConditions();
    public abstract String getName();
    //#### Getters, Setters and Internals ####

    public Optional<DMEReference<BookCharacter>> getHolder() {
        if(holder == null){
            return Optional.empty();
        }
        return Optional.of(holder);
    }


    public void setHolder(DMEReference<BookCharacter> holder) {
        getTimeline().addChange(new TitleSingletonChange.ChangeHolder<>(getReference(),current(),holder,this.holder));
    }

    public void removeRelationship(DMEReference<? extends Title<?>> title) {

    }
    @SuppressWarnings("unchecked")
    public void internalHolder(DMEReference<? extends BookCharacter> character) {
        holder = (DMEReference<BookCharacter>) character;
    }
    public void linkChild(DMEReference<? extends Title<?>> title) {
        this.children.add(title);
    }

    @Override
    public void onDateChange() {
        super.onDateChange();
        children.clear();
        levelsBelow = Suppliers.memoize(() -> {return lb.get();});
    }

    @Override
    protected void onLink() {
        Title<?> parent = this.parent.get();
        parent.linkChild(getReference());
    }
    public final int getPrestige(){
        return basePrestige() * levelsBelow.get();
    }
    protected abstract int basePrestige();
    private Supplier<Integer> lb = ()->{
        int level = 0;
        for (DMEReference<? extends Title<?>> child : children) {
            level += child.get().getPrestige();
        }
        return level;
    };
    protected Supplier<Integer> levelsBelow = Suppliers.memoize(() -> {return lb.get();});

    //#### Getters ####
    public List<DMEReference<? extends Title<?>>> getAllOffspring() {
        List<DMEReference<? extends Title<?>>> offspring = new ArrayList<>();
        for (DMEReference<? extends Title<?>> child : children) {
            offspring.add(child);
            offspring.addAll(child.get().getAllOffspring());
        }
        return offspring;
    }
    public Optional<DMEReference<? extends Title<?>>> getParent() {
        return Optional.ofNullable(parent);
    }
    public SuccessionEntry<?> getSuccession(LocalDate date) {
        return succession.getEntry(date);
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
