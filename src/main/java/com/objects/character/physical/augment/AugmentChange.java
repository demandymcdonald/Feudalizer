package com.objects.character.physical.augment;

import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.datemutable.timeline.change.multi.condition.MultiCondition;
import com.base.datemutable.timeline.change.multi.wrapper.TLMap;
import com.base.datemutable.timeline.change.multi.TimelineMapChange;
import com.base.datemutable.timeline.state.TimelineState;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.objects.character.LivingCreature;
import com.objects.character.physical.GeneManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class AugmentChange<T extends LivingCreature<T>> extends TimelineMapChange<AugmentChange<T>, AugmentSlot, AugmentInstance,String,T> {
    public AugmentChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }

    public AugmentChange(DMEReference<? extends T> owner, LocalDate date, Map<AugmentSlot, AugmentInstance> initial) {
        super(owner, date, initial);
    }

    @Override
    public void setRuntimeMap(TLMap<AugmentSlot, AugmentInstance> map) {
        getOwner().get().internalSetAugments(map);
    }

    @Override
    public TLMap<AugmentSlot, AugmentInstance> getRuntimeMap() {
        return getOwner().get().internalGetAugments();
    }

    @Override
    public AugmentChange<T> getEmptyChange(DMEReference<? extends T> owner, LocalDate date) {
        return null;
    }

    @Override
    public boolean hasEndingChanges() {
        return false;
    }

    @Override
    public void conditionsWipeForward(List<MultiCondition<AugmentChange<T>, AugmentSlot, AugmentInstance, String, T>> current) {

    }

    @Override
    public void conditionsWipeBackward(List<MultiCondition<AugmentChange<T>, AugmentSlot, AugmentInstance, String, T>> current) {

    }

    @Override
    protected JsonElement kSerialize(AugmentSlot slot) {
        return new JsonPrimitive(slot.getID());
    }

    @Override
    protected AugmentSlot kDeserialize(JsonElement o) {
        return GeneManager.Augments.getAugmentSlot(o.getAsString());
    }

    @Override
    protected JsonElement vSerialize(AugmentInstance augmentInstance) {
        return augmentInstance.toJson();
    }

    @Override
    protected AugmentInstance vDeserialize(JsonElement o) {
        return AugmentInstance.fromJson(o.getAsJsonObject());
    }

    @Override
    protected JsonElement iSerialize(String s) {
        return new JsonPrimitive(s);
    }

    @Override
    protected String iDeserialize(JsonElement o) {
        return o.getAsString();
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
