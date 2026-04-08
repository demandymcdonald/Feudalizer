package com.objects.culture.tenet.types;

import com.base.DateMutableEntity;
import com.base.timeline.change.TimelineChange;
import com.google.common.base.Suppliers;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.TenetCondition;
import com.objects.culture.tenet.TenetManager;
import com.objects.culture.tenet.compass.CompassEntry;
import com.objects.culture.tenet.group.TenetGroup;
import com.utilities.Displayable;

import java.util.*;
import java.util.function.Supplier;

public abstract class Tenet<TE extends Tenet<TE>> implements Displayable {
    private final TenetGroup group;
    private final String id;
    private final String name;
    private final String description;
    private final Supplier<Map<Tenet<?>, Acceptance>> related = Suppliers.memoize(() -> {return new HashMap<>(related());});
    private final CompassEntry entry;
    private final Supplier<Multimap<Class<? extends TimelineChange<?>>, TenetCondition<?,?,?>>> condition = Suppliers.memoize(() -> {return HashMultimap.create(conditions());});
    public Tenet(TenetGroup group, CompassEntry entry, String id, String name, String description) {
        this.group = group;
        this.id = id;
        this.name = name;
        this.description = description;
        this.entry = entry;
        TenetManager.registerTenet(this);
    }
    public <T extends DateMutableEntity<T>, C extends TimelineChange<T>> List<TenetCondition<? super C,? extends T,?>>  getConditions(TimelineChange<T> change){
        List<TenetCondition<? super C, ? extends T,?>> conditions = new ArrayList<>();
        for (TenetCondition<?,?,?> lCondition : condition.get().get((Class<? extends TimelineChange<?>>) change.getClass())){
            conditions.add((TenetCondition<? super C, ? extends T,TE>) lCondition);
        };
        return conditions;
    }
    protected abstract Multimap<Class<? extends TimelineChange<?>>,TenetCondition<?,?,?>> conditions();
    protected abstract Map<Tenet<?>, Acceptance> related();
    @Override
    public String getID() {
        return id;
    }

    @Override
    public String displayName() {
        return description;
    }
    public CompassEntry getCompassEntry(){
        return entry;
    }
    @Override
    public String description() {
        return name;
    }
    public TenetGroup getGroup() {
        return group;
    }
    public Acceptance getRelationship(Tenet<?> tenet){
        Map<Tenet<?>, Acceptance> map = related.get();
        return map.getOrDefault(tenet, Acceptance.NEUTRAL);
    }
    public Multimap<Class<? extends TimelineChange<?>>, TenetCondition<?,?,?>> getAllConditions() {
        return condition.get();
    }
    public Map<Tenet<?>, Acceptance> getRelated(){
        return related.get();
    }


}
