package com.base.geography;

import com.Global;
import com.base.component.ComponentManager;
import com.base.geography.params.LayerType;
import com.base.geography.tools.GeographyType;
import com.base.loaders.LoaderVars;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.geotools.api.feature.simple.SimpleFeatureType;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geopkg.FeatureEntry;
import org.geotools.geopkg.GeoPackage;
import org.locationtech.jts.geom.Geometry;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

import static com.base.geography.params.LayerType.Category.Administrative;
import static com.base.geography.params.LayerType.Category.Natural;

public class GeographyManager {

    private GeoPackage database = null;

    public void loadSave(Path saveFileLoc){
        Path dbLoc = saveFileLoc.resolve(LoaderVars.GEO_PACKAGE_LOCATION);
        if(database != null){
            closeDatabase();
             database = null;
        }
        try {
            database = new GeoPackage(dbLoc.toFile());
            validateTables();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load GeoPackage at " + dbLoc, e);
        }
    }
    private void validateTables() throws IOException {
        database.init();
        List<String> tableNames = database.features().stream().map(FeatureEntry::getTableName).toList();
        for (LayerType type : Layer.LAYER_TYPE_INSTANCE.getAll()){
            final String typeID = type.getID();
            if(!tableNames.contains(typeID)){
                SimpleFeatureType schema = getOurSchema(type);
                FeatureEntry entry = new FeatureEntry();
                entry.setTableName(typeID);
                entry.setDescription(type.getCategory() + " Level " + type.getPosition() + " for LayerType: " + type.getFileCode());
                database.create(entry,schema);
            }
        }
    }

    public static SimpleFeatureType getOurSchema(LayerType type){
        LayerType.Category category = type.getCategory();
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName(type.getFileCode());
        builder.setCRS(getProjectCRS());
        builder.add("id",String.class);
        builder.add("name",String.class);
        builder.add("group",String.class);
        builder.add("source",String.class);
        builder.add("geom", Geometry.class);
        if (category == Administrative || category == Natural){
            builder.add("area_sqkm",Float.class);
        }
        return builder.buildFeatureType();
    }



    public void closeDatabase(){
        if(database != null){
            database.close();
        }
    }




    public static CoordinateReferenceSystem getProjectCRS(){
        return Global.CURRENT_PROPERTIES.get().getCrs();
    }


    public static class Layer extends ComponentManager<MapLayer<?>>{
        public static final Layer INSTANCE = new Layer();
        public static final LayerTypeManager LAYER_TYPE_INSTANCE = new LayerTypeManager();
        private final ConcurrentSkipListMap<Integer, MapLayer<?>> layers = new ConcurrentSkipListMap<>(); //what a mouthful.
        public Layer() {
            super((Class<? extends MapLayer<?>>) MapLayer.class);
        }

        public synchronized int resolveNumber(int layer){
            if(layers.containsKey(layer)){
                int current = layer++;
                while(layers.containsKey(current)){
                    current++;
                }
                return current;
            }
            return layer;
        }
        public void finishRegister(MapLayer<?> layer){
            layers.put(layer.getOrder(), layer);
        }
        public Set<MapLayer<?>> getLayers(){
            return new HashSet<>(layers.values());
        }
        public static class LayerTypeManager extends ComponentManager<LayerType>{

            public LayerTypeManager() {
                super(LayerType.class);
            }
        }
    }































//
//    private static SimpleFeatureType featureType(){
//        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
//        builder.setCRS(DefaultGeographicCRS.WGS84);
//        builder.setName("CustomFeatureType");
//        builder.add("geometry", Geometry.class);  // geometry first
//        builder.add("id", String.class);
//        builder.add("name", String.class);
//        return builder.buildFeatureType();
//    }
//    public static void register(GeometryType type, SimpleFeatureCollection... collection) {
//        for (SimpleFeatureCollection fc : collection) {
//            FeatureMap.put(type, fc);
//        }
//    }
//    public static Pair<Point2D, Point2D> getBounds() {
//        double maxX = -Double.MAX_VALUE;  // Start from most negative
//        double maxY = -Double.MAX_VALUE;
//        double minX = Double.MAX_VALUE;   // Start from most positive
//        double minY = Double.MAX_VALUE;
//
//        int featureCount = 0;
//        int geometryCount = 0;
//        for (SimpleFeatureCollection fc : FeatureMap.values()) {
//            // Manually iterate features instead of using cached bounds
//            SimpleFeatureIterator iterator = fc.features();
//            try {
//                while (iterator.hasNext()) {
//                    SimpleFeature feature = iterator.next();
//                    Geometry geom = (Geometry) feature.getDefaultGeometry();
//                    featureCount++;
//                    if (geom != null) {
//                        Envelope env = geom.getEnvelopeInternal();
//                        maxX = Math.max(maxX, env.getMaxX());
//                        maxY = Math.max(maxY, env.getMaxY());
//                        minX = Math.min(minX, env.getMinX());
//                        minY = Math.min(minY, env.getMinY());
//                        geometryCount++;
//                        // Debug: print first few envelopes
//                        if (geometryCount <= 5) {
//                            System.out.println("Feature " + geometryCount + " envelope: "
//                                    + env.getMinX() + " to " + env.getMaxX());
//                        }
//                    }
//                }
//            } finally {
//                iterator.close();
//            }
//        }
//        System.out.println("Calculated bounds:");
//        System.out.println("  minX: " + minX + ", maxX: " + maxX);
//        System.out.println("  minY: " + minY + ", maxY: " + maxY);
//        System.out.println("  X range: " + (maxX - minX));
//        System.out.println("  Y range: " + (maxY - minY));
//        return new Pair<>(new Point2D((float) minX, (float) minY),
//                new Point2D((float) maxX, (float) maxY));
//    }
//    public static Point2D  calculateScale(double screenWidth, double screenHeight, Pair<Point2D ,Point2D > bounds) {
//        double scaleX = screenWidth / (bounds.getValue().getX() - bounds.getKey().getX());
//        double scaleY = screenHeight / (bounds.getValue().getY() - bounds.getKey().getY());
//        double scale = Math.min(scaleX, scaleY);
//        return new Point2D ((float) scale, (float) scale);
//    }
//    public static Set<SimpleFeatureCollection> getFeaturesForType(GeometryType type) {
//        return FeatureMap.get(type);
//    }
//    public static HashMultimap<GeometryType, SimpleFeatureCollection> getFeatureMap() {
//        return FeatureMap;
//    }
//    public static Collection<SimpleFeatureCollection> getFeatures() {
//        return FeatureMap.values();
//    }
//    public static SimpleFeature getFeature(GeometryType type, String id) {
//        Set<SimpleFeatureCollection> collections = FeatureMap.get(type);
//        int hash = TitleManager.simpleChecksum(type.toString(), id);
//        for (SimpleFeatureCollection fc : collections) {
//            SimpleFeatureIterator iterator = fc.features();
//            try {
//                while (iterator.hasNext()) {
//                    SimpleFeature feature = iterator.next();
//
//                    // For FIPS, check the GEOID attribute directly
//                    if (type == GeometryType.FIPS) {
//                        Object geoid = feature.getAttribute("GEOID");
//                        if (geoid != null && geoid.toString().equals(id)) {
//                            return feature;
//                        }
//                    } else if (type.isCustom()) {
//                        String geoid = feature.getAttribute("id").toString();
//                        if (geoid.matches(id)) {
//                            return feature;
//                        }
//                    } else {
//                        // For CUST (and future types), scan all string attributes
//                        for (org.geotools.api.feature.Property prop : feature.getProperties()) {
//                            Object val = prop.getValue();
//                            if (val instanceof String && val.toString().equals(id)) {
//                                return feature;
//                            }
//                        }
//                    }
//                }
//            } finally {
//                iterator.close();
//            }
//        }
//
//        System.err.println("GeographyManager: No feature found for type=" + type + ", reasonID=" + id);
//        return null;
//    }
//    public static void excludeGeometry(GeometryType type, String id) {
//        excludedGeometryChecksums.add(TitleManager.simpleChecksum(type.toString(), id));
//    }
//    public static GeometryType getType(SimpleFeatureCollection feature) {
//        for (GeometryType type : GeometryType.values()) {
//            if (FeatureMap.containsEntry(type, feature)) {
//                return type;
//            }
//        }
//        throw new IllegalArgumentException("No GeometryType found for feature collection: " + feature);
//    }
//    public void includeGeometry(GeometryType type, String id) {
//        excludedGeometryChecksums.remove(TitleManager.simpleChecksum(type.toString(), id));
//    }
//    public static boolean isExcluded(GeometryType type, SimpleFeature id) {
//        return excludedGeometryChecksums.contains(TitleManager.simpleChecksum(type.toString(), resolveId(id, type)));
//    }
//    public boolean isExcluded(GeometryType type, String id) {
//        return excludedGeometryChecksums.contains(TitleManager.simpleChecksum(type.toString(), id));
//    }
//    public int resolveID(GeometryType type, SimpleFeature geom) {
//        String id = resolveId(geom, type);
//        return TitleManager.simpleChecksum(type.toString(), id);
//    }
//    public static String resolveId(SimpleFeature feature, GeometryType type) {
//        if (type == GeometryType.FIPS) {
//            Object geoid = feature.getAttribute("GEOID");
//            return geoid != null ? geoid.toString() : null;
//        } else {
//            for (Property prop : feature.getProperties()) {
//                Object val = prop.getValue();
//                if (val instanceof String) {
//                    return val.toString();
//                }
//            }
//        }
//        return null;
//    }
//    public static SimpleFeature registerCustomGeometry(String name, GeometryType type, Geometry geom) {
//        final int id = TitleManager.simpleChecksum(type.toString(), name);
//        SimpleFeature feature = featureBuilder(String.valueOf(id),name,geom);
//        DefaultFeatureCollection collection = (DefaultFeatureCollection) featureCollection(type);
//        collection.add(feature);
//        FeatureMap.put(type, collection);
//        try {
//            GeographySaver.save(type);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return feature;
//    }
//    public static SimpleFeature featureBuilder(String id, String name, Geometry geom) {
//
//        return SimpleFeatureBuilder.build(customFeatureType, new Object[]{geom, id, name}, id);
//    }
//    public static SimpleFeatureCollection featureCollection(GeometryType type) {
//            SimpleFeatureCollection collection = FeatureMap.get(type).iterator().next();
//            if (collection.isEmpty()) {
//                return collection;
//            }
//            return new DefaultFeatureCollection(type.toString(), customFeatureType);
//    }
//    public static SimpleFeatureCollection get(GeometryType type, String geoID) {
//        Collection<SimpleFeatureCollection> collections = FeatureMap.get(type);
//        for (SimpleFeatureCollection collection : collections) {
//            SimpleFeatureIterator iterator = collection.features();
//            while (iterator.hasNext()) {
//                SimpleFeature feature = iterator.next();
//                if (feature.getAttribute("geometryID").equals(geoID)) {
//                    return collection;
//                }
//            }
//        }
//        return null;
//    }
}
