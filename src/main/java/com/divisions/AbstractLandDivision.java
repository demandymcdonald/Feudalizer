package com.divisions;

import com.base.DMRegistry;
import com.base.DateMutableEntity;
import com.geography.GeometryType;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.people.Character;
import com.people.CharacterManager;
import org.locationtech.jts.geom.Geometry;

import java.util.*;

public abstract class AbstractLandDivision extends DateMutableEntity<AbstractLandDivision.MutableLandContainer> {
    Geometry Borders;
    Optional<Character> Holder;
    Optional<AbstractLandDivision> Parent;
    Set<AbstractLandDivision> Children = new HashSet<>();
    Set<Character> Visitors = new HashSet<>();
    GeometryType GeometryType;
    String GeometryID;
    JsonObject passthrough;

    public record MutableLandContainer(Optional<UUID> Holder, Optional<UUID> parent, Set<UUID> Children, Set<UUID> visitors, GeometryType GeometryType, String GeometryID, JsonObject passthrough) {
        public JsonObject serialize() {
            JsonObject json = new JsonObject();
            json.addProperty("Holder", Holder.isPresent() ? Holder.get().toString() : "");
            json.addProperty("GeometryType", GeometryType.toString());
            json.addProperty("GeometryID", GeometryID);
            json.addProperty("Parent", parent.isPresent() ? parent.get().toString() : "");
            json.add("Passthrough", passthrough);
            JsonArray children = new JsonArray();
            for (UUID child : Children) {
                children.add(child.toString());
            }
            JsonArray visit = new JsonArray();
            for (UUID vis : visitors) {
                visit.add(vis.toString());
            }
            json.add("Visitors", visit);
            json.add("Children", children);
            return json;
        }
        public static MutableLandContainer deserialize(JsonObject json) {
            Set<UUID> children = Sets.newHashSet();
            for (JsonElement child : json.get("Children").getAsJsonArray()) {
                children.add(UUID.fromString(child.getAsString()));
            }
            Set<UUID> visit = Sets.newHashSet();
            for (JsonElement child : json.get("Visitors").getAsJsonArray()) {
                visit.add(UUID.fromString(child.getAsString()));
            }
            String parentString = json.get("Parent").getAsString();
            String holderString = json.get("Holder").getAsString();
            Optional<UUID> parent;
            Optional<UUID> holder;
            if (holderString.equals("")) {
                holder = Optional.empty();
            } else {
                holder = Optional.of(UUID.fromString(holderString));
            }
            if (parentString.equals("")) {
                parent = Optional.empty();
            } else {
                parent = Optional.of(UUID.fromString(parentString));
            }
            GeometryType geometryType = com.geography.GeometryType.valueOf(json.get("GeometryType").getAsString());
            JsonObject passthrough = json.get("Passthrough").getAsJsonObject();
            MutableLandContainer MLC = new MutableLandContainer(holder, parent, children, visit, geometryType, json.get("GeometryID").getAsString(),passthrough);
            return MLC;
        }
        public static MutableLandContainer builder(Optional<Character> Holder, Optional<AbstractLandDivision> Parent, Set<AbstractLandDivision> Children, Set<Character> Visitors, GeometryType GeometryType, String GeometryID, JsonObject passthrough) {
            Optional<UUID> parentID;
            if (Parent.isPresent()) {
                parentID = Optional.of(Parent.get().getId());
            } else {
                parentID = Optional.empty();
            }
            Optional<UUID> holderID;
            if (Holder.isPresent()) {
                holderID = Optional.of(Holder.get().getId());
            } else {
                holderID = Optional.empty();
            }
            Set<UUID> children = Sets.newHashSet();
            for (AbstractLandDivision child : Children) {
                children.add(child.getId());
            }
            Set<UUID> visitor = Sets.newHashSet();
            for (Character child : Visitors) {
                visitor.add(child.getId());
            }
            return new MutableLandContainer(holderID, parentID, children, visitor, GeometryType, GeometryID,passthrough);
        }
    }

    public AbstractLandDivision(JsonObject payload) {
        super(payload);
    }

    public AbstractLandDivision(UUID id, Date created, Date ended) {
        super(id, created, ended);
    }

    public AbstractLandDivision(UUID id, Date created, Date ended, JsonObject additionalData) {
        super(id, created, ended, additionalData);
    }
    protected abstract JsonObject getPassthroughData();
    protected abstract void setPassthroughData(JsonObject passthrough);
    @Override
    protected MutableLandContainer getCurrentState() {
        return MutableLandContainer.builder(Holder,Parent,Children,Visitors,GeometryType,GeometryID,getPassthroughData());
    }
    public void setState(MutableLandContainer state){
        Children.clear();
        Visitors.clear();
        Holder = Optional.empty();
        Parent = Optional.empty();
        GeometryType = state.GeometryType();
        GeometryID = state.GeometryID();
        passthrough = state.passthrough();
        final CharacterManager CM = DMRegistry.getCharacterManager();
        final LandManager LM = DMRegistry.getLandManager();
        if (state.Holder.isPresent()) {
            Holder = Optional.of(CM.get(state.Holder.get()));
        }
        if (state.parent().isPresent()){
            Parent = Optional.of(LM.get(state.parent.get()));
        }
        for (UUID child : state.Children) {
            Children.add(LM.get(child));
        }
        for (UUID visit : state.visitors) {
            Visitors.add(CM.get(visit));
        }
        setPassthroughData(passthrough);
    }
    @Override
    public void relink(MutableLandContainer state) {
        setState(state);
        onRelink();
    }
    protected abstract void onRelink();
    @Override
    protected JsonObject serializeData(MutableLandContainer data) {
        return data.serialize();
    }



    @Override
    protected MutableLandContainer buildState(JsonObject o) {
        return MutableLandContainer.deserialize(o);
    }

    public Geometry Borders() {
        return Borders;
    }

    public Set<AbstractLandDivision> Children() {
        return Children;
    }

    public String GeometryID() {
        return GeometryID;
    }

    public GeometryType GeometryType() {
        return GeometryType;
    }

    public Optional<Character> Holder() {
        return Holder;
    }

    public Optional<AbstractLandDivision> Parent() {
        return Parent;
    }

    public Set<Character> Visitors() {
        return Visitors;
    }

    @Override
    protected JsonObject saveAdditional(JsonObject j) {
        j.addProperty("LandType", this.getClass().getSimpleName());
        return super.saveAdditional(j);
    }
}
