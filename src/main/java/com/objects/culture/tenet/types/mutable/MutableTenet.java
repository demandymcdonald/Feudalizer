package com.objects.culture.tenet.types.mutable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonObject;
import com.objects.culture.Culture;
import com.objects.culture.object.compass.IPoliticalCompass;
import com.objects.culture.tenet.Tenet;
import com.objects.culture.tenet.TenetManager;
import com.objects.culture.tenet.factory.CultureCondition;
import com.objects.culture.tenet.types.TenetReference;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.group.TenetGroup;
import com.utilities.serialization.SuperclassSerializable;

import java.util.*;

public abstract class MutableTenet implements Tenet, SuperclassSerializable<MutableTenet> {

    private final UUID id;
    private final TenetReference reference;
    private final TenetGroup group;
    private final PoliticalCompass politicalCompass;
    private final String displayID;
    private final String name;
    private final String description;
    private final TenetReference parent;
    public MutableTenet(TenetReference parent, TenetGroup group, PoliticalCompass entry, String id, String name, String description) {
        this.id = UUID.randomUUID();
        this.group = group;
        this.displayID = buildID(group, id);
        this.politicalCompass = entry;
        this.name = name;
        this.description = description;
        reference = TenetReference.of(this);
        this.parent = parent;
        TenetManager.registerTenet(this);
    }
    public MutableTenet(TenetReference parent, UUID uuid,TenetGroup group, PoliticalCompass entry, String id, String name, String description) {
        this.id = uuid;
        this.group = group;
        this.politicalCompass = entry;
        this.displayID = buildID(group, id);
        this.name = name;
        this.description = description;
        reference = TenetReference.of(this);
        this.parent = parent;
        TenetManager.registerTenet(this);
    }
//    public <T extends DateMutableEntity<T>, C extends TimelineChange<T>> List<TenetCondition<? super C,? extends T,?>>  getConditions(TimelineChange<T> change){
//        List<TenetCondition<? super C, ? extends T,?>> conditions = new ArrayList<>();
//        for (TenetCondition<?,?,?> lCondition : condition.get().get((Class<? extends TimelineChange<?>>) change.getClass())){
//            conditions.add((TenetCondition<? super C, ? extends T,TE>) lCondition);
//        };
//        return conditions;
//    }
    @Override
    public final Multimap<CultureCondition.Key, CultureCondition<?, ?, ?>> getConditions() {
        Multimap<CultureCondition.Key, CultureCondition<?, ?, ?>> result = HashMultimap.create();
        List<CultureCondition<?, ?, ?>> conditions = new ArrayList<>();
        for (CultureCondition<?, ?, ?> condition : getConditions().values()) {
            for (CultureCondition.Key key : condition.getKeys()) {
                result.put(key,condition);
            }
        }
        return result;
    }
    public abstract List<CultureCondition<?,?,?>> getConditionList();
    private static String buildID(TenetGroup group, String id){
        return group.getDisplayID() + "." + id;
    }
    public abstract List<TenetGroup> compatibleParents();
    @Override
    public TenetReference getTenetReference() {
        return reference;
    }

    @Override
    public TenetGroup getGroup() {
        return group;
    }

    @Override
    public IPoliticalCompass getCompass() {
        return politicalCompass;
    }

    @Override
    public String getDisplayID() {
        return displayID;
    }

    @Override
    public String displayName() {
        return name;
    }

    @Override
    public String description() {
        return description;
    }
    @Override
    public UUID getID() {
        return id;
    }
    @Override
    public void mainSave(JsonObject object) {
        object.addProperty("uuid",id.toString());
        object.addProperty("displayID", displayID);
        object.addProperty("name", name);
        object.addProperty("description", description);
        object.addProperty("group", group.getDisplayID());
        object.add("parent",parent.serialize());
        object.add("compass", politicalCompass.toJson());
    }

    @Override
    public Culture getCulture() {
        return parent.get().getCulture();
    }

    @Override
    public void mainLoad(JsonObject object) {

    }

}
