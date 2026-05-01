package com.objects.organization;

import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.varswap.TimelineVarChange;
import com.google.gson.JsonElement;

import java.time.LocalDate;

public class OrgChange<T extends DateMutableEntity<T> & IOrganizedEntity<T>> extends TimelineVarChange<T, DMEReference<? extends AbstractOrganization<?>>> {
    protected OrgChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }

    protected OrgChange(DMEReference<? extends T> owner, LocalDate date, DMEReference<? extends AbstractOrganization<?>> changed) {
        super(owner, date, changed);
    }

    @Override
    public DMEReference<? extends AbstractOrganization<?>> getCurrent(T owner) {
        return getOwner().get().getOrganization();
    }

    @Override
    public void setNew(T entity, DMEReference<? extends AbstractOrganization<?>> newValue) {
        getOwner().get().internalSetOrg(newValue);
    }

    @Override
    public void onVariableLink(T entity, DMEReference<? extends AbstractOrganization<?>> changed, DMEReference<? extends AbstractOrganization<?>> former) {

    }

    @Override
    protected JsonElement serializeO(DMEReference<? extends AbstractOrganization<?>> o) {
        return o.serialize();
    }

    @Override
    protected DMEReference<? extends AbstractOrganization<?>> deserializeO(JsonElement json) {
        return DMEReference.deserialize(json.getAsJsonObject());
    }
}
