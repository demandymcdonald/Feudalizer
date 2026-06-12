package com.base.loaders.global.json;

import com.base.worldforge.DataFileType;
import com.base.loaders.LoaderVars;
import com.base.loaders.global.IFile;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public abstract class JsonDataFile implements IFile {
    private final AtomicReference<Path> currentFile = new AtomicReference<>();
    private final AtomicReference<JsonObject> file = new AtomicReference<>(new JsonObject());
    private final AtomicBoolean isDirty = new AtomicBoolean(false);
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public synchronized void doSave() {
        try (FileWriter writer = new FileWriter(getCurrent().toFile())){
            LoaderVars.GSON.toJson(this.file.get(), writer);
        } catch (IOException e) {
            logger.error("Failed to save file: {}",e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @Override
    public synchronized void doLoad() {
        Path loadPath = getCurrent();
        File loadFile = loadPath.toFile();
        boolean saveAfter = false;
        try {
            loadFile.getParentFile().mkdirs();
            if (loadFile.createNewFile()) {
                saveAfter = true;
                loadPath = getDefaultLocation();
                loadFile = loadPath.toFile();
            }
        } catch (IOException | NullPointerException e) {
            logger.error("Failed to pull defaults: {}",e.getMessage());
            throw new RuntimeException(e);
        }
        JsonObject loaded = loadObject(loadPath, logger);
        if (loaded == null){
            throw new RuntimeException("Failed to load object from path: " + loadPath);
        }
        this.file.set(loaded);
        if(saveAfter){
            doSave();
        }
    }
    private static JsonObject loadObject(Path path, Logger logger){

    }

    public synchronized JsonElement getElement(String key){
        return this.file.get().get(key);
    }
    public synchronized JsonObject getJsonObject(String key){
        return this.file.get().getAsJsonObject(key);
    }
    public synchronized int getInt(String key){
        return this.file.get().get(key).getAsInt();
    }
    public synchronized boolean getBoolean(String key){
        return this.file.get().get(key).getAsBoolean();
    }
    public synchronized String getString(String key){
        return this.file.get().get(key).getAsString();
    }
    public synchronized double getDouble(String key){
        return this.file.get().get(key).getAsDouble();
    }
    public synchronized float getFloat(String key){
        return this.file.get().get(key).getAsFloat();
    }
    public synchronized long getLong(String key){
        return this.file.get().get(key).getAsLong();
    }
    public synchronized byte getByte(String key){
        return this.file.get().get(key).getAsByte();
    }
    public synchronized short getShort(String key){
        return this.file.get().get(key).getAsShort();
    }
    public synchronized <O> O getProperty(Class<O> propertyClass, String key){
        return LoaderVars.GSON.fromJson(getElement(key), propertyClass);
    }
    public synchronized <O> void setProperty(String key, O value, boolean save){
        this.file.get().add(key, LoaderVars.GSON.toJsonTree(value));
        if (save){
            doSave();
        } else {
            isDirty.set(true);
        }
    }
    public synchronized void resetToDefault(String key, boolean save){
        JsonObject defaultObj = loadObject(getDefaultLocation(), logger);
        assert defaultObj != null;
        this.file.get().add(key, defaultObj.get(key));
        if (save){
            doSave();
        } else {
            isDirty.set(true);
        }
    }
    public synchronized Set<String> getProperties(){
        return this.file.get().keySet();
    }

    @Override
    public final DataFileType getFileType() {
        return DataFileType.JSON;
    }

    @Override
    public void onExit() {
        IFile.super.onExit();
        if (isDirty.get()){
            doSave();
        }
    }

    @Override
    public void doSetCurrent(Path file) {
        currentFile.set(file);
    }

    @Override
    public Path getCurrent() {
        return currentFile.get();
    }

    @Override
    public File getFile() {
        return currentFile.get().toFile();
    }
}
