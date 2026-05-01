package com.objects.culture.object.change;

import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.varswap.TimelineVarChange;
import com.google.gson.JsonElement;
import com.objects.culture.Culture;
import com.objects.culture.object.CultureObject;

import java.time.LocalDate;

public class CultureChange <T extends DateMutableEntity<T> & CultureObject<T>> extends TimelineVarChange<T, DMEReference<Culture>> {
    protected CultureChange(DMEReference<? extends T> owner, LocalDate date, DMEReference<Culture> changed) {
        super(owner, date, changed);
    }
    public CultureChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }

    @Override
    public DMEReference<Culture> getCurrent(T owner) {
        return getOwner().get().getCulture();
    }

    @Override
    public void setNew(T entity, DMEReference<Culture> newValue) {
        getOwner().get().internalSetCulture(newValue);
    }

    @Override
    public void onVariableLink(T entity, DMEReference<Culture> changed, DMEReference<Culture> former) {
        if(changed != null){
            changed.get().linkFollower(entity.getReference());
        }
        if(former != null){
            former.get().unlinkFollower(entity.getReference());
        }
    }

    @Override
    protected JsonElement serializeO(DMEReference<Culture> o) {
        return o.serialize();
    }

    @Override
    protected DMEReference<Culture> deserializeO(JsonElement json) {
        return DMEReference.deserialize(json.getAsJsonObject());
    }
}
