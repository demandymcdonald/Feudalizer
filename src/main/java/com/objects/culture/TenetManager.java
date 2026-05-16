package com.objects.culture;

import com.base.component.ComponentManager;
import com.base.reference.DMEReference;
import com.google.common.cache.Cache;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.object.ideology.Ideology;

import com.objects.culture.tenet.group.ConnectionEdge;
import com.objects.culture.tenet.group.TGType;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.group.groups.*;
import com.objects.culture.tenet.interest.InterestGroup;
import com.objects.culture.tenet.mutable.MutableTenet;
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

public class TenetManager extends ComponentManager<MutableTenet>{
    public static final TenetManager INSTANCE = new TenetManager();
    public static final TenetGroup CULTURE = new TenetGroup.Builder<TenetGroup>(TGType.SORT_ONLY,META_PILLAR,"culture", "All_Culture", "Every Tenet").build();
    public static final TenetGroup HARD_CULTURE = new TenetGroup.Builder<TenetGroup>(TGType.SORT_ONLY,META_PILLAR,"hard", "Hard Culture", "").setParent(CULTURE).build();
    public static final TenetGroup SOFT_CULTURE = new TenetGroup.Builder<TenetGroup>(TGType.SORT_ONLY,META_PILLAR,"soft", "Soft Culture", "").setParent(CULTURE).build();

    public TenetManager() {
        super(MutableTenet.class);
    }

    @Override
    protected void onInit() {
        super.onInit();
        SocietyGroups.init();
        FamilyGroups.init();
        EducationGroups.init();
        EconomicGroups.init();
        ReligionGroups.init();
        GovernmentGroups.init();
    }

    public static class Group extends ComponentManager<TenetGroup> {
        public static final Group INSTANCE = new Group(TenetGroup.class);
        private final Multimap<TenetGroup.Level, TenetGroup> groupLevels = HashMultimap.create();
        private final Graph<TenetGroup, DefaultEdge> groupParents = new DirectedPseudograph<>(DefaultEdge.class);
        private final Graph<TenetGroup, DefaultEdge> groupChildren = new EdgeReversedGraph<>(groupParents);
        private final Graph<TenetGroup, ConnectionEdge> groupConnected = new DefaultUndirectedGraph<>(ConnectionEdge.class);

        public Group(Class<? extends TenetGroup> type) {
            super(type);
        }

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
        public static Graph<TenetGroup, DefaultEdge> getParentGraph() {
            return INSTANCE.groupParents;
        }
        public static Graph<TenetGroup, ConnectionEdge> getConnectedGraph() {
            return INSTANCE.groupConnected;
        }
        public static TenetGroup getParent(TenetGroup child){
            return Graphs.successorListOf(INSTANCE.groupChildren,child).getFirst();
        }
        public static Set<TenetGroup> getChildren(TenetGroup parent){
            return new HashSet<>(Graphs.successorListOf(INSTANCE.groupParents,parent));
        }
        public static Set<TenetGroup> getConnected(TenetGroup group){
            return getConnected(group, ConnectionEdge.Type.ANY);
        }
        public static Set<TenetGroup> getDependent(TenetGroup group){
            return getConnected(group, ConnectionEdge.Type.DEPENDENT);
        }
        public static Set<TenetGroup> getInfluencing(TenetGroup group){
            return getConnected(group, ConnectionEdge.Type.INFLUENCING);
        }
        public static Set<TenetGroup> getStructural(TenetGroup group){
            return getConnected(group, ConnectionEdge.Type.STRUCTURAL);
        }
        public static Set<TenetGroup> getConnected(TenetGroup group, ConnectionEdge.Type type){
            Set<TenetGroup> preList = new HashSet<>(Graphs.successorListOf(INSTANCE.groupConnected,group));
            return switch (type){
                case ANY ->  preList;
                case STRUCTURAL ->  preList.stream().filter(g -> INSTANCE.groupConnected.getEdge(group, g).getType() == ConnectionEdge.Type.STRUCTURAL).collect(HashSet::new, HashSet::add, HashSet::addAll);
                case DEPENDENT ->  preList.stream().filter(g -> INSTANCE.groupConnected.getEdge(group, g).getType() == ConnectionEdge.Type.DEPENDENT).collect(HashSet::new, HashSet::add, HashSet::addAll);
                case INFLUENCING ->  preList.stream().filter(g -> INSTANCE.groupConnected.getEdge(group, g).getType() == ConnectionEdge.Type.INFLUENCING).collect(HashSet::new, HashSet::add, HashSet::addAll);
            };
        }
        public static Set<TenetGroup> getAncestors(TenetGroup group){
            Set<TenetGroup> toReturn = new HashSet<>();
            List<TenetGroup> current = Graphs.successorListOf(INSTANCE.groupChildren,group);
            for (TenetGroup g : current){
                toReturn.add(g);
                current.addAll(Graphs.successorListOf(INSTANCE.groupChildren,g));
            }
            return toReturn;
        }
        public static Set<TenetGroup> getDescendants(TenetGroup group){
            Set<TenetGroup> toReturn = new HashSet<>();
            List<TenetGroup> current = Graphs.successorListOf(INSTANCE.groupParents,group);
            for (TenetGroup g : current){
                toReturn.add(g);
                current.addAll(Graphs.successorListOf(INSTANCE.groupParents,g));
            }
            return toReturn;
        }
        public static TenetGroup getRoot(TenetGroup group){
            if (group.getLevel() == TenetGroup.Level.META_PILLAR){
                return group;
            }
            BreadthFirstIterator<TenetGroup,DefaultEdge> iterator = new BreadthFirstIterator<>(INSTANCE.groupParents,group);
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
            BreadthFirstIterator<TenetGroup,DefaultEdge> iterator = new BreadthFirstIterator<>(INSTANCE.groupParents,group);
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
            BreadthFirstIterator<TenetGroup,DefaultEdge> iterator = new BreadthFirstIterator<>(INSTANCE.groupChildren,group);
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
                iterator = new BreadthFirstIterator<>(INSTANCE.groupParents,group);
            } else {
                iterator = new BreadthFirstIterator<>(INSTANCE.groupChildren,group);
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
    public static class Ideologies extends ComponentManager<Ideology> {
        public static final Ideologies INSTANCE = new Ideologies();
        public Ideologies() {
            super(Ideology.class);
        }
    }
    public static class InterestGroups extends ComponentManager<InterestGroup> {

        private final Multimap<InterestGroup.Dimension,InterestGroup> byDimension = HashMultimap.create();
        public static final InterestGroups INSTANCE = new InterestGroups();
        private InterestGroups() {
            super(InterestGroup.class);
        }
        @Override
        protected void onRegister(InterestGroup object) {
            super.onRegister(object);
            byDimension.put(object.getDimension(),object);
        }
        public Set<InterestGroup> getByDimension(InterestGroup.Dimension dimension){
            return new HashSet<>(byDimension.get(dimension));
        }

        public Set<InterestGroup> getForCharacter(DMEReference<? extends SentientCharacter<?>> character){
            Set<InterestGroup> toReturn = new HashSet<>();
            for (InterestGroup group : instanceMap.values()){
                if(group.isMember(character)){
                    toReturn.add(group);
                }
            }
            return toReturn;
        }
    }
    public static class ElectionType extends ComponentManager<ElectionType<?>> {
        public static final ElectionType INSTANCE = new ElectionType();
        private ElectionType() {
            super(ElectionType.class);
        }
    }
}

