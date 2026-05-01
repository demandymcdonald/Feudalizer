package com.objects.culture.tenet.group;

import com.base.component.IComponent;
import com.base.component.InstanceType;
import com.base.component.immutable.ImmutableComponent;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.objects.culture.TenetManager;
import com.objects.culture.tenet.group.groups.*;
import com.utilities.IDisplayable;
import com.utilities.hierarchy.Parented;
import com.utilities.serialization.SuperclassSerializable;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.*;
import java.util.function.Function;

public class TenetGroup extends ImmutableComponent<TenetGroup> implements IDisplayable, Parented<TenetGroup> {
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
    protected TenetGroup parent;
    protected TenetGroup(InstanceType instType, @NonNull TGType type, Level level, String fullID, String id, String name, String description) {
        super(instType,fullID);
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
    public Set<TenetGroup> getConnected(){
        return TenetManager.Group.getConnected(this);
    }
    public Set<TenetGroup> getChildren(){
        return TenetManager.Group.getChildren(this);
    }
    public Set<TenetGroup> getAncestors(){
        return TenetManager.Group.getAncestors(this);
    }
    public Set<TenetGroup> getDescendants(){
        return TenetManager.Group.getDescendants(this);
    }
    @Override
    public final TenetGroup getNewObject(InstanceType type, String id, JsonObject data) {
        JsonObject additional = SuperclassSerializable.getAdditional(data);
        return getNewObject(
                type,
                TGType.valueOf(additional.get("tg:type").getAsString()),
                Level.valueOf(additional.get("tg:level").getAsString()),
                id,
                additional.get("tg:id").getAsString(),
                additional.get("tg:name").getAsString(),
                additional.get("tg:description").getAsString());
    }
    @SuppressWarnings("unchecked")
    public <T extends TenetGroup> T getNewObject(InstanceType instType, @NonNull TGType type, Level level, String fullID, String id,  String name, String description){
        return (T) new TenetGroup(
            instType,
            type,
            level,
            fullID,
            id,
            name,
            description);
    }

    @Override
    public void additionalSave(JsonObject data) {
        data.addProperty("tg:type", type.name());
        data.addProperty("tg:id", id);
        data.addProperty("tg:name", name);
        data.addProperty("tg:description", description);
        data.addProperty("tg:level", level.name());
        data.add("tg:parent", parent == null ? new JsonPrimitive("null") : parent.serializeRef());
    }
    @Override
    public void additionalLoad(JsonObject data) {
        if (data.has("tg:parent")){
            JsonElement e = data.get("tg:parent");
            if(e.getAsString().equals("null")){
                parent = null;
            } else {
                parent = (TenetGroup) IComponent.deserializeRef(data.get("tg:parent")).get();
            }
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

    //-----------------------------------------------------------------------------------------------------------------

    public static class Builder<T extends TenetGroup> {
        public static final Function<Builder<ReligionGroups.ReligionGroup>, ReligionGroups.ReligionGroup> RGF = (bt) -> {return  new ReligionGroups.ReligionGroup(bt.instType,bt.type,bt.level,bt.buildFullID(),bt.id,bt.name,bt.description);};
        public static final Function<Builder<GovernmentGroups.GovernmentGroup>, GovernmentGroups.GovernmentGroup> GGF = (bt) -> {return  new GovernmentGroups.GovernmentGroup(bt.instType,bt.type,bt.level,bt.buildFullID(),bt.id,bt.name,bt.description);};
        public static final Function<Builder<FamilyGroups.FamilyGroup>, FamilyGroups.FamilyGroup> FGF = (bt) -> {return  new FamilyGroups.FamilyGroup(bt.instType,bt.type,bt.level,bt.buildFullID(),bt.id,bt.name,bt.description);};
        public static final Function<Builder<MilitaryGroups.MilitaryGroup>, MilitaryGroups.MilitaryGroup> MGF = (bt) -> {return  new MilitaryGroups.MilitaryGroup(bt.instType,bt.type,bt.level,bt.buildFullID(),bt.id,bt.name,bt.description);};
        public static final Function<Builder<EconomicGroups.EconomicGroup>, EconomicGroups.EconomicGroup> ECGF = (bt) -> {return  new EconomicGroups.EconomicGroup(bt.instType,bt.type,bt.level,bt.buildFullID(),bt.id,bt.name,bt.description);};
        public static final Function<Builder<EducationGroups.EducationGroup>, EducationGroups.EducationGroup> EDGF = (bt) -> {return  new EducationGroups.EducationGroup(bt.instType,bt.type,bt.level,bt.buildFullID(),bt.id,bt.name,bt.description);};
        public static final Function<Builder<SocietyGroups.SocietyGroup>, SocietyGroups.SocietyGroup> SGF = (bt) -> {return  new SocietyGroups.SocietyGroup(bt.instType,bt.type,bt.level,bt.buildFullID(),bt.id,bt.name,bt.description);};
        Function<Builder<T>,T> factory = (bt) -> {return (T) new TenetGroup(bt.instType,bt.type,bt.level,bt.buildFullID(),bt.id,bt.name,bt.description);};
        private final InstanceType instType;
        private final TGType type;
        private final String id;
        private final String name;
        private final String description;
        private TenetGroup parent;
        private Level level;
        private final Map<TenetGroup, ConnectionEdge.Type> connected = new HashMap<>();
        public Builder(TGType type, String id, String name, String description){
            this.instType = InstanceType.HARDCODED;
            this.type = type;
            this.id = id;
            this.name = name;
            this.description = description;
            this.level = Level.NORMAL;
        }
        public Builder(InstanceType instType, TGType type, String id, String name, String description){
            this.instType = instType;
            this.type = type;
            this.id = id;
            this.name = name;
            this.description = description;
            this.level = Level.NORMAL;
        }
        public Builder(TGType type, Level level, String id, String name, String description){
            this.instType = InstanceType.HARDCODED;
            this.type = type;
            this.id = id;
            this.name = name;
            this.description = description;
            this.level = level;
        }
        public Builder(InstanceType instType, TGType type, Level level, String id, String name, String description){
            this.instType = instType;
            this.type = type;
            this.id = id;
            this.name = name;
            this.description = description;
            this.level = level;
        }

        public Builder(Function<Builder<T>,T> function, TGType type, Level level, String id, String name, String description){
            this.instType = InstanceType.HARDCODED;
            this.type = type;
            this.id = id;
            this.name = name;
            this.description = description;
            this.level = level;
            this.factory = function;
        }
        public Builder(Function<Builder<T>,T> function, InstanceType instType, TGType type, Level level, String id, String name, String description){
            this.instType = instType;
            this.type = type;
            this.id = id;
            this.name = name;
            this.description = description;
            this.level = level;
            this.factory = function;
        }
        public Builder<T> setParent(TenetGroup parent){
            this.parent = parent;
            return this;
        }
        public Builder<T> addConnected(TenetGroup connected){
            this.connected.put(connected, ConnectionEdge.Type.INFLUENCING);
            return this;
        }

        public Builder<T> addDependent(TenetGroup dependent){
            this.connected.put(dependent, ConnectionEdge.Type.DEPENDENT);
            return this;
        }
        public Builder<T> addDependent(TenetGroup... dependent){
            for (TenetGroup d : dependent){
                addDependent(d);
            }
            return this;
        }
        public Builder<T> addDependent(Collection<TenetGroup> dependent){
            for (TenetGroup d : dependent){
                addDependent(d);
            }
            return this;
        }
        public Builder<T> addConnected(Collection<TenetGroup> connected){
            for (TenetGroup c : connected){
                addConnected(c);
            }
            return this;
        }
        public Builder<T> setLevel(Level level){
            this.level = level;
            return this;
        }
        public Builder<T> addConnected(TenetGroup... connected){
            for (TenetGroup c : connected){
                addConnected(c);
            }
            return this;
        }
        public T build(){
            T g = factory.apply(this);
            final Graph<TenetGroup, DefaultEdge> parentGraph = TenetManager.Group.getParentGraph();
            final Graph<TenetGroup, ConnectionEdge> connectedGraph = TenetManager.Group.getConnectedGraph();
            parentGraph.addVertex(g);
            connectedGraph.addVertex(g);
            if (parent != null){
                g.parent = parent;
                validateVertex(g, parentGraph,connectedGraph);
                parentGraph.addEdge(parent, g);
                connectedGraph.addEdge(parent, g, new ConnectionEdge(ConnectionEdge.Type.STRUCTURAL));
            } else {
                g.parent = null;
            }
            if (!connected.isEmpty()){
                for (Map.Entry<TenetGroup, ConnectionEdge.Type> c : connected.entrySet()){
                    validateVertex(c.getKey(), connectedGraph);
                    connectedGraph.addEdge(g, c.getKey(),new ConnectionEdge(c.getValue()));
                }
            }
            return g;
        }
        private String buildFullID(){
            return String.format("%s-%s", parent == null ? "culture:" : parent.getDisplayID() + ":", id);
        }
    }

}
