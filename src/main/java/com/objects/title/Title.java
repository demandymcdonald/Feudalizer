package com.objects.title;

import com.Global;
import com.base.*;
import com.base.reference.DMEReference;
import com.base.reference.StateReference;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.changes.TimelineChange;
import com.base.timeline.change.changes.TitleTLChange;
import com.base.timeline.change.conditions.CanHoldTitleCondition;
import com.base.timeline.change.conditions.DMEResult;
import com.google.gson.JsonObject;
import com.objects.character.BookCharacter;
import com.objects.character.CharacterManager;
import com.objects.title.change.TitleSingletonChange;
import com.objects.title.condition.CanHoldCondition;
import com.objects.title.condition.CanInheritCondition;
import com.objects.title.succession.SuccessionContainer;
import com.objects.title.succession.rules.SuccessionEntry;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.*;

public abstract class Title<T extends Title<T>> extends DateMutableEntity<T> {
    public enum Relationship {
        Parent_Title(1),
        Child_Title(999);

        private final int maxOf;
        Relationship(int max){
            this.maxOf = max;
        }
    }
    private DMEReference<BookCharacter> holder = null;
    private Map<DMEReference<? extends Title<?>>,Relationship> relationships = new HashMap<>();
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

    //#### Getters, Setters and Internals ####

    public Optional<DMEReference<BookCharacter>> getHolder() {
        if(holder == null){
            return Optional.empty();
        }
        return Optional.of(holder);
    }
    public Optional<DMEReference<? extends Title<?>>> getParent() {
        List<DMEReference<? extends Title<?>>> parents = getByRelationship(Relationship.Parent_Title);
        if(parents.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(parents.get(0));
    }
    public Optional<List<DMEReference<? extends Title<?>>>> getChildren() {
        return Optional.of(getByRelationship(Relationship.Child_Title));
    }
    private List<DMEReference<? extends Title<?>>> getByRelationship(Relationship rel){
        List<DMEReference<? extends Title<?>>> list = new ArrayList<>();
        for (Map.Entry<DMEReference<? extends Title<?>>, Relationship> entry : relationships.entrySet()) {
            if (entry.getValue() == rel) list.add(entry.getKey());
        }
        return list;
    }
    public SuccessionEntry<?> getSuccession(LocalDate date) {
        return succession.getEntry(date);
    }


    public void setHolder(DMEReference<BookCharacter> holder) {
        getTimeline().addChange(new TitleSingletonChange.ChangeHolder<>(getReference(),current(),holder,this.holder));
    }
    public void setRelationship(DMEReference<? extends Title<?>> title, Relationship rel) {

    }
    public void removeRelationship(DMEReference<? extends Title<?>> title) {

    }

    public void internalHolder(DMEReference<BookCharacter> character) {
        holder = character;
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
