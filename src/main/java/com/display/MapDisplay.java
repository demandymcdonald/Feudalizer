package com.display;

import com.display.geography.GeographyManager;
import com.sun.javafx.geom.Point2D ;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.FeatureCollection;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;

import java.util.ArrayList;
import java.util.List;

import static com.GlobalVars.SCREEN_HEIGHT;
import static com.GlobalVars.SCREEN_WIDTH;

public class MapDisplay {
    private StackPane mapDisplay = new StackPane();
    private Scene mapDisplayScene = new Scene(mapDisplay, SCREEN_WIDTH, SCREEN_HEIGHT);
    private Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
    public MapDisplay(Stage stage) {
        mapDisplay.getChildren().add(canvas);
        renderMap();
        stage.setScene(mapDisplayScene);
        stage.show();

    }
    private void renderMap(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.LIGHTGRAY);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(0.5);
        Pair<Point2D ,Point2D > bounds = GeographyManager.getBounds();
        final Point2D  scale = GeographyManager.calculateScale(bounds);
        for(SimpleFeatureCollection sfc : GeographyManager.getFeatures()){
            final SimpleFeatureIterator iterator = sfc.features();
            while(iterator.hasNext()){
                SimpleFeature feature = iterator.next();
                Geometry geo = (Geometry) feature.getDefaultGeometry();
                Coordinate[] coord = geo.getCoordinates();
                countyRender(gc, splitAndScale(coord,bounds.getKey(),bounds.getValue(),scale));
            }
        }
    }

    private static Pair<double[],double[]> splitAndScale(Coordinate[] coordinates, Point2D  boundMin, Point2D  boundMax, Point2D  scale){
        double[] x = new double[coordinates.length];
        double[] y = new double[coordinates.length];

        for (int i = 0; i < coordinates.length; i++) {
            x[i] = (coordinates[i].x - boundMin.x) * scale.x;
            y[i] = (boundMax.y - coordinates[i].y) * scale.y;
        }
        return new Pair<>(x,y);
    }

    private static void countyRender(GraphicsContext gc, Pair<double[],double[]> coord) {
        gc.fillPolygon(coord.getKey(),coord.getValue(), coord.getKey().length);
        gc.strokePolygon(coord.getKey(),coord.getValue(), coord.getKey().length);
    }
}
