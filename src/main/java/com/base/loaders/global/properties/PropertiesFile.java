package com.base.loaders.global.properties;

import com.base.worldforge.DataFileType;
import com.base.loaders.global.IFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.*;
import java.nio.file.Path;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public abstract class PropertiesFile<T extends PropertiesFile<T>> extends Properties implements IFile {
    private volatile AtomicReference<Path> currentFile = new AtomicReference<>();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AtomicBoolean isDirty = new AtomicBoolean(false);
    public final File getFile(){
        return currentFile.get().toFile();
    }

    @Override
    public void onExit() {
        IFile.super.onExit();
    }

    @Override
    public boolean isDirty() {
        return false;
    }

    @Override
    public void setDirty(boolean dirty) {

    }

    ;
    protected final boolean isXML(){
        return defaultResourcePath().endsWith(".xml");
    };

    public final Path getCurrent(){
        return currentFile.get();
    }
    public final void doSetCurrent(Path path){
        this.currentFile.set(path);
    }




    @Override
    public final void saveFile() {
        saveFile(null);
    }
    public final void saveFile(@Nullable String comment){
        if (getFile() == null) throw new RuntimeException("Runtime path for: "+ this.getClass()+" is null");
        try (OutputStream out = new FileOutputStream(getFile())){
            if (isXML()) {
                this.storeToXML(out, comment);
            } else {
                this.store(out, comment);
            }

        } catch (IOException e) {
            logger.error("Could not save file: {}", getFile().getAbsolutePath(), e);
            throw new RuntimeException(e);
        }
        doSave();
    }

    @Override
    public final DataFileType getFileType() {
        return isXML() ? DataFileType.XML : DataFileType.PROPERTIES;
    }

    public final void loadFile(){
        boolean saveAfter = false;
        if (getFile() == null) throw new RuntimeException("Runtime path for: "+ this.getClass()+" is null");
        File file = getFile();
        getFile().getParentFile().mkdirs();
        try {
            if (!file.exists()) {
                getFile().createNewFile();
                file = getDefaultLocation().toFile();
                saveAfter = true;
            }
        } catch (IOException e) {
            logger.error("Failed to create file: {}", getFile().getAbsolutePath(), e);
            throw new RuntimeException(e);
        }
        try (InputStream in = new FileInputStream(file)){
            if (this.isXML()){
                loadFromXML(in);
            } else {
                load(in);
            }
            if (saveAfter){
                saveFile("Initial");
            }
        } catch (IOException e) {
            logger.error("Failed to load file: {}", getFile().getAbsolutePath(), e);
            throw new RuntimeException(e);
        }
        doLoad();
    }

}
