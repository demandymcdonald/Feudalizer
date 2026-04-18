package com.objects.character.physical;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.objects.character.physical.aspect.GeneProperty;
import com.objects.character.physical.aspect.BodyPart;
import com.objects.character.physical.aspect.PhysicalAspect;
import com.objects.character.physical.genetics.Gene;
import com.objects.character.physical.genetics.GeneNode;
import com.objects.character.physical.race.Race;
import com.objects.character.physical.species.Species;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;

import java.util.HashMap;
import java.util.Map;

public class GeneManager {
    private static final BiMap<Class<GeneNode<?>>,Long> NodeMap = HashBiMap.create();
    protected static final MutableBoolean isLoaded = new MutableBoolean(false);
    protected static final Graph<GeneNode<?>,GeneNodeEdge> GeneticEcosystem = new DirectedPseudograph<>(GeneNodeEdge.class);
    public static void init(){
        Body_Part.init();
        Aspect_Property.init();
        Physical_Aspect.init();
        Species_Race.init();
        Genetics.init();
        isLoaded.setTrue();
        Species_Race.initLink();
        Physical_Aspect.initLink();
        Genetics.initLink();
    }
    public static <T extends GeneNode<T>> Class<T> getClassNode(long key){
        return (Class<T>) NodeMap.inverse().get(key);
    }
    public static <T extends GeneNode<T>> Long getClassNode(Class<T> key){
        return NodeMap.get(key);
    }
    public static <T extends GeneNode<T>> void registerNode(Class<T> geneNode, long id){
        NodeMap.put((Class<GeneNode<?>>) geneNode,id);
    }

    public static class GeneNodeEdge extends DefaultEdge {
        private final long sourceID;
        private final long targetID;
        public GeneNodeEdge(GeneNode<?> source, GeneNode<?> target){
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
    }
    public static class Body_Part{
        public static void register(BodyPart part){
            GeneticEcosystem.addVertex(part);
        }
        public static void init(){
            //TODO wire once defaults are in place.
        }
    }
    public static class Aspect_Property{
        private static final Map<String, GeneProperty> propertyMap = new HashMap<>();
        public static void register(GeneProperty aspect){
            if(propertyMap.containsKey(aspect.getID()) &&  !(propertyMap.get(aspect.getID()).equals(aspect))){
                throw new IllegalArgumentException("Aspect Property: "+aspect.getID()+" already registered");
            } else if (propertyMap.containsKey(aspect.getID())){
                return;
            }
            propertyMap.put(aspect.getID(),aspect);
            GeneticEcosystem.addVertex(aspect);
        }
        public static void init(){
            //TODO wire once defaults are in place.
        }
    }
    public static class Species_Race {
        private static final Map<String,Species> speciesMap = new HashMap<>();
        private static final Map<String,Race> raceMap = new HashMap<>();
        public static void registerSpecies(Species species){
            if(speciesMap.containsKey(species.getID()) &&  !(speciesMap.get(species.getID()).equals(species))){
                throw new IllegalArgumentException("Species: "+species.getID()+" already registered");
            } else if (speciesMap.containsKey(species.getID())){
                return;
            }
            speciesMap.put(species.getID(),species);
            GeneticEcosystem.addVertex(species);
            if(isLoaded.booleanValue()){
                link(species);
            }
        }
        public static void registerRace(Race race){
            if(raceMap.containsKey(race.getID()) &&  !(raceMap.get(race.getID()).equals(race))){
                throw new IllegalArgumentException("Race: "+race.getID()+" already registered");
            } else if (raceMap.containsKey(race.getID())){
                return;
            }
            raceMap.put(race.getID(),race);
            GeneticEcosystem.addVertex(race);
            if(isLoaded.booleanValue()){
                link(race);
            }
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
                GeneticEcosystem.addEdge(species,as.part(),new GeneNodeEdge(species,as));
            }
        }
        public static void link(Race race){
            Species species = race.getSpecies();
            GeneticEcosystem.addEdge(species,race,new GeneNodeEdge(species,race));
        }
    }
    public static class Physical_Aspect{
        private static final Map<String,PhysicalAspect> physicalAspects = new HashMap<>();
        public static void register(PhysicalAspect aspect){

            if(physicalAspects.containsKey(aspect.getID()) && !(physicalAspects.get(aspect.getID()).equals(aspect))){
                throw new IllegalArgumentException("Physical Aspect: "+aspect.id()+" already registered");
            } else if (physicalAspects.containsKey(aspect.getID())){
                return;
            }
            GeneticEcosystem.addVertex(aspect);
            physicalAspects.put(aspect.getID(),aspect);
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
            BodyPart bp = aspect.part();
            GeneticEcosystem.addEdge(bp,aspect,new GeneNodeEdge(bp,aspect));
            for(GeneProperty property : aspect.validProperties()){
                GeneticEcosystem.addEdge(aspect,property,new GeneNodeEdge(aspect,property));
            }
        }
    }
    public static class Genetics{
        private static final Map<String, Gene> geneticsMap = new HashMap<>();
        public static void register(Gene genetics){
            if(geneticsMap.containsKey(genetics.getID()) &&  !(geneticsMap.get(genetics.getID()).equals(genetics))){
                throw new IllegalArgumentException("Gene: "+genetics.getID()+" already registered");
            } else if (geneticsMap.containsKey(genetics.getID())){
                return;
            }
            geneticsMap.put(genetics.getID(),genetics);
            GeneticEcosystem.addVertex(genetics);
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
    }


}
