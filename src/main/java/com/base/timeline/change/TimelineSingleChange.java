package com.base.timeline.change;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.google.gson.JsonObject;

import java.time.LocalDate;

public abstract class TimelineSingleChange<T extends DateMutableEntity<T>> extends TimelineChange<T> {
    protected TimelineSingleChange(DMEReference<T> owner, LocalDate date) {
        super(owner, date);
    }


    @Override
    public final void mainSave(JsonObject o) {
        super.mainSave(o);
    }

    @Override
    public final void mainLoad(JsonObject object) {
        super.mainLoad(object);
    }
}
