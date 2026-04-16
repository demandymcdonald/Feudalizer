package com.objects.culture.tenet.instance;

import com.Global;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.TimelineObject;
import com.base.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.timeline.change.condition.nullify.NullifyCondition;
import com.base.timeline.change.multi.TimelineMap;
import com.base.timeline.change.multi.TimelineMapChange;
import com.base.timeline.state.TimelineState;
import com.base.timeline.variable.EasingChange;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.objects.culture.object.CultureObject;
import com.objects.culture.tenet.types.TenetReference;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;
//public class TenetInstanceChange<M extends TenetInstanceChange<M,T,TI>,T extends DateMutableEntity<T> & CultureObject<T>,
//        TI extends TenetInstance<TI,T,M>> extends TimelineMapChange<M, TenetReference,TI,String,T> implements EasingChange<TI,M,T> {
public class TenetInstanceChange<T extends DateMutableEntity<T> & CultureObject<T>>
        extends TimelineMapChange<TenetInstanceChange<T>,TenetReference,TenetInstance<T>, UUID,T>
        implements EasingChange<TenetInstance<T>,TenetInstanceChange<T>,T> {

    protected TenetInstanceChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }

    @Override
    protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {

    }

    @Override
    protected String getText() {
        return "tenet_instance_change";
    }

    @Override
    protected void nullifyConditions(List<NullifyCondition<? super T>> list) {

    }

    @Override
    protected void deactivateConditions(List<DeactivateCondition<? super T>> list) {

    }

    @Override
    public boolean hasEndingChanges() {
        return true;
    }

    @Override
    protected JsonElement kSerialize(TenetReference tenetReference) {
        return tenetReference.serialize();
    }

    @Override
    protected TenetReference kDeserialize(JsonElement o) {
        return TenetReference.deserialize(o.getAsJsonObject());
    }

    @Override
    protected JsonElement vSerialize(TenetInstance<T> tTenetInstance) {
        return tTenetInstance.serialize();
    }

    @Override
    protected TenetInstance<T> vDeserialize(JsonElement o) {
        TenetInstance<T> t = new TenetInstance<>();
        t.deserialize(o.getAsJsonObject());
        return t;
    }

    @Override
    protected JsonElement iSerialize(UUID s) {
        return new JsonPrimitive(s.toString());
    }

    @Override
    protected UUID iDeserialize(JsonElement o) {
        return UUID.fromString(o.getAsString());
    }

    @Override
    public void setRuntimeMap(TimelineMap<TenetReference, TenetInstance<T>,T> map) {
        getOwner().get().internalSetOpinions(map);
    }

    @Override
    public TimelineMap<TenetReference, TenetInstance<T>,T> getRuntimeMap() {
        return getOwner().get().getOpinions();
    }
    @Override
    public TenetInstanceChange<T> getNext(){
        return TimelineObject.getChangeStep(this, Global.TimeDirection.FORWARD,false,1,null);
    }


    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }

    @Override
    public TenetInstance<T> getEasingVariable(Predicate<TenetInstance<T>> matching) {
        for(Map.Entry<TenetReference, TenetInstance<T>> entry : getFullMap().entrySet()) {
            if(matching.test(entry.getValue())) {
                return entry.getValue();
            }
        }
        return null;
    }
}
