package com.base.loaders.global;

import com.base.worldforge.DataFileType;
import com.base.loaders.local.LocalLoader;

import java.util.concurrent.atomic.AtomicReference;

public enum HandlerType {
    APP_CONFIG(DataFileType.PROPERTIES),
    PROJECT_SETTINGS(DataFileType.PROPERTIES),
    GEOGRAPHY(DataFileType.SHP),
    GEOGRAPHY_VISIBILITY(DataFileType.JSON),
    RL_GEOGRAPHY(DataFileType.JSON),
    GEOGRAPHY_LAYER(DataFileType.JSON),
    SQL_DME(DataFileType.JSON),
    SQL_COMP(DataFileType.JSON),
    REMOTE_GZIP_UNPACK(DataFileType.GZIP);


    private final DataFileType fileType;
    private final AtomicReference<LocalLoader> localLoader = new AtomicReference<>();
    private final AtomicReference<LocalLoader> remoteLoader = new AtomicReference<>();

    HandlerType(DataFileType fileType) {
        this.fileType = fileType;
    }

    public void setRemoteLoader(LocalLoader loader){
        remoteLoader.set(loader);
    }
    public void setLocalLoader(LocalLoader loader){
        localLoader.set(loader);
    }
    public AtomicReference<LocalLoader> getRemoteLoader() {
        return remoteLoader;
    }
    public AtomicReference<LocalLoader> getLocalLoader() {
        return localLoader;
    }

}
