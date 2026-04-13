package com.objects.culture.tenet.types;

import com.base.timeline.change.TimelineChange;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.TenetCondition;
import com.objects.culture.tenet.reference.TenetReference;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.group.TenetGroup;

import java.util.HashMap;
import java.util.Map;

public abstract class TenetPillar<CP extends TenetPillar<CP>> extends MutableTenet {
    private final Multimap<TenetGroup, TenetReference> children = HashMultimap.create();
    public TenetPillar(TenetGroup group, PoliticalCompass ce, String id, String name, String description) {
        super(group,ce, id, name, description);
    }

    public CP add(TenetReference childRef){
        MutableTenet child = childRef.tenet.get();
        if (child.getGroup().isChildOf(this.getGroup())) {
            children.put(child.getGroup(), childRef);
        } else {
            throw new RuntimeException("Cannot add an unrelated child to cultural pillar: "+ this.getDisplayID());
        }
        return (CP) this;
    }

    @Override
    protected Multimap<Class<? extends TimelineChange<?>>, TenetCondition<?, ?, ?>> conditions() {
        Multimap<Class<? extends TimelineChange<?>>, TenetCondition<?, ?, ?>> map = HashMultimap.create();
        for (MutableTenet child : children.values()) {
            map.putAll(child.getAllConditions());
        }
        return map;
    }

    @Override
    protected Map<TenetReference, Acceptance> related() {
        Map<TenetReference, Acceptance> map = new HashMap<>();
        for (TenetReference child : children.values()) {
            map.putAll(child.getRelated());
        }
        return map;
    }
}
