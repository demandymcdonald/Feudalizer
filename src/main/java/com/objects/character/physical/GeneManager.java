package com.objects.character.physical;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.objects.character.physical.aspect.GeneProperty;
import com.objects.character.physical.aspect.BodyPart;
import com.objects.character.physical.aspect.PhysicalAspect;
import com.objects.character.physical.augment.Augment;
import com.objects.character.physical.augment.AugmentSlot;
import com.objects.character.physical.genetics.Gene;
import com.objects.character.physical.race.Race;
import com.objects.character.physical.species.Species;
import com.objects.character.physical.species.nomenclature.NomenEntry;
import com.utilities.id.StringIdentifiable;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.DirectedPseudograph;

import java.util.*;

public class GeneManager {
    private static final BiMap<Class<IGeneNode<?>>,Long> NodeMap = HashBiMap.create();
    protected static final MutableBoolean isLoaded = new MutableBoolean(false);
    protected static final Graph<IGeneNode<?>,GeneNodeEdge> GeneticEcosystem = new DirectedPseudograph<>(GeneNodeEdge.class);
    public static void init(){
        Body_Part.init();
        Aspect_Property.init();
        Physical_Aspect.init();
        Species_Race.init();
        Genetics.init();
        isLoaded.setTrue();
        Body_Part.initLink();
        Species_Race.initLink();
        Physical_Aspect.initLink();
        Genetics.initLink();
    }
    public static <T extends IGeneNode<T>> Class<T> getClassNode(long key){
        return (Class<T>) NodeMap.inverse().get(key);
    }
    public static <T extends IGeneNode<T>> Long getClassNode(Class<T> key){
        return NodeMap.get(key);
    }
    public static <T extends IGeneNode<T>> void registerNode(Class<T> geneNode, long id){
        NodeMap.put((Class<IGeneNode<?>>) geneNode,id);
    }
    public static <S extends IGeneNode<S>,T extends IGeneNode<T>> Set<T> getConnectionsWhereSource(S source, Class<T> targetClass){
        return getConnectionsWhereSource(source,getClassNode(targetClass));
    }

    public static <S extends IGeneNode<S>,T extends IGeneNode<T>> Set<S> getConnectionsWhereTarget(T target, Class<S> sourceClass){
        return getConnectionsWhereTarget(target,getClassNode(sourceClass));
    }
    @SuppressWarnings("unchecked")
    public static <S extends IGeneNode<S>,T extends IGeneNode<T>> Set<T> getConnectionsWhereSource(S source, long id){
        List<T> os = (List<T>) GeneticEcosystem.outgoingEdgesOf(source).stream().filter((ed) -> ed.getTargetID() == id)
                .map((java.util.function.Function<? super GeneNodeEdge, ? extends T>) GeneNodeEdge::getTargetNode).toList();
         return new HashSet<>(os);
    }
    @SuppressWarnings("unchecked")
    public static <S extends IGeneNode<S>,T extends IGeneNode<T>> Set<S> getConnectionsWhereTarget(T target, long id){
        List<S> os = (List<S>) GeneticEcosystem.incomingEdgesOf(target).stream().filter((ed) -> ed.getSourceID() == id)
                .map((java.util.function.Function<? super GeneNodeEdge, ? extends S>) GeneNodeEdge::getSourceNode).toList();
        return new HashSet<>(os);
    }
    public static class GeneNodeEdge extends DefaultEdge {
        private final long sourceID;
        private final long targetID;
        public GeneNodeEdge(IGeneNode<?> source, IGeneNode<?> target){
            //These are class ids that allow for faster filtering.
            sourceID = source.getNodeId();
            targetID = target.getNodeId();
        }
        public long getSourceID() {
            return sourceID;
        }
        public long getTargetID() {
            return targetID;
        }
        public <S extends IGeneNode<S>> S getSourceNode(){
            return (S) super.getSource();
        }
        public <T extends IGeneNode<T>> T getTargetNode(){
            return (T) super.getTarget();
        }
    }
    public static class Body_Part{
        public static void register(BodyPart part){
            GeneticEcosystem.addVertex(part);
        }
        public static void init(){
            //TODO wire once defaults are in place.
        }
        public static void initLink(){
            for(BodyPart part : BodyPart.values()){
                link(part);
            }
        }
        public static void link(BodyPart part){
            if(part.getParent() != null){
                GeneticEcosystem.addEdge(part.getParent(),part,new GeneNodeEdge(part.getParent(),part));
            }
        }
    }
    public static class Aspect_Property{
        private static final Map<String, GeneProperty> propertyMap = new HashMap<>();
        public static void register(GeneProperty aspect){
            registerGeneObject(GeneProperty.class.getSimpleName(),aspect,propertyMap);
        }
        public static void init(){
            //TODO wire once defaults are in place.
        }
        public static GeneProperty getProperty(String id){
            return propertyMap.get(id);
        }
    }
    public static class Species_Race {
        private static final Map<String,Species> speciesMap = new HashMap<>();
        private static final Map<String,Race> raceMap = new HashMap<>();
        private static final Map<String,NomenEntry> nomenclatureMap = new HashMap<>();
        private static final Graph<NomenEntry,DefaultEdge> nomenclatureGraph = new DefaultUndirectedGraph<>(DefaultEdge.class);
        private static final Multimap<Species,Race> speciesRaceMap = HashMultimap.create();
        public static void registerSpecies(Species species){
            registerGeneObject(Species.class.getSimpleName(),species,speciesMap);
            if(isLoaded.booleanValue()){
                link(species);
            }
        }
        public static void registerRace(Race race){
            registerGeneObject(Race.class.getSimpleName(),race,raceMap);
            if(isLoaded.booleanValue()){
                link(race);
            }
        }
        public static NomenEntry getNomenEntry(String id){
            return nomenclatureMap.get(id);
        }
        public static void registerNomenclature(NomenEntry entry){
            nomenclatureMap.put(entry.getID(),entry);
            nomenclatureGraph.addVertex(entry);
            NomenEntry parent = entry.parent() ;
            if (parent== null){
                return;
            }
            if (!nomenclatureMap.containsKey(parent.getID())){
                registerNomenclature(parent);
            }
            nomenclatureGraph.addEdge(parent,entry);
        }
        public static void init(){
            //TODO wire once defaults are in place.
        }
        public static void initLink(){
            for(Species species : speciesMap.values()){
                link(species);
            }
            for(Race race : raceMap.values()){
                link(race);
            }
        }
        public static void link(Species species){
            for (PhysicalAspect as : species.getValidProperties()){
                GeneticEcosystem.addEdge(species,as,new GeneNodeEdge(species,as));
                GeneticEcosystem.addEdge(species,as.getMainBodyPart(),new GeneNodeEdge(species,as.getMainBodyPart()));
            }
        }
        public static void link(Race race){
            Species species = race.getSpecies();
            GeneticEcosystem.addEdge(species,race,new GeneNodeEdge(species,race));
        }
        public static Species getSpecies(String id){
            return speciesMap.get(id);
        }
        public static Race getRace(String id){
            return raceMap.get(id);
        }
        public static Set<Race> getRaces(Species species){
            return new HashSet<>(speciesRaceMap.get(species));
        }

        public static Graph<IGeneNode<?>,GeneNodeEdge> getSpeciesGraph(Species species){
            Graph<IGeneNode<?>,GeneNodeEdge> graph = new DefaultDirectedGraph<>(GeneNodeEdge.class);
            graph.addVertex(species);
            for(PhysicalAspect aspect : species.getValidProperties()){
                graph.addVertex(aspect);
                for(BodyPart bp : aspect.getValidBodyParts()){
                    graph.addVertex(bp);
                    graph.addEdge(aspect,bp,new GeneNodeEdge(aspect,bp));
                    if (bp == BodyPart.WHOLE_BODY){
                        graph.addEdge(species,bp,new GeneNodeEdge(species,bp));
                    }
                    if(bp.getParent() == null){
                        continue;
                    } else if (!graph.containsVertex(bp.getParent())){
                        graph.addVertex(bp.getParent());
                        if (bp.getParent() == BodyPart.WHOLE_BODY){
                            graph.addEdge(species,bp.getParent(),new GeneNodeEdge(species,bp.getParent()));
                        }
                    }
                    graph.addEdge(bp.getParent(),bp,new GeneNodeEdge(bp.getParent(),bp));
                }
                for (GeneProperty property : aspect.getValidProperties()){
                    graph.addVertex(property);
                    graph.addEdge(aspect,property,new GeneNodeEdge(aspect,property));
                    for (Gene gene : property.getValidGenes()){
                        graph.addVertex(gene);
                        graph.addEdge(property,gene,new GeneNodeEdge(property,gene));
                    }
                }
            }
            for(Race r : getRaces(species)){
                graph.addVertex(r);
                graph.addEdge(species,r,new GeneNodeEdge(species,r));
            }
            return graph;
        }
    }
    public static class Physical_Aspect{
        private static final Map<String,PhysicalAspect> physicalAspects = new HashMap<>();
        public static void register(PhysicalAspect aspect){
            registerGeneObject(PhysicalAspect.class.getSimpleName(),aspect,physicalAspects);
            if(isLoaded.booleanValue()){
                link(aspect);
            }
        }
        public static void init(){
            //TODO wire once defaults are in place.
        }
        public static void initLink(){
            for(PhysicalAspect as : physicalAspects.values()){
                link(as);
            }
        }
        public static void link(PhysicalAspect aspect){
            BodyPart bp = aspect.getMainBodyPart();
            if(aspect.canSubInherit()){
                while(bp.getParent() != null){
                    GeneticEcosystem.addEdge(bp,aspect,new GeneNodeEdge(bp,aspect));
                    bp = bp.getParent();
                }
            } else {
                GeneticEcosystem.addEdge(bp,aspect,new GeneNodeEdge(bp,aspect));
            }
            for(GeneProperty property : aspect.getValidProperties()){
                GeneticEcosystem.addEdge(aspect,property,new GeneNodeEdge(aspect,property));
            }
            for(AugmentSlot augment : aspect.getValidAugmentSlots()){
                GeneticEcosystem.addEdge(aspect,augment,new GeneNodeEdge(augment,aspect));
            }
        }

    }
    public static class Genetics{
        private static final Map<String, Gene> geneticsMap = new HashMap<>();
        public static void register(Gene genetics){
            registerGeneObject(Gene.class.getSimpleName(),genetics,geneticsMap);
            if(isLoaded.booleanValue()){
                link(genetics);
            }
        }
        public static void init(){
            //TODO wire once defaults are in place.
        }
        public static void initLink(){
            for(Gene genetic : geneticsMap.values()){
                link(genetic);
            }
        }
        public static void link(Gene gene){
            GeneticEcosystem.addEdge(gene.getProperty(),gene,new GeneNodeEdge(gene.getProperty(),gene));
        }
        public static Gene get(String id){
            return geneticsMap.get(id);
        }
    }
    public static class Augments{
        private static final Map<String, AugmentSlot> augmentSlotMap = new HashMap<>();
        private static final Map<String, Augment> augmentMap = new HashMap<>();
        private static final Multimap<AugmentSlot, Augment> augmentTotalMap = HashMultimap.create();
        public static void registerAugmentSlot(AugmentSlot slot){
            registerGeneObject(AugmentSlot.class.getSimpleName(),slot,augmentSlotMap);
            if(isLoaded.booleanValue()){
                link(slot);
            }
        }
        public static Augment getAugment(String id){
            return augmentMap.get(id);
        }
        public static AugmentSlot getAugmentSlot(String id){
            return augmentSlotMap.get(id);
        }
        public static Set<Augment> getAugments(AugmentSlot slot){
            return new HashSet<>(augmentTotalMap.get(slot));
        }
        public static void registerAugment(Augment object){
            if(augmentTotalMap.containsKey(object.getSlot()) && augmentTotalMap.get(object.getSlot()).stream().anyMatch((om) -> object != om)){
                throw new IllegalArgumentException("Augment: "+object.getID()+" already registered!");
                //TODO think about handling this better if I ever add datapack support.
            } else if (augmentTotalMap.containsKey(object.getSlot())){
                return;
            }
            augmentMap.put(object.getID(),object);
            augmentTotalMap.put(object.getSlot(),object);
        }
        public static void init(){
            //TODO wire once defaults are in place.
        }
        public static void initLink(){
            for(AugmentSlot slot : augmentSlotMap.values()){
                link(slot);
            }
        }
        public static void link(AugmentSlot slot){
            //GeneticEcosystem.addEdge(slot.getBodyPart(),slot,new GeneNodeEdge(slot.getBodyPart(),slot));
        }
    }
    public static <T extends StringIdentifiable>void registerGeneObject(String objectType, T object, Map<String,T> map){
        if(map.containsKey(object.getID()) && map.get(object.getID()) != object){
            throw new IllegalArgumentException(objectType+": "+object.getID()+" already registered!");
            //TODO think about handling this better if I ever add datapack support.
        } else if (map.containsKey(object.getID())){
            return;
        }
        map.put(object.getID(),object);
        if (object instanceof IGeneNode<?> gn) {
            gn.getNodeId(); //Forces the registration of the node type if it hasn't been forced already.
            GeneticEcosystem.addVertex(gn);
        }
    }

}
