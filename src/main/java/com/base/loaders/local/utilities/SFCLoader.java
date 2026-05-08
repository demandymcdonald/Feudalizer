package com.base.loaders.local.utilities;

import com.base.geography.GeoContainer;
import com.base.loaders.global.json.FileLoaderUtility;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.tuple.Pair;
import org.geotools.api.data.*;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.simple.SimpleFeatureCollection;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.*;

public class SFCLoader extends FileLoaderUtility<DataStore> {


    @Override
    protected DataStore doRead(File path, boolean shouldThrow) {

        try  {
            Map<String, Object> params = getParams(path);
            return DataStoreFinder.getDataStore(params);
        } catch (IOException e) {
            LOGGER.error("Error loading SimpleFeatureCollection from file: {}", path, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doWrite(File path, DataStore store, boolean shouldThrow) {
        try (Transaction tx = new DefaultTransaction("write")) {
            String typeName = store.getTypeNames()[0];
            SimpleFeatureStore featureStore = (SimpleFeatureStore) store.getFeatureSource(typeName);
            featureStore.setTransaction(tx);
            tx.commit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static Map<String, Object> getParams(File file){
        URI uri = file.toURI();
        try  {
            Map<String, Object> params = new HashMap<>();
            params.put("url", uri.toURL().toString());
            return params;
        }catch (MalformedURLException e) {
            LOGGER.error("Malformed URL (uri: {}) Error loading SimpleFeatureCollection from file: {}",uri,file, e);
            throw new RuntimeException(e);
        }
    }
}
