package com.objects.culture.tenet;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonObject;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.group.TGType;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.group.groups.*;
import com.objects.culture.tenet.reference.TenetReference;
import com.objects.culture.tenet.tenets.general.Leadership;
import com.objects.culture.tenet.types.mutable.MutableTenet;
import com.objects.culture.tenet.tenets.ReligionTenets;
import com.utilities.serialization.SuperclassSerializable;

import java.util.*;

import static com.objects.culture.tenet.group.TenetGroup.builder;

public class TenetManager {
    private static final Map<String, MutableTenet> tenets = new HashMap<>();
    private static final Map<String, TenetGroup> groups = new HashMap<>();
    private static final Multimap<TenetGroup, MutableTenet> tenetsByGroup = HashMultimap.create();
    private static final Multimap<TenetGroup,TenetGroup> groupRelations = HashMultimap.create();
    private static final Multimap<TenetGroup,TenetGroup> parentRelations = HashMultimap.create();
    private static final Map<Class<? extends MutableTenet>, TenetFactory<? extends MutableTenet>> mutableTenetFactories = new HashMap<>();





    public static final TenetGroup CULTURE = builder(TGType.SORT_ONLY,"culture", "All_Culture", "Every Tenet");
    public static final TenetGroup HARD_CULTURE = builder(TGType.SORT_ONLY,"hard", "Hard Culture", "");
    public static final TenetGroup SOFT_CULTURE = builder(TGType.SORT_ONLY,"soft", "Soft Culture", "");

    public static void registerTenet(MutableTenet tenet) {
        tenets.put(tenet.getDisplayID(), tenet);
        tenetsByGroup.put(tenet.getGroup(), tenet);
    }
    public static void registerGroup(TenetGroup group) {
        String id = group.getDisplayID();
        if (groups.containsKey(id)) {
            if (groups.get(id).equals(group)) {
                return;
            } else {
                throw new RuntimeException("Duplicate group ID: " + id);
            }
        }
        groups.put(group.getDisplayID(), group);
        handleGroupRelations(group, group.connected());
        TenetGroup parent = group.parent();
        if (parent != null) {
            parentRelations.put(parent, group);
        }
    }
    public static List<TenetGroup> getChildren(TenetGroup group) {
        return new ArrayList<>(parentRelations.get(group));
    }
    public static <T extends MutableTenet> void registerMutableFactory(Class<T> mutClass, TenetFactory<T> factory) {
        mutableTenetFactories.put(mutClass, factory);
    }
    private static void handleGroupRelations(TenetGroup group, List<TenetGroup> linkedGroups){
        for (TenetGroup linkedGroup : linkedGroups) {
            groupRelations.put(group, linkedGroup);
            groupRelations.put(linkedGroup, group);
        }
    }
    public static boolean isParentGroup(TenetGroup parent, TenetGroup child){
        Collection<TenetGroup> children = parentRelations.get(parent);
        if(children.contains(child)){
            return true;
        }
        for (TenetGroup c: children){
            if(isParentGroup(c,child)){
                return true;
            }
        }
        return false;
    }
    public static <T extends MutableTenet> T deserialize(JsonObject object) {
        Class<T> tC = (Class<T>) SuperclassSerializable.getSSClass(object);
        return (T) mutableTenetFactories.get(tC).rebuild(object);
    }

    public static MutableTenet getTenet(String id) {
        return tenets.get(id);
    }
    public static TenetGroup getGroup(String id) {
        return groups.get(id);
    }
    public static Collection<TenetGroup> getConnectedGroups(TenetGroup group){
        return groupRelations.get(group);
    }
    public static void init(){
        SocietyGroups.init();
        FamilyGroups.init();
        EducationGroups.init();
        EconomicGroups.init();
        ReligionGroups.init();
        GovernmentGroups.init();

        ReligionTenets.init();
    }

    public static abstract class TenetFactory<T extends MutableTenet> {
        public final T rebuild(JsonObject object) {
            JsonObject main = SuperclassSerializable.getMainData(object);
            UUID id = UUID.fromString(main.get("id").getAsString());
            TenetReference reference = TenetReference.fromJson(main.get("parent").getAsJsonObject());
            TenetGroup g = TenetManager.getGroup(main.get("group").getAsString());
            String displayID = main.get("displayID").getAsString();
            String displayName = main.get("name").getAsString();
            String description = main.get("description").getAsString();
            PoliticalCompass compass = PoliticalCompass.build(main.get("compass").getAsJsonObject());
            T t = onRebuild(id,reference,g,compass,displayID,displayName,description);
            t.additionalLoad(SuperclassSerializable.getAdditional(object));
            return t;
        }
        protected abstract T onRebuild(UUID id, TenetReference parent, TenetGroup tenetGroup, PoliticalCompass compass, String displayID, String displayName, String description);
    }

    static {
        registerMutableFactory(Leadership.TermLimit.class, new TenetFactory<Leadership.TermLimit>() {
            @Override
            protected Leadership.TermLimit onRebuild(UUID id, TenetReference parent, TenetGroup tenetGroup, PoliticalCompass compass, String displayID, String displayName, String description) {
                return new Leadership.TermLimit(parent,id, tenetGroup, compass, displayID, displayName, description);
            }
        });
    }
}

