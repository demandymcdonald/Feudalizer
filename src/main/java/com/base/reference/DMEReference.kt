package com.base.reference

import com.Feudalizer
import com.base.DMRegistry
import com.base.DateMutableEntity
import com.base.ObjectType
import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import com.google.common.hash.Hashing
import com.google.gson.JsonObject
import java.nio.charset.StandardCharsets
import java.util.UUID
import java.util.concurrent.TimeUnit

class DMEReference<T : DateMutableEntity<T>> private constructor(
    private val type: Class<T>,
    private val uuid: UUID
) : StateReference() {
    private val cachedEntity: ThreadLocal<T?> = ThreadLocal()

    companion object {
        private val CACHE: Cache<Long, DMEReference<*>> = CacheBuilder.newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .maximumSize(1000)
            .build()

        private val CLASS_CACHE: Cache<String, Class<out DateMutableEntity<*>>> = CacheBuilder.newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .maximumSize(1000)
            .build()

        fun <T : DateMutableEntity<T>> of(entity: T): DMEReference<T> {
            val hash = doHash(entity.id, entity.javaClass)
            @Suppress("UNCHECKED_CAST")
            var cached = CACHE.getIfPresent(hash) as DMEReference<T>?
            if (cached == null) {
                cached = DMEReference(entity)
                CACHE.put(hash, cached)
            }
            return cached
        }

        fun <T : DateMutableEntity<T>> of(vararg entities: T): Array<DMEReference<T>> {
            return Array(entities.size) { of(entities[it]) }
        }

        fun <T : DateMutableEntity<T>> of(type: Class<T>, uuid: UUID): DMEReference<T> {
            val hash = doHash(uuid, type)
            @Suppress("UNCHECKED_CAST")
            var cached = CACHE.getIfPresent(hash) as DMEReference<T>?
            if (cached == null) {
                cached = DMEReference(type, uuid)
                CACHE.put(hash, cached)
            }
            return cached
        }

        fun <T : DateMutableEntity<T>> deserialize(jsonObject: JsonObject): DMEReference<T> {
            val name = jsonObject.get("dme_type").asString
            @Suppress("UNCHECKED_CAST")
            var clazz = CLASS_CACHE.getIfPresent(name) as Class<T>?
            if (clazz == null) {
                clazz = buildClass(name)
            }
            val uuid = jsonObject.get("uuid").asString
            return if (uuid.isNotEmpty() && clazz != null) {
                DMEReference(clazz, UUID.fromString(uuid))
            } else {
                throw RuntimeException("Could not deserialize DMEReference: $jsonObject")
            }
        }

        private fun <T : DateMutableEntity<T>> buildClass(name: String): Class<T> {
            return try {
                @Suppress("UNCHECKED_CAST")
                val clazz = Class.forName(name) as Class<T>
                CLASS_CACHE.put(name, clazz)
                clazz
            } catch (e: ClassNotFoundException) {
                Feudalizer.LOGGER.error(e.message)
                throw RuntimeException("Could not find class $name", e)
            }
        }

        private fun <T : DateMutableEntity<T>> doHash(uuid: UUID, type: Class<T>): Long {
            val hasher = Hashing.murmur3_128().newHasher()
            hasher.putLong(uuid.mostSignificantBits)
            hasher.putLong(uuid.leastSignificantBits)
            hasher.putString(type.name, StandardCharsets.UTF_8)
            return hasher.hash().asLong()
        }

        private fun doHash(reference: DMEReference<*>): Long {
            return reference.hash()
        }
    }

    private constructor(entity: T) : this(entity.javaClass, entity.id)

    fun get(): T {
        if (cachedEntity.get() == null) {
            cachedEntity.set(DMRegistry.getEntity(type, uuid))
        }
        return cachedEntity.get()!!
    }

    override fun serialize(): JsonObject {
        return JsonObject().apply {
            addProperty("Type", "DMEReference")
            addProperty("uuid", uuid.toString())
            addProperty("dme_type", type.name)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DMEReference<*>) return false
        return uuid == other.uuid && type == other.type
    }

    fun equals(type: Class<out DMEReference<*>>, uuid: UUID): Boolean {
        return this.uuid == uuid && this.type == type
    }

    override fun hashCode(): Int {
        return hash().toInt()
    }

    fun getType(): Class<T> {
        return type
    }

    fun getID(): UUID {
        return uuid
    }

    fun hash(): Long {
        return doHash(uuid, type)
    }

    override fun parse(): String {
        return get().toString()
    }
}