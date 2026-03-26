package com.display.windows;

import com.Feudalizer;
import com.display.geography.GeographyManager;
import com.display.geography.GeometryType;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import org.geotools.api.feature.Property;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import static com.display.geography.GeographyManager.*;

/**
 * The MapDisplay class represents a component for rendering and interacting
 * with a map using features such as panning, zooming, and selecting geographical regions.
 * It renders map data and supports mouse-based interactions to manipulate the map view.
 *
 * Key Features:
 * - Supports panning by dragging the map.
 * - Enables zooming in and out, centered on the mouse position.
 * - Allows selection of specific regions by clicking on the map.
 * - Dynamically renders the map based on the current view scale and position.
 */
public class MapDisplay {
    private final StackPane mapDisplay;
    private boolean renderPending = false;
    private Consumer<Pair<GeometryType, String>> onCountySelected;
    private final Canvas canvas;
    private static final double LABEL_RENDER_THRESHOLD = 3;
    // Pan and zoom state
    private double offsetX = 0;
    private double offsetY = 0;
    private double scale = 1.0;
    private static final double MIN_SCALE = 0.1;
    private static final double MAX_SCALE = 10.0;

    // Drag state
    private double dragStartX;
    private double dragStartY;
    private boolean isDragging = false;

    public MapDisplay() {
        canvas = new Canvas();
        mapDisplay = new StackPane();
        canvas.widthProperty().bind(mapDisplay.widthProperty());
        canvas.heightProperty().bind(mapDisplay.heightProperty());
        mapDisplay.getChildren().add(canvas);

        canvas.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() > 0) requestRender();
        });
        canvas.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() > 0) requestRender();
        });

        setupMouseHandlers();
    }

    private void setupMouseHandlers() {
        // Mouse press - start drag
        canvas.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                dragStartX = event.getX();
                dragStartY = event.getY();
                isDragging = true;
            }
        });

        // Mouse drag - pan the map
        canvas.setOnMouseDragged(event -> {
            if (isDragging) {
                double dx = event.getX() - dragStartX;
                double dy = event.getY() - dragStartY;

                offsetX += dx;
                offsetY += dy;

                dragStartX = event.getX();
                dragStartY = event.getY();

                requestRender();
            }
        });

        // Mouse release - end drag or handle click
        canvas.setOnMouseReleased(event -> {
            if (isDragging) {
                // If barely moved, treat as click
                double dx = Math.abs(event.getX() - dragStartX);
                double dy = Math.abs(event.getY() - dragStartY);
                if (dx < 5 && dy < 5) {
                    handleClick(event.getX(), event.getY());
                }
                isDragging = false;
            }
        });

        // Scroll wheel - zoom
        canvas.setOnScroll(this::handleScroll);
    }

    private void handleScroll(ScrollEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();

        // Calculate zoom factor
        double zoomFactor = event.getDeltaY() > 0 ? 1.1 : 0.9;
        double newScale = scale * zoomFactor;

        // Clamp scale
        newScale = Math.max(MIN_SCALE, Math.min(MAX_SCALE, newScale));

        if (newScale != scale) {
            // Zoom towards mouse position
            // Before zoom, calculate what world point the mouse is over
            double worldX = (mouseX - offsetX) / scale;
            double worldY = (mouseY - offsetY) / scale;

            // Update scale
            scale = newScale;

            // After zoom, ensure mouse is still over the same world point
            offsetX = mouseX - worldX * scale;
            offsetY = mouseY - worldY * scale;

            requestRender();
        }

        event.consume();
    }

    public void setOnCountySelected(Consumer<Pair<GeometryType, String>> callback) {
        this.onCountySelected = callback;
    }

    public void requestRender() {
        if (!renderPending) {
            renderPending = true;
            Platform.runLater(() -> {
                renderMap();
                renderPending = false;
            });
        }
    }

    private void handleClick(double screenX, double screenY) {
        Feudalizer.LOGGER.info("MapDisplay: Click at (" + screenX + ", " + screenY + ")");

        Pair<Point2D, Point2D> bounds = GeographyManager.getBounds();
        Point2D baseScale = GeographyManager.calculateScale(canvas.getWidth(), canvas.getHeight(), bounds);

        // Unproject screen coords back to geo space, accounting for pan and zoom
        double geoX = ((screenX - offsetX) / scale) / baseScale.getX() + bounds.getKey().getX();
        double geoY = bounds.getValue().getY() - ((screenY - offsetY) / scale) / baseScale.getY();

        org.locationtech.jts.geom.Point clickPoint =
                new GeometryFactory().createPoint(new Coordinate(geoX, geoY));

        for (GeometryType type : GeometryType.values()) {
            for (SimpleFeatureCollection fc : GeographyManager.getFeaturesForType(type)) {
                SimpleFeatureIterator iterator = fc.features();
                try {
                    while (iterator.hasNext()) {
                        SimpleFeature feature = iterator.next();
                        Geometry geom = (Geometry) feature.getDefaultGeometry();
                        if (geom != null && geom.contains(clickPoint)) {
                            String id = resolveId(feature, type);
                            if (id != null) {
                                onCountySelected.accept(new Pair<>(type, id));
                            }
                            return;
                        }
                    }
                } finally {
                    iterator.close();
                }
            }
        }

        System.out.println("MapDisplay: No feature found at click (" + screenX + ", " + screenY + ")");
    }



    private void renderMap() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Clear canvas
        gc.setFill(Color.rgb(26, 20, 16)); // Your dark background color
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(Color.LIGHTGRAY);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(0.5);

        Pair<Point2D, Point2D> bounds = GeographyManager.getBounds();
        final Point2D baseScale = GeographyManager.calculateScale(canvas.getWidth(), canvas.getHeight(), bounds);

        for (Map.Entry<GeometryType, SimpleFeatureCollection> sfc : getFeatureMap().entries()) {
            final GeometryType currentGeometryType = sfc.getKey();
            final SimpleFeatureIterator iterator = sfc.getValue().features();
            while (iterator.hasNext()) {
                SimpleFeature feature = iterator.next();
                if (GeographyManager.isExcluded(currentGeometryType, feature)) {
                    continue;
                }
                Geometry geo = (Geometry) feature.getDefaultGeometry();
                Coordinate[] coord = geo.getCoordinates();
                countyRender(gc, splitAndScale(coord, bounds.getKey(), bounds.getValue(), baseScale));
            }
        }
        if (scale > LABEL_RENDER_THRESHOLD) {
            renderCountyLabels(gc, bounds, baseScale);
        }
    }

    private Pair<double[], double[]> splitAndScale(Coordinate[] coordinates, Point2D boundMin, Point2D boundMax, Point2D baseScale) {
        double[] x = new double[coordinates.length];
        double[] y = new double[coordinates.length];

        for (int i = 0; i < coordinates.length; i++) {
            // Apply base scaling first, then pan/zoom transform
            double baseX = (coordinates[i].getX() - boundMin.getX()) * baseScale.getX();
            double baseY = (boundMax.getY() - coordinates[i].getY()) * baseScale.getY();

            x[i] = baseX * scale + offsetX;
            y[i] = baseY * scale + offsetY;
        }
        return new Pair<>(x, y);
    }

    private static void countyRender(GraphicsContext gc, Pair<double[], double[]> coord) {
        gc.fillPolygon(coord.getKey(), coord.getValue(), coord.getKey().length);
        gc.strokePolygon(coord.getKey(), coord.getValue(), coord.getKey().length);
    }

    private void renderCountyLabels(GraphicsContext gc, Pair<Point2D, Point2D> bounds, Point2D baseScale) {
        // Very visible debug color
        gc.setFill(Color.RED);
        gc.setStroke(Color.RED);
        gc.setFont(javafx.scene.text.Font.font("Arial", 16)); // Simple font, bigger size
        gc.setTextAlign(javafx.scene.text.TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER); // CENTER the text vertically

        double minScreenX = 0;
        double maxScreenX = canvas.getWidth();
        double minScreenY = 0;
        double maxScreenY = canvas.getHeight();

        int labelCount = 0; // Debug counter

        for (SimpleFeatureCollection sfc : GeographyManager.getFeatures()) {
            SimpleFeatureIterator iterator = sfc.features();
            GeometryType currentType = getType(sfc);
            while (iterator.hasNext()) {
                SimpleFeature feature = iterator.next();
                if (GeographyManager.isExcluded(currentType, feature)) {
                    continue;
                }

                String name = (String) feature.getAttribute("NAME");
                if (name == null) continue;

                Geometry geo = (Geometry) feature.getDefaultGeometry();
                if (geo == null) continue;

                org.locationtech.jts.geom.Point centroid = geo.getCentroid();
                Coordinate c = centroid.getCoordinate();

                double baseX = (c.getX() - bounds.getKey().getX()) * baseScale.getX();
                double baseY = (bounds.getValue().getY() - c.getY()) * baseScale.getY();

                double screenX = baseX * scale + offsetX;
                double screenY = baseY * scale + offsetY;

                if (screenX < minScreenX || screenX > maxScreenX ||
                        screenY < minScreenY || screenY > maxScreenY) {
                    continue;
                }

                // Draw a debug circle at the centroid
                gc.fillOval(screenX - 3, screenY - 3, 6, 6);

                // Draw the label
                gc.fillText(name, screenX, screenY);
                labelCount++;
            }
            iterator.close();
        }

        System.out.println("Rendered " + labelCount + " labels");
    }
    private boolean multiSelectMode = false;
    private Consumer<Pair<GeometryType, String>> onCountyMultiSelected;
    private Set<Pair<GeometryType, String>> selectedCounties = new HashSet<>();

    // Add methods
    public void enterMultiSelectMode(Consumer<Pair<GeometryType, String>> callback) {
        this.multiSelectMode = true;
        this.onCountyMultiSelected = callback;
        this.selectedCounties.clear();
        requestRender();
    }

    public void exitMultiSelectMode() {
        this.multiSelectMode = false;
        this.onCountyMultiSelected = null;
        this.selectedCounties.clear();
        requestRender();
    }

    public StackPane getMapPane() {
        return mapDisplay;
    }

}