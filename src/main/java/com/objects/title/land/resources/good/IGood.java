package com.objects.title.land.resources.good;

import com.utilities.IDisplayable;

import java.util.Set;

public interface IGood extends IDisplayable {
    Set<GoodTag> getTags();
    long popImpactProduction();
    long popImpactConsumption();
    default GoodToken instance(int quantity){
        return new GoodToken(this,quantity);
    }
}
