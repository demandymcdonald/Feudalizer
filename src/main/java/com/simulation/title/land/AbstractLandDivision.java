package com.simulation.title.land;

import com.base.DMRegistry;
import com.display.geography.GeometryType;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simulation.people.BookCharacter;
import com.simulation.people.CharacterManager;
import com.simulation.title.Title;
import com.simulation.title.TitleManager;
import org.locationtech.jts.geom.Geometry;

import java.time.LocalDate;
import java.util.*;

public abstract class AbstractLandDivision<T extends AbstractLandDivision<T>> extends Title<T> {
    String Name;
    Geometry Borders;
    final Set<BookCharacter> Visitors = new HashSet<>();
    GeometryType GeometryType;
    String GeometryID;
    MutableLandContainer Container;
    private int Checksum = 0;
    public record MutableLandContainer(String name, Set<UUID> visitors, GeometryType GeometryType, String GeometryID, JsonObject subPassthrough) {
        public JsonObject serialize() {
            JsonObject json = new JsonObject();
            json.addProperty("Name", name);
            json.addProperty("GeometryType", GeometryType.toString());
            json.addProperty("GeometryID", GeometryID);
            json.add("Passthrough", subPassthrough);
            JsonArray visit = new JsonArray();
            if (visitors != null) {
                for (UUID vis : visitors) {
                    visit.add(vis.toString());
                }
            }
            json.add("Visitors", visit);
            return json;
        }
        public static MutableLandContainer deserialize(AbstractLandDivision<?> ald, JsonObject json) {
            Set<UUID> visit = Sets.newHashSet();
            for (JsonElement child : json.get("Visitors").getAsJsonArray()) {
                visit.add(UUID.fromString(child.getAsString()));
            }
            GeometryType geometryType = com.display.geography.GeometryType.valueOf(json.get("GeometryType").getAsString());
            JsonObject passthrough = json.get("Passthrough").getAsJsonObject();
            MutableLandContainer MLC = new MutableLandContainer(json.get("Name").getAsString(),visit, geometryType, json.get("GeometryID").getAsString(),passthrough);
            ald.Name = json.get("Name").getAsString();
            ald.Container = MLC;
            ald.GeometryID = json.get("GeometryID").getAsString();
            ald.GeometryType = geometryType;
            if (ald.GeometryID != null && ald.GeometryType != null){
                ald.Checksum = TitleManager.simpleChecksum(ald.GeometryType.toString(), ald.GeometryID);
            } else {
                ald.Checksum = 0;
            }
            ald.Visitors.clear();
            return MLC;
        }
        public static MutableLandContainer builder(String name, Set<BookCharacter> Visitors, GeometryType GeometryType, String GeometryID, JsonObject passthrough) {
            Set<UUID> visitor = Sets.newHashSet();
            if (Visitors != null) {
                for (BookCharacter child : Visitors) {
                    visitor.add(child.getId());
                }
            }
            return new MutableLandContainer(name, visitor, GeometryType, GeometryID,passthrough);
        }
    }

    public AbstractLandDivision(JsonObject payload) {
        super(payload);
    }


    public AbstractLandDivision(UUID id, String name, LocalDate created, LocalDate ended, GeometryType type, String GeoID) {
        super(id, created, ended);
        GeometryType = type;
        GeometryID = GeoID;
        Checksum = TitleManager.simpleChecksum(type.toString(),GeoID);
        this.Name = name;
        Container = MutableLandContainer.builder(name,new HashSet<>(), GeometryType, GeometryID, new JsonObject());

    }

    public AbstractLandDivision(UUID id, String name, LocalDate created, LocalDate ended, JsonObject additionalData) {
        super(id, created, ended, additionalData);
        this.Name = name;
        Container = MutableLandContainer.builder(name,new HashSet<>(), null, null, new JsonObject());
    }
    protected abstract JsonObject getPassthroughData();

    @Override
    protected void onRelink() {
        CharacterManager cm = DMRegistry.getCharacterManager();
        Visitors.clear();
        for (UUID id : Container.visitors){
            Visitors.add(cm.get(id));
        }
    }
    @Override
    public boolean isInheritable() {
        return true;
    }

    @Override
    public boolean isSubPropagating() {
        return false;
    }

    @Override
    protected void onNewStateLoad(JsonObject passthrough) {
        JsonObject subPassthrough = passthrough.get("AbstractLandDivision").getAsJsonObject();
        Container = MutableLandContainer.deserialize(this,subPassthrough);
    }
    protected String getName(){
        return this.Name;
    }
    @Override
    protected JsonObject updateState(JsonObject j) {
        AbstractLandDivision.MutableLandContainer MLC = MutableLandContainer.builder(Name, Visitors, GeometryType, GeometryID, getPassthroughData());
        Container = MLC;
        j.add("AbstractLandDivision", MLC.serialize());
        return j;
    }
    public int getGeoID(){
        return Checksum;
    }
}
