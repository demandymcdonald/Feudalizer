package com.base.loaders.local;

import com.base.loaders.LoaderVars;
import com.google.common.collect.Sets;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class LocalLoadManager{
    private static final Set<LocalLoader> listeners = Sets.newConcurrentHashSet();
    protected static final File SAVE_PATH_LOCAL = new File(LoaderVars.getLocalDirectory(),"saves/");
    protected static final File SAVE_LOCATION_REMOTE = new File(LoaderVars.getLocalDirectory(),"remote/");
    private static String currentProjectName = "default_project";
    private static final AtomicReference<File> currentPath = new AtomicReference<>();
    private static AtomicBoolean IS_REMOTE = new AtomicBoolean(false);
    private static File buildPath(String name, boolean isRemote){
        if(isRemote){
            return new File(SAVE_LOCATION_REMOTE,name);
        }
        return new File(SAVE_PATH_LOCAL,name);
    }
    public static void setActiveSave(String name, boolean isRemote){
        currentProjectName = name;
        currentPath.set(buildPath(name,isRemote));
        validateFileOrDir(currentPath.get().getAbsolutePath(),null,null);
        IS_REMOTE.set(isRemote);
    }
    public static void registerListener(LocalLoader listener){
        listeners.add(listener);
    }
    public static File validateFileOrDir(String path, @Nullable Consumer<File> loader, @Nullable Consumer<File> defaultNew){
        File dir = new File(path);
        if(dir.mkdirs()){
            if(defaultNew != null){
                defaultNew.accept(dir);
            }
            if (loader != null){
                loader.accept(dir);
            }
        }
        return dir;
    }

}
