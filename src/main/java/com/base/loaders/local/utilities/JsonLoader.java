package com.base.loaders.local.utilities;

import com.Global.*;
import com.base.loaders.LoaderVars;
import com.base.loaders.global.json.FileLoaderUtility;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class JsonLoader extends FileLoaderUtility<JsonObject> {
    @Override
    protected JsonObject doRead(File path, boolean shouldThrow){
        try (FileReader reader = new FileReader(path)){
            return LoaderVars.GSON.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            LOGGER.error("Failed to read object from path: {}", path, e);
            if (shouldThrow){
                throw new RuntimeException(e);
            }
            LOGGER.debug("Stacktrace Read: {}",e.getStackTrace());
            return null;
        }
    }
    @Override
    protected void doWrite(File path, JsonObject json, boolean shouldThrow){
        try (FileWriter writer = new FileWriter(path)){
            LoaderVars.GSON.toJson(json, writer);
        } catch (IOException e) {
            LOGGER.error("Failed to write object to path: {}", path, e);
            if (shouldThrow){
                throw new RuntimeException(e);
            }
            LOGGER.debug("Stacktrace Write: {}",e.getStackTrace());
        }
    }
}
