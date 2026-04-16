package com.objects.culture.object.change;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.timeline.change.condition.nullify.NullifyCondition;
import com.base.timeline.change.multi.TimelineMap;
import com.base.timeline.change.multi.TimelineMapChange;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.objects.culture.Influencers.InfluencerInstance;
import com.objects.culture.object.CultureObject;
import com.objects.culture.object.COReference;
import com.utilities.serialization.RegistrySerialManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InfluencerMapChange<T extends DateMutableEntity<T> & CultureObject<T>> extends TimelineMapChange<InfluencerMapChange<T>, COReference<?>, InfluencerInstance, UUID, T> {

    public InfluencerMapChange(DMEReference<? extends T> owner, LocalDate date, Map<COReference<?>, InfluencerInstance> initial) {
        super(owner, date, initial);
    }

    protected InfluencerMapChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }


    @Override
    public TimelineMap<COReference<?>, InfluencerInstance, T> getRuntimeMap() {
        return getOwner().get().getInfluencers();
    }

    @Override
    public void setRuntimeMap(TimelineMap<COReference<?>, InfluencerInstance, T> map) {
        getOwner().get().internalSetInfluencers(map);
    }

    @Override
    public boolean hasEndingChanges() {
        return true;
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
    protected void nullifyConditions(List<NullifyCondition<? super T>> list) {

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
