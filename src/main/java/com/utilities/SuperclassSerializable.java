package com.utilities;

import com.google.gson.JsonObject;

/**
 * An interface that standardizes the serialization and deserialization of objects into three primary
 * data buckets: metadata, main, and subclass. This interface enforces a structured approach to saving
 * and loading data, ensuring organized data management for parent-child hierarchy implementations.
 *
 * The three categories of data managed by this interface are:
 * 1. Metadata: Contains information used by superclass loaders or factory methods for object
 *    initialization, validation, and locating the appropriate factory method.
 * 2. Main: Contains data meant for use by the superclass or abstract class. Typically, methods that save
 *    and load this data should be made final in superclass implementations to ensure consistency.
 * 3. Subclass: Contains data specifically used by the subclass. Implementations should call the
 *    superclass's methods at the start to maintain a hierarchical order of data.
 *
 * Implementers of this interface must provide logic for saving and loading the "main" and "subclass" data
 * independently, and may customize metadata handling as required. Though the validation boolean and classtype are
 * saved automatically under "class" and "isSuperSerialized" respectively, those fields should not be duplicated.
 */
public interface SuperclassSerializable {
    //=== The three buckets of data ===
    // 1. Metadata: Data that is meant to be used by the superclass loader/factory method. Generally to locate the right
    // factory method to use and to initialize the object. Also used for validation. HERE IS NO LOAD METHOD FOR METADATA
    // 2. Main: Data that is meant to be used by the superclass/abstract class. This bucket is routed to save/load main.
    // I personally recomend making saveMain and loadMain final in their superclass implementations.
    // 3. Subclass: Data that is meant to be used by the subclass. This bucket is routed to saveAdditional and loadAdditional.
    // In implementation (especially with nested classes), you'll need to call super to feed the object up. I reccomend
    // doing super at the start to keep the data organized by parent heirarchy.
    default JsonObject serialize(){
        JsonObject data = new JsonObject();
        JsonObject metadata = new JsonObject();
        metadata.addProperty("class",this.getClass().getName());
        metadata.addProperty("isSuperSerialized",true);
        JsonObject main = new JsonObject();
        JsonObject subclass = new JsonObject();
        metadataSave(metadata);
        mainSave(main);
        additionalSave(subclass);
        data.add("metadata",metadata);
        data.add("main",main);
        data.add("subclass",subclass);
        return data;
    }
    default void deserialize(JsonObject data){
        JsonObject main = data.get("main").getAsJsonObject();
        JsonObject subclass = data.get("subclass").getAsJsonObject();
        mainLoad(main);
        additionalLoad(subclass);
    }

    default void metadataSave(JsonObject data){

    };
    static boolean isSuperSerialized(JsonObject data){
        JsonObject metadata = data.get("metadata").getAsJsonObject();
        return metadata != null && metadata.has("isSuperSerialized");
    }
    static JsonObject getMetadata(JsonObject data){
        if (!isSuperSerialized(data)) throw new IllegalArgumentException("Not a super serialized object: "+ data.toString());
        return data.get("metadata").getAsJsonObject();
    }
    void mainSave(JsonObject object);
    void mainLoad(JsonObject object);
    void additionalSave(JsonObject data);
    void additionalLoad(JsonObject data);
}
