package com.base.loaders.global;

import com.base.worldforge.IDataDriven;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;

public interface IFile extends IDataDriven {
    static final String defaultResourcePath = "/default_data/";
    default void saveFile(){
        doSave();
    }
    default void loadFile(){
        doLoad();
    }
    default void setCurrent(Path file, boolean isFirst){
        if (!isFirst){
            saveFile();
        }
        doSetCurrent(file);
        loadFile();
    }
    void doSave();
    void doLoad();
    void doSetCurrent(Path file);
    Path getCurrent();

    String defaultResourcePath();
    default File getFile(){
        return getCurrent().toFile();
    };
    default void onExit(){
        if (isDirty()){
            saveFile();
        }
    };
    boolean isDirty();
    void setDirty(boolean dirty);

    default Path getDefaultLocation() throws NullPointerException{
        String base = this.defaultResourcePath();
        if(!base.startsWith(defaultResourcePath)){
            if( base.startsWith("/")){
                base = base.substring(1);
            }
            base = defaultResourcePath + base;
        }
        return Path.of(Objects.requireNonNull(this.getClass().getResource(this.defaultResourcePath())).getPath());
    }
}
