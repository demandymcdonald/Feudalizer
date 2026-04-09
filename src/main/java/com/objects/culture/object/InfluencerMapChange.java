package com.objects.culture.object;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.condition.apply.ApplyCondition;
import com.base.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.timeline.change.condition.nullify.NullifyCondition;
import com.base.timeline.change.map.TimelineMapChange;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.objects.culture.Influencers.InfluencerInstance;
import com.objects.culture.instance.TOReference;
import com.utilities.serialization.RegistrySerialManager;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class InfluencerMapChange<T extends DateMutableEntity<T>> extends TimelineMapChange<InfluencerMapChange<T>, TOReference<?>, InfluencerInstance, T> {
    protected InfluencerMapChange(DMEReference<T> owner, LocalDate date) {
        super(owner, date);
    }

    @SafeVarargs
    protected InfluencerMapChange(DMEReference<T> owner, LocalDate date, Pair<TOReference<?>, InfluencerInstance>... changes) {
        super(owner, date, changes);
    }

    @Override
    protected Map<TOReference<?>, InfluencerInstance> getMapFromObject(DMEReference<? extends T> object) {
        if (object.get() instanceof CultureObject<?, ?, ?> to){
            return to.getInfluencers();
        } else {
            throw new IllegalArgumentException("Object must be a TenetOpinionated");
        }
    }

    @Override
    protected boolean hasEndingChanges() {
        return true;
    }

    @Override
    protected JsonElement serializeK(TOReference<?> toReference) {
        return toReference.serialize();
    }

    @Override
    protected TOReference<?> deserializeK(JsonElement m) {
        return TOReference.deserialize(m.getAsJsonObject());
    }

    @Override
    protected InfluencerInstance deserializeV(JsonElement m) {
        return RegistrySerialManager.deserialize(m.getAsJsonObject());
    }

    @Override
    protected JsonElement serializeV(InfluencerInstance influencerInstance) {
        return influencerInstance.serialize();
    }

    @Override
    protected String getText() {
        return "";
    }

    @Override
    protected void applyConditions(List<ApplyCondition<? super T>> list) {

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
