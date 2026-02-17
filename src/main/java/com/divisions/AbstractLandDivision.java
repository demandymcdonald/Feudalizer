package com.divisions;

import com.display.geography.GeometryType;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.people.Character;
import com.succession.Title;
import org.locationtech.jts.geom.Geometry;

import java.util.*;

public abstract class AbstractLandDivision<T extends AbstractLandDivision<T>> extends Title<T> {
    Geometry Borders;
    Set<Character> Visitors = new HashSet<>();
    GeometryType GeometryType;
    String GeometryID;
    MutableLandContainer container;
    public record MutableLandContainer(Set<UUID> visitors, GeometryType GeometryType, String GeometryID, JsonObject subPassthrough) {
        public JsonObject serialize() {
            JsonObject json = new JsonObject();
            json.addProperty("GeometryType", GeometryType.toString());
            json.addProperty("GeometryID", GeometryID);
            json.add("Passthrough", subPassthrough);
            JsonArray visit = new JsonArray();
            for (UUID vis : visitors) {
                visit.add(vis.toString());
            }
            json.add("Visitors", visit);
            return json;
        }
        public static MutableLandContainer deserialize(JsonObject json) {
            Set<UUID> visit = Sets.newHashSet();
            for (JsonElement child : json.get("Visitors").getAsJsonArray()) {
                visit.add(UUID.fromString(child.getAsString()));
            }
            GeometryType geometryType = com.display.geography.GeometryType.valueOf(json.get("GeometryType").getAsString());
            JsonObject passthrough = json.get("Passthrough").getAsJsonObject();
            MutableLandContainer MLC = new MutableLandContainer(visit, geometryType, json.get("GeometryID").getAsString(),passthrough);
            return MLC;
        }
        public static MutableLandContainer builder(Set<Character> Visitors, GeometryType GeometryType, String GeometryID, JsonObject passthrough) {
            Set<UUID> visitor = Sets.newHashSet();
            for (Character child : Visitors) {
                visitor.add(child.getId());
            }
            return new MutableLandContainer(visitor, GeometryType, GeometryID,passthrough);
        }
    }

    public AbstractLandDivision(JsonObject payload) {
        super(payload);
    }

    @Override
    public boolean isInheritable() {
        return true;
    }

    @Override
    public boolean isSubPropagating() {
        return false;
    }

    public AbstractLandDivision(UUID id, Date created, Date ended) {
        super(id, created, ended);
    }

    public AbstractLandDivision(UUID id, Date created, Date ended, JsonObject additionalData) {
        super(id, created, ended, additionalData);
    }
    protected abstract JsonObject getPassthroughData();

    @Override
    protected void onNewStateLoad(JsonObject passthrough) {
        JsonObject subPassthrough = passthrough.get("AbstractLandDivision").getAsJsonObject();
        container = MutableLandContainer.deserialize(subPassthrough);
    }

    @Override
    protected JsonObject updateState(JsonObject j) {
        if (container != null){
            j.add("AbstractLandDivision", container.serialize());
        }

        return j;
    }

}
