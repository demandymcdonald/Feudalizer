package com.objects.culture.tenet.group;

import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.TenetManager;
import com.utilities.IDisplayable;
import com.utilities.hierarchy.Parented;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.*;

public class TenetGroup implements IDisplayable, Parented<TenetGroup> {
    public static final int SYSTEM_MAX = 10;
    public static final int BELIEF_MAX = 10;
    public static final int VALUE_MAX = 3;
    public static final int TRADITION_MAX = 10;
    public static final int AESTHETIC_MAX = 6;
    public static final int LANGUAGE_MAX = 3;
    //=============================================================
    public enum Level{
        META_PILLAR,
        PILLAR,//Top level things like Government, Society, Economy, etc. Will always be sort only
        CATEGORY, //Category of a top level, Government_System, Religious Doctrine, etc.
        SUBCATEGORY, //Middle management stuff, Leadership, Class and Caste, etc.
        NORMAL, //Everything not covered by another category.
        LORE_ONLY // Aestetic things like Fashion, architecture, etc. Will always map onto AESTHETICS_AND_VISUAL TGType
    }
    private final TGType type;
    //private final List<TenetGroup> connected;
    private final String fullID;
    private final String id;
    private final String name;
    private final String description;
    private final Level level;
    private TenetGroup(@NonNull TGType type, Level level, String fullID, String id,  String name, String description) {
        if (type == null){
            throw new RuntimeException("Cannot create TenetGroup: "+ id +" without a type");
        }
        this.fullID = fullID;
        this.type = type;
        //this.connected = buildList(parent, connected);
        this.id = id;
        this.level = level;
        this.name = name;
        this.description = description;
    }
    public void init(){
        //May be needed to do buildList after all the statics are registered, but that seems unlikely.
    }
//    private static ImmutableList<TenetGroup> buildList(TenetGroup parent, List<TenetGroup> connected){
//        List<TenetGroup> list = new ArrayList<>(connected);
//        TenetGroup current = parent;
//        while(current.parent() != null){
//            list.addAll(current.connected());
//            current = current.parent();
//        }
//        return ImmutableList.copyOf(list);
//    }
    private static String buildID(String id, TenetGroup parent){
        if (parent == null){
            return id.toLowerCase();
        } else {
            return parent.getDisplayID() + ":" + id.toLowerCase();
        }
    }
    @Override
    public String getDisplayID() {
        return fullID;
    }

    @Override
    public String getDisplayName() {
        return name;
    }
    public Level getLevel(){
        return level;
    }
    public boolean isParentOf(TenetGroup child){
        return TenetManager.Group.getParentGraph().containsEdge(this, child);
    }
    public boolean isAncestorOf(TenetGroup child){
        return TenetManager.Group.getAncestors(child).contains(this);
    }
    public boolean isChildOf(TenetGroup parent){
        return TenetManager.Group.getParentGraph().containsEdge(parent, this);
    }
    public boolean isDescendantOf(TenetGroup parent){
        return TenetManager.Group.getDescendants(parent).contains(this);
    }

    @Override
    public Optional<TenetGroup> getParent() {
        return Optional.ofNullable(TenetManager.Group.getParent(this));
    }
    public List<TenetGroup> getConnected(){
        return TenetManager.Group.getConnected(this);
    }
    public List<TenetGroup> getChildren(){
        return TenetManager.Group.getChildren(this);
    }
    public List<TenetGroup> getAncestors(){
        return TenetManager.Group.getAncestors(this);
    }
    public List<TenetGroup> getDescendants(){
        return TenetManager.Group.getDescendants(this);
    }
    public record AcceptanceContainer(int maxNumber, Acceptance... accept){}
    @Deprecated
    public static TenetGroup builder(TGType type, String id, String name, String description){
        Builder b = new Builder(type, id, name, description);
        return b.build();
    }
    @Deprecated
    public static TenetGroup builder(TGType type, Level level, String id, String name, String description){
        Builder b = new Builder(type, level, id, name, description);
        return b.build();
    }
    @Deprecated
    public static TenetGroup builder(TGType type, TenetGroup parent, String id, String name, String description){
        Builder b = new Builder(type, id, name, description);
        return b.setParent(parent).build();
    }
    @Deprecated
    public static TenetGroup builder(TGType type, TenetGroup parent, Collection<TenetGroup> connected, String id, String name, String description){
        return new Builder(type, id, name, description).setParent(parent).addConnected(connected).build();
    }

    public static class Builder {
        private final TGType type;
        private final String id;
        private final String name;
        private final String description;
        private TenetGroup parent;
        private Level level;
        private final Map<TenetGroup, ConnectionEdge.Type> connected = new HashMap<>();
        public Builder(TGType type, String id, String name, String description){
            this.type = type;
            this.id = id;
            this.name = name;
            this.description = description;
            this.level = Level.NORMAL;
        }
        public Builder(TGType type, Level level, String id, String name, String description){
            this.type = type;
            this.id = id;
            this.name = name;
            this.description = description;
            this.level = level;
        }
        public Builder setParent(TenetGroup parent){
            this.parent = parent;
            return this;
        }
        public Builder addConnected(TenetGroup connected){
            this.connected.put(connected, ConnectionEdge.Type.INFLUENCING);
            return this;
        }

        public Builder addDependent(TenetGroup dependent){
            this.connected.put(dependent, ConnectionEdge.Type.DEPENDENT);
            return this;
        }
        public Builder addDependent(TenetGroup... dependent){
            for (TenetGroup d : dependent){
                addDependent(d);
            }
            return this;
        }
        public Builder addDependent(Collection<TenetGroup> dependent){
            for (TenetGroup d : dependent){
                addDependent(d);
            }
            return this;
        }
        public Builder addConnected(Collection<TenetGroup> connected){
            for (TenetGroup c : connected){
                addConnected(c);
            }
            return this;
        }
        public Builder setLevel(Level level){
            this.level = level;
            return this;
        }
        public Builder addConnected(TenetGroup... connected){
            for (TenetGroup c : connected){
                addConnected(c);
            }
            return this;
        }
        public TenetGroup build(){
            TenetGroup g = new TenetGroup(type, level, buildFullID(),id, name, description);
            final Graph<TenetGroup, DefaultEdge> parentGraph = TenetManager.Group.getParentGraph();
            final Graph<TenetGroup, ConnectionEdge> connectedGraph = TenetManager.Group.getConnectedGraph();
            parentGraph.addVertex(g);
            connectedGraph.addVertex(g);
            if (parent != null){
                validateVertex(g, parentGraph,connectedGraph);
                parentGraph.addEdge(parent, g);
                connectedGraph.addEdge(parent, g, new ConnectionEdge(ConnectionEdge.Type.STRUCTURAL));
            }
            if (!connected.isEmpty()){
                for (Map.Entry<TenetGroup, ConnectionEdge.Type> c : connected.entrySet()){
                    validateVertex(c.getKey(), connectedGraph);
                    connectedGraph.addEdge(g, c.getKey(),new ConnectionEdge(c.getValue()));
                }
            }
            TenetManager.Group.register(g);
            return g;
        }
        private String buildFullID(){
            return String.format("%s-%s", parent == null ? "culture:" : parent.getDisplayID() + ":", id);
        }
    }
    @SafeVarargs
    private static void validateVertex(TenetGroup group,Graph<TenetGroup, ? extends DefaultEdge>... graph){
        for (Graph<TenetGroup, ? extends DefaultEdge> g : graph){
            if (!g.containsVertex(group)){
                g.addVertex(group);
            }
        }
    }




    public TGType type() {
        return type;
    }


    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
