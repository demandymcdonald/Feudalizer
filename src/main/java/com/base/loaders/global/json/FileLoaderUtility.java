package com.base.loaders.global.json;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

public abstract class FileLoaderUtility<T> {
    public static final Logger LOGGER = LoggerFactory.getLogger(FileLoaderUtility.class);
    public final T read(Path path, @Nullable Supplier<T> defaultObject, boolean shouldThrow){
        return doRead(validatePath(path,this,defaultObject,shouldThrow), shouldThrow);
    }
    public final void write(Path path, T json, boolean shouldThrow){
        doWrite(validatePath(path,null,null,shouldThrow), json, shouldThrow);
    }
    protected abstract T doRead(Path path, boolean shouldThrow);
    protected abstract void doWrite(Path path, T json, boolean shouldThrow);
    public static <T> Path validatePath(Path path, boolean shouldThrow){
        return validatePath(path,null,null,shouldThrow);
    }
    public static <T> Path validatePath(@MonotonicNonNull Path path, @Nullable FileLoaderUtility<T> utility, @Nullable Supplier<T> defaultObject, boolean shouldThrow){
        try {
            if (path.getParent().toFile().mkdirs()){
                LOGGER.debug("Created Directories up to: {}", path.getParent());
            }
            if(path.toFile().createNewFile()){
                LOGGER.debug("Created file: {}", path);
                if(defaultObject != null && utility != null){
                    utility.doWrite(path, defaultObject.get(),shouldThrow); ;
                }
            }
        } catch (IOException e) {
            LOGGER.error("Failed to create file: {}", path, e);
            LOGGER.debug("Stacktrace Write: {}",e.getStackTrace());
            throw new RuntimeException(e);
        }
        return path;
    }
}
