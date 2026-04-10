package com.objects.culture.tenet.instance;

import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeID;
import com.objects.culture.change.CultureMapChanges;
import com.objects.title.land.HabitableLand;
import com.objects.title.land.changes.LandMapChanges;

public class LandTenetInstance<T extends HabitableLand<T>> extends TenetInstance<LandTenetInstance<T>, LandMapChanges.LandCultureInstanceChange, T>{
    @Override
    protected LandTenetInstance findE(DMEReference<HabitableLand<?>> ref, ChangeID current) {
        LandMapChanges.LandCultureInstanceChange c = getC(ref, getThisChange());
        return c.getFromFuture(current, this);
    }
}
