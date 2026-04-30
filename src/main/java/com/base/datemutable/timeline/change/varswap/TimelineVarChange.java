package com.base.datemutable.timeline.change.varswap;

import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.TimelineSingleChange;
import com.base.datemutable.timeline.state.TimelineState;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.util.Optional;

public abstract class TimelineVarChange<T extends DateMutableEntity<T>,O> extends TimelineSingleChange<T> {
    private O changed;
    private Optional<O> old;
    protected TimelineVarChange(DMEReference<? extends T> owner, LocalDate date, O changed) {
        super(owner, date);
        this.changed = changed;
        old = Optional.ofNullable(getCurrent());
    }
    protected TimelineVarChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
        this.changed = null;
        old = Optional.ofNullable(null);
    }
    public final O getCurrent(){
        return getCurrent(getOwner().get());
    }
    public abstract O getCurrent(T owner);
    public abstract void setNew(T entity, O newValue);
    @Override
    protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
        setNew(entity.get(), changed);
    }
    public final Optional<O> getOld(){
        return old;
    }
    public final O getNew(){
        return changed;
    }
    @Override
    protected String getText() {
        return "";
    }
    protected abstract JsonElement serializeO(O o);
    protected abstract O deserializeO(JsonElement json);
    @Override
    public void additionalSave(JsonObject data) {
        data.add("change",serializeO(changed));
        if (old.isPresent()) {
            data.add("old",serializeO(old.get()));
        }
    }

    @Override
    public void additionalLoad(JsonObject data) {
        changed = deserializeO(data.get("change"));
        old = Optional.ofNullable(deserializeO(data.get("old")));
    }
}
