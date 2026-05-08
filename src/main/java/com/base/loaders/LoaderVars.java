package com.base.loaders;

import com.base.loaders.local.LocalLoadManager;
import com.google.gson.Gson;

import java.io.File;

public class LoaderVars {
    public static final String OS = System.getProperty("os.name").toLowerCase();
    public static final Gson GSON = new Gson();
    public static final String DME_SQL_TABLE = """
        CREATE TABLE IF NOT EXISTS dme_date_store (
            uuid TEXT PRIMARY KEY,
            class_type TEXT NOT NULL,
            session TEXT NOT NULL,
            payload TEXT NOT NULL
        );""";



    public static final String GEO_DIRECTORY_SUBPATH = "geography/";
    public static final String GEO_NAME = "geo_properties.xml";
    public static final String GEO_VISIBILITY = "visibility.json";
    public static final String PROJECT_PROPERTIES = "project.properties";

    public static File getLocalDirectory(){
        String path;
        if (OS.contains("win")) {
            path = System.getenv("APPDATA") + "/Feudalizer/";
        } else if (OS.contains("mac")) {
            path = System.getProperty("user.home") + "/Library/Application Support/Feudalizer/";
        } else {
            // Linux/Unix
            path = System.getProperty("user.home") + "/.config/Feudalizer/";
        }
        final File dir = new File(path);
        dir.mkdirs();
        return dir;
    }
}
