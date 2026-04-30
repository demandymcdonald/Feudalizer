package com.objects.organization.government;

import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.varswap.TimelineVarChange;
import com.google.gson.JsonElement;

import java.time.LocalDate;

public class GovernmentChanged<T extends DateMutableEntity<T> & IGoverned<T>> extends TimelineVarChange<T, DMEReference<? extends GoverningEntity<?>>> {
    protected GovernmentChanged(DMEReference<? extends T> owner, LocalDate date, DMEReference<? extends GoverningEntity<?>> changed) {
        super(owner, date, changed);
    }

    protected GovernmentChanged(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }

    @Override
    public DMEReference<? extends GoverningEntity<?>> getCurrent(T owner) {
        return getOwner().get().getGovernment();
    }

    @Override
    public void setNew(T entity, DMEReference<? extends GoverningEntity<?>> newValue) {
        getOwner().get().internalSetGovernment(newValue);
    }

    @Override
    protected JsonElement serializeO(DMEReference<? extends GoverningEntity<?>> o) {
        return ;
    }

    @Override
    protected DMEReference<? extends GoverningEntity<?>> deserializeO(JsonElement json) {
        return null;
    }
}
