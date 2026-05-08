package com.base.geography;

import com.base.datemutable.DateMutableEntity;
import org.geotools.api.feature.simple.SimpleFeature;
import org.locationtech.jts.geom.Geometry;

public interface IGeographyObject {
    default SimpleFeature getFeature(){

    };
    @SuppressWarnings("unchecked")
    default Geometry getGeometry(){
        return (Geometry) getFeature().getDefaultGeometry();
    };
    String getGeoID();
    <T extends DateMutableEntity<T> & IGeographyObject> getLayer();
}
