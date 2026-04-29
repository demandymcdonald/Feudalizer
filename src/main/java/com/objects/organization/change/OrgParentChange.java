package com.objects.organization.change;

import com.Global.*;
import com.base.datemutable.timeline.change.varswap.TimelineVarChange;
import com.base.reference.DMEReference;
import com.google.gson.JsonElement;
import com.objects.organization.AbstractOrganization;
import org.apache.commons.math3.analysis.function.Abs;

import java.time.LocalDate;

public class OrgParentChange<T extends AbstractOrganization<T>> extends TimelineVarChange<T, DMEReference<? extends AbstractOrganization<?>>> {
    protected OrgParentChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }

    public OrgParentChange(DMEReference<? extends T> owner, LocalDate date, DMEReference<? extends AbstractOrganization<?>> changed) {
        super(owner, date, changed);
    }

    @Override
    public DMEReference<? extends AbstractOrganization<?>> getCurrent() {
        return getOwner().get().getParent().orElse(null);
    }

    @Override
    public void setNew(DMEReference<? extends AbstractOrganization<?>> newValue) {
        getOwner().get().internalSetParent(newValue);
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
