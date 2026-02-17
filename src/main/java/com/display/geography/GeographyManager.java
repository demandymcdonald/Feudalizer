package com.display.geography;

import com.FeudalizerApp;
import com.google.common.collect.HashMultimap;
import javafx.geometry.Point2D;
import javafx.util.Pair;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.w3.xlink.Simple;

import java.util.*;

import static com.GlobalVars.SCREEN_HEIGHT;
import static com.GlobalVars.SCREEN_WIDTH;

public class GeographyManager {
    private static final HashMultimap<GeometryType, SimpleFeatureCollection> FeatureMap = HashMultimap.create();


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
    public static Point2D  calculateScale(Pair<Point2D ,Point2D > bounds) {
        double scaleX = SCREEN_WIDTH / (bounds.getValue().getX() - bounds.getKey().getX());
        double scaleY = SCREEN_HEIGHT / (bounds.getValue().getY() - bounds.getKey().getY());
        double scale = Math.min(scaleX, scaleY);
        return new Point2D ((float) scale, (float) scale);
    }
    public static Collection<SimpleFeatureCollection> getFeatures() {
        return FeatureMap.values();
    }
}
