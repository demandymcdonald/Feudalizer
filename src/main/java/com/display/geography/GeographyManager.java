package com.display.geography;

import com.FeudalizerApp;
import com.google.common.collect.HashMultimap;
import com.sun.javafx.geom.Point2D ;
import javafx.util.Pair;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.geometry.jts.ReferencedEnvelope;
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
    public static Pair<Point2D ,Point2D > getBounds() {
        double maxX = Double.MIN_VALUE;  // Start small, grow upward
        double maxY = Double.MIN_VALUE;
        double minX = Double.MAX_VALUE;  // Start large, shrink downward
        double minY = Double.MAX_VALUE;
        for (SimpleFeatureCollection fc : FeatureMap.values()) {
            ReferencedEnvelope bounds = fc.getBounds();
            maxX = Math.max(maxX, bounds.getMaxX());
            maxY = Math.max(maxY, bounds.getMaxY());
            minX = Math.min(minX, bounds.getMinX());
            minY = Math.min(minY, bounds.getMinY());
        }
        return new Pair<>(new Point2D ((float) minX, (float) minY), new Point2D ((float) maxX, (float) maxY));
    }
    public static Point2D  calculateScale(Pair<Point2D ,Point2D > bounds) {
        double scaleX = SCREEN_WIDTH / (bounds.getValue().x - bounds.getKey().x);
        double scaleY = SCREEN_HEIGHT / (bounds.getValue().y - bounds.getKey().y);
        return new Point2D ((float) scaleX, (float) scaleY);
    }
    public static Collection<SimpleFeatureCollection> getFeatures() {
        return FeatureMap.values();
    }
}
