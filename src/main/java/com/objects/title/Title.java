package com.objects.title;

import com.base.*;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.google.gson.JsonObject;
import com.objects.character.BookCharacter;
import com.objects.family.Family;
import com.objects.title.change.TitleSingletonChange;
import com.objects.title.condition.CanHoldCondition;
import com.objects.title.condition.CanInheritCondition;
import com.objects.title.succession.SuccessionContainer;
import com.objects.title.succession.rules.SuccessionEntry;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.*;

public abstract class Title<T extends Title<T>> extends DateMutableEntity<T> {
    private DMEReference<BookCharacter> holder = null;
    private DMEReference<? extends Title<?>> parent = null;
    private HashSet<DMEReference<? extends Title<?>>> children = new HashSet<>();
    private SuccessionContainer succession = new SuccessionContainer();

    public Title(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(id, created, ended, initialState);
    }

    public Title(LocalDate created, LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(created, ended, initialState);
    }

    public Title(DMEReference<T> dme) {
        super(dme);
    }

    public abstract List<CanHoldCondition<? super T>> getCanHoldConditions();
    public abstract List<CanInheritCondition<? super T>> getCanInheritConditions();
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

    public void internalHolder(DMEReference<BookCharacter> character) {
        holder = character;
    }
    public void linkChild(DMEReference<? extends Title<?>> title) {
        this.children.add(title);
    }

    @Override
    public void onDateChange() {
        super.onDateChange();
        children.clear();
    }

    @Override
    protected void onLink() {
        Title<?> parent = this.parent.get();
        parent.linkChild(getReference());
    }



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











    //==== Serializers ====
    @Override
    public void additionalSave(JsonObject data) {
        data.add("succession",succession.serialize());
    }
    @Override
    public void additionalLoad(JsonObject data) {
        succession.deserialize(data.get("succession").getAsJsonObject());
    }
}
