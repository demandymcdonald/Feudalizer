package com.display.geography;

import com.GlobalData;
import org.geotools.api.data.FileDataStore;
import org.geotools.api.data.FileDataStoreFinder;
import org.geotools.api.data.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureCollection;

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

    private SimpleFeatureCollection loadShapefile(File shp) throws IOException {
        FileDataStore store = FileDataStoreFinder.getDataStore(shp);
        SimpleFeatureSource source = store.getFeatureSource();
        return source.getFeatures();
    }

}
