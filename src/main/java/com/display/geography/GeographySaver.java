package com.display.geography;

import org.geotools.api.data.SimpleFeatureStore;
import org.geotools.api.data.Transaction;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.api.feature.simple.SimpleFeatureType;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GeographySaver {

    /**
     * Saves all features registered under the given GeometryType back to its shapefile.
     * Only intended for CUST_* types. Will overwrite the existing file.
     *
     * @param type the GeometryType to save
     * @throws IOException if writing fails
     * @throws IllegalArgumentException if no features are registered for the type,
     *                                  or if the type has mixed geometry schemas
     */
    public static void save(GeometryType type) throws IOException {
        Set<SimpleFeatureCollection> collections = GeographyManager.getFeaturesForType(type);

        if (collections == null || collections.isEmpty()) {
            throw new IllegalArgumentException("No features registered for type: " + type);
        }

        // Flatten all collections into a single list
        List<SimpleFeature> allFeatures = new ArrayList<>();
        SimpleFeatureType schema = null;

        for (SimpleFeatureCollection fc : collections) {
            // Grab schema from the first collection we encounter
            if (schema == null) {
                schema = fc.getSchema();
            }

            SimpleFeatureIterator it = fc.features();
            try {
                while (it.hasNext()) {
                    allFeatures.add(it.next());
                }
            } finally {
                it.close();
            }
        }

        if (allFeatures.isEmpty()) {
            throw new IllegalArgumentException("Feature collections for type " + type + " are empty.");
        }

        writeShapefile(type, schema, allFeatures);
    }

    private static void writeShapefile(GeometryType type, SimpleFeatureType schema, List<SimpleFeature> features)
            throws IOException {

        java.io.File outputFile = type.buildFullPath().toFile();

        // Ensure parent directories exist
        Files.createDirectories(outputFile.toPath().getParent());

        // Change up the ShapefileDataStore
        ShapefileDataStoreFactory factory = new ShapefileDataStoreFactory();

        Map<String, Serializable> params = new HashMap<>();
        params.put(ShapefileDataStoreFactory.URLP.key, outputFile.toURI().toURL());
        params.put(ShapefileDataStoreFactory.CREATE_SPATIAL_INDEX.key, Boolean.TRUE);

        ShapefileDataStore dataStore = (ShapefileDataStore) factory.createNewDataStore(params);
        dataStore.createSchema(schema);

        // Write features in a transaction
        Transaction transaction = new DefaultTransaction("save-" + type.name());
        try {
            SimpleFeatureStore featureStore =
                    (SimpleFeatureStore) dataStore.getFeatureSource(dataStore.getTypeNames()[0]);
            featureStore.setTransaction(transaction);

            SimpleFeatureCollection collection = new ListFeatureCollection(schema, features);
            featureStore.addFeatures(collection);

            transaction.commit();
            System.out.println("GeographySaver: Saved " + features.size() + " features to " + outputFile.getPath());

        } catch (Exception e) {
            transaction.rollback();
            throw new IOException("Failed to save shapefile for type " + type + ": " + e.getMessage(), e);
        } finally {
            transaction.close();
            dataStore.dispose();
        }
    }
}
