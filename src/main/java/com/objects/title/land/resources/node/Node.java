package com.objects.title.land.resources.node;

import com.base.reference.DMEReference;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.objects.title.land.AbstractLandDivision;
import com.objects.title.land.habitable.HabitableLand;
import com.objects.title.land.resources.GoodToken;
import com.objects.title.land.resources.IGood;
import com.utilities.IDisplayable;
import com.utilities.hierarchy.StateIntegrity;
import com.utilities.id.StringIdentifiable;
import com.utilities.id.UUIDIdentifiable;
import org.apache.commons.lang3.mutable.MutableLong;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public abstract class Node<T extends HabitableLand<?>> implements StringIdentifiable, IDisplayable, StateIntegrity {
    DMEReference<T> host;
    UUID runtimeID;
    public Node(DMEReference<T> host) {
        this.host = host;
        runtimeID = UUID.randomUUID();
    }
    public final UUID getRuntimeID() {
        return runtimeID;
    }
    public final DMEReference<T> getHost(){
        return host;
    };
    protected abstract Map<IGood,Integer> activeTokenRequirement();
    protected abstract Map<IGood,Integer> activeTokenProduction();
    public abstract boolean isActive();
    public final Set<GoodToken> getTokenCost(){
        if(isActive()){
            return activeTokenRequirement();
        } else {
            return Set.of();
        }
    }
    public final Set<GoodToken> getTokenProduction(){
        if(isActive()){
            return activeTokenProduction();
        } else {
            return Set.of();
        }
    }
    @Override
    public final long getCurrent(){
        Hasher hash = Hashing.murmur3_128().newHasher();
        hash.putLong(getHost().getID().getMostSignificantBits());
        hash.putLong(getHost().getID().getLeastSignificantBits());
        hash.putLong(isActive() ? 1 : 0);
        return hash.hash().asLong();
    };
}
