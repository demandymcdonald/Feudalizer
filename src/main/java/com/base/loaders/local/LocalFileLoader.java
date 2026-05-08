package com.base.loaders.local;

import com.base.geography.GeographyManager;
import com.base.geography.MapLayer;
import com.base.loaders.global.properties.ProjectProperties;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;

import static com.base.loaders.LoaderVars.GEO_DIRECTORY_SUBPATH;
import static com.base.loaders.LoaderVars.PROJECT_PROPERTIES;

public class LocalFileLoader extends LocalLoader{


    private static final String REMOTE_CONFIG = "remote_config.json";
    private static final AtomicReference<ProjectProperties> PROPERTIES_FILE = new AtomicReference<>(new ProjectProperties());
    private static final AtomicReference<File> REMOTE_CONFIG_FILE = new AtomicReference<>();
    private static final AtomicReference<Path> GEO_DIRECTORY_FILE = new AtomicReference<>();




    protected void loadGeography(){
        GeographyManager.prepareForFreshLoad();
        try {
            for (Path path : Files.walk(GEO_DIRECTORY_FILE.get()).toList()){
                File file = path.toFile();
                if(!file.isDirectory()) continue;
                String name = file.getName();
                LOGGER.debug("Found geography file: {}",name);

            }
        } catch (IOException e){
            LOGGER.error("Failed to load geography: {}",e.getMessage());
            LOGGER.debug("Stack trace: {}",e.getStackTrace());
        }

    }




    @Override
    protected void doSaveAll() {

    }

    @Override
    protected void doLoadAll() {

    }

    @Override
    public void newProjectActive(File path) {
        PROPERTIES_FILE.get().doSetCurrent(new File(path, PROJECT_PROPERTIES).toPath());
        REMOTE_CONFIG_FILE.set(new File(path, REMOTE_CONFIG));
        GEO_DIRECTORY_FILE.set(Path.of(path.toURI().getPath(), GEO_DIRECTORY_SUBPATH));
        for(MapLayer<?> layer : GeographyManager.Layer.INSTANCE.getLayers()){
            File directory = new File(GEO_DIRECTORY_FILE.get().toFile(), layer.getID());
            if(directory.mkdirs()){

            };
        }
    }
}
