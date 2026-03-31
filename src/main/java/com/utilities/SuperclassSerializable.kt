package com.utilities

import com.google.gson.JsonObject

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
 * independently and may customize metadata handling as required. Though the validation boolean and classtype are
 * saved automatically under "class" and "isSuperSerialized" respectively, those fields should not be duplicated.
 */
interface SuperclassSerializable {

    // Serializes the object into metadata, main, and subclass JsonObjects and combines them into a single JsonObject.
    fun serialize(): JsonObject {
        val data = JsonObject()
        val metadata = JsonObject().apply {
            addProperty("class", this@SuperclassSerializable::class.java.name)
            addProperty("isSuperSerialized", true)
        }
        val main = JsonObject()
        val subclass = JsonObject()

        metadataSave(metadata)
        mainSave(main)
        additionalSave(subclass)

        data.add("metadata", metadata)
        data.add("main", main)
        data.add("subclass", subclass)

        return data
    }

    // Deserializes the given JsonObject and populates the main and subclass components of the object.
    fun deserialize(data: JsonObject) {
        val main = data.getAsJsonObject("main")
        val subclass = data.getAsJsonObject("subclass")

        mainLoad(main)
        additionalLoad(subclass)
    }

    // Allows for saving metadata into the provided JsonObject. Default implementation does nothing.
    fun metadataSave(data: JsonObject) {
        // No-op by default
    }

    // Checks whether the given JsonObject is a super serialized object.
    companion object {
        fun isSuperSerialized(data: JsonObject): Boolean {
            val metadata = data.getAsJsonObject("metadata")
            return metadata != null && metadata.has("isSuperSerialized")
        }

        // Retrieves the metadata from a JsonObject if it is super serialized. Throws an exception otherwise.
        fun getMetadata(data: JsonObject): JsonObject {
            if (!isSuperSerialized(data)) {
                throw IllegalArgumentException("Not a super serialized object: $data")
            }
            return data.getAsJsonObject("metadata")
        }
    }

    // Saves the state of the "main" component of the object into the provided JsonObject.
    fun mainSave(data: JsonObject)

    // Loads the state of the "main" component of the object from the provided JsonObject.
    fun mainLoad(data: JsonObject)

    // Saves the state of additional subclass-specific data into the provided JsonObject.
    fun additionalSave(data: JsonObject)

    // Loads the state of additional subclass-specific data from the provided JsonObject.
    fun additionalLoad(data: JsonObject)
}