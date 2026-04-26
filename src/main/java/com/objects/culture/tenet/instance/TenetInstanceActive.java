package com.objects.culture.tenet.instance;

import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.TimelineSingleChange;
import com.base.datemutable.timeline.change.condition.apply.ApplyCondition;
import com.base.datemutable.timeline.state.TimelineState;
import com.google.gson.JsonObject;
import com.objects.culture.object.CultureObject;

import java.time.LocalDate;
import java.util.List;

public class TenetInstanceActive<T extends DateMutableEntity<T> & CultureObject<T>> extends TimelineSingleChange<T> {
    protected TenetInstanceActive(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }

    @Override
    protected void applyConditions(List<ApplyCondition<? super T>> list) {
        super.applyConditions(list);

    }

    @Override
    protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {

    }

    @Override
    protected String getText() {
        return "";
    }

    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }
}
