package com.objects.culture.tenet.pillar;

import com.base.timeline.change.TimelineChange;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.Tenet;
import com.objects.culture.tenet.TenetCondition;
import com.objects.culture.tenet.group.TenetGroup;

import java.util.HashMap;
import java.util.Map;

public abstract class CulturalPillar<CP extends CulturalPillar<CP,TG>, TG extends TenetGroup> extends Tenet<CP,TG> {
    Multimap<TenetGroup, Tenet<?,?>> children = HashMultimap.create();
    public CulturalPillar(TenetGroup group, String id, String name, String description) {
        super(group, id, name, description);
    }

    public CP addChild(Tenet<?,?> child){
        if (child.getGroup().isChildOf(this.getGroup())) {
            children.put(child.getGroup(), child);
        } else {
            throw new RuntimeException("Cannot add an unrelated child to cultural pillar: "+ this.getID());
        }
        return (CP) this;
    }

    @Override
    protected Multimap<Class<? extends TimelineChange<?>>, TenetCondition<?, ?, ?>> conditions() {
        Multimap<Class<? extends TimelineChange<?>>, TenetCondition<?, ?, ?>> map = ArrayListMultimap.create();
        for (Tenet<?,?> child : children.values()) {
            map.putAll(child.getAllConditions());
        }
    }

    @Override
    protected Map<Tenet<?, ?>, Acceptance> related() {
        Map<Tenet<?, ?>, Acceptance> map = new HashMap<>();
        for (Tenet<?,?> child : children.values()) {
            map.putAll(child.getRelated());
        }
        return map;
    }
}
