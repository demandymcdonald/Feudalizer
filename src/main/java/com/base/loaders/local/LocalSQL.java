package com.base.loaders.local;

import com.Global;
import com.base.loaders.LoaderVars;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class LocalSQL extends LocalLoader {
    private static final AtomicBoolean isRemoteFile = new AtomicBoolean(false);
    private static final AtomicReference<File> currentPath = new AtomicReference<>();
    private static final AtomicReference<Connection> saveConnection = new AtomicReference<>();
    private static final AtomicReference<Connection> cacheConnection = new AtomicReference<>();

    public static void onNewSaveLoad(File path, boolean isRemote){
        save(path,isRemote);
        clearCacheTables();

    }
    private static void save(File path, boolean isRemote){



    }
    private static void load(File path, boolean isRemote){
        CountDownLatch latch = holdLoading(()->{
            try{
                synchronized (saveConnection) {
                    synchronized (cacheConnection) {
                        currentPath.set(path);
                        isRemoteFile.set(isRemote);
                        if (isRemote) {
                            cacheConnection.set(DriverManager.getConnection("jdbc:sqlite:" + path + "local_cache.db"));
                            saveConnection.set(null);
                        } else {
                            saveConnection.set(DriverManager.getConnection("jdbc:sqlite:" + path + "save_file.db"));
                            cacheConnection.set(DriverManager.getConnection("jdbc:sqlite:" + path + "local_cache.db"));
                            validateSaveTables();
                        }
                        validateCacheTables();
                    }
                }
            } catch (Exception e){
                LOGGER.error("Failed to connect to local database", e);
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private static void clearCacheTables(){
        String sql = "DROP TABLE IF EXISTS dme_cache";
        synchronized (cacheConnection) {
            try (Statement stmt = cacheConnection.get().createStatement()) {
                stmt.execute(sql);
            }catch (Exception e){
                LOGGER.error("Failed to clear cache tables", e);
            }
        }
    }
    private static void validateCacheTables(){
        String sql = """
        CREATE TABLE IF NOT EXISTS dme_cache (
            uuid TEXT PRIMARY KEY,
            class_type TEXT NOT NULL,
            start_date TEXT NOT NULL,
            end_date TEXT NOT NULL,
            is_dirty BOOLEAN NOT NULL DEFAULT 0,
            payload TEXT NOT NULL
        );""";
        synchronized (cacheConnection) {
            try (Statement stmt = cacheConnection.get().createStatement()) {
                stmt.execute(sql);
            }catch (Exception e){
                LOGGER.error("Failed to connect to local database", e);
            }
        }
    }
    private static void validateSaveTables(){
        if (!Global.SQL_ENABLED) return;
        synchronized (saveConnection) {
            try (Statement stmt = cacheConnection.get().createStatement()) {
                stmt.execute(LoaderVars.DME_SQL_TABLE);
            }catch (Exception e){
                LOGGER.error("Failed to connect to local database", e);
            }
        }
    }

}
