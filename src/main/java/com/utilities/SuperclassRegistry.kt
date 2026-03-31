package com.utilities

import com.google.gson.JsonObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import java.util.function.Consumer
import kotlin.collections.HashMap

abstract class SuperclassRegistry<R : SuperclassRegistry<R, T, OK, BA>, T : SuperclassSerializable, OK, BA>(
    private val uniqueKey: String
) : ThreadMutable<R, ObjectRegistry<T, OK>> {

    private val factoryRegistry: MutableMap<Class<out T>, Factory<out T>> = Collections.synchronizedMap(HashMap())
    private val objectRegistry: ThreadLocal<ObjectRegistry<T, OK>> = ThreadLocal.withInitial { ObjectRegistry() }

    init {
        ts_init()
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <tT : T> getFactory(subclass: Class<tT>): Factory<tT> {
        val factory = factoryRegistry[subclass] as? Factory<tT>
        return factory ?: throw RuntimeException("No factory registered for ${subclass.name}")
    }

    protected fun <tT : T> registerFactory(subclass: Class<tT>, factory: Factory<tT>) {
        factoryRegistry[subclass] = factory
    }

    fun <tT : T> loadObject(subclass: Class<tT>, key: OK, obj: JsonObject): tT {
        val instance = getFactory(subclass).load(obj)
        afterLoad(instance)
        objectRegistry.get().put(subclass, key, instance)
        return instance
    }

    fun <tT : T> createObject(subclass: Class<tT>, key: OK, argumentContainer: BA): tT {
        val instance = getFactory(subclass).create(argumentContainer)
        afterLoad(instance)
        objectRegistry.get().put(subclass, key, instance)
        return instance
    }

    fun <tT : T> remove(subclass: Class<tT>, key: OK) {
        objectRegistry.get().remove(subclass, key)
    }

    fun remove(key: OK) {
        objectRegistry.get().remove(key)
    }

    fun clear() {
        objectRegistry.get().clear()
    }

    fun <tT : T> clearSingle(subclass: Class<tT>) {
        objectRegistry.get().clearSingle(subclass)
    }

    fun <tT : T> get(subclass: Class<tT>, key: OK): tT {
        val instance = objectRegistry.get().get(subclass, key)
        return instance ?: throw RuntimeException("No object of type ${subclass.name} with key $key")
    }

    fun getAll(): Collection<out T> {
        return objectRegistry.get().getAllValues()
    }

    fun doIterate(consumer: Consumer<T>) {
        // Placeholder for functionality
    }

    fun <Tt : T> doSpecificIterate(clazz: Class<Tt>, consumer: Consumer<Tt>) {
        // Placeholder for functionality
    }

    override fun share(): ObjectRegistry<T, OK> {
        return objectRegistry.get()
    }

    override fun receiveShared(shared: ObjectRegistry<T, OK>) {
        objectRegistry.set(shared)
    }

    override fun uniqueKey(): String {
        return uniqueKey
    }

    protected abstract inner class Factory<tT : T>(
        val subclassReference: Class<tT>
    ) {
        abstract fun create(argumentContainer: BA): tT
        abstract fun load(obj: JsonObject): tT
    }

    protected open fun <tT : T> afterCreate(instance: tT) {
        // Can be implemented by subclasses
    }

    protected open fun <tT : T> afterLoad(instance: tT) {
        // Can be implemented by subclasses
    }

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(SuperclassRegistry::class.java)
    }
}