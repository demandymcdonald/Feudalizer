package com.objects.title.land;

import com.Feudalizer;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.display.ITLDisplayable;
import com.display.geography.GeographyManager;
import com.display.geography.GeometryType;
import com.google.gson.JsonObject;
import com.objects.title.Title;
import com.utilities.caching.CachingSupplier;
import com.utilities.serialization.CompressString;
import org.apache.commons.math3.util.Precision;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.api.referencing.operation.MathTransform;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.locationtech.jts.geom.Geometry;

import java.time.LocalDate;
import java.util.*;

public abstract class AbstractLandDivision<T extends AbstractLandDivision<T>> extends Title<T> implements ITLDisplayable<T> {
    private String sfcID;
    private GeometryType geometryType;
    private String geometryID;
    private final CachingSupplier<SimpleFeatureCollection> geometrySupplier = new CachingSupplier<>(() -> loadGeometry(geometryType, geometryID));
    private CachingSupplier<Double> areaMeters = new CachingSupplier<>(this::calculateSquareMeters);
    public AbstractLandDivision(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(id, created, ended, initialState);
    }

    public AbstractLandDivision(LocalDate created, LocalDate ended, GeometryType type, String geoID, List<ChangeSupplier<T, ?>> initialState) {
        super(created, ended, initialState);
        this.geometryType = type;
        this.geometryID = geoID;
        this.sfcID = geometrySupplier.get().getID();
    }
    public AbstractLandDivision(DMEReference<T> dme) {
        super(dme);
    }

    private SimpleFeatureCollection loadGeometry(GeometryType type, String id){
        return GeographyManager.get(type,id);
    };
    public final double getAreaMiles(){
        return Precision.round(areaMeters.get()/(2_589_988.11),2);
    }
    public final double getAreaKilometers(){
        return Precision.round(areaMeters.get()/1_000_000,2);
    }
    private double calculateSquareMeters(){
        try {
            SimpleFeatureCollection collection = geometrySupplier.get();
            CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:6933");
            MathTransform transform = CRS.findMathTransform(DefaultGeographicCRS.WGS84, targetCRS, true);
            SimpleFeatureIterator it = collection.features();
            double totalAreaSqMeters = 0;
            try {
                while (it.hasNext()) {
                    SimpleFeature feature = it.next();
                    Geometry geom = (Geometry) feature.getDefaultGeometry();
                    Geometry projected = JTS.transform(geom, transform);
                    totalAreaSqMeters += projected.getArea(); // in square meters
                }
            } finally {
                it.close();
            }
            return Precision.round(totalAreaSqMeters,2);
        } catch (Exception e) {
            Feudalizer.LOGGER.error("Error calculating area of geometry in AbstractLandDivision", e);
            throw new RuntimeException(e);
        }
    }
    public void geoAreaChanged(){
        areaMeters = new CachingSupplier<>(this::calculateSquareMeters);
    }
    @Override
    public void additionalLoad(JsonObject data) {
        super.additionalLoad(data);
        String[] split = CompressString.decompress(data.get("geo").getAsString()).split("::");
        sfcID = CompressString.decompress(split[0]);
        geometryType = GeometryType.valueOf(CompressString.decompress(split[1]));
        geometryID = CompressString.decompress(split[2]);
        if(split.length>3){
            double area = Double.parseDouble(split[3]);
            areaMeters = new CachingSupplier<>(() -> area);
        }
    }
    @Override
    public void additionalSave(JsonObject data) {
        super.additionalSave(data);
        StringBuilder sb = new StringBuilder();
        sb.append(CompressString.compress(sfcID)).append("::")
                .append(CompressString.compress(geometryType.name())).append("::")
                .append(CompressString.compress(geometryID));
        if(areaMeters.isMemoized()){
            sb.append("::").append(areaMeters.get());
        }
        data.addProperty("geo", sb.toString());
    }
}
