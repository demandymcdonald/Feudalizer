package com.objects.succession.rule;

import com.base.component.InstanceType;
import com.base.component.instanced.bi.IOBi;
import com.base.datemutable.DateMutableEntity;
import com.base.datemutable.timeline.sandbox.core.Sandbox;
import com.base.reference.DMEReference;
import com.google.gson.JsonObject;
import com.objects.character.sentient.SentientCharacter;
import com.objects.succession.held.ICharacterHeld;
import com.objects.title.Title;
import com.objects.succession.base.SuccessionType;
import com.utilities.IDisplayable;
import com.utilities.serialization.StringToHex;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;

public abstract class SuccessionRule<R extends SuccessionRule<R>> extends IOBi<R,RuleEntry<R>,Integer, Set<DMEReference<? extends Title<?>>>> implements IDisplayable {
    private String name;
    private String description;
    private SuccessionType successionType;
    private boolean shouldRemoveAfterInherit;
    public SuccessionRule(InstanceType instType, SuccessionType sucType, String id, String name, String description, boolean shouldRemoveAfterInherit) {
        super(instType, id);
        this.name = name;
        this.description = description;
        this.successionType = sucType;
        this.shouldRemoveAfterInherit = shouldRemoveAfterInherit;
    }
    public SuccessionRule(InstanceType type, String id) {
        super(type, id);
    }
    @Override
    public final String getDescription() {
        return description;
    }

    @Override
    public final String getDisplayName() {
        return name;
    }

    @Override
    public final String getDisplayID() {
        return getID();
    }

    public final boolean shouldRemoveAfterInherit() {
        return shouldRemoveAfterInherit;
    }
    public final SuccessionType getSuccessionType() {
        return successionType;
    }
    public final <T extends DateMutableEntity<T> & ICharacterHeld<T>, S extends SentientCharacter<S>> boolean isAllowed(DMEReference<T> title) {
        return governmentAllows((R) this,title) && isAllowedRules(title).stream().allMatch(p-> p.test(title));
    }
    protected abstract <T extends DateMutableEntity<T> & ICharacterHeld<T>> Set<Predicate<DMEReference<T>>> isAllowedRules(DMEReference<T> title);
    public <T extends DateMutableEntity<T> & ICharacterHeld<T>, S extends SentientCharacter<S>> LinkedHashSet<DMEReference<? extends SentientCharacter<?>>> generateLOS(Sandbox<S> sandbox, DMEReference<T> title, DMEReference<S> currentHolder, LocalDate date, Set<DMEReference<? extends SentientCharacter<?>>> blacklist){
        LinkedHashSet<DMEReference<? extends SentientCharacter<?>>> set = generateLOS(sandbox,title,currentHolder,date);
        set.removeAll(blacklist);
        return set;
    }
    protected abstract <T extends DateMutableEntity<T> & ICharacterHeld<T>, S extends SentientCharacter<S>> LinkedHashSet<DMEReference<? extends SentientCharacter<?>>> generateLOS(Sandbox<S> sandbox, DMEReference<T> title, DMEReference<S> currentHolder, LocalDate date);

    @Override
    public RuleEntry<R> instance(Integer integer, Set<DMEReference<? extends Title<?>>> dmeReferences) {
        return new RuleEntry<>(this.getReference(),integer,dmeReferences);
    }

    @Override
    public void additionalLoad(JsonObject data) {
        String[] idParts = data.get("sr:identity").getAsString().split("::");
        name = StringToHex.decode(idParts[0]);
        successionType = SuccessionType.valueOf(StringToHex.decode(idParts[1]));
        description = data.get("sr:desc").getAsString();
        shouldRemoveAfterInherit = data.get("sr:remove").getAsBoolean();
    }
    @Override
    public void additionalSave(JsonObject data) {
        String idStuff = StringToHex.encode(name) + "::" + StringToHex.encode(successionType.name());
        data.addProperty("sr:identity", idStuff);
        data.addProperty("sr:desc", description);
        data.addProperty("sr:remove", shouldRemoveAfterInherit);
    }
    public static <T extends DateMutableEntity<T> & ICharacterHeld<T>, R extends SuccessionRule<R>> boolean governmentAllows(R rule, DMEReference<T> title) {
        return title.get().getGovernment().get().isAllowedSuccessionType(title,rule);
    }
}
