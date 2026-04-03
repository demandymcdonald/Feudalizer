package com.display.geography;

import com.google.common.collect.HashMultimap;
import com.objects.title.TitleManager;
import javafx.geometry.Point2D;
import javafx.util.Pair;
import org.geotools.api.feature.Property;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.api.feature.simple.SimpleFeatureType;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;

import java.util.*;

public class GeographyManager {
    private static final HashMultimap<GeometryType, SimpleFeatureCollection> FeatureMap = HashMultimap.create();
    private static final Set<Integer> excludedGeometryChecksums = new HashSet<>();
    private static final SimpleFeatureType customFeatureType = featureType();


    private static SimpleFeatureType featureType(){
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setCRS(DefaultGeographicCRS.WGS84);
        builder.setName("CustomFeatureType");
        builder.add("geometry", Geometry.class);  // geometry first
        builder.add("id", String.class);
        builder.add("name", String.class);
        return builder.buildFeatureType();
    }
    public static void register(GeometryType type, SimpleFeatureCollection... collection) {
        for (SimpleFeatureCollection fc : collection) {
            FeatureMap.put(type, fc);
        }
    }
    public static Pair<Point2D, Point2D> getBounds() {
        double maxX = -Double.MAX_VALUE;  // Start from most negative
        double maxY = -Double.MAX_VALUE;
        double minX = Double.MAX_VALUE;   // Start from most positive
        double minY = Double.MAX_VALUE;

        int featureCount = 0;
        int geometryCount = 0;
        for (SimpleFeatureCollection fc : FeatureMap.values()) {
            // Manually iterate features instead of using cached bounds
            SimpleFeatureIterator iterator = fc.features();
            try {
                while (iterator.hasNext()) {
                    SimpleFeature feature = iterator.next();
                    Geometry geom = (Geometry) feature.getDefaultGeometry();
                    featureCount++;
                    if (geom != null) {
                        Envelope env = geom.getEnvelopeInternal();
                        maxX = Math.max(maxX, env.getMaxX());
                        maxY = Math.max(maxY, env.getMaxY());
                        minX = Math.min(minX, env.getMinX());
                        minY = Math.min(minY, env.getMinY());
                        geometryCount++;
                        // Debug: print first few envelopes
                        if (geometryCount <= 5) {
                            System.out.println("Feature " + geometryCount + " envelope: "
                                    + env.getMinX() + " to " + env.getMaxX());
                        }
                    }
                }
            } finally {
                iterator.close();
            }
        }
        System.out.println("Calculated bounds:");
        System.out.println("  minX: " + minX + ", maxX: " + maxX);
        System.out.println("  minY: " + minY + ", maxY: " + maxY);
        System.out.println("  X range: " + (maxX - minX));
        System.out.println("  Y range: " + (maxY - minY));
        return new Pair<>(new Point2D((float) minX, (float) minY),
                new Point2D((float) maxX, (float) maxY));
    }
    public static Point2D  calculateScale(double screenWidth, double screenHeight, Pair<Point2D ,Point2D > bounds) {
        double scaleX = screenWidth / (bounds.getValue().getX() - bounds.getKey().getX());
        double scaleY = screenHeight / (bounds.getValue().getY() - bounds.getKey().getY());
        double scale = Math.min(scaleX, scaleY);
        return new Point2D ((float) scale, (float) scale);
    }
    public static Set<SimpleFeatureCollection> getFeaturesForType(GeometryType type) {
        return FeatureMap.get(type);
    }
    public static HashMultimap<GeometryType, SimpleFeatureCollection> getFeatureMap() {
        return FeatureMap;
    }
    public static Collection<SimpleFeatureCollection> getFeatures() {
        return FeatureMap.values();
    }
    public static SimpleFeature getFeature(GeometryType type, String id) {
        Set<SimpleFeatureCollection> collections = FeatureMap.get(type);
        int hash = TitleManager.simpleChecksum(type.toString(), id);
        for (SimpleFeatureCollection fc : collections) {
            SimpleFeatureIterator iterator = fc.features();
            try {
                while (iterator.hasNext()) {
                    SimpleFeature feature = iterator.next();

                    // For FIPS, check the GEOID attribute directly
                    if (type == GeometryType.FIPS) {
                        Object geoid = feature.getAttribute("GEOID");
                        if (geoid != null && geoid.toString().equals(id)) {
                            return feature;
                        }
                    } else if (type.isCustom()) {
                        String geoid = feature.getAttribute("id").toString();
                        if (geoid.matches(id)) {
                            return feature;
                        }
                    } else {
                        // For CUST (and future types), scan all string attributes
                        for (org.geotools.api.feature.Property prop : feature.getProperties()) {
                            Object val = prop.getValue();
                            if (val instanceof String && val.toString().equals(id)) {
                                return feature;
                            }
                        }
                    }
                }
            } finally {
                iterator.close();
            }
        }

        System.err.println("GeographyManager: No feature found for type=" + type + ", reasonID=" + id);
        return null;
    }
    public static void excludeGeometry(GeometryType type, String id) {
        excludedGeometryChecksums.add(TitleManager.simpleChecksum(type.toString(), id));
    }
    public static GeometryType getType(SimpleFeatureCollection feature) {
        for (GeometryType type : GeometryType.values()) {
            if (FeatureMap.containsEntry(type, feature)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No GeometryType found for feature collection: " + feature);
    }
    public void includeGeometry(GeometryType type, String id) {
        excludedGeometryChecksums.remove(TitleManager.simpleChecksum(type.toString(), id));
    }
    public static boolean isExcluded(GeometryType type, SimpleFeature id) {
        return excludedGeometryChecksums.contains(TitleManager.simpleChecksum(type.toString(), resolveId(id, type)));
    }
    public boolean isExcluded(GeometryType type, String id) {
        return excludedGeometryChecksums.contains(TitleManager.simpleChecksum(type.toString(), id));
    }
    public int resolveID(GeometryType type, SimpleFeature geom) {
        String id = resolveId(geom, type);
        return TitleManager.simpleChecksum(type.toString(), id);
    }
    public static String resolveId(SimpleFeature feature, GeometryType type) {
        if (type == GeometryType.FIPS) {
            Object geoid = feature.getAttribute("GEOID");
            return geoid != null ? geoid.toString() : null;
        } else {
            for (Property prop : feature.getProperties()) {
                Object val = prop.getValue();
                if (val instanceof String) {
                    return val.toString();
                }
            }
        }
        return null;
    }
    public static SimpleFeature registerCustomGeometry(String name, GeometryType type, Geometry geom) {
        final int id = TitleManager.simpleChecksum(type.toString(), name);
        SimpleFeature feature = featureBuilder(String.valueOf(id),name,geom);
        DefaultFeatureCollection collection = (DefaultFeatureCollection) featureCollection(type);
        collection.add(feature);
        FeatureMap.put(type, collection);
        try {
            GeographySaver.save(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feature;
    }
    public static SimpleFeature featureBuilder(String id, String name, Geometry geom) {

        return SimpleFeatureBuilder.build(customFeatureType, new Object[]{geom, id, name}, id);
    }
    public static SimpleFeatureCollection featureCollection(GeometryType type) {
            SimpleFeatureCollection collection = FeatureMap.get(type).iterator().next();
            if (collection.isEmpty()) {
                return collection;
            }
            return new DefaultFeatureCollection(type.toString(), customFeatureType);
    }
    public static SimpleFeatureCollection get(GeometryType type, String geoID) {
        Collection<SimpleFeatureCollection> collections = FeatureMap.get(type);
        for (SimpleFeatureCollection collection : collections) {
            SimpleFeatureIterator iterator = collection.features();
            while (iterator.hasNext()) {
                SimpleFeature feature = iterator.next();
                if (feature.getAttribute("geometryID").equals(geoID)) {
                    return collection;
                }
            }
        }
        return null;
    }
}
