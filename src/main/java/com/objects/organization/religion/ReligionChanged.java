package com.objects.organization.religion;

import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.varswap.TimelineVarChange;
import com.google.gson.JsonElement;
import com.objects.culture.object.CultureObject;

import java.time.LocalDate;

public class ReligionChanged<T extends DateMutableEntity<T> & IReligious<T>> extends TimelineVarChange<T, DMEReference<Religion>> {

    protected ReligionChanged(DMEReference<? extends T> owner, LocalDate date, DMEReference<Religion> changed) {
        super(owner, date, changed);
    }

    protected ReligionChanged(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }

    @Override
    public DMEReference<Religion> getCurrent(T owner) {
        return getOwner().get().getReligion();
    }

    @Override
    public void setNew(T entity, DMEReference<Religion> newValue) {
        getOwner().get().internalSetReligion(newValue);
    }

    @Override
    public void onVariableLink(T entity, DMEReference<Religion> changed, DMEReference<Religion> former) {
        if (entity instanceof CultureObject<?> co) {
            if (former != null) {
                former.get().unlinkFollower(co.getReference());
            }
            if (changed != null) {
                changed.get().linkFollower(co.getReference());
            }
        }
    }

    @Override
    protected JsonElement serializeO(DMEReference<Religion> o) {
        return o.serialize();
    }

    @Override
    protected DMEReference<Religion> deserializeO(JsonElement json) {
        return DMEReference.deserialize(json.getAsJsonObject());
    }
}
