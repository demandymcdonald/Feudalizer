package com.objects.title.land.resources.node;

import com.base.reference.DMEReference;
import com.objects.title.land.AbstractLandDivision;
import com.objects.title.land.habitable.HabitableLand;
import com.objects.title.land.resources.GoodToken;
import com.utilities.id.UUIDIdentifiable;

import java.util.Set;

public interface INode<T extends HabitableLand<?>> extends UUIDIdentifiable {
    DMEReference<T> getHost();
    Set<GoodToken> activeTokenRequirement();
    Set<GoodToken> activeTokenProduction();
    boolean isActive();
    default Set<GoodToken> getTokenCost(){
        if(isActive()){
            return activeTokenRequirement();
        } else {
            return Set.of();
        }
    }
    default Set<GoodToken> getTokenProduction(){
        if(isActive()){
            return activeTokenProduction();
        } else {
            return Set.of();
        }
    }
}
