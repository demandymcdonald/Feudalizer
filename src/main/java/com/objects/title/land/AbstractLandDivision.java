package com.objects.title.land;

import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.display.geography.GeographyManager;
import com.display.geography.GeometryType;
import com.google.gson.JsonObject;
import com.objects.title.Title;
import com.utilities.Displayable;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.locationtech.jts.geom.Geometry;

import java.time.LocalDate;
import java.util.*;

public abstract class AbstractLandDivision<T extends AbstractLandDivision<T>> extends Title<T> implements Displayable {
    private String id;
    private String name;
    private String description;
    private Geometry borders;
    //final Set<HumanCharacter> Visitors = new HashSet<>();
    private GeometryType geometryType;
    private String geometryID;

    public AbstractLandDivision(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(id, created, ended, initialState);
    }

    public AbstractLandDivision(LocalDate created, LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(created, ended, initialState);
    }

    public AbstractLandDivision(DMEReference<T> dme) {
        super(dme);
    }

    private void loadGeometry(GeometryType type, String id){
        GeographyManager.get(type,id);
    };

    @Override
    public void additionalLoad(JsonObject data) {
        super.additionalLoad(data);
        id = data.get("id").getAsString();
        name = data.get("name").getAsString();
        description = data.get("description").getAsString();
        geometryType = geometryType.valueOf(data.get("geometryType").getAsString());
        geometryID = data.get("geometryID").getAsString();
        //loadGeometry(geometryType,geometryID);
    }

    @Override
    public void additionalSave(JsonObject data) {
        super.additionalSave(data);
        data.addProperty("id", id);
        data.addProperty("name", name);
        data.addProperty("description", description);
        data.addProperty("geometryType", geometryType.name());
        data.addProperty("geometryID", geometryID);
    }
}
