package com.objects.culture.tenet.mutable;

import com.base.component.InstanceType;
import com.base.component.mutable.MutableComponent;
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

public abstract class MutableTenet extends MutableComponent<MutableTenet> implements Tenet {
    private TenetReference reference;
    private TenetGroup group;
    private PoliticalCompass politicalCompass;
    private String displayID;
    private String name;
    private String description;
    private TenetReference parent;
    public MutableTenet(InstanceType type, TenetReference parent, TenetGroup group, PoliticalCompass entry, String id, String name, String description) {
        super(type,buildID(group,id));
        this.group = group;
        this.displayID = buildID(group, id);
        this.politicalCompass = entry;
        this.name = name;
        this.description = description;
        reference = TenetReference.of(this);
        this.parent = parent;
    }
    public MutableTenet(InstanceType type, String id){
        super(type,id);
    }

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
    public void additionalSave(JsonObject object) {
        object.addProperty("mt:displayID", displayID);
        object.addProperty("mt:name", name);
        object.addProperty("mt:description", description);
        object.addProperty("mt:group", group.getDisplayID());
        object.add("mt:parent",parent.serialize());
        object.add("mt:compass", politicalCompass.toJson());
    }
    @Override
    public void additionalLoad(JsonObject object) {
        displayID = object.get("mt:displayID").getAsString();
        name = object.get("mt:name").getAsString();
        description = object.get("mt:description").getAsString();
        group = TenetManager.Group.get(object.get("mt:group").getAsString());
        parent = TenetReference.deserialize(object.get("mt:parent").getAsJsonObject());
        politicalCompass = PoliticalCompass.build(object.get("mt:compass").getAsJsonObject());
    }
    @Override
    public AcceptanceContainer getAcceptanceObject(ICultureObject other, boolean includeInfluencers, boolean factorOtherTolerance) {
        return politicalCompass.getAcceptanceContainer(other.getCompass(), factorOtherTolerance);
    }
    @Override
    public DMEReference<Culture> getCulture() {
        return parent.get().getCulture();
    }

    public static PoliticalCompass makeCompass(PoliticalCompass base, Set<PoliticalCompass.IdeologyEntry> opinions, @Nullable TenetGroup reference, @Nullable CategoryModifier modifiers){
        PoliticalCompass compass = PoliticalCompass.of(opinions);
        return PoliticalCompass.of(base,makeCompass(compass, reference, modifiers));
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
