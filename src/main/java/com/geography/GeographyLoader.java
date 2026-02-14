package com.geography;

import org.locationtech.jts.geom.Geometry;

import java.util.Map;

public class GeographyLoader {
    private Map<String, Geometry> usCounties;
    private Map<String, Geometry> canadianCounties;
    private Map<String, Geometry> customGeometries;

    public void init() {
    }

    public Geometry getGeometry(String source, GeometryType identifier) {
        return switch(identifier) {
            case FIPS -> usCounties.get(source);
            case CANC -> canadianCounties.get(source);
            case CUST -> customGeometries.get(source);
        };
    }


}
