package com.objects.organization.labor;

import com.base.datemutable.DateMutableEntity;
import com.base.datemutable.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.datemutable.timeline.change.multi.TimelineSetChange;
import com.base.datemutable.timeline.change.multi.condition.MultiCondition;
import com.base.datemutable.timeline.change.multi.wrapper.TLSet;
import com.base.datemutable.timeline.state.TimelineState;
import com.base.reference.DMEReference;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.objects.organization.government.IGoverned;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class LocalChange<T extends DateMutableEntity<T> & IUnionizable<T> & IGoverned<T>> extends TimelineSetChange<LocalChange<T>,UnionLocal, UUID,T> {
    protected LocalChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }

    protected LocalChange(DMEReference<? extends T> owner, LocalDate date, Set<UnionLocal> initial) {
        super(owner, date, initial);
    }

    @Override
    public TLSet<UnionLocal> getRuntimeSet() {
        return getOwner().get().getLocals();
    }

    @Override
    public void setRuntimeSet(TLSet<UnionLocal> set) {
        getOwner().get().internalSetLocals(set);
    }

    @Override
    public LocalChange<T> getEmptyChange(DMEReference<? extends T> owner, LocalDate date) {
        return new LocalChange<>(owner, date);
    }

    @Override
    public void conditionsWipeForward(List<MultiCondition<LocalChange<T>, UnionLocal, Boolean, UUID, T>> current) {

    }

    @Override
    public void conditionsWipeBackward(List<MultiCondition<LocalChange<T>, UnionLocal, Boolean, UUID, T>> current) {

    }

    @Override
    protected JsonElement kSerialize(UnionLocal unionLocal) {
        return unionLocal.toJson();
    }

    @Override
    protected UnionLocal kDeserialize(JsonElement o) {
        UnionLocal unionLocal = new UnionLocal();
        unionLocal.fromJson(o);
        return unionLocal;
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
