package com.objects.succession.rule;

import com.base.component.ComponentReference;
import com.base.component.instanced.bi.IOIBi;
import com.base.datemutable.DateMutableEntity;
import com.base.datemutable.timeline.sandbox.core.Sandbox;
import com.base.reference.DMEReference;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.objects.character.sentient.SentientCharacter;
import com.objects.succession.held.ICharacterHeld;
import com.objects.title.Title;
import com.utilities.id.Identifiable;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class RuleEntry<R extends SuccessionRule<R>> extends IOIBi<R,RuleEntry<R>,Integer, Set<DMEReference<? extends Title<?>>>> implements Identifiable<UUID> {
    private int priority;
    private ImmutableSet<DMEReference<? extends Title<?>>> scope;
    public RuleEntry(ComponentReference<R> reference, int priority, Set<DMEReference<? extends Title<?>>> titles) {
        super(reference);
        this.priority = priority;
        this.scope = ImmutableSet.copyOf(titles);
    }
    public RuleEntry(ComponentReference<R> reference) {
        super(reference);
        this.priority = 999;
        this.scope = ImmutableSet.of();
    }

    public RuleEntry(JsonObject object) {
        super(object);
    }

    public boolean hasScope(){
        return !scope.isEmpty();
    }
    public int getPriority() {
        return priority;
    }
    public ImmutableSet<DMEReference<? extends Title<?>>> getScope() {
        return scope;
    }

    public <T extends DateMutableEntity<T> & ICharacterHeld<T>, S extends SentientCharacter<S>> LinkedHashSet<DMEReference<? extends SentientCharacter<?>>>
    getLoS(Sandbox<S> sandbox, DMEReference<T> title, DMEReference<S> currentHolder, LocalDate date, Set<DMEReference<? extends SentientCharacter<?>>> blacklist){
        return getBase().get().generateLOS(sandbox,title,currentHolder,date,blacklist);
    }
    @Override
    public void additionalSave(JsonObject data) {
        data.addProperty("sr:priority", priority);
        JsonArray array = new JsonArray();
        for(DMEReference<? extends Title<?>> title : scope){
            array.add(title.serialize());
        }
        if(!array.isJsonNull()){
            data.add("sr:titles",array);
        }
    }
    @Override
    public void additionalLoad(JsonObject data) {
        priority = data.get("sr:priority").getAsInt();
        JsonArray array = data.get("sr:titles").getAsJsonArray();
        ImmutableSet.Builder<DMEReference<? extends Title<?>>> builder = ImmutableSet.builder();
        for(JsonElement e : array){
            builder.add(DMEReference.deserialize(e));
        }
        scope = builder.build();
    }
}
