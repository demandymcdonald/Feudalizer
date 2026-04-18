package com.objects.title.land;

import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.display.geography.GeographyManager;
import com.display.geography.GeometryType;
import com.google.gson.JsonObject;
import com.objects.title.Title;
import com.utilities.IDisplayable;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.geotools.data.simple.SimpleFeatureCollection;

import java.time.LocalDate;
import java.util.*;

public abstract class AbstractLandDivision<T extends AbstractLandDivision<T>> extends Title<T> implements IDisplayable {
    private String id;
    private String name;
    private String description;
    //final Set<HumanCharacter> Visitors = new HashSet<>();
    private GeometryType geometryType;
    private String geometryID;

    public AbstractLandDivision(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(id, created, ended, initialState);
    }

    public AbstractLandDivision(LocalDate created, LocalDate ended, GeometryType type, String geoID, List<ChangeSupplier<T, ?>> initialState) {
        super(created, ended, initialState);
        this.geometryType = type;
        this.geometryID = geoID;
        SimpleFeatureCollection sfc = loadGeometry(geometryType, geometryID);
        this.id = sfc.getID();
    }

    public AbstractLandDivision(DMEReference<T> dme) {
        super(dme);
    }

    private SimpleFeatureCollection loadGeometry(GeometryType type, String id){
        return GeographyManager.get(type,id);
    };

    @Override
    public void additionalLoad(JsonObject data) {
        super.additionalLoad(data);
        id = data.get("landID").getAsString();
        geometryType = geometryType.valueOf(data.get("geometryType").getAsString());
        geometryID = data.get("geometryID").getAsString();

        //loadGeometry(geometryType,geometryID);
    }

    @Override
    public void additionalSave(JsonObject data) {
        super.additionalSave(data);
        data.addProperty("landID", id);
        data.addProperty("geometryType", geometryType.name());
        data.addProperty("geometryID", geometryID);
    }
}
