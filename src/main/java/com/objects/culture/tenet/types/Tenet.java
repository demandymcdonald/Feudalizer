package com.objects.culture.tenet.types;

import com.base.DateMutableEntity;
import com.base.timeline.change.TimelineChange;
import com.google.common.base.Suppliers;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.TenetCondition;
import com.objects.culture.tenet.TenetManager;
import com.objects.culture.tenet.TenetReference;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.group.TenetGroup;
import com.utilities.Displayable;
import com.utilities.id.StringIdentifiable;

import java.util.*;
import java.util.function.Supplier;

public abstract class Tenet implements Displayable, StringIdentifiable {
    private final TenetReference reference;
    private final TenetGroup group;
    private final String id;
    private final String name;
    private final String description;
    private final Supplier<Map<TenetReference, Acceptance>> related = Suppliers.memoize(() -> {return new HashMap<>(related());});
    private final PoliticalCompass entry;
    private final Supplier<Multimap<Class<? extends TimelineChange<?>>, TenetCondition<?,?,?>>> condition = Suppliers.memoize(() -> {return HashMultimap.create(conditions());});
    public Tenet(TenetGroup group, PoliticalCompass entry, String id, String name, String description) {
        this.group = group;
        this.id = buildID(group, id);
        this.name = name;
        this.description = description;
        this.entry = entry;
        reference = TenetReference.of((TE) this);
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
    protected abstract Map<TenetReference, Acceptance> related();
    @Override
    public String getDisplayID() {
        return id;
    }
    @Override
    public String getID(){
        return id;
    }
    @Override
    public String displayName() {
        return description;
    }
    public PoliticalCompass getCompassEntry(){
        return entry;
    }
    @Override
    public String description() {
        return name;
    }
    public TenetGroup getGroup() {
        return group;
    }
    public Acceptance getRelationship(TenetReference tenet){
        Map<TenetReference, Acceptance> map = related.get();
        return map.getOrDefault(tenet, Acceptance.NEUTRAL);
    }
    public Multimap<Class<? extends TimelineChange<?>>, TenetCondition<?,?,?>> getAllConditions() {
        return condition.get();
    }
    public Map<TenetReference, Acceptance> getRelated(){
        return related.get();
    }
    private static String buildID(TenetGroup group, String id){
        return group.getDisplayID() + "." + id;
    }

}
