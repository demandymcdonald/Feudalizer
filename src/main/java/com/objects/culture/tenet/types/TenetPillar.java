package com.objects.culture.tenet.types;

import com.base.timeline.change.TimelineChange;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.TenetCondition;
import com.objects.culture.tenet.compass.CompassEntry;
import com.objects.culture.tenet.group.TenetGroup;

import java.util.HashMap;
import java.util.Map;

public abstract class TenetPillar<CP extends TenetPillar<CP>> extends Tenet<CP> {
    private final Multimap<TenetGroup, Tenet<?>> children = HashMultimap.create();
    public TenetPillar(TenetGroup group, CompassEntry ce, String id, String name, String description) {
        super(group,ce, id, name, description);
    }

    public CP add(Tenet<?> child){
        if (child.getGroup().isChildOf(this.getGroup())) {
            children.put(child.getGroup(), child);
        } else {
            throw new RuntimeException("Cannot add an unrelated child to cultural pillar: "+ this.getID());
        }
        return (CP) this;
    }

    @Override
    protected Multimap<Class<? extends TimelineChange<?>>, TenetCondition<?, ?, ?>> conditions() {
        Multimap<Class<? extends TimelineChange<?>>, TenetCondition<?, ?, ?>> map = HashMultimap.create();
        for (Tenet<?> child : children.values()) {
            map.putAll(child.getAllConditions());
        }
        return map;
    }

    @Override
    protected Map<Tenet<?>, Acceptance> related() {
        Map<Tenet<?>, Acceptance> map = new HashMap<>();
        for (Tenet<?> child : children.values()) {
            map.putAll(child.getRelated());
        }
        return map;
    }
}
