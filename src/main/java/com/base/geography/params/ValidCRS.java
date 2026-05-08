package com.base.geography.params;

import com.google.common.collect.ImmutableMap;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.api.referencing.crs.GeographicCRS;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeocentricCRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.referencing.operation.projection.AlbersEqualArea;
import org.geotools.referencing.operation.projection.Sinusoidal;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public enum ValidCRS {
    WGS84("wgs_84", "World Geodetic System 1984", () -> DefaultGeographicCRS.WGS84),
    WGS84_3D("wgs_84_3d", "World Geodetic System 1984 3D", () -> DefaultGeographicCRS.WGS84_3D),
    CARTESIAN("cartesian_default", "Cartesian Projection", () -> DefaultGeocentricCRS.CARTESIAN),
    SPHERICAL("spherical","Spherical Projection", () -> DefaultGeocentricCRS.SPHERICAL),
    MERCATOR_TRUE("mercator_true","True Mercator Projection", () -> decode("EPSG:3395")),
    MERCATOR_PSUEDO("mercator_pseudo","Pseudo Mercator Projection", () -> decode("EPSG:3857")),
    ALBERS("albers","Albers Equal Area Projection (US)",() -> decode("EPSG:9822")),
//    SINO("sinusoidal","Sinusoidal Projection", () -> CRS.decode()), //Too scary, maybe fix if there's demand
//    MOLL("mollweide","Mollweide Projection", () -> decode("EPSG:54001")),
//    ROBINSON("robinson","Robinson Projection", () -> decode("EPSG:54002"))
    ;
    private final String id;
    private final String name;
    private final Supplier<CoordinateReferenceSystem> crs;
    ValidCRS(String id, String name, Supplier<CoordinateReferenceSystem> crs){
        this.id = id;
        this.name = name;
        this.crs = crs;
    }
    public static final ImmutableMap<String, ValidCRS> map = ImmutableMap.copyOf(Arrays.stream(ValidCRS.values()).collect(Collectors.toMap(
            ValidCRS::getId,
            v -> v)));
    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public CoordinateReferenceSystem getCRS(){
        return crs.get();
    }
    public static CoordinateReferenceSystem decode(String epsg){
        if (!epsg.contains("EPSG:")){
            epsg = "EPSG:" + epsg;
        }
        try {
            return CRS.decode(epsg);
        } catch (Exception e){
            throw new IllegalArgumentException("Invalid EPSG code: " + epsg);
        }
    }
}
