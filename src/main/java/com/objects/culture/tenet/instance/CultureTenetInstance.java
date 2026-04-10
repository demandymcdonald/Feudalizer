package com.objects.culture.tenet.instance;

import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeID;
import com.objects.culture.Culture;
import com.objects.culture.change.CultureMapChanges;

public class CultureTenetInstance extends TenetInstance<CultureTenetInstance, CultureMapChanges.TenetMapChange, Culture> {
    @Override
    protected CultureTenetInstance findE(DMEReference<Culture> ref, ChangeID current) {
        CultureMapChanges.TenetMapChange c = getC(ref, getThisChange());
        return c.getFromFuture(current, this);
    }
}
