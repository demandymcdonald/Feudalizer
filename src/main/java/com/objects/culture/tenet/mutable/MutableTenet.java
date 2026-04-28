package com.objects.culture.tenet.mutable;

import com.base.reference.DMEReference;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonObject;
import com.objects.culture.Culture;
import com.objects.culture.object.ICultureObject;
import com.objects.culture.object.compass.IPoliticalCompass;
import com.objects.culture.tenet.AcceptanceContainer;
import com.objects.culture.tenet.Tenet;
import com.objects.culture.TenetManager;
import com.objects.culture.tenet.factory.CultureCondition;
import com.objects.culture.tenet.TenetReference;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.group.CategoryModifier;
import com.objects.culture.tenet.group.TenetGroup;
import com.utilities.number.BoundDbl;
import com.utilities.serialization.SuperclassSerializable;
import javafx.scene.chart.Axis;

import javax.annotation.Nullable;
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
    public final Multimap<CultureCondition.Key, CultureCondition<?, ?>> getConditions() {
        Multimap<CultureCondition.Key, CultureCondition<?, ?>> result = HashMultimap.create();
        List<CultureCondition<?, ?>> conditions = new ArrayList<>();
        for (CultureCondition<?, ?> condition : getConditions().values()) {
            for (CultureCondition.Key key : condition.getKeys()) {
                result.put(key,condition);
            }
        }
        return result;
    }
    public abstract Set<CultureCondition<?,?>> getConditionList();
    private static String buildID(TenetGroup group, String id){
        return group.getDisplayID() + "." + id;
    }
    public abstract Set<TenetGroup> compatibleParents();
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
    public String getDisplayName() {
        return name;
    }

    @Override
    public String getDescription() {
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
    public AcceptanceContainer getAcceptanceObject(ICultureObject other, boolean includeInfluencers, boolean factorOtherTolerance) {
        return politicalCompass.getAcceptanceContainer(other.getCompass(), factorOtherTolerance);
    }
    @Override
    public DMEReference<Culture> getCulture() {
        return parent.get().getCulture();
    }

    @Override
    public void mainLoad(JsonObject object) {

    }
    public static PoliticalCompass makeCompass(Set<PoliticalCompass.IdeologyEntry> opinions, @Nullable TenetGroup reference, @Nullable CategoryModifier modifiers){
        PoliticalCompass compass = PoliticalCompass.of(opinions);
        return makeCompass(compass, reference, modifiers);
    }
    public static PoliticalCompass makeCompass(PoliticalCompass base, @Nullable TenetGroup reference, @Nullable CategoryModifier modifiers){
        if (modifiers == null || reference == null) return base;
        return factorModifiers(base,reference, modifiers);
    }
    protected static PoliticalCompass factorModifiers(PoliticalCompass compass, TenetGroup reference, CategoryModifier modifiers){
//        if(!modifiers.contains(reference)){
//            return compass;
//        }
        for (Map.Entry<TenetGroup, BoundDbl> entry : modifiers.modifiers().entrySet()) {
            TenetGroup group = entry.getKey();
            if(group.equals(reference) || group.isAncestorOf(reference)){
                double mod = modifiers.getModifier(group);
                for(IPoliticalCompass.Axis axis : IPoliticalCompass.Axis.values()){
                    compass.setCompass(axis,(int) Math.round(compass.getByAxis(axis).get() * mod));
                }
            }
        }
        return compass;
    }
}
