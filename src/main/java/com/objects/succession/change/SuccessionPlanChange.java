package com.objects.succession.change;

import com.base.datemutable.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.datemutable.timeline.change.multi.TimelineSetChange;
import com.base.datemutable.timeline.change.multi.condition.MultiCondition;
import com.base.datemutable.timeline.change.multi.wrapper.TLSet;
import com.base.datemutable.timeline.state.TimelineState;
import com.base.reference.DMEReference;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.objects.character.sentient.SentientCharacter;
import com.objects.succession.rules.RuleEntry;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class SuccessionPlanChange<T extends SentientCharacter<T>> extends TimelineSetChange<SuccessionPlanChange<T>, RuleEntry<?>, UUID,T> {
    protected SuccessionPlanChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }

    protected SuccessionPlanChange(DMEReference<? extends T> owner, LocalDate date, Set<RuleEntry<?>> initial) {
        super(owner, date, initial);
    }

    @Override
    public TLSet<RuleEntry<?>> getRuntimeSet(T owner) {

    }

    @Override
    public void setRuntimeSet(T owner, TLSet<RuleEntry<?>> set) {

    }

    @Override
    public SuccessionPlanChange<T> getEmptyChange(DMEReference<? extends T> owner, LocalDate date) {
        return new SuccessionPlanChange<>(owner, date);
    }

    @Override
    public void conditionsWipeForward(List<MultiCondition<SuccessionPlanChange<T>, RuleEntry<?>, Boolean, UUID, T>> current) {

    }

    @Override
    public void conditionsWipeBackward(List<MultiCondition<SuccessionPlanChange<T>, RuleEntry<?>, Boolean, UUID, T>> current) {

    }

    @Override
    protected JsonElement kSerialize(RuleEntry<?> ruleEntry) {
        return ruleEntry.serialize();
    }

    @Override
    protected RuleEntry<?> kDeserialize(JsonElement o) {
        return new RuleEntry<>(o.getAsJsonObject());
    }

    @Override
    protected JsonElement iSerialize(UUID uuid) {
        return new JsonPrimitive(uuid.toString());
    }

    @Override
    protected UUID iDeserialize(JsonElement o) {
        return UUID.fromString(o.getAsString());
    }

    @Override
    protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {

    }

    @Override
    protected String getText() {
        return "";
    }

    @Override
    protected void deactivateConditions(List<DeactivateCondition<? super T>> list) {

    }

    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }
}
