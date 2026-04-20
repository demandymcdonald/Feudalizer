package com.objects.culture.tenet;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonObject;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.group.ConnectionEdge;
import com.objects.culture.tenet.group.TGType;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.group.groups.*;
import com.objects.culture.tenet.interest.InterestGroup;
import com.objects.culture.tenet.mutable.tenets.general.Leadership;
import com.objects.culture.tenet.mutable.MutableTenet;
import com.objects.culture.tenet.mutable.tenets.ReligionTenets;
import com.utilities.serialization.SuperclassSerializable;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.DirectedPseudograph;
import org.jgrapht.graph.EdgeReversedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import java.util.*;

import static com.objects.culture.tenet.group.TenetGroup.Level.META_PILLAR;
import static com.objects.culture.tenet.group.TenetGroup.builder;

public class TenetManager {
    private static final Map<String, MutableTenet> tenets = new HashMap<>();

    private static final Multimap<TenetGroup, MutableTenet> tenetsByGroup = HashMultimap.create();
    private static final Map<Class<? extends MutableTenet>, TenetFactory<? extends MutableTenet>> mutableTenetFactories = new HashMap<>();


    public static final TenetGroup CULTURE = new TenetGroup.Builder(TGType.SORT_ONLY,META_PILLAR,"culture", "All_Culture", "Every Tenet").build();
    public static final TenetGroup HARD_CULTURE = new TenetGroup.Builder(TGType.SORT_ONLY,META_PILLAR,"hard", "Hard Culture", "").setParent(CULTURE).build();
    public static final TenetGroup SOFT_CULTURE = new TenetGroup.Builder(TGType.SORT_ONLY,META_PILLAR,"soft", "Soft Culture", "").setParent(CULTURE).build();

    public static void registerTenet(MutableTenet tenet) {
        tenets.put(tenet.getDisplayID(), tenet);
        tenetsByGroup.put(tenet.getGroup(), tenet);
    }


    public static <T extends MutableTenet> void registerMutableFactory(Class<T> mutClass, TenetFactory<T> factory) {
        mutableTenetFactories.put(mutClass, factory);
    }
    public static <T extends MutableTenet> T deserialize(JsonObject object) {
        Class<T> tC = (Class<T>) SuperclassSerializable.getSSClass(object);
        return (T) mutableTenetFactories.get(tC).rebuild(object);
    }

    public static MutableTenet getTenet(String id) {
        return tenets.get(id);
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
    public static class Group {
        private static final Map<String, TenetGroup> groups = new HashMap<>();
        private static final Multimap<TenetGroup.Level, TenetGroup> groupLevels = HashMultimap.create();
        private static final Graph<TenetGroup, DefaultEdge> groupParents = new DirectedPseudograph<>(DefaultEdge.class);
        private static final Graph<TenetGroup, DefaultEdge> groupChildren = new EdgeReversedGraph<>(groupParents);
        private static final Graph<TenetGroup, ConnectionEdge> groupConnected = new DefaultUndirectedGraph<>(ConnectionEdge.class);
        public enum Pillar {
            GOVERNMENT(GovernmentGroups.GOVERNMENT),
            ECONOMY(EconomicGroups.ECONOMY),
            RELIGION(ReligionGroups.RELIGION),
            SOCIETY(SocietyGroups.SOCIETY),
            FAMILY(FamilyGroups.FAMILY),
            EDUCATION(EducationGroups.EDUCATION)
            ;

            private final TenetGroup group;
            Pillar(TenetGroup group){
                this.group = group;
            }
            public TenetGroup getGroup(){
                return group;
            }
        }
        public static void register(TenetGroup group) {
            String id = group.getDisplayID();
            if (groups.containsKey(id)) {
                if (groups.get(id).equals(group)) {
                    return;
                } else {
                    throw new RuntimeException("Duplicate group Name: " + id);
                }
            }
            groupLevels.put(group.getLevel(), group);
            groups.put(group.getDisplayID(), group);
        }
        public static TenetGroup get(String id) {
            return groups.get(id);
        }
        public static List<TenetGroup> getPillars(){
            return new ArrayList<>(groupLevels.get(TenetGroup.Level.PILLAR));
        }
        public static List<TenetGroup> getCategories(){
            return new ArrayList<>(groupLevels.get(TenetGroup.Level.CATEGORY));
        }
        public static List<TenetGroup> getSubCategories(){
            return new ArrayList<>(groupLevels.get(TenetGroup.Level.SUBCATEGORY));
        }
        public static List<TenetGroup> getMetaPillars(){
            return new ArrayList<>(groupLevels.get(TenetGroup.Level.META_PILLAR));
        }
        public static Graph<TenetGroup, DefaultEdge> getParentGraph() {
            return groupParents;
        }
        public static Graph<TenetGroup, ConnectionEdge> getConnectedGraph() {
            return groupConnected;
        }
        public static TenetGroup getParent(TenetGroup child){
            return Graphs.successorListOf(groupChildren,child).getFirst();
        }
        public static List<TenetGroup> getChildren(TenetGroup parent){
            return Graphs.successorListOf(groupParents,parent);
        }
        public static List<TenetGroup> getConnected(TenetGroup group){
            return getConnected(group, ConnectionEdge.Type.ANY);
        }
        public static List<TenetGroup> getDependent(TenetGroup group){
            return getConnected(group, ConnectionEdge.Type.DEPENDENT);
        }
        public static List<TenetGroup> getInfluencing(TenetGroup group){
            return getConnected(group, ConnectionEdge.Type.INFLUENCING);
        }
        public static List<TenetGroup> getStructural(TenetGroup group){
            return getConnected(group, ConnectionEdge.Type.STRUCTURAL);
        }
        public static List<TenetGroup> getConnected(TenetGroup group, ConnectionEdge.Type type){
            List<TenetGroup> preList = Graphs.successorListOf(groupConnected,group);
            return switch (type){
                case ANY ->  preList;
                case STRUCTURAL ->  preList.stream().filter(g -> groupConnected.getEdge(group, g).getType() == ConnectionEdge.Type.STRUCTURAL).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
                case DEPENDENT ->  preList.stream().filter(g -> groupConnected.getEdge(group, g).getType() == ConnectionEdge.Type.DEPENDENT).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
                case INFLUENCING ->  preList.stream().filter(g -> groupConnected.getEdge(group, g).getType() == ConnectionEdge.Type.INFLUENCING).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
            };
        }
        public static List<TenetGroup> getAncestors(TenetGroup group){
            List<TenetGroup> toReturn = new ArrayList<>();
            List<TenetGroup> current = Graphs.successorListOf(groupChildren,group);
            for (TenetGroup g : current){
                toReturn.add(g);
                current.addAll(Graphs.successorListOf(groupChildren,g));
            }
            return toReturn;
        }
        public static List<TenetGroup> getDescendants(TenetGroup group){
            List<TenetGroup> toReturn = new ArrayList<>();
            List<TenetGroup> current = Graphs.successorListOf(groupParents,group);
            for (TenetGroup g : current){
                toReturn.add(g);
                current.addAll(Graphs.successorListOf(groupParents,g));
            }
            return toReturn;
        }
        public static TenetGroup getRoot(TenetGroup group){
            if (group.getLevel() == TenetGroup.Level.META_PILLAR){
                return group;
            }
            BreadthFirstIterator<TenetGroup,DefaultEdge> iterator = new BreadthFirstIterator<>(groupParents,group);
            while (iterator.hasNext()){
                TenetGroup g = iterator.next();
                if (g.getLevel() == TenetGroup.Level.META_PILLAR){
                    return g;
                }
            }
            return null;
        }
        public static TenetGroup getPillar(TenetGroup group){
            if (group.getLevel() == TenetGroup.Level.PILLAR){
                return group;
            }
            BreadthFirstIterator<TenetGroup,DefaultEdge> iterator = new BreadthFirstIterator<>(groupParents,group);
            while (iterator.hasNext()){
                TenetGroup g = iterator.next();
                if (g.getLevel() == TenetGroup.Level.PILLAR){
                    return g;
                }
            }
            return null;
        }
        public static TenetGroup getCategory(TenetGroup group){
            if (group.getLevel() == TenetGroup.Level.CATEGORY){
                return group;
            }
            BreadthFirstIterator<TenetGroup,DefaultEdge> iterator = new BreadthFirstIterator<>(groupChildren,group);
            while (iterator.hasNext()){
                TenetGroup g = iterator.next();
                if (g.getLevel() == TenetGroup.Level.CATEGORY){
                    return g;
                }
            }
            return null;
        }
        public static TenetGroup getSubCategory(TenetGroup group){
            BreadthFirstIterator<TenetGroup,DefaultEdge> iterator;
            if (group == null)
                return null;
            if (group.getLevel() == TenetGroup.Level.SUBCATEGORY){
                return group;
            } else if (group.getLevel() == TenetGroup.Level.CATEGORY){
                iterator = new BreadthFirstIterator<>(groupParents,group);
            } else {
                iterator = new BreadthFirstIterator<>(groupChildren,group);
            }
            while (iterator.hasNext()){
                TenetGroup g = iterator.next();
                if (g.getLevel() == TenetGroup.Level.SUBCATEGORY){
                    return g;
                }
            }
            return null;
        }
    }
    public static class InterestGroups{
        private Map<String, InterestGroup> groups = new HashMap<>();
        Graph<InterestGroup, DefaultEdge> graph = new DirectedPseudograph<>(DefaultEdge.class);
    }
    public static abstract class TenetFactory<T extends MutableTenet> {
        public final T rebuild(JsonObject object) {
            JsonObject main = SuperclassSerializable.getMainData(object);
            UUID id = UUID.fromString(main.get("id").getAsString());
            TenetReference reference = TenetReference.fromJson(main.get("parent").getAsJsonObject());
            TenetGroup g = TenetManager.Group.get(main.get("group").getAsString());
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

