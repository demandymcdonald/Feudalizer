package com.base.loaders.global;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public abstract class PackageFile<T extends PackageFile<T>> implements IFile {
    protected final Map<String,IFile> files = new ConcurrentHashMap<>();
    private final AtomicReference<Path> basePath = new AtomicReference<>();
    //OOPs: do base/overriden logic first (if relevant), then do for children. Then run after hook (if relevant)
    private PackageFile(){}
    @Override
    public boolean isDirty() {
        return files.values().stream().anyMatch(IFile::isDirty);
    }

    public <T extends IFile> T getFile(String name){
        return (T) files.get(name);
    }
    @Override
    public final void loadFile() {
        IFile.super.loadFile();
        for(IFile file : files.values()){
            file.loadFile();
        }
        afterLoad();
    }
    @Override
    public final void saveFile() {
        IFile.super.saveFile();
        for(IFile file : files.values()){
            file.saveFile();
        }
        afterSave();
    }
    @Override
    public final void onExit() {
        IFile.super.onExit();
        for(IFile file : files.values()){
            file.onExit();
        }
    }
    @Override
    public final void setCurrent(Path file, boolean isFirst) {
        IFile.super.setCurrent(file, isFirst);
        basePath.set(file);
        for(Map.Entry<String,IFile> entry : files.entrySet()){
            entry.getValue().doSetCurrent(Path.of(file.toString(),entry.getKey()));
        }
        afterSetCurrent(file,isFirst);
    }
    public void afterLoad(){}
    public void afterSave(){}
    public void afterSetCurrent(Path file, boolean isFirst){}
    @Override
    public void setDirty(boolean dirty) {
        for(IFile file : files.values()){
            file.setDirty(dirty);
        }
    }
    public static class Builder<T extends PackageFile<T>> {
        private final T raw;
        public Builder(Class<T> type){
            try {
                raw = type.getDeclaredConstructor().newInstance();
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        public Builder<T> addFile(String offset, IFile file){
            if (offset.startsWith("/")){
                offset = offset.substring(1);
            } else if (offset.startsWith("\\")){
                offset = offset.substring(2);
            }
            raw.files.put(offset,file);
            return this;
        }
        T build(){
            return raw;
        }
    }
}
