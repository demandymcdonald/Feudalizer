package com.objects.culture.object.change;

import com.base.datemutable.DateMutableEntity;
import com.base.datemutable.timeline.change.multi.condition.MultiCondition;
import com.base.datemutable.timeline.change.multi.wrapper.TLMap;
import com.base.datemutable.timeline.variable.EasingChange;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.datemutable.timeline.change.multi.TimelineMapChange;
import com.base.datemutable.timeline.state.TimelineState;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.objects.culture.Influencers.InfluencerInstance;
import com.objects.culture.object.CultureObject;
import com.objects.culture.object.reference.COReference;
import com.objects.culture.tenet.instance.TenetInstance;
import com.utilities.serialization.RegistrySerialManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

public class InfluencerMapChange<T extends DateMutableEntity<T> & CultureObject<T>> extends TimelineMapChange<InfluencerMapChange<T>, COReference<?>, InfluencerInstance, UUID, T>
implements EasingChange<TenetInstance<T>, InfluencerMapChange<T>,T> {

    public InfluencerMapChange(DMEReference<? extends T> owner, LocalDate date, Map<COReference<?>, InfluencerInstance> initial) {
        super(owner, date, initial);
    }

    protected InfluencerMapChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }

    @Override
    protected TLMap<COReference<?>, InfluencerInstance> getRuntimeMap(T owner) {
        return owner.getInfluencers();
    }

    @Override
    public void setRuntimeMap(T owner, TLMap<COReference<?>, InfluencerInstance> map) {
        owner.internalSetInfluencers(map);
    }


    @Override
    public boolean hasEndingChanges() {
        return true;
    }

    @Override
    public void conditionsWipeForward(List<MultiCondition<InfluencerMapChange<T>, COReference<?>, InfluencerInstance, UUID, T>> current) {

    }

    @Override
    public void conditionsWipeBackward(List<MultiCondition<InfluencerMapChange<T>, COReference<?>, InfluencerInstance, UUID, T>> current) {

    }

    @Override
    protected JsonElement kSerialize(COReference<?> toReference) {
        return toReference.serialize();
    }

    @Override
    protected COReference<?> kDeserialize(JsonElement o) {
        return COReference.deserialize(o.getAsJsonObject());
    }

    @Override
    protected JsonElement vSerialize(InfluencerInstance influencerInstance) {
        return influencerInstance.serialize();
    }

    @Override
    protected InfluencerInstance vDeserialize(JsonElement o) {
        return RegistrySerialManager.deserialize(o.getAsJsonObject());
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
        return "influencer_map_change";
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

    @Override
    public TenetInstance<T> getEasingVariable(Predicate<TenetInstance<T>> matching) {
        for (InfluencerInstance instance : getRuntimeMap().getValues()){
            if()
        }
        return ;
    }

    @Override
    public InfluencerMapChange<T> getNext() {
        return null;
    }
}
