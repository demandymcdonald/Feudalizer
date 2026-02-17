package com.display.geography;

import com.GlobalData;
import org.geotools.api.data.FileDataStore;
import org.geotools.api.data.FileDataStoreFinder;
import org.geotools.api.data.SimpleFeatureSource;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.api.referencing.FactoryException;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.api.referencing.operation.MathTransform;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.store.ReprojectingFeatureCollection;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class GeographyLoader {

    public void init(){
        loadAll();
    }
    public void loadAll() {

        File shapefilesDir = new File("data/shapefiles");

        // Recursively find all .shp files
        List<File> shapefiles = findAllShapefiles(shapefilesDir);

        for (File shp : shapefiles) {
            try {
                GeometryType type = determineType(shp);
                SimpleFeatureCollection features = loadShapefile(shp);

                GeographyManager.register(type, features);

                System.out.println("Loaded: " + shp.getName() + " as " + type);
            } catch (Exception e) {
                System.err.println("Failed to load: " + shp.getName());
            }
        }

    }

    private List<File> findAllShapefiles(File directory) {
        List<File> shapefiles = new ArrayList<>();

        File[] files = directory.listFiles();
        if (files == null) return shapefiles;

        for (File file : files) {

            if (file.isDirectory()) {
                shapefiles.addAll(findAllShapefiles(file));  // Recurse
            } else if (file.getName().endsWith(".shp")) {
                shapefiles.add(file);
            }
        }

        return shapefiles;
    }

    private GeometryType determineType(File shp) {
        String path = shp.getPath();

        if (path.contains("us_county")) return GeometryType.FIPS;
        if (path.contains("canada")) return GeometryType.CANA;
        if (path.contains("custom")) return GeometryType.CUST;

        return GeometryType.CUST;  // Default to custom
    }

    private SimpleFeatureCollection loadShapefile(File shp) throws IOException, FactoryException {
        FileDataStore store = FileDataStoreFinder.getDataStore(shp);
        SimpleFeatureSource source = store.getFeatureSource();
        SimpleFeatureCollection features = source.getFeatures();

        // Filter out dateline-crossing islands
        return filterDatelineIslands(features);
    }
    private SimpleFeatureCollection filterDatelineIslands(SimpleFeatureCollection features) {
        List<SimpleFeature> filtered = new ArrayList<>();

        SimpleFeatureIterator iterator = features.features();
        try {
            while (iterator.hasNext()) {
                SimpleFeature feature = iterator.next();
                Geometry geom = (Geometry) feature.getDefaultGeometry();

                // Filter out extreme east/west coordinates
                Geometry cleaned = filterGeometry(geom);

                if (cleaned != null && !cleaned.isEmpty()) {
                    feature.setDefaultGeometry(cleaned);
                    filtered.add(feature);
                }
            }
        } finally {
            iterator.close();
        }

        return new ListFeatureCollection(features.getSchema(), filtered);
    }

    private Geometry filterGeometry(Geometry geom) {
        if (geom instanceof MultiPolygon) {
            List<Polygon> kept = new ArrayList<>();
            for (int i = 0; i < geom.getNumGeometries(); i++) {
                Polygon poly = (Polygon) geom.getGeometryN(i);
                if (isValidPolygon(poly)) {
                    kept.add(poly);
                }
            }
            if (kept.isEmpty()) return null;
            return geom.getFactory().createMultiPolygon(kept.toArray(new Polygon[0]));
        } else if (geom instanceof Polygon) {
            return isValidPolygon((Polygon) geom) ? geom : null;
        }
        return geom;
    }

    private boolean isValidPolygon(Polygon poly) {
        for (Coordinate coord : poly.getCoordinates()) {
            // Cut anything near the dateline
            if (coord.x > 0 || coord.x < -170) {
                return false;
            }
            if (coord.y < 15) {  // Below 15°N
                return false;
            }
        }
        return true;
    }
}
